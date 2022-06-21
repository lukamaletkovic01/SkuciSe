using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IUserDAL : IBaseDAL<User>
    {
        public bool IfUserExists(String email);
        public User GetUser(string email, string password);
        public bool CheckPassword(long userId, string v);
        User GetByEmail(string email);
    }
}
