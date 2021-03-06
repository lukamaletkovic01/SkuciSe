using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL.Interfaces
{
    public interface ICommentUIL
    {
        public long LeaveComment(Comment comment);
        public void RemoveComment(long id);
        public Comment GetComment(long id);
        public List<Comment> GetComments(long advertId);
        List<Comment> GetReceivedComments(long ownerId);
        List<Comment> GetSentComments(long userId);
    }
}
