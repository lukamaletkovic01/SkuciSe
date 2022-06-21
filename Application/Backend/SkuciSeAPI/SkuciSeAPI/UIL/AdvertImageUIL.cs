using Microsoft.AspNetCore.Http;
using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class AdvertImageUIL : IAdvertImageUIL
    {

        private readonly IAdvertImageBLL _IAdvertImageBLL;

        public AdvertImageUIL(IAdvertImageBLL iAdvertImageBLL)
        {
            _IAdvertImageBLL = iAdvertImageBLL;
        }

        public void AddImages(List<IFormFile> images, long AdvertID)
        {
            _IAdvertImageBLL.AddImages(images, AdvertID);
        }

        public List<AdvertImage> GetAdvertImages(long advertID)
        {
            throw new NotImplementedException();
        }
    }
}
