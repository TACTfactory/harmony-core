<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
<#assign curr = entities[current_entity] />
<#assign curr_fields = (curr.name) />

#import <Foundation/Foundation.h>
${ImportUtils.importRelatedEntitiesHeader(curr, true)}
<#if (InheritanceUtils.isExtended(curr))>#import "${curr.inheritance.superclass.name}.h" </#if>

@interface ${curr.name} : <#if (InheritanceUtils.isExtended(curr))>${curr.inheritance.superclass.name} <#else>NSObject </#if>

<#list curr.fields?values as field>
    <#if !field.internal>
/** ${field.name} */
        <#if field.name == "serverId">
@property (nonatomic, strong) NSNumber *${field.name};
        <#elseif field.primitive || (field.harmony_type?lower_case == "int")>
@property (nonatomic) ${FieldsUtils.convertToObjectiveType(field)} ${field.name};
        <#elseif (field.harmony_type?lower_case == "relation") >
@property (nonatomic) ${FieldsUtils.convertToObjectiveType(field)} *${field.name};
        <#elseif (field.harmony_type?lower_case == "enum") >
@property (nonatomic) int ${field.name};
        <#elseif (field.harmony_type?lower_case != "boolean") && (field.harmony_type?lower_case != "char")>
@property (nonatomic, strong) ${FieldsUtils.convertToObjectiveType(field)} *${field.name};
        <#else>
@property (nonatomic) ${FieldsUtils.convertToObjectiveType(field)} ${field.name};
        </#if>
    </#if>
</#list>

@end
