<#function getMappedField field>
	<#assign ref_entity = entities[field.relation.targetEntity] />
	<#list ref_entity.fields?values as ref_field>
		<#if ref_field.name == field.relation.mappedBy>
			<#return ref_field />
		</#if>
	</#list>
</#function>
<#assign curr = entities[current_entity] />
<#assign relation_array = [] />
<#assign hasRelations = false />
<#assign hasInternalFields = false />
<#list curr.relations as relation><#if (relation.internal?? && relation.internal==true)><#assign hasInternalFields = true /></#if></#list>
<#list curr.relations as relation>
	<#if (relation.relation.type == "OneToMany") >
		<#assign hasRelations = true />
		<#if (!relation_array?seq_contains(relation.relation.targetEntity))>
			<#assign relation_array = relation_array + [relation.relation.targetEntity] />
		</#if>
	<#elseif (relation.relation.type == "ManyToMany") >
		<#assign hasRelations = true />	
	</#if>
</#list>
package ${project_namespace}.provider.utils.base;

import ${project_namespace}.provider.utils.${curr.name}ProviderUtils;

import java.util.ArrayList;

<#if hasRelations>import android.content.ContentProviderOperation;
</#if>import android.database.Cursor;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;<#if hasRelations>
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;<#else>
import android.net.Uri;</#if>

import ${data_namespace}.${curr.name}SQLiteAdapter;
<#list relation_array as relation>import ${data_namespace}.${relation}SQLiteAdapter;</#list>
<#list curr.relations as relation>import ${data_namespace}.${relation.relation.targetEntity}SQLiteAdapter;
import ${project_namespace}.provider.${relation.relation.targetEntity}ProviderAdapter;
<#if relation.relation.type=="ManyToMany">import ${data_namespace}.${relation.relation.joinTable}SQLiteAdapter;
</#if></#list>
import ${entity_namespace}.${curr.name};
<#list curr.relations as relation><#if !relation.internal>import ${entity_namespace}.${relation.relation.targetEntity};
</#if></#list>
import ${project_namespace}.provider.${curr.name}ProviderAdapter;<#if hasRelations>
<#list relation_array as relation>import ${project_namespace}.provider.${relation}ProviderAdapter;</#list>
import ${project_namespace}.provider.${project_name?cap_first}Provider;</#if>
<#list curr.relations as relation><#if relation.relation.type=="ManyToMany">import ${project_namespace}.provider.${relation.relation.joinTable}ProviderAdapter;
</#if></#list>

<#list curr.relations as relation><#if !relation_array?seq_contains(relation.relation.targetEntity)>import ${project_namespace}.provider.utils.${relation.relation.targetEntity}ProviderUtils;
</#if></#list>

/**
 * ${curr.name?cap_first} Provider Utils Base.
 */
public class ${curr.name?cap_first}ProviderUtilsBase {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "${curr.name?cap_first}ProviderUtilBase";

	/** Base operations.*/

	<#if hasInternalFields>
	/**
	 * Insert into DB. 
	 * @param ctx Context
	 * @param item ${curr.name} to insert
	 * @return number of rows affected
	 */
	public static int insert(final Context ctx, final ${curr.name} item) {
		int result = -1;
		${curr.name?cap_first}SQLiteAdapter adapt = new ${curr.name?cap_first}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();

		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(${curr.name?cap_first}SQLiteAdapter.COL_ID);
		<#if (!hasRelations)> <#-- If there aren't any relations in the application -->
		Uri uri = prov.insert(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI, itemValues);		
		if (uri != null) {
			result = 0;
		}
		<#else>
		ArrayList<ContentProviderOperation> operations = 
				new ArrayList<ContentProviderOperation>();

		operations.add(ContentProviderOperation.newInsert(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI)
			    .withValues(itemValues)
			    .build());

		<#list curr.relations as relation>
			<#if (relation.relation.type == "OneToMany") >
		String ${relation.name}Selection = ${relation.relation.targetEntity?cap_first}SQLiteAdapter.COL_ID + " IN (";
		String[] ${relation.name}SelectionArgs = new String[item.get${relation.name?cap_first}().size()];
		for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
			${relation.name}SelectionArgs[i] = String.valueOf(item.get${relation.name?cap_first}().get(i).getId());
			${relation.name}Selection += "? ";
			if (i != item.get${relation.name?cap_first}().size()-1) {
				 ${relation.name}Selection += ", ";
			}
		}
		${relation.name}Selection += ")";

