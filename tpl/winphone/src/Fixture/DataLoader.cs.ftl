<#include utilityPath + "all_imports.ftl" />
<#assign orderedEntities = MetadataUtils.orderEntitiesByRelation() />
<@header?interpret />

using System;
using System.Collections.Generic;
using System.Data.Linq;
using ${project_namespace};
using ${project_namespace}.Utils;


namespace ${project_namespace}.Fixture
{
    /**
     * DataLoader for fixture purpose.
     * 
     * The Dataloader is a useful class to wrap the fixture loading 
     * for various entities.
     * The order in which the fixtures are loaded are the order in which
     * they are put in the "dataLoaders" list.
     */
    public class DataLoader
    {
        /** TAG for debug purpose. */
        protected const String TAG = "DataLoader";
        
        /** Test mode. */
        public static readonly int MODE_TEST   = 1;//Int32.parseInt("0001", 2);
        /** Application mode. */
        public static readonly int MODE_APP    = 2;//Int32.parseInt("0010", 2);
        /** Debug mode. */
        public static readonly int MODE_DEBUG  = 4;//Int32.parseInt("0100", 2);
        
        private DataContext context;
        
        /** List of DataLoaders. */
        private IList<FixtureBase> dataLoaders;
        
        /** List of Fixture folders. */
        private static IDictionary<int, String> fixtureFolders;
        
        /** Has the fixtures been loaded yet ? */
        public static bool hasFixturesBeenLoaded = false;
        
        /**
         * Static constructor.
         */
        static DataLoader()
        {
            fixtureFolders = new Dictionary<int, String>();
            
            // Add your own folder and mode here for new fixture cases
            fixtureFolders.Add(MODE_APP, "app/");
            fixtureFolders.Add(MODE_DEBUG, "debug/");
            fixtureFolders.Add(MODE_TEST, "test/");
        }
        
        /**
         * Constructor.
         * @param ctx The context
         */
        public DataLoader(DataContext context)
        {
            this.context = context;
            this.dataLoaders = new List<FixtureBase>();
            
            <#list orderedEntities as entityName>
                <#assign entity = entities[entityName] />
                <#if (!(entity.internal) && (entity.fields?size>0 || entity.inheritance??))>
            this.dataLoaders.Add(${entity.name}DataLoader.getInstance(this.context));
                </#if>
            </#list>
        }
        
        /**
         * LoadData from fixtures.
         * @param db The DB to work in
         * @param modes Mode
         */
        public void loadData(int modes)
        {
            Log.I(TAG, "Initializing fixtures.");
            DataManager manager = new DataManager(this.context);
            
            foreach (FixtureBase dataLoader in this.dataLoaders)
            {
                if (${project_name?cap_first}Application.DEBUG)
                {
                    Log.D(TAG, string.format(
                            "Loading {0} fixtures",
                            dataLoader.GetFixtureFileName()));
                }
    
                if (this.isType(modes, MODE_APP))
                {
                    if (${project_name?cap_first}Application.DEBUG)
                    {
                        Log.D(TAG, "Loading APP fixtures");
                    }
    
                    dataLoader.getModelFixtures(MODE_APP);
                }
                
                if (this.isType(modes, MODE_DEBUG))
                {
                    if (${project_name?cap_first}Application.DEBUG)
                    {
                        Log.D(TAG, "Loading DEBUG fixtures");
                    }
    
                    dataLoader.getModelFixtures(MODE_DEBUG);
                }
                
                if (this.isType(modes, MODE_TEST))
                {
                    if (${project_name?cap_first}Application.DEBUG)
                    {
                        Log.D(TAG, "Loading TEST fixtures");
                    }
    
                    dataLoader.getModelFixtures(MODE_TEST);
                }
                
                dataLoader.load(manager);
            }
    
            // After getting all the informations from the fixture,
            // serialize the datas.
            //for (FixtureBase dataLoader : this.dataLoaders)
            //{
            //    dataLoader.backup();
            //}
            
            hasFixturesBeenLoaded = true;
        }
    
        /**
         * isType.
         * @param modes Modes
         * @param mode Mode
         * @return true if mode
         */
        private boolean isType(int modes, int mode)
        {
            boolean result;
    
            if ((modes & mode) == mode)
            {
                result = true;
            } else
            {
                result = false;
            }
    
            return result;
        }
    
        /**
         * Get path to fixtures.
         * @param mode Mode
         * @return A String representing the path to fixtures
         */
        public static String getPathToFixtures(int mode)
        {
            return fixtureFolders[mode];
        }
    
        /**
         * Clean dataLoaders.
         */
        public void clean()
        {
            foreach (FixtureBase dataLoader in this.dataLoaders)
            {
                dataLoader.Clear();
            }
        }
    }
}