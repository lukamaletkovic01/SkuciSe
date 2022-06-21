// Osnovna klasa za komunikaciju sa bazom

using Microsoft.EntityFrameworkCore;
using SkuciSeAPI.DAL.Interfaces;
using SkuciSeAPI.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SkuciSeAPI.DAL
{
    public class BaseDAL<T> : IBaseDAL<T> where T : class
    {
        private readonly ApplicationDBContext _context;
        private DbSet<T> entities = null;

        public BaseDAL(ApplicationDBContext context)
        {
            this._context = context;
            entities = _context.Set<T>();
        }

        public T Create(T entity)
        {
            if (entity == null)
            {
                throw new ArgumentNullException("Entity for create is null.");
            }
            entities.Add(entity);
            _context.SaveChanges();

            return entity;
        }

        public void Delete(T entity)
        {
            if (entity == null)
            {
                throw new ArgumentNullException("Entity for delete is null.");
            }
            entities.Remove(entity);
            _context.SaveChanges();
        }

        public T GetById(long id)
        {
            return entities.Find(id);
        }

        public List<T> GetAll()
        {
            return entities.AsEnumerable().ToList();
        }

        public void Update(T entity)
        {
            if (entity == null)
            {
                throw new ArgumentNullException("Entity for update is null.");
            }
            _context.SaveChanges();
        }
    }
}
