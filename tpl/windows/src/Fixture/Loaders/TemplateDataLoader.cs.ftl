<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<#assign hasLocaleTime = MetadataUtils.hasLocaleTime(curr) />
<@header?interpret />

using System;
using System.Data.Linq;
<#if fixtureType == "xml">
    <#assign listImported = false />
    <#assign arraylistImported = false />
    <#list curr.relations as relation>
        <#if !listImported && (relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany")>
using System.Collections.Generic;
            <#assign listImported = true />
            <#break />
        </#if>
        <#if !listImported && (relation.relation.type == "ManyToOne" && MetadataUtils.getInversingField(relation)??)>
using System.Collections.Generic;
            <#assign listImported = true />
        </#if>
    </#list>
</#if>
<#if fixtureType=="yml">
import java.util.Map;
<#else>
using System.Xml.Linq;
</#if>
using ${project_namespace}.Entity;
using ${project_namespace}.Harmony.Util;


namespace ${project_namespace}.Fixture
{
    public class ${curr.name}DataLoader : FixtureBase<${curr.name?cap_first}>
    {
        /** ${curr.name?cap_first}DataLoader name. */
        private const String FILE_NAME = "${curr.name}";
        
        <#list curr_fields as field>${AdapterUtils.fixtureConstantsFieldAdapter(field, 1)}</#list>
        
        /** ${curr.name?cap_first}DataLoader instance (Singleton). */
        private static ${curr.name?cap_first}DataLoader instance;
    
        /**
         * Get the ${curr.name?cap_first}DataLoader singleton.
         * @param ctx The context
         * @return The dataloader instance
         */
        public static ${curr.name?cap_first}DataLoader getInstance(DataContext ctx) {
            if (instance == null) {
                instance = new ${curr.name?cap_first}DataLoader(ctx);
            }
            
            return instance;
        }
    
        /**
         * Constructor.
         * @param ctx The context
         */
        private ${curr.name?cap_first}DataLoader(DataContext ctx) : base(ctx)
        {
            
        }
    
    
        <#if fixtureType=="xml">
        public override ${curr.name} ExtractItem(XElement element) {
            ${curr.name?cap_first} ${curr.name?uncap_first} = new ${curr.name?cap_first}();
    
            return this.ExtractItem(element, ${curr.name?uncap_first});
        }
    
