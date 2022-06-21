using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IReservationDAL : IBaseDAL<Reservation>
    {
        public List<Reservation> GetUsersReservations(long UserId);
        public List<DateTime> GetReservedTerms(long UserId);
        public Reservation GetReservation(long ownerId, long advertId, long userId);
        public List<Reservation> GetSentReservations(long userId, Status status);
        public List<Reservation> GetReceivedReservations(long userId, Status status);

        public Reservation GetReservationByDate(long ownerId, DateTime date);
        public bool IsAdvertReserved(long advertId,long UserId);
    }
}
