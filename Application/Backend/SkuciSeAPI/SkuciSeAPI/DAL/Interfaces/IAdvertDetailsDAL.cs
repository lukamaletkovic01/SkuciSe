using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IAdvertDetailsDAL : IBaseDAL<AdvertDetails>
    {
        public Advert GetAdvert(long id, string name);
        public long GetLastId();
        public AdvertDetails GetAdvertDetailsById(long id);
    }
}
