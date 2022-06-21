using FirebaseAdmin.Messaging;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SkuciSeAPI.Models.HelperModels;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class NotificationController : ControllerBase
    {
        private readonly INotificationUIL _INotificationUIL;

        public NotificationController(INotificationUIL iNotificationUIL)
        {
            _INotificationUIL = iNotificationUIL;
        }

        [HttpPost]
        [Route("[action]")]
        public ActionResult SendNotification(Message message)
        {
            try
            {
                return Ok(_INotificationUIL.SendNotification(message));
            }
            catch (Exception e)
            {
                return BadRequest(e);
            }
        }

        [HttpPost]
        [Route("[action]")]
        public ActionResult SendReservationMessage(ReservationMessage reservationMessage)
        {
            try
            {
                _INotificationUIL.SendReservationMessage(reservationMessage);
                return Ok(true);
            }
            catch (Exception e)
            {
                return BadRequest(false);
            }
        }
    }
}
