using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class RealtyUIL : IRealtyUIL
    {
        private readonly IRealtyBLL _IRealtyBLL;

        public RealtyUIL(IRealtyBLL _IRealtyBLL)
        {
            this._IRealtyBLL = _IRealtyBLL;
        }
        public void CreateRealty(RealtyType realty)
        {
            _IRealtyBLL.CreateRealty(realty);
        }

        public void DeleteRealty(long id)
        {
            this._IRealtyBLL.DeleteRealty(id);
        }

        public List<RealtyType> GetAllRealties()
        {
            return _IRealtyBLL.GetAllRealties();
        }

        public RealtyType GetById(long id)
        {
            return this._IRealtyBLL.GetById(id);
        }

        public RealtyType GetRealty(long id)
        {
            throw new NotImplementedException();
        }

        public void UpdateRealty(RealtyType realty)
        {
            this._IRealtyBLL.UpdateRealty(realty);
        }
    }
}
