using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.UIL.Interfaces
{
    public interface IReservationUIL
    {
        public List<Reservation> GetUsersReservations(long UserId);
        public List<DateTime> GetReservedTerms(long UserId);
        public void CreateReservation(Reservation r);
        public List<Reservation> GetSentReservations(long userId, Status status);
        public List<Reservation> GetReceivedReservations(long userId, Status status);
        public Reservation CheckIfReviewed(long ownerId, long advertId, long userId);
        public void LeaveCommentForReservation(Reservation reservation);
        public bool IsAdvertReserved(long advertId, long UserId);
        public bool CancelReservation(long reservationId);
    }
}
