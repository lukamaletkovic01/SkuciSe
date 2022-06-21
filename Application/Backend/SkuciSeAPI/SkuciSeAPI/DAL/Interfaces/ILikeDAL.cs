using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface ILikeDAL : IBaseDAL<Like>
    {
        public Like GetLike(long id);

        public List<Advert> GetAllMyLikes(long id);

        public Like Like(long userId, long advertId);
        public long GetLastId();
    }
}
