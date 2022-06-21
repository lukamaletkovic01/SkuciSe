using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.OpenApi.Models;
using Quartz;
using SkuciSeAPI.BLL;
using SkuciSeAPI.BLL.FirebaseProxy;
using SkuciSeAPI.BLL.Interfaces;
using SkuciSeAPI.BLL.Interfaces.FirebaseProxy;
using SkuciSeAPI.BLL.Interfaces.QuartzScheduler;
using SkuciSeAPI.BLL.QuartzScheduler;
using SkuciSeAPI.DAL;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using SkuciSeAPI.UIL;
using SkuciSeAPI.UIL.Interfaces;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddSingleton(Configuration);
            services.AddDbContext<ApplicationDBContext>(opt =>
                opt.UseSqlite("Filename=skucise.db;")
            );

            services.AddControllers();
            services.AddControllers().
                AddNewtonsoftJson(options => options.SerializerSettings.ReferenceLoopHandling = Newtonsoft.Json.ReferenceLoopHandling.Ignore);

            services.AddCors(options =>
            {
                options.AddPolicy("AllowAll",
                    builder =>
                    {
                        builder
                        .AllowAnyOrigin()
                        .AllowAnyMethod()
                        .AllowAnyHeader();
                    });
            });

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = "SkuciSeAPI", Version = "v1-intern" });
            });

            // Services
            services.AddScoped(typeof(IBaseDAL<>), typeof(BaseDAL<>));

            services.AddScoped<IUserUIL, UserUIL>();
            services.AddScoped<IUserBLL, UserBLL>();
            services.AddScoped<IUserDAL, UserDAL>();

            services.AddScoped<IAdvertUIL, AdvertUIL>();
            services.AddScoped<IAdvertBLL, AdvertBLL>();
            services.AddScoped<IAdvertDAL, AdvertDAL>();

            services.AddScoped<IAdvertDetailsUIL, AdvertDetailsUIL>();
            services.AddScoped<IAdvertDetailsBLL, AdvertDetailsBLL>();
            services.AddScoped<IAdvertDetailsDAL, AdvertDetailsDAL>();

            services.AddScoped<IRealtyUIL, RealtyUIL>();
            services.AddScoped<IRealtyBLL, RealtyBLL>();
            services.AddScoped<IRealtyDAL, RealtyDAL>();

            services.AddScoped<ILikeUIL, LikeUIL>();
            services.AddScoped<ILikeBLL, LikeBLL>();
            services.AddScoped<ILikeDAL, LikeDAL>();

            services.AddScoped<ILoginUIL, LoginUIL>();
            services.AddScoped<ILoginBLL, LoginBLL>();

            services.AddScoped<IAdvertImageDAL, AdvertImageDAL>();
            services.AddScoped<IAdvertImageBLL, AdvertImageBLL>();
            services.AddScoped<IAdvertImageUIL, AdvertImageUIL>();

            services.AddScoped<ICommentUIL, CommentUIL>();
            services.AddScoped<ICommentBLL, CommentBLL>();
            services.AddScoped<ICommentDAL, CommentDAL>();

            services.AddScoped<IReservationUIL, ReservationUIL>();
            services.AddScoped<IReservationBLL, ReservationBLL>();
            services.AddScoped<IReservationDAL, ReservationDAL>();


            services.AddScoped<IVerificationUIL, VerificationUIL>();
            services.AddScoped<IVerificationBLL, VerificationBLL>();
            services.AddScoped<IVerificationDAL, VerificationDAL>();

            services.AddSingleton<INotificationService, NotificationService>();

            services.AddScoped<INotificationUIL, NotificationUIL>();
            services.AddScoped<INotificationBLL, NotificationBLL>();

            services.AddScoped<ISchedulerBLL, SchedulerBLL>();

            services.AddScoped<IJob, NotificationAlertJob>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseRouting();
            app.UseAuthorization();
            app.UseAuthentication();
            app.UseCors("AllowAll");
            app.UseStaticFiles();

            app.UseSwagger();
            app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "SkuciSeAPI v1-intern"));

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });

            app.Run(async (context) =>
            {
                await context.Response.WriteAsync("Could Not Find Anything");
            });
        }
    }
}
