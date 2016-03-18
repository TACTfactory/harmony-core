<@header?interpret />

using ${project_namespace}.Data.Base;

namespace ${project_namespace}.Data
{
    /// <summary>
    /// This class is used to access SQLite database.
    /// </summary>
    public class ${project_name?cap_first}SQLiteOpenHelper : ${project_name?cap_first}SQLiteOpenHelperBase
    {
        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${project_name?cap_first}SQLiteOpenHelper()
        {

        }
    }
}