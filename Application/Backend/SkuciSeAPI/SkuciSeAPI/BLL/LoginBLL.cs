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
    public class LoginBLL : ILoginBLL
    {
        private readonly IUserDAL _iUserDAL;

        public LoginBLL(IUserDAL iUserDAL)
        {
            _iUserDAL = iUserDAL;
        }

        public byte? CheckUserAndGetRole(UserLogin userLogin)
        {
            var user = _iUserDAL.GetUser(userLogin.Email, HashPassword(userLogin.Password));

            return user != null ? user.Role : null;
        }

        public string HashPassword(string input)
        {
            using (SHA1Managed sha1 = new SHA1Managed())
            {
                var hash = sha1.ComputeHash(Encoding.UTF8.GetBytes(input));
                var sb = new StringBuilder(hash.Length * 2);
                foreach (byte b in hash)
                {
                    sb.Append(b.ToString("x2"));
                }
                return sb.ToString();
            }
        }

        public void Logout(long userId)
        {

        }

        public long GetId(UserLogin login)
        {
            User user = _iUserDAL.GetUser(login.Email, HashPassword(login.Password));

            if (user != null)
                return user.Id;

            return -1;
        }

    }
}
