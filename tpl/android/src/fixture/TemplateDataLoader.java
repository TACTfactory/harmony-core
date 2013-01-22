<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
package ${fixture_namespace};

import android.content.Context;
<#assign isToMany=false />
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#list curr.relations as relation>
	<#if relation.relation.type=="ManyToMany" || relation.relation.type=="OneToMany">
		<#assign isToMany=true />
	</#if>
</#list>
<#list curr.fields as field>
	<#if field.type=="date">
		<#assign hasDate=true />
	<#elseif field.type=="time">
		<#assign hasTime=true />
	<#elseif field.type="datetime">
		<#assign hasDateTime=true />
	</#if>
</#list>
<#if isToMany>
import ${project_namespace}.entity.*;
import java.util.ArrayList;
</#if>

import java.io.InputStream;
<#if fixtureType=="xml">
	<#if (hasTime || hasDate || hasDateTime)>
import ${project_namespace}.harmony.util.DateUtils;
import java.util.Date;
import org.joda.time.DateTime;
	</#if>
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.util.List;
<#elseif fixtureType=="yml">
	<#if (hasTime)>
import ${project_namespace}.harmony.util.DateUtils;
	</#if>
	<#if (hasTime || hasDate || hasDateTime)>
import java.util.Date;
import org.joda.time.DateTime;
	</#if>
import org.yaml.snakeyaml.Yaml;
import java.util.Map;

</#if>

import java.util.LinkedHashMap;

import ${curr.namespace}.entity.${curr.name};

public class ${curr.name?cap_first}DataLoader extends FixtureBase {
	public static LinkedHashMap<String, ${curr.name?cap_first}> ${curr.name?uncap_first}s = new LinkedHashMap<String, ${curr.name?cap_first}>();
	
	public ${curr.name?cap_first}DataLoader(Context context){
		super(context);
	}
	
