<#assign curr = entities[current_entity]>
<#import "methods.tpl" as m>

<#function alias name>
	<#return "COL_"+name?upper_case>
</#function>
<#function hasRelationOrIds>
	<#if (curr.ids?size>1)><#return true></#if>
	<#list curr.relations as relation>
		<#if relation.relation.type!="OneToMany" && relation.relation.type!="ManyToMany">
			<#return true>
		</#if>
	</#list>
	<#return false>
</#function>

<#function isInArray array var>
	<#list array as item>
		<#if item==var>
			<#return true>
		</#if>
	</#list>
	<#return false>
</#function>

package ${data_namespace};

import java.util.ArrayList;
import org.joda.time.DateTime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ${curr.namespace}.BuildConfig;
<#if curr.internal=="false">
import ${curr.namespace}.entity.${curr.name};
</#if>
<#assign import_array = []>
<#list curr.relations as relation>
	<#if !isInArray(import_array, relation.relation.targetEntity)>
		<#assign import_array = import_array + [relation.relation.targetEntity]>
import ${curr.namespace}.entity.${relation.relation.targetEntity};
	</#if>
</#list>
import ${curr.namespace}.harmony.util.DateUtils;

/** ${curr.name} adapter database abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}Adapter class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}SQLiteAdapterBase{
	private static final String TAG = "${curr.name}DatabaseAdapter";
	
	/** Table name of SQLite database */
	public static final String TABLE_NAME = "${curr.name}";
	
	// Columns constants fields mapping
<#list curr.fields as field>
	<#if !field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")>
	public static final String ${alias(field.name)} = "${field.columnName}";
	</#if>
</#list>
	
	/** Global Fields */
	public static final String[] COLS = new String[] {
<#assign firstFieldDone=false>
<#list curr.fields as field>
	<#if !field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")>
<#if firstFieldDone>,</#if>
		${alias(field.name)}<#assign firstFieldDone=true></#if></#list>
	};
	
	private Context context;

	/** Generate Entity Table Schema
	 * 
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static final String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
<#list curr.fields as field>
	<#if !field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")>
		<#if lastLine??>${lastLine},"</#if>
		<#assign lastLine="+ "+alias(field.name)+"	+ \""+field.schema>
	</#if>
</#list>
		${lastLine}<#if hasRelationOrIds()>,</#if>"
<#if curr.relations??>
	<#list curr.relations as relation>
		<#if lastRelation??>${lastRelation},"</#if>
		<#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
			<#assign lastRelation="+\"FOREIGN KEY(\"+"+alias(relation.name)+"+\") REFERENCES \"+"+relation.relation.targetEntity+"SQLiteAdapter.TABLE_NAME+\" (\"+"+relation.relation.targetEntity+"SQLiteAdapter."+alias(relation.relation.field_ref[0])+"+\")">
		</#if>
	</#list>
		<#if lastRelation??>${lastRelation}<#if (curr.ids?size>1)>,</#if>"</#if>
</#if>
<#if (curr.ids?size>1)>
		+ "PRIMARY KEY ("+<#list curr.ids as id>${alias(id.name)}<#if id_has_next>+","+</#if></#list>+")"
</#if>
		+ ");";
	}
	
	// Database tools
	protected SQLiteDatabase mDatabase;
	protected ${project_name?cap_first}SQLiteOpenHelper mBaseHelper;
		
	/** Constructor
	 * 
	 * @param ctx context
	 */
	public ${curr.name}SQLiteAdapterBase(Context ctx) {	
		this.context = ctx;
		this.mBaseHelper = new ${project_name?cap_first}SQLiteOpenHelper(
				ctx, 
				"database", 
				null,
				1);
	}
	
	/** Initialize and open database
	 * 
	 * @return Open database
	 */
	public SQLiteDatabase open() {
		this.mDatabase = this.mBaseHelper.getWritableDatabase();
		return this.mDatabase;
	}
	
	/** Initialize and open database
	 * 
	 * @return Open database
	 */
	public SQLiteDatabase open(SQLiteDatabase db) {
		this.mDatabase = db;
		return this.mDatabase;
	}

	/** Close database */
	public void close() {
		mDatabase.close();
	}
	
