using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using SkuciSeAPI.Common;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
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
    public class AdvertController : ControllerBase
    {
        private readonly IAdvertUIL _IAdvertUIL;
        private readonly IAdvertImageUIL _IAdvertImageUIL;
        private readonly ICommentUIL _ICommentUIL;
        private readonly IAdvertDetailsUIL _IAdvertDetailsUIL;

        public AdvertController(IAdvertUIL _IAdvertUIL, IAdvertImageUIL _IAdvertImageUIL, ICommentUIL _ICommentUIL, IAdvertDetailsUIL _IAdvertDetailsUIL)
        {
            this._IAdvertUIL = _IAdvertUIL;
            this._IAdvertImageUIL = _IAdvertImageUIL;
            this._ICommentUIL = _ICommentUIL;
            this._IAdvertDetailsUIL = _IAdvertDetailsUIL;
        }

        [HttpGet]
        [Route("GetAdverts")]
        public ActionResult GetAdverts()
        {
            List<Advert> adverts = null;
            try
            {
                adverts = _IAdvertUIL.GetAllAdverts();
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(adverts);
        }

        [HttpGet]
        [Route("[action]/{advertId}")]
        public ActionResult<Advert> GetAdvertById(long advertId)
        {
            try
            {
                Advert advert = _IAdvertUIL.GetAdvertById(advertId);

                return Ok(advert);
            }
            catch (Exception e)
            {
                return NotFound();
            }
        }
        public class FileUpload
        {
            public List<IFormFile> files { get; set; }

        }
        [HttpPost]
        [Route("CreateAdvert")]
        public ActionResult Create([FromForm] FileUpload objFile, [FromForm] string advertJson)
        {
            Advert advert = Newtonsoft.Json.JsonConvert.DeserializeObject<Advert>(advertJson);
            advert.AdvertDetailsId = _IAdvertDetailsUIL.GetLastId() + 1;
            try
            {
                long advertId = _IAdvertUIL.CreateAdvert(advert);
                this._IAdvertImageUIL.AddImages(objFile.files, advert.Id);

                return Ok(advertId);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return BadRequest(false);
            }
        }

        [HttpDelete]
        [Route("{id}")]
        public ActionResult Delete(long id)
        {
            try
            {
                this._IAdvertUIL.DeleteAdvert(id);
            }
            catch (Exception e)
            {
                return NotFound();
            }
            return Ok();
        }

        [HttpPut]
        [Route("UpdateAdvert")]
        public ActionResult Update([FromBody] Advert advert)
        {
            try
            {
                this._IAdvertUIL.UpdateAdvert(advert);

                return Ok(true);
            }
            catch (Exception e)
            {
                return BadRequest(false);
            }
        }

        [HttpPost]
        [Route("SearchAdvertsWithFilters")]
        public IActionResult getAdvertsByFilter([FromBody] Filters filter)
        {
            List<Advert> adverts = null;

            try
            {
                adverts = _IAdvertUIL.getAdvertsByFilter(filter);

            }
            catch (Exception e)
            {
                return BadRequest(new { message = "Exception." });
            }

            return Ok(adverts);
        }

        [HttpGet]
        [Route("[action]")]
        public IActionResult GetAdvertsForRent()
        {
            List<Advert> adverts = null;

            try
            {
                adverts = _IAdvertUIL.GetAdvertsForRent();
            }
            catch (Exception e)
            {
                return BadRequest(new { message = "Exception." });
            }

            return Ok(adverts);
        }

        [HttpGet]
        [Route("[action]/{searchInput}")]
        public IActionResult GetAdvertsByName(String searchInput)
        {
            List<Advert> adverts = null;

            try
            {

                adverts = _IAdvertUIL.GetAdvertsByName(searchInput);

            }
            catch (Exception e)
            {
                return BadRequest(new { message = "Exception." });
            }

            return Ok(adverts);
        }

        [HttpGet]
        [Route("[action]/{realtyId}")]
        public IActionResult GetAdvertsByRealtyType(long realtyId)
        {
            List<Advert> adverts = null;

            try
            {

                adverts = _IAdvertUIL.GetAdvertsByRealtyType(realtyId);

            }
            catch (Exception e)
            {
                return BadRequest(new { message = "Exception." });
            }

            return Ok(adverts);
        }

        [HttpGet]
        [Route("[action]/{ownerId}")]
        public IActionResult getAdvertsByOwnerId(long ownerId)
        {
            List<Advert> adverts = null;

            try
            {

                adverts = _IAdvertUIL.getAdvertsByOwnerId(ownerId);

            }
            catch (Exception e)
            {
                return BadRequest(new { message = "Exception." });
            }

            return Ok(adverts);
        }

        [HttpGet]
        [Route("[action]")]
        public IActionResult GetAdvertsForSale()
        {
            List<Advert> adverts = null;

            try
            {
                adverts = _IAdvertUIL.GetAdvertsForSale();
            }
            catch (Exception e)
            {
                return BadRequest(new { message = "Exception." });
            }

            return Ok(adverts);
        }

        [HttpPost]
        [Route("[action]")]
        public IActionResult LeaveComment(Comment comment)
        {
            try
            {
                _ICommentUIL.LeaveComment(comment);

                return Ok();
            }
            catch (Exception e)
            {
                return BadRequest(new { message = "Unable to leave comment." });
            }
        }

        [HttpDelete]
        [Route("[action]/{commentId}")]
        public IActionResult RemoveComment(long commentId)
        {
            try
            {
                _ICommentUIL.RemoveComment(commentId);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpGet]
        [Route("[action]/{commentId}")]
        public ActionResult<Comment> GetComment(long commentId)
        {
            try
            {
                Comment comment = _ICommentUIL.GetComment(commentId);

                return Ok(comment);
            }
            catch (Exception e)
            {
                return NotFound();
            }
        }

        [HttpGet]
        [Route("[action]/{advertId}")]
        public ActionResult GetComments(long advertId)
        {
            List<Comment> comments = null;
            try
            {
                comments = _ICommentUIL.GetComments(advertId);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(comments);
        }

        [HttpGet]
        [Route("[action]/{ownerId}")]
        public ActionResult GetReceivedComments(long ownerId)
        {
            List<Comment> comments = null;
            try
            {
                comments = _ICommentUIL.GetReceivedComments(ownerId);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(comments);
        }

        [HttpGet]
        [Route("[action]/{userId}")]
        public ActionResult GetSentComments(long userId)
        {
            List<Comment> comments = null;
            try
            {
                comments = _ICommentUIL.GetSentComments(userId);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(comments);
        }

    }
}
