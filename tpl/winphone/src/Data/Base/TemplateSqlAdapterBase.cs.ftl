<#include utilityPath + "all_imports.ftl" />
<#assign sync = curr.options.sync?? />
<#assign isRecursiveJoinTable = (curr.internal) && (!curr.relations[1]??) && (curr.relations[0].relation.targetEntity == entities[curr.relations[0].relation.targetEntity].fields[curr.relations[0].relation.inversedBy].relation.targetEntity) />
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#assign hasInternalFields = false />
<#list (curr_relations) as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />

<#assign isTopMostSuperClass = (curr.inheritance?? && (!curr.inheritance.superclass?? || !entities[curr.inheritance.superclass.name]??)) />

<#if (curr.internal)>
    <#assign extendType = "Void" />
<#else>
    <#assign extendType = curr.name />
</#if>
<#if sync>
    <#assign extend="SyncSqlAdapterBase<" +extendType+ ">" />
<#else>
    <#assign extend="SqlAdapter<" +extendType+ ">" />
</#if>

<@header?interpret />
using System;
using System.Data.Linq;
using System.Linq;
using ${project_namespace}.Entity;
using ${project_namespace}.Utils;

namespace ${project_namespace}.Data.Base
{
    public class ${curr.name}SqlAdapterBase : ${extend}
    {
        private const string TAG = "${curr.name}SqlAdapterBase";

        public ${curr.name}SqlAdapterBase()
            : this(new ${project_name?cap_first}SqlOpenHelper())
        {

        }

        public ${curr.name}SqlAdapterBase(DataContext context)
            : base(context)
        {

        }

        /// <summary>
        /// Get <see cref="${curr.name}"/> by id.
        /// </summary>
        /// <param name="id">Id of <see cref="${curr.name}"/></param>
        /// <returns>The wanted <see cref="${curr.name}"/></returns>
        public ${curr.name} GetById(int id)
        {
            ${curr.name} result = null;

            IQueryable<${curr.name}> queryable =
                from entity in this.Items
                where entity.Id == id
                select entity;

            result = queryable.SingleOrDefault();

            return result;
        }

        public override long Insert(${curr.name} item)
        {
            Log.D(TAG, "Insert into ${curr.name} table");

            this.Items.InsertOnSubmit(item);
            this.Items.Context.SubmitChanges();

            return item.Id;
        }

        /// <summary>
        /// Insert or update a <see cref="${curr.name}"/> in database.
        /// </summary>
        /// <param name="item">The <see cref="${curr.name}"/> to insert/update</param>
        /// <returns>1 if insert/update, 0 otherwise</returns>
        public int InsertOrUpdate(${curr.name} item)
        {
            int result = 0;

            if (this.GetById(item.Id) != null)
            {
                // Item already exists => update it
                result = this.Update(item);
            }
            else
            {
                // Item doesn't exist => create it
                long id = this.Insert(item);

                if (id != 0)
                {
                    result = 1;
                }
            }

            return result;
        }

<#if sync>
        public override void CompleteEntityRelationsServerId(${curr.name} item)
        {
            throw new NotImplementedException();
        }
        
</#if>
/*
        public override ${curr.name} Refresh(${curr.name} item)
        {
            ${curr.name} result = base.Refresh(item);
            result.Uuid = item.Uuid;
            result.Id = item.Id;
            result.ServerId = item.ServerId;
            result.Sync_dtag = item.Sync_dtag;
            result.Sync_uDate = item.Sync_uDate;
            result.Scans.Clear();

            return result;
        }
*/
    }
}