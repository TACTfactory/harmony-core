<#assign curr = entities[current_entity]>
<#function alias name>
	<#return "JSON_"+name?upper_case>
</#function>

<#function jsonID name>
	<#if (name?length>2)>
		<#return name?substring(0,3)>
	<#else>
		<#return name>
	</#if>
</#function>

<#function typeToJsonType field>
	<#if !field.relation??>
		<#if field.type=="int" || field.type=="integer">
			<#return "Int">
		<#elseif field.type=="float">
			<#return "Float">
		<#elseif field.type=="double">
			<#return "Double">
		<#elseif field.type=="long">
			<#return "Long">
		<#else>
			<#return "String">
		</#if>
	<#else>
		<#if field.relation.type=="ManyToMany"||field.relation.type=="OneToMany">
			<#return "JSONObject">
		<#else>
			<#return "JSONObject">
		</#if>
	</#if>
</#function>

<#function getFormatter datetype>
	<#assign ret="ISODateTimeFormat.">
	<#if datetype?lower_case=="datetime">
		<#assign ret=ret+"dateTime()">
	<#elseif datetype?lower_case=="time">
		<#assign ret=ret+"dateTime()">
	<#elseif datetype?lower_case=="date">
		<#assign ret=ret+"dateTime()">
	</#if>
	<#return ret>
</#function>

<#function isRestEntity restArray entityName>
	<#list restArray as restEntity>
		<#if restEntity.name==entityName>
			<#return true>
		</#if>
	</#list>
	<#return false>
</#function>

package ${curr.data_namespace};

import ${curr.namespace}.entity.${curr.name};

import org.json.*;

import java.util.List;
import java.util.ArrayList;

import android.util.Log;

import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

<#list curr.relations as relation>
	<#if isRestEntity(options.rest.entities?values, relation.relation.targetEntity)>
import ${curr.namespace}.entity.${relation.relation.targetEntity};
	</#if>
</#list>

public class ${curr.name}WebServiceClientAdapterBase {
	private static final String TAG = "${curr.name}WebServiceClientAdapterBase";

	<#list curr.fields as field>
		<#if !field.internal>
	private static final String ${alias(field.name)} = "${jsonID(field.name)}";
		</#if>
	</#list>
	
	/**
	 *
	 *
	 */
	protected int get(${curr.name} ${curr.name?lower_case}){
		return 0;
	}

	/**
	 *
	 *
	 */
	protected int insert(${curr.name} ${curr.name?lower_case}){
		return 0;
	}

	/**
	 *
	 *
	 */
	protected int update(${curr.name} ${curr.name?lower_case}){
		return 0;
	}

	/**
	 *
	 *
	 */
	protected int delete(${curr.name} ${curr.name?lower_case}){
		return 0;
	}

	/**
	 *
	 *
	 */
	public static boolean extract(JSONObject json, ${curr.name} ${curr.name?lower_case}){
		boolean result = false;
		
		int id = json.optInt("id", 0);

		if (id != 0) {
			<#list curr.fields as field>
				<#if !field.internal>
					<#if !field.relation??>
						<#if field.type?lower_case=="date"||field.type?lower_case=="datetime"||field.type?lower_case=="time">
			DateTimeFormatter ${field.name?uncap_first}Formatter = ${getFormatter(field.type)};
			${curr.name?lower_case}.set${field.name?cap_first}(${field.name?uncap_first}Formatter.parseDateTime(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${curr.name?lower_case}.get${field.name?cap_first}().toString())));	
						<#else>
			${curr.name?lower_case}.set${field.name?cap_first}(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${curr.name?lower_case}.get${field.name?cap_first}()));	
						</#if>
					<#else>
						<#if isRestEntity(options.rest.entities?values, field.relation.targetEntity)>
							<#if field.relation.type=="OneToMany" || field.relation.type=="ManyToMany">
			ArrayList<${field.relation.targetEntity}> ${field.name?uncap_first} = new ArrayList<${field.relation.targetEntity}>();
			${field.relation.targetEntity}WebServiceClientAdapter.extract${field.relation.targetEntity}s(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
			${curr.name?lower_case}.set${field.name?cap_first}(${field.name?uncap_first});
							<#else>
			${field.relation.targetEntity} ${field.name?uncap_first} = new ${field.relation.targetEntity}();
			${field.relation.targetEntity}WebServiceClientAdapter.extract(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
			${curr.name?lower_case}.set${field.name?cap_first}(${field.name?uncap_first});
							</#if>
						</#if>
					</#if>

				</#if>
			</#list>
			
			result = true;
		}
		
		return result;
	}

	/**
	 *
	 *
	 */
	public static int extract${curr.name}s(JSONObject json, List<${curr.name}> ${curr.name?lower_case}s) throws JSONException{
		JSONArray itemArray = json.optJSONArray("${curr.name}s");
		
		int result = -1;
		
		if (itemArray != null) {
			int count = itemArray.length();			
			
			for (int i = 0 ; i < count; i++) {
				JSONObject json${curr.name} = itemArray.getJSONObject(i);
				
				${curr.name} ${curr.name?lower_case} = new ${curr.name}();
				if (extract(json${curr.name}, ${curr.name?lower_case})){
					synchronized (${curr.name?lower_case}s) {
						${curr.name?lower_case}s.add(${curr.name?lower_case});
					}
				}
			}
		}
		
		if (!json.isNull("Meta")){
			JSONObject meta = json.optJSONObject("Meta");
			result = meta.optInt("nbt",0);
		}
		
		return result;
	}

	/**
	 *	
	 *
	 */
	public static JSONObject ${curr.name?uncap_first}ToJson(${curr.name} ${curr.name?uncap_first}){
		JSONObject params = new JSONObject();
		try{
			<#list curr.fields as field>
				<#if !field.internal>
					<#if !field.relation??>
						<#if field.type?lower_case=="date" || field.type?lower_case=="time" || field.type?lower_case=="datetime">
			params.put(${alias(field.name)}, ${curr.name?lower_case}.get${field.name?cap_first}().toString());
						<#else>
			params.put(${alias(field.name)}, ${curr.name?lower_case}.get${field.name?cap_first}());
						</#if>
					<#else>
						<#if isRestEntity(options.rest.entities?values, field.relation.targetEntity)>
			params.put(${alias(field.name)}, ${field.relation.targetEntity?cap_first}WebServiceClientAdapter.${field.relation.targetEntity?uncap_first}ToJson(${curr.name?lower_case}.get${field.name?cap_first}()));
						</#if>
					</#if>
				</#if>
			</#list>
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return params;
	}
}
