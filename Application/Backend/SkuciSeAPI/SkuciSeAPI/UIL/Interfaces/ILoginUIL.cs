using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL.Interfaces
{
    public interface ILoginUIL
    {
        byte? CheckUserAndGetRole(UserLogin userLogin);
        string HashPassword(string input);
        void Logout(long userId);
        long GetId(UserLogin login);
    }
}
