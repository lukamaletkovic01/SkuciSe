using LazZiya.ImageResize;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class ImagesController : ControllerBase
    {
        public static IWebHostEnvironment _environment;
        private readonly IUserUIL _IUserUIL;
        private readonly IAdvertImageUIL _IAdvertImageUIL;

        public ImagesController(IWebHostEnvironment environment, IUserUIL _IUserUIL, IAdvertImageUIL _IAdvertImageUIL)
        {
            _environment = environment;
            this._IUserUIL = _IUserUIL;
            this._IAdvertImageUIL = _IAdvertImageUIL;
        }


        public class FileUpload
        {
            public IFormFile files { get; set; }

        }

        [HttpPost]
        public async Task<IActionResult> PostProfileImage([FromForm] FileUpload objFile,[FromForm] int userID)
        {
           
            try
            {
                if (objFile.files.Length > 0)
                {
                    if (!Directory.Exists(_environment.WebRootPath + "\\Upload\\"))
                    {
                        Directory.CreateDirectory(_environment.WebRootPath + "\\Upload\\");
                    }
                   
                    User user = _IUserUIL.GetById(userID);
                    string path = _environment.WebRootPath + "\\Upload\\" + user.Id + "_" + objFile.files.FileName;
                   
                    if (user.ProfileImage != "default.jpg")
                    {
                        System.IO.File.Delete(_environment.WebRootPath + "\\Upload\\" + user.ProfileImage);
                    }

                    ImageResizeAndSave(objFile.files, path);
                    user.ProfileImage = user.Id + "_" + objFile.files.FileName;
                    _IUserUIL.UpdateUser(user);

                    return Ok(true);
                    
                }
                else
                {
                    return BadRequest(false);
                }
            }
            catch (Exception e)
            {
                return BadRequest(false);
            }
        }

        private void ImageResizeAndSave(IFormFile files, string path)
        {
            int width = 500;
            int height = 500;

            Image image = Image.FromStream(files.OpenReadStream(), true, true);
            var newImage = new Bitmap(width, height);

            using(var a = Graphics.FromImage(newImage))
            {

                a.DrawImage(image, 0, 0, width, height);
                a.Dispose();

                newImage.Save(path);
            }
        }


        [HttpGet("UserImages/{fileName}")]
        public async Task<IActionResult> Get([FromRoute] string fileName)
        {

            string path = _environment.WebRootPath + "\\Upload\\";
            var filePath = path + fileName;
            if (System.IO.File.Exists(filePath))
            {
                byte[] b = System.IO.File.ReadAllBytes(filePath);
                return File(b, "image/png");
            }
            return BadRequest();


        }

        [HttpGet("AdvertImages/{fileName}")]
        public async Task<IActionResult> GetAdvertImages([FromRoute] string fileName)
        {

            string path = _environment.WebRootPath + "\\AdvertImages\\";
            var filePath = path + fileName;
            if (System.IO.File.Exists(filePath))
            {
                byte[] b = System.IO.File.ReadAllBytes(filePath);
                return File(b, "image/png");
            }
            return BadRequest();


        }


    }
}
