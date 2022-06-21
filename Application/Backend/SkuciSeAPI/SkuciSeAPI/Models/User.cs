using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class User
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }
        [Required]
        public string Email { get; set; }
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        [Required]
        public string Password { get; set; }
        public string ProfileImage { get; set; }
        public string PhoneNumber { get; set; }

        public string City { get; set; }
        public string Address { get; set; }

        [Required]
        public byte Role { get; set; }
        public string FcmToken { get; set; }

        public double UserAverageRating { get; set; }
        public int RatingsCount { get; set; }

        // Foreign Keys
        public List<Advert> Adverts { get; set; }

        public List<Like> Likes { get; set; }

        public List<Comment> Comments { get; set; }
        public List<Reservation> Reservations { get; set; }
    }
}
