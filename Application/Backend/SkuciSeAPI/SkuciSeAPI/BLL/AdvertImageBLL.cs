using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class AdvertImageBLL : IAdvertImageBLL
    {

        private readonly IAdvertImageDAL _IAdvertImageDAL;
        public static IWebHostEnvironment _environment;

        public AdvertImageBLL(IAdvertImageDAL iAdvertImageDAL, IWebHostEnvironment environment)
        {
            _IAdvertImageDAL = iAdvertImageDAL;
            _environment = environment;
        }


        public void AddImages(List<IFormFile> images, long AdvertID)
        {

            string path = _environment.WebRootPath + "\\AdvertImages\\";

            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);
            }

            for (int i = 0; i < images.Count; i++)
            {
                string file_path = path + AdvertID + "_" + images[i].FileName;
                if (!File.Exists(file_path))
                {
                    File.Delete(file_path);
                }
                
                ImageSave(images[i], file_path);


                AdvertImage image = new AdvertImage();
                image.AdvertId = AdvertID;
                image.Path = AdvertID + "_" + images[i].FileName;
                _IAdvertImageDAL.Create(image);
            }

        }
        private void ImageSave(IFormFile file, string path)
        {

            Image image = Image.FromStream(file.OpenReadStream(), true, true);
            var newImage = new Bitmap(image.Width, image.Height);

            using (var a = Graphics.FromImage(newImage))
            {

                a.DrawImage(image, 0, 0, image.Width, image.Height);
                a.Dispose();

                newImage.Save(path);
            }

        }

        public List<AdvertImage> GetAdvertImages(long advertID)
        {
            throw new NotImplementedException();
        }
    }
}
