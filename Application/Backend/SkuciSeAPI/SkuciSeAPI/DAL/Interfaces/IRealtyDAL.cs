using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IRealtyDAL : IBaseDAL<RealtyType>
    {
        public RealtyType GetRealty(long id);
    }
}
