<#include utilityPath + "all_imports.ftl" />
<#assign fields = ViewUtils.getAllFields(curr)?values + curr_fields />
<#assign relations = ViewUtils.getAllRelations(curr) />
<#assign relation_array = [] />
<#assign hasRelations = false />
<#assign hasInternalFields = false />
<#assign inherited = false />
<#list relations as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<@header?interpret />
package ${project_namespace}.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

${ImportUtils.importRelatedCriterias(curr)}
${ImportUtils.importManyToManyTargetSQLiteAdapters(curr)}
${ImportUtils.importRelatedEntities(curr, true)}
${ImportUtils.importRelatedEnums(curr, false)}
${ImportUtils.importRelatedProviderAdapters(curr, false)}
import ${project_namespace}.provider.${project_name?cap_first}Provider;
import ${project_namespace}.provider.${project_name?cap_first}Contract;

/**
 * ${curr.name?cap_first} Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ${curr.name?cap_first}ProviderUtilsBase
			extends ProviderUtilsBase<${curr.name?cap_first}> {
	/**
	 * Tag for debug messages.
	 */
	public static final String TAG = "${curr.name?cap_first}ProviderUtilBase";

	/**
	 * Constructor.
	 * @param context Context
	 */
	public ${curr.name?cap_first}ProviderUtilsBase(Context context) {
		super(context);
	}

	@Override
	public Uri insert(final ${curr.name} item) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();


		ContentValues itemValues = ${project_name?cap_first}Contract.${curr.name}.itemToContentValues(item);
		itemValues.remove(${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)});

		operations.add(ContentProviderOperation.newInsert(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI)
						.withValues(itemValues)
						.build());

		<#list relations as relation>
			<#if (relation.relation.type == "OneToMany") >
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			String ${relation.name}Selection = ${project_name?cap_first}Contract.${entities[relation.relation.targetEntity].ids[0].owner}.${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)} + " IN (";
			String[] ${relation.name}SelectionArgs = new String[item.get${relation.name?cap_first}().size()];
			for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
				${relation.name}SelectionArgs[i] = String.valueOf(item.get${relation.name?cap_first}().get(i).get${entities[relation.relation.targetEntity].ids[0].name?cap_first}());
				${relation.name}Selection += "? ";
				if (i != item.get${relation.name?cap_first}().size() - 1) {
					 ${relation.name}Selection += ", ";
				}
			}
			${relation.name}Selection += ")";

			operations.add(ContentProviderOperation.newUpdate(${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
					.withValueBackReference(
							${project_name?cap_first}Contract.${relation.relation.targetEntity}
									.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
							0)
					.withSelection(${relation.name}Selection, ${relation.name}SelectionArgs)
					.build());
		}
			<#elseif (relation.relation.type == "ManyToMany") >
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
				ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
				${relation.relation.targetEntity?uncap_first}Values.put(
						${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.targetEntity)}ID,
						${relation.relation.targetEntity?uncap_first}.get${entities[relation.relation.targetEntity].ids[0].name?cap_first}());

				operations.add(ContentProviderOperation.newInsert(
					${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
					    .withValues(${relation.relation.targetEntity?uncap_first}Values)
					    .withValueBackReference(
								${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)},
					    		0)
					    .build());

			}
		}
			</#if>
		</#list>

		try {
			ContentProviderResult[] results = 
					prov.applyBatch(${project_name?cap_first}Provider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	<#if hasInternalFields>
	/**
	 * Insert into DB.
	 * @param item ${curr.name} to insert
	 <#list relations as relation><#if (relation.internal)>* @param ${relation.name?uncap_first}Id ${relation.name?uncap_first} Id</#if></#list>
	 * @return number of rows affected
	 */
	public Uri insert(final ${curr.name?cap_first} item<#list relations as relation><#if (relation.internal)>,
							 final int ${relation.name?uncap_first}Id</#if></#list>) {
		Uri result = null;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();

		ContentValues itemValues = ${project_name?cap_first}Contract.${curr.name}.itemToContentValues(item<#list relations as relation><#if (relation.internal)>,
					${relation.name?uncap_first}Id</#if></#list>);
		itemValues.remove(${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)});

		operations.add(ContentProviderOperation.newInsert(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI)
			    	.withValues(itemValues)
			    	.build());


		<#list relations as relation>
			<#if (relation.relation.type == "OneToMany") >
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			${curr.name}Criterias ${curr.name?uncap_first}Crit =
						new ${curr.name}Criterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(${project_name?cap_first}Contract.${relation.relation.targetEntity}.${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)});
			crit.addValue(values);
			${curr.name?uncap_first}Crit.add(crit);


			for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
				values.addValue(String.valueOf(
						item.get${relation.name?cap_first}().get(i).get${entities[relation.relation.targetEntity].ids[0].name?cap_first}()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
						.withValueBackReference(
								${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}
										.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
								0)
					.withSelection(
							${curr.name?uncap_first}Crit.toSQLiteSelection(),
							${curr.name?uncap_first}Crit.toSQLiteSelectionArgs())
					.build());
		}
			<#elseif (relation.relation.type == "ManyToMany") >
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
				ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
				${relation.relation.targetEntity?uncap_first}Values.put(
						${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.targetEntity)}ID,
						${relation.relation.targetEntity?uncap_first}.get${entities[relation.relation.targetEntity].ids[0].name?cap_first}());

				operations.add(ContentProviderOperation.newInsert(
					${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
					    .withValues(${relation.relation.targetEntity?uncap_first}Values)
					    .withValueBackReference(
								${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)},
					    		0)
					    .build());

			}
		}
			</#if>
		</#list>

		try {
			ContentProviderResult[] results =
				prov.applyBatch(${project_name?cap_first}Provider.authority, operations);
			if (results[0] != null) {
				result = results[0].uri;
			}
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}
	</#if>

	/**
	 * Delete from DB.
	 * @param item ${curr.name?cap_first}
	 * @return number of row affected
	 */
	public int delete(final ${curr.name?cap_first} item) {
		int result = -1;
		ContentResolver prov = this.getContext().getContentResolver();

		Uri uri = Uri.withAppendedPath(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				String.valueOf(item.get${curr_ids[0].name?cap_first}()));
		result = prov.delete(uri,
			null,
			null);


		return result;
	}

	/**
	 * Query the DB.
	 * @param id The ID
	 * @return ${curr.name?cap_first}
	 */
	public ${curr.name?cap_first} query(final int id) {
		${curr.name?cap_first} result = null;
		ContentResolver prov = this.getContext().getContentResolver();

		${curr.name}Criterias crits =
				new ${curr.name}Criterias(GroupType.AND);
		crits.add(${project_name?cap_first}Contract.${curr_ids[0].owner}.ALIASED_${NamingUtils.alias(curr_ids[0].name)},
					String.valueOf(id));

		Cursor cursor = prov.query(
			${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI,
			${project_name?cap_first}Contract.${curr.name?cap_first}.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = ${project_name?cap_first}Contract.${curr.name}.cursorToItem(cursor);
			cursor.close();

		<#list relations as relation>
			<#if (!relation.internal)>
				<#if (relation.relation.type == "ManyToOne" || relation.relation.type == "OneToOne")>
			if (result.get${relation.name?cap_first}() != null) {
				result.set${relation.name?cap_first}(
					this.getAssociate${relation.name?cap_first}(result));
			}
				<#else>
			result.set${relation.name?cap_first}(
				this.getAssociate${relation.name?cap_first}(result));
				</#if>
			</#if>
		</#list>
		}

		return result;
	}

	/**
	 * Query the DB to get all entities.
	 * @return ArrayList<${curr.name}>
	 */
	public ArrayList<${curr.name}> queryAll() {
		ArrayList<${curr.name}> result =
					new ArrayList<${curr.name}>();
		ContentResolver prov =
					this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				${project_name?cap_first}Contract.${curr.name?cap_first}.ALIASED_COLS,
				null,
				null,
				null);

		result = ${project_name?cap_first}Contract.${curr.name}.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<${curr.name}>
	 */
	public ArrayList<${curr.name}> query(
				CriteriasBase<${curr.name}> criteria) {
		ArrayList<${curr.name}> result =
					new ArrayList<${curr.name}>();
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				${project_name?cap_first}Contract.${curr.name?cap_first}.ALIASED_COLS,
				criteria.toSQLiteSelection(),
				criteria.toSQLiteSelectionArgs(),
				null);

		result = ${project_name?cap_first}Contract.${curr.name}.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	<#if hasInternalFields>
	/**
	 * Updates the DB.
	 * @param item ${curr.name}
	 * @return number of rows updated
	 */
	public int update(final ${curr.name} item) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = ${project_name?cap_first}Contract.${curr.name}.itemToContentValues(item);

		Uri uri = Uri.withAppendedPath(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				String.valueOf(item.get${curr_ids[0].name?cap_first}()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());

		<#list relations as relation>
			<#if (relation.relation.type == "OneToMany")>
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			// Set new ${relation.name} for ${curr.name}
			${relation.relation.targetEntity}Criterias ${relation.name}Crit =
						new ${relation.relation.targetEntity}Criterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(${project_name?cap_first}Contract.${entities[relation.relation.targetEntity].ids[0].owner}.${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)});
			crit.addValue(values);
			${relation.name}Crit.add(crit);


			for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
				values.addValue(String.valueOf(
						item.get${relation.name?cap_first}().get(i).get${entities[relation.relation.targetEntity].ids[0].name?cap_first}()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
						.withValue(
								${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}
										.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
								item.get${curr_ids[0].name?cap_first}())
					.withSelection(
							${relation.name}Crit.toSQLiteSelection(),
							${relation.name}Crit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated ${relation.name}
			crit.setType(Type.NOT_IN);
			${relation.name}Crit.add(${project_name?cap_first}Contract.${relation.relation.targetEntity}.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
					String.valueOf(item.get${curr_ids[0].name?cap_first}()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
						.withValue(
								${project_name?cap_first}Contract.${relation.relation.targetEntity}
										.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
								null)
					.withSelection(
							${relation.name}Crit.toSQLiteSelection(),
							${relation.name}Crit.toSQLiteSelectionArgs())
					.build());
		}

			<#elseif (relation.relation.type == "ManyToMany") >
		operations.add(ContentProviderOperation.newDelete(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
				.withSelection(${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)} + "= ?",
								new String[]{String.valueOf(item.get${curr_ids[0].name?cap_first}())})
				.build());

		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			${relation.relation.targetEntity?uncap_first}Values.put(${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.targetEntity)}ID,
					${relation.relation.targetEntity?uncap_first}.get${entities[relation.relation.targetEntity].ids[0].name?cap_first}());
			${relation.relation.targetEntity?uncap_first}Values.put(${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)},
					item.get${curr_ids[0].name?cap_first}());

			operations.add(ContentProviderOperation.newInsert(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
					.withValues(${relation.relation.targetEntity?uncap_first}Values)
					.build());
		}
			</#if>
		</#list>

		try {
			ContentProviderResult[] results = prov.applyBatch(${project_name?cap_first}Provider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	</#if>
	/**
	 * Updates the DB.
	 * @param item ${curr.name}
	 <#list relations as relation><#if (relation.internal)>* @param ${relation.name?uncap_first}Id ${relation.name?uncap_first} Id</#if></#list>
	 * @return number of rows updated
	 */
	public int update(final ${curr.name} item<#list relations as relation><#if (relation.internal)>,
							 final int ${relation.name?uncap_first}Id</#if></#list>) {
		int result = -1;
		ArrayList<ContentProviderOperation> operations =
				new ArrayList<ContentProviderOperation>();
		ContentResolver prov = this.getContext().getContentResolver();
		ContentValues itemValues = ${project_name?cap_first}Contract.${curr.name}.itemToContentValues(
				item<#list relations as relation><#if (relation.internal)>,
				${relation.name?uncap_first}Id</#if></#list>);

		Uri uri = Uri.withAppendedPath(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				String.valueOf(item.get${curr_ids[0].name?cap_first}()));


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		<#list relations as relation>
			<#if (relation.relation.type == "OneToMany")>
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			// Set new ${relation.name} for ${curr.name}
			${relation.relation.targetEntity}Criterias ${relation.name}Crit =
						new ${relation.relation.targetEntity}Criterias(GroupType.AND);
			Criteria crit = new Criteria();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(${project_name?cap_first}Contract.${entities[relation.relation.targetEntity].ids[0].owner}.${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)});
			crit.addValue(values);
			${relation.name}Crit.add(crit);


			for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
				values.addValue(String.valueOf(
						item.get${relation.name?cap_first}().get(i).get${entities[relation.relation.targetEntity].ids[0].name?cap_first}()));
			}

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
						.withValue(
								${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}
										.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
								item.get${curr_ids[0].name?cap_first}())
					.withSelection(
							${relation.name}Crit.toSQLiteSelection(),
							${relation.name}Crit.toSQLiteSelectionArgs())
					.build());

			// Remove old associated ${relation.name}
			crit.setType(Type.NOT_IN);
			${relation.name}Crit.add(${project_name?cap_first}Contract.${relation.relation.targetEntity}.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
					String.valueOf(item.get${curr_ids[0].name?cap_first}()),
					Type.EQUALS);
			

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
						.withValue(
								${project_name?cap_first}Contract.${relation.relation.targetEntity}
										.COL_${MetadataUtils.getMappedField(relation).name?upper_case},
								null)
					.withSelection(
							${relation.name}Crit.toSQLiteSelection(),
							${relation.name}Crit.toSQLiteSelectionArgs())
					.build());
		}

			<#elseif (relation.relation.type == "ManyToMany") >
		operations.add(ContentProviderOperation.newDelete(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
				.withSelection(${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)} + "= ?",
								new String[]{String.valueOf(item.get${curr_ids[0].name?cap_first}())})
				.build());

		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			${relation.relation.targetEntity?uncap_first}Values.put(${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.targetEntity)}ID,
					${relation.relation.targetEntity?uncap_first}.get${entities[relation.relation.targetEntity].ids[0].name?cap_first}());
			${relation.relation.targetEntity?uncap_first}Values.put(${project_name?cap_first}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)},
					item.get${curr_ids[0].name?cap_first}());

			operations.add(ContentProviderOperation.newInsert(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
					.withValues(${relation.relation.targetEntity?uncap_first}Values)
					.build());
		}
			</#if>
		</#list>

		try {
			ContentProviderResult[] results = prov.applyBatch(${project_name?cap_first}Provider.authority, operations);
			result = results[0].count;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	<#if (relations?size>0)>/** Relations operations. */</#if>
	<#list relations as relation>
		<#if (!relation.internal)>
			<#if (relation.relation.type == "ManyToOne" || relation.relation.type == "OneToOne")>
	/**
	 * Get associate ${relation.name?cap_first}.
	 * @param item ${curr.name}
	 * @return ${relation.relation.targetEntity?cap_first}
	 */
	public ${relation.relation.targetEntity?cap_first} getAssociate${relation.name?cap_first}(
			final ${curr.name} item) {
		${relation.relation.targetEntity?cap_first} result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor ${relation.relation.targetEntity?uncap_first}Cursor = prov.query(
				${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI,
				${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_COLS,
				${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}.${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)} + "= ?",
				new String[]{String.valueOf(item.get${relation.name?cap_first}().get${entities[relation.relation.targetEntity].ids[0].name?cap_first}())},
				null);

		if (${relation.relation.targetEntity?uncap_first}Cursor.getCount() > 0) {
			${relation.relation.targetEntity?uncap_first}Cursor.moveToFirst();
			result = ${project_name?cap_first}Contract.${relation.relation.targetEntity}.cursorToItem(${relation.relation.targetEntity?uncap_first}Cursor);
		} else {
			result = null;
		}
		${relation.relation.targetEntity?uncap_first}Cursor.close();

		return result;
	}
			<#elseif (relation.relation.type == "OneToMany")>
	/**
	 * Get associate ${relation.name?cap_first}.
	 * @param item ${curr.name}
	 * @return ${relation.relation.targetEntity?cap_first}
	 */
	public ArrayList<${relation.relation.targetEntity?cap_first}> getAssociate${relation.name?cap_first}(
			final ${curr.name} item) {
		ArrayList<${relation.relation.targetEntity?cap_first}> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor ${relation.relation.targetEntity?uncap_first}Cursor = prov.query(
				${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI,
				${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_COLS,
				${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}.COL_${relation.relation.mappedBy?upper_case}
						+ "= ?",
				new String[]{String.valueOf(item.get${curr_ids[0].name?cap_first}())},
				null);

		result = ${project_name?cap_first}Contract.${relation.relation.targetEntity}.cursorToItems(
						${relation.relation.targetEntity?uncap_first}Cursor);
		${relation.relation.targetEntity?uncap_first}Cursor.close();

		return result;
	}
			<#elseif (relation.relation.type == "ManyToMany")>
	/**
	 * Get associate ${relation.name?cap_first}.
	 * @param item ${curr.name}
	 * @return ${relation.relation.targetEntity?cap_first}
	 */
	public ArrayList<${relation.relation.targetEntity?cap_first}> getAssociate${relation.name?cap_first}(
			final ${curr.name} item) {
		ArrayList<${relation.relation.targetEntity?cap_first}> result;
		ContentResolver prov = this.getContext().getContentResolver();
		Cursor ${relation.relation.joinTable?uncap_first}Cursor = prov.query(
				${relation.relation.joinTable?cap_first}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI,
				${project_name?cap_first}Contract.${relation.relation.joinTable?cap_first}.ALIASED_COLS,
				${project_name?cap_first}Contract.${relation.relation.joinTable?cap_first}.${NamingUtils.alias(relation.relation.mappedBy)} + "= ?",
				new String[]{String.valueOf(item.get${curr_ids[0].name?cap_first}())},
				null);

		if (${relation.relation.joinTable?uncap_first}Cursor.getCount() > 0) {
			${relation.relation.targetEntity}Criterias ${relation.relation.targetEntity?uncap_first}Crits =
					new ${relation.relation.targetEntity}Criterias(GroupType.AND);
			Criteria inCrit = new Criteria();
			ArrayValue arrayValue = new ArrayValue();
			inCrit.setKey(${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}.${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)});
			inCrit.setType(Type.IN);
			inCrit.addValue(arrayValue);
			${relation.relation.targetEntity?uncap_first}Crits.add(inCrit);

			while (${relation.relation.joinTable?uncap_first}Cursor.moveToNext()) {
				int index = ${relation.relation.joinTable?uncap_first}Cursor.getColumnIndex(
						${project_name?cap_first}Contract.${relation.relation.joinTable?cap_first}.${NamingUtils.alias(relation.relation.targetEntity)}ID);
				int ${relation.relation.targetEntity?uncap_first}Id = ${relation.relation.joinTable?uncap_first}Cursor.getInt(index);

				arrayValue.addValue(String.valueOf(
						${relation.relation.targetEntity?uncap_first}Id));
			}
			${relation.relation.joinTable?uncap_first}Cursor.close();
			Cursor ${relation.relation.targetEntity?uncap_first}Cursor = prov.query(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI,
					${project_name?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_COLS,
					${relation.relation.targetEntity?uncap_first}Crits.toSQLiteSelection(),
					${relation.relation.targetEntity?uncap_first}Crits.toSQLiteSelectionArgs(),
					null);

			result = ${project_name?cap_first}Contract.${relation.relation.targetEntity}.cursorToItems(${relation.relation.targetEntity?uncap_first}Cursor);
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
