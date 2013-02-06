<#assign curr = entities[current_entity] />
<#function alias name>
	<#return "JSON_"+name?upper_case />
</#function>
<#function typeToJsonType field>
	<#if (!field.relation??)>
		<#if (field.type=="int" || field.type=="integer")>
			<#return "Int" />
		<#elseif (field.type=="float")>
			<#return "Float" />
		<#elseif (field.type=="double")>
			<#return "Double" />
		<#elseif (field.type=="long")>
			<#return "Long" />
		<#elseif (field.type=="boolean")>
			<#return "Boolean" />
		<#else>
			<#return "String" />
		</#if>
	<#else>
		<#if (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")>
			<#return "JSONObject" />
		<#else>
			<#return "JSONObject" />
		</#if>
	</#if>
</#function>
<#function extract field>
	<#if (!field.internal)>
		<#if (!field.relation??)>
			<#if (field.type=="date"||field.type=="datetime"||field.type=="time")>
		DateTimeFormatter ${field.name?uncap_first}Formatter = ${getFormatter(field.type)};
		${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first}Formatter.parseDateTime(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${curr.name?uncap_first}.get${field.name?cap_first}().toString())));	
			<#elseif (field.type=="boolean")>
		${curr.name?uncap_first}.set${field.name?cap_first}(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${curr.name?uncap_first}.is${field.name?cap_first}()));	
			<#else>
		${curr.name?uncap_first}.set${field.name?cap_first}(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${curr.name?uncap_first}.get${field.name?cap_first}()));	
			</#if>
		<#else>
			<#if (isRestEntity(field.relation.targetEntity))>
				<#if (field.relation.type=="OneToMany" || field.relation.type=="ManyToMany")>
		ArrayList<${field.relation.targetEntity}> ${field.name?uncap_first} = new ArrayList<${field.relation.targetEntity}>();
		try{
		${field.relation.targetEntity}WebServiceClientAdapter.extract${field.relation.targetEntity}s(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
		${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first});
		}catch(JSONException e){
		Log.e(TAG, e.getMessage());
		}
				<#else>
		${field.relation.targetEntity} ${field.name?uncap_first} = new ${field.relation.targetEntity}();
		${field.relation.targetEntity}WebServiceClientAdapter.extract(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
		${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first});
				</#if>
			</#if>
		</#if>
	</#if>
</#function>
<#function getFormatter datetype>
	<#assign ret="ISODateTimeFormat." />
	<#if (datetype?lower_case=="datetime")>
		<#assign ret=ret+"dateTimeNoMillis()" />
	<#elseif (datetype?lower_case=="time")>
		<#assign ret=ret+"dateTimeNoMillis()" />
	<#elseif (datetype?lower_case=="date")>
		<#assign ret=ret+"dateTimeNoMillis()" />
	</#if>
	<#return ret />
</#function>
<#function isRestEntity entityName>
	<#return entities[entityName].options.rest?? />
</#function>
<#function isInArray array var>
	<#list array as item>
		<#if (item==var)>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
package ${curr.data_namespace}.base;


<#assign importDate = false />
<#list curr.fields as field>
	<#if !importDate && (field.type=="date" || field.type=="time" || field.type=="datetime")>
import org.joda.time.format.DateTimeFormatter;
		<#assign importDate = true />
	</#if>
</#list>
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import ${data_namespace}.*;
import ${curr.namespace}.entity.${curr.name};
import ${data_namespace}.RestClient.Verb;

import org.json.*;

import java.util.List;

import android.util.Log;
import android.content.Context;

<#assign import_array = [curr.name] />
<#assign alreadyImportArrayList=false />
<#list curr.relations as relation>
	<#if (isRestEntity(relation.relation.targetEntity))>
		<#if (!alreadyImportArrayList && (relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany"))>
import java.util.ArrayList;
			<#assign alreadyImportArrayList=true />
		</#if>
		<#if (!isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
import ${curr.namespace}.entity.${relation.relation.targetEntity};
		</#if>
	</#if>
</#list>
<#if (curr.options.sync??)>
import ${curr.namespace}.entity.base.EntityBase;
	<#if !alreadyImportArrayList>
import java.util.ArrayList;
	</#if>
</#if>

