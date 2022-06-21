using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class Comment
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }
        public string Content { get; set; }
        public double Rating { get; set; }
        public DateTime DatePublished { get; set; }

        // Foreign keys
        public long UserId { get; set; }
        public User User { get; set; }

        public long AdvertId { get; set; }
        public Advert Advert { get; set; }

        [ForeignKey("Reservation")]
        public long ReservationId { get; set; }
        public Reservation Reservation { get; set; }
    }
}
