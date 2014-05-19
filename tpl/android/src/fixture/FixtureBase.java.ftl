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
<#if fixtureType=="yml">import java.util.ArrayList;</#if>
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
import org.joda.time.DateTime;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;
</#if>


import android.content.res.AssetManager;


<#if fixtureType=="yml">import ${project_namespace}.harmony.util.DateUtils;</#if>

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
	/** android.content.Context. */
	protected android.content.Context ctx;

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

	/** Current field being read. */
	protected String currentFieldName;

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	public FixtureBase(final android.content.Context ctx) {
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
			String fileName = DataLoader.getPathToFixtures(mode)
					+ this.getFixtureFileName();
			// Make engine
			SAXBuilder builder = new SAXBuilder();
			InputStream xmlStream = this.getXml(fileName);
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
						String elementName = (String) element.getAttributeValue("id");
						try {
							this.items.put(elementName,
								this.extractItem(element));
						} catch (Exception e) {
							this.displayError(e, fileName, elementName);
						}
					}
				}
			}
		} catch (IOException e) {
			android.util.Log.e(TAG, e.getMessage());
		} catch (JDOMException e) {
			android.util.Log.e(TAG, e.getMessage());
		}
<#elseif fixtureType == "yml">
		// YAML Loader
		final Yaml yaml = new Yaml(
				new Constructor(),
				new Representer(),
				new DumperOptions(),
				new CustomResolver());

		String fileName = DataLoader.getPathToFixtures(mode)
					+ this.getFixtureFileName();
		final InputStream inputStream = this.getYml(fileName);

		if (inputStream != null) {
			final Map<?, ?> map = (Map<?, ?>) yaml.load(inputStream);
			if (map != null && map.containsKey(this.getFixtureFileName())) {
				final Map<?, ?> listEntities = (Map<?, ?>) map.get(
						this.getFixtureFileName());
				if (listEntities != null) {
					for (final Object name : listEntities.keySet()) {
						final Map<?, ?> currEntity =
								(Map<?, ?>) listEntities.get(name);
						try {
							this.items.put((String) name,
								this.extractItem(currEntity));
						} catch (Exception e) {
							this.displayError(e, fileName, name.toString());
						}
					}
				}
			}
		}
