using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL
{
    public class VerificationDAL : BaseDAL<Verification>, IVerificationDAL
    {

        private readonly ApplicationDBContext _context;

        public VerificationDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
        }

        public bool IsVerified(string email)
        {
            var result = _context.Verifications.Where(p => p.Email.Equals(email) && p.Verified.Equals(true));
            if (!(result == null || result.ToList().Count == 0))
                return true;
            return false;
        }

       

        public Verification CheckVerification(Verification v)
        {
            var result = _context.Verifications.Where(p => p.Email.Equals(v.Email) && p.Code.Equals(v.Code)).FirstOrDefault();
            return result;
        }

        public void CreateRecovery(Recovery v)
        {
            if (v == null)
            {
                throw new ArgumentNullException("Entity for create is null.");
            }
            _context.Recoveries.Add(v);
            _context.SaveChanges();

        }

        public Recovery CheckRecovery(Recovery v)
        {
            var result = _context.Recoveries.Where(p => p.Email.Equals(v.Email) && p.Code.Equals(v.Code) && (p.Timestamp.AddHours(3) > DateTime.Now)).FirstOrDefault();
            return result;
        }
    }
}
