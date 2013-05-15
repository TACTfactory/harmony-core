<#function typeConvert type>
	<#assign ret = type />
	<#if type=="login" || type=="password">
		<#assign ret = "string" />
	</#if>
	<#return ret />
</#function>

<#assign curr = entities[current_entity] />
${project_name?cap_first}\ApiBundle\Entity\${curr.name}:
    type: entity
    table: null
    repositoryClass: ${project_name?cap_first}\ApiBundle\Entity\${curr.name}Repository
    fields:
<#list curr.fields as field>
	<#if !field.relation?? && !field.internal && field.name!="id">
        ${field.name}:
            type: ${typeConvert(field.type)}
		<#if field.id>
            id: true
            generator: 
                strategy: AUTO
		</#if>
	</#if>
</#list>
<#assign putOneToMany=false />
<#list curr.relations as field>
	<#if !field.internal && field.relation.type=="OneToMany">
		<#if !putOneToMany>

    oneToMany: <#assign putOneToMany=true />
		</#if>
        ${field.name}:
            targetEntity: ${field.relation.targetEntity}
            mappedBy: ${field.relation.mappedBy}
	</#if>
</#list>
<#assign putManyToOne=false />
<#list curr.relations as field>
	<#if !field.internal && field.relation.type=="ManyToOne">
		<#if !putManyToOne>

    manyToOne: <#assign putManyToOne=true />
		</#if>
        ${field.name}:
            targetEntity: ${field.relation.targetEntity}
            <#if field.relation.inversedBy??>inversedBy: ${field.relation.inversedBy}</#if>
	</#if>
</#list>

    lifecycleCallbacks: {  }