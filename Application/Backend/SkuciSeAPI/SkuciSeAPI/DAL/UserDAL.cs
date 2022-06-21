using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using System.Linq;

namespace SkuciSeAPI.DAL
{
    public class UserDAL : BaseDAL<User>, IUserDAL
    {
        private readonly ApplicationDBContext _context;
        private DbSet<User> users = null;

        public UserDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
            this.users = _context.Set<User>();
        }

        public User GetUser(string email, string password)
        {
            return users.Where(x => x.Email.Equals(email) && x.Password.Equals(password)).FirstOrDefault();
        }

        public bool IfUserExists(string email)
        {
            return users.Any(user => user.Email == email);
        }
        public bool CheckPassword(long userId, string v)
        {
            User user = users.Where(x => x.Id.Equals(userId) && x.Password.Equals(v)).FirstOrDefault();
            if (user != null)
                return true;
            return false;
        }

        public User GetByEmail(string email)
        {
            return users.Where(x => x.Email.Equals(email)).FirstOrDefault();
        }
    }
}
