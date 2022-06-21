using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class AdvertUIL : IAdvertUIL
    {
        private readonly IAdvertBLL _IAdvertBLL;

        public AdvertUIL(IAdvertBLL _IAdvertBLL)
        {
            this._IAdvertBLL = _IAdvertBLL;
        }
        public long CreateAdvert(Advert advert)
        {
            return _IAdvertBLL.CreateAdvert(advert);
        }

        public void DeleteAdvert(long id)
        {
            this._IAdvertBLL.DeleteAdvert(id);
        }

        public Advert GetAdvertById(long id)
        {
            return this._IAdvertBLL.GetAdvertById(id);
        }

        public List<Advert> getAdvertsByFilter(Filters filter)
        {
            return this._IAdvertBLL.getAdvertsByFilter(filter);
        }

        public List<Advert> GetAdvertsByName(string searchInput)
        {
            return this._IAdvertBLL.GetAdvertsByName(searchInput);
        }

        public List<Advert> getAdvertsByOwnerId(long ownerId)
        {
            return this._IAdvertBLL.getAdvertsByOwnerId(ownerId);
        }

        public List<Advert> GetAdvertsByRealtyType(long realtyId)
        {
            return this._IAdvertBLL.GetAdvertsByRealtyType(realtyId);
        }

        public List<Advert> GetAdvertsForRent()
        {
            return _IAdvertBLL.GetAdvertsForRent();
        }

        public List<Advert> GetAdvertsForSale()
        {
            return _IAdvertBLL.GetAdvertsForSale();
        }

        public List<Advert> GetAllAdverts()
        {
            return _IAdvertBLL.GetAllAdverts();
        }

        public Advert GetById(long id)
        {
            return this._IAdvertBLL.GetById(id);
        }

        public void UpdateAdvert(Advert advert)
        {
            this._IAdvertBLL.UpdateAdvert(advert);
        }
    }
}
