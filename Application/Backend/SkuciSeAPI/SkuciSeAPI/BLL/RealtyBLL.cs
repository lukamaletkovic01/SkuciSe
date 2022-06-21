using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class RealtyBLL : IRealtyBLL
    {
        private readonly IRealtyDAL _IRealtyDAL;
        public RealtyBLL(IRealtyDAL _IRealtyDAL)
        {
            this._IRealtyDAL = _IRealtyDAL;
        }
        public void CreateRealty(RealtyType realty)
        {
            this._IRealtyDAL.Create(realty);
        }

        public void DeleteRealty(long id)
        {
            RealtyType realty = this._IRealtyDAL.GetById(id);
            if (realty == null)
            {
                throw new Exception();
            }

            this._IRealtyDAL.Delete(realty);
        }

        public List<RealtyType> GetAllRealties()
        {
            return _IRealtyDAL.GetAll();
        }

        public RealtyType GetById(long id)
        {
            return this._IRealtyDAL.GetById(id);
        }

        public RealtyType GetRealty(long id)
        {
            throw new NotImplementedException();
        }

        public void UpdateRealty(RealtyType realty)
        {
            RealtyType newRealty = this._IRealtyDAL.GetById(realty.Id);
            if (newRealty == null)
            {
                throw new Exception();
            }

            newRealty.RealtyTypeName = realty.RealtyTypeName;

            this._IRealtyDAL.Update(newRealty);
        }
    }
}
