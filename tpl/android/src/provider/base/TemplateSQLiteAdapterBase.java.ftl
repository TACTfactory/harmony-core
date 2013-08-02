<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign sync = curr.options.sync?? />
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#assign hasInternalFields = false />
<#list curr.relations as relation><#if (relation.internal?? && relation.internal==true)><#assign hasInternalFields = true /></#if></#list>
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<@header?interpret />
package ${data_namespace}.base;

import java.util.ArrayList;
<#if hasDate || hasTime || hasDateTime>
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
</#if>
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

${ImportUtils.importRelatedSQLiteAdapters(curr, true, true)}
${ImportUtils.importRelatedEntities(curr)}
${ImportUtils.importRelatedEnums(curr)}<#if !(curr.ids?size>0)>import ${project_namespace}.harmony.exception.NotImplementedException;</#if>
<#if hasDate || hasTime || hasDateTime>import ${curr.namespace}.harmony.util.DateUtils;</#if>
import ${project_namespace}.${project_name?cap_first}Application;
<#if curr.internal?? && curr.internal=='true'>
	<#assign extendType = "Void" />
<#else>
	<#assign extendType = curr.name />
</#if>
<#if sync>
	<#assign extend="SyncSQLiteAdapterBase<" +extendType+ ">" />
<#else>
	<#assign extend="SQLiteAdapterBase<" +extendType+ ">" />
</#if>

/** ${curr.name} adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ${curr.name}Adapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}SQLiteAdapterBase 
									extends ${extend} {
	
	/** TAG for debug purpose. */									
	protected static final String TAG = "${curr.name}DBAdapter";
	
	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "${curr.name}";
	
	/**
	 *  Columns constants fields mapping.
	 */
<#list curr.fields?values as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
	/** ${field.columnName}. */
	public static final String ${NamingUtils.alias(field.name)} = 
			"${field.columnName}";
	/** Alias. */
	public static final String ALIASED_${NamingUtils.alias(field.name)} = 
			TABLE_NAME + "." + ${NamingUtils.alias(field.name)};
	</#if>
