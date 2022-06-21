using Microsoft.AspNetCore.Http;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.Interfaces
{
    public interface IAdvertImageBLL
    {

        public void AddImages(List<IFormFile> images, long AdvertID);
        public List<AdvertImage> GetAdvertImages(long advertID);
    }
}
