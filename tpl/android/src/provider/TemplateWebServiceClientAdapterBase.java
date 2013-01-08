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
</#function>

package ${data_namespace};

import ${space}.entity.${name};
import org.json.*;

public class ${name}WebServiceClientAdapterBase {
	<#list fields as field>
		<#if !field.internal>
	private static final String ${alias(field.name)} = "${jsonID(field.name)}";
		</#if>
	</#list>
	
	/**
	 *
	 *
	 */
	protected int getByID(${name} ${name?lower_case}){
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
	public static boolean extract${name}(JSONObject json, ${name} ${name?lower_case}){
		boolean result = false;
		
		int id = json.optInt("id", 0);

		if (id != 0) {
			<#list fields as field>
			${name?lower_case}.set${field.name?cap_first}(json.opt${typeToJsonType(field)}(${alias(field.name)}, ${name?lower_case}.get${field.name?cap_first}()));
			</#list>
			
			result = true;
		}
		
		return result;
	}

	/**
	 *
	 *
	 */
	public static boolean extract${name}s(JSONObject json, List<${name}> ${name?lower_case}s){
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
}
