using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IVerificationDAL : IBaseDAL<Verification>
    {
        public Verification CheckVerification(Verification v);
        public bool IsVerified(string email);
        void CreateRecovery(Recovery v);
        Recovery CheckRecovery(Recovery v);
    }
}
