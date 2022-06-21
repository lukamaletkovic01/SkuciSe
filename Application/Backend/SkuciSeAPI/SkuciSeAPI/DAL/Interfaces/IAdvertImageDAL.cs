using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IAdvertImageDAL : IBaseDAL<AdvertImage>
    {

        public List<AdvertImage> GetAdvertImages(long advertID);
    }
}
