using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL
{
    public class CommentDAL : BaseDAL<Comment>, ICommentDAL
    {
        private readonly ApplicationDBContext _context;
        private DbSet<Comment> comments = null;

        public CommentDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
            this.comments = _context.Set<Comment>();
        }

        public List<Comment> GetComments(long advertId)
        {
            return comments.Where(c => c.AdvertId.Equals(advertId))
                           .Include(c => c.User)
                           .ToList();
        }

        public List<Comment> GetReceivedComments(long ownerId)
        {
            return comments.Include(a => a.User)
                           .Include(a => a.Advert)
                           .Where(c => c.Advert.UserId.Equals(ownerId)).ToList();
        }

        public List<Comment> GetSentComments(long userId)
        {
            return comments.Include(a => a.User)
                           .Include(a => a.Advert)
                           .Where(c => c.UserId.Equals(userId)).ToList();
        }

        public bool IsCommented(long advertId, long userId)
        {
            return comments.Any(c => c.AdvertId.Equals(advertId) && c.UserId.Equals(userId));
        }
    }
}
