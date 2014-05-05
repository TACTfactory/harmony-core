<@header?interpret />
using System.Data.Linq;
using System.Linq;
using ${project_namespace}.Data.Base;

namespace ${project_namespace}.Data
{
    public abstract class SqlAdapter<T> : SqlAdapterBase<T> where T : class
    {
        protected SqlAdapter(DataContext context) : base(context)
        {
            
        }
    }
}