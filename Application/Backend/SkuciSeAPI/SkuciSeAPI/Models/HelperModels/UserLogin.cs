using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class UserLogin
    {
        public long Id { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public byte Role { get; set; }
        public string Token { get; set; }
        public string FcmToken { get; set; }
    }
}
