<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
package ${fixture_namespace};

import android.content.Context;

import ${project_namespace}.harmony.util.*;
<#if (curr.relations?size>0)>
import ${project_namespace}.entity.*;
</#if>

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
<#if fixtureType=="xml">
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
<#elseif fixtureType=="yml">
import org.yaml.snakeyaml.Yaml;
import java.util.Date;
</#if>

import java.util.LinkedHashMap;
import java.util.ArrayList;

import ${curr.namespace}.entity.${curr.name};

import com.tactfactory.mda.test.demact.fixture.DataManager;

public class ${curr.name?cap_first}DataLoader extends FixtureBase {
	public static LinkedHashMap<String, ${curr.name?cap_first}> ${curr.name?uncap_first}s = new LinkedHashMap<String, ${curr.name?cap_first}>();
	
	public ${curr.name?cap_first}DataLoader(Context context){
		super(context);
	}
	
	@Override
	public void getModelFixtures() {
		${curr.name?cap_first} ${curr.name?uncap_first} = null;
		String entityName = "${curr.name?cap_first}";
		
		<#if fixtureType=="xml">
		String patternDate = "yyyy-MM-dd";
		String patternTime = "HH:mm";
		String patternDateTime = "yyyy-MM-dd HH:mm";
		
		// XML Loader
		try {
			//String currentDir = new File(".").getAbsolutePath();

			SAXBuilder builder = new SAXBuilder();		// Make engine
			InputStream xmlStream = this.getXml(entityName);//new File(currentDir + "${project_path}/fixture/" + entityName + ".xml");
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
							${curr.name?uncap_first}.set${field.name?cap_first}(DateTimeFormat.forPattern(patternDate).parseDateTime(element.getChildText("${field.name?uncap_first}")));
									<#elseif field.type=="datetime">
							${curr.name?uncap_first}.set${field.name?cap_first}(DateTimeFormat.forPattern(patternDateTime).parseDateTime(element.getChildText("${field.name?uncap_first}")));
									<#elseif field.type=="time">
							${curr.name?uncap_first}.set${field.name?cap_first}(DateTimeFormat.forPattern(patternTime).parseDateTime(element.getChildText("${field.name?uncap_first}")));
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
		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		<#elseif fixtureType=="yml">
		// YAML Loader
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getYml(entityName);
		
		Map<?, ?> map = (Map<?, ?>) yaml.load(inputStream);
		if(map != null && map.containsKey(entityName)){
			Map<?, ?> listEntities = (Map<?, ?>) map.get(entityName);
			for (Object name : listEntities.keySet()) {
				Map<?, ?> columns = (Map<?, ?>) listEntities.get(name);
				${curr.name?uncap_first} = new ${curr.name?cap_first}();
				<#list curr.fields as field>
				if(columns.get("${field.name?uncap_first}")!=null){
					<#if !field.relation??>
						<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
					${curr.name?uncap_first}.set${field.name?cap_first}((Integer)columns.get("${field.name?uncap_first}"));
						<#elseif field.type=="date">
					${curr.name?uncap_first}.set${field.name?cap_first}(new DateTime(((Date)columns.get("${field.name?uncap_first}"))));
						<#elseif field.type=="datetime">
					${curr.name?uncap_first}.set${field.name?cap_first}(new DateTime(((Date)columns.get("${field.name?uncap_first}"))));
						<#elseif field.type=="time">
					${curr.name?uncap_first}.set${field.name?cap_first}(new DateTime(((Date)columns.get("${field.name?uncap_first}"))));
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
				</#list>
				${curr.name?uncap_first}s.put((String)name , ${curr.name?uncap_first});
			}
		}
		<#else>
		
		</#if>
	}

	@Override
	public void load(DataManager manager) {
		for (${curr.name?cap_first} ${curr.name?uncap_first} : this.${curr.name?uncap_first}s.values()) {
			${curr.name?uncap_first}.setId(manager.persist(${curr.name?uncap_first}));
		}
		manager.flush();
	}

	/**
	 * @see com.tactfactory.mda.test.FixtureBase#getModelFixture(java.lang.String)
	 */
	@Override
	public Object getModelFixture(String id) {
		return this.${curr.name?uncap_first}s.get(id);
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
