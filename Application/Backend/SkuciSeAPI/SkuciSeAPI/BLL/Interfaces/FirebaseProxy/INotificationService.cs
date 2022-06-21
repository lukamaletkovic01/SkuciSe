using FirebaseAdmin.Messaging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.BLL.Interfaces.FirebaseProxy
{
    public interface INotificationService
    {
        public Task<string> SendNotification(Message message);
    }
}
