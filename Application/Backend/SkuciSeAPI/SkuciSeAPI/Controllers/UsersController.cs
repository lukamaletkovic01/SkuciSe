using Microsoft.AspNetCore.Authorization;
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
    public class UserController : ControllerBase
    {
        private readonly IUserUIL _IUserUIL;
        private readonly ILoginUIL _iLoginUIL;

        public UserController(IUserUIL _IUserUIL, ILoginUIL _ILoginUIL)
        {
            this._IUserUIL = _IUserUIL;
            this._iLoginUIL = _ILoginUIL;
        }

        [HttpGet]
        [Route("GetUsers")]
        public ActionResult GetUsers()
        {
            List<User> users = null;
            try
            {
                users = _IUserUIL.GetAllUsers();
            }
            catch (Exception e)
            {
                return BadRequest();
            }

            return Ok(users);
        }

        [HttpGet]
        [Route("CheckEmail")]
        public ActionResult CheckEmail(string email)
        {
            bool response = false;
            try
            {
                response = _IUserUIL.CheckEmail(email);

            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(response);
        }


        [HttpPost]
        [Route("CreateUser")]
        public ActionResult Create(User user)
        {

            try
            {
                if (this._IUserUIL.CreateUser(user))
                {
                    
                    return Ok(true);
                }
                else
                {
                    return BadRequest(false);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                Console.WriteLine(e.StackTrace);
                return BadRequest(false);
            }
        }
        [HttpGet]
        [Route("GetUserInfo")]
        public IActionResult GetUserInfo(long id)
        {
            User user = null;

            try
            {
                user = _IUserUIL.GetById(id);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok(user);

        }

        [HttpDelete]
        [Route("{id}")]
        public ActionResult Delete(long id)
        {
            try
            {
                this._IUserUIL.DeleteUser(id);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpPut]
        [Route("UpdateUser")]
        public ActionResult Update([FromBody] User user)
        {
            try
            {
                this._IUserUIL.UpdateUser(user);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }

        // Metoda za validaciju tokena.
        [HttpPost]
        [Route("[action]")]
        public IActionResult ValidateToken(UserLogin userLogin)
        {
            IActionResult response = Unauthorized();

            if (JWTTokenHelper.Jwt.ValidateJSONWebToken(userLogin).Equals(userLogin.Email))
            {
                return Ok();
            }

            return response;
        }

        [HttpPost]
        [Route("[action]")]
        public IActionResult UpdateFcmToken(UserLogin userLogin)
        {
            try
            {
                _IUserUIL.UpdateFcmToken(userLogin.Id, userLogin.FcmToken);

                return Ok();
            }
            catch
            {
                return BadRequest();
            }
        }

        [HttpPut]
        [Route("UpdatePassword")]
        public ActionResult UpdatePassword(long userId, string currentPassword, string newPassword)
        {
            try
            {
                this._IUserUIL.UpdatePassword(userId, currentPassword, newPassword);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }
        [HttpPut]
        [Route("ChangePassword")]
        public ActionResult ChangePassword(long userId, string recoveryCode, string newPassword)
        {
            try
            {
                this._IUserUIL.ChangePassword(userId, recoveryCode, newPassword);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }
    }
}
