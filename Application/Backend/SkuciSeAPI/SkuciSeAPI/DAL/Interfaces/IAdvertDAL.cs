using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IAdvertDAL : IBaseDAL<Advert>
    {
        public Advert GetAdvert(long id, string name);
        public Advert GetAdvertById(long id);
        public List<Advert> GetAdvertsForRent();
        public List<Advert> GetAdvertsForSale();
        List<Advert> GetAdvertsByName(string searchInput);
        List<Advert> GetAdvertsByRealtyType(long realtyId);
        List<Advert> getAdvertsByFilter(Filters filter);
        List<Advert> getAdvertsByOwnerId(long ownerId);

        public void UpdateAdvert(Advert advert);
    }
}