<#if curr.internal!="true">
	// Converters
	/** Convert ${curr.name} entity to Content Values for database
	 * 
	 * @param ${curr.name?lower_case} ${curr.name} entity object
	 * @return ContentValues object
	 */
	public static ContentValues ${curr.name?lower_case}ToContentValues(${curr.name} ${curr.name?lower_case}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, int ${relation.relation.targetEntity?lower_case}_id</#if></#list>) {		
		ContentValues result = new ContentValues();		
	<#list curr.fields as field>
		<#if !field.internal>
			<#if !field.relation??>
		result.put(${alias(field.name)}, 			${m.typeToParser(curr.name, field)} );				
			<#else>
				<#if (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
		result.put(${alias(field.name)}, 			String.valueOf(${curr.name?lower_case}.get${field.name?cap_first}().getId()) );
				</#if>
			</#if>
		<#else>
		result.put(${alias(field.name)},			String.valueOf(${field.relation.targetEntity?lower_case}_id));
		</#if>
	</#list>

		
		return result;
	}
	
	/** Convert Cursor of database to ${curr.name} entity
	 * 
	 * @param c Cursor object
	 * @return ${curr.name} entity
	 */
	public static ${curr.name} cursorTo${curr.name}(Cursor c) {
		${curr.name} result = null;

		if (c.getCount() != 0) {
			result = new ${curr.name}();			

	<#list curr.fields as field>
		<#if !field.internal>
			<#if !field.relation??>
				<#if (field.type == "date" || field.type == "datetime" || field.type = "time" )>
				
			try {
				result.set${field.name?cap_first}(new DateTime(
						DateUtils.formatISOStringToDateTime(c.getString( c.getColumnIndexOrThrow(COL_${field.name?upper_case})) )));
			} catch (Exception e) {
				e.printStackTrace();
				result.set${field.name?cap_first}(new DateTime());
			}

				<#elseif (field.type == "boolean" )>
			result.set${field.name?cap_first}  (c.getString( c.getColumnIndexOrThrow(COL_${field.name?upper_case}) ).equals("true"));
				<#elseif (field.type == "int" || field.type == "integer" || field.type == "ean" || field.type == "zipcode")>
			result.set${field.name?cap_first}(c.getInt( c.getColumnIndexOrThrow(COL_${field.name?upper_case}) ));
				<#elseif (field.type == "float" )>
			result.set${field.name?cap_first}(c.getFloat( c.getColumnIndexOrThrow(COL_${field.name?upper_case}) ));
				<#else>
			result.set${field.name?cap_first}(c.getString( c.getColumnIndexOrThrow(COL_${field.name?upper_case}) )); 
				</#if>
			<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			${field.type} ${field.name} = new ${field.type}();
			${field.name}.setId( Integer.valueOf(c.getString( c.getColumnIndexOrThrow(${alias(field.name)}) )) );
			result.set${field.name?cap_first}(${field.name});	
			</#if>
		</#if>
	</#list>
		}
		
		return result;
	}
	
	/** Convert Cursor of database to Array of ${curr.name} entity
	 * 
	 * @param c Cursor object
	 * @return Array of ${curr.name} entity
	 */
	public static ArrayList<${curr.name}> cursorTo${curr.name}s(Cursor c) {
		ArrayList<${curr.name}> result = new ArrayList<${curr.name}>(c.getCount());

		if (c.getCount() != 0) {
			c.moveToFirst();
			
			${curr.name} item;
			do {
				item = ${curr.name}SQLiteAdapterBase.cursorTo${curr.name}(c);
				result.add(item);
			} while (c.moveToNext());
			
			if (BuildConfig.DEBUG)
				Log.d(TAG, "Read DB(" + TABLE_NAME + ") count : " + c.getCount() );
		}

		return result;
	}
	
	//// CRUD Entity ////
	/** Find & read ${curr.name} by id in database
	 * 
	 * @param id Identify of ${curr.name}
	 * @return ${curr.name} entity
	 */
	public ${curr.name} getByID(<#list curr.ids as id>${m.javaType(id.type)} ${id.name}<#if id_has_next>,</#if></#list>) {
	<#if (curr.ids?size>0)>
		Cursor c = this.getSingleCursor(id);
		if(c.getCount()!=0)
			c.moveToFirst();
		${curr.name} result = ${curr.name}SQLiteAdapterBase.cursorTo${curr.name}(c);
		c.close();
		
		<#list curr.relations as relation>
			<#if relation.relation.type=="OneToMany">
		${relation.relation.targetEntity}SQLiteAdapter ${relation.relation.targetEntity?lower_case}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.relation.targetEntity?lower_case}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(${relation.relation.targetEntity?lower_case}Adapter.getBy${curr.name}(result.getId())); // relation.relation.inversedBy?cap_first
			<#elseif relation.relation.type=="ManyToMany">
		${relation.relation.joinTable}SQLiteAdapter ${relation.relation.joinTable?lower_case}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.context);
		${relation.relation.joinTable?lower_case}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(${relation.relation.joinTable?lower_case}Adapter.getBy${curr.name}(result.getId())); // relation.relation.inversedBy?cap_first
			</#if>
		</#list>
		return result;
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}
	
	<#if curr.relations??>
		<#list curr.relations as relation>
			<#if relation.relation.type=="ManyToOne" | relation.relation.type=="OneToOne">
	/** Find & read ${curr.name} by ${relation.name} 
	 * 
	 * @return List of ${curr.name} entities
	 */
	 public ArrayList<${curr.name}> getBy${relation.name?cap_first}(int ${relation.name?lower_case}_id){
		Cursor c = this.getCursor(${alias(relation.name)}+"=?", new String[]{${relation.name?lower_case}_id+""});
		ArrayList<${curr.name}> result = ${curr.name}SQLiteAdapterBase.cursorTo${curr.name}s(c);
		c.close();
		
		return result;
	 }
	 
			<#elseif relation.relation.type=="ManyToMany">	<#--
	public ArrayList<${curr.name}> getBy${relation.name?cap_first}(int ${relation.name?lower_case}_id){
		Cursor c = this.getCursor(${alias(relation.name)}+"=?", new String[]{${relation.name?lower_case}_id+""});
		ArrayList<${curr.name}> result = ${curr.name}SQLiteAdapterBase.cursorTo${curr.name}s(c);
		c.close();
		
		return result;
	 }		
			
			--></#if>
		</#list>
	</#if>
	
	/** Read All ${curr.name}s entities
	 * 
	 * @return List of ${curr.name} entities
	 */
	public ArrayList<${curr.name}> getAll() {
		Cursor c = this.getAllCursor();
		ArrayList<${curr.name}> result = ${curr.name}SQLiteAdapterBase.cursorTo${curr.name}s(c);
		c.close();
		
	<#if relations??>
		<#list curr.relations as relation>
			<#if relation.relation.type=="OneToMany">
		${relation.relation.targetEntity}SQLiteAdapter adapt${relation.relation.targetEntity} = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		adapt${relation.relation.targetEntity}.open(this.mDatabase);
		for(${curr.name} ${curr.name?lower_case} : result){
			${curr.name?lower_case}.set${relation.name?cap_first}(adapt${relation.relation.targetEntity}.getBy${curr.name}(${curr.name?lower_case}.getId())); // relation.relation.inversedBy?cap_first
		}
		
			</#if>
		</#list>
	</#if>
		
		return result;
	}

	
	/** Insert a ${curr.name} entity into database
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return Id of the ${curr.name} entity
	 */
	public long insert(${curr.name} item) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${curr.name}SQLiteAdapterBase.${curr.name?lower_case}ToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
	<#list curr.ids as id>
		values.remove(${alias(id.name)});
	</#list>
		int newid = (int)this.mDatabase.insert(
			TABLE_NAME, 
			null, 
			values);
	
	<#list curr.relations as relation>
		<#if relation.relation.type=="ManyToMany">
			
		${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		for(${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()){
			${relation.name?uncap_first}Adapter.insert(newid, i.get${relation.relation.field_ref[0]?cap_first}());
		}
		<#elseif relation.relation.type=="OneToMany">
		${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		for(${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()){
			<#if relation.relation.mappedBy??>
			${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(${relation.relation.targetEntity?lower_case});
			<#else>
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(${relation.relation.targetEntity?lower_case}, newid);
			</#if>
		}
		
		</#if>
	</#list>
		
		return newid;
	}
	
	/** Update a ${curr.name} entity into database 
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @return 
	 */
	public int update(${curr.name} item) {
	<#if (curr.ids?size>0)>
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${curr.name}SQLiteAdapterBase.${curr.name?lower_case}ToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);	
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.update(
				TABLE_NAME, 
				values, 
				whereClause, 
				whereArgs);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}

	<#list curr.relations as relation>
		<#if relation.relation.type=="ManyToOne" && relation.internal>
			
	/** Update a ${curr.name} entity into database 
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @return 
	 */
	public int updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(${curr.name} item, int ${relation.relation.targetEntity?lower_case}_id) {
			<#if (curr.ids?size>0)>
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");

		ContentValues values = ${curr.name}SQLiteAdapterBase.${curr.name?lower_case}ToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, ${relation.relation.targetEntity?lower_case}_id<#else>, 0</#if></#if></#list>);	
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, </#if></#list>};

		return this.mDatabase.update(
				TABLE_NAME, 
				values, 
				whereClause, 
				whereArgs);
			<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
			</#if>
	}

	
	/** Insert a ${curr.name} entity into database
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return Id of the ${curr.name} entity
	 */
	public long insertWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(${curr.name} item, int ${relation.relation.targetEntity?lower_case}_id) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${curr.name}SQLiteAdapterBase.${curr.name?lower_case}ToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, ${relation.relation.targetEntity?lower_case}_id<#else>, 0</#if></#if></#list>);
	<#list curr.ids as id>
		values.remove(${alias(id.name)});
	</#list>
		int newid = (int)this.mDatabase.insert(
			TABLE_NAME, 
			null, 
			values);
	
	<#list curr.relations as relation>
		<#if relation.relation.type=="ManyToMany">
			
		${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.context);
		for(${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()){
			${relation.name?uncap_first}Adapter.insert(newid, i.get${relation.relation.field_ref[0]?cap_first}());
		}
		<#elseif relation.relation.type=="OneToMany">
		${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		for(${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()){
			<#if relation.relation.mappedBy??>
			${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(${relation.relation.targetEntity?lower_case});
			<#else>
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(${relation.relation.targetEntity?lower_case}, newid);
			</#if>
		}
		
		</#if>
	</#list>
		
		return newid;
	}			

		</#if>
	</#list>
	
	/** Delete a ${curr.name} entity of database
	 * 
	 * @param id Identify the ${curr.name} entity to delete
	 * @return
	 */
	public int remove(<#list curr.ids as id>${m.javaType(id.type)} ${id.name}<#if id_has_next>,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Delete DB(" + TABLE_NAME + ") id : "+ <#list curr.ids as id>${id.name}<#if id_has_next> + " id : " + </#if></#list>);
		
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.delete(
				TABLE_NAME, 
				whereClause, 
				whereArgs);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}
	
	// Internal Cursor
	protected Cursor getSingleCursor(<#list curr.ids as id>${m.javaType(id.type)} ${id.name}<#if id_has_next>,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Get entities id : " + <#list curr.ids as id>${id.name}<#if id_has_next> + " id : " + </#if></#list>);
		
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return getCursor(whereClause, whereArgs);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}
	
	protected Cursor getAllCursor() {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Get all entities");
		
		return getCursor(null, null);
	}
	
</#if>

	protected Cursor getCursor(String whereClause, String[] whereArgs){
		return this.mDatabase.query(
				TABLE_NAME,
				COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}
	
<#if curr.internal=="true">
	<#--<#list curr.relations as relation>
	public ArrayList<${relation.type}> get${relation.type}s(int id){
		${relation.type}SQLiteAdapter adapt = new ${relation.type}SQLiteAdapter(this.context);
		return adapt.getBy${curr.name}(id);
	}

	</#list>-->
		
	/** Insert a ${curr.name} entity into database
	 * 
	 * param item The ${curr.name} entity to persist 
	 * return Id of the ${curr.name} entity
	 */
	public long insert(int ${curr.relations[0].name?lower_case}, int ${curr.relations[1].name?lower_case}) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = new ContentValues();
		values.put(${alias(curr.relations[0].name)}, ${curr.relations[0].name?lower_case});
		values.put(${alias(curr.relations[1].name)}, ${curr.relations[1].name?lower_case});
		
		return this.mDatabase.insert(
				TABLE_NAME, 
				null, 
				values);
	}
	
	<#--/** Find & read ${curr.name} by ${curr.relations[0].name}v*/
	public ArrayList<${curr.relations[0].relation.targetEntity}> getBy${curr.relations[1].relation.targetEntity?cap_first}(int ${curr.relations[1].name?lower_case}){
		Cursor c = this.getCursor(${alias(curr.relations[1].name)}+"=?", new String[]{${curr.relations[1].name?lower_case}+""});
		ArrayList<${curr.name}> result = ${curr.name}SQLiteAdapterBase.cursorTo${curr.name}s(c);
		c.close();
		
		return result;
	}-->
	 
	public ArrayList<${curr.relations[0].relation.targetEntity}> getBy${curr.relations[1].relation.targetEntity}(int ${curr.relations[1].name}){
		String whereClause = ${alias(curr.relations[1].name)}+"=?";
		String whereArg = String.valueOf(${curr.relations[1].name});
		Cursor c = getCursor(whereClause, new String[]{whereArg});
		${curr.relations[0].relation.targetEntity}SQLiteAdapter adapt = new ${curr.relations[0].relation.targetEntity}SQLiteAdapter(this.context);
		adapt.open(this.mDatabase);
		ArrayList<${curr.relations[0].relation.targetEntity}> ret = new ArrayList<${curr.relations[0].relation.targetEntity}>();

		while(c.moveToNext()){
			ret.add(adapt.getByID(c.getInt( c.getColumnIndexOrThrow(${alias(relations[0].name)}) )));
		}
		return ret;
	}


	public ArrayList<${curr.relations[1].relation.targetEntity}> getBy${curr.relations[0].relation.targetEntity}(int ${curr.relations[0].name}){
		String whereClause = ${alias(curr.relations[0].name)}+"=?";
		String whereArg = String.valueOf(${curr.relations[0].name});
		Cursor c = getCursor(whereClause, new String[]{whereArg});
		${curr.relations[1].relation.targetEntity}SQLiteAdapter adapt = new ${curr.relations[1].relation.targetEntity}SQLiteAdapter(this.context);
		adapt.open(this.mDatabase);
		ArrayList<${curr.relations[1].relation.targetEntity}> ret = new ArrayList<${curr.relations[1].relation.targetEntity}>();

		while(c.moveToNext()){
			ret.add(adapt.getByID(c.getInt( c.getColumnIndexOrThrow(${alias(relations[1].name)}) )));
		}
		return ret;
	}
	
</#if>
}
