using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class VerificationUIL : IVerificationUIL
    {
        private readonly IVerificationBLL _IVerificationBLL;

        public VerificationUIL(IVerificationBLL iVerificationBLL)
        {
            _IVerificationBLL = iVerificationBLL;
        }

        public long Recover(Recovery v)
        {
            return _IVerificationBLL.Recover(v);
        }

        public void SendRecoveryCode(string email)
        {
            _IVerificationBLL.SendRecoveryCode(email);
        }

        public void SendVerificationCode(string email, string name)
        {
            _IVerificationBLL.SendVerificationCode(email, name);
        }

        public bool Verify(Verification v)
        {
            return _IVerificationBLL.Verify(v);
        }
    }
}
