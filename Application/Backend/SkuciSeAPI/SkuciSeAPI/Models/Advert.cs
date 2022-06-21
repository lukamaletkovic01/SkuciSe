using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class Advert
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }
        [Required]
        public string Name { get; set; }
        [Required]
        public long Price { get; set; }
        public int RatingsCount { get; set; }
        public double AdvertAverageRating { get; set; }

        // Foreign Keys
        public long UserId { get; set; }
        public User User { get; set; }

        public long AdvertDetailsId { get; set; }
        public AdvertDetails AdvertDetails { get; set; }

        public long RealtyTypeId { get; set; }
        public RealtyType RealtyType { get; set; }

        public long AdvertTypeId { get; set; }
        public AdvertType AdvertType { get; set; }

        public List<Like> Likes { get; set; }

        public List<AdvertImage> AdvertImages { get; set; }

        public List<Comment> Comments { get; set; }
    }
}
