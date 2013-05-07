<#assign fixtureType = options["fixture"].type />
package ${fixture_namespace};

import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

<#if fixtureType=="xml">
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
<#elseif fixtureType=="yml">
import org.yaml.snakeyaml.Yaml;

</#if>


import android.util.Log;

/**
 * FixtureBase.
 * @param <T> Fixture
 */
public abstract class FixtureBase<T> {	
	/** TAG for debug purpose. */
	private static String TAG = "FixtureBase";
	/** Context. */
	protected Context context;

	/** Date + time pattern. */
	protected String patternDateTime = "yyyy-MM-dd HH:mm";
	/** Date pattern. */
	protected String patternDate = "yyyy-MM-dd";
	/** Time pattern. */
	protected String patternTime = "HH:mm";
	
	/** Link an ID and its entity. */
	public Map<String, T> items = new LinkedHashMap<String, T>();

	/**
	 * Constructor.
	 * @param context The context
	 */
	public FixtureBase(final Context context) {
		this.context = context;
	}
	/**
     * Load the fixtures for the current model.
     * @param mode Mode
     */
	public void getModelFixtures(final int mode) {
<#if fixtureType == "xml">
		// XML Loader
		try {
			//String currentDir = new File(".").getAbsolutePath();
			
			// Make engine
			SAXBuilder builder = new SAXBuilder();
			InputStream xmlStream = this.getXml(
					DataLoader.getPathToFixtures(mode) 
					+ this.getFixtureFileName());
			if (xmlStream != null) {
				// Load XML File
				Document doc = (Document) builder.build(xmlStream);
				// Load Root element
				final Element rootNode = doc.getRootElement();
				// Load Name space (required for manipulate attributes)
				//final Namespace ns = rootNode.getNamespace("android");

				// Find Application Node
			 	// Find an element
				List<Element> entities = rootNode.getChildren(
											this.getFixtureFileName());
				if (entities != null) {
					for (Element element : entities) {
						this.items.put((String)element.getAttributeValue("id"), 
								this.extractItem(element));
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
		final Yaml yaml = new Yaml();
		final InputStream inputStream = this.getYml(
					DataLoader.getPathToFixtures(mode) 
					+ this.getFixtureFileName());

		if (inputStream != null) {
			final Map<?, ?> map = (Map<?, ?>) yaml.load(inputStream);
			if (map != null && map.containsKey(this.getFixtureFileName())) {
				final Map<?, ?> listEntities = (Map<?, ?>) map.get(
						this.getFixtureFileName());
				if (listEntities != null) {
					for (final Object name : listEntities.keySet()) {
						final Map<?, ?> currEntity = 
								(Map<?, ?>) listEntities.get(name);
						this.items.put((String) name, 
								this.extractItem(currEntity));
					}
				}
			}
		}
</#if>
	}

	/**
	 * Load data fixtures.
	 * @param manager The DataManager
	 */
	public abstract void load(DataManager manager);
	
	/**
	 * Return the fixture with the given ID.
	 * @param id The fixture id as String
	 * @return fixtures with the given ID
	 */
	public T getModelFixture(final String id) {
		return this.items.get(id);
	}

	<#if (fixtureType=="xml")>
	protected abstract T extractItem(Element element);
	<#else>
	protected abstract T extractItem(Map<?, ?> columns);
	</#if>

	/**
	 * Get the order of this fixture.
	 * 
	 * @return index order
	 */
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	<#if (fixtureType=="xml")>
	/** Retrieve an xml file from the assets.
	 * @param entityName The entity name
	 * @return The InputStream corresponding to the entity
	 */
	public InputStream getXml(final String entityName) {
		final AssetManager assetManager = this.context.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName + ".xml");
		} catch (IOException e) {
			// TODO Auto-generated method stub
			Log.w(TAG, "No " + entityName + " fixture file found.");
		}
		return ret;
	}
	<#elseif (fixtureType=="yml")>
	/** Retrieve an xml file from the assets.
	 * @param entityName The entity name
	 * @return The InputStream corresponding to the entity
	 */
	public InputStream getYml(final String entityName) {
		AssetManager assetManager = this.context.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName + ".yml");
		} catch (IOException e) {
			// TODO Auto-generated method stub
			Log.w(TAG, "No " + entityName + " fixture file found.");
		}
		return ret;
	}
	</#if>

	protected abstract String getFixtureFileName();
}
