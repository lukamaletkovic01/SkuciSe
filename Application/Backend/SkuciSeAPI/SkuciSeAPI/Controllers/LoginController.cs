using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using SkuciSeAPI.Common;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly ILoginUIL _iLoginUIL;
        private readonly IUserUIL _iUserUIL;

        public LoginController(ILoginUIL iLoginUIL, IUserUIL iUserUIL)
        {
            _iLoginUIL = iLoginUIL;
            _iUserUIL = iUserUIL;
        }

        // Metoda za login.
        // U koliko korisnik postoji u bazi, token se generise, cuva u bazi i UserLogin model se vraca na front.
        [HttpPost]
        public IActionResult Login(UserLogin login)
        {
            IActionResult response = Unauthorized();
            var userRole = _iLoginUIL.CheckUserAndGetRole(login);
            var userId = _iLoginUIL.GetId(login);
            if (userRole != null)
            {
                var userLogin = JWTTokenHelper.Jwt.GenerateJSONWebToken(login, (byte)userRole, userId);
                _iUserUIL.UpdateFcmToken(userLogin.Id, userLogin.FcmToken);

                response = Ok(userLogin);
            }

            return response;
        }

        // Metoda za logout.
        [HttpGet]
        [Route("[action]/{userId}")]
        public ActionResult Logout(long userId)
        {
            _iLoginUIL.Logout(userId);
            return Ok();
        }
    }
}
