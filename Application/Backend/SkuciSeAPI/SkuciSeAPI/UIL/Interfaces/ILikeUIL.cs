using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL.Interfaces
{
    public interface ILikeUIL
    {
        public List<Like> GetAllLikes();
        public List<Advert> GetAllMyLikes(long id);

        public void Like(long userId, long advertId);

        public bool CheckLike(long userId, long advertId);
        public void CreateLike(Like like);
        public void DeleteLike(long id);
        public Like GetLike(long id);
    }
}

