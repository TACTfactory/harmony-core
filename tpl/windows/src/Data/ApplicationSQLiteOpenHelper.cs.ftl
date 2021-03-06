<@header?interpret />

using ${project_namespace}.Data.Base;

namespace ${project_namespace}.Data
{
    /// <summary>
    /// This class is used to access SQLite database.
    /// </summary>
    public class ${project_name?cap_first}SQLiteOpenHelper : ${project_name?cap_first}SQLiteOpenHelperBase
    {
        #region Singleton
        private static ${project_name?cap_first}SQLiteOpenHelper instance;

        /// <summary>
        /// Default constructor.
        /// </summary>
        protected ${project_name?cap_first}SQLiteOpenHelper()
        {
        }

        /// <summary>
        /// Instance of openhelper use to manage :
        ///     - Database connection
        ///     - Database creation / deletion
        /// </summary>
        public static ${project_name?cap_first}SQLiteOpenHelper Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new ${project_name?cap_first}SQLiteOpenHelper();
                }
                return instance;
            }
        }
        #endregion
    }
}
