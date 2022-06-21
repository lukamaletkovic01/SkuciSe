using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL
{
    public class AdvertImageDAL : BaseDAL<AdvertImage>, IAdvertImageDAL
    {

        private readonly ApplicationDBContext _context;

        public AdvertImageDAL(ApplicationDBContext context) : base(context)
        {
            _context = context;
        }

        public List<AdvertImage> GetAdvertImages(long advertID)
        {
            throw new NotImplementedException();
        }
    }
}
