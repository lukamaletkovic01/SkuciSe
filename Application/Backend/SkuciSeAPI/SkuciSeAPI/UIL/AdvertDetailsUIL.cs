using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class AdvertDetailsUIL : IAdvertDetailsUIL
    {
        private readonly IAdvertDetailsBLL _IAdvertDetailsBLL;

        public AdvertDetailsUIL(IAdvertDetailsBLL _IAdvertDetailsBLL)
        {
            this._IAdvertDetailsBLL = _IAdvertDetailsBLL;
        }
        public void CreateAdvertDetails(AdvertDetails adDetails)
        {
            _IAdvertDetailsBLL.CreateAdvertDetails(adDetails);
        }

        public void DeleteAdvertDetails(long id)
        {
            this._IAdvertDetailsBLL.DeleteAdvertDetails(id);
        }

        public AdvertDetails GetAdvertDetails(long id)
        {
            return _IAdvertDetailsBLL.GetAdvertDetails(id);
        }

        public List<AdvertDetails> GetAllAdvertDetails()
        {
            return _IAdvertDetailsBLL.GetAllAdvertDetails();
        }

        public AdvertDetails GetById(long id)
        {
            return this._IAdvertDetailsBLL.GetById(id);
        }

        public long GetLastId()
        {
            return _IAdvertDetailsBLL.GetLastId();
        }

        public void UpdateAdvertDetails(AdvertDetails adDetails)
        {
            this._IAdvertDetailsBLL.UpdateAdvertDetails(adDetails);
        }
    }
}