	@Override
	public void getModelFixtures(int mode) {
		${curr.name?cap_first} ${curr.name?uncap_first} = null;
		String entityName = "${curr.name?cap_first}";
		
		
		<#if hasTime>String patternTime = "HH:mm";</#if>
		<#if fixtureType=="xml">
		<#if hasDateTime>String patternDateTime = "yyyy-MM-dd HH:mm";</#if>
		<#if hasDate>String patternDate = "yyyy-MM-dd";</#if>
		// XML Loader
		try {
			//String currentDir = new File(".").getAbsolutePath();

			SAXBuilder builder = new SAXBuilder();		// Make engine
			InputStream xmlStream;
			if(mode==MODE_BASE)
				xmlStream = this.getXml("app/"+entityName);
			else
				xmlStream = this.getXml("test/"+entityName);
			if(xmlStream != null){
				Document doc = (Document) builder.build(xmlStream); 	// Load XML File
				final Element rootNode = doc.getRootElement(); 			// Load Root element
				//final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)
	
				// Find Application Node
				List<Element> entities = rootNode.getChildren(entityName); 	// Find a element
				if (entities != null) {
					for (Element element : entities) {
						${curr.name?uncap_first} = new ${curr.name?cap_first}();
						<#list curr.fields as field>
							<#if (!field.internal)>
						if(element.getChildText("${field.name?uncap_first}")!=null){
								<#if !field.relation??>
									<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
							${curr.name?uncap_first}.set${field.name?cap_first}(Integer.parseInt(element.getChildText("${field.name?uncap_first}")));
									<#elseif field.type=="date">
							${curr.name?uncap_first}.set${field.name?cap_first}(DateUtils.formatPattern(patternDate, element.getChildText("${field.name?uncap_first}")));
									<#elseif field.type=="datetime">
							${curr.name?uncap_first}.set${field.name?cap_first}(DateUtils.formatPattern(patternDateTime, element.getChildText("${field.name?uncap_first}")));
									<#elseif field.type=="time">
							${curr.name?uncap_first}.set${field.name?cap_first}(DateUtils.formatPattern(patternTime, element.getChildText("${field.name?uncap_first}")));
									<#elseif field.type=="boolean">
							${curr.name?uncap_first}.set${field.name?cap_first}(Boolean.parseBoolean(element.getChildText("${field.name?uncap_first}")));		
									<#else>
							${curr.name?uncap_first}.set${field.name?cap_first}(element.getChildText("${field.name?uncap_first}"));
									</#if>
								<#else>
									<#if field.relation.type=="ManyToOne" || field.relation.type=="OneToOne">
							${curr.name?uncap_first}.set${field.name?cap_first}(${field.relation.targetEntity?cap_first}DataLoader.${field.relation.targetEntity?uncap_first}s.get(element.getChildText("${field.name?uncap_first}")));
									<#else>
							ArrayList<${field.relation.targetEntity?cap_first}> ${field.relation.targetEntity?uncap_first}s = new ArrayList<${field.relation.targetEntity?cap_first}>();
							List<Element> ${field.relation.targetEntity?uncap_first}sMap = element.getChildren("${field.name?uncap_first}");
							for(Element ${field.relation.targetEntity?uncap_first}Name : ${field.relation.targetEntity?uncap_first}sMap){
								if(${field.relation.targetEntity?cap_first}DataLoader.${field.relation.targetEntity?uncap_first}s.containsKey(${field.relation.targetEntity?uncap_first}Name.getText()))
									${field.relation.targetEntity?uncap_first}s.add(${field.relation.targetEntity?cap_first}DataLoader.${field.relation.targetEntity?uncap_first}s.get(${field.relation.targetEntity?uncap_first}Name.getText()));
							}
							${curr.name?uncap_first}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first}s);		
									</#if>
								</#if>
						}
							</#if>
						</#list>
						${curr.name?uncap_first}s.put((String)element.getAttributeValue("id") , ${curr.name?uncap_first});
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		<#elseif fixtureType=="yml">
		// YAML Loader
		Yaml yaml = new Yaml();
		InputStream inputStream;
		if(mode==MODE_BASE)
			inputStream = this.getYml("app/"+entityName);
		else
			inputStream = this.getYml("test/"+entityName);
		
		Map<?, ?> map = (Map<?, ?>) yaml.load(inputStream);
		if(map != null && map.containsKey(entityName)){
			Map<?, ?> listEntities = (Map<?, ?>) map.get(entityName);
			for (Object name : listEntities.keySet()) {
				Map<?, ?> columns = (Map<?, ?>) listEntities.get(name);
				${curr.name?uncap_first} = new ${curr.name?cap_first}();
				<#list curr.fields as field>
					<#if (!field.internal)>
				if(columns.get("${field.name?uncap_first}")!=null){
						<#if !field.relation??>
							<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
					${curr.name?uncap_first}.set${field.name?cap_first}((Integer)columns.get("${field.name?uncap_first}"));
							<#elseif field.type=="double">
					${curr.name?uncap_first}.set${field.name?cap_first}((Double)columns.get("${field.name?uncap_first}"));
							<#elseif field.type=="float">
					${curr.name?uncap_first}.set${field.name?cap_first}(((Double)columns.get("${field.name?uncap_first}")).floatValue());
							<#elseif field.type=="date">
					${curr.name?uncap_first}.set${field.name?cap_first}(new DateTime(((Date)columns.get("${field.name?uncap_first}"))));
							<#elseif field.type=="datetime">		
					${curr.name?uncap_first}.set${field.name?cap_first}(new DateTime(((Date)columns.get("${field.name?uncap_first}"))));
							<#elseif field.type=="time">
					${curr.name?uncap_first}.set${field.name?cap_first}(DateUtils.formatPattern(patternTime,(String)columns.get("${field.name?uncap_first}")));
							<#elseif field.type=="boolean">
					${curr.name?uncap_first}.set${field.name?cap_first}((Boolean)columns.get("${field.name?uncap_first}"));		
							<#else>
					${curr.name?uncap_first}.set${field.name?cap_first}((String)columns.get("${field.name?uncap_first}"));
							</#if>
						<#else>
							<#if field.relation.type=="ManyToOne" || field.relation.type=="OneToOne">
					${curr.name?uncap_first}.set${field.name?cap_first}(${field.relation.targetEntity?cap_first}DataLoader.${field.relation.targetEntity?uncap_first}s.get((String)columns.get("${field.name?uncap_first}")));
							<#else>
					ArrayList<${field.relation.targetEntity?cap_first}> ${field.relation.targetEntity?uncap_first}s = new ArrayList<${field.relation.targetEntity?cap_first}>();
					Map<?, ?> ${field.relation.targetEntity?uncap_first}sMap = (Map<?, ?>)columns.get("${field.name?uncap_first}");
					for(Object ${field.relation.targetEntity?uncap_first}Name : ${field.relation.targetEntity?uncap_first}sMap.values()){
						if(${field.relation.targetEntity?cap_first}DataLoader.${field.relation.targetEntity?uncap_first}s.containsKey((String)${field.relation.targetEntity?uncap_first}Name))
							${field.relation.targetEntity?uncap_first}s.add(${field.relation.targetEntity?cap_first}DataLoader.${field.relation.targetEntity?uncap_first}s.get((String)${field.relation.targetEntity?uncap_first}Name));
					}
					${curr.name?uncap_first}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first}s);		
							</#if>
						</#if>
				}
					</#if>
				</#list>
				${curr.name?uncap_first}s.put((String)name , ${curr.name?uncap_first});
			}
		}
		<#else>
		
		</#if>
	}

	@Override
	public void load(DataManager manager) {
		for (${curr.name?cap_first} ${curr.name?uncap_first} : ${curr.name?cap_first}DataLoader.${curr.name?uncap_first}s.values()) {
			${curr.name?uncap_first}.setId(manager.persist(${curr.name?uncap_first}));
		}
		manager.flush();
	}

	/**
	 * @see com.tactfactory.mda.test.FixtureBase#getModelFixture(java.lang.String)
	 */
	@Override
	public Object getModelFixture(String id) {
		return ${curr.name?cap_first}DataLoader.${curr.name?uncap_first}s.get(id);
	}
	
	/**
	 * Give priority for fixtures insertion in database.
	 * 0 is the first.
	 * @return The order
	 */
	@Override
	public int getOrder(){
		return 0;
	}
}
