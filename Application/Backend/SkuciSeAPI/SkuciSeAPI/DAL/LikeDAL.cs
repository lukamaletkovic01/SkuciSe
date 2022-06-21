using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SkuciSeAPI.DAL
{
    public class LikeDAL : BaseDAL<Like>, ILikeDAL
    {
        private readonly ApplicationDBContext _context;
        private DbSet<Like> likes = null;
        private DbSet<Advert> adverts = null;
        private readonly ILikeDAL _ILikeDAL;
        public LikeDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
            this.likes = _context.Set<Like>();
            this.adverts = _context.Set<Advert>();
        }

        public Like Like(long userId, long advertId)
        {
            Like query = likes.Where(x => x.UserId.Equals(userId) && x.AdvertId.Equals(advertId)).FirstOrDefault();

            return query;
        }

        public Like GetLike(long id)
        {
            return likes.Where(x => x.LikeId.Equals(id)).FirstOrDefault();
        }

        List<Advert> ILikeDAL.GetAllMyLikes(long id)
        {
            var myLikes = from x in likes.Where(x => x.UserId.Equals(id))
                          select x.AdvertId ;
            myLikes.ToList();
            var query = from x in adverts.Where(x => myLikes.Contains(x.Id))
                        select new Advert() { Id = x.Id, Name = x.Name, Price = x.Price, RatingsCount = x.RatingsCount, 
                            AdvertAverageRating = x.AdvertAverageRating, UserId = x.UserId, AdvertDetailsId = x.AdvertDetailsId,AdvertDetails = x.AdvertDetails,AdvertImages = x.AdvertImages,
                            RealtyTypeId = x.RealtyTypeId, AdvertTypeId = x.AdvertTypeId };

            //.Where(y => y.UserId.Equals(id))

            //select * from Adverts a where a.Id in (SELECT l.AdvertId from Likes l where l.UserId = 7)


            return query.ToList();
        }
        public long GetLastId()
        {
            var result = (from r in _context.Likes
                          orderby r.LikeId
                          select r.LikeId).Max();
            return result;
        }
    }
}
