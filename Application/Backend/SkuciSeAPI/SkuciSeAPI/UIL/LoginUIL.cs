using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class LoginUIL : ILoginUIL
    {
        private readonly ILoginBLL _iLoginBLL;

        public LoginUIL(ILoginBLL iLoginBLL)
        {
            _iLoginBLL = iLoginBLL;
        }

        public byte? CheckUserAndGetRole(UserLogin userLogin)
        {
            return _iLoginBLL.CheckUserAndGetRole(userLogin);
        }

        public string HashPassword(string input)
        {
            return _iLoginBLL.HashPassword(input);
        }

        public void Logout(long userId)
        {
            _iLoginBLL.Logout(userId);
        }
        public long GetId(UserLogin login)
        {
            return _iLoginBLL.GetId(login);
        }
    }
}
