using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class LikeBLL : ILikeBLL
    {
        private readonly ILikeDAL _ILikeDAL;
        public LikeBLL(ILikeDAL _ILikeDAL)
        {
            this._ILikeDAL = _ILikeDAL;
        }

        public void Like(long userId, long advertId)
        {
            
            Like query = this._ILikeDAL.Like(userId, advertId);
            
            if (query == null)
            {
                
                Like like = new Like();
                long likeid;
                try
                {
                    likeid = this._ILikeDAL.GetLastId();
                }
                catch
                {
                    likeid = 0;
                }
                   
                like.LikeId = likeid  + 1;

                like.UserId = userId;
                like.AdvertId = advertId;

                this._ILikeDAL.Create(like);
                
            }
            else
            {
                
                Like like = this._ILikeDAL.GetLike(query.LikeId);
                if (like == null)
                 {
                     throw new Exception();
                 }

                 this._ILikeDAL.Delete(like);
            }
        }

        public void CreateLike(Like like)
        {
            this._ILikeDAL.Create(like);
        }

        public void DeleteLike(long id)
        {
            Like like = this._ILikeDAL.GetLike(id);
            
            if (like == null)
            {
                throw new Exception();
            }

            this._ILikeDAL.Delete(like);
        }

        public List<Like> GetAllLikes()
        {

            return _ILikeDAL.GetAll();
        }

        public List<Advert> GetAllMyLikes(long id)
        {

            return _ILikeDAL.GetAllMyLikes(id);
        }

        public Like GetLike(long id)
        {
            return this._ILikeDAL.GetLike(id);
        }

        public bool CheckLike(long userId, long advertId)
        {
            Like query = this._ILikeDAL.Like(userId, advertId);
            if (query == null)
            {
                return true;
            }
            else return false;
        }
    }
}
