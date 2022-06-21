using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;


namespace SkuciSeAPI.UIL
{
    public interface IAdvertDetailsUIL
    {
        public List<AdvertDetails> GetAllAdvertDetails();
        public void CreateAdvertDetails(AdvertDetails adDetails);
        public void DeleteAdvertDetails(long id);
        public AdvertDetails GetById(long id);
        public void UpdateAdvertDetails(AdvertDetails adDetails);
        public AdvertDetails GetAdvertDetails(long id);
        public long GetLastId();

        
    }
}
