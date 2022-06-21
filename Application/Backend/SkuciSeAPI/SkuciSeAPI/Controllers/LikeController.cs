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
    public class LikeController : ControllerBase
    {
        private readonly ILikeUIL _ILikeUIL;
        private IConfiguration _configuration;

        public LikeController(ILikeUIL _ILikeUIL, IConfiguration configuration)
        {
            this._ILikeUIL = _ILikeUIL;
            _configuration = configuration;
        }


        [HttpGet]
        [Route("GetLikes")]
        public ActionResult GetLikes()
        {
            List<Like> likes = null;
            try
            {
                likes = _ILikeUIL.GetAllLikes();
            }
            catch (Exception e)
            {
                return BadRequest();
            }

            return Ok(likes);
        }

        [HttpGet]
        [Route("[action]/{userId}/{advertId}")]
        public ActionResult CheckLike(long userId, long advertid)
        {
            
            try
            {
                return  Ok(_ILikeUIL.CheckLike(userId,  advertid));
                
            }
            catch (Exception e)
            {
                return BadRequest();
            }

        }

        [HttpPost]
        [Route("[action]/{userId}/{advertId}")]
        public ActionResult Like(long userId, long advertid)
        {

            try
            {
                this._ILikeUIL.Like(userId, advertid);
                return Ok(true);
            }
            catch (Exception e)
            {
                return BadRequest();
            }

        }

        [HttpGet]
        [Route("[action]/{id}")]
        public ActionResult GetMyLikes(long id)
        {
            List<Advert> likedAdverts = null;
            try
            {
                likedAdverts = _ILikeUIL.GetAllMyLikes(id);
            }
            catch (Exception e)
            {
                return BadRequest();
            }

            return Ok(likedAdverts);
        }

        [HttpPost]
        [Route("CreateLike")]
        public ActionResult Create([FromBody] Like like)
        {
            try
            {
                this._ILikeUIL.CreateLike(like);

                return Ok(true);
            }
            catch (Exception e)
            {
                return Ok(false);
            }
        }



        [HttpDelete]
        [Route("{id}")]
        public ActionResult Delete(long id)
        {
            try
            {
                this._ILikeUIL.DeleteLike(id);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }


    }
}
