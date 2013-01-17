<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
package ${fixture_namespace};

import android.content.Context;

import ${project_namespace}.harmony.util.*;
<#if fixtureType=="xml">
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
</#if>

import java.util.HashMap;

import ${curr.namespace}.entity.${curr.name};
import com.tactfactory.mda.test.demact.fixture.DataManager;

public class ${curr.name?cap_first}DataLoader extends FixtureBase {
	HashMap<String, ${curr.name?cap_first}> ${curr.name?uncap_first}s = new HashMap<String, ${curr.name?cap_first}>();
	
	public ${curr.name?cap_first}DataLoader(Context context){
		super(context);
	}
	
	@Override
	public void getModelFixtures() {
		${curr.name?cap_first} ${curr.name?uncap_first} = null;
		
		<#if fixtureType=="xml">
		// XML Loader
		try {
			String entityName = "${curr.name?cap_first}";
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
							<#if !field.relation??>
								<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
						${curr.name?uncap_first}.set${field.name?cap_first}(Integer.parseInt(element.getChildText("${field.name?uncap_first}")));
								<#elseif field.type=="date">
						${curr.name?uncap_first}.set${field.name?cap_first}(DateUtils.formatStringToDate(element.getChildText("${field.name?uncap_first}")));
								<#elseif field.type=="datetime">
						${curr.name?uncap_first}.set${field.name?cap_first}(DateUtils.formatISOStringToDateTime(element.getChildText("${field.name?uncap_first}")));
								<#elseif field.type=="time">
						${curr.name?uncap_first}.set${field.name?cap_first}(DateUtils.formatStringToTime(element.getChildText("${field.name?uncap_first}")));
								<#elseif field.type=="boolean">
						${curr.name?uncap_first}.set${field.name?cap_first}(Boolean.parseBoolean(element.getChildText("${field.name?uncap_first}")));		
								<#else>
						${curr.name?uncap_first}.set${field.name?cap_first}(element.getChildText("${field.name?uncap_first}"));
								</#if>
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
		
		<#else>
		
		</#if>
	}

	@Override
	public void load(DataManager manager) {
		for (${curr.name?cap_first} ${curr.name?uncap_first} : this.${curr.name?uncap_first}s.values()) {
			manager.persist(${curr.name?uncap_first});
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
}
