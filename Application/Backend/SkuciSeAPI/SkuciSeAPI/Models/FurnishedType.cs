using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class FurnishedType
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public long Id { get; set; }

        public string FurnishedTypeName { get; set; }

        // Foreign Keys
        public List<AdvertDetails> AdvertDetails { get; set; }
    }
}