</#if>
	}

	/**
	 * Display a fixture error.
	 */
	protected void displayError(final Exception e,
			final String fileName,
			final String entityName) {
		StringBuilder error = new StringBuilder();
		error.append("Error in ");
		error.append(fileName);
		error.append(".${fixtureType}");
		error.append(" in field ");
		error.append(entityName);
		error.append(".");
		error.append(this.currentFieldName);
		error.append(" => ");
		error.append(e.getMessage());

		android.util.Log.e(TAG, error.toString());
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
			android.util.Log.w(TAG, "No " + entityName + " fixture file found.");
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
			android.util.Log.w(TAG, "No " + entityName + " fixture file found.");
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
			android.util.Log.e(TAG, e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					android.util.Log.e(TAG, e.getMessage());
				}
			}
			try {
				bos.close();
			} catch (IOException e) {
				android.util.Log.e(TAG, e.getMessage());
			}
		}
	}


	/**
	 * Returns the Map<String, T> loaded from the fixtures.
	 * @return the Map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, T> getMap() {
		Map<String, T> result = null;
		ByteArrayInputStream bis = 
			new ByteArrayInputStream(this.serializedBackup);
		ObjectInput in = null;
		try {
		  in = new ObjectInputStream(bis);
		  result = (LinkedHashMap<String, T>) in.readObject();

		} catch (IOException e) {
			android.util.Log.e(TAG, e.getMessage());
		} catch (ClassNotFoundException e) {
			android.util.Log.e(TAG, e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					android.util.Log.e(TAG, e.getMessage());
				}
			}
			try {
				bis.close();
			} catch (IOException e) {
				android.util.Log.e(TAG, e.getMessage());
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

	<#if (fixtureType == "yml")>

	/**
	 * Parse a basic field (for datetimes/enums/relations,
	 * use the dedicated functions.)
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 * @param type The type to parse (String.class, Integer.class, etc.)
	 *
	 * @result The value of the field
	 */
	protected <U> U parseField(final Map<?, ?> columns,
				final String key,
				final Class<U> type) {
		this.currentFieldName = key;
		U result;
		
		if (columns.containsKey(key)) {
			result = (U) columns.get(key);
		} else {
			result = null;
		}
		
		return result;
	}

	/**
	 * Parse a datetime field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The datetime value of the field
	 */
	protected DateTime parseDateTimeField(final Map<?, ?> columns,
				final String key) {
		DateTime result;
		String dateTimeString = this.parseField(columns, key, String.class);
		if (dateTimeString != null) {
			result = DateUtils.formatYAMLStringToDateTime(dateTimeString);
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Parse a relation field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The datetime value of the field
	 */
	protected <U> U parseSimpleRelationField(final Map<?, ?> columns,
				final String key,
				final FixtureBase<U> relationLoader) {
		U result;
		String relationString = this.parseField(columns, key, String.class);
		if (relationString != null) {
			result = relationLoader.get(relationString);
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Parse a relation field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The datetime value of the field
	 */
	protected <U> ArrayList<U> parseMultiRelationField(final Map<?, ?> columns,
				final String key,
				final FixtureBase<U> relationLoader) {
		ArrayList<U> result;
		Map<?, ?> relationMap = this.parseField(columns, key, Map.class);
		if (relationMap != null) {
			result = new ArrayList<U>();
			for (Object relationName : relationMap.values()) {
				U relatedEntity = relationLoader.get((String) relationName);
				if (relatedEntity != null) {
					result.add(relatedEntity);
				}
			}
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Parse a primitive int field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The value of the field, 0 if nothing found
	 */
	protected int parseIntField(final Map<?, ?> columns,
				final String key) {
		int result;
		Integer field = this.parseField(columns, key, Integer.class);
		
		if (field != null) {
			result = field.intValue();
		} else {
			result = 0;
		}
		
		return result;
	}

	/**
	 * Parse a primitive byte field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The value of the field, 0 if nothing found
	 */
	protected byte parseByteField(final Map<?, ?> columns,
				final String key) {
		byte result;
		Integer field = this.parseField(columns, key, Integer.class);
		
		if (field != null) {
			result = field.byteValue();
		} else {
			result = 0;
		}
		
		return result;
	}

	/**
	 * Parse a primitive short field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The value of the field, 0 if nothing found
	 */
	protected short parseShortField(final Map<?, ?> columns,
				final String key) {
		short result;
		Integer field = this.parseField(columns, key, Integer.class);
		
		if (field != null) {
			result = field.shortValue();
		} else {
			result = 0;
		}
		
		return result;
	}

	/**
	 * Parse a primitive char field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The value of the field, '\u0000' if nothing found
	 */
	protected char parseCharField(final Map<?, ?> columns,
				final String key) {
		char result;
		String field = this.parseField(columns, key, String.class);
		
		if (field != null) {
			result = field.charAt(0);
		} else {
			result = '\u0000';
		}
		
		return result;
	}

	/**
	 * Parse a primitive boolean field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The value of the field, 'false' if nothing found
	 */
	protected boolean parseBooleanField(final Map<?, ?> columns,
				final String key) {
		boolean result;
		Boolean field = this.parseField(columns, key, Boolean.class);
		
		if (field != null) {
			result = field.booleanValue();
		} else {
			result = false;
		}
		
		return result;
	}

	/**
	 * Parse a primitive float field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The value of the field, 0.0f if nothing found
	 */
	protected float parseFloatField(final Map<?, ?> columns,
				final String key) {
		float result;
		Double field = this.parseField(columns, key, Double.class);
		
		if (field != null) {
			result = field.floatValue();
		} else {
			result = 0.0f;
		}
		
		return result;
	}

	/**
	 * Parse a primitive double field.
	 *
	 * @param columns The map
	 * @param key The key of the value to retrieve
	 *
	 * @result The value of the field, 0.0d if nothing found
	 */
	protected double parseDoubleField(final Map<?, ?> columns,
				final String key) {
		double result;
		Double field = this.parseField(columns, key, Double.class);
		
		if (field != null) {
			result = field.doubleValue();
		} else {
			result = 0.0d;
		}
		
		return result;
	}
	</#if>
}
