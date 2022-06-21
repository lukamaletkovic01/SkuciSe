using FirebaseAdmin.Messaging;
using Newtonsoft.Json;
using Quartz;
using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.BLL.Interfaces.QuartzScheduler;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.QuartzScheduler
{
    public class NotificationAlertJob : INotificationAlertJob
    {
        private readonly INotificationBLL _INotificationBLL;

        public NotificationAlertJob(INotificationBLL iNotificationBLL)
        {
            _INotificationBLL = iNotificationBLL;
        }

        public void Execute(IJobExecutionContext context)
        {
            Console.WriteLine("aa");
            //JobDataMap dataMap = context.JobDetail.JobDataMap;
            //var datetemp = DateTime.Now.Equals(dataMap.GetDateTime("dateOfReservation"));
            //var resmsgtemp = JsonConvert.DeserializeObject<ReservationMessage>(dataMap.GetString("reservationMessage"));

            //if (DateTime.Now.Equals(dataMap.GetDateTime("dateOfReservation")))
            //{
            //    _INotificationBLL.SendReservationMessage(JsonConvert.DeserializeObject<ReservationMessage>(dataMap.GetString("reservationMessage")));
            //}
        }

        Task IJob.Execute(IJobExecutionContext context)
        {
            throw new NotImplementedException();
        }

        //    public async Task Execute(IJobExecutionContext context)
        //    {
        //        await Console.Out.WriteLineAsync("Greetings from HelloJob!");
        //    }
        //}
    }
}

