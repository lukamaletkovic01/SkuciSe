using Microsoft.AspNetCore.Mvc;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class VerificationController : ControllerBase
    {
        private readonly IVerificationUIL _IVerificationUIL;

        public VerificationController(IVerificationUIL iVerificationUIL)
        {
            _IVerificationUIL = iVerificationUIL;
        }

        [HttpPost]
        [Route("SendVerificationCode")]
        public ActionResult SendVerificationCode(string email, string name)
        {
            try
            {
                _IVerificationUIL.SendVerificationCode(email, name);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                Console.WriteLine(e.StackTrace);
                return BadRequest();
            }
            return Ok(true);
        }

        [HttpPost]
        [Route("Verify")]
        public ActionResult Verify(string email, string code)
        {

            Verification v = new Verification();
            v.Email = email;
            v.Code = code;

            try
            {
                bool bl = _IVerificationUIL.Verify(v);
                return Ok(bl);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
        }

        [HttpPost]
        [Route("SendRecoveryCode")]
        public ActionResult SendRecoveryCode(string email)
        {
            try
            {
                _IVerificationUIL.SendRecoveryCode(email);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(true);
        }

        [HttpPost]
        [Route("Recover")]
        public ActionResult Recover(string email, string code)
        {

            Recovery v = new Recovery();
            v.Email = email;
            v.Code = code;

            try
            {
                long userId = _IVerificationUIL.Recover(v);
                return Ok(userId);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
        }


    }
}
