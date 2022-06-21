using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class UserBLL : IUserBLL
    {
        private readonly IUserDAL _IUserDAL;
        private readonly IVerificationDAL _IVerificationDAL;

        public UserBLL(IUserDAL _IUserDAL, IVerificationDAL _IVerificationDAL)
        {
            this._IUserDAL = _IUserDAL;
            this._IVerificationDAL = _IVerificationDAL;
        }

        public bool CreateUser(User user)
        {
            if (!(this._IUserDAL.IfUserExists(user.Email)) && this._IVerificationDAL.IsVerified(user.Email))
            {
                user.Password = HashPassword(user.Password);
                this._IUserDAL.Create(user);

                return true;
            }
            else
            {
                return false;
            }
        }

        private string HashPassword(string password)
        {
            using (SHA1Managed sha1 = new SHA1Managed())
            {
                var hash = sha1.ComputeHash(Encoding.UTF8.GetBytes(password));
                var sb = new StringBuilder(hash.Length * 2);
                foreach (byte b in hash)
                {
                    sb.Append(b.ToString("x2"));
                }
                return sb.ToString();
            }
        }

        public void DeleteUser(long id)
        {
            User user = this._IUserDAL.GetById(id);
            if (user == null)
            {
                throw new Exception();
            }

            this._IUserDAL.Delete(user);
        }

        public List<User> GetAllUsers()
        {
            return _IUserDAL.GetAll();
        }

        public User GetById(long id)
        {
            return this._IUserDAL.GetById(id);
        }

        public User GetUser(string email, string password)
        {
            return this._IUserDAL.GetUser(email, password);
        }

        public void UpdateUser(User user)
        {
            User newUser = this._IUserDAL.GetById(user.Id);
            if (newUser == null)
            {
                throw new Exception();
            }

            newUser.Email = user.Email;
            newUser.Firstname = user.Firstname;
            newUser.Lastname = user.Lastname;
            newUser.ProfileImage = user.ProfileImage;
            newUser.PhoneNumber = user.PhoneNumber;
            newUser.Address = user.Address;
            newUser.City = user.City;

            this._IUserDAL.Update(newUser);
        }

        public bool CheckEmail(string email)
        {
            return _IUserDAL.IfUserExists(email);
        }

        public void UpdateFcmToken(long userId, string fcmToken)
        {
            User newUser = this._IUserDAL.GetById(userId);
            if (newUser != null && !newUser.FcmToken.Equals(fcmToken))
            {
                newUser.FcmToken = fcmToken;
                this._IUserDAL.Update(newUser);
            }
        }


        public void UpdatePassword(long userId, string password, string newPassword)
        {
            if (_IUserDAL.CheckPassword(userId, HashPassword(password)))
            {
                User user = _IUserDAL.GetById(userId);
                user.Password = HashPassword(newPassword);
                _IUserDAL.Update(user);
            }
            else
            {
                throw new Exception();
            }
        }

        public void ChangePassword(long userId, string recoveryCode, string newPassword)
        {
            User u = _IUserDAL.GetById(userId);
            Recovery v = new Recovery();
            v.Email = u.Email;
            v.Code = recoveryCode;
            Recovery rec = _IVerificationDAL.CheckRecovery(v);
            if (rec != null)
            {
                u.Password = HashPassword(newPassword);
                _IUserDAL.Update(u);
            }
            else
            {
                throw new Exception();
            }
        }
    }
}
