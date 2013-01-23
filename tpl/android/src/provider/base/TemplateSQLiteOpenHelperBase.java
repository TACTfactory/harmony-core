<#function callLoader entity>
	<#assign ret="//Load "+entity.name+" fixtures\r\t\t" /> 
	<#assign ret=ret+entity.name?cap_first+"DataLoader "+entity.name?uncap_first+"Loader = new "+entity.name?cap_first+"DataLoader(this.context);\r\t\t" />
	<#assign ret=ret+entity.name?uncap_first+"Loader.getModelFixtures();\r\t\t" />
	<#assign ret=ret+entity.name?uncap_first+"Loader.load(manager);\r" />
	<#return ret />
</#function>


<#function getZeroRelationsEntities>
	<#assign ret = [] />
	<#list entities?values as entity>
		<#if entity.relations?size==0>
			<#assign ret = ret + [entity.name]>
		</#if>
	</#list>
	<#return ret />
</#function>
<#function isInArray array val>
	<#list array as val_ref>
		<#if val_ref==val>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function isOnlyDependantOf entity entity_list>
	<#list entity.relations as rel>
		<#if rel.relation.type=="ManyToOne">
			<#if !isInArray(entity_list, rel.relation.targetEntity)>
				<#return false />
			</#if>
		</#if>	
	</#list>
	<#return true />
</#function>
<#function orderEntitiesByRelation>
	<#assign ret = getZeroRelationsEntities() />
	<#assign maxLoop = entities?size />
	<#list 1..maxLoop as i>
		<#list entities?values as entity>
			<#if !isInArray(ret, entity.name)>
				<#if isOnlyDependantOf(entity, ret)>
					<#assign ret = ret + [entity.name] />
				</#if>
			</#if>
		</#list>
	</#list>
	<#return ret>
</#function>
package ${data_namespace}.base;

import ${data_namespace}.*;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

<#if options.fixture?? && options.fixture.enabled>
import ${fixture_namespace}.*;
</#if>


/**
 * This class makes it easy for ContentProvider implementations to defer opening and upgrading the database until first use, to avoid blocking application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class ${project_name?cap_first}SQLiteOpenHelperBase extends SQLiteOpenHelper {
	protected String TAG = "DatabaseHelper";
	protected Context context;
	
	public ${project_name?cap_first}SQLiteOpenHelperBase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Create database..");
		
		/// Create Schema
	<#list entities?values as entity>
		<#if (entity.fields?? && (entity.fields?size>0))>
		db.execSQL( ${entity.name}SQLiteAdapter.getSchema() );
			<#list entity["relations"] as relation>
				<#if (relation.type=="ManyToMany")>
		db.execSQL( ${entity.name}SQLiteAdapter.get${relation.name?cap_first}RelationSchema() );
				</#if>
			</#list>
		</#if>
	</#list>
	
	<#if options.fixture?? && options.fixture.enabled>
		this.loadData(db);
	</#if>
		
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Update database..");
		
		//if (SqliteAdapter.BASE_VERSION < 0) {
			Log.i(TAG, "Upgrading database from version " + oldVersion + 
					   " to " + newVersion + ", which will destroy all old data");
		
		<#list entities?values as entity>
			<#if (entity.fields?? && (entity.fields?size>0))>
			db.execSQL("DROP TABLE IF EXISTS "+ ${entity.name}SQLiteAdapter.TABLE_NAME);
				<#list entity['relations'] as relation>
					<#if (relation.type=="ManyToMany")>
			db.execSQL("DROP TABLE IF EXISTS "+${entity.name}SQLiteAdapter.RELATION_${relation.name?upper_case}_TABLE_NAME );
					</#if>
				</#list>
			</#if>
	    </#list>
		//}
		    
		this.onCreate(db);
	}
	
	<#if options.fixture?? && options.fixture.enabled>
	//@SuppressWarnings("rawtypes")
	private void loadData(SQLiteDatabase db){
		// Sample of data
		DataManager manager = new DataManager(this.context, db);
		
		<#assign entitiesOrder = orderEntitiesByRelation() />
		<#list entitiesOrder as entityName>
			<#assign entity = entities[entityName]>
			<#if (entity.fields?size>0)>
		${callLoader(entity)}
			</#if>
		</#list>
	}
	</#if>
}
