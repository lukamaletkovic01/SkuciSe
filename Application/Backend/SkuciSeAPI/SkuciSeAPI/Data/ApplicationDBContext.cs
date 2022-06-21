using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.Data
{
    public class ApplicationDBContext : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<Advert> Adverts { get; set; }
        public DbSet<AdvertDetails> AdvertDetails { get; set; }
        public DbSet<RealtyType> Realties { get; set; }
        public DbSet<AdvertType> AdvertTypes { get; set; }
        public DbSet<Like> Likes { get; set; }
        public DbSet<AdvertImage> AdvertImages { get; set; }
        public DbSet<FurnishedType> FurnishedTypes { get; set; }
        public DbSet<HeatingType> HeatingTypes { get; set; }
        public DbSet<OldNewBuilding> OldNewBuildings { get; set; }
        public DbSet<Comment> Comments { get; set; }
        public DbSet<Reservation> Reservations { get; set; }
        public DbSet<Verification> Verifications { get; set; }
        public DbSet<Recovery> Recoveries { get; set; }

        public ApplicationDBContext([NotNullAttribute] DbContextOptions options) : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {


            modelBuilder.Entity<Like>()
                .HasKey(o => new { o.LikeId, o.UserId, o.AdvertId });
        }
    }
}