/**
 * 
 * b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}WebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class ${curr.name}WebServiceClientAdapterBase extends WebServiceClientAdapterBase{
	private static final String TAG = "${curr.name}WSClientAdapter";

	private static final String ${alias(curr.name)} = "${curr.name}";
	<#list curr.fields as field>
		<#if (!field.internal)>
			<#if (!field.relation?? || isRestEntity(field.relation.targetEntity))>
	private static final String ${alias(field.name)} = "${field.name?uncap_first}";
			</#if>
		</#if>
	</#list>
	<#if (curr.options.sync??)>
	private static final String JSON_MOBILE_ID = "mobile_id";
	</#if>

	public ${curr.name}WebServiceClientAdapterBase(Context context){
		super(context);
	}
	
	/**
	 * Retrieve all the ${curr.name}s in the given list. Uses the route : ${curr.options.rest.uri?lower_case}
	 * @param ${curr.name?uncap_first}s : The list in which the ${curr.name}s will be returned
	 * @return The number of ${curr.name}s returned
	 */
	public int getAll(List<${curr.name}> ${curr.name?uncap_first}s){
		int result = -1;
		String response = this.invokeRequest(
					Verb.GET,
					String.format(
						"${curr.options.rest.uri?lower_case}%s",
						REST_FORMAT),
					null);
		if (this.isValidResponse(response) && this.isValidRequest()) {
			try {
				JSONObject json = new JSONObject(response);
				result = ${curr.name}WebServiceClientAdapter.extract${curr.name?cap_first}s(json, ${curr.name?uncap_first}s);
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				${curr.name?uncap_first}s = null;
			}
		}

		return result;
	}

	/**
	 * Retrieve one ${curr.name}. Uses the route : ${curr.options.rest.uri?lower_case}/%id%
	 * @param ${curr.name?uncap_first} : The ${curr.name} to retrieve (set the ID)
	 * @return -1 if an error has occurred. 0 if not.
	 */
	public int get(${curr.name} ${curr.name?uncap_first}){
		int result = -1;
		String response = this.invokeRequest(
					Verb.GET,
					String.format(
						"${curr.options.rest.uri?lower_case}/%s%s",
						${curr.name?uncap_first}.getId(), 
						REST_FORMAT),
					null);
		if (this.isValidResponse(response) && this.isValidRequest()) {
			try {
				JSONObject json = new JSONObject(response);
				${curr.name}WebServiceClientAdapter.extract(json, ${curr.name?uncap_first});
				result = 0;
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				${curr.name?uncap_first} = null;
			}
		}

		return result;
	}

	/**
	 * Insert the ${curr.name}. Uses the route : ${curr.options.rest.uri?lower_case}
	 * @param ${curr.name?uncap_first} : The ${curr.name} to insert
	 * @return -1 if an error has occurred. 0 if not.
	 */
	public int insert(${curr.name} ${curr.name?uncap_first}){
		int result = -1;
		String response = this.invokeRequest(
					Verb.POST,
					String.format(
						"${curr.options.rest.uri?lower_case}%s",
						REST_FORMAT),
					${curr.name}WebServiceClientAdapter.${curr.name?uncap_first}ToJson(${curr.name?uncap_first}));
		if (this.isValidResponse(response) && this.isValidRequest()) {
			result = 0;
		}

		return result;
	}

	/**
	 * Update a ${curr.name}. Uses the route : ${curr.options.rest.uri?lower_case}/%id%
	 * @param ${curr.name?uncap_first} : The ${curr.name} to update
	 * @return -1 if an error has occurred. 0 if not.
	 */
	public int update(${curr.name} ${curr.name?uncap_first}){
		int result = -1;
		String response = this.invokeRequest(
					Verb.PUT,
					String.format(
						"${curr.options.rest.uri?lower_case}/%s%s",
						${curr.name?uncap_first}.getId(),
						REST_FORMAT),
					${curr.name}WebServiceClientAdapter.${curr.name?uncap_first}ToJson(${curr.name?uncap_first}));
		if (this.isValidResponse(response) && this.isValidRequest()) {
			result = 0;
		}

		return result;
	}

	/**
	 * Delete a ${curr.name}. Uses the route : ${curr.options.rest.uri?lower_case}/%id%
	 * @param ${curr.name?uncap_first} : The ${curr.name} to delete (only the id is necessary)
	 * @return -1 if an error has occurred. 0 if not.
	 */
	public int delete(${curr.name} ${curr.name?uncap_first}){
		int result = -1;
		String response = this.invokeRequest(
					Verb.DELETE,
					String.format(
						"${curr.options.rest.uri?lower_case}/%s%s",
						${curr.name?uncap_first}.getId(), 
						REST_FORMAT),
					null);
		if (this.isValidResponse(response) && this.isValidRequest()) {
			result = 0;
		}

		return result;
	}

	<#list curr.relations as relation>
		<#if (isRestEntity(relation.relation.targetEntity))>
			<#if (relation.relation.type=="OneToMany")>

			<#elseif (relation.relation.type=="ManyToMany" || relation.relation.type=="ManyToOne")>
	/**
	 * Get the ${curr.name}s associated with a ${relation.relation.targetEntity}. Uses the route : ${entities[relation.relation.targetEntity].options.rest.uri?lower_case}/%${relation.relation.targetEntity}_id%/${curr.options.rest.uri?lower_case}
	 * @param ${curr.name?uncap_first}s : The list in which the ${curr.name}s will be returned
	 * @param ${relation.relation.targetEntity?lower_case} : The associated ${relation.relation.targetEntity?lower_case}
	 * @return The number of ${curr.name}s returned
	 */
	public int getBy${relation.name?cap_first}(List<${curr.name}> ${curr.name?uncap_first}s, ${relation.relation.targetEntity} ${relation.relation.targetEntity?lower_case}){
		int result = -1;
		String response = this.invokeRequest(
					Verb.GET,
					String.format(
						"${curr.options.rest.uri?lower_case}/${relation.name?lower_case}/%s%s",
						${relation.relation.targetEntity?lower_case}.getId(), 
						REST_FORMAT),
					null);

		if (this.isValidResponse(response) && this.isValidRequest()) {
			try {
				JSONObject json = new JSONObject(response);
				result = ${curr.name}WebServiceClientAdapter.extract${curr.name}s(json, ${curr.name?uncap_first}s);

			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				${curr.name?uncap_first}s = null;
			}
		}

		return result;
	}

			<#else>
	/**
	 * Get the ${curr.name} associated with a ${relation.relation.targetEntity}. Uses the route : ${entities[relation.relation.targetEntity].options.rest.uri?lower_case}/%${relation.relation.targetEntity}_id%/${curr.options.rest.uri?lower_case}
	 * @param ${curr.name?uncap_first} : The ${curr.name} that will be returned
	 * @param ${relation.relation.targetEntity?lower_case} : The associated ${relation.relation.targetEntity?lower_case}
	 * @return -1 if an error has occurred. 0 if not.
	 */
	public int getBy${relation.relation.targetEntity}(${curr.name} ${curr.name?uncap_first}, ${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first}){
		int result = -1;
		String response = this.invokeRequest(
					Verb.GET,
					String.format(
						"${curr.options.rest.uri?lower_case}/${relation.name?lower_case}/%s%s",
						${relation.relation.targetEntity?uncap_first}.getId(), 
						REST_FORMAT),
					null);

		if (this.isValidResponse(response) && this.isValidRequest()) {
			try {
				JSONObject json = new JSONObject(response);
				${curr.name}WebServiceClientAdapter.extract(json, ${curr.name?uncap_first});
				result = 0;

			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				${curr.name?uncap_first} = null;
			}
		}

		return result;
	}

			</#if>
		</#if>
	</#list>

	/**
	 * Extract a ${curr.name} from a JSONObject describing a ${curr.name}
	 * @param json The JSONObject describing the ${curr.name}
	 * @param ${curr.name?uncap_first} The returned ${curr.name}
	 * @return true if a ${curr.name} was found. false if not
	 */
	public static boolean extract(JSONObject json, ${curr.name} ${curr.name?uncap_first}){
		boolean result = false;
		
		int id = json.optInt("id", 0);

		if (id != 0) {
			<#list curr.fields as field>
				<#if (!field.internal)>
					<#if (!field.relation??)>
						<#if (curr.options.sync?? && field.name?lower_case=="id")>
			${curr.name?uncap_first}.setId(json.optInt(JSON_MOBILE_ID, 0));			
						<#elseif (curr.options.sync?? && field.name=="serverId")>
			int server_id = json.optInt(JSON_ID);
			
			if (server_id != 0)
				${curr.name?uncap_first}.setServerId(server_id);	
						<#else>
							<#if (field.type=="date"||field.type=="datetime"||field.type=="time")>
			DateTime ${field.name?uncap_first} = ${curr.name?uncap_first}.get${field.name?cap_first}();
			if(${field.name?uncap_first} ==null) ${field.name?uncap_first} = new DateTime();
			DateTimeFormatter ${field.name?uncap_first}Formatter = ${getFormatter(field.type)};
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first}Formatter.parseDateTime(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${field.name?uncap_first}.toString())));	
							<#elseif (field.type=="boolean")>
			${curr.name?uncap_first}.set${field.name?cap_first}(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${curr.name?uncap_first}.is${field.name?cap_first}()));	
							<#else>
			${curr.name?uncap_first}.set${field.name?cap_first}(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${curr.name?uncap_first}.get${field.name?cap_first}()));	
							</#if>
						</#if>
					<#else>
						<#if (isRestEntity(field.relation.targetEntity))>
							<#if (field.relation.type=="OneToMany" || field.relation.type=="ManyToMany")>
			ArrayList<${field.relation.targetEntity}> ${field.name?uncap_first} = new ArrayList<${field.relation.targetEntity}>();
			try{
				${field.relation.targetEntity}WebServiceClientAdapter.extract${field.relation.targetEntity}s(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
				${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first});
			}catch(JSONException e){
				Log.e(TAG, e.getMessage());
			}
							<#else>
			${field.relation.targetEntity} ${field.name?uncap_first} = new ${field.relation.targetEntity}();
			${field.relation.targetEntity}WebServiceClientAdapter.extract(json.opt${typeToJsonType(field)}(${alias(field.name)}), ${field.name?uncap_first});
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first});
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
	 * Extract a list of ${curr.name}s from a JSONObject describing an array of ${curr.name}s
	 * @param json The JSONObject describing the array of ${curr.name}s
	 * @param ${curr.name?uncap_first}s The returned list of ${curr.name}s
	 * @return The number of ${curr.name}s found in the JSON
	 */
	public static int extract${curr.name}s(JSONObject json, List<${curr.name}> ${curr.name?uncap_first}s) throws JSONException{
		JSONArray itemArray = json.optJSONArray(${alias(curr.name)});
		
		int result = -1;
		
		if (itemArray != null) {
			int count = itemArray.length();			
			
			for (int i = 0 ; i < count; i++) {
				JSONObject json${curr.name} = itemArray.getJSONObject(i);
				
				${curr.name} ${curr.name?uncap_first} = new ${curr.name}();
				if (extract(json${curr.name}, ${curr.name?uncap_first})){
					synchronized (${curr.name?uncap_first}s) {
						${curr.name?uncap_first}s.add(${curr.name?uncap_first});
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
	
	<#if (curr.options.sync??)>
	public static int extract${curr.name?cap_first}s(JSONObject json, String paramName, List<EntityBase> ${curr.name?uncap_first}s) throws JSONException{
		JSONArray itemArray = json.optJSONArray(paramName);
		
		int result = -1;
		
		if (itemArray != null) {
			int count = itemArray.length();			
			
			for (int i = 0 ; i < count; i++) {
				JSONObject json${curr.name?cap_first} = itemArray.getJSONObject(i);
				
				${curr.name?cap_first} ${curr.name?uncap_first} = new ${curr.name?cap_first}();
				if (extract(json${curr.name?cap_first}, ${curr.name?uncap_first})){
					synchronized (${curr.name?uncap_first}s) {
						${curr.name?uncap_first}s.add(${curr.name?uncap_first});
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
	</#if>
	
	/**
	 * Convert a ${curr.name} to a JSONObject	
	 * @param ${curr.name?uncap_first} The ${curr.name} to convert
	 * @return The converted ${curr.name}
	 */
	public static JSONObject ${curr.name?uncap_first}ToJson(${curr.name} ${curr.name?uncap_first}){
		JSONObject params = new JSONObject();
		try{
			<#list curr.fields as field>
				<#if (!field.internal)>
					<#if (!field.relation??)>
						<#if (curr.options.sync?? && field.name?lower_case=="id")>
			params.put(JSON_ID, ${curr.name?uncap_first}.getServerId());
						<#elseif (curr.options.sync?? && field.name=="serverId")>
			params.put(JSON_MOBILE_ID, ${curr.name?uncap_first}.getId());			
						<#elseif (field.type=="date" || field.type=="time" || field.type=="datetime")>
			params.put(${alias(field.name)}, ${curr.name?uncap_first}.get${field.name?cap_first}().toString());
						<#elseif (field.type=="boolean")>
			params.put(${alias(field.name)}, ${curr.name?uncap_first}.is${field.name?cap_first}());
						<#else>
			params.put(${alias(field.name)}, ${curr.name?uncap_first}.get${field.name?cap_first}());
						</#if>
					<#else>
						<#if (isRestEntity(field.relation.targetEntity))>
							<#if (field.relation.type=="OneToMany" || field.relation.type=="ManyToMany")>
			params.put(${alias(field.name)}, ${field.relation.targetEntity?cap_first}WebServiceClientAdapter.${field.relation.targetEntity?uncap_first}sToJson(${curr.name?uncap_first}.get${field.name?cap_first}()));
							<#else>
			params.put(${alias(field.name)}, ${field.relation.targetEntity?cap_first}WebServiceClientAdapter.${field.relation.targetEntity?uncap_first}ToJson(${curr.name?uncap_first}.get${field.name?cap_first}()));
							</#if>
						</#if>
					</#if>
				</#if>
			</#list>
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return params;
	}

	/**
	 * Convert a list of ${curr.name}s to a JSONArray	
	 * @param ${curr.name?uncap_first}s The array of ${curr.name}s to convert
	 * @return The array of converted ${curr.name}s
	 */
	public static JSONArray ${curr.name?uncap_first}sToJson(List<${curr.name}> ${curr.name?uncap_first}s){
		JSONArray itemArray = new JSONArray();
		
		for (int i = 0 ; i < ${curr.name?uncap_first}s.size(); i++) {
			JSONObject json${curr.name} = ${curr.name?uncap_first}ToJson(${curr.name?uncap_first}s.get(i));
			itemArray.put(json${curr.name});
		}
		
		return itemArray;
	}
	
	<#if (curr.options.sync??)>
	public void sync(DateTime dateLast, DateTime dateStart, 
			ArrayList<EntityBase> deleted, ArrayList<EntityBase> inserted, 
			ArrayList<EntityBase> updated, ArrayList<EntityBase> merged) {
		
		
		String uri = String.format(
				"${curr.name?uncap_first}",
				dateLast.toString(ISODateTimeFormat.dateTime().withZoneUTC()),
				dateStart.toString(ISODateTimeFormat.dateTime().withZoneUTC()),
				REST_FORMAT);
		
		JSONObject json = new JSONObject();
		this.addJsonDate(json, "lastSyncDate", dateLast);
		this.addJsonDate(json, "startSyncDate", dateStart);
		this.addJson${curr.name?cap_first}s(json, "${curr.name?cap_first}s-d", deleted);
		this.addJson${curr.name?cap_first}s(json, "${curr.name?cap_first}s-i", inserted);
		this.addJson${curr.name?cap_first}s(json, "${curr.name?cap_first}s-u", updated);
	    //this.addJsonUsers(json, "Users-m", merged);
		
		String response = this.invokeRequest(Verb.POST, uri , json);
		
		inserted.clear();
		updated.clear();
		merged.clear();
		
		try{
			JSONObject jsonResp = new JSONObject(response);
			${curr.name?cap_first}WebServiceClientAdapter.extract${curr.name?cap_first}s(jsonResp, "${curr.name?cap_first}s-i", inserted);
			${curr.name?cap_first}WebServiceClientAdapter.extract${curr.name?cap_first}s(jsonResp, "${curr.name?cap_first}s-u", updated);
			${curr.name?cap_first}WebServiceClientAdapter.extract${curr.name?cap_first}s(jsonResp, "${curr.name?cap_first}s-m", merged);
		}catch(JSONException e){
			Log.e(TAG, e.getMessage());
		}
		
	}
	
	private JSONObject addJsonDate(JSONObject js${curr.name?cap_first}s, String paramName, DateTime date) {
		try {
			js${curr.name?cap_first}s.put(paramName, date.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return js${curr.name?cap_first}s;
	}
	
	private JSONObject addJson${curr.name?cap_first}s(JSONObject js${curr.name?cap_first}s, String paramName, ArrayList<EntityBase> entities) {		
		ArrayList<${curr.name?cap_first}> ${curr.name?uncap_first}s = new ArrayList<${curr.name?cap_first}>();
		for (EntityBase entity : entities) {
			${curr.name?uncap_first}s.add((${curr.name?cap_first})entity);
		}
		JSONArray jsattr = ${curr.name?cap_first}WebServiceClientAdapter.${curr.name?uncap_first}sToJson(${curr.name?uncap_first}s);

		try {
			js${curr.name?cap_first}s.put(paramName, jsattr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return js${curr.name?cap_first}s;
	}
	</#if>
}