</#list>
	
	/** Global Fields. */
	public static final String[] COLS = new String[] {
<#assign firstFieldDone=false />
<#list ViewUtils.getAllFields(curr)?values as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
		${field.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {
<#assign firstFieldDone=false />
<#list ViewUtils.getAllFields(curr)?values as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
		${field.owner?cap_first}SQLiteAdapter.ALIASED_${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
	};
	
	/**
	 * Get the table name used in DB for your ${curr.name} entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your ${curr.name} entity 
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		<#if InheritanceUtils.isExtended(curr)>
		${curr.extends}SQLiteAdapter motherAdapt = new ${curr.extends}SQLiteAdapter(this.ctx);
		result += " INNER JOIN ";
		result += motherAdapt.getJoinedTableName();
		result += " <#if InheritanceUtils.isExtended(entities[curr.extends])>AND<#else>ON</#if> ";
		result += ALIASED_COL_ID + " = " + ${curr.extends}SQLiteAdapter.ALIASED_COL_ID;
		</#if>
		return result;
	}
	
	/**
	 * Get the column names from the ${curr.name} entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return ALIASED_COLS;
	}

	/** 
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
<#list curr.fields?values as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
		<#if (lastLine??)>${lastLine},"</#if>
		<#assign lastLine=" + " + NamingUtils.alias(field.name) + "	+ \"" + field.schema />
	</#if>
</#list>
		${lastLine}<#if MetadataUtils.hasRelationOrIds(curr)>,</#if>"
<#if (curr.relations??)>
	<#list curr.relations as relation>
		<#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
		<#if (lastRelation??)>${lastRelation},"</#if>
			<#assign lastRelation=" + \"FOREIGN KEY(\" + " + NamingUtils.alias(relation.name) 
			+ " + \") REFERENCES \" \n\t\t\t + " + relation.relation.targetEntity 
			+ "SQLiteAdapter.TABLE_NAME \n\t\t\t\t+ \" (\" + " + relation.relation.targetEntity 
			+ "SQLiteAdapter." + NamingUtils.alias(relation.relation.field_ref[0]) + " + \")">
		</#if>
	</#list>
		<#if (lastRelation??)>${lastRelation}<#if (curr.ids?size>1)>,</#if>"</#if>
</#if>
<#if (curr.ids?size>1)>
		+ "PRIMARY KEY (" + <#list curr.ids as id>${NamingUtils.alias(id.name)}<#if (id_has_next)> + "," + </#if></#list> + ")"
</#if>
<#if (InheritanceUtils.isExtended(curr))>
		+ ", FOREIGN KEY (" + COL_ID + ") REFERENCES " + ${curr.extends}SQLiteAdapter.TABLE_NAME + "(" + ${curr.extends}SQLiteAdapter.COL_ID + ") ON DELETE CASCADE"
</#if>
		+ ");";
	}
	
		
	/** 
	 * Constructor.
	 * @param ctx context
	 */
	public ${curr.name}SQLiteAdapterBase(final Context ctx) {	
		super(ctx);
	}
	
<#if (curr.internal!="true")>
	// Converters
	<#if (hasInternalFields)>
	/** Convert ${curr.name} entity to Content Values for database.
	 * 
	 * @param item ${curr.name} entity object<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>
	 * @param ${relation.relation.targetEntity?lower_case}Id ${relation.relation.targetEntity?lower_case} id</#if></#list>
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ${curr.name} item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 
				int ${relation.relation.targetEntity?lower_case}Id</#if></#list>) {
		final ContentValues result = this.itemToContentValues(item);		
	<#list curr.fields?values as field>
		<#if (field.internal)>
		result.put(${NamingUtils.alias(field.name)},
				String.valueOf(${field.relation.targetEntity?lower_case}Id));
		</#if>
	</#list>

		
		return result;
	}
	</#if>

	/** 
	 * Convert ${curr.name} entity to Content Values for database.
	 * @param item ${curr.name} entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ${curr.name} item) {
		final ContentValues result = new ContentValues();
		<#if (InheritanceUtils.isExtended(curr))>
		${curr.extends?cap_first}SQLiteAdapter motherAdapt = new ${curr.extends?cap_first}SQLiteAdapter(this.ctx);
		result.putAll(motherAdapt.itemToContentValues(item));
		</#if>
	<#list curr.fields?values as field>
		<#if (!field.internal)>
			<#if (!field.relation??)>
				<#if (MetadataUtils.isPrimitive(field))>
		result.put(${NamingUtils.alias(field.name)},
				${m.typeToParser("item", field)});
				<#else>
		if (item.get${field.name?cap_first}() != null) {
			result.put(${NamingUtils.alias(field.name)},
					${m.typeToParser("item", field)});
		}
				</#if>
			<#else>
				<#if (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
		if (item.get${field.name?cap_first}() != null) {
			result.put(${NamingUtils.alias(field.name)},
					String.valueOf(item.get${field.name?cap_first}().getId()));
		}
		
				</#if>
			</#if>
		</#if>
	</#list>

		
		return result;
	}

	/** 
	 * Convert Cursor of database to ${curr.name} entity.
	 * @param cursor Cursor object
	 * @return ${curr.name} entity
	 */
	public ${curr.name} cursorToItem(final Cursor cursor) {
		${curr.name} result = new ${curr.name}();
		this.cursorToItem(cursor, result);
		return result;
	}
	
	/** 
	 * Convert Cursor of database to ${curr.name} entity.
	 * @param cursor Cursor object
	 * @param result ${curr.name} entity
	 */
	public void cursorToItem(final Cursor cursor, final ${curr.name} result) {
		if (cursor.getCount() != 0) {
			<#if (InheritanceUtils.isExtended(curr))>
			${curr.extends}SQLiteAdapter motherAdapt = new ${curr.extends}SQLiteAdapter(this.ctx);
			motherAdapt.cursorToItem(cursor, result);			

			</#if>			
			int index;
	<#list curr.fields?values as field>
		<#if (!field.internal && !(field.relation?? && (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")))>
			<#assign t="" />
			index = cursor.getColumnIndexOrThrow(${NamingUtils.alias(field.name)});
			<#if (field.nullable?? && field.nullable)>
			if (!cursor.isNull(index)) {<#assign t="\t" />
			</#if>
			<#if (!field.relation??)>
				<#if (field.type?lower_case == "datetime") >
					<#if ((field.harmony_type == "date") || (field.harmony_type == "datetime") || (field.harmony_type == "time"))> 
			
						<#if field.is_locale>
			${t}final DateTime dt${field.name?cap_first} = 
					DateUtils.formatLocalISOStringToDateTime(
							cursor.getString(index));	
						<#else>
			${t}final DateTime dt${field.name?cap_first} = 
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));	
						</#if>
				${t}if (dt${field.name?cap_first} != null) {
					${t}result.set${field.name?cap_first}(
							dt${field.name?cap_first});
				${t}} else {
				${t}result.set${field.name?cap_first}(new DateTime());
			${t}}
					</#if>
				<#elseif (field.type?lower_case == "boolean")>
			${t}result.set${field.name?cap_first}(
					cursor.getString(index).equals("true"));
				<#elseif (field.type?lower_case == "int" || field.type?lower_case == "integer" || field.type == "ean" || field.type == "zipcode")>
			${t}result.set${field.name?cap_first}(
					cursor.getInt(index));
				<#elseif (field.type?lower_case == "float")>
			${t}result.set${field.name?cap_first}(
					cursor.getFloat(index));
				<#elseif (field.type?lower_case == "double")>
			${t}result.set${field.name?cap_first}(
					cursor.getDouble(index));
				<#elseif (field.type?lower_case == "long")>
			${t}result.set${field.name?cap_first}(
					cursor.getLong(index));
				<#elseif (field.type?lower_case == "short")>
			${t}result.set${field.name?cap_first}(
					cursor.getShort(index));
				<#elseif (field.type?lower_case == "char" || field.type?lower_case == "character")>
			String ${field.name?uncap_first}DB = cursor.getString(index);
			if (${field.name?uncap_first}DB != null 
				&& ${field.name?uncap_first}DB.length() > 0) {
				${t}result.set${field.name?cap_first}(
					${field.name?uncap_first}DB.charAt(0));
			}
				<#elseif (field.type?lower_case == "byte")>
			${t}result.set${field.name?cap_first}(Byte.valueOf(
					cursor.getString(index)));
				<#elseif (field.type?lower_case == "string")>
			${t}result.set${field.name?cap_first}(
					cursor.getString(index)); 
				<#elseif (field.harmony_type?lower_case == "enum")>
					<#assign enumType = enums[field.type] />
					<#if enumType.id??> <#-- If an Id has been declared in the enum -->
						<#assign idEnum = enumType.fields[enumType.id] />
						<#if (idEnum.type?lower_case == "int" || idEnum.type?lower_case == "integer") >
			${t}result.set${field.name?cap_first}(
				${field.type}.fromValue(cursor.getInt(index))); 
						<#else>
			${t}result.set${field.name?cap_first}(
				${field.type}.fromValue(cursor.getString(index))); 
						</#if>
					<#else> <#-- If not, use the enum name -->
			${t}result.set${field.name?cap_first}(
				${field.type}.valueOf(cursor.getString(index))); 	

					</#if>
				</#if>
			<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			${t}final ${field.type} ${field.name} = new ${field.type}();
			${t}${field.name}.setId(cursor.getInt(index));
			${t}result.set${field.name?cap_first}(${field.name});	
			</#if>
			<#if (field.nullable?? && field.nullable)>
			}
			</#if>
		</#if>
	</#list>
		}
	}
	
	//// CRUD Entity ////
	/** 
	 * Find & read ${curr.name} by id in database.
	 * 
	 * @param id Identify of ${curr.name}
	 * @return ${curr.name} entity
	 */
	public ${curr.name} getByID(<#list curr.ids as id>final ${m.javaType(id.type)} ${id.name}<#if (id_has_next)>
							,</#if></#list>) {
	<#if (curr.ids?size>0)>
		final Cursor cursor = this.getSingleCursor(<#list curr.ids as id>${id.name}<#if (id_has_next)>,
										</#if></#list>);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}
		
		final ${curr.name} result = this.cursorToItem(cursor);
		cursor.close();
		
		<#list curr.relations as relation>
			<#if (!relation.internal)>
				<#if (relation.relation.type=="OneToMany")>
		final ${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
				new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter
					.getBy${relation.relation.mappedBy?cap_first}(result.getId()));
				<#elseif (relation.relation.type=="ManyToMany")>
		${relation.relation.joinTable}SQLiteAdapter ${relation.relation.joinTable?lower_case}Adapter = 
				new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
		${relation.relation.joinTable?lower_case}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(
					${relation.relation.joinTable?lower_case}Adapter.getBy${curr.name}(
							result.getId())); // relation.relation.inversedBy?cap_first
				<#else>
		if (result.get${relation.name?cap_first}() != null) {
			final ${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			result.set${relation.name?cap_first}(
					${relation.name?uncap_first}Adapter.getByID(
							result.get${relation.name?cap_first}().getId()));
		}
				</#if>
			</#if>
		</#list>
		return result;
	<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
	</#if>
	}	
	
	<#if (curr.relations??)>
		<#list curr.relations as relation>
			<#if (relation.relation.type=="ManyToOne" | relation.relation.type=="OneToOne")>
	/** 
	 * Find & read ${curr.name} by ${relation.name}.
	 * @param ${relation.name?lower_case}Id ${relation.name?lower_case}Id
	 * @return List of ${curr.name} entities
	 */
	 public ArrayList<${curr.name}> getBy${relation.name?cap_first}(final int ${relation.name?lower_case}Id) {
		final Cursor cursor = this.query(ALIASED_COLS,
				${NamingUtils.alias(relation.name)} + "=?", 
				new String[]{Integer.toString(${relation.name?lower_case}Id)}, 
				null,
				null,
				null);
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();
		
		return result;
	 }
	 
			<#elseif (relation.relation.type=="ManyToMany")>	<#--
	/** 
	 * Find & read ${curr.name} by ${relation.name}.
	 * @param ${relation.name?lower_case}Id ${relation.name?lower_case}Id
	 * @return List of ${curr.name} entities
	 */
	public ArrayList<${curr.name}> getBy${relation.name?cap_first}(int ${relation.name?lower_case}Id) {
		final Cursor cursor = this.getCursor(${NamingUtils.alias(relation.name)} + "=?", 
				new String[]{Integer.toString(${relation.name?lower_case}Id)});
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();
		
		return result;
	 }		
			
			--></#if>
		</#list>
	</#if>
	
	/** 
	 * Read All ${curr.name}s entities.
	 * 
	 * @return List of ${curr.name} entities
	 */
	public ArrayList<${curr.name}> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();		
	<#if (relations??)>
		<#list curr.relations as relation>
			<#if (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter adapt${relation.relation.targetEntity} = 
				new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
		adapt${relation.relation.targetEntity}.open(this.mDatabase);
		for (${curr.name} ${curr.name?lower_case} : result) {
			${curr.name?lower_case}.set${relation.name?cap_first}(
					adapt${relation.relation.targetEntity}.getBy${curr.name}(
							${curr.name?lower_case}.getId())); // relation.relation.inversedBy?cap_first
		}
		
			</#if>
		</#list>
	</#if>
		
		return result;
	}

	
	/**
	 * Insert a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return Id of the ${curr.name} entity
	 */
	public long insert(final ${curr.name} item) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}
		
		final ContentValues values = 
				this.itemToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
	<#list curr.ids as id>
		values.remove(${NamingUtils.alias(id.name)});
	</#list>
	<#if !InheritanceUtils.isExtended(curr)>
		int newid;
	<#else>
		${curr.extends}SQLiteAdapter motherAdapt = new ${curr.extends}SQLiteAdapter(this.ctx);
		motherAdapt.open(this.mDatabase);
		final ContentValues currentValues = 
				this.extractContentValues(values);
		int newid = (int) motherAdapt.insert(null, values);
		currentValues.put(COL_ID, newid);
	</#if>
		if (values.size() != 0) {
			<#if !InheritanceUtils.isExtended(curr)>newid = (int) </#if>this.insert(
					null, 
					<#if InheritanceUtils.isExtended(curr)>currentValues<#else>values</#if>);
			
			item.setId((int) newid); 
	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToMany")>
			
			${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = 
					new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()) {
				${relation.name?uncap_first}Adapter.insert(newid, 
						i.get${relation.relation.field_ref[0]?cap_first}());
			}
		<#elseif (relation.relation.type=="OneToMany")>
			${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = 
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			if (item.get${relation.name?cap_first}() != null) {
				for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} 
							: item.get${relation.name?cap_first}()) {
				<#if (relation.relation.mappedBy?? && !MetadataUtils.getMappedField(relation).internal)>
					${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
					${relation.name?uncap_first}Adapter.insertOrUpdate(${relation.relation.targetEntity?lower_case});
				<#else>
					${relation.name?uncap_first}Adapter.insertOrUpdateWith${curr.name?cap_first}${relation.name?cap_first}(
										${relation.relation.targetEntity?lower_case}, 
										newid);
				</#if>
				}
			}
		</#if>
	</#list>
		} else {
			newid = -1;
		}
		
		return newid;
	}

	<#if (InheritanceUtils.isExtended(curr))>
	protected ContentValues extractContentValues(ContentValues from) {
		ContentValues to = new ContentValues();
		for (String colName : COLS) {
			if (from.containsKey(colName)) {
				this.transfer(from, to, colName, false);
			}
		}
		return to;
	}
	
	protected void transfer(ContentValues from, 
			ContentValues to,
			String colName,
			boolean keep) {
		to.put(colName, from.getAsString(colName));
		if (!keep) {
			from.remove(colName);
		}
	}
	</#if>

	/** 
	 * Either insert or update a ${curr.name} entity into database whether.
	 * it already exists or not.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final ${curr.name} item) {
		<#if (curr.ids?? && curr.ids?size > 0)>
		int result = 0;
		<#assign id = curr.ids[0] />
		if (this.getByID(item.get${id.name?cap_first}()) != null) {
			// Item already exists => update it
			result = this.update(item);
		} else {
			// Item doesn't exist => create it
			final long id = this.insert(item);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
		<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
		</#if> 
	}

	/** 
	 * Update a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @return count of updated entities
	 */
	public int update(final ${curr.name} item) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}
		
		final ContentValues values = 
				this.itemToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);	
		final String whereClause = 
				<#list curr.ids as id> ${NamingUtils.alias(id.name)} 
				 + "=? <#if id_has_next>AND </#if>"</#list>;
		final String[] whereArgs = 
				new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, 
							  </#if></#list>};
		
		<#if (InheritanceUtils.isExtended(curr))>
		final ContentValues currentValues = 
				this.extractContentValues(values);
		final ${curr.extends?cap_first}SQLiteAdapter motherAdapt = 
				new ${curr.extends?cap_first}SQLiteAdapter(this.ctx);
		motherAdapt.open(this.mDatabase);
		motherAdapt.update(values, whereClause, whereArgs);
		
		return this.update(
				currentValues, 
				whereClause, 
				whereArgs);
		<#else>
		return this.update(
				values, 
				whereClause, 
				whereArgs);
		</#if>
		
	<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
	</#if>
	}

	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToOne" && relation.internal)>
			
	/** 
	 * Update a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @param ${relation.relation.targetEntity?lower_case}Id The ${relation.relation.targetEntity?lower_case} id
	 * @return count of updated entities
	 */
	public int updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
					${curr.name} item, int ${relation.relation.targetEntity?lower_case}Id) {
			<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		ContentValues values = 
				this.itemToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, 
							${relation.relation.targetEntity?lower_case}Id<#else>, 0</#if></#if></#list>);	
		String whereClause = 
				<#list curr.ids as id> ${NamingUtils.alias(id.name)} 
				 + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = 
				new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>,
				</#if></#list>};

		return this.update(
				values, 
				whereClause, 
				whereArgs);
			<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
			</#if>
	}


	/** 
	 * Either insert or update a ${curr.name} entity into database whether.
	 * it already exists or not.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @param ${relation.relation.targetEntity?lower_case}Id The ${relation.relation.targetEntity?lower_case} id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
			${curr.name} item, int ${relation.relation.targetEntity?lower_case}Id) {
		int result = 0;
		<#assign id = curr.ids[0] />
		if (this.getByID(item.get${id.name?cap_first}()) != null) {
			// Item already exists => update it
			result = this.updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(item, 
					${relation.relation.targetEntity?lower_case}Id);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(item, 
					${relation.relation.targetEntity?lower_case}Id);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}

	
	/** 
	 * Insert a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @param ${relation.relation.targetEntity?lower_case}Id The ${relation.relation.targetEntity?lower_case} id
	 * @return Id of the ${curr.name} entity
	 */
	public long insertWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
			${curr.name} item, int ${relation.relation.targetEntity?lower_case}Id) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}
		
		ContentValues values = this.itemToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, 
				${relation.relation.targetEntity?lower_case}Id<#else>, 
				0</#if></#if></#list>);
	<#list curr.ids as id>
		values.remove(${NamingUtils.alias(id.name)});
	</#list>
		int newid = (int)this.insert(
			null, 
			values);
	
	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToMany")>
			
		${relation.relation.joinTable}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
				new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
		for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()) {
			${relation.name?uncap_first}Adapter.insert(newid, i.get${relation.relation.field_ref[0]?cap_first}());
		}
		<#elseif (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
				new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()) {
			<#if (relation.relation.mappedBy?? && !getMappedField(relation).internal)>
			${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(
					${relation.relation.targetEntity?lower_case});
			<#else>
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(
					${relation.relation.targetEntity?lower_case}, newid);
			</#if>
		}
		
		</#if>
	</#list>
		
		return newid;
	}			

		</#if>
	</#list>
	
	/** 
	 * Delete a ${curr.name} entity of database.
	 * 
	 <#list curr.ids as id>* @param ${id.name} ${id.name}
	 </#list>
	 * @return count of updated entities
	 */
	public int remove(<#list curr.ids as id>final ${m.javaType(id.type)} ${id.name}<#if (id_has_next)>
			,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME 
					+ ") id : " + <#list curr.ids as id>${id.name}<#if (id_has_next)>
					+ " id : " + </#if></#list>);
		}
		
		final String whereClause = <#list curr.ids as id> ${NamingUtils.alias(id.name)} 
					 + "=? <#if (id_has_next)>AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if (id_has_next)>,
					</#if></#list>};
		
		return this.delete(
				whereClause, 
				whereArgs);
	<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
	</#if>
	}
	
	/**
	 * Deletes the given entity.
	 * @param ${curr.name?uncap_first} The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final ${curr.name?cap_first} ${curr.name?uncap_first}) {
		return this.delete(${curr.name?uncap_first}.getId());
	}
	
	/**
	 *  Internal Cursor.
	 <#list curr.ids as id>* @param ${id.name} ${id.name}
	 </#list>
	 *  @return A Cursor pointing to the ${curr.name} corresponding 
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(<#list curr.ids as id>final ${m.javaType(id.type)} ${id.name}<#if id_has_next>
										,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + <#list curr.ids as id>${id.name}<#if id_has_next> 
					 + " id : " + </#if></#list>);
		}
		
		final String whereClause = <#list curr.ids as id> ALIASED_${NamingUtils.alias(id.name)} 
					 + "=? <#if id_has_next>AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if id_has_next>, 
					</#if></#list>};
		
		return this.query(ALIASED_COLS, 
				whereClause, 
				whereArgs, 
				null, 
				null, 
				null);
	<#else>
		throw new NotImplementedException(
				"An entity with no ID can't implement this method.");
	</#if>
	}
	
</#if>
	
	/**
	 * Query the DB to find a ${curr.name} entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result 
	 */
	public Cursor query(final int id) {
		<#if curr.ids?size==0>
			throw new NotImplementedException(
				"An entity with no ID can't implement this method.");
		<#else>
		return this.query(
				ALIASED_COLS,
				ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)},
				null,
				null,
				null);
		</#if>
	}
	
	/**
	 * Deletes the given entity.
	 * @param id The ID of the entity to delete
	 * @return the number of token deleted
	 */
	public int delete(final int id) {
		<#if curr.ids?size==0>
			throw new NotImplementedException(
				"An entity with no ID can't implement this method.");
		<#else>
		return this.delete(
				ALIASED_COL_ID + " = ?",
				new String[]{String.valueOf(id)});
		</#if>
	}
	
