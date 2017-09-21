<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
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
    <#assign extend="SyncSQLiteAdapterBase<" +extendType+ ">" />
<#else>
    <#assign extend="SQLiteAdapterBase<" +extendType+ ">" />
</#if>
<#assign fields = ViewUtils.getAllFields(curr) />
<#list fields?values as field>
    <#if field.id>
        <#assign idCurr = field.name />
    </#if>
</#list>
<@header?interpret />

using System;
using SQLite.Net;
using SQLiteNetExtensions.Extensions;
using System.Collections.Generic;
using ${project_namespace}.Entity;
using ${project_namespace}.Utils;
using ${project_namespace}.Provider.Contract;
using ${project_namespace}.Data;

namespace ${project_namespace}.Data.Base
{
    /// <summary>
    /// Base class to manage SQLite database functions.
    /// </summary>
    public class ${curr.name?cap_first}SQLiteAdapterBase : ${extend?cap_first}
    {
        private const string TAG = "${curr.name?cap_first}SQLiteAdapterBase";

        /// <summary>
        /// Constructor using current instance of ${project_name?cap_first}SQLiteOpenHelper.
        /// </summary>
        public ${curr.name?cap_first}SQLiteAdapterBase()
            : this(${project_name?cap_first}SQLiteOpenHelper.Instance)
        {

        }

        /// <summary>
        /// Constructor using new SQLiteConnection as context.
        /// </summary>
        public ${curr.name?cap_first}SQLiteAdapterBase(SQLiteConnection context)
            : base(context)
        {

        }

        /// <summary>
        /// Get ${curr.name?cap_first} by id.
        /// </summary>
        /// <param name="id">Id of ${curr.name?cap_first}.</param>
        /// <returns>The wanted ${curr.name?cap_first}.</returns>
        public ${curr.name?cap_first} GetById(Int32 id)
        {
            return this.Context.Find<${curr.name?cap_first}>(id);
        }

        /// <summary>
        /// Get ${curr.name?cap_first} by item using is id.
        /// </summary>
        /// <param name="item">Whished item regarding id to return.</param>
        /// <returns>The wanted ${curr.name?cap_first}.</returns>
        public ${curr.name?cap_first} GetById(${curr.name?cap_first} item)
        {
            return this.Context.Find<${curr.name?cap_first}>(item.${idCurr?cap_first});
        }

        /// <summary>
        /// Get ${curr.name?cap_first} with is linked entities references.
        /// </summary>
        /// <param name="item">Base ${curr.name?cap_first} item to get.</param>
        /// <returns>${curr.name?cap_first} item updated with references.</returns>
        public ${curr.name?cap_first} GetWithChildren(${curr.name?cap_first} item)
        {
            return this.Context.FindWithChildren<${curr.name?cap_first}>(item.${idCurr?cap_first});
        }

