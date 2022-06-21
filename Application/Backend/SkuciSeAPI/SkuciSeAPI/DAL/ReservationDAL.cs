using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL
{
    public class ReservationDAL : BaseDAL<Reservation>, IReservationDAL
    {
        private readonly ApplicationDBContext _context;
        private DbSet<Reservation> reservations = null;

        public ReservationDAL(ApplicationDBContext _context) : base(_context)
        {
            this._context = _context;
            this.reservations = _context.Set<Reservation>();
        }

        public List<Reservation> GetReceivedReservations(long userId, Status status)
        {
            var result = from reservation in _context.Reservations.Include(a => a.User)
                         .Include(a => a.Advert).ThenInclude(b => b.AdvertDetails)
                         .Include(a => a.Advert).ThenInclude(b => b.AdvertImages)
                         where reservation.OwnerId.Equals(userId) && reservation.Status.Equals(status)
                         select reservation;
            return result.ToList();
        }

        public Reservation GetReservation(long ownerId, long advertId, long userId)
        {
            return reservations.Where(r => r.OwnerId.Equals(ownerId) && r.AdvertId.Equals(advertId) && r.UserId.Equals(userId)).FirstOrDefault();
        }
        public Reservation GetReservationByDate(long ownerId, DateTime date)
        {
            return reservations.Where(r => r.OwnerId.Equals(ownerId) && r.DateOfReservation.Equals(date)).FirstOrDefault();
        }

        public List<DateTime> GetReservedTerms(long UserId)
        {
            var result = from reservation in _context.Reservations
                         where reservation.OwnerId.Equals(UserId) && reservation.Status.Equals(Status.Confirmed)
                         select reservation.DateOfReservation;
            return result.ToList();
        }

        public List<Reservation> GetSentReservations(long userId, Status status)
        {
            var result = from reservation in _context.Reservations.Include(a => a.User)
                         .Include(a => a.Advert).ThenInclude(b => b.AdvertDetails)
                         .Include(a => a.Advert).ThenInclude(b => b.AdvertImages)
                         .Include(a => a.Advert).ThenInclude(b => b.User)
                         where reservation.UserId.Equals(userId) && reservation.Status.Equals(status)
                         select reservation;
            return result.ToList();
        }

        public List<Reservation> GetUsersReservations(long UserId)
        {
            var result = from reservation in _context.Reservations.Include(a => a.User)
                                                            .Include(a => a.Advert)
                         where reservation.OwnerId.Equals(UserId)
                         select reservation;
            return result.ToList();
        }

        public bool IsAdvertReserved(long advertId,long UserId)
        {

            var result = from reservation in _context.Reservations.Include(a => a.User)
                                                        .Include(a => a.Advert)
                         where reservation.UserId.Equals(UserId) && reservation.Status == Status.Confirmed && reservation.AdvertId == advertId
                         select reservation;

            if (result.Count() > 0)
                return true;
            else return false;
        }
    }
}
