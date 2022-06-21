using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL
{
    public class ReservationUIL : IReservationUIL
    {
        private readonly IReservationBLL _IReservationBLL;

        public ReservationUIL(IReservationBLL _IReservationBLL)
        {
            this._IReservationBLL = _IReservationBLL;
        }

        public Reservation CheckIfReviewed(long ownerId, long advertId, long userId)
        {
            return _IReservationBLL.CheckIfReviewed(ownerId, advertId, userId);
        }

        public void CreateReservation(Reservation r)
        {
            _IReservationBLL.CreateReservation(r);
        }

        public List<Reservation> GetReceivedReservations(long userId, Status status)
        {
            return _IReservationBLL.GetReceivedReservations(userId, status);
        }

        public List<DateTime> GetReservedTerms(long UserId)
        {
            return _IReservationBLL.GetReservedTerms(UserId);
        }

        public List<Reservation> GetSentReservations(long userId, Status status)
        {
            return _IReservationBLL.GetSentReservations(userId, status);
        }

        public List<Reservation> GetUsersReservations(long UserId)
        {
            return _IReservationBLL.GetUsersReservations(UserId);
        }

        public void LeaveCommentForReservation(Reservation reservation)
        {
            _IReservationBLL.LeaveCommentForReservation(reservation);
        }
        public bool IsAdvertReserved(long advertId, long UserId)
        {
            return _IReservationBLL.IsAdvertReserved(advertId, UserId);
        }

        public bool CancelReservation(long reservationId)
        {
            return _IReservationBLL.CancelReservation(reservationId);
        }
    }
}
