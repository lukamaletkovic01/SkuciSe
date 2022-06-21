using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class LikeUIL : ILikeUIL
    {
        private readonly ILikeBLL _ILikeBLL;

        public LikeUIL(ILikeBLL _IRealtyBLL)
        {
            this._ILikeBLL = _IRealtyBLL;
        }

        public void Like(long userId, long advertId)
        {
            
            this._ILikeBLL.Like(userId, advertId);
            
        }

        public void CreateLike(Like like)
        {
            _ILikeBLL.CreateLike(like);
        }

        public void DeleteLike(long id)
        {
            this._ILikeBLL.DeleteLike(id);
        }

        public List<Like> GetAllLikes()
        {
            return _ILikeBLL.GetAllLikes();
        }

        public List<Advert> GetAllMyLikes(long id)
        {
            return _ILikeBLL.GetAllMyLikes(id);
        }

        public Like GetLike(long id)
        {
            return this._ILikeBLL.GetLike(id);
        }

        public bool CheckLike(long userId, long advertId)
        {
            return this._ILikeBLL.CheckLike(userId, advertId);
        }
    }
}
