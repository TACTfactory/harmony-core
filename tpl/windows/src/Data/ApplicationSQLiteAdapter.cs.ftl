<@header?interpret />

using SQLite.Net;
using ${project_namespace}.Data.Base;

namespace ${project_namespace}.Data
{
    /// <summary>
    /// This class is used to manage data access as adapter.
    /// </summary>
    public abstract class SQLiteAdapter<T> : SQLiteAdapterBase<T> where T : class
    {
        /// <summary>
        /// Default constructor.
        /// </summary>
        /// <param name="context">Current SQLite connection to use.</param>
        protected SQLiteAdapter(SQLiteConnection context) : base(context)
        {

        }
    }
}
