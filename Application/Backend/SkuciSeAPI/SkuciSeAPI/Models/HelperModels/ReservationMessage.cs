using FirebaseAdmin.Messaging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Models.HelperModels
{
    public class ReservationMessage
    {
        public long SenderId { get; set; }
        public long ReceiverId { get; set; }
        public long AdvertId { get; set; }
        public string Title { get; set; }
        public DateTime DateOfReservation { get; set; }
        public Message Message { get; set; }
        public Flag Flag { get; set; }
        public Status Answer { get; set; }
    }
}
