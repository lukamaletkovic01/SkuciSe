using FirebaseAdmin.Messaging;
using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.BLL.Interfaces.FirebaseProxy;
using SkuciSeAPI.BLL.Interfaces.QuartzScheduler;
using SkuciSeAPI.Models;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL
{
    public class NotificationBLL : INotificationBLL
    {
        private readonly INotificationService _INotificationService;
        private readonly IUserBLL _IUserBLL;
        private readonly IAdvertBLL _IAdvertBLL;
        private readonly IReservationBLL _IReservationBLL;
        private readonly ISchedulerBLL _ISchedulerBLL;

        public NotificationBLL(INotificationService iNotificationService, IUserBLL iUserBLL, IAdvertBLL iAdvertBLL, IReservationBLL iReservationBLL, ISchedulerBLL iSchedulerBLL)
        {
            _INotificationService = iNotificationService;
            _IUserBLL = iUserBLL;
            _IAdvertBLL = iAdvertBLL;
            _IReservationBLL = iReservationBLL;
            _ISchedulerBLL = iSchedulerBLL;
        }

        public string SendNotification(Message message)
        {
            return _INotificationService.SendNotification(message).Result;
        }

        public string SendReservationMessage(ReservationMessage reservationMessage)
        {
            Message message = new Message();
            User receiver = _IUserBLL.GetById(reservationMessage.ReceiverId);
            User sender = _IUserBLL.GetById(reservationMessage.SenderId);
            Advert advert = _IAdvertBLL.GetById(reservationMessage.AdvertId);

            if (reservationMessage.Flag.Equals(Flag.Request))
            {
                if (receiver != null && sender != null && advert != null)
                {
                    message.Token = receiver.FcmToken;
                    message.Notification = new Notification
                    {
                        Title = reservationMessage.Title,
                        Body = "Korisnik " + sender.Firstname + " " + sender.Lastname + " Vam šalje zahtev za razgledanje Vaše " + advert.Name + " nekretnine"
                    };

                    message.Data = new Dictionary<string, string>
                    {
                        {"advertId", advert.Id.ToString()}, {"advertName", advert.Name }, { "dateOfReservation", reservationMessage.DateOfReservation.ToString()}, { "flag" , "request" }, { "answer", ""}
                    };

                    message.Android = new AndroidConfig
                    {
                        Notification = new AndroidNotification
                        {
                            ClickAction = "android.intent.action.ReceivedReservations"
                        }
                    };

                    Reservation reservation = new Reservation
                    {
                        AdvertId = advert.Id,
                        OwnerId = receiver.Id,
                        UserId = sender.Id,
                        DateOfReservation = reservationMessage.DateOfReservation,
                        Status = Status.Pending,
                    };

                    _IReservationBLL.CreateReservation(reservation);
                    return _INotificationService.SendNotification(message).Result;
                }
                else throw new Exception("Receiver, sender or advert aren't valid. Unable to send a message.");
            }
            else if (reservationMessage.Flag.Equals(Flag.Response))
            {
                if (receiver != null && sender != null && advert != null)
                {
                    DateTime dateOfReservation = _IReservationBLL.GetDateOfReservation(sender.Id, advert.Id, receiver.Id);

                    message.Token = receiver.FcmToken;
                    message.Notification = new Notification
                    {
                        Title = reservationMessage.Title
                    };

                    message.Data = new Dictionary<string, string>
                    {
                        {"advertId", advert.Id.ToString()}, {"advertName", advert.Name }, { "dateOfReservation", dateOfReservation.ToString()}, { "flag" , "response" }, { "answer", reservationMessage.Answer.Equals(Status.Confirmed) ? "confirmed" : "rejected"}
                    };

                    message.Android = new AndroidConfig
                    {
                        Notification = new AndroidNotification
                        {
                            ClickAction = "android.intent.action.SentReservationsActivity"
                        }
                    };

                    if (reservationMessage.Answer.Equals(Status.Confirmed))
                    {
                        message.Notification.Body = "Korisnik " + sender.Firstname + " " + sender.Lastname + " je prihvatio razgledanje nekretnine " + advert.Name;
                        try
                        {
                            _IReservationBLL.UpdateReservationStatus(sender.Id, advert.Id, receiver.Id, reservationMessage.Answer);

                            //_ISchedulerBLL.SetScheduler(dateOfReservation, new ReservationMessage
                            //{
                            //    SenderId = reservationMessage.SenderId,
                            //    ReceiverId = reservationMessage.ReceiverId,
                            //    AdvertId = reservationMessage.AdvertId,
                            //    Title = "Završeno razgledanje",
                            //    Flag = reservationMessage.Flag,
                            //    Answer = reservationMessage.Answer
                            //});

                            return _INotificationService.SendNotification(message).Result;
                        }
                        catch (Exception e)
                        {
                            throw new Exception("Answer not valid. Unable to send a message. Check if reservation exists in database.\n" + e);
                        }
                    }
                    else if (reservationMessage.Answer.Equals(Status.Rejected))
                    {
                        message.Notification.Body = "Korisnik " + sender.Firstname + " " + sender.Lastname + " je odbio razgledanje nekretnine " + advert.Name;
                        try
                        {
                            _IReservationBLL.UpdateReservationStatus(sender.Id, advert.Id, receiver.Id, reservationMessage.Answer);
                            return _INotificationService.SendNotification(message).Result;
                        }
                        catch (Exception e)
                        {
                            throw new Exception("Answer not valid. Unable to send a message. Check if reservation exists in database.\n" + e);
                        }
                    }
                    else throw new Exception("ReservationMessage answer error.");

                }
                else throw new Exception("Receiver, sender or advert aren't valid. Unable to send a message.");
            }
            else throw new Exception("ReservationMessage flag error.");
        }
    }
}
