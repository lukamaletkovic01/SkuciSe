using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using System.Linq;

namespace SkuciSeAPI.DAL
{
    public class AdvertDetailsDAL : BaseDAL<AdvertDetails>, IAdvertDetailsDAL
    {
        private readonly ApplicationDBContext _context;
        private DbSet<AdvertDetails> advertDetails = null;

        public AdvertDetailsDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
            this.advertDetails = _context.Set<AdvertDetails>();
        }

        public Advert GetAdvert(long id, string name)
        {
            throw new System.NotImplementedException();
        }

        public long GetLastId()
        {          
            var result = (from r in _context.AdvertDetails
                          orderby r.Id select r.Id).Max();
            return result;
        }

        public AdvertDetails GetAdvertDetailsById(long id)
        {
            var result = from advert in _context.AdvertDetails.Include(a => a.Township)
                                            .Include(a => a.City)
                                            .Include(a => a.Description)
                                            .Include(a => a.HouseOrder)
                                            
                         where advert.Id.Equals(id)
                         select advert;

            return result.ToList().FirstOrDefault();
        }

        
    }
}
