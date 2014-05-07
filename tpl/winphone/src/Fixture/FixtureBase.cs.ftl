<#include utilityPath + "all_imports.ftl" />
<#assign fixtureType = options["fixture"].type />
<@header?interpret />

using ${project_namespace}.Utils;
using System;
using System.Collections.Generic;
using System.Data.Linq;
using System.IO;
using System.Text;
<#if fixtureType == "xml">
using System.Xml.Linq;
</#if>


namespace ${project_namespace}.Fixture
{
    public abstract class FixtureBase
    {
        /**
         * Returns the fixture file name.
         * @return the fixture file name
         */
        public abstract String GetFixtureFileName();
        public abstract void getModelFixtures(int mode);
        public abstract void Clear();
        /**
         * Load data fixtures.
         * @param manager The DataManager
         */
        public abstract void Load(DataManager manager);
    }
    
    /**
     * FixtureBase.
     * FixtureBase is the abstract base of all your fixtures' dataloaders.
     * It loads the fixture file associated to your entity, parse each items in it
     * and store them in the database.
     *
     * @param <T> Entity related to this fixture loader
     */
    public abstract class FixtureBase<T> : FixtureBase
    {
        /** TAG for debug purpose. */
        private const String TAG = "FixtureBase";
        /** DataContext. */
        protected DataContext ctx;
    
        /** Date + time pattern. */
        protected String patternDateTime = "yyyy-MM-dd HH:mm";
        /** Date pattern. */
        protected String patternDate = "yyyy-MM-dd";
        /** Time pattern. */
        protected String patternTime = "HH:mm";
    
        /** Link an ID and its entity. */
        public Dictionary<String, T> items = new Dictionary<String, T>();
    
        /** SerializedBackup. */
        protected byte[] serializedBackup;
    
        /** Current field being read. */
        protected String currentFieldName;
        
        /**
         * Constructor.
         * @param ctx The context
         */
        public FixtureBase(DataContext ctx)
        {
            this.ctx = ctx;
        }
        
