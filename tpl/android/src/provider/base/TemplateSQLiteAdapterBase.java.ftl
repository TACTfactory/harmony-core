<#assign curr = entities[current_entity] />
<#assign sync = curr.options.sync?? />
<#import "methods.ftl" as m />
<#function alias name>
	<#return "COL_"+name?upper_case />
</#function>
<#function hasRelationOrIds>
	<#if (curr.ids?size>1)><#return true /></#if>
	<#list curr.relations as relation>
		<#if (relation.relation.type!="OneToMany" && relation.relation.type!="ManyToMany")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function isInArray array var>
	<#list array as item>
		<#if (item==var)>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function getMappedField field>
	<#assign ref_entity = entities[field.relation.targetEntity] />
	<#list ref_entity.fields as ref_field>
		<#if ref_field.name == field.relation.mappedBy>
			<#return ref_field />
		</#if>
	</#list>
</#function>
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#list curr.fields as field>
	<#if field.type=="date">
		<#assign hasDate=true />
	<#elseif field.type=="time">
		<#assign hasTime=true />
	<#elseif field.type="datetime">
		<#assign hasDateTime=true />
	</#if>
</#list>
package ${data_namespace}.base;

<#if (curr.relations?size>0)>
import ${data_namespace}.*;
</#if>
import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import ${project_namespace}.${project_name?cap_first}Application;
<#if (curr.internal=="false")>
import ${curr.namespace}.entity.${curr.name};
</#if>
<#assign import_array = [curr.name] />
<#list curr.relations as relation>
	<#if !relation.internal>
		<#if (!isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
import ${curr.namespace}.entity.${relation.relation.targetEntity};
		</#if>
	</#if>
</#list>
<#if hasDate || hasTime || hasDateTime>
import ${curr.namespace}.harmony.util.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
</#if>
<#if curr.internal?? && curr.internal=='true'>
	<#assign extendType = "Void" />
<#else>
	<#assign extendType = curr.name />
</#if>
<#if sync>
	<#assign extend="SyncSQLiteAdapterBase<"+extendType+">" />
<#else>
	<#assign extend="SQLiteAdapterBase<"+extendType+">" />
</#if>
/** ${curr.name} adapter database abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}Adapter class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}SQLiteAdapterBase extends ${extend}{
	protected static final String TAG = "${curr.name}DBAdapter";
	
	/** Table name of SQLite database */
	public static final String TABLE_NAME = "${curr.name}";
	
	// Columns constants fields mapping
<#list curr.fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
	public static final String ${alias(field.name)} = "${field.columnName}";
	</#if>