<#if (curr.internal=="true")>
	<#--<#list curr.relations as relation>
	/**
	 * get${relation.type}s.
	 * @param id id
	 * @return ArrayList of ${relation.type} matching id
	 */
	public ArrayList<${relation.type}> get${relation.type}s(final int id) {
		final ${relation.type}SQLiteAdapter adapt = 
				new ${relation.type}SQLiteAdapter(this.ctx);
		return adapt.getBy${curr.name}(id);
	}

	</#list>-->
		
	/** 
	 * Insert a ${curr.name} entity into database.
	 * 
	 * @param ${curr.relations[0].name?lower_case} ${curr.relations[0].name?lower_case}
	 * @param ${curr.relations[1].name?lower_case} ${curr.relations[1].name?lower_case}
	 * @return Id of the ${curr.name} entity
	 */
	public long insert(final int ${curr.relations[0].name?lower_case}, 
					   final int ${curr.relations[1].name?lower_case}) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}
		
		ContentValues values = new ContentValues();
		values.put(${NamingUtils.alias(curr.relations[0].name)}, 
				${curr.relations[0].name?lower_case});
		values.put(${NamingUtils.alias(curr.relations[1].name)}, 
				${curr.relations[1].name?lower_case});
		
		return this.mDatabase.insert(
				TABLE_NAME, 
				null, 
				values);
	}
	
	/** 
	 * Find & read ${curr.name} by ${curr.relations[0].name}.
     * @param ${curr.relations[1].name?lower_case} ${curr.relations[1].name?lower_case}
	 * @return ArrayList of ${curr.relations[0].relation.targetEntity} matching ${curr.relations[1].name?lower_case}
	 */ 
	public ArrayList<${curr.relations[0].relation.targetEntity}> getBy${curr.relations[1].relation.targetEntity}(
			final int ${curr.relations[1].name}) {
		String whereClause = ${NamingUtils.alias(curr.relations[1].name)} + "=?";
		String whereArg = String.valueOf(${curr.relations[1].name});
		Cursor cursor = this.query(this.getCols(), 
				whereClause, 
				new String[]{whereArg}, 
				null, 
				null, 
				null);
		${curr.relations[0].relation.targetEntity}SQLiteAdapter adapt = 
				new ${curr.relations[0].relation.targetEntity}SQLiteAdapter(this.ctx);
		adapt.open(this.mDatabase);
		final ArrayList<${curr.relations[0].relation.targetEntity}> ret = 
				new ArrayList<${curr.relations[0].relation.targetEntity}>();

		while (cursor.moveToNext()) {
			ret.add(adapt.getByID(cursor.getInt(
					cursor.getColumnIndexOrThrow(
							${NamingUtils.alias(curr.relations[0].name)}))));
		}
		return ret;
	}

	/** 
	 * Find & read ${curr.name} by ${curr.relations[0].name}.
     * @param ${curr.relations[0].name?lower_case} ${curr.relations[1].name?lower_case}
	 * @return ArrayList of ${curr.relations[1].relation.targetEntity} matching ${curr.relations[0].name?lower_case}
	 */ 
	public ArrayList<${curr.relations[1].relation.targetEntity}> getBy${curr.relations[0].relation.targetEntity}(
			final int ${curr.relations[0].name}) {
		String whereClause = ${NamingUtils.alias(curr.relations[0].name)} + "=?";
		String whereArg = String.valueOf(${curr.relations[0].name});
		Cursor cursor = this.query(this.getCols(), 
				whereClause, 
				new String[]{whereArg}, 
				null, 
				null, 
				null);
		${curr.relations[1].relation.targetEntity}SQLiteAdapter adapt = 
				new ${curr.relations[1].relation.targetEntity}SQLiteAdapter(this.ctx);
		adapt.open(this.mDatabase);
		final ArrayList<${curr.relations[1].relation.targetEntity}> ret = 
				new ArrayList<${curr.relations[1].relation.targetEntity}>();

		while (cursor.moveToNext()) {
			ret.add(adapt.getByID(cursor.getInt(
					cursor.getColumnIndexOrThrow(${NamingUtils.alias(
							curr.relations[1].name)}))));
		}
		return ret;
	}

	@Override
	public Void cursorToItem(Cursor c) {
		return null;
	}

	@Override
	public ContentValues itemToContentValues(Void item) {
		return null;
	}

	@Override
	public long insert(Void item) {
		return -1;
	}

	@Override
	public int update(Void item) {
		return 0;
	}

	@Override
	public int delete(Void item) {
		return 0;
	}
</#if>
}
