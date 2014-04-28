<@header?interpret />
using System;
using System.Collections.Generic;
using System.Data.Linq;
using System.Linq;
using System.Linq.Expressions;
using ${project_namespace}.Utils;

namespace ${project_namespace}.Data.Base
{
    public abstract class SqlAdapterBase<T> where T : class
    {
        private const string TAG = "${project_name?cap_first}SqlAdapterBase";

        /// <summary>
        /// Sql DataContext.
        /// </summary>
        protected DataContext Context { get; set; }

        /// <summary>
        /// Entity table.
        /// </summary>
        protected Table<T> Items { get; set; }

        protected SqlAdapterBase(DataContext context)
        {
            this.Context = context;
            this.Items = context.GetTable<T>();
        }

        /// <summary>
        /// Get all entities.
        /// </summary>
        /// <returns>An IQueryable of entities</returns>
        public virtual IQueryable<T> GetAll()
        {
            IQueryable<T> query = from T info in this.Items select info;

            return query;
        }

        /// <summary>
        /// Insert a single entity.
        /// </summary>
        /// <param name="item">Entity to insert</param>
        /// <returns>Id of entity</returns>
        public abstract long Insert(T item);

        /// <summary>
        /// Insert a list of entities.
        /// </summary>
        /// <param name="items">List of entities to insert</param>
        public virtual void Insert(List<T> items)
        {
            this.Items.InsertAllOnSubmit(items);
            this.Items.Context.SubmitChanges();
        }

        /// <summary>
        /// Update an entity.
        /// </summary>
        /// <param name="item">Entity to update</param>
        /// <returns>The number of updated row</returns>
        public virtual int Update(T item)
        {
            Log.D(TAG, "Update item in database");

            //this.Context.ObjectTrackingEnabled = false;
            //this.Context.DeferredLoadingEnabled = false;

            if (this.Items.GetOriginalEntityState(item) == null)
            {
                this.Items.Attach(item);
            }

            this.Items.Context.SubmitChanges();

            return 1;
        }

        public void Commit()
        {
            this.Items.Context.SubmitChanges();
        }

        /// <summary>
        /// Delete an entity.
        /// </summary>
        /// <param name="item">Entity to delete</param>
        /// <returns>Number of deleted row</returns>
        public int Delete(T item)
        {
            this.Items.DeleteOnSubmit(item);
            this.Items.Context.SubmitChanges();

            return 1;
        }

        /// <summary>
        /// Delete a list of entities.
        /// </summary>
        /// <param name="items">List of entities to delete</param>
        public void Delete(List<T> items)
        {
            this.Items.DeleteAllOnSubmit(items);
            this.Items.Context.SubmitChanges();
        }

        /// <summary>
        /// Get list of entities from criteria.
        /// </summary>
        /// <param name="criteria">Query filter</param>
        /// <returns>A IQueryable of entities</returns>
        public IQueryable<T> Query(params Expression<Func<T, bool>>[] criteria)
        {
            IQueryable<T> queryable =
                from entity in this.Items
                select entity;

            return criteria.Aggregate(queryable,
                (current, criterion) => current.Where(criterion));
        }

        /// <summary>
        /// Refresh item from database.
        /// Use this method if the original object not from database.
        /// </summary>
        /// <param name="item">Entity to refresh</param>
        /// <returns>Database entity</returns>
        public abstract T Refresh(T item);
    }
}