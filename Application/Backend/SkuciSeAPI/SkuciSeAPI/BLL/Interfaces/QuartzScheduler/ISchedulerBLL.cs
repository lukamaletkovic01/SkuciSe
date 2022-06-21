using FirebaseAdmin.Messaging;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.Interfaces.QuartzScheduler
{
    public interface ISchedulerBLL
    {
        public void SetScheduler(DateTime dateOfReservation, ReservationMessage reservationMessage);
    }
}