		operations.add(ContentProviderOperation.newUpdate(${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
				.withValueBackReference(${relation.relation.targetEntity}SQLiteAdapter.COL_${getMappedField(relation).name?upper_case}, 0)
				.withSelection(${relation.name}Selection, ${relation.name}SelectionArgs)
				.build());
			<#elseif (relation.relation.type == "ManyToMany") >
		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${relation.relation.targetEntity?upper_case}_ID, ${relation.relation.targetEntity?uncap_first}.getId());
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${curr.name?upper_case}_ID, item.getId());
			
			operations.add(ContentProviderOperation.newInsert(
				${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
				    .withValues(${relation.relation.targetEntity?uncap_first}Values)
				    .build());
			
		}
	
			</#if>
		</#list>

		try {
			prov.applyBatch(${project_name?cap_first}Provider.authority, operations);
			result = 0;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}
		</#if>

		return result;
	}

	</#if>
	/**
	 * Insert into DB. 
	 * @param ctx Context
	 * @param item ${curr.name} to insert
	 * @return number of rows affected
	 */
	public static int insert(final Context ctx, 
							 final ${curr.name?cap_first} item
<#list curr.relations as relation><#if (relation.internal?? && relation.internal==true)>, final int ${relation.name?uncap_first}Id</#if></#list>) {
		int result = -1;
		${curr.name?cap_first}SQLiteAdapter adapt = new ${curr.name?cap_first}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();

		ContentValues itemValues = adapt.itemToContentValues(item<#list curr.relations as relation><#if (relation.internal?? && relation.internal==true)>, ${relation.name?uncap_first}Id</#if></#list>);
		itemValues.remove(${curr.name?cap_first}SQLiteAdapter.COL_ID);
		<#if (!hasRelations)> <#-- If there aren't any relations in the application -->
		Uri uri = prov.insert(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI, itemValues);		
		if (uri != null) {
			result = 0;
		}
		<#else>
		ArrayList<ContentProviderOperation> operations = 
				new ArrayList<ContentProviderOperation>();

		operations.add(ContentProviderOperation.newInsert(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI)
			    .withValues(itemValues)
			    .build());

		<#list curr.relations as relation>
			<#if (relation.relation.type == "OneToMany") >
		String ${relation.name}Selection = ${relation.relation.targetEntity?cap_first}SQLiteAdapter.COL_ID + " IN (";
		String[] ${relation.name}SelectionArgs = new String[item.get${relation.name?cap_first}().size()];
		for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
			${relation.name}SelectionArgs[i] = String.valueOf(item.get${relation.name?cap_first}().get(i).getId());
			${relation.name}Selection += "? ";
			if (i != item.get${relation.name?cap_first}().size()-1) {
				 ${relation.name}Selection += ", ";
			}
		}
		${relation.name}Selection += ")";

		operations.add(ContentProviderOperation.newUpdate(${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
				.withValueBackReference(${relation.relation.targetEntity}SQLiteAdapter.COL_${getMappedField(relation).name?upper_case}, 0)
				.withSelection(${relation.name}Selection, ${relation.name}SelectionArgs)
				.build());
			<#elseif (relation.relation.type == "ManyToMany") >
		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${relation.relation.targetEntity?upper_case}_ID, ${relation.relation.targetEntity?uncap_first}.getId());
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${curr.name?upper_case}_ID, item.getId());
			
			operations.add(ContentProviderOperation.newInsert(
				${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
				    .withValues(${relation.relation.targetEntity?uncap_first}Values)
				    .build());
			
		}
	
			</#if>
		</#list>

		try {
			prov.applyBatch(${project_name?cap_first}Provider.authority, operations);
			result = 0;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}
		</#if>

		return result;
	}

	/**
	 * Delete from DB.
	 * @param ctx Context
	 * @param item ${curr.name?cap_first}
	 * @return number of row affected 
	 */
	public static int delete(final Context ctx, 
							 final ${curr.name?cap_first} item) {
		int result = -1;
		ContentResolver prov = ctx.getContentResolver();

		String selection = ${curr.name?cap_first}SQLiteAdapter.COL_ID + "= ?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = String.valueOf(item.getId());
		
		result = prov.delete(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
			selection,
			selectionArgs);

		return result;
	}

	/**
	 * Query the DB.
	 * @param ctx Context
	 * @param id The ID
	 * @return ${curr.name?cap_first}
	 */
	public static ${curr.name?cap_first} query(final Context ctx, final int id) {
		${curr.name?cap_first} result = null;
		${curr.name?cap_first}SQLiteAdapter adapt = new ${curr.name?cap_first}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();

		String selection = ${curr.name?cap_first}SQLiteAdapter.COL_ID + "= ?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = String.valueOf(id);
		
		Cursor cursor = prov.query(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI,
			${curr.name?cap_first}SQLiteAdapter.COLS,
			selection,
			selectionArgs,
			null);
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = adapt.cursorToItem(cursor);
			cursor.close();

		<#list curr.relations as relation>
			<#--<#if (!relation.internal)>
			// Query ${relation.name} relation
				<#if (relation.relation.type=="OneToMany")>
			String ${relation.name}Selection = ${relation.relation.targetEntity}SQLiteAdapter.COL_${getMappedField(relation).name?upper_case} + " = ?";
			String[] ${relation.name}SelectionArgs = new String[1];
			${relation.name}SelectionArgs[0] = String.valueOf(id);
			Cursor ${relation.name}Cursor = 
					prov.query(${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI, 
							${relation.relation.targetEntity}SQLiteAdapter.COLS,
							${relation.name}Selection, 
							${relation.name}SelectionArgs, 
							null);
			
			${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapt = new ${relation.relation.targetEntity}SQLiteAdapter(ctx);
			result.set${relation.name?cap_first}(${relation.name}Adapt.cursorToItems(${relation.name}Cursor));
			${relation.name}Cursor.close();
				<#elseif (relation.relation.type=="ManyToMany")>
			String ${relation.name}Selection = ${relation.relation.joinTable?cap_first}SQLiteAdapter.COL_${curr.name?upper_case}_ID + " = ?";
			String[] ${relation.name}SelectionArgs = new String[1];
			${relation.name}SelectionArgs[0] = String.valueOf(id);
			Cursor ${relation.name}Cursor = 
					prov.query(${relation.relation.joinTable?cap_first}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI, 
							${relation.relation.joinTable}SQLiteAdapter.COLS,
							${relation.name}Selection, 
							${relation.name}SelectionArgs, 
							null);

			ArrayList<${relation.relation.targetEntity?cap_first}> ${relation.name}Array = new ArrayList<${relation.relation.targetEntity?cap_first}>();
			if (${relation.name}Cursor.getCount() > 0) {	
				int ${relation.name}IdColumnIndex = ${relation.name}Cursor.getColumnIndex(${relation.relation.joinTable?cap_first}SQLiteAdapter.COL_${relation.relation.targetEntity?upper_case}_ID);
				while (${relation.name}Cursor.moveToNext()) {
					int ${relation.name}Id = ${relation.name}Cursor.getInt(${relation.name}IdColumnIndex);
					${relation.name}Array.add(${relation.relation.targetEntity?cap_first}ProviderUtils.query(ctx, ${relation.name}Id));
				}
			}
			result.set${relation.name?cap_first}(${relation.name}Array);
				<#else>
			if (result.get${relation.name?cap_first}() != null) {
				result.set${relation.name?cap_first}(
					${relation.relation.targetEntity?cap_first}ProviderUtils.query(ctx,
							result.get${relation.name?cap_first}().getId()));
			}
				</#if>

			</#if>-->
			<#if (!relation.internal)>
				result.set${relation.name?cap_first}(
					${curr.name}ProviderUtils.getAssociate${relation.name?cap_first}(ctx,
							result));
			</#if>
		</#list>
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @param ctx Context
	 * @return ArrayList<${curr.name}>
	 */
	public static ArrayList<${curr.name}> queryAll(final Context ctx) {
		ArrayList<${curr.name}> result = new ArrayList<${curr.name}>();
		${curr.name}SQLiteAdapter adapt = new ${curr.name}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();
		
		Cursor cursor = prov.query(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				${curr.name}SQLiteAdapter.COLS,
				null, 
				null, 
				null);
		
		result = adapt.cursorToItems(cursor);
		
		cursor.close();

		return result;
	}

	<#if hasInternalFields>
	/**
	 * Updates the DB.
	 * @param ctx Context
	 * @param item ${curr.name}
	 * @return number of rows updated
	 */
	public static int update(final Context ctx, final ${curr.name} item) {
		int result = -1;
		${curr.name}SQLiteAdapter adapt = new ${curr.name}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();		
		ContentValues itemValues = adapt.itemToContentValues(item);
		
		String selection = ${curr.name}SQLiteAdapter.COL_ID + "= ?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = String.valueOf(item.getId());
		
		result = prov.update(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				itemValues, 
				selection,
				selectionArgs);

		<#list curr.relations as relation>
			<#if (relation.relation.type == "ManyToMany") >
		prov.delete(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI,
				${relation.relation.joinTable}SQLiteAdapter.COL_${curr.name?upper_case}_ID + "= ?", 
				new String[]{String.valueOf(item.getId())});
		
		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${relation.relation.targetEntity?upper_case}_ID,
					${relation.relation.targetEntity?uncap_first}.getId());
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${curr.name?upper_case}_ID,
					item.getId());
			
			prov.insert(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI,
					${relation.relation.targetEntity?uncap_first}Values);
		}
			</#if>
		</#list>

		return result;
	}

	</#if>
	/**
	 * Updates the DB.
	 * @param ctx Context
	 * @param item ${curr.name}
	 * @return number of rows updated
	 */
	public static int update(final Context ctx, final ${curr.name} item
<#list curr.relations as relation><#if (relation.internal?? && relation.internal==true)>, final int ${relation.name?uncap_first}Id</#if></#list>) {
		int result = -1;
		${curr.name}SQLiteAdapter adapt = new ${curr.name}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();		
		ContentValues itemValues = adapt.itemToContentValues(item<#list curr.relations as relation><#if (relation.internal?? && relation.internal==true)>, ${relation.name?uncap_first}Id</#if></#list>);
		
		String selection = ${curr.name}SQLiteAdapter.COL_ID + "= ?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = String.valueOf(item.getId());
		
		result = prov.update(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				itemValues, 
				selection,
				selectionArgs);

		<#list curr.relations as relation>
			<#if (relation.relation.type == "ManyToMany") >
		prov.delete(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI,
				${relation.relation.joinTable}SQLiteAdapter.COL_${curr.name?upper_case}_ID + "= ?", 
				new String[]{String.valueOf(item.getId())});
		
		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${relation.relation.targetEntity?upper_case}_ID,
					${relation.relation.targetEntity?uncap_first}.getId());
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}SQLiteAdapter.COL_${curr.name?upper_case}_ID,
					item.getId());
			
