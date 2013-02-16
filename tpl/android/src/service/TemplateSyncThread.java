<#function hasOnlyRecursiveRelations entity>
	<#list entity.relations as relation>
		<#if relation.relation.targetEntity!=entity.name> 
			<#return false>
		</#if>
	</#list>
	<#return true>
</#function>
<#function getZeroRelationsEntities>
	<#assign ret = [] />
	<#list entities?values as entity>
		<#if (entity.fields?size!=0 && hasOnlyRecursiveRelations(entity))>
			<#assign ret = ret + [entity.name]>
		</#if>
	</#list>
	<#return ret />
</#function>
<#function isInArray array val>
	<#list array as val_ref>
		<#if val_ref==val>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function isOnlyDependantOf entity entity_list>
	<#list entity.relations as rel>
		<#if rel.relation.type=="ManyToOne">
			<#if !isInArray(entity_list, rel.relation.targetEntity)>
				<#return false />
			</#if>
		</#if>	
	</#list>
	<#return true />
</#function>
<#function orderEntitiesByRelation>
	<#assign ret = getZeroRelationsEntities() />
	<#assign maxLoop = entities?size />
	<#list 1..maxLoop as i>
		<#list entities?values as entity>	
			<#if (entity.fields?size>0)>
				<#if !isInArray(ret, entity.name)>
					<#if isOnlyDependantOf(entity, ret)>
						<#assign ret = ret + [entity.name] />
					</#if>
				</#if>
			</#if>
		</#list>
	</#list>
	<#return ret>
</#function>
<#assign orderedEntities = orderEntitiesByRelation() />
<#assign syncEntities = [] />
<#list orderedEntities as entityName>
	<#if (entities[entityName].options.sync??)>
		<#assign syncEntities = syncEntities + [entities[entityName]] />
	</#if>
</#list>

package ${service_namespace};

import android.content.Context;

<#list syncEntities as entity>
import ${data_namespace}.${entity.name?cap_first}SQLiteAdapter;
import ${data_namespace}.${entity.name?cap_first}WebServiceClientAdapter;
import ${entity_namespace}.${entity.name?cap_first};
</#list>


public class ${project_name?cap_first}SyncThread extends Thread{
	private Context context;
	
	<#list syncEntities as entity>
	private ${entity.name?cap_first}SQLiteAdapter ${entity.name?uncap_first}SQLAdapter;
	private ${entity.name?cap_first}WebServiceClientAdapter ${entity.name?uncap_first}WSAdapter;
	
	</#list>
	
	public ${project_name?cap_first}SyncThread(Context ctx){
		this.context = ctx;
		<#list syncEntities as entity>
		this.${entity.name?uncap_first}SQLAdapter = new ${entity.name?cap_first}SQLiteAdapter(this.context);
		this.${entity.name?uncap_first}WSAdapter = new ${entity.name?cap_first}WebServiceClientAdapter(this.context);
		
		</#list>
	}
	
	@Override
	public void run() {			
		<#list syncEntities as entity>
		new ${project_name?cap_first}SyncService<${entity.name?cap_first}>(this.context, this.${entity.name?uncap_first}SQLAdapter, this.${entity.name?uncap_first}WSAdapter).run();
		</#list>
	}
}
