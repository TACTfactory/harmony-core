<#include utilityPath + "all_imports.ftl" />
<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>
<@header?interpret />

namespace ${project_namespace}.Provider.Contract.Base
{
    public abstract class ${curr.name?cap_first}ContractBase
    {
<#if !curr.internal>
        <#list curr_fields as field>
            <#if !field.relation??>
        public const string COL_${field.name?upper_case} = "${field.name?lower_case}";
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
                <#list entities?values as entity>
                    <#if entity.name == field.relation.targetEntity>
                        <#assign relatedEntity = entity />
                    </#if>
                </#list>
                <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                <#list fields?values as field>
                    <#if field.id>
                        <#assign id = field.name />
                    </#if>
                </#list>
        public const string COL_${field.name?upper_case}_${id?upper_case} = "${field.name?lower_case}";
            </#if>
        </#list>

        <#list curr_fields as field>
            <#if !field.relation??>
        public const string ALIASED_COL_${field.name?upper_case} = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${field.name?upper_case};
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
                <#list entities?values as entity>
                    <#if entity.name == field.relation.targetEntity>
                        <#assign relatedEntity = entity />
                    </#if>
                </#list>
                <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                <#list fields?values as field>
                    <#if field.id>
                        <#assign id = field.name />
                    </#if>
                </#list>
        public const string ALIASED_COL_${field.name?upper_case}_${id?upper_case} = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_${id?upper_case};
            </#if>
        </#list>

        public const string TABLE_NAME = "${curr.name?cap_first}";

        public static string[] COLS = {
        <#list curr_fields as field>
            <#if !field.relation?? >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case},
                </#if>
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
                <#list entities?values as entity>
                    <#if entity.name == field.relation.targetEntity>
                        <#assign relatedEntity = entity />
                    </#if>
                </#list>
                <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                <#list fields?values as field>
                    <#if field.id>
                        <#assign id = field.name />
                    </#if>
                </#list>
                <#if field?is_last>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_${id?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_${id?upper_case},
                </#if>
            </#if>
        </#list>
        };

        public static string[] ALIASED_COLS = {
        <#list curr_fields as field>
            <#if !field.relation?? >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case},
                </#if>
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                <#list entities?values as entity>
                    <#if entity.name == field.relation.targetEntity>
                        <#assign relatedEntity = entity />
                    </#if>
                </#list>
                <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                <#list fields?values as field>
                    <#if field.id>
                        <#assign id = field.name />
                    </#if>
                </#list>
                <#if field?is_last>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}_${id?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}_${id?upper_case},
                </#if>
            </#if>
        </#list>
<#else>
        public const string COL_${curr.name?upper_case}_ID = "${curr.name?lower_case}_id";
        <#list curr_fields as field>
            <#assign test = field.name?uncap_first />
            <#if field.name == test>
            <#if !field.relation??>
        public const string COL_${field.name?upper_case} = "${field.name?lower_case}";
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
        public const string COL_${field.name?upper_case}_ID = "${field.name?lower_case}";
            </#if>
            </#if>
        </#list>

        public const string ALIASED_COL_${curr.name?upper_case}_ID = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${curr.name?upper_case}_ID;
        <#list curr_fields as field>
            <#assign test = field.name?uncap_first />
            <#if field.name == test>
            <#if !field.relation??>
        public const string ALIASED_COL_${field.name?upper_case} = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${field.name?upper_case};
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
        public const string ALIASED_COL_${field.name?upper_case}_ID = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID;
            </#if>
            </#if>
        </#list>

        public const string TABLE_NAME = "${curr.name?cap_first}";

        public static string[] COLS = {
            ${curr.name?cap_first}Contract.COL_${curr.name?upper_case}_ID,
        <#list curr_fields as field>
            <#assign test = field.name?uncap_first />
            <#if field.name == test>
            <#if !field.relation?? >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case},
                </#if>
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID,
                </#if>
            </#if>
            </#if>
        </#list>
        };

        public static string[] ALIASED_COLS = {
            ${curr.name?cap_first}Contract.ALIASED_COL_${curr.name?upper_case}_ID,
        <#list curr_fields as field>
            <#assign test = field.name?uncap_first />
            <#if field.name == test>
            <#if !field.relation?? >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case},
                </#if>
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                <#if field?is_last>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}_ID
                <#else>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}_ID,
                </#if>
            </#if>
            </#if>
        </#list>
</#if>
        };
    }
}