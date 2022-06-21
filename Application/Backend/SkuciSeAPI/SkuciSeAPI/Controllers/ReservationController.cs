using Microsoft.AspNetCore.Mvc;
using SkuciSeAPI.Models;
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
    public class ReservationController : Controller
    {
        private readonly IReservationUIL _IReservationUIL;

        public ReservationController(IReservationUIL _IReservationUIL)
        {
            this._IReservationUIL = _IReservationUIL;
        }

        [HttpPost]
        [Route("CreateReservation")]
        public ActionResult Create(Reservation r)
        {
            try
            {
                _IReservationUIL.CreateReservation(r);
            }
            catch (Exception e)
            {
                return BadRequest(false);
            }

            return Ok(true);
        }

        [HttpGet]
        [Route("[action]")]
        public ActionResult IsAdvertReserved(long advertId, long UserId)
        {
            bool result = false;
            try
            {
                result = _IReservationUIL.IsAdvertReserved(advertId, UserId);
            }
            catch (Exception e)
            {
                return BadRequest(false);
            }

            return Ok(result);
        }

        [HttpGet]
        [Route("[action]")]
        public ActionResult GetReservedTerms(long userId)
        {
            List<DateTime> dates = null;
            try
            {
                dates = _IReservationUIL.GetReservedTerms(userId);
            }
            catch (Exception e)
            {
                return BadRequest();
            }


            return Ok(dates);
        }

        [HttpGet]
        [Route("[action]")]
        public ActionResult GetReservations(long userId)
        {
            List<Reservation> res = null;
            try
            {
                res = _IReservationUIL.GetUsersReservations(userId);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(res);
        }

        [HttpGet]
        [Route("[action]")]
        public ActionResult GetSentReservations(long userId, Status status)
        {
            List<Reservation> res = null;
            try
            {
                res = _IReservationUIL.GetSentReservations(userId, status);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(res);
        }

        [HttpGet]
        [Route("[action]")]
        public ActionResult GetReceivedReservations(long userId, Status status)
        {
            List<Reservation> res = null;
            try
            {
                res = _IReservationUIL.GetReceivedReservations(userId, status);
            }
            catch (Exception e)
            {
                return BadRequest();
            }
            return Ok(res);
        }

        [HttpGet]
        [Route("[action]/{ownerId}/{advertId}/{userId}")]
        public ActionResult CheckIfReviewed(long ownerId, long advertId, long userId)
        {
            Reservation reservation = null;
            try
            {
                reservation = _IReservationUIL.CheckIfReviewed(ownerId, advertId, userId);

                return Ok(reservation);
            }
            catch
            {
                return BadRequest();
            }
        }

        [HttpPost]
        [Route("[action]")]
        public ActionResult LeaveCommentForReservation(Reservation reservation)
        {
            try
            {
                _IReservationUIL.LeaveCommentForReservation(reservation);
            }
            catch (Exception e)
            {
                return BadRequest(false);
            }

            return Ok(true);
        }

        [HttpGet]
        [Route("[action]/{reservationId}")]
        public ActionResult CancelReservation(long reservationId)
        {
            try
            {
                _IReservationUIL.CancelReservation(reservationId);
            }
            catch (Exception e)
            {
                return BadRequest(false);
            }

            return Ok(true);
        }
    }
}
