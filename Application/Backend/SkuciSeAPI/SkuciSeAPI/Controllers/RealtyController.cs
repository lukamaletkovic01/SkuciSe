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
    public class RealtyController : Controller
    {
        private readonly IRealtyUIL _IRealtyUIL;
        private IConfiguration _configuration;

        public RealtyController(IRealtyUIL _IRealtyUIL, IConfiguration configuration)
        {
            this._IRealtyUIL = _IRealtyUIL;
            _configuration = configuration;
        }


        [HttpGet]
        [Route("GetRealties")]
        public ActionResult GetRealties()
        {
            List<RealtyType> realties = null;
            try
            {
                realties = _IRealtyUIL.GetAllRealties();
            }
            catch (Exception e)
            {
                return BadRequest();
            }

            return Ok(realties);
        }

        [HttpPost]
        [Route("CreateRealty")]
        public ActionResult Create([FromBody] RealtyType realty)
        {
            try
            {
                this._IRealtyUIL.CreateRealty(realty);

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
                this._IRealtyUIL.DeleteRealty(id);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpPut]
        [Route("UpdateRealty")]
        public ActionResult Update([FromBody] RealtyType realty)
        {
            try
            {
                this._IRealtyUIL.UpdateRealty(realty);
            }
            catch (Exception e)
            {
                return NotFound();
            }

            return Ok();
        }
    }
}