			prov.insert(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI,
					${relation.relation.targetEntity?uncap_first}Values);
		}
			</#if>
		</#list>

		return result;
	}

	<#if (curr.relations?size>0)>/** Relations operations. */</#if>
	<#list curr.relations as relation>
		<#if (!relation.internal)>
			<#if (relation.relation.type == "ManyToOne" || relation.relation.type == "OneToOne")>
	/**
	 * Get associate ${relation.name?cap_first}.
	 * @param ctx Context
	 * @param item ${curr.name}
	 * @return ${relation.relation.targetEntity?cap_first}
	 */
	public static ${relation.relation.targetEntity?cap_first} getAssociate${relation.name?cap_first}(final Context ctx, final ${curr.name} item) {		
		${relation.relation.targetEntity?cap_first} result;
		ContentResolver prov = ctx.getContentResolver();
		Cursor ${relation.relation.targetEntity?uncap_first}Cursor = prov.query(
				${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI, 
				${relation.relation.targetEntity?cap_first}SQLiteAdapter.COLS,
				${relation.relation.targetEntity?cap_first}SQLiteAdapter.COL_ID + "= ?", 
				new String[]{String.valueOf(item.get${relation.name?cap_first}().getId())}, 
				null);

		if (${relation.relation.targetEntity?uncap_first}Cursor.getCount() > 0) {
			${relation.relation.targetEntity?uncap_first}Cursor.moveToFirst();
			${relation.relation.targetEntity?cap_first}SQLiteAdapter ${relation.relation.targetEntity?uncap_first}Adapt = new ${relation.relation.targetEntity?cap_first}SQLiteAdapter(ctx);
			result = ${relation.relation.targetEntity?uncap_first}Adapt.cursorToItem(${relation.relation.targetEntity?uncap_first}Cursor);
		} else {
			result = null;
		}
		${relation.relation.targetEntity?uncap_first}Cursor.close();

		return result;
	}
			<#elseif (relation.relation.type == "OneToMany")>
	/**
	 * Get associate ${relation.name?cap_first}.
	 * @param ctx Context
	 * @param item ${curr.name}
	 * @return ${relation.relation.targetEntity?cap_first}
	 */
	public static ArrayList<${relation.relation.targetEntity?cap_first}> getAssociate${relation.name?cap_first}(final Context ctx, final ${curr.name} item) {	
		ArrayList<${relation.relation.targetEntity?cap_first}> result;	
		ContentResolver prov = ctx.getContentResolver();
		Cursor ${relation.relation.targetEntity?uncap_first}Cursor = prov.query(
				${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI, 
				${relation.relation.targetEntity?cap_first}SQLiteAdapter.COLS,
				${relation.relation.targetEntity?cap_first}SQLiteAdapter.COL_${relation.relation.mappedBy?upper_case} + "= ?", 
				new String[]{String.valueOf(item.getId())}, 
				null);

		${relation.relation.targetEntity?cap_first}SQLiteAdapter ${relation.relation.targetEntity?uncap_first}Adapt = new ${relation.relation.targetEntity?cap_first}SQLiteAdapter(ctx);
		result = ${relation.relation.targetEntity?uncap_first}Adapt.cursorToItems(${relation.relation.targetEntity?uncap_first}Cursor);
		${relation.relation.targetEntity?uncap_first}Cursor.close();

		return result;
	}
			<#elseif (relation.relation.type == "ManyToMany")>
	/**
	 * Get associate ${relation.name?cap_first}.
	 * @param ctx Context
	 * @param item ${curr.name}
	 * @return ${relation.relation.targetEntity?cap_first}
	 */
	public static ArrayList<${relation.relation.targetEntity?cap_first}> getAssociate${relation.name?cap_first}(final Context ctx, final ${curr.name} item) {		
		ArrayList<${relation.relation.targetEntity?cap_first}> result;	
		ContentResolver prov = ctx.getContentResolver();
		Cursor ${relation.relation.joinTable?uncap_first}Cursor = prov.query(
				${relation.relation.joinTable?cap_first}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI, 
				${relation.relation.joinTable?cap_first}SQLiteAdapter.COLS,
				${relation.relation.joinTable?cap_first}SQLiteAdapter.COL_${curr.name?upper_case}_ID + "= ?", 
				new String[]{String.valueOf(item.getId())}, 
				null);

		if (${relation.relation.joinTable?uncap_first}Cursor.getCount() > 0) {
			String selection = ${relation.relation.targetEntity?cap_first}SQLiteAdapter.COL_ID + " IN (";
			String[] selectionArgs = new String[${relation.relation.joinTable?uncap_first}Cursor.getCount()];
			
			
			while (${relation.relation.joinTable?uncap_first}Cursor.moveToNext()) {
				int index = ${relation.relation.joinTable?uncap_first}Cursor.getColumnIndex(${relation.relation.joinTable?cap_first}SQLiteAdapter.COL_${relation.relation.targetEntity?upper_case}_ID);
				int ${relation.relation.targetEntity?uncap_first}Id = ${relation.relation.joinTable?uncap_first}Cursor.getInt(index);
				
				selection += "?";
				if (!${relation.relation.joinTable?uncap_first}Cursor.isLast()) {
					selection += ", ";
				} else {
					selection += ")";
				}
				selectionArgs[${relation.relation.joinTable?uncap_first}Cursor.getPosition()] = 
						String.valueOf(${relation.relation.targetEntity?uncap_first}Id);
			}
			${relation.relation.joinTable?uncap_first}Cursor.close();
			Cursor ${relation.relation.targetEntity?uncap_first}Cursor = prov.query(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI,
					${relation.relation.targetEntity?cap_first}SQLiteAdapter.COLS,
					selection,
					selectionArgs,
					null);
			

			${relation.relation.targetEntity?cap_first}SQLiteAdapter ${relation.relation.targetEntity?uncap_first}Adapt = new ${relation.relation.targetEntity?cap_first}SQLiteAdapter(ctx);
			result = ${relation.relation.targetEntity?uncap_first}Adapt.cursorToItems(${relation.relation.targetEntity?uncap_first}Cursor);
			${relation.relation.targetEntity?uncap_first}Cursor.close();
		} else {
			result = new ArrayList<${relation.relation.targetEntity?cap_first}>();
		}

		return result;
	}
			</#if>

		</#if>
	</#list>
}
