<@header?interpret />

using System;
using System.Collections.Generic;
using System.Linq;
using SQLite.Net;
using SQLiteNetExtensions.Extensions;
using ${project_namespace}.Utils;

namespace ${project_namespace}.Data.Base
{
    public abstract class SQLiteAdapterBase
    {

    }

    /// <summary>
    /// Base adapter class to connect to SQLite.
    /// </summary>
    /// <typeparam name="T">Whished entity to map.</typeparam>
    public abstract class SQLiteAdapterBase<T> : SQLiteAdapterBase where T : class
    {
        private const string TAG = "${project_name?cap_first}SQLiteAdapterBase";

        /// <summary>
        /// Sql DataContext.
        /// </summary>
        protected SQLiteConnection Context { get; set; }

        /// <summary>
        /// Entity table.
        /// </summary>
        protected T Items { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        protected SQLiteAdapterBase(SQLiteConnection context)
        {
            this.Context = context;
        }

        /// <summary>
        /// Get all entities.
        /// </summary>
        /// <returns>An IQueryable of entities.</returns>
        public virtual List<T> GetAll()
        {
            List<T> query = this.Context.Table<T>().ToList<T>();

            return query;
        }

        /// <summary>
        /// Insert a single entity.
        /// </summary>
        /// <param name="item">Entity to insert.</param>
        /// <returns>Id of entity.</returns>
        public abstract Int32 Insert(T item);

        /// <summary>
        /// Insert a list of entities.
        /// </summary>
        /// <param name="items">List of entities to insert.</param>
        public virtual List<Int32> Insert(List<T> items)
        {
            List<Int32> ids = new List<Int32>();

            foreach (var item in items)
            {
                ids.Add(this.Context.Insert(items));
            }

            return ids;
        }

        /// <summary>
        /// Update an entity.
        /// </summary>
        /// <param name="item">Entity to update.</param>
        public virtual void Update(T item)
        {
            Log.D(TAG, "Update item in database");

            this.Context.UpdateWithChildren(item);
        }

        /// <summary>
        /// Remove all entity items.
        /// </summary>
        public virtual Int32 Clear()
        {
            return this.Context.DeleteAll<T>();
        }

        /// <summary>
        /// Retrieve last insertred id.
        /// </summary>
        /// <returns>Last inserted id.</returns>
        public virtual Int32 LastInsertedRowId()
        {
            string sql = @"select last_insert_rowid()";
            return this.Context.ExecuteScalar<Int32>(sql);
        }

        public void Commit()
        {
            this.Context.Commit();
        }

        /// <summary>
        /// Delete an entity.
        /// </summary>
        /// <param name="item">Entity to delete.</param>
        /// <returns>Number of deleted row.</returns>
        public virtual Int32 Delete(T item)
        {
            return this.Context.Delete<T>(item);
        }

        /// <summary>
        /// Delete a list of entities.
        /// </summary>
        /// <param name="items">List of entities to delete.</param>
        /// <returns>List of deleted ids.</returns>
        public List<Int32> Delete(List<T> items)
        {
            List<Int32> result = new List<Int32>();
            foreach (var item in items)
            {
                result.Add(this.Delete(item));
            }

            return result;
        }

        /// <summary>
        /// Get list of entities from criteria.
        /// </summary>
        /// <param name="criteria">Query filter</param>
        /// <returns>A IQueryable of entities</returns>
        public virtual List<T> Query(string query, params Object[] criteria)
        {
            return this.Context.Query<T>(query,criteria);
        }

        /// <summary>
        /// Get the table's columns.
        /// </summary>
        /// <returns>List of cols</returns>
        public abstract string[] getCols();

        /// <summary>
        /// Get the table's columns alias.
        /// </summary>
        /// <returns>List of cols</returns>
        public abstract string[] getAliasedCols();
    }
}
