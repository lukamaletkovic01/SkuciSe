using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class CommentBLL : ICommentBLL
    {
        private readonly ICommentDAL _ICommentDAL;
        private readonly IAdvertDAL _IAdvertDAL;
        private readonly IUserDAL _IUserDAL;

        public CommentBLL(ICommentDAL _ICommentDAL, IAdvertDAL _IAdvertDAL, IUserDAL _IUserDAL)
        {
            this._ICommentDAL = _ICommentDAL;
            this._IAdvertDAL = _IAdvertDAL;
            this._IUserDAL = _IUserDAL;
        }

        public Comment GetComment(long id)
        {
            return _ICommentDAL.GetById(id);
        }

        public List<Comment> GetComments(long advertId)
        {
            List<Comment> comments = _ICommentDAL.GetComments(advertId);

            return comments.OrderByDescending(c => c.DatePublished).ToList();
        }

        public List<Comment> GetReceivedComments(long ownerId)
        {
            return _ICommentDAL.GetReceivedComments(ownerId);
        }

        public List<Comment> GetSentComments(long userId)
        {
            return _ICommentDAL.GetSentComments(userId);
        }

        public long LeaveComment(Comment comment)
        {
            Advert advert = _IAdvertDAL.GetById(comment.AdvertId);

            if (advert != null && /*!advert.UserId.Equals(comment.UserId) &&*/ !_ICommentDAL.IsCommented(advert.Id, comment.UserId))
            {
                User user = _IUserDAL.GetById(advert.UserId);

                _ICommentDAL.Create(comment);

                if (advert.RatingsCount > 0)
                {
                    advert.RatingsCount++;
                    user.RatingsCount++;

                    advert.AdvertAverageRating = advert.AdvertAverageRating + (comment.Rating - advert.AdvertAverageRating) / advert.RatingsCount;
                    user.UserAverageRating = user.UserAverageRating + (comment.Rating - user.UserAverageRating) / user.RatingsCount;
                }
                else
                {
                    advert.RatingsCount++;
                    user.RatingsCount++;

                    advert.AdvertAverageRating = comment.Rating;

                    if (user.RatingsCount == 0)
                    {
                        user.UserAverageRating = comment.Rating;
                    }
                    else
                    {
                        user.UserAverageRating = user.UserAverageRating + (comment.Rating - user.UserAverageRating) / user.RatingsCount;
                    }
                }

                _IAdvertDAL.Update(advert);

                return comment.Id;
            }
            else
                throw new Exception();
        }

        // TODO: Update AverageUserRating i RatingsCount za korisnika
        public void RemoveComment(long id)
        {
            Comment comment = _ICommentDAL.GetById(id);

            if (comment != null)
            {
                Advert advert = _IAdvertDAL.GetById(comment.AdvertId);

                if (advert != null)
                {
                    if (advert.RatingsCount > 1)
                    {
                        advert.AdvertAverageRating = ((advert.AdvertAverageRating * advert.RatingsCount) - comment.Rating) / (advert.RatingsCount - 1);
                        advert.RatingsCount--;
                    }
                    else
                    {
                        advert.AdvertAverageRating = 0.0;
                        advert.RatingsCount--;
                    }

                    _IAdvertDAL.Update(advert);
                }

                _ICommentDAL.Delete(comment);
            }
        }
    }
}
