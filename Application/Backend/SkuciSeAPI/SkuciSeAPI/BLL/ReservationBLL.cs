using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class ReservationBLL : IReservationBLL
    {
        private readonly IReservationDAL _IReservationDAL;
        private readonly ICommentBLL _ICommentBLL;

        public ReservationBLL(IReservationDAL _IReservationDAL, ICommentBLL _ICommentBLL)
        {
            this._IReservationDAL = _IReservationDAL;
            this._ICommentBLL = _ICommentBLL;
        }

        public Reservation CheckIfReviewed(long ownerId, long advertId, long userId)
        {
            Reservation reservation = _IReservationDAL.GetReservation(ownerId, advertId, userId);
            if (reservation != null && DateTime.Now > reservation.DateOfReservation && reservation.Status.Equals(Status.Confirmed) && reservation.CommentId == null)
            {
                return reservation;
            }

            return null;
        }

        public void CreateReservation(Reservation r)
        {
            if (_IReservationDAL.GetReservation(r.OwnerId, r.AdvertId, r.UserId) == null && _IReservationDAL.GetReservationByDate(r.OwnerId, r.DateOfReservation) == null)
            {
                _IReservationDAL.Create(r);
            }
            else throw new Exception("Reservation already exists.");
        }

        public DateTime GetDateOfReservation(long ownerId, long advertId, long userId)
        {
            Reservation reservation = _IReservationDAL.GetReservation(ownerId, advertId, userId);
            if (reservation == null)
            {
                throw new Exception("Reservation doesn't exists in database.");
            }

            return reservation.DateOfReservation;
        }

        public List<Reservation> GetReceivedReservations(long userId, Status status)
        {
            return _IReservationDAL.GetReceivedReservations(userId, status);
        }

        public List<DateTime> GetReservedTerms(long UserId)
        {
            return _IReservationDAL.GetReservedTerms(UserId);
        }

        public List<Reservation> GetSentReservations(long userId, Status status)
        {
            return _IReservationDAL.GetSentReservations(userId, status);
        }

        public List<Reservation> GetUsersReservations(long UserId)
        {
            return _IReservationDAL.GetUsersReservations(UserId);
        }

        public void LeaveCommentForReservation(Reservation reservation)
        {
            Reservation newReservation = _IReservationDAL.GetById(reservation.Id);
            if (newReservation == null || reservation == null)
            {
                throw new Exception();
            }

            newReservation.CommentId = _ICommentBLL.LeaveComment(reservation.Comment);
            _IReservationDAL.Update(newReservation);
        }

        public bool IsAdvertReserved(long advertId, long UserId)
        {
            return _IReservationDAL.IsAdvertReserved(advertId, UserId);
        }


        public void UpdateReservationStatus(long ownerId, long advertId, long userId, Status answer)
        {
            Reservation newReservation = _IReservationDAL.GetReservation(ownerId, advertId, userId);
            if (newReservation == null)
            {
                throw new Exception("Reservation doesn't exists in database.");
            }

            newReservation.Status = answer;
            _IReservationDAL.Update(newReservation);
        }

        public bool CancelReservation(long reservationId)
        {
            Reservation reservation = _IReservationDAL.GetById(reservationId);
            if (reservation == null)
            {
                throw new Exception("Reservation doesn't exists in database.");
            }

            try
            {
                _IReservationDAL.Delete(reservation);
            }
            catch
            {
                return false;
            }

            return true;
        }
    }
}
