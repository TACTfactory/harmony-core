<@header?interpret />
using System;
using System.IO;
using System.IO.IsolatedStorage;
using System.Linq;
using System.Windows;
using System.Windows.Resources;
using System.Data.Linq;
using ${project_namespace}.Entity;

namespace ${project_namespace}.Data.Base
{
    public class ${project_name?cap_first}SqlOpenHelperBase : DataContext
    {
        private const string TAG = "${project_name?cap_first}SqlOpenHelperBase";

        /// <summary>
        /// Database name
        /// </summary>
        private const String DB_NAME = "${project_name}.sdf";

        // Specify the connection string as a static,
        //  used in main page and app.xaml.
        public const string DATABASE_CONNECTION_STRING =
            "Data Source=isostore:/" + DB_NAME;

        <#if (entities??)>
            <#list entities?values as entity>
                <#if (!entity.internal)>
        /// <summary>
        /// Table for ${entity.name} entity.
        /// </summary>
        public Table<${entity.name}> ${entity.name};
        
                </#if>
            </#list>
        </#if>
        public ${project_name?cap_first}SqlOpenHelperBase()
            : base(DATABASE_CONNECTION_STRING)
        {

        }

        public bool CreateDatabaseIfNotExists()
        {
            bool result = this.DatabaseExists();

            if (!result)
            {
                StreamResourceInfo streamInfo = this.GetDatabaseAssets();

                if (streamInfo != null)
                {
                    this.CopyDataBase(streamInfo);
                }
                else
                {
                    //Create the database
                    this.CreateDatabase();
                }
            }

            return result;
        }

        private StreamResourceInfo GetDatabaseAssets()
        {
            StreamResourceInfo stream = Application.GetResourceStream(
                new Uri("Assets/" + DB_NAME, UriKind.Relative));

            return stream;
        }

        /**
        * Copies your database from your local assets-folder to the just created
        * empty database in the system folder, from where it can be accessed and
        * handled. This is done by transfering bytestream.
        * @throws IOException if error has occured while copying files
        * */
        private void CopyDataBase(StreamResourceInfo streamInfo)
        {
            if (streamInfo != null)
            {
                // Obtain the virtual store for the application.
                IsolatedStorageFile iso =
                    IsolatedStorageFile.GetUserStoreForApplication();

                // Create a stream for the file in the installation folder.
                using (Stream input = streamInfo.Stream)
                {
                    // Create a stream for the new file in the local folder.
                    using (IsolatedStorageFileStream output = iso.CreateFile(DB_NAME))
                    {
                        // Initialize the buffer.
                        byte[] readBuffer = new byte[4096];
                        int bytesRead = -1;

                        // Copy the file from the installation folder to the local folder. 
                        while ((bytesRead = input.Read(readBuffer, 0, readBuffer.Length)) > 0)
                        {
                            output.Write(readBuffer, 0, bytesRead);
                        }
                    }
                }
            }
        }

/*
        private void UpdateLastSyncDate()
        {
            DateTime dateTime = ${project_name?cap_first}Application.GetLastSyncDate();

            dateTime = this.GetMaxDateTime(
                dateTime, new ProductInfoSqlAdapter(this));
            dateTime = this.GetMaxDateTime(
                dateTime, new ProductSqlAdapter(this));
                
            ${project_name?cap_first}Application.SetLastSyncDate(dateTime.AddMinutes(1));
        }

        private DateTime GetMaxDateTime<T>(DateTime lastSyncDate,
            SqlAdapterBase<T> adapter) where T : EntityBase
        {
            try
            {
                DateTime max = adapter.GetAll()
                    .OrderByDescending(i => i.Sync_uDate)
                    .Select(i => i.Sync_uDate).First();

                if (max > lastSyncDate)
                {
                    lastSyncDate = max;
                }
            }
            catch (Exception e)
            {
                Utils.Log.D(TAG, e);
            }

            return lastSyncDate;
        }
*/
    }
}