using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface ICommentDAL : IBaseDAL<Comment>
    {
        public List<Comment> GetComments(long advertId);
        public bool IsCommented(long advertId, long userId);
        List<Comment> GetReceivedComments(long ownerId);
        List<Comment> GetSentComments(long userId);
    }
}
