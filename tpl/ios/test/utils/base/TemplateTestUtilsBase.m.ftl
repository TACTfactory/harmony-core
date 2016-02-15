<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign inherited = false />
<#if ((joinedInheritance || singleTabInheritance) && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??) >
    <#assign inherited = true />
</#if>
<@header?interpret />

#import "DateUtils.h"
#import "${curr.name}TestUtilsBase.h"
<#assign importList = [] />
<#list curr.relations as relation>
    <#if !relation.internal>
        <#if !Utils.isInArray(importList, relation.relation.targetEntity)>
<#if (dataLoader?? && dataLoader) || (relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany")>#import "${relation.relation.targetEntity?cap_first}.h"</#if>
#import "${relation.relation.targetEntity}TestUtils.h"
            <#assign importList = importList + [relation.relation.targetEntity] />
        </#if>
    </#if>
</#list>
<#if (InheritanceUtils.isExtended(curr))>#import "${curr.inheritance.superclass.name}TestUtils.h"</#if>
<#list curr.fields?values as field>
    <#if field.harmony_type?lower_case == "enum">
        <#assign enumClass = enums[field.enum.targetEnum] />
#import "${enumClass.name}.h"
    </#if>
</#list>

@implementation ${curr.name?cap_first}TestUtilsBase

    // If you have enums, you may have to override this method to generate the random enums values
+ (${curr.name?cap_first}*) generateRandom {
    ${curr.name?cap_first}* ${curr.name?uncap_first} = [${curr.name?cap_first} new];
        <#list curr.relations as relation> <#if relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne">
    ${relation.relation.targetEntity}* ${relation.relation.targetEntity?uncap_first} = [${relation.relation.targetEntity}TestUtils generateRandom];
        </#if></#list>
        <#if (inherited)>
    ${curr.inheritance.superclass.name?cap_first}* ${curr.inheritance.superclass.name?uncap_first} = [${curr.inheritance.superclass.name?cap_first}TestUtils generateRandom];
            <#list entities[curr.inheritance.superclass.name].fields?values as field>
                <#if !field.internal>
    ${curr.name?uncap_first}.${field.name?uncap_first} = ${curr.inheritance.superclass.name?uncap_first}.${field.name?uncap_first};
                </#if>
            </#list>
        </#if>

        <#list curr.fields?values as field>
            <#if !field.internal>
                <#if field.harmony_type?lower_case != "relation">
                    <#switch FieldsUtils.getObjectiveType(field)?lower_case>
                        <#case "string">
    ${curr.name?uncap_first}.${field.name?uncap_first} = [NSString stringWithFormat:@"value_%@", @"nothing"];
                            <#break />
                        <#case "int">
    ${curr.name?uncap_first}.${field.name?uncap_first} = arc4random_uniform(1000);
                            <#break />
                        <#case "nsnumber">
    ${curr.name?uncap_first}.${field.name?uncap_first} = [NSNumber numberWithInt:arc4random_uniform(1000)];
                            <#break />
                        <#case "boolean">
    ${curr.name?uncap_first}.${field.name?uncap_first} = true;
                            <#break />
                        <#case "double">
    ${curr.name?uncap_first}.${field.name?uncap_first} = 1;
                            <#break />
                        <#case "float">
    ${curr.name?uncap_first}.${field.name?uncap_first} = 0.1
                            <#break />
                        <#case "short">
    ${curr.name?uncap_first}.${field.name?uncap_first} = 3;
                            <#break />
                        <#case "char">
                        <#case "character">
    ${curr.name?uncap_first}.${field.name?uncap_first} = 'a';
                            <#break />
                        <#case "byte">
    ${curr.name?uncap_first}.${field.name?uncap_first} = 0;
                            <#break />
                        <#case "datetime">
                            <#if field.harmony_type?lower_case=="date">
    ${curr.name?uncap_first}.${field.name?uncap_first} = [DateUtils isoStringToDate:@"2013-11-25T23:00:43.0+02:00"];
                            <#elseif field.harmony_type?lower_case=="time">
    ${curr.name?uncap_first}.${field.name?uncap_first} = [DateUtils isoStringToDate:@"2013-11-25T23:00:43.0+02:00"];
                            <#elseif field.harmony_type?lower_case=="datetime">
    ${curr.name?uncap_first}.${field.name?uncap_first} = [DateUtils isoStringToDate:@"2013-11-25T23:00:43.0+02:00"];
                            </#if>
                            <#break />
                        <#case "enum">
    ${curr.name?uncap_first}.${field.name?uncap_first} = 0;
                            <#break />
                        <#default>
    //TODO : Manage field type : ${field.harmony_type} / ${FieldsUtils.getObjectiveType(field)}
                    </#switch>
                <#else>
                    <#if field.relation.type=="ManyToOne" || field.relation.type=="OneToOne" >
    ${curr.name?uncap_first}.${field.name?uncap_first} = ${field.relation.targetEntity?uncap_first};
                    <#elseif field.relation.type=="OneToMany" || field.relation.type=="ManyToMany" >
    ${curr.name?uncap_first}.${field.name?uncap_first} = [NSArray new];
                    <#else>
    ${curr.name?uncap_first}.${field.name?uncap_first} = [${field.relation.targetEntity?cap_first} new];
                    </#if>
                </#if>
            </#if>
        </#list>

    return ${curr.name?uncap_first};
}

