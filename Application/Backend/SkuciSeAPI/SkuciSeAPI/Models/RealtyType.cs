using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class RealtyType
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public long Id { get; set; }

        public string RealtyTypeName { get; set; }

        // Foreign Keys
        public List<Advert> Adverts { get; set; }
    }
}
