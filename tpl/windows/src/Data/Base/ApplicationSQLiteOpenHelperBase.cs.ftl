<@header?interpret />

using System;
using System.IO;
using ${project_namespace}.Entity;
using SQLite.Net.Interop;
using SQLite.Net;
<#if options.fixture?? && options.fixture.enabled>
using ${project_namespace}.Fixture;
</#if>

namespace ${project_namespace}.Data.Base
{
    /// <summary>
    /// Openhelper to manage SQLite connection.
    /// </summary>
    public class ${project_name?cap_first}SQLiteOpenHelperBase : SQLiteConnection
    {
        #region Singleton
        private static ${project_name?cap_first}SQLiteOpenHelperBase instance;

        /// <summary>
        /// Default constructor calling base class.
        /// </summary>
        protected ${project_name?cap_first}SQLiteOpenHelperBase() : base(SqlitePlatform, DatabasePath)
        {
        }

        /// <summary>
        /// Instance of openhelper use to manage :
        ///     - Database connection
        ///     - Database creation / deletion
        /// </summary>
        public static ${project_name?cap_first}SQLiteOpenHelperBase Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new ${project_name?cap_first}SQLiteOpenHelperBase();
                }
                return instance;
            }
        }
        #endregion

        private const string TAG = "${project_name?cap_first}SQLiteOpenHelperBase";

        /// <summary>
        /// Database name
        /// </summary>
        private const String DB_NAME = "${project_name?lower_case}.sqlite";

        private static string databasePath = 
            Path.Combine(
                Windows.Storage.ApplicationData.Current.LocalFolder.Path
                , ${project_name?cap_first}SQLiteOpenHelperBase.DB_NAME);
        private static ISQLitePlatform sqlitePlatform = 
            new SQLite.Net.Platform.WinRT.SQLitePlatformWinRT();

        /// <summary>
        /// DatabasePath item property to set it on or directly use default.
        /// </summary>
        public static new string DatabasePath
        {
            get
            {
                return databasePath;
            }

            set
            {
                databasePath = value;
            }
        }

        /// <summary>
        /// SqlitePlatform item property to set it on or directly use default.
        /// </summary>
        public static ISQLitePlatform SqlitePlatform
        {
            get
            {
                return sqlitePlatform;
            }

            set
            {
                sqlitePlatform = value;
            }
        }

        /// <summary>
        /// Create database if not already exist.
        /// Take care you need to use DeleteDatabase() if your database structure
        /// have been modified.
        /// </summary>
        public void CreateDatabase()
        {
        <#list entities?values as entity>
            this.CreateTable<${entity.name?cap_first}>();
        </#list>
        }

        /// <summary>
        /// Delete all tables in database.
        /// </summary>
        public void DeleteDatabase()
        {
        <#list entities?values as entity>
            this.DropTable<${entity.name?cap_first}>();
        </#list>
        }

        <#if options.fixture?? && options.fixture.enabled>
        private void LoadData()
        {
            DataLoader dataLoader = new DataLoader(this);
            dataLoader.clean();

            int mode = DataLoader.MODE_APP;

            if (DemactApplication.DEBUG) {
                mode = DataLoader.MODE_APP | DataLoader.MODE_DEBUG;
            }

            dataLoader.loadData(mode);
        }

        </#if>
    }
}
