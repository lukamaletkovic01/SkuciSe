using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class AdvertDetailsBLL : IAdvertDetailsBLL
    {
        private readonly IAdvertDetailsDAL _IAdvertDetailsDAL;
        public AdvertDetailsBLL(IAdvertDetailsDAL _IAdvertDetailsDAL)
        {
            this._IAdvertDetailsDAL = _IAdvertDetailsDAL;
        }
        public void CreateAdvertDetails(AdvertDetails adDetails)
        {
            this._IAdvertDetailsDAL.Create(adDetails);
        }

        public void DeleteAdvertDetails(long id)
        {
            AdvertDetails advertDetails = this._IAdvertDetailsDAL.GetById(id);
            if (advertDetails == null)
            {
                throw new Exception();
            }

            this._IAdvertDetailsDAL.Delete(advertDetails);
        }

        public AdvertDetails GetAdvertDetails(long id)
        {
            return this._IAdvertDetailsDAL.GetAdvertDetailsById(id);
        }

        public List<AdvertDetails> GetAllAdvertDetails()
        {
            return _IAdvertDetailsDAL.GetAll();
        }

        public AdvertDetails GetById(long id)
        {
            throw new NotImplementedException();
        }

        public long GetLastId()
        {
            return _IAdvertDetailsDAL.GetLastId();
        }

        public void UpdateAdvertDetails(AdvertDetails advertDetails)
        {
            AdvertDetails newAdvertDetails = this._IAdvertDetailsDAL.GetById(advertDetails.Id);
            if (newAdvertDetails == null)
            {
                throw new Exception();
            }

            newAdvertDetails.Date = advertDetails.Date;
            newAdvertDetails.Township = advertDetails.Township;
            newAdvertDetails.City = advertDetails.City;
            newAdvertDetails.SquaredArea = newAdvertDetails.SquaredArea;
            newAdvertDetails.BedroomNumber = newAdvertDetails.BedroomNumber;
            newAdvertDetails.Terrace = newAdvertDetails.Terrace;
            newAdvertDetails.FloorNumber = newAdvertDetails.FloorNumber;
            newAdvertDetails.Parking = newAdvertDetails.Parking;

            this._IAdvertDetailsDAL.Update(newAdvertDetails);
        }

       
    }
}
