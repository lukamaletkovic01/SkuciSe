using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class AdvertImage
    {

        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public long Id { get; set; }

        public string Path { get; set; }

        // Foreign Keys
        [ForeignKey("Advert")]
        public long AdvertId { get; set; }
        public Advert Advert { get; set; }

    }
}
