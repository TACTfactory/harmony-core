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

package ${data_namespace};

import ${namespace}.entity.${name};

import org.json.*;

import java.util.List;
import java.util.ArrayList;

import android.util.Log;

import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

<#list relations as relation>
import ${namespace}.entity.${relation.relation.targetEntity};
</#list>

public class ${name}WebServiceClientAdapterBase {
	private static final String TAG = "${name}WebServiceClientAdapterBase";

	<#list fields as field>
		<#if !field.internal>
	private static final String ${alias(field.name)} = "${jsonID(field.name)}";
		</#if>
	</#list>
	
	/**
	 *
	 *
	 */
	protected int get(${name} ${name?lower_case}){
		JSONObject json = new JSONObject();
		return 0;
	}

	/**
	 *
	 *
	 */
	protected int insert(${name} ${name?lower_case}){
		return 0;
	}

	/**
	 *
	 *
	 */
	protected int update(${name} ${name?lower_case}){
		return 0;
	}

	/**
	 *
	 *
	 */
	protected int delete(${name} ${name?lower_case}){
		return 0;
	}

	/**
	 *
	 *
	 */
	public static boolean extract(JSONObject json, ${name} ${name?lower_case}){
		boolean result = false;
		
		int id = json.optInt("id", 0);

		if (id != 0) {
			<#list fields as field>
				<#if !field.internal>
					<#if !field.relation??>
						<#if field.type?lower_case=="date"||field.type?lower_case=="datetime"||field.type?lower_case=="time">
			DateTimeFormatter ${field.name?uncap_first}Formatter = ${getFormatter(field.type)};
			${name?lower_case}.set${field.name?cap_first}(${field.name?uncap_first}Formatter.parseDateTime(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${name?lower_case}.get${field.name?cap_first}().toString())));	
						<#else>
			${name?lower_case}.set${field.name?cap_first}(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${name?lower_case}.get${field.name?cap_first}()));	
						</#if>
					<#else>
						<#if field.relation.type=="OneToMany" || field.relation.type=="ManyToMany">
			/*ArrayList<${field.relation.targetEntity}> ${field.name?uncap_first} = new ArrayList<${field.relation.targetEntity}>();
			${field.relation.targetEntity}WebServiceClientAdapter.extract${field.relation.targetEntity}s(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
			${name?lower_case}.set${field.name?cap_first}(${field.name?uncap_first});*/
						<#else>
			/*${field.relation.targetEntity} ${field.name?uncap_first} = new ${field.relation.targetEntity}();
			${field.relation.targetEntity}WebServiceClientAdapter.extract(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
			${name?lower_case}.set${field.name?cap_first}(${field.name?uncap_first});*/
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
	public static int extract${name}s(JSONObject json, List<${name}> ${name?lower_case}s) throws JSONException{
		JSONArray itemArray = json.optJSONArray("${name}s");
		
		int result = -1;
		
		if (itemArray != null) {
			int count = itemArray.length();			
			
			for (int i = 0 ; i < count; i++) {
				JSONObject json${name} = itemArray.getJSONObject(i);
				
				${name} ${name?lower_case} = new ${name}();
				if (extract(json${name}, ${name?lower_case})){
					synchronized (${name?lower_case}s) {
						${name?lower_case}s.add(${name?lower_case});
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
	public static JSONObject ${name?uncap_first}ToJson(${name} ${name?uncap_first}){
		JSONObject params = new JSONObject();
		try{
			<#list fields as field>
				<#if !field.internal>
					<#if field.type?lower_case=="date" || field.type?lower_case=="time" || field.type?lower_case=="datetime">
			params.put(${alias(field.name)}, ${name?lower_case}.get${field.name?cap_first}().toString());
					<#else>
			params.put(${alias(field.name)}, ${name?lower_case}.get${field.name?cap_first}());
					</#if>
				</#if>
			</#list>
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return params;
	}
}
