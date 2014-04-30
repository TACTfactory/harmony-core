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
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import ${project_namespace}.provider.utils.ProviderUtils;
${ImportUtils.importRelatedCriterias(curr)}
${ImportUtils.importManyToManyTargetSQLiteAdapters(curr)}
${ImportUtils.importRelatedEntities(curr, true)}
${ImportUtils.importRelatedEnums(curr, false)}
${ImportUtils.importRelatedProviderAdapters(curr, false)}
import ${project_namespace}.provider.${project_name?cap_first}Provider;
import ${project_namespace}.provider.contract.${curr.name?cap_first}Contract;
<#if (InheritanceUtils.isExtended(curr))>
import ${project_namespace}.provider.contract.${curr.inheritance.superclass.name?cap_first}Contract;
</#if>
<#list relations as relation>
	<#if (relation.relation.type == "ManyToMany")>
import ${project_namespace}.provider.contract.${relation.relation.joinTable?cap_first}Contract;
	</#if>
import ${project_namespace}.provider.contract.${relation.relation.targetEntity?cap_first}Contract;
</#list>

/**
 * ${curr.name?cap_first} Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ${curr.name?cap_first}ProviderUtilsBase
			extends ProviderUtils<${curr.name?cap_first}> {
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


		ContentValues itemValues = ${ContractUtils.getContractItemToContentValues(curr)}(item);
		<#list curr_ids as id>
			<#if id.strategy == "IDENTITY">
		itemValues.remove(${ContractUtils.getContractCol(id)});
			</#if>
		</#list>

		operations.add(ContentProviderOperation.newInsert(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI)
						.withValues(itemValues)
						.build());

		<#list relations as relation>
			<#assign targetEntity = entities[relation.relation.targetEntity] />
			<#assign fieldNames = ContractUtils.getColumnsNames(relation) />
			<#if (relation.relation.type == "OneToMany") >
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
			Criterion inCrit = new Criterion();
			crit.add(inCrit);
			
			inCrit.setKey(<#list IdsUtils.getAllIdsColsFromArray(targetEntity.ids) as refId>${refId}<#if refId_has_next>
					+ " || '::dirtyHack::' ||"
					+ </#if></#list>);
			inCrit.setType(Type.IN);
			ArrayValue inValue = new ArrayValue();
			inCrit.addValue(inValue);

			for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
				inValue.addValue(<#list IdsUtils.getAllIdsGetters(targetEntity) as refId>String.valueOf(item.get${relation.name?cap_first}().get(i)${refId})<#if refId_has_next>
						+ " || ::dirtHack:: ||"
						+ </#if></#list>);
			}

			operations.add(ContentProviderOperation.newUpdate(${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
					<#list curr_ids as id>
						<#if id.strategy == "IDENTITY">
					.withValueBackReference(
							${relation.relation.targetEntity}Contract.${relation.relation.targetEntity}
									.${NamingUtils.alias(MetadataUtils.getMappedField(relation).name)}_${id.name?upper_case},
							0)
						<#else>
					.withValue(
							${relation.relation.targetEntity}Contract.${relation.relation.targetEntity}
									.${NamingUtils.alias(MetadataUtils.getMappedField(relation).name)}_${id.name?upper_case},
							item.get${id.name?cap_first}())
						</#if>
					</#list>
					.withSelection(
							crit.toSQLiteSelection(),
							crit.toSQLiteSelectionArgs())
					.build());
		}
			<#elseif (relation.relation.type == "ManyToMany") >
				<#assign joinTable = entities[relation.relation.joinTable] />
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			for (${targetEntity.name} ${targetEntity.name?uncap_first} : item.get${relation.name?cap_first}()) {
				ContentValues ${targetEntity.name?uncap_first}Values = new ContentValues();
				<#list relation.relation.field_ref as refField>
				${targetEntity.name?uncap_first}Values.put(
						${ContractUtils.getContractClass(joinTable)}.${NamingUtils.alias(fieldNames[refField_index])},
						${targetEntity.name?uncap_first}.get${refField.name?cap_first}());
				</#list>

				operations.add(ContentProviderOperation.newInsert(
					${joinTable.name}ProviderAdapter.${joinTable.name?upper_case}_URI)
					    .withValues(${targetEntity.name?uncap_first}Values)
						<#list curr.ids as id>
							<#if id.strategy == "IDENTITY">
					    .withValueBackReference(
								${ContractUtils.getContractClass(joinTable)}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case},
					    		0)
							<#else>
						.withValue(
								${ContractUtils.getContractClass(joinTable)}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case},
								item.get${id.name?cap_first}())	
							</#if>
						</#list>
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
				<#assign idTypes = IdsUtils.getAllIdsTypesFromArray(curr_ids) />
				<#list IdsUtils.getAllIdsSettersFromArray(curr_ids) as id>
				item${id}<#if idTypes[id_index]?lower_case == "int" || idTypes[id_index]?lower_case == "integer">Integer.parseInt(</#if>result.getPathSegments().get(${id_index + 1}<#if idTypes[id_index]?lower_case != "string">)</#if>));
				</#list>
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

		ContentValues itemValues = ${ContractUtils.getContractItemToContentValues(curr)}(item<#list relations as relation><#if (relation.internal)>,
					${relation.name?uncap_first}Id</#if></#list>);
		<#list curr_ids as id>
			<#if id.strategy == "IDENTITY">
		itemValues.remove(${ContractUtils.getContractCol(id)});
			</#if>
		</#list>

		operations.add(ContentProviderOperation.newInsert(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI)
			    	.withValues(itemValues)
			    	.build());


		<#list relations as relation>
			<#assign fieldNames = ContractUtils.getColumnsNames(relation) />
			<#if (relation.relation.type == "OneToMany") >
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
			Criterion inCrit = new Criterion();
			crit.add(inCrit);
			
			inCrit.setKey(${ContractUtils.getContractCol(entities[relation.relation.targetEntity].ids[0])});
			inCrit.setType(Type.IN);
			ArrayValue inValue = new ArrayValue();
			inCrit.addValue(inValue);

			for (int i = 0; i < item.get${relation.name?cap_first}().size(); i++) {
				inValue.addValue(String.valueOf(item.get${relation.name?cap_first}().get(i).get${entities[relation.relation.targetEntity].ids[0].name?cap_first}()));
			}

			operations.add(ContentProviderOperation.newUpdate(${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
					<#list curr_ids as id>
						<#if id.strategy == "IDENTITY">
					.withValueBackReference(
							${relation.relation.targetEntity}Contract.${relation.relation.targetEntity}
									.${NamingUtils.alias(MetadataUtils.getMappedField(relation).name)}_${id.name?upper_case},
							0)
						<#else>
					.withValue(
							${relation.relation.targetEntity}Contract.${relation.relation.targetEntity}
									.${NamingUtils.alias(MetadataUtils.getMappedField(relation).name)}_${id.name?upper_case},
							item.get${id.name?cap_first}())
						</#if>
					</#list>
					.withSelection(
							crit.toSQLiteSelection(),
							crit.toSQLiteSelectionArgs())
					.build());
		}
			<#elseif (relation.relation.type == "ManyToMany") >
				<#assign joinTable = entities[relation.relation.joinTable] />
				<#assign targetEntity = entities[relation.relation.targetEntity] />
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			for (${targetEntity.name} ${targetEntity.name?uncap_first} : item.get${relation.name?cap_first}()) {
				ContentValues ${targetEntity.name?uncap_first}Values = new ContentValues();
				<#list relation.relation.field_ref as refField>
				${targetEntity.name?uncap_first}Values.put(
						${ContractUtils.getContractClass(joinTable)}.${NamingUtils.alias(fieldNames[refField_index])},
						${targetEntity.name?uncap_first}.get${refField.name?cap_first}());
				</#list>

				operations.add(ContentProviderOperation.newInsert(
					${joinTable.name}ProviderAdapter.${joinTable.name?upper_case}_URI)
					    .withValues(${targetEntity.name?uncap_first}Values)
						<#list curr.ids as id>
							<#if id.strategy == "IDENTITY">
					    .withValueBackReference(
								${ContractUtils.getContractClass(joinTable)}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case},
					    		0)
							<#else>
						.withValue(
								${ContractUtils.getContractClass(joinTable)}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case},
								item.get${id.name?cap_first}())	
							</#if>
						</#list>
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
				item.set${curr.ids[0].name?cap_first}(<#if curr.ids[0].type?lower_case=="int" || curr.ids[0].type?lower_case=="integer">Integer.parseInt(result.getLastPathSegment())<#else>result.getLastPathSegment()</#if>);
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

		Uri uri = ${curr.name}ProviderAdapter.${curr.name?upper_case}_URI;
		<#list curr_ids as id>
		uri = Uri.withAppendedPath(uri, String.valueOf(item.get${id.name?cap_first}()));
		</#list>

		result = prov.delete(uri,
			null,
			null);

		return result;
	}


	/**
	 * Query the DB.
	 * @param item The item with its ids set
	 * @return ${curr.name?cap_first}
	 */
	public ${curr.name?cap_first} query(final ${curr.name} item) {
		return this.query(<#list curr_ids as id>item.get${id.name?cap_first}()<#if id_has_next>, </#if></#list>);
	}

	/**
	 * Query the DB.
	 *
	 * <#list curr_ids as id>@param ${id.name} ${id.name}<#if id_has_next>
	 * </#if></#list>
	 *
	 * @return ${curr.name?cap_first}
	 */
	public ${curr.name?cap_first} query(<#list curr_ids as id>final ${id.type} ${id.name}<#if id_has_next>,
				</#if></#list>) {
		${curr.name?cap_first} result = null;
		ContentResolver prov = this.getContext().getContentResolver();

		CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
		<#assign idGetters = IdsUtils.getAllIdsGettersFromArray(curr_ids) />
		<#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>
		crits.add(${id},
					String.valueOf(${idGetters[id_index]}));
		</#list>

		Cursor cursor = prov.query(
			${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI,
			${curr.name?cap_first}Contract.${curr.name?cap_first}.ALIASED_COLS,
			crits.toSQLiteSelection(),
			crits.toSQLiteSelectionArgs(),
			null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = ${curr.name?cap_first}Contract.${curr.name}.cursorToItem(cursor);
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
				${curr.name?cap_first}Contract.${curr.name?cap_first}.ALIASED_COLS,
				null,
				null,
				null);

		result = ${curr.name?cap_first}Contract.${curr.name}.cursorToItems(cursor);

		cursor.close();

		return result;
	}

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param expression The criteria expression defining the selection and selection args
	 * @return ArrayList<${curr.name}>
	 */
	public ArrayList<${curr.name}> query(CriteriaExpression expression) {
		ArrayList<${curr.name}> result =
					new ArrayList<${curr.name}>();
		ContentResolver prov = this.getContext().getContentResolver();

		Cursor cursor = prov.query(
				${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				${curr.name?cap_first}Contract.${curr.name?cap_first}.ALIASED_COLS,
				expression.toSQLiteSelection(),
				expression.toSQLiteSelectionArgs(),
				null);

		result = ${curr.name?cap_first}Contract.${curr.name}.cursorToItems(cursor);

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
		ContentValues itemValues = ${ContractUtils.getContractItemToContentValues(curr)}(item);

		Uri uri = ${curr.name}ProviderAdapter.${curr.name?upper_case}_URI;
		<#list curr_ids as id>
		uri = Uri.withAppendedPath(uri, String.valueOf(item.get${id.name?cap_first}()));
		</#list>


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		<#list relations as relation>
			<#if (relation.relation.type == "OneToMany")>
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			String selection;
			String[] selectionArgs;
			// Set new ${relation.name} for ${curr.name}
			CriteriaExpression ${relation.name}Crit = 
					new CriteriasExpression(GroupType.AND);
			Criterion crit = new Criterion();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(<#list IdsUtils.getAllIdsColsFromArray(entities[relation.relation.targetEntity].ids) as id>${id}<#if id_has_next>
					+ " || '::dirtyHack::' || " 
					+ </#if></#list>);
			crit.addValue(values);
			${relation.name}Crit.add(crit);


			for (${relation.relation.targetEntity} ${relation.name} : item.get${relation.name?cap_first}()) {
				values.addValue(<#list entities[relation.relation.targetEntity].ids as id>
					String.valueOf(${relation.name}.get${id.name?cap_first}())<#if id_has_next>
					+ "::dirtyHack::"
					+ </#if></#list>);
			}
			selection = ${relation.name}Crit.toSQLiteSelection();
			selectionArgs = ${relation.name}Crit.toSQLiteSelectionArgs();

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
					<#list curr_ids as id>
					.withValue(
							${ContractUtils.getContractCol(MetadataUtils.getMappedField(relation))}_${id.name?upper_case},
							item.get${id.name?cap_first}())
					</#list>
					.withSelection(
							selection,
							selectionArgs)
					.build());

			// Remove old associated ${relation.name}
			crit.setType(Type.NOT_IN);
			<#list curr_ids as id>
			${relation.name}Crit.add(${ContractUtils.getContractCol(MetadataUtils.getMappedField(relation))}_${id.name?upper_case},
					String.valueOf(item.get${id.name?cap_first}()),
					Type.EQUALS);
			</#list>
			

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
					<#list curr_ids as id>
					.withValue(
							${ContractUtils.getContractCol(MetadataUtils.getMappedField(relation))}_${id.name?upper_case},
							null)
					</#list>
					.withSelection(
							${relation.name}Crit.toSQLiteSelection(),
							${relation.name}Crit.toSQLiteSelectionArgs())
					.build());
		}

			<#elseif (relation.relation.type == "ManyToMany") >
		operations.add(ContentProviderOperation.newDelete(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
				<#list curr_ids as id>
				.withSelection(${relation.relation.joinTable}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case} + "= ?",
								new String[]{String.valueOf(item.get${id.name?cap_first}())})
				</#list>
				.build());

		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			<#list entities[relation.relation.targetEntity].ids as id>
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.name)}_${id.name?upper_case},
					${relation.relation.targetEntity?uncap_first}.get${id.name?cap_first}());
			</#list>
			<#list curr.ids as id>
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case},
					item.get${id.name?cap_first}());
			</#list>

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
		ContentValues itemValues = ${ContractUtils.getContractItemToContentValues(curr)}(
				item<#list relations as relation><#if (relation.internal)>,
				${relation.name?uncap_first}Id</#if></#list>);

		Uri uri = ${curr.name}ProviderAdapter.${curr.name?upper_case}_URI;
		<#list curr_ids as id>
		uri = Uri.withAppendedPath(uri, String.valueOf(item.get${id.name?cap_first}()));
		</#list>


		operations.add(ContentProviderOperation.newUpdate(uri)
				.withValues(itemValues)
				.build());


		<#list relations as relation>
			<#if (relation.relation.type == "OneToMany")>
		if (item.get${relation.name?cap_first}() != null && item.get${relation.name?cap_first}().size() > 0) {
			String selection;
			String[] selectionArgs;
			// Set new ${relation.name} for ${curr.name}
			CriteriaExpression ${relation.name}Crit =
						new CriteriaExpression(GroupType.AND);
			Criterion crit = new Criterion();
			ArrayValue values = new ArrayValue();
			crit.setType(Type.IN);
			crit.setKey(<#list IdsUtils.getAllIdsColsFromArray(entities[relation.relation.targetEntity].ids) as id>${id}<#if id_has_next>
					+ " || '::dirtyHack::' || " 
					+ </#if></#list>);
			crit.addValue(values);
			${relation.name}Crit.add(crit);


			for (${relation.relation.targetEntity} ${relation.name} : item.get${relation.name?cap_first}()) {
				values.addValue(<#list entities[relation.relation.targetEntity].ids as id>
					String.valueOf(${relation.name}.get${id.name?cap_first}())<#if id_has_next>
					+ "::dirtyHack::"
					+ </#if></#list>);
			}
			selection = ${relation.name}Crit.toSQLiteSelection();
			selectionArgs = ${relation.name}Crit.toSQLiteSelectionArgs();

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
					<#list curr_ids as id>
					.withValue(
							${ContractUtils.getContractCol(MetadataUtils.getMappedField(relation))}_${id.name?upper_case},
							item.get${id.name?cap_first}())
					</#list>
					.withSelection(
							selection,
							selectionArgs)
					.build());

			// Remove old associated ${relation.name}
			crit.setType(Type.NOT_IN);
			<#list curr_ids as id>
			${relation.name}Crit.add(${ContractUtils.getContractCol(MetadataUtils.getMappedField(relation))}_${id.name?upper_case},
					String.valueOf(item.get${id.name?cap_first}()),
					Type.EQUALS);
			</#list>
			

			operations.add(ContentProviderOperation.newUpdate(
					${relation.relation.targetEntity}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI)
					<#list curr_ids as id>
					.withValue(
							${ContractUtils.getContractCol(MetadataUtils.getMappedField(relation))}_${id.name?upper_case},
							null)
					</#list>
					.withSelection(
							${relation.name}Crit.toSQLiteSelection(),
							${relation.name}Crit.toSQLiteSelectionArgs())
					.build());
		}

			<#elseif (relation.relation.type == "ManyToMany") >
		operations.add(ContentProviderOperation.newDelete(${relation.relation.joinTable}ProviderAdapter.${relation.relation.joinTable?upper_case}_URI)
				<#list curr_ids as id>
				.withSelection(${relation.relation.joinTable}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case} + "= ?",
								new String[]{String.valueOf(item.get${id.name?cap_first}())})
				</#list>
				.build());

		for (${relation.relation.targetEntity} ${relation.relation.targetEntity?uncap_first} : item.get${relation.name?cap_first}()) {
			ContentValues ${relation.relation.targetEntity?uncap_first}Values = new ContentValues();
			<#list entities[relation.relation.targetEntity].ids as id>
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.name)}_${id.name?upper_case},
					${relation.relation.targetEntity?uncap_first}.get${id.name?cap_first}());
			</#list>
			<#list curr.ids as id>
			${relation.relation.targetEntity?uncap_first}Values.put(${relation.relation.joinTable}Contract.${relation.relation.joinTable}.${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case},
					item.get${id.name?cap_first}());
			</#list>

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
				${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_COLS,
				${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)} + "= ?",
				new String[]{String.valueOf(item.get${relation.name?cap_first}().get${entities[relation.relation.targetEntity].ids[0].name?cap_first}())},
				null);

		if (${relation.relation.targetEntity?uncap_first}Cursor.getCount() > 0) {
			${relation.relation.targetEntity?uncap_first}Cursor.moveToFirst();
			result = ${relation.relation.targetEntity}Contract.${relation.relation.targetEntity}.cursorToItem(${relation.relation.targetEntity?uncap_first}Cursor);
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
				${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_COLS,
				<#list curr_ids as id>
				${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_COL_${relation.relation.mappedBy?upper_case}_${id.name?upper_case}
						+ "= ?<#if id_has_next> AND "
						+ </#if></#list>",
				new String[]{String.valueOf(item.get${curr_ids[0].name?cap_first}())},
				null);

		result = ${relation.relation.targetEntity}Contract.${relation.relation.targetEntity}.cursorToItems(
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
				${relation.relation.joinTable?cap_first}Contract.${relation.relation.joinTable?cap_first}.ALIASED_COLS,
				<#list curr_ids as id>${relation.relation.joinTable?cap_first}Contract.${relation.relation.joinTable?cap_first}.ALIASED_${NamingUtils.alias(relation.relation.mappedBy)}_${id.name?upper_case} 
						+ "= ?<#if id_has_next> AND "
						+ </#if></#list>",
				new String[]{String.valueOf(item.get${curr_ids[0].name?cap_first}())},
				null);

		if (${relation.relation.joinTable?uncap_first}Cursor.getCount() > 0) {
			CriteriaExpression ${relation.relation.targetEntity?uncap_first}Crits =
					new CriteriaExpression(GroupType.AND);
			Criterion inCrit = new Criterion();
			ArrayValue arrayValue = new ArrayValue();
			inCrit.setKey(${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_${NamingUtils.alias(entities[relation.relation.targetEntity].ids[0].name)});
			inCrit.setType(Type.IN);
			inCrit.addValue(arrayValue);
			${relation.relation.targetEntity?uncap_first}Crits.add(inCrit);

			while (${relation.relation.joinTable?uncap_first}Cursor.moveToNext()) {
				<#list entities[relation.relation.targetEntity].ids as id>
				<#if (id_index == 0)>int </#if>index = ${relation.relation.joinTable?uncap_first}Cursor.getColumnIndex(
						${relation.relation.joinTable?cap_first}Contract.${relation.relation.joinTable?cap_first}.${NamingUtils.alias(relation.name)}_${id.name?upper_case});
				String ${relation.relation.targetEntity?uncap_first}${id.name?cap_first} = ${relation.relation.joinTable?uncap_first}Cursor.getString(index);
				</#list>

				arrayValue.addValue(<#list entities[relation.relation.targetEntity].ids as id>${relation.relation.targetEntity?uncap_first}${id.name?cap_first}<#if id_has_next> + "::dirtyHack::" + </#if></#list>);
			}
			${relation.relation.joinTable?uncap_first}Cursor.close();
			Cursor ${relation.relation.targetEntity?uncap_first}Cursor = prov.query(
					${relation.relation.targetEntity?cap_first}ProviderAdapter.${relation.relation.targetEntity?upper_case}_URI,
					${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity?cap_first}.ALIASED_COLS,
					${relation.relation.targetEntity?uncap_first}Crits.toSQLiteSelection(),
					${relation.relation.targetEntity?uncap_first}Crits.toSQLiteSelectionArgs(),
					null);

			result = ${relation.relation.targetEntity}Contract.${relation.relation.targetEntity}.cursorToItems(${relation.relation.targetEntity?uncap_first}Cursor);
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
