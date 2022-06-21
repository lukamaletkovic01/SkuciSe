using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using System.Linq;

namespace SkuciSeAPI.DAL
{
    public class RealtyDAL : BaseDAL<RealtyType>, IRealtyDAL
    {
        private readonly ApplicationDBContext _context;
        private DbSet<RealtyType> realty = null;

        public RealtyDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
            this.realty = _context.Set<RealtyType>();
        }

        public RealtyType GetRealty(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}