+ (bool) equals:(${curr.name?cap_first}*) ${curr.name?uncap_first}1
    withCompare:(${curr.name?cap_first}*) ${curr.name?uncap_first}2 {

        <#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
    bool result = [${curr.inheritance.superclass.name}TestUtils equals:${curr.name?uncap_first}1 withCompare:${curr.name?uncap_first}2];
        <#else>
    bool result = false;
        </#if>

    NSAssert(${curr.name?uncap_first}1, @"${curr.name?cap_first} is null");
    NSAssert(${curr.name?uncap_first}2, @"Compare ${curr.name?cap_first} is null");

    if (${curr.name?uncap_first}1 && ${curr.name?uncap_first}2){
    <#list curr.fields?values as field>
        <#if !field.internal && !field.columnResult>
            <#if field.harmony_type?lower_case != "relation">
                <#switch FieldsUtils.getObjectiveType(field)?lower_case>
                    <#case "string">
        NSAssert([${curr.name?uncap_first}1.${field.name?uncap_first} isEqualToString:${curr.name?uncap_first}2.${field.name?uncap_first}],  @"${field.name?uncap_first} are not equals");
                    <#break />
                    <#case "datetime">
        NSAssert([${curr.name?uncap_first}1.${field.name?uncap_first} isEqualToDate:${curr.name?uncap_first}2.${field.name?uncap_first}],  @"${field.name?uncap_first} are not equals");
                        <#break />
                    <#case "integer">
                    <#case "float">
                    <#case "long">
                    <#case "double">
                    <#case "float">
                    <#case "char">
                    <#case "byte">
                    <#case "short">
                    <#case "int">
                    <#case "boolean">
        NSAssert(${curr.name?uncap_first}1.${field.name?uncap_first} == ${curr.name?uncap_first}2.${field.name?uncap_first}, @"${field.name?uncap_first} are not equals");
                        <#break />
                    <#default>
        //TODO : Manage field type : ${field.harmony_type} / ${FieldsUtils.getObjectiveType(field)}
                </#switch>
            <#else>
        if (${curr.name?uncap_first}1.${field.name?uncap_first}
                && ${curr.name?uncap_first}2.${field.name?uncap_first}) {
            <#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
            NSAssert(${curr.name?uncap_first}1.${field.name?uncap_first}.${entities[field.relation.targetEntity].ids[0].name?uncap_first} ==
                    ${curr.name?uncap_first}2.${field.name?uncap_first}.${entities[field.relation.targetEntity].ids[0].name?uncap_first}, @"author are not equals");
            <#else>
            assert(${curr.name?uncap_first}1.${field.name?uncap_first}.count ==
                ${curr.name?uncap_first}2.${field.name?uncap_first}.count);
            for (${field.relation.targetEntity?cap_first} *${field.name?uncap_first}1 in ${curr.name?uncap_first}1.${field.name?uncap_first}) {
                bool found = false;
                for (${field.relation.targetEntity?cap_first} *${field.name?uncap_first}2 in ${curr.name?uncap_first}2.${field.name?uncap_first}) {
                    <#assign target = entities[field.relation.targetEntity] />
                    if (<#list IdsUtils.getAllIdsGetters(target) as refId>${field.name?uncap_first}1.${refId}<#if MetadataUtils.isPrimitive(target.ids[refId_index])> == <#else>.equals(</#if>${field.name?uncap_first}2.${refId}<#if !MetadataUtils.isPrimitive(target.ids[refId_index])>)</#if><#if refId_has_next>
                        && </#if></#list>) {
                        found = true;
                    }
                }

            NSString* msg = [NSString stringWithFormat:
                            @"Couldn't find associated ${field.name} (<#list target.ids as id>${id.name} = %d<#if id_has_next>, </#if></#list>) in ${curr.name} (<#list curr_ids as id>${id.name} = %d<#if id_has_next>,</#if></#list>)",
                            <#list IdsUtils.getAllIdsGetters(target) as id>${field.name?uncap_first}1.${id},
                            </#list><#list IdsUtils.getAllIdsGetters(curr) as id>${curr.name?uncap_first}1.${id}<#if id_has_next>,
                            </#if></#list>];

            NSAssert(found, msg);
            }
                </#if>
        }
            </#if>
        </#if>
    </#list>
    }

    return result;
}

@end
