using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class VerificationBLL : IVerificationBLL
    {
        private readonly IVerificationDAL _IVerificationDAL;
        private readonly IUserDAL _IUserDAL;

        public VerificationBLL(IVerificationDAL iVerificationDAL, IUserDAL _IUserDAL)
        {
            _IVerificationDAL = iVerificationDAL;
            this._IUserDAL = _IUserDAL;
        }

        public long Recover(Recovery v)
        {
            Recovery rec = _IVerificationDAL.CheckRecovery(v);

            if (rec != null)
            {
                User u = _IUserDAL.GetByEmail(v.Email);
                return u.Id;
            }
            return -1;
        }
        
        public void SendRecoveryCode(string email)
        {
            Random random = new Random();
            string activationCode = random.Next(1001, 9999).ToString();
            User u = _IUserDAL.GetByEmail(email);
            if (u != null)
            {
                SendRecoveryMail(email, u.Firstname, activationCode);

                Recovery v = new Recovery();
                v.Code = activationCode;
                v.Email = email;
                v.Timestamp = DateTime.Now;
                _IVerificationDAL.CreateRecovery(v);
            }
            else
            {
                throw new Exception();
            }
        }

        public void SendVerificationCode(string email, string name)
        {
            Random random = new Random();
            string activationCode = random.Next(1001, 9999).ToString();

            SendMail(email, name, activationCode);

            Verification v = new Verification();
            v.Code = activationCode;
            v.Email = email;
            v.Verified = false;
            _IVerificationDAL.Create(v);

        }

        public bool Verify(Verification v)
        {
            Verification ver = _IVerificationDAL.CheckVerification(v);
            

            if (ver != null)
            {
                ver.Verified = true;
                _IVerificationDAL.Update(ver);
                return true;
            }
            return false;
        }

        private void SendMail(string email, string name, string activationCode)
        {
            SmtpClient smtp = new SmtpClient();
            smtp.UseDefaultCredentials = false;
            smtp.Host = "smtp.gmail.com";
            smtp.Port = 587;
            smtp.Credentials = new System.Net.NetworkCredential("skucise.podrska@gmail.com", "Lozinka1");
            smtp.EnableSsl = true;

            MailMessage msg = new MailMessage();
            msg.Subject = "Aktivacioni kod za verifikovanje email adrese";
            msg.Body = "Dragi " + name + ", Vaš aktivacioni kod je " + activationCode + "\n\nHvala što koristite naše usluge,\nVaš SkućiSe tim";

            msg.To.Add(email);

            msg.From = new MailAddress("Skuci Se <skucise.podrska@gmail.com>");
            smtp.Send(msg);
        }
        private void SendRecoveryMail(string email, string name, string activationCode)
        {
            SmtpClient smtp = new SmtpClient();
            smtp.UseDefaultCredentials = false;
            smtp.Host = "smtp.gmail.com";
            smtp.Port = 587;
            smtp.Credentials = new System.Net.NetworkCredential("skucise.podrska@gmail.com", "Lozinka1");
            smtp.EnableSsl = true;

            MailMessage msg = new MailMessage();
            msg.Subject = "Kod za resetovanje naloga";
            msg.Body = "Dragi " + name + ", Vaš kod za resetovanje naloga je " + activationCode + "\n\nHvala što koristite naše usluge,\nVaš SkućiSe tim";

            msg.To.Add(email);

            msg.From = new MailAddress("Skuci Se <skucise.podrska@gmail.com>");
            smtp.Send(msg);
        }
    }
}
