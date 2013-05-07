<#function getMappedField field>
	<#assign ref_entity = entities[field.relation.targetEntity] />
	<#list ref_entity.fields as ref_field>
		<#if ref_field.name == field.relation.mappedBy>
			<#return ref_field />
		</#if>
	</#list>
</#function>
<#assign curr = entities[current_entity] />
<#assign relation_array = [] />
<#list curr.relations as relation>
	<#if (relation.relation.type == "OneToMany" || relation.relation.type == "ManyToMany") >
		<#if (!relation_array?seq_contains(relation.relation.targetEntity))>
			<#assign relation_array = relation_array + [relation.relation.targetEntity] />
		</#if>
	</#if>
</#list>
<#assign hasRelations = (relation_array?size > 0) />
package ${project_namespace}.provider.utils.base;

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
<#list relation_array as relation>
import ${data_namespace}.${relation}SQLiteAdapter;
</#list>
import ${entity_namespace}.${curr.name};
import ${project_namespace}.provider.${curr.name}ProviderAdapter;<#if hasRelations>
<#list relation_array as relation>import ${project_namespace}.provider.${relation}ProviderAdapter;</#list>
import ${project_namespace}.provider.${project_name?cap_first}Provider;</#if>

<#list curr.relations as relation><#if !relation_array?seq_contains(relation.relation.targetEntity)>import ${project_namespace}.provider.utils.${relation.relation.targetEntity}ProviderUtils;</#if>
</#list>

public class ${curr.name?cap_first}ProviderUtilsBase {
	public static final String TAG = "${curr.name?cap_first}ProviderUtilBase";

	public static int insert(Context ctx, ${curr.name?cap_first} item) {
		int result = -1;
		${curr.name?cap_first}SQLiteAdapter adapt = new ${curr.name?cap_first}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();

		ContentValues itemValues = adapt.itemToContentValues(item);
		itemValues.remove(${curr.name?cap_first}SQLiteAdapter.COL_ID);
		<#if (relation_array?size == 0)> <#-- If there aren't any relations in the application -->
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
			${relation.name}SelectionArgs[i] = String.valueOf(item.getComments().get(i).getId());
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

	public static int delete(Context ctx, ${curr.name?cap_first} item) {
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

	
	public static ${curr.name?cap_first} query(Context ctx, int id) {
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
			<#if (!relation.internal)>
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
			result.setComments(${relation.name}Adapt.cursorToItems(${relation.name}Cursor));
			${relation.name}Cursor.close();
				<#elseif (relation.relation.type=="ManyToMany")>
			${relation.relation.joinTable}SQLiteAdapter ${relation.relation.joinTable?lower_case}Adapter = 
					new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
			${relation.relation.joinTable?lower_case}Adapter.open(this.mDatabase);
			result.set${relation.name?cap_first}(
					${relation.relation.joinTable?lower_case}Adapter.getBy${curr.name}(
							result.getId())); // relation.relation.inversedBy?cap_first
				<#else>
			if (result.get${relation.name?cap_first}() != null) {
				result.set${relation.name?cap_first}(
					${relation.relation.targetEntity?cap_first}ProviderUtils.query(ctx,
							result.get${relation.name?cap_first}().getId()));
			}
				</#if>

			</#if>
		</#list>
		}

		return result;
	}

	
	public static ArrayList<${curr.name}> queryAll(Context ctx) {
		ArrayList<${curr.name}> result = new ArrayList<${curr.name}>();
		${curr.name}SQLiteAdapter adapt = new ${curr.name}SQLiteAdapter(ctx);
		ContentResolver prov = ctx.getContentResolver();
		
		Cursor cursor = prov.query(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				${curr.name}SQLiteAdapter.COLS,
				null, 
				null, 
				null);
		
		result = adapt.cursorToItems(cursor);
		
		return result;
	}

	
	public static int update(Context ctx, ${curr.name} item) {
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
		
		return result;
	}
}
