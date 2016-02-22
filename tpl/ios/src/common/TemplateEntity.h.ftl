<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
<#assign curr = entities[current_entity] />
<#assign curr_fields = (curr.name) />
<#assign sync = curr.options.sync?? />

#import <Foundation/Foundation.h>
${ImportUtils.importRelatedEntitiesHeader(curr, true)}
<#if (InheritanceUtils.isExtended(curr))>#import "${curr.inheritance.superclass.name}.h"</#if>
<#list curr.fields?values as field>
    <#if (field.harmony_type?lower_case == "enum") >
#import "${field.name?cap_first}.h"
     </#if>
</#list>
<#if (sync)>#import "EntityBase.h"</#if>

@interface ${curr.name} : <#if (InheritanceUtils.isExtended(curr))>${curr.inheritance.superclass.name}<#elseif (sync)>EntityBase<#else>NSObject</#if>

<#list curr.fields?values as field>
    <#if field.name != "serverId" && field.name != "sync_uDate" && field.name != "sync_dTag" && field.name != "uuid" && !field.internal>
/** ${field.name} */
            <#if field.primitive || (field.harmony_type?lower_case == "int")>
@property (nonatomic) ${FieldsUtils.convertToObjectiveType(field)} ${field.name};
            <#elseif (field.harmony_type?lower_case == "relation")>
@property (nonatomic) ${FieldsUtils.convertToObjectiveType(field)} *${field.name};
            <#elseif (field.harmony_type?lower_case == "enum") >
@property (nonatomic) ${field.name?cap_first} ${field.name};
            <#elseif (field.harmony_type?lower_case != "boolean") && (field.harmony_type?lower_case != "char")>
@property (nonatomic, strong) ${FieldsUtils.convertToObjectiveType(field)} *${field.name};
            <#else>
@property (nonatomic) ${FieldsUtils.convertToObjectiveType(field)} ${field.name};
            </#if>
    </#if>
</#list>

@end
