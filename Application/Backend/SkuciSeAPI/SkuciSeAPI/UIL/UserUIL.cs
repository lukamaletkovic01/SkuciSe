using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class UserUIL : IUserUIL
    {
        private readonly IUserBLL _IUserBLL;

        public UserUIL(IUserBLL _IUserBLL)
        {
            this._IUserBLL = _IUserBLL;
        }

        public void ChangePassword(long userId, string recoveryCode, string newPassword)
        {
            _IUserBLL.ChangePassword(userId, recoveryCode, newPassword);
        }

        public bool CheckEmail(string email)
        {
            return _IUserBLL.CheckEmail(email);
        }

        public bool CreateUser(User user)
        {
            return _IUserBLL.CreateUser(user);
        }

        public void DeleteUser(long id)
        {
            this._IUserBLL.DeleteUser(id);
        }

        public List<User> GetAllUsers()
        {
            return _IUserBLL.GetAllUsers();
        }

        public User GetById(long id)
        {
            return this._IUserBLL.GetById(id);
        }

        public User GetUser(string email, string password)
        {
            return _IUserBLL.GetUser(email, password);
        }

        public void UpdateFcmToken(long userId, string fcmToken)
        {
            _IUserBLL.UpdateFcmToken(userId, fcmToken);
        }


        public void UpdatePassword(long userId, string password, string newPassword)
        {
            _IUserBLL.UpdatePassword(userId, password, newPassword);
        }

        public void UpdateUser(User user)
        {
            this._IUserBLL.UpdateUser(user);
        }
    }
}
