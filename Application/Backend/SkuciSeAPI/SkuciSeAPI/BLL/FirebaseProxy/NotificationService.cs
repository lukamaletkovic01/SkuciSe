using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;
using SkuciSeAPI.BLL.Interfaces.FirebaseProxy;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.FirebaseProxy
{
    public class NotificationService : INotificationService
    {
        private readonly FirebaseMessaging messaging;

        public NotificationService()
        {
            var app = FirebaseApp.Create(new AppOptions() { Credential = GoogleCredential.FromFile("serviceAccountKey.json").CreateScoped("https://www.googleapis.com/auth/firebase.messaging") });
            messaging = FirebaseMessaging.GetMessaging(app);
        }


        public async Task<string> SendNotification(Message message)
        {
            return await messaging.SendAsync(message);
        }
    }
}
