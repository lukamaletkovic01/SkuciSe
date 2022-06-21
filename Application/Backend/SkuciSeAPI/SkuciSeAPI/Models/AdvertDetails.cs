using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models
{
    public class AdvertDetails
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public long Id { get; set; }

        public DateTime Date { get; set; }
        public string Township { get; set; }
        public string City { get; set; }
        public long SquaredArea { get; set; }
        public long BedroomNumber { get; set; }

        public bool Terrace { get; set; }
        public bool Parking { get; set; }
        public bool Wifi { get; set; }
        public bool Tv { get; set; }
        public bool AirCondition { get; set; }
        public bool Kitchen { get; set; }
        public bool Bathroom { get; set; }
        public bool Alarm { get; set; }
        public bool Pool { get; set; }
        public bool Barbecue { get; set; }
        public bool FirePlace { get; set; }
        public bool Gym { get; set; }

        public int FloorNumber { get; set; }
        public string Description { get; set; }
        public string HouseOrder { get; set; }

        // Foreign Keys
        [ForeignKey("Advert")]
        public long AdvertId { get; set; }
        public Advert Advert { get; set; }

        public long FurnishedTypeId { get; set; }
        public FurnishedType FurnishedType { get; set; }

        public long HeatingTypeId { get; set; }
        public HeatingType HeatingType { get; set; }

        public long OldNewBuildingId { get; set; }
        public OldNewBuilding OldNewBuilding { get; set; }
        public double Latitude { get; set; }

        public double Longitude { get; set; }

    }
}