        <#list curr_fields as field>
            <#if field.relation?? && !field.internal>
                <#list entities?values as entity>
                    <#if entity.name == field.relation.targetEntity>
                        <#assign relatedEntity = entity />
                    </#if>
                </#list>
                <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                <#list fields?values as field>
                    <#if field.id>
                        <#assign id = field.name />
                    <#elseif field.relation?? && field.relation.targetEntity == curr.name>
                        <#assign field_mapped = field.name>
                    </#if>
                </#list>
                <#if field.relation.type == "ManyToMany" && MetadataUtils.getInversingField(field)??>
        /// <summary>
        /// Get list of ${curr.name?cap_first} items referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">Parent item where we checkout related items regarding is id.</param>
        /// <returns>List of referenced ${curr.name?cap_first} items.</returns>
        public List<${curr.name?cap_first}> GetByParentId(${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            return parent.${field_mapped?cap_first};
        }

        /// <summary>
        /// Insert ${curr.name?cap_first} item in ${field.relation.targetEntity?cap_first} parentId to save it in SQLite.
        /// </summary>
        /// <param name="item">Item to save associate to is parent.</param>
        /// <param name="parentId">Parent item to save child item.</param>
        /// <returns>${curr.name?cap_first} item database new id.</returns>
        public Int32 Insert(${curr.name?cap_first} item, ${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            parent.${field_mapped?cap_first}.Add(item);
            this.Insert(item);
            ${field.relation.targetEntity?cap_first}SQLiteAdapter parentAdapter =
                new ${field.relation.targetEntity?cap_first}SQLiteAdapter(this.Context);
            parentAdapter.Update(parent);
            return LastInsertedRowId();
        }

        /// <summary>
        /// Remove all ${curr.name?cap_first} items referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">${field.relation.targetEntity?cap_first} item containing ${curr.name?cap_first} items.</param>
        public void Clear(${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            foreach (var item in parent.${field_mapped?cap_first})
            {
                this.Context.Delete<${curr.name?cap_first}>(item.${idCurr?cap_first});
            }
        }
                <#elseif field.relation.type == "OneToMany" && MetadataUtils.getInversingField(field)??>
        /// <summary>
        /// Get ${curr.name?cap_first} item referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">Parent item where we checkout related item regarding is id.</param>
        /// <returns>${curr.name?cap_first} item.</returns>
        public ${curr.name?cap_first} GetByParentId(${field.relation.targetEntity?cap_first} parentId)
        {
            ${curr.name?cap_first} child =
                this.Context.FindWithChildren<${curr.name?cap_first}>(parentId.${field_mapped?cap_first});
            return child;
        }

        /// <summary>
        /// Insert ${curr.name?cap_first} item in ${field.relation.targetEntity?cap_first} parentId to save it in SQLite.
        /// </summary>
        /// <param name="item">Item to save associate to is parent.</param>
        /// <param name="parentId">Parent item to save child item.</param>
        /// <returns>${curr.name?cap_first} item database new id.</returns>
        public Int32 Insert(${curr.name?cap_first} item, ${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            parent.${field.relation.mappedBy?cap_first} = this.Insert(item);
            ${field.relation.targetEntity?cap_first}SQLiteAdapter parentAdapter =
                new ${field.relation.targetEntity?cap_first}SQLiteAdapter(this.Context);
            parentAdapter.Update(parent);
            return LastInsertedRowId();
        }

        /// <summary>
        /// Remove all ${curr.name?cap_first} items referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">${field.relation.targetEntity?cap_first} item containing ${curr.name?cap_first} items.</param>
        public void Clear(${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
                this.Context.Delete<${curr.name?cap_first}>(parent.${id?cap_first});
        }
                <#elseif field.relation.type == "OneToOne" && MetadataUtils.getInversingField(field)??>
        /// <summary>
        /// Get ${curr.name?cap_first} item referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">Parent item where we checkout related item regarding is id.</param>
        /// <returns>${curr.name?cap_first} item.</returns>
        public ${curr.name?cap_first} GetByParentId(${field.relation.targetEntity?cap_first} parentId)
        {
            ${curr.name?cap_first} child =
                this.Context.FindWithChildren<${curr.name?cap_first}>(parentId.${field_mapped?cap_first});
            return child;
        }

        /// <summary>
        /// Insert ${curr.name?cap_first} item in ${field.relation.targetEntity?cap_first} parentId to save it in SQLite.
        /// </summary>
        /// <param name="item">Item to save associate to is parent.</param>
        /// <param name="parentId">Parent item to save child item.</param>
        /// <returns>${curr.name?cap_first} item database new id.</returns>
        public Int32 Insert(${curr.name?cap_first} item, ${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            parent.${field_mapped?cap_first} = this.Insert(item);
            ${field.relation.targetEntity?cap_first}SQLiteAdapter parentAdapter =
                new ${field.relation.targetEntity?cap_first}SQLiteAdapter(this.Context);
            parentAdapter.Update(parent);
            return LastInsertedRowId();
        }

        /// <summary>
        /// Remove all ${curr.name?cap_first} items referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">${field.relation.targetEntity?cap_first} item containing ${curr.name?cap_first} items.</param>
        public void Clear(${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            this.Context.Delete<${curr.name?cap_first}>(parent.${field_mapped?cap_first});
        }
                <#elseif field.relation.type == "ManyToOne" && MetadataUtils.getInversingField(field)??>
        /// <summary>
        /// Get list of ${curr.name?cap_first} items referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">Parent item where we checkout related items regarding is id.</param>
        /// <returns>List of referenced ${curr.name?cap_first} items.</returns>
        public List<${curr.name?cap_first}> GetByParentId(${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            return parent.${field.relation.inversedBy?cap_first};
        }

        /// <summary>
        /// Insert ${curr.name?cap_first} item in ${field.relation.targetEntity?cap_first} parentId to save it in SQLite.
        /// </summary>
        /// <param name="item">Item to save associate to is parent.</param>
        /// <param name="parentId">Parent item to save child item.</param>
        /// <returns>${curr.name?cap_first} item database new id.</returns>
        public Int32 Insert(${curr.name?cap_first} item, ${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            parent.${field.relation.inversedBy?cap_first}.Add(item);
            this.Insert(item);
            ${field.relation.targetEntity?cap_first}SQLiteAdapter parentAdapter =
                new ${field.relation.targetEntity?cap_first}SQLiteAdapter(this.Context);
            parentAdapter.Update(parent);
            return LastInsertedRowId();
        }

        /// <summary>
        /// Remove all ${curr.name?cap_first} items referenced by ${field.relation.targetEntity?cap_first} parent.
        /// </summary>
        /// <param name="parentId">${field.relation.targetEntity?cap_first} item containing ${curr.name?cap_first} items.</param>
        public void Clear(${field.relation.targetEntity?cap_first} parentId)
        {
            ${field.relation.targetEntity?cap_first} parent =
                this.Context.FindWithChildren<${field.relation.targetEntity?cap_first}>(parentId.${id?cap_first});
            foreach (var item in parent.${field.relation.inversedBy?cap_first})
            {
                this.Context.Delete<${curr.name?cap_first}>(item.${idCurr?cap_first});
            }
        }
                </#if>
            </#if>
        </#list>

        /// <summary>
        /// Insert whised item.
        /// </summary>
        /// <param name="item">Item to save.</param>
        /// <returns>New id of item in database.</returns>
        public override Int32 Insert(${curr.name?cap_first} item)
        {
            Log.D(TAG, "Insert into ${curr.name?cap_first} table");
            this.Context.InsertWithChildren(item, true);
            return LastInsertedRowId();
        }

        /// <summary>
        /// Delete whised item.
        /// </summary>
        /// <param name="item">Item to delete.</param>
        /// <returns>Number of colones modifications.</returns>
        public override Int32 Delete(${curr.name?cap_first} item)
        {
            return this.Context.Delete<${curr.name?cap_first}>(item.${idCurr?cap_first}); ;
        }

        /// <summary>
        /// Insert or update a <see cref="${curr.name?cap_first}"/> in database.
        /// </summary>
        /// <param name="item">The <see cref="${curr.name?cap_first}"/> to insert/update</param>
        /// <returns>1 if insert/update, 0 otherwise</returns>
        public Int32 InsertOrUpdate(${curr.name?cap_first} item)
        {
            // We need to take care of updating an item with an id of 0.
            // If we do default behaviour for SQLite library is to assign
            // 0 value on all foreign keys.
            if (item.${idCurr?cap_first} == 0)
            {
                item.${idCurr?cap_first} = LastInsertedRowId() + 1;
            }
            this.Context.InsertOrReplace(item);
            return LastInsertedRowId();
        }

        /// <summary>
        /// Get the table's columns.
        /// </summary>
        /// <returns>List of cols</returns>
        public override string[] getCols()
        {
            return ${curr.name?cap_first}Contract.COLS;
        }

        /// <summary>
        /// Get the table's columns alias.
        /// </summary>
        /// <returns>List of cols</returns>
        public override string[] getAliasedCols()
        {
            return ${curr.name?cap_first}Contract.ALIASED_COLS;
        }

        /// <summary>
        /// Generate Entity Table Schema.
        /// </summary>
        /// <returns>"SQL query : CREATE TABLE..."</returns>
        public static String getSchema()
        {
            return "CREATE TABLE "
            + ${ContractUtils.getContractTableName(curr)}    + " ("
    <#list curr_fields as field>
        <#if (field.columnName??) && (!field.columnResult && (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")))>
            <#assign fieldNames = ContractUtils.getFieldsNames(field) />
            <#list fieldNames as fieldName>
            <#if (lastLine??)>${lastLine},"</#if>
                <#if (field.relation?? && field.relation.field_ref?size > 1)>
                    <#if field.nullable>
                        <#assign lastLine=" + ${fieldName}    + \"" + field.relation.field_ref[fieldName_index].schema?replace("NOT NULL", "") />
                    <#else>
                        <#assign lastLine=" + ${fieldName}    + \"" + field.relation.field_ref[fieldName_index].schema />
                    </#if>
                <#else>
                    <#assign lastLine=" + ${fieldName}    + \"" + field.schema />
                </#if>
            </#list>
        </#if>
    </#list>
            ${lastLine}<#if (singleTabInheritance && isTopMostSuperClass)>,"
            + ${ContractUtils.getContractCol(curr.inheritance.discriminatorColumn)} + " ${curr.inheritance.discriminatorColumn.schema}<#if (curr.inheritance.subclasses?size > 0)>,</#if>"<#elseif MetadataUtils.hasRelationOrIds(curr, false)>,"<#else>"</#if>
            <#if (singleTabInheritance)><#list curr.inheritance.subclasses as subclass>
            + ${subclass.name}SQLiteAdapter.getSchemaColumns() + ","
            </#list></#if>
            <#if (singleTabInheritance)><#list curr.inheritance.subclasses as subclass><#if (subclass.relations?size > 0)>
            + ${subclass.name}SQLiteAdapter.getSchemaConstraints()<#if subclass_has_next || MetadataUtils.hasRelationOrIds(curr, false)> + ","
            </#if></#if></#list></#if>

    <#if (curr.relations??)>
        <#list (curr.relations) as relation>
            <#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
            <#assign fieldNames = ContractUtils.getFieldsNames(relation) />
            <#list fieldNames as fieldName>
            <#assign refId = relation.relation.field_ref[fieldName_index] />
            <#if (relation.relation.resource)><#assign idCol = "ResourceContract.COL_ID" /><#else><#assign idCol = ContractUtils.getFieldsNames(refId)[0] /></#if>
            <#if (lastRelation??)>${lastRelation},"</#if>
                <#assign lastRelation=" + \"FOREIGN KEY(\" + ${fieldName}"
                + " + \") REFERENCES \" \n             + "
                + "${ContractUtils.getContractTableName(entities[relation.relation.targetEntity])} \n                + \" (\" + ${idCol} + \")">
            </#list>
            </#if>
        </#list>
            <#if (lastRelation??)>${lastRelation}"</#if>
    </#if>
    <#if (!((singleTabInheritance && curr.inheritance.superclass??) && entities[curr.inheritance.superclass.name]??) && curr_ids?size>1)>
            + ", PRIMARY KEY (" + <#list curr_ids as id><#assign fieldNames = ContractUtils.getFieldsNames(id) /><#list fieldNames as fieldName>${fieldName}<#if (id_has_next || fieldName_has_next)> + "," + </#if></#list></#list> + ")"
    </#if>
    <#if (joinedInheritance)>
            + ", FOREIGN KEY (" + ${ContractUtils.getContractCol(entities[curr.inheritance.superclass.name].ids[0])} + ") REFERENCES " + ${ContractUtils.getContractTableName(curr.inheritance.superclass)} + "(" + ${ContractUtils.getContractCol(entities[curr.inheritance.superclass.name].ids[0])} + ") ON DELETE CASCADE"
    </#if>
    <#list curr_fields as field>
        <#if (field.unique?? && field.unique)>
            + ", UNIQUE(" + ${ContractUtils.getContractCol(field)} + ")"
        </#if>
    </#list>
    <#if !(singleTabInheritance && !isTopMostSuperClass)>
            + ");"
    </#if>
    <#if (curr.indexes?? && curr.indexes?size > 0)>
        <#list curr.indexes?keys as indexKey>
            + "CREATE UNIQUE INDEX IF NOT EXISTS ${indexKey} ON ${curr.name}(<#list curr.indexes[indexKey] as indexColumn>${indexColumn}<#if indexColumn_has_next>, </#if></#list>);"
        </#list>
    </#if>
        ;
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
