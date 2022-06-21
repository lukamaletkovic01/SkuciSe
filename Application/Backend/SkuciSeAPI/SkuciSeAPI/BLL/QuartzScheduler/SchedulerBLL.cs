using FirebaseAdmin.Messaging;
using Newtonsoft.Json;
using Quartz;
using Quartz.Impl;
using Quartz.Impl.Matchers;
using SkuciSeAPI.BLL.Interfaces.QuartzScheduler;
using SkuciSeAPI.Models.HelperModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.QuartzScheduler
{
    public class SchedulerBLL : ISchedulerBLL
    {
        public SchedulerBLL() { }

        public async void SetScheduler(DateTime dateOfReservation, ReservationMessage reservationMessage)
        {
            IScheduler scheduler = await StdSchedulerFactory.GetDefaultScheduler();
            IJobDetail job = JobBuilder.Create<NotificationAlertJob>()
                                        .WithIdentity("job1", "group1")
                                        .Build();

            // Trigger the job to run now, and then repeat every 10 seconds
            ITrigger trigger = TriggerBuilder.Create()
                                        .WithIdentity("trigger1", "group1")
                                        .WithSimpleSchedule(x => x
                                            .WithIntervalInSeconds(1)
                                            .RepeatForever())
                                        .Build();

            // Tell quartz to schedule the job using our trigger
            await scheduler.ScheduleJob(job, trigger);
            await scheduler.Start();

            //IScheduler scheduler = await StdSchedulerFactory.GetDefaultScheduler();

            ////JobDataMap jobDataMap = new JobDataMap();
            ////jobDataMap.Put("dateOfReservation", dateOfReservation);
            ////jobDataMap.Put("reservationMessage", JsonConvert.SerializeObject(reservationMessage));
            //string notificationAlertJobId = Guid.NewGuid().ToString();
            //string notificationAlertTriggerId = notificationAlertJobId + "/notificationAlertJob?=" + reservationMessage.ReceiverId + "&" + reservationMessage.AdvertId + "&" + dateOfReservation.ToString().Replace(" ", "");

            //IJobDetail job = JobBuilder.Create<NotificationAlertJob>()
            //                            .WithIdentity(notificationAlertJobId, "notificationAlertJobGroup")
            //                            // .UsingJobData(jobDataMap)
            //                            .Build();

            //ITrigger trigger = TriggerBuilder.Create()
            //        .WithIdentity(notificationAlertTriggerId, "notificationAlertTriggerGroup")
            //        .WithSimpleSchedule(x => x.WithIntervalInSeconds(1).RepeatForever())
            //        // .WithSchedule(CronScheduleBuilder.DailyAtHourAndMinute(8, 0))
            //        .ForJob(job)
            //        .Build();

            //await scheduler.ScheduleJob(job, trigger);
            //await scheduler.Start();

            //var jobGroups = scheduler.GetJobGroupNames();
            //// IList<string> triggerGroups = scheduler.GetTriggerGroupNames();

            //foreach (string group in await jobGroups)
            //{
            //    GroupMatcher<JobKey> groupMatcher = GroupMatcher<JobKey>.GroupContains(group);
            //    var jobKeys = scheduler.GetJobKeys(groupMatcher);
            //    foreach (var jobKey in await jobKeys)
            //    {
            //        var detail = scheduler.GetJobDetail(jobKey);
            //        var triggers = scheduler.GetTriggersOfJob(jobKey);
            //        foreach (ITrigger t in await triggers)
            //        {
            //            Console.WriteLine(group);
            //            Console.WriteLine(jobKey.Name);
            //            Console.WriteLine(t.Key.Name);
            //            Console.WriteLine(t.Key.Group);
            //            Console.WriteLine(t.GetType().Name);
            //            Console.WriteLine(scheduler.GetTriggerState(t.Key));
            //            DateTimeOffset? nextFireTime = t.GetNextFireTimeUtc();
            //            if (nextFireTime.HasValue)
            //            {
            //                Console.WriteLine(nextFireTime.Value.LocalDateTime.ToString());
            //            }

            //            DateTimeOffset? previousFireTime = t.GetPreviousFireTimeUtc();
            //            if (previousFireTime.HasValue)
            //            {
            //                Console.WriteLine(previousFireTime.Value.LocalDateTime.ToString());
            //            }
            //        }
            //    }
            //}
        }
    }
}
