<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

using System;
using System.Collections.Generic;
using System.Data.Linq;
using ${data_namespace};
using ${project_namespace}.Data.Base;
using ${project_namespace}.Entity;
using ${project_namespace}.Utils;


namespace ${project_namespace}.Fixture
{
    /**
     * DataManager.
     * 
     * This class is an "orm-like" manager which simplifies insertion in database
     * with sqlite adapters.
     */
    public class DataManager
    {
        /** TAG for debug purpose. */
        private const String TAG = "DataManager";
        
        /** HashMap to join Entity Name and its SQLiteAdapterBase. */
        protected IDictionary<Type, SqlAdapterBase> adapters =
                new Dictionary<Type, SqlAdapterBase>();
        /** is successfull. */
        protected bool isSuccessfull = true;
        /** is in internal transaction. */
        protected bool isInInternalTransaction = false;
        /** database. */
        protected DataContext context;
    <#list entities?values as entity>
        <#if ((entity.fields?size>0) && !(entity.internal))>
        /** ${entity.name} name constant. */
        private const String ${entity.name?upper_case} = "${entity.name?cap_first}";
        </#if>
    </#list>
    
        /**
         * Constructor.
         * @param ctx The context
         * @param db The DB to work in
         */
        public DataManager(DataContext context)
        {
            this.context = context;
            
            <#list entities?values as entity>
                <#if ((entity.fields?size>0) && !(entity.internal))>
            this.adapters.Add(typeof(${entity.name}),
                    new ${entity.name?cap_first}SqlAdapter(context));
            
                </#if>
            </#list>
        }
    
        <#--
        /**
         * Finds a object by its identifier.
         *
         * This is just a convenient shortcut for getRepository($className)
         * ->find($id).
         *
         * @param nameClass The class of the object's name
         * @param id The id of the object
         * @return The found object
         */
        public Object find(final String nameClass, final int id)
        {
            Object ret = null;
            this.beginTransaction();
    
            <#list entities?values as entity>
                <#if ((entity.fields?size>0) && (entity.ids?size>0) && !(entity.internal))>
            if (nameClass.equals(${entity.name?upper_case}))
            {
                ret = ((${entity.name}SqlAdapter)
                                           this.adapters.get(nameClass)).query(id);
            }
                </#if>
            </#list>
    
            return ret;
        }
        -->
        /**
         * Tells the ObjectManager to make an instance managed and persistent.
         *
         * The object will be entered into the database as a result of the <br />
         * flush operation.
         *
         * NOTE: The persist operation always considers objects that are not<br />
         * yet known to this ObjectManager as NEW. Do not pass detached <br />
         * objects to the persist operation.
         *
         * @param object $object The instance to make managed and persistent.
         * @return Count of objects entered into the DB
         */
        public int persist<T>(T obj) where T : class
        {
            int result;
    
            this.beginTransaction();
            
            try
            {
                SqlAdapterBase<T> adapter = this.getRepository<T>(obj);
                result = (int) adapter.Insert(obj);
            }
            catch (Exception ex)
            {
                Log.D(TAG, ex);
                this.isSuccessfull = false;
                result = 0;
            }
    
            return result;
        }
    
        /**
         * Removes an object instance.
         *
         * A removed object will be removed from the database as a result of <br />
         * the flush operation.
         *
         * @param object $object The object instance to remove.
         */
        public void remove<T>(T obj) where T : class
        {
            this.beginTransaction();
            
            try
            {
                ((SqlAdapterBase<T>)this.adapters[typeof(T)]).Delete(obj);
            }
            catch (Exception ex)
            {
                Log.E(TAG, ex);
                this.isSuccessfull = false;
            }
        }
    
        /**
         * Flushes all changes to objects that have been queued up to now to <br />
         * the database. This effectively synchronizes the in-memory state of<br />
         * managed objects with the database.
         */
        public void flush()
        {
            if (this.isInInternalTransaction)
            {
                if (this.isSuccessfull)
                {
                    this.context.SubmitChanges();
                    //this.db.setTransactionSuccessful();
                }
                
                //this.db.endTransaction();
                this.isInInternalTransaction = false;
            }
        }
    
        /**
         * Gets the repository for a class.
         *
         * @param className $className
         * @return \Doctrine\Common\Persistence\ObjectRepository
         */
        public SqlAdapterBase<T> getRepository<T>(String className) where T : class
        {
            return (SqlAdapterBase<T>) this.adapters[typeof(T)];
        }
    
    
        /**
         * Gets the repository for a given object.
         *
         * @param o object
         * @return \Doctrine\Common\Persistence\ObjectRepository
         */
        private SqlAdapterBase<T> getRepository<T>(T o) where T : class
        {
            String className = o.GetType().Name;
    
            return this.getRepository<T>(className);
        }
    
        /**
         * Check if the object is part of the current UnitOfWork and therefore
         * managed.
         *
         * @param object $object
         * @return bool
         */
        public bool contains<T>(T obj) where T : class
        {
            return false;
        }
    
        /**
         * Called before any transaction to open the DB.
         */
        private void beginTransaction()
        {
            // If we are not already in a transaction, begin it
            if (!this.isInInternalTransaction)
            {
                //this.db.beginTransaction();
                this.isSuccessfull = true;
                this.isInInternalTransaction = true;
            }
        }
    }
}