</#list>
	
	/** Global Fields */
	public static final String[] COLS = new String[] {
<#assign firstFieldDone=false />
<#list curr.fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
		${alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
	};
	
	public String getTableName(){
		return TABLE_NAME;
	}
	
	public String[] getCols(){
		return COLS;
	}

	/** Generate Entity Table Schema
	 * 
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static final String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
<#list curr.fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
		<#if (lastLine??)>${lastLine},"</#if>
		<#assign lastLine="+ "+alias(field.name)+"	+ \""+field.schema />
	</#if>
</#list>
		${lastLine}<#if hasRelationOrIds()>,</#if>"
<#if (curr.relations??)>
	<#list curr.relations as relation>
		<#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
			<#if (lastRelation??)>${lastRelation},"</#if>
			<#assign lastRelation="+\"FOREIGN KEY(\"+"+alias(relation.name)+"+\") REFERENCES \"+"+relation.relation.targetEntity+"SQLiteAdapter.TABLE_NAME+\" (\"+"+relation.relation.targetEntity+"SQLiteAdapter."+alias(relation.relation.field_ref[0])+"+\")">
		</#if>
	</#list>
		<#if (lastRelation??)>${lastRelation}<#if (curr.ids?size>1)>,</#if>"</#if>
</#if>
<#if (curr.ids?size>1)>
		+ "PRIMARY KEY ("+<#list curr.ids as id>${alias(id.name)}<#if (id_has_next)>+","+</#if></#list>+")"
</#if>
		+ ");";
	}
	
		
	/** Constructor
	 * 
	 * @param ctx context
	 */
	public ${curr.name}SQLiteAdapterBase(Context ctx) {	
		super(ctx);
	}
	
<#if (curr.internal!="true")>
	// Converters
	/** Convert ${curr.name} entity to Content Values for database
	 * 
	 * @param ${curr.name?lower_case} ${curr.name} entity object
	 * @return ContentValues object
	 */
	public static ContentValues itemToContentValues(${curr.name} item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, int ${relation.relation.targetEntity?lower_case}_id</#if></#list>) {		
		ContentValues result = new ContentValues();		
	<#list curr.fields as field>
		<#if (!field.internal)>
			<#if (!field.relation??)>
		result.put(${alias(field.name)}, 			${m.typeToParser("item", field)} );				
			<#else>
				<#if (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
		result.put(${alias(field.name)}, 			String.valueOf(item.get${field.name?cap_first}().getId()) );
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
	public ${curr.name} cursorToItem(Cursor c) {
		${curr.name} result = null;

		if (c.getCount() != 0) {
			result = new ${curr.name}();
			
			int index;
	<#list curr.fields as field>
		<#if (!field.internal && !(field.relation?? && (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")))>
			<#assign t="" />
			index = c.getColumnIndexOrThrow(${alias(field.name)});
			<#if (field.nullable?? && field.nullable)>
			if (!c.isNull(index)){<#assign t="\t" />
			</#if>
			<#if (!field.relation??)>
				<#if ((field.type == "date") || (field.type == "datetime") || (field.type == "time"))> 
			
					<#if field.is_locale>
			${t}DateTime dt${field.name?cap_first} = DateUtils.formatLocalISOStringToDateTime(c.getString(index) );	
					<#else>
			${t}DateTime dt${field.name?cap_first} = DateUtils.formatISOStringToDateTime(c.getString(index) );	
					</#if>
				${t}if (dt${field.name?cap_first} != null){
					${t}result.set${field.name?cap_first}(dt${field.name?cap_first});
				${t}} else {
				${t}result.set${field.name?cap_first}(new DateTime());
			${t}}
				<#elseif (field.type == "boolean")>
			${t}result.set${field.name?cap_first}  (c.getString(index).equals("true"));
				<#elseif (field.type == "int" || field.type == "integer" || field.type == "ean" || field.type == "zipcode")>
			${t}result.set${field.name?cap_first}(c.getInt(index));
				<#elseif (field.type == "float")>
			${t}result.set${field.name?cap_first}(c.getFloat(index));
				<#elseif (field.type?lower_case=="string")>
			${t}result.set${field.name?cap_first}(c.getString(index)); 
				<#else>
					<#if field.columnDefinition?lower_case=="integer" || field.columnDefinition?lower_case=="int">
			${t}result.set${field.name?cap_first}(${curr.name}.${field.type}.fromValue(c.getInt(index))); 
					<#else>
			${t}result.set${field.name?cap_first}(${curr.name}.${field.type}.fromValue(c.getString(index))); 
					</#if>
				</#if>
			<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			${t}${field.type} ${field.name} = new ${field.type}();
			${t}${field.name}.setId(c.getInt(index));
			${t}result.set${field.name?cap_first}(${field.name});	
			</#if>
			<#if (field.nullable?? && field.nullable)>
			}
			</#if>
		</#if>
	</#list>
		}
		
		return result;
	}
	
	//// CRUD Entity ////
	/** Find & read ${curr.name} by id in database
	 * 
	 * @param id Identify of ${curr.name}
	 * @return ${curr.name} entity
	 */
	public ${curr.name} getByID(<#list curr.ids as id>${m.javaType(id.type)} ${id.name}<#if (id_has_next)>,</#if></#list>) {
	<#if (curr.ids?size>0)>
		Cursor c = this.getSingleCursor(<#list curr.ids as id>${id.name}<#if (id_has_next)>,</#if></#list>);
		if (c.getCount()!=0)
			c.moveToFirst();
		${curr.name} result = this.cursorToItem(c);
		c.close();
		
		<#list curr.relations as relation>
			<#if (!relation.internal)>
				<#if (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter.getBy${relation.relation.mappedBy?cap_first}(result.getId())); // relation.relation.inversedBy?cap_first
				<#elseif (relation.relation.type=="ManyToMany")>
		${relation.relation.joinTable}SQLiteAdapter ${relation.relation.joinTable?lower_case}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.context);
		${relation.relation.joinTable?lower_case}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(${relation.relation.joinTable?lower_case}Adapter.getBy${curr.name}(result.getId())); // relation.relation.inversedBy?cap_first
				<#else>
		${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter.getByID(result.get${relation.name?cap_first}().getId())); // relation.relation.inversedBy?cap_first		
				</#if>
			</#if>
		</#list>
		return result;
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}	
	
	<#if (curr.relations??)>
		<#list curr.relations as relation>
			<#if (relation.relation.type=="ManyToOne" | relation.relation.type=="OneToOne")>
	/** Find & read ${curr.name} by ${relation.name} 
	 * 
	 * @return List of ${curr.name} entities
	 */
	 public ArrayList<${curr.name}> getBy${relation.name?cap_first}(int ${relation.name?lower_case}_id){
		Cursor c = this.query(COLS, ${alias(relation.name)}+"=?", new String[]{${relation.name?lower_case}_id+""}, null, null, null);
		ArrayList<${curr.name}> result = this.cursorToItems(c);
		c.close();
		
		return result;
	 }
	 
			<#elseif (relation.relation.type=="ManyToMany")>	<#--
	public ArrayList<${curr.name}> getBy${relation.name?cap_first}(int ${relation.name?lower_case}_id){
		Cursor c = this.getCursor(${alias(relation.name)}+"=?", new String[]{${relation.name?lower_case}_id+""});
		ArrayList<${curr.name}> result = this.cursorToItems(c);
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
		ArrayList<${curr.name}> result = this.cursorToItems(c);
		c.close();
		
	<#if (relations??)>
		<#list curr.relations as relation>
			<#if (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter adapt${relation.relation.targetEntity} = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		adapt${relation.relation.targetEntity}.open(this.mDatabase);
		for (${curr.name} ${curr.name?lower_case} : result){
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
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${curr.name}SQLiteAdapterBase.itemToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
	<#list curr.ids as id>
		values.remove(${alias(id.name)});
	</#list>
	
		if (values.size()!=0){
			int newid = (int)this.insert(
					null, 
					values);
	
	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToMany")>
			
			${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.context);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()){
				${relation.name?uncap_first}Adapter.insert(newid, i.get${relation.relation.field_ref[0]?cap_first}());
			}
		<#elseif (relation.relation.type=="OneToMany")>
			${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()){
			<#if (relation.relation.mappedBy?? && !getMappedField(relation).internal)>
				${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
				${relation.name?uncap_first}Adapter.update(${relation.relation.targetEntity?lower_case});
			<#else>
				${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(${relation.relation.targetEntity?lower_case}, newid);
			</#if>
			}
		
		</#if>
	</#list>
		
			return newid;
		} else {
			return -1;
		}
	}
	
	/** Update a ${curr.name} entity into database 
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @return 
	 */
	public int update(${curr.name} item) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${curr.name}SQLiteAdapterBase.itemToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);	
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, </#if></#list>};
		
		return this.update(
				values, 
				whereClause, 
				whereArgs);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}

	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToOne" && relation.internal)>
			
	/** Update a ${curr.name} entity into database 
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @return 
	 */
	public int updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(${curr.name} item, int ${relation.relation.targetEntity?lower_case}_id) {
			<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");

		ContentValues values = ${curr.name}SQLiteAdapterBase.itemToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, ${relation.relation.targetEntity?lower_case}_id<#else>, 0</#if></#if></#list>);	
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, </#if></#list>};

		return this.update(
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
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${curr.name}SQLiteAdapterBase.itemToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, ${relation.relation.targetEntity?lower_case}_id<#else>, 0</#if></#if></#list>);
	<#list curr.ids as id>
		values.remove(${alias(id.name)});
	</#list>
		int newid = (int)this.insert(
			null, 
			values);
	
	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToMany")>
			
		${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.context);
		for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()){
			${relation.name?uncap_first}Adapter.insert(newid, i.get${relation.relation.field_ref[0]?cap_first}());
		}
		<#elseif (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()){
			<#if (relation.relation.mappedBy?? && !getMappedField(relation).internal)>
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
	public int remove(<#list curr.ids as id>${m.javaType(id.type)} ${id.name}<#if (id_has_next)>,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Delete DB(" + TABLE_NAME + ") id : "+ <#list curr.ids as id>${id.name}<#if (id_has_next)> + " id : " + </#if></#list>);
		
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if (id_has_next)>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if (id_has_next)>, </#if></#list>};
		
		return this.delete( 
				whereClause, 
				whereArgs);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}
	
	public int delete(${curr.name?cap_first} ${curr.name?uncap_first}){
		return this.delete(${curr.name?uncap_first}.getId());
	}
	
	// Internal Cursor
	protected Cursor getSingleCursor(<#list curr.ids as id>${m.javaType(id.type)} ${id.name}<#if id_has_next>,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Get entities id : " + <#list curr.ids as id>${id.name}<#if id_has_next> + " id : " + </#if></#list>);
		
		String whereClause = <#list curr.ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.query(COLS, whereClause, whereArgs, null, null, null);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}
	
</#if>
	
	public Cursor query(int id){
		<#if curr.ids?size==0>
			throw new UnsupportedOperationException("Method not implemented yet.");
		<#else>
		return this.query(
				COLS,
				COL_ID+" = ?",
				new String[]{String.valueOf(id)},
				null,
				null,
				null);
		</#if>
	}
	

	public int delete(int id){
		<#if curr.ids?size==0>
			throw new UnsupportedOperationException("Method not implemented yet.");
		<#else>
		return this.delete(
				COL_ID+" = ?",
				new String[]{String.valueOf(id)});
		</#if>
	}
	
<#if (curr.internal=="true")>
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
		if (${project_name?cap_first}Application.DEBUG)
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
		ArrayList<${curr.name}> result = this.cursorToItems(c);
		c.close();
		
		return result;
	}-->
	 
	public ArrayList<${curr.relations[0].relation.targetEntity}> getBy${curr.relations[1].relation.targetEntity}(int ${curr.relations[1].name}){
		String whereClause = ${alias(curr.relations[1].name)}+"=?";
		String whereArg = String.valueOf(${curr.relations[1].name});
		Cursor c = this.query(this.getCols(), whereClause, new String[]{whereArg}, null, null, null);
		${curr.relations[0].relation.targetEntity}SQLiteAdapter adapt = new ${curr.relations[0].relation.targetEntity}SQLiteAdapter(this.context);
		adapt.open(this.mDatabase);
		ArrayList<${curr.relations[0].relation.targetEntity}> ret = new ArrayList<${curr.relations[0].relation.targetEntity}>();

		while (c.moveToNext()){
			ret.add(adapt.getByID(c.getInt( c.getColumnIndexOrThrow(${alias(curr.relations[0].name)}) )));
		}
		return ret;
	}


	public ArrayList<${curr.relations[1].relation.targetEntity}> getBy${curr.relations[0].relation.targetEntity}(int ${curr.relations[0].name}){
		String whereClause = ${alias(curr.relations[0].name)}+"=?";
		String whereArg = String.valueOf(${curr.relations[0].name});
		Cursor c = this.query(this.getCols(), whereClause, new String[]{whereArg}, null, null, null);
		${curr.relations[1].relation.targetEntity}SQLiteAdapter adapt = new ${curr.relations[1].relation.targetEntity}SQLiteAdapter(this.context);
		adapt.open(this.mDatabase);
		ArrayList<${curr.relations[1].relation.targetEntity}> ret = new ArrayList<${curr.relations[1].relation.targetEntity}>();

		while (c.moveToNext()){
			ret.add(adapt.getByID(c.getInt( c.getColumnIndexOrThrow(${alias(curr.relations[1].name)}) )));
		}
		return ret;
	}
	
</#if>
}
