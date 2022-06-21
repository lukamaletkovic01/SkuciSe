using FirebaseAdmin.Messaging;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.Interfaces
{
    public interface INotificationBLL
    {
        public string SendNotification(Message message);
        public string SendReservationMessage(ReservationMessage reservationMessage);
    }
}
