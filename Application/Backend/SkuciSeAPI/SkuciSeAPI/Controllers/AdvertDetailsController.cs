using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using SkuciSeAPI.Common;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Controllers
{

    [Route("[controller]")]
    [ApiController]
    public class AdvertDetailsController : ControllerBase
    {
        private readonly IAdvertDetailsUIL _IAdvertDetailsUIL;
        private IConfiguration _configuration;


        public AdvertDetailsController(IAdvertDetailsUIL _IAdvertDetailsUIL, IConfiguration configuration)
        {
            this._IAdvertDetailsUIL = _IAdvertDetailsUIL;
            _configuration = configuration;
        }

        [HttpGet]
        [Route("GetAdvertDetails")]
        public ActionResult GetAdvertDetails()
        {
            List<AdvertDetails> adverts = null;
            try
            {
                adverts = _IAdvertDetailsUIL.GetAllAdvertDetails();
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(adverts);
        }

        [HttpPost]
        [Route("CreateAdvertDetails")]
        public ActionResult Create([FromBody] AdvertDetails advert)
        {
            try
            {
                this._IAdvertDetailsUIL.CreateAdvertDetails(advert);

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
                this._IAdvertDetailsUIL.DeleteAdvertDetails(id);
            }
            catch (Exception e)
            {
                return NotFound();
            }
            return Ok();
        }

        [HttpPut]
        [Route("UpdateAdvertDetails")]
        public ActionResult Update([FromBody] AdvertDetails advert)
        {
            try
            {
                this._IAdvertDetailsUIL.UpdateAdvertDetails(advert);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpGet]
        [Route("[action]/{id}")]
        public ActionResult GetAdvertDetails(long id)
        {
            AdvertDetails adverts = null;
            try
            {
                adverts = _IAdvertDetailsUIL.GetAdvertDetails(id);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(adverts);
        }

    }
}
