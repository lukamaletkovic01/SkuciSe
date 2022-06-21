using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using SkuciSeAPI.UIL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class AdvertBLL : IAdvertBLL
    {
        private readonly IAdvertDAL _IAdvertDAL;
        private readonly IAdvertDetailsDAL _IAdvertDetailsDAL;
        public AdvertBLL(IAdvertDAL _IAdvertDAL, IAdvertDetailsDAL _IAdvertDetailsDAL)
        {
            this._IAdvertDAL = _IAdvertDAL;
            this._IAdvertDetailsDAL = _IAdvertDetailsDAL;
        }
        public long CreateAdvert(Advert advert)
        {
            return _IAdvertDAL.Create(advert).Id;
        }

        public void DeleteAdvert(long id)
        {
            Advert advert = this._IAdvertDAL.GetById(id);
            if (advert == null)
            {
                throw new Exception();
            }

            this._IAdvertDAL.Delete(advert);
        }

        public Advert GetAdvert(long id)
        {
            throw new NotImplementedException();
        }

        public List<Advert> GetAdvertsForRent()
        {
            return _IAdvertDAL.GetAdvertsForRent();
        }

        public List<Advert> GetAdvertsForSale()
        {
            return _IAdvertDAL.GetAdvertsForSale();
        }

        public List<Advert> GetAllAdverts()
        {
            return _IAdvertDAL.GetAll();
        }

        public Advert GetAdvertById(long id)
        {
            return this._IAdvertDAL.GetAdvertById(id);
        }

        public void UpdateAdvert(Advert advert)
        {
            
            this._IAdvertDAL.UpdateAdvert(advert);
            
        }

        public Advert GetById(long id)
        {
            return this._IAdvertDAL.GetById(id);
        }

        public List<Advert> GetAdvertsByName(string searchInput)
        {
            return _IAdvertDAL.GetAdvertsByName(searchInput);
        }

        public List<Advert> GetAdvertsByRealtyType(long realtyId)
        {
            return this._IAdvertDAL.GetAdvertsByRealtyType(realtyId);
        }

        public List<Advert> getAdvertsByFilter(Filters filter)
        {
            return this._IAdvertDAL.getAdvertsByFilter(filter);
        }

        public List<Advert> getAdvertsByOwnerId(long ownerId)
        {
            return this._IAdvertDAL.getAdvertsByOwnerId(ownerId);
        }
    }
}
