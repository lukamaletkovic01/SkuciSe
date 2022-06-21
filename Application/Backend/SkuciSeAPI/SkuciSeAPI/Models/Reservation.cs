using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class Reservation
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }
        public DateTime DateOfReservation { get; set; }
        public Status Status { get; set; }

        // Foreign keys
        public long AdvertId { get; set; }
        public Advert Advert { get; set; }

        public long OwnerId { get; set; }

        public long UserId { get; set; }
        public User User { get; set; }

        public long? CommentId { get; set; }
        public Comment Comment { get; set; }
    }
}
