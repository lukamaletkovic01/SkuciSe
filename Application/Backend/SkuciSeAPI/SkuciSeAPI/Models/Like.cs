using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;


namespace SkuciSeAPI.Models
{
    public class Like 
    {
       [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long LikeId { get; set; }
        // Foreign keys
        [Required]
        public long UserId { get; set; }
        
        public User? User { get; set; }

        [Required]
        public long AdvertId { get; set; }
        
        public Advert? Advert { get; set; }
    }
}
