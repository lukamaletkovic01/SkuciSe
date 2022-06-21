using FirebaseAdmin.Messaging;
using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models.HelperModels;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class NotificationUIL : INotificationUIL
    {
        private readonly INotificationBLL _INotificationBLL;

        public NotificationUIL(INotificationBLL iNotificationBLL)
        {
            _INotificationBLL = iNotificationBLL;
        }

        public string SendNotification(Message message)
        {
            return _INotificationBLL.SendNotification(message);
        }

        public string SendReservationMessage(ReservationMessage reservationMessage)
        {
            return _INotificationBLL.SendReservationMessage(reservationMessage);
        }
    }
}
