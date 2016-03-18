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
    public class ${project_name?cap_first}SQLiteOpenHelperBase : SQLiteConnection
    {
        #region Singleton
        private static ${project_name?cap_first}SQLiteOpenHelperBase instance;

        private ${project_name?cap_first}SQLiteOpenHelperBase() : base(Instance.SqlitePlatform, Instance.DatabasePath)
        {
        }

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

        private string databasePath = 
            Path.Combine(
                Windows.Storage.ApplicationData.Current.LocalFolder.Path
                , "this.DB_NAME");
        private static ISQLitePlatform sqlitePlatform = 
            new SQLite.Net.Platform.WinRT.SQLitePlatformWinRT();
            
        public string DatabasePath
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

        public ISQLitePlatform SqlitePlatform
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
            
        public void CreateDatabase()
        {
        <#list entities?values as entity>
            this.CreateTable<${entity.name?cap_first}>();
        </#list>
        }

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