        /**
         * Load the fixtures for the current model.
         * @param mode Mode
         */
        public override void getModelFixtures(int mode)
        {
    <#if fixtureType == "xml">
            // XML Loader
            try
            {
                String fileName = DataLoader.getPathToFixtures(mode)
                        + this.GetFixtureFileName();
                // Make engine
                StreamReader xmlStream = this.GetXml(fileName);
                
                if (xmlStream != null)
                {
                    // Load XML File
                    XDocument doc = XDocument.Load(xmlStream);
                    // Load Root element
                    XElement rootNode = doc.Root;
    
                    // Find Application Node
                    // Find an element
                    IEnumerable<XElement> entities = rootNode.Descendants(
                                    XName.Get(this.GetFixtureFileName()));
                                                
                    if (entities != null)
                    {
                        foreach (XElement element in entities)
                        {
                            String elementName = element.Attribute("id").Value;
                            
                            try
                            {
                                this.items.Add(elementName,
                                    this.ExtractItem(element));
                            }
                            catch (Exception e)
                            {
                                this.DisplayError(e, fileName, elementName);
                            }
                        }
                    }
                }
            }
            catch (IOException e)
            {
                Log.E(TAG, e);
            }
    <#elseif fixtureType == "yml">
            // YAML Loader
            Yaml yaml = new Yaml(
                    new Constructor(),
                    new Representer(),
                    new DumperOptions(),
                    new CustomResolver());
    
            String fileName = DataLoader.getPathToFixtures(mode)
                        + this.getFixtureFileName();
            InputStream inputStream = this.GetYml(fileName);
    
            if (inputStream != null)
            {
                Dictionary<?, ?> map = (Dictionary<?, ?>) yaml.load(inputStream);
                
                if (map != null && map.containsKey(this.GetFixtureFileName()))
                {
                    Dictionary<?, ?> listEntities = (Dictionary<?, ?>) map.get(
                            this.getFixtureFileName());
                    if (listEntities != null)
                    {
                        foreach (Object name in listEntities.keySet())
                        {
                            Dictionary<?, ?> currEntity =
                                    (Dictionary<?, ?>) listEntities.get(name);
                                    
                            try
                            {
                                this.items.put((String) name,
                                    this.extractItem(currEntity));
                            }
                            catch (Exception e)
                            {
                                this.DisplayError(e, fileName, name.toString());
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
        protected void DisplayError(Exception e,
                String fileName,
                String entityName)
        {
            StringBuilder error = new StringBuilder();
            error.Append("Error in ");
            error.Append(fileName);
            error.Append(".${fixtureType}");
            error.Append(" in field ");
            error.Append(entityName);
            error.Append(".");
            error.Append(this.currentFieldName);
            error.Append(" => ");
            error.Append(e.Message);
    
            Log.E(TAG, error.ToString());
        }
        
        public override void Clear()
        {
            this.items.Clear();
        }
    
        /**
         * Return the fixture with the given ID.
         * @param id The fixture id as String
         * @return fixtures with the given ID
         */
        public T GetModelFixture(String id)
        {
            return this.items[id];
        }
    
        <#if (fixtureType=="xml")>
        /**
         * Transform the xml into an Item of type T.
         * @param element The xml representation of the item.
         * @return The T item read.
         */
        public abstract T ExtractItem(XElement element);
        <#else>
        /**
         * Transform the yml into an Item of type T.
         * @param columns The yml representation of the item.
         * @return The T item read.
         */
        public abstract T ExtractItem(Dictionary<?, ?> columns);
        </#if>
    
        /**
         * Get the order of this fixture.
         *
         * @return index order
         */
        public virtual int GetOrder()
        {
            return 0;
        }
    
        <#if (fixtureType=="xml")>
        /** Retrieve an xml file from the assets.
         * @param entityName The entity name
         * @return The InputStream corresponding to the entity
         */
        public StreamReader GetXml(String entityName)
        {
            StreamReader ret = null;
            
            try
            {
                ret = AssetManager.Open(entityName + ".xml");
            }
            catch (IOException)
            {
                Log.W(TAG, "No " + entityName + " fixture file found.");
            }
            
            return ret;
        }
        <#elseif (fixtureType=="yml")>
        /** Retrieve an xml file from the assets.
         * @param entityName The entity name
         * @return The InputStream corresponding to the entity
         */
        public InputStream GetYml(String entityName) {
            AssetManager assetManager = this.ctx.getAssets();
            InputStream ret = null;
            
            try
            {
                ret = assetManager.open(entityName + ".yml");
            }
            catch (IOException e)
            {
                Log.W(TAG, "No " + entityName + " fixture file found.");
            }
            
            return ret;
        }
        </#if>
    
        ///**
        // * Serializes the Dictionary into a byte array for backup purposes.
        // */
        //public void backup() {
        //    ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //    ObjectOutput output = null;
        //    try {
        //      output = new ObjectOutputStream(bos);
        //      output.writeObject(this.items);
        //     this.serializedBackup = bos.toByteArray();
    
        //    } catch (IOException e) {
        //        Log.E(TAG, e.Message);
        //    } finally {
        //        if (output != null) {
        //            try {
        //                output.close();
        //            } catch (IOException e) {
        //                Log.E(TAG, e.Message);
        //            }
        //        }
        //        try {
        //            bos.close();
        //        } catch (IOException e) {
        //            Log.E(TAG, e.Message);
        //        }
        //    }
        //}


        ///**
        // * Returns the Dictionary<String, T> loaded from the fixtures.
        // * @return the Dictionary
        // */
        //public Dictionary<String, T> getMap() {
        //    Dictionary<String, T> result = null;
        //    ByteArrayInputStream bis = 
        //        new ByteArrayInputStream(this.serializedBackup);
        //    ObjectInput input = null;
        //    try {
        //      input = new ObjectInputStream(bis);
        //      result = (Dictionary<String, T>) input.readObject();
    
        //    } catch (IOException e) {
        //        Log.E(TAG, e.Message);
        //    } finally {
        //        if (input != null) {
        //            try {
        //                input.close();
        //            } catch (IOException e) {
        //                Log.E(TAG, e.Message);
        //            }
        //        }
        //        try {
        //            bis.close();
        //        } catch (IOException e) {
        //            Log.E(TAG, e.Message);
        //        }
        //    }
    
        //    return result;
        //}
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
        protected abstract T get(String key);
    
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
        protected <U> U parseField(Dictionary<?, ?> columns,
                    String key,
                    Class<U> type)
        {
            this.currentFieldName = key;
            U result;
            
            if (columns.containsKey(key))
            {
                result = (U) columns.get(key);
            }
            else
            {
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
        protected DateTime ParseDateTimeField(Dictionary<?, ?> columns,
                    String key)
        {
            DateTime result;
            String dateTimeString = this.ParseField(columns, key, String.class);
            
            if (dateTimeString != null)
            {
                result = DateUtils.formatYAMLStringToDateTime(dateTimeString);
            }
            else
            {
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
        protected <U> U ParseSimpleRelationField(Dictionary<?, ?> columns,
                    String key,
                    FixtureBase<U> relationLoader) {
            U result;
            String relationString = this.ParseField(columns, key, String.class);
            if (relationString != null)
            {
                result = relationLoader.get(relationString);
            }
            else
            {
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
        protected <U> ArrayList<U> ParseMultiRelationField(Dictionary<?, ?> columns,
                    String key,
                    FixtureBase<U> relationLoader)
        {
            ArrayList<U> result;
            Dictionary<?, ?> relationMap = this.ParseField(columns, key, Dictionary.class);
            if (relationMap != null)
            {
                result = new ArrayList<U>();
                foreach (Object relationName in relationMap.values())
                {
                    U relatedEntity = relationLoader.get((String) relationName);
                    if (relatedEntity != null)
                    {
                        result.add(relatedEntity);
                    }
                }
            }
            else
            {
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
        protected int ParseIntField(Dictionary<?, ?> columns,
                    String key)
        {
            int result;
            Integer field = this.ParseField(columns, key, Integer.class);
            
            if (field != null)
            {
                result = field.intValue();
            }
            else
            {
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
        protected byte ParseByteField(Dictionary<?, ?> columns,
                    String key) {
            byte result;
            Integer field = this.ParseField(columns, key, Integer.class);
            
            if (field != null)
            {
                result = field.byteValue();
            }
            else
            {
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
        protected short ParseShortField(Dictionary<?, ?> columns,
                    String key)
        {
            short result;
            Integer field = this.ParseField(columns, key, Integer.class);
            
            if (field != null)
            {
                result = field.shortValue();
            }
            else
            {
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
        protected char ParseCharField(Dictionary<?, ?> columns,
                    String key)
        {
            char result;
            String field = this.ParseField(columns, key, String.class);
            
            if (field != null)
            {
                result = field.charAt(0);
            }
            else
            {
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
        protected boolean ParseBooleanField(Dictionary<?, ?> columns,
                    String key)
        {
            boolean result;
            Boolean field = this.ParseField(columns, key, Boolean.class);
            
            if (field != null)
            {
                result = field.booleanValue();
            }
            else
            {
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
        protected float ParseFloatField(Dictionary<?, ?> columns,
                    String key)
        {
            float result;
            Double field = this.ParseField(columns, key, Double.class);
            
            if (field != null)
            {
                result = field.floatValue();
            }
            else
            {
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
        protected double ParseDoubleField(Dictionary<?, ?> columns,
                    String key)
        {
            double result;
            Double field = this.ParseField(columns, key, Double.class);
            
            if (field != null)
            {
                result = field.doubleValue();
            }
            else
            {
                result = 0.0d;
            }
            
            return result;
        }
        </#if>
    }
}