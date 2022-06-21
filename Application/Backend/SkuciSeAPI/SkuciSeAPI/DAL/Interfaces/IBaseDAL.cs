using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SkuciSeAPI.DAL.Interfaces
{
    public interface IBaseDAL<T> where T : class
    {
        T Create(T entity);
        void Update(T entity);
        void Delete(T entity);
        List<T> GetAll();
        T GetById(long id);
    }
}