        /**
         * Extract an entity from a fixture element (XML).
         * @param element The element to be extracted
         * @param ${curr.name?uncap_first} Item in which to store the extracted data
         * @return A ${curr.name} entity
         */
        public ${curr.name} ExtractItem(XElement element,
                                    ${curr.name} ${curr.name?uncap_first}) {
            XElement subElement = null;
        <#list curr_fields as field>${AdapterUtils.xmlExtractFieldAdapter(curr.name?uncap_first, field, curr, 3)}</#list>
            
            <#if InheritanceUtils.isExtended(curr)>
            ${curr.inheritance.superclass.name}DataLoader inheritanceDataLoader =
                    ${curr.inheritance.superclass.name}DataLoader.getInstance(this.ctx);
            inheritanceDataLoader.ExtractItem(element, ${curr.name?uncap_first});
            </#if>
    
        <#elseif fixtureType=="yml">
        public ${curr.name} extractItem(Map<?, ?> columns) {
            ${curr.name?cap_first} ${curr.name?uncap_first} =
                    new ${curr.name?cap_first}();
    
            return this.ExtractItem(columns, ${curr.name?uncap_first});
        }
        /**
         * Extract an entity from a fixture element (YML).
         * @param columns Columns to extract
         * @param ${curr.name?uncap_first} Entity to extract
         * @return A ${curr.name} entity
         */
        public ${curr.name} ExtractItem(Map<?, ?> columns,
                    ${curr.name?cap_first} ${curr.name?uncap_first}) {
            <#if InheritanceUtils.isExtended(curr)>
            ${curr.inheritance.superclass.name}DataLoader.getInstance(this.ctx).extractItem(columns, ${curr.name?uncap_first});
    
            </#if>
            <#list curr_fields as field>
                <#if (!field.internal)>
                    <#if (!field.relation??)>
                        <#if field.harmony_type?lower_case=="datetime">
            ${curr.name?uncap_first}.set${field.name?cap_first}(this.parseDateTimeField(columns, ${NamingUtils.fixtureAlias(field)}));
                        <#elseif field.harmony_type?lower_case=="enum">
                            <#assign enumType = enums[field.enum.targetEnum] />
                            <#if (enumType.id??)>
                                <#assign idEnumType = FieldsUtils.getJavaType(enumType.fields[enumType.id])?lower_case />
                                <#if (idEnumType?lower_case == "int" || idEnumType?lower_case == "integer") >
            ${curr.name?uncap_first}.set${field.name?cap_first}(${field.harmony_type}.fromValue(this.parseField(columns, ${NamingUtils.fixtureAlias(field)}, Integer.class)));
                                <#else>
            ${curr.name?uncap_first}.set${field.name?cap_first}(${field.harmony_type}.fromValue(this.parseField(columns, ${NamingUtils.fixtureAlias(field)}, String.class)));
                                </#if>
                            <#else>
            ${curr.name?uncap_first}.set${field.name?cap_first}(${field.harmony_type}.valueOf(this.parseField(columns, ${NamingUtils.fixtureAlias(field)}, String.class)));
                                
                            </#if>
                        <#else>
                            <#if (field.harmony_type == "double" || field.harmony_type?lower_case == "char" || field.harmony_type?lower_case == "float" || field.harmony_type?lower_case == "byte" || field.harmony_type?lower_case == "short" || field.harmony_type?lower_case == "int" || field.harmony_type?lower_case == "boolean")>
            ${curr.name?uncap_first}.set${field.name?cap_first}(this.parse${field.harmony_type?cap_first}Field(columns, ${NamingUtils.fixtureAlias(field)}));
                            <#elseif (field.harmony_type?lower_case == "character")>
            ${curr.name?uncap_first}.set${field.name?cap_first}(this.parseField(columns, ${NamingUtils.fixtureAlias(field)}, String.class).charAt(0));
                            <#else>
            ${curr.name?uncap_first}.set${field.name?cap_first}(this.parseField(columns, ${NamingUtils.fixtureAlias(field)}, ${field.harmony_type?cap_first}.class));
                            </#if>
                        </#if>
                    <#else>
                        <#if (field.relation.type == "OneToOne" || field.relation.type == "ManyToOne")>
            ${curr.name?uncap_first}.set${field.name?cap_first}(this.parseSimpleRelationField(columns, ${NamingUtils.fixtureAlias(field)}, ${field.relation.targetEntity}DataLoader.getInstance(this.ctx)));
                            <#if (field.relation.inversedBy??)>
            if (${curr.name?uncap_first}.get${field.name?cap_first}() != null) {
                ${curr.name?uncap_first}.get${field.name?cap_first}().get${field.relation.inversedBy?cap_first}().add(${curr.name?uncap_first});
            }
                            </#if>
                        <#else>
            ${curr.name?uncap_first}.set${field.name?cap_first}(this.parseMultiRelationField(columns, ${NamingUtils.fixtureAlias(field)}, ${field.relation.targetEntity}DataLoader.getInstance(this.ctx)));
                            <#if (field.relation.type == "OneToMany" && field.relation.mappedBy?? && field.relation.mappedBy?? && !entities[field.relation.targetEntity].fields[field.relation.mappedBy].internal)>
            for (${field.relation.targetEntity} related : ${curr.name?uncap_first}.get${field.name?cap_first}()) {
                related.set${field.relation.mappedBy?cap_first}(${curr.name?uncap_first});
            }
                            </#if>
                        </#if>
                    </#if>
                </#if>
            </#list>
        </#if>
    
            return ${curr.name?uncap_first};
        }
        /**
         * Loads ${curr.name?cap_first}s into the DataManager.
         * @param manager The DataManager
         */
        public override void Load(DataManager dataManager) {
            foreach (${curr.name?cap_first} ${curr.name?uncap_first} in this.items.Values) {
                int id = dataManager.persist(${curr.name?uncap_first});
                <#list curr_ids as id><#if id.strategy == "IDENTITY">
                ${curr.name?uncap_first}.${id.name?cap_first} = id;
                </#if></#list>
    
            }
            dataManager.flush();
        }
    
        /**
         * Give priority for fixtures insertion in database.
         * 0 is the first.
         * @return The order
         */
        public override int GetOrder() {
            return 0;
        }
    
        /**
         * Get the fixture file name.
         * @return A String representing the file name
         */
        public override String GetFixtureFileName() {
            return FILE_NAME;
        }
        
        protected override ${curr.name} get(String key) {
            ${curr.name} result;
            if (this.items.ContainsKey(key)) {
                result = this.items[key];
            }
            <#if (curr.inheritance?? && curr.inheritance.subclasses??)>
                <#list curr.inheritance.subclasses as subclass>
            else if (${subclass.name}DataLoader.getInstance(this.ctx).items.ContainsKey(key)) {
                result = ${subclass.name}DataLoader.getInstance(this.ctx).items[key];
            }
                </#list>
            </#if>
            else {
                result = null;
            }
            return result;
        }
    }
}