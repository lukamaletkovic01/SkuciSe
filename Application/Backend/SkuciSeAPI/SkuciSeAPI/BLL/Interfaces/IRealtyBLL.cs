using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.Interfaces
{
    public interface IRealtyBLL
    {
        public List<RealtyType> GetAllRealties();
        public void CreateRealty(RealtyType realty);
        public void DeleteRealty(long id);
        public RealtyType GetById(long id);
        public void UpdateRealty(RealtyType realty);
        public RealtyType GetRealty(long id);
    }
}
