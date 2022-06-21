using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL.Interfaces
{
    public interface IAdvertUIL
    {
        public List<Advert> GetAllAdverts();
        public long CreateAdvert(Advert advert);
        public void DeleteAdvert(long id);
        public Advert GetById(long id);
        public Advert GetAdvertById(long id);
        public void UpdateAdvert(Advert advert);
        public List<Advert> GetAdvertsForRent();
        public List<Advert> GetAdvertsForSale();
        List<Advert> GetAdvertsByName(string searchInput);
        List<Advert> GetAdvertsByRealtyType(long realtyId);
        List<Advert> getAdvertsByFilter(Filters filter);
        List<Advert> getAdvertsByOwnerId(long ownerId);
    }
}
