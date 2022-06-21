using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.Interfaces
{
    public interface IUserBLL
    {
        public List<User> GetAllUsers();
        public bool CreateUser(User user);
        public void DeleteUser(long id);
        public User GetById(long id);
        public void UpdateUser(User user);
        public User GetUser(string email, string password);
        public bool CheckEmail(string email);
        void ChangePassword(long userId, string recoveryCode, string newPassword);
        public void UpdateFcmToken(long userId, string fcmToken);
        void UpdatePassword(long userId, string password, string newPassword);
    }
}
