using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;


namespace SkuciSeAPI.DAL
{
    public class AdvertDAL : BaseDAL<Advert>, IAdvertDAL
    {
        private readonly ApplicationDBContext _context;

        public AdvertDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
        }

        public Advert GetAdvert(long id, string name)
        {
            throw new System.NotImplementedException();
        }

        public Advert GetAdvertById(long id)
        {
            var result = from advert in _context.Adverts.Include(a => a.User)
                                            .Include(a => a.RealtyType)
                                            .Include(a => a.AdvertType)
                                            .Include(a => a.AdvertDetails)
											.Include(a => a.AdvertImages)
                                            .Include(a => a.Comments)
                                            .ThenInclude(c => c.User)
                         where advert.Id.Equals(id)
                         select advert;

            return result.ToList().FirstOrDefault();
        }

        public List<Advert> getAdvertsByFilter(Filters filter)
        {
            
            Console.WriteLine(filter.PriceMin.ToString() + " - " + filter.PriceMax.ToString());

            var result = from advert in _context.Adverts.Include(a => a.User)
                                            .Include(a => a.RealtyType)
                                            .Include(a => a.AdvertType)
                                            .Include(a => a.AdvertDetails)
                                            .Include(a => a.AdvertImages)
                                            .Include(a => a.Comments)
                                            .ThenInclude(c => c.User)

                                    //Cena i grad
                         where filter.City != "Izaberite grad" ? advert.AdvertDetails.City == filter.City:true && (advert.Price >= filter.PriceMin && advert.Price <= filter.PriceMax) &&
                                    
                                    //Tip oglasavanja
                                    ((filter.Rent == true && filter.Sell == true) ? 
                                    (advert.AdvertTypeId == 1 || advert.AdvertTypeId == 2) : 
                                    filter.Sell == true ? advert.AdvertTypeId == 2: filter.Rent == true ? 
                                    advert.AdvertTypeId == 1 :(advert.AdvertTypeId == 1 || advert.AdvertTypeId == 2)) &&

                                    //Tip nekretnine
                                    ((filter.House == true ? advert.RealtyTypeId == 1:false) ||
                                    (filter.Flat == true ? advert.RealtyTypeId == 2 : false) ||
                                    (filter.Garage == true ? advert.RealtyTypeId == 3 : false) ||
                                    (filter.ParkingLott == true ? advert.RealtyTypeId == 4 : false) ||
                                    (filter.BusinessArea == true ? advert.RealtyTypeId == 5 : false) ||
                                    (filter.House == false && filter.Flat == false && filter.Garage == false && filter.ParkingLott == false && filter.BusinessArea == false ? (advert.RealtyTypeId == 5 || advert.RealtyTypeId == 4 || advert.RealtyTypeId == 3 || advert.RealtyTypeId == 2 || advert.RealtyTypeId == 1) :false))&&
                                    
                                    //Dodatni sadrzaji
                                    (filter.Wifi == true ? advert.AdvertDetails.Wifi == true : true) &&
                                    (filter.Tv == true ? advert.AdvertDetails.Tv == true : true) &&
                                    (filter.Terrace == true ? advert.AdvertDetails.Terrace == true : true) &&
                                    (filter.AirCondition == true ? advert.AdvertDetails.AirCondition == true : true) &&
                                    (filter.Kitchen == true ? advert.AdvertDetails.Kitchen == true : true) &&
                                    (filter.Parking == true ? advert.AdvertDetails.Parking == true : true) &&
                                    (filter.Bathroom == true ? advert.AdvertDetails.Bathroom == true : true) &&
                                    (filter.Alarm == true ? advert.AdvertDetails.Alarm == true : true) &&
                                    (filter.Pool == true ? advert.AdvertDetails.Pool == true : true) &&
                                    (filter.Barbecue == true ? advert.AdvertDetails.Barbecue == true : true) &&
                                    (filter.FirePlace == true ? advert.AdvertDetails.FirePlace == true : true) &&
                                    (filter.Gym == true ? advert.AdvertDetails.Gym == true : true)
                                    
                         
                         select advert;

            return result.ToList();
        }

        public List<Advert> GetAdvertsByName(string searchInput)
        {
            var result = from advert in _context.Adverts.Include(a => a.User)
                                            .Include(a => a.RealtyType)
                                            .Include(a => a.AdvertType)
                                            .Include(a => a.AdvertDetails)
                                            .Include(a => a.AdvertImages)
                         where advert.Name.ToLower().Contains(searchInput.ToLower())
                         select advert;

            return result.ToList();
        }

        public List<Advert> getAdvertsByOwnerId(long ownerId)
        {
            var result = from advert in _context.Adverts.Include(a => a.User)
                                            .Include(a => a.RealtyType)
                                            .Include(a => a.AdvertType)
                                            .Include(a => a.AdvertDetails)
                                            .Include(a => a.AdvertImages)
                                            .Include(a => a.Comments)
                                            .ThenInclude(c => c.User)
                         where advert.UserId == ownerId
                         select advert;

            return result.ToList();
        }

        public List<Advert> GetAdvertsByRealtyType(long realtyId)
        {
            var result = from advert in _context.Adverts.Include(a => a.User)
                                            .Include(a => a.RealtyType)
                                            .Include(a => a.AdvertType)
                                            .Include(a => a.AdvertDetails)
                                            .Include(a => a.AdvertImages)
                         where advert.RealtyTypeId == realtyId
                         select advert;

            return result.ToList();
        }

        public List<Advert> GetAdvertsForRent()
        {
            var result = from advert in _context.Adverts.Include(a => a.User).Include(a => a.AdvertType).Include(a => a.AdvertDetails).Include(a => a.AdvertImages)
                         join advertType in _context.AdvertTypes on advert.AdvertTypeId equals advertType.Id
                         where advertType.Id.Equals(1) || advertType.AdvertTypeName.Equals("Izdavanje")
                         select advert;

            return result.OrderByDescending(m => m.Id).ToList();
        }

        public List<Advert> GetAdvertsForSale()
        {
            var result = from advert in _context.Adverts.Include(a => a.User).Include(a => a.AdvertType).Include(a => a.AdvertDetails).Include(a => a.AdvertImages)
                         join advertType in _context.AdvertTypes on advert.AdvertTypeId equals advertType.Id
                         where advertType.Id.Equals(2) || advertType.AdvertTypeName.Equals("Prodaja")
                         select advert;

            return result.OrderByDescending(m => m.Id).ToList();
        }

        public void UpdateAdvert(Advert advert)
        {
            Advert result = (from a in _context.Adverts
                             where a.Id == advert.Id
                             select a).SingleOrDefault();
            
            AdvertDetails resultDetails = (from ad in _context.AdvertDetails
                             where ad.Id == result.AdvertDetailsId
                             select ad).SingleOrDefault();

            if (resultDetails == null || result == null)
                throw new Exception();

            if(advert.Price != 0)
                result.Price = advert.Price;
            if (advert.Name != null)
                result.Name = advert.Name;
            if (advert.AdvertDetails.Township != null)
                resultDetails.Township = advert.AdvertDetails.Township;
            if (advert.AdvertDetails.Description != null)
                resultDetails.Description = advert.AdvertDetails.Description;
            if (advert.AdvertDetails.HouseOrder != null)
                resultDetails.HouseOrder = advert.AdvertDetails.HouseOrder;
            if (advert.AdvertDetails.City != null)
                resultDetails.City = advert.AdvertDetails.City;
            _context.SaveChanges();
        }
    }
}
