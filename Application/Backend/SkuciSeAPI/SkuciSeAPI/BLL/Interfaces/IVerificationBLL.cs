using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.Interfaces
{
    public interface IVerificationBLL
    {
        public void SendVerificationCode(string email, string name);
        public bool Verify(Verification v);
        public void SendRecoveryCode(string email);
        public long Recover(Recovery v);
    }
}
