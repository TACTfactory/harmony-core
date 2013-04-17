<#assign fixtureType = options["fixture"].type />
package ${fixture_namespace};

import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import android.util.Log;


public abstract class FixtureBase<T> {	
	private static String TAG = "FixtureBase";
	protected Context context;

	protected String patternDateTime = "yyyy-MM-dd HH:mm";
	protected String patternDate = "yyyy-MM-dd";
	protected String patternTime = "HH:mm";
	
	public LinkedHashMap<String, T> items = new LinkedHashMap<String, T>();

	public FixtureBase(Context context){
		this.context = context;
	}
	/**
     	* Load the fixtures for the current model.
    	 */
	public void getModelFixtures(int mode) {
<#if fixtureType == "xml">
		// XML Loader
		try {
			//String currentDir = new File(".").getAbsolutePath();

			SAXBuilder builder = new SAXBuilder();		// Make engine
			InputStream xmlStream = this.getXml(
					DataLoader.getPathToFixtures(mode) 
					+ this.getFixtureFileName());
			if (xmlStream != null){
				Document doc = (Document) builder.build(xmlStream); 	// Load XML File
				final Element rootNode = doc.getRootElement(); 			// Load Root element
				//final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

				// Find Application Node
				List<Element> entities = rootNode.getChildren(this.getFixtureFileName()); 	// Find a element
				if (entities != null) {
					for (Element element : entities) {
						this.items.put((String)element.getAttributeValue("id"), this.extractItem(element));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
		}
<#elseif fixtureType == "yml">
		// YAML Loader
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getYml(
					DataLoader.getPathToFixtures(mode) 
					+ this.getFixtureFileName());

		Map<?, ?> map = (Map<?, ?>) yaml.load(inputStream);
		if (map != null && map.containsKey(this.getFixtureFileName())){
			Map<?, ?> listEntities = (Map<?, ?>) map.get(this.getFixtureFileName());
			if (listEntities!=null){
				for (Object name : listEntities.keySet()) {
					this.items.put((String)element.getAttributeValue("id"), this.extractItem(element));
				}
			}
		}
</#if>
	}

	/**
	 * Load data fixtures
	 */
	public abstract void load(DataManager manager);
	
	
	public T getModelFixture(String id) {
		return this.items.get(id);
	}

	protected abstract T extractItem(Element element);

	/**
	 * Get the order of this fixture
	 * 
	 * @return index order
	 */
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	<#if (fixtureType=="xml")>
	// Retrieve an xml file from the assets
	public InputStream getXml(String entityName){
		AssetManager assetManager = this.context.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName+".xml");
		} catch (IOException e){
			// TODO Auto-generated method stub
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}
	<#elseif (fixtureType=="yml")>
	// Retrieve an xml file from the assets
	public InputStream getYml(String entityName){
		AssetManager assetManager = this.context.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName+".yml");
		} catch (IOException e){
			// TODO Auto-generated method stub
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}
	</#if>

	protected abstract String getFixtureFileName();
}
