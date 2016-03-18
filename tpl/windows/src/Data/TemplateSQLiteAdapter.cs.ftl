<#assign curr = entities[current_entity] />
<@header?interpret />

using SQLite.Net;
using ${project_namespace}.Data.Base;

namespace ${project_namespace}.Data
{
    /// <summary>
    /// ${curr.name?cap_first} adapter for data management.
    /// </summary>
    public class ${curr.name}SQLiteAdapter : ${curr.name}SQLiteAdapterBase
    {
        private const string TAG = "${curr.name}SQLiteAdapterBase";

        /// <summary>
        /// Default constructor
        /// </summary>
        public ${curr.name}SQLiteAdapter()
        {

        }

        /// <summary>
        /// ${curr.name?cap_first} adapter using context.
        /// </summary>
        /// <param name="context">Current SQLite connection to use.</param>
        public ${curr.name}SQLiteAdapter(SQLiteConnection context)
            : base(context)
        {

        }
    }
}
