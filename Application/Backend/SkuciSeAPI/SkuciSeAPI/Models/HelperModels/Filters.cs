using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models.HelperModels
{
    public class Filters
    {
        public string City { get; set; }
        public Boolean Terrace { get; set; }
        public Boolean Parking { get; set; }
        public Boolean Wifi { get; set; }
        public Boolean Tv { get; set; }
        public Boolean AirCondition { get; set; }
        public Boolean Kitchen { get; set; }
        public Boolean Bathroom { get; set; }
        public Boolean Alarm { get; set; }
        public Boolean Pool { get; set; }
        public Boolean Barbecue { get; set; }
        public Boolean FirePlace { get; set; }
        public Boolean Gym { get; set; }
        public Boolean Rent { get; set; }
        public Boolean Sell { get; set; }
        public Boolean House { get; set; }
        public Boolean Flat { get; set; }
        public Boolean Garage { get; set; }
        public Boolean ParkingLott { get; set; }
        public Boolean BusinessArea { get; set; }
        public long PriceMin { get; set; }
        public long PriceMax { get; set; }
    }
}
