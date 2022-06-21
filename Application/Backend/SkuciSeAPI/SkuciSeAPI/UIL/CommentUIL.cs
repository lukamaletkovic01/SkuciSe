using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class CommentUIL : ICommentUIL
    {
        private readonly ICommentBLL _ICommentBLL;

        public CommentUIL(ICommentBLL _ICommentBLL)
        {
            this._ICommentBLL = _ICommentBLL;
        }

        public Comment GetComment(long id)
        {
            return _ICommentBLL.GetComment(id);
        }

        public List<Comment> GetComments(long advertId)
        {
            return _ICommentBLL.GetComments(advertId);
        }

        public List<Comment> GetReceivedComments(long ownerId)
        {
            return _ICommentBLL.GetReceivedComments(ownerId);
        }

        public List<Comment> GetSentComments(long userId)
        {
            return _ICommentBLL.GetSentComments(userId);
        }

        public long LeaveComment(Comment comment)
        {
            return _ICommentBLL.LeaveComment(comment);
        }

        public void RemoveComment(long id)
        {
            _ICommentBLL.RemoveComment(id);
        }
    }
}
