<#assign fixtureType = options["fixture"].type />
<@header?interpret />
package ${fixture_namespace};

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
<#if fixtureType=="xml">
import java.util.List;
</#if>
import java.util.Map;

<#if fixtureType=="xml">
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
<#elseif fixtureType=="yml">
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;
</#if>

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * FixtureBase.
 * FixtureBase is the abstract base of all your fixtures' dataloaders.
 * It loads the fixture file associated to your entity, parse each items in it
 * and store them in the database.
 *
 * @param <T> Entity related to this fixture loader
 */
public abstract class FixtureBase<T> {
	/** TAG for debug purpose. */
	private static final String TAG = "FixtureBase";
	/** Context. */
	protected Context ctx;

	/** Date + time pattern. */
	protected String patternDateTime = "yyyy-MM-dd HH:mm";
	/** Date pattern. */
	protected String patternDate = "yyyy-MM-dd";
	/** Time pattern. */
	protected String patternTime = "HH:mm";

	/** Link an ID and its entity. */
	protected Map<String, T> items = new LinkedHashMap<String, T>();

	/** SerializedBackup. */
	protected byte[] serializedBackup;

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	public FixtureBase(final Context ctx) {
		this.ctx = ctx;
	}
	/**
     * Load the fixtures for the current model.
     * @param mode Mode
     */
	public void getModelFixtures(final int mode) {
<#if fixtureType == "xml">
		// XML Loader
		try {
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

				// Find Application Node
			 	// Find an element
				List<Element> entities = rootNode.getChildren(
											this.getFixtureFileName());
				if (entities != null) {
					for (Element element : entities) {
						this.items.put((String) element.getAttributeValue("id"),
								this.extractItem(element));
					}
				}
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} catch (JDOMException e) {
			Log.e(TAG, e.getMessage());
		}
<#elseif fixtureType == "yml">
		// YAML Loader
		final Yaml yaml = new Yaml(
				new Constructor(),
				new Representer(),
				new DumperOptions(),
				new CustomResolver());

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
	/**
	 * Transform the xml into an Item of type T.
	 * @param element The xml representation of the item.
	 * @return The T item read.
	 */
	protected abstract T extractItem(Element element);
	<#else>
	/**
	 * Transform the yml into an Item of type T.
	 * @param columns The yml representation of the item.
	 * @return The T item read.
	 */
	protected abstract T extractItem(Map<?, ?> columns);
	</#if>

	/**
	 * Get the order of this fixture.
	 *
	 * @return index order
	 */
	public int getOrder() {
		return 0;
	}

	<#if (fixtureType=="xml")>
	/** Retrieve an xml file from the assets.
	 * @param entityName The entity name
	 * @return The InputStream corresponding to the entity
	 */
	public InputStream getXml(final String entityName) {
		final AssetManager assetManager = this.ctx.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName + ".xml");
		} catch (IOException e) {
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
		AssetManager assetManager = this.ctx.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName + ".yml");
		} catch (IOException e) {
			Log.w(TAG, "No " + entityName + " fixture file found.");
		}
		return ret;
	}
	</#if>

	/**
	 * Returns the fixture file name.
	 * @return the fixture file name
	 */
	protected abstract String getFixtureFileName();


	/**
	 * Serializes the Map into a byte array for backup purposes.
	 */
	public void backup() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);
		  out.writeObject(this.items);
		 this.serializedBackup = bos.toByteArray();

		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
			try {
				bos.close();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}


	/**
	 * Returns the Map<String, T> loaded from the fixtures.
	 * @return the Map
	 */
	public Map<String, T> getMap() {
		Map<String, T> result = null;
		ByteArrayInputStream bis = 
			new ByteArrayInputStream(this.serializedBackup);
		ObjectInput in = null;
		try {
		  in = new ObjectInputStream(bis);
		  result = (LinkedHashMap<String, T>) in.readObject();

		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} catch (ClassNotFoundException e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
			try {
				bis.close();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}

		return result;
	}
	<#if (fixtureType=="yml")>
	/**
	 * CustomResolver not resolving timestamps.
	 */
	public class CustomResolver extends Resolver {

	    /*
	     * Do not resolve timestamp.
	     */
	    protected void addImplicitResolvers() {
	        addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
	        addImplicitResolver(Tag.INT, INT, "-+0123456789");
	        addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
	        addImplicitResolver(Tag.MERGE, MERGE, "<");
	        addImplicitResolver(Tag.NULL, NULL, "~nN\0");
	        addImplicitResolver(Tag.NULL, EMPTY, null);
	        addImplicitResolver(Tag.VALUE, VALUE, "=");
	    }
	}
	</#if>

	/**
	 * Gets the extracted fixture corresponding to the given name.
	 * This method will search for a T type, or for any type extending T.
	 */
	protected abstract T get(final String key);
}
