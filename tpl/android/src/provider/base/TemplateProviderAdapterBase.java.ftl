<#include utilityPath + "all_imports.ftl" />
<#assign inherited = joinedInheritance || singleTabInheritance && curr.inheritance.superclass?? />
<#assign ext = curr.name?cap_first />
<#assign hasIds = (curr_ids?? && curr_ids?size > 0) />
<#if (curr.internal)>
	<#assign ext = "Void" />
</#if>
<@header?interpret />
package ${local_namespace}.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

<#if (curr.inheritance?? && curr.inheritance.superclass??) >import com.google.common.base.Strings;</#if>

<#if (curr.options.sync??)>
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
</#if>
<#if (!curr.internal)>
import ${entity_namespace}.${curr.name};
</#if>import ${local_namespace}.${project_name?cap_first}Provider;
import ${local_namespace}.${project_name?cap_first}Contract;
<#if (inherited)>
	<#if joinedInheritance>
import ${local_namespace}.${curr.inheritance.superclass.name?cap_first}ProviderAdapter;
import ${data_namespace}.${curr.inheritance.superclass.name?cap_first}SQLiteAdapter;

import ${project_namespace}.criterias.${curr.name}Criterias;
import ${project_namespace}.criterias.base.Criteria;
import ${project_namespace}.criterias.base.Criteria.Type;
import ${project_namespace}.criterias.base.CriteriasBase;
import ${project_namespace}.criterias.base.CriteriasBase.GroupType;
import ${project_namespace}.criterias.base.value.ArrayValue;
	<#elseif singleTabInheritance>
import com.google.common.collect.ObjectArrays;
	</#if>
</#if>
${ImportUtils.importRelatedSQLiteAdapters(curr, false, true, false)}
<#if inherited && joinedInheritance>
import ${project_namespace}.harmony.util.DatabaseUtil;
</#if>

/**
 * ${curr.name?cap_first}ProviderAdapterBase.
 */
public abstract class ${curr.name?cap_first}ProviderAdapterBase
				extends ProviderAdapterBase<${ext}> {

	/** TAG for debug purpose. */
	protected static final String TAG = "${curr.name?cap_first}ProviderAdapter";

	/** ${curr.name?upper_case}_URI. */
	public	  static Uri ${curr.name?upper_case}_URI;

	/** ${curr.name?uncap_first} type. */
	protected static final String ${curr.name?uncap_first}Type =
			"${curr.name?lower_case}";

	/** ${curr.name?upper_case}_ALL. */
	protected static final int ${curr.name?upper_case}_ALL =
			${provider_id?c};
	<#if (hasIds)>
	/** ${curr.name?upper_case}_ONE. */
	protected static final int ${curr.name?upper_case}_ONE =
			${(provider_id + 1)?c};

	<#assign provider_id = provider_id + 2 />
	<#list curr.relations as relation>
		<#if !relation.internal>
	/** ${curr.name?upper_case}_${relation.name?upper_case}. */
	protected static final int ${curr.name?upper_case}_${relation.name?upper_case} =
			${(provider_id)?c};
	<#assign provider_id = provider_id + 1 />
		</#if>
	</#list>
	</#if>

	/**
	 * Static constructor.
	 */
	static {
		${curr.name?upper_case}_URI =
				${project_name?cap_first}Provider.generateUri(
						${curr.name?uncap_first}Type);
		${project_name?cap_first}Provider.getUriMatcher().addURI(
				${project_name?cap_first}Provider.authority,
				${curr.name?uncap_first}Type,
				${curr.name?upper_case}_ALL);
		<#if (hasIds)>
		${project_name?cap_first}Provider.getUriMatcher().addURI(
				${project_name?cap_first}Provider.authority,
				${curr.name?uncap_first}Type + "/#",
				${curr.name?upper_case}_ONE);
		<#list curr.relations as relation>
			<#if !relation.internal>
		${project_name?cap_first}Provider.getUriMatcher().addURI(
				${project_name?cap_first}Provider.authority,
				${curr.name?uncap_first}Type + "/#/${relation.name?lower_case}",
				${curr.name?upper_case}_${relation.name?upper_case});
			</#if>
		</#list>
		</#if>
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ${curr.name?cap_first}ProviderAdapterBase(
				final Context ctx,
				final SQLiteDatabase db) {
		super(ctx);
		this.adapter = new ${curr.name?cap_first}SQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}

		this.uriIds.add(${curr.name?upper_case}_ALL);
		<#if (hasIds)>
		this.uriIds.add(${curr.name?upper_case}_ONE);
		<#list curr.relations as relation>
			<#if !relation.internal>
		this.uriIds.add(${curr.name?upper_case}_${relation.name?upper_case});
			</#if>
		</#list>
		</#if>
	}

	@Override
	public String getType(final Uri uri) {
		String result;
		final String single =
				"vnc.android.cursor.item/"
					+ ${project_name?cap_first}Provider.authority + ".";
		final String collection =
				"vnc.android.cursor.collection/"
					+ ${project_name?cap_first}Provider.authority + ".";

		int matchedUri = ${project_name?cap_first}ProviderBase
				.getUriMatcher().match(uri);

		switch (matchedUri) {
			case ${curr.name?upper_case}_ALL:
				result = collection + "${curr.name?lower_case}";
				break;
		<#if (hasIds)>
			case ${curr.name?upper_case}_ONE:
				result = single + "${curr.name?lower_case}";
				break;
			<#list curr.relations as relation>
				<#if !relation.internal>
			case ${curr.name?upper_case}_${relation.name?upper_case}:
				result = <#if relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany">collection<#else>single</#if> + "${curr.name?lower_case}";
				break;
				</#if>
			</#list>
		</#if>
			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int delete(
			final Uri uri,
			String selection,
			String[] selectionArgs) {
		<#if (curr.options.sync??)>
		ContentValues deleteCv = new ContentValues();
		deleteCv.put(${project_name?cap_first}Contract.${curr.name}.COL_SYNC_DTAG, 1);
		</#if>
		int matchedUri = ${project_name?cap_first}ProviderBase
					.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			<#if (hasIds)>
			case ${curr.name?upper_case}_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				<#if inherited && joinedInheritance>
				Uri motherUri = Uri.withAppendedPath(
						${curr.inheritance.superclass.name?cap_first}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI, String.valueOf(id));
				result = this.ctx.getContentResolver().delete(motherUri,
						selection, selectionArgs);
				<#else>
				selection = ${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)}
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
					<#if (curr.options.sync??)>
				result = this.adapter.update(
						deleteCv,
						selection,
						selectionArgs);
					<#else>
				result = this.adapter.delete(
						selection,
						selectionArgs);
					</#if>
				</#if>
				break;
			</#if>
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
					<#if joinedInheritance>
				// Query the ids of the changing fields.
				Cursor idsCursor = this.adapter.query(
						new String[]{${project_name?cap_first}Contract.${curr_ids[0].owner}.ALIASED_${NamingUtils.alias(curr_ids[0].name)}},
						selection,
						selectionArgs,
						null,
						null,
						null);
				// If there are ids
				if (idsCursor.getCount() > 0) {
					CriteriasBase parentCrit = this.cursorToIDSelection(idsCursor, ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr_ids[0].name)});
					String parentSelection = parentCrit.toSQLiteSelection();
					String[] parentSelectionArgs = parentCrit.toSQLiteSelectionArgs();
					result = this.ctx.getContentResolver().delete(
							${curr.inheritance.superclass.name}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI,
							parentSelection,
							parentSelectionArgs);
				}
					<#else>
				if (Strings.isNullOrEmpty(selection)) {
					selection = 
						${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?";
					selectionArgs = new String[]{
							${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER};
				} else {
					selection += " AND " 
							+ ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)}
							+ " = ?";
					selectionArgs = ObjectArrays.concat(selectionArgs,
							${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER);
				}
						<#if (curr.options.sync??)>
				result = this.adapter.update(
							deleteCv,
							selection,
							selectionArgs);
						<#else>
				result = this.adapter.delete(
							selection,
							selectionArgs);
						</#if>
					</#if>
				<#else>				
					<#if (curr.options.sync??)>
				result = this.adapter.update(
							deleteCv,
							selection,
							selectionArgs);
					<#else>
				result = this.adapter.delete(
							selection,
							selectionArgs);
					</#if>
				</#if>
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}
	
	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		int matchedUri = ${project_name?cap_first}ProviderBase
				.getUriMatcher().match(uri);
		<#if inherited && joinedInheritance>ContentValues ${curr.name?uncap_first}Values =
			DatabaseUtil.extractContentValues(values, ${project_name?cap_first}Contract.${curr.name}.COLS);</#if>
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ALL:
				<#if inherited && joinedInheritance>
				Uri newUri = this.ctx.getContentResolver().insert(
						${curr.inheritance.superclass.name}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI,
						values);
				int newId = Integer.parseInt(newUri.getPathSegments().get(1));
				${curr.name?uncap_first}Values.put(${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)}, newId);
				if (${curr.name?uncap_first}Values.size() > 0) {
					id = (int) this.adapter.insert(null, ${curr.name?uncap_first}Values);
				} else {
					id = (int) this.adapter.insert(${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)}, ${curr.name?uncap_first}Values);
				}
				<#elseif (hasIds)>
				if (values.size() > 0) {
					id = (int) this.adapter.insert(null, values);
				} else {
					id = (int) this.adapter.insert(${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)}, values);
				}
				<#else>
				id = (int) this.adapter.insert(null, values);
				</#if>
				if (id > 0) {
					result = ContentUris.withAppendedId(
							${curr.name?upper_case}_URI,
							id);
				}
				break;
			default:
				result = null;
				break;
		}
		return result;
	}

	@Override
	public Cursor query(final Uri uri,
						String[] projection,
						String selection,
						String[] selectionArgs,
						final String sortOrder) {

		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
				.match(uri);
		Cursor result = null;
		<#if MetadataUtils.hasToOneRelations(curr)>
		Cursor ${curr.name?uncap_first}Cursor;
		</#if>
		<#if (MetadataUtils.hasToManyRelations(curr))>int id = 0;</#if>

		switch (matchedUri) {

			case ${curr.name?upper_case}_ALL:
				<#if inherited && singleTabInheritance>
				if (Strings.isNullOrEmpty(selection)) {
					selection = 
						${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?"<#if (curr.options.sync??)>
								+ " AND " + ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_COL_SYNC_DTAG + " = ?"</#if>;
					selectionArgs = new String[]{${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER<#if (curr.options.sync??)>, "0"</#if>};
				} else {
					selection += " AND " 
							+ ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)}
							+ " = ?"<#if (curr.options.sync??)>
							+ " AND " + ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_COL_SYNC_DTAG 
							+ " = ?"</#if>;
					selectionArgs = ObjectArrays.concat(selectionArgs,
							${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER<#if (curr.options.sync??)>, "0"</#if>);
				}
				</#if>
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			<#if (hasIds)>
			case ${curr.name?upper_case}_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;
			
			<#list curr.relations as relation>
				<#if !relation.internal>
			case ${curr.name?upper_case}_${relation.name?upper_case}:
				<#if relation.relation.type == "OneToOne" || relation.relation.type == "ManyToOne">
				${curr.name?uncap_first}Cursor = this.queryById(uri.getPathSegments().get(1));
				
				if (${curr.name?uncap_first}Cursor.getCount() > 0) {
					${curr.name?uncap_first}Cursor.moveToFirst();
					int ${relation.name?uncap_first}Id = ${curr.name?uncap_first}Cursor.getInt(${curr.name?uncap_first}Cursor.getColumnIndex(
									${project_name?cap_first}Contract.${curr.name}.COL_${relation.name?upper_case}));
					
					${relation.relation.targetEntity}SQLiteAdapter ${relation.relation.targetEntity?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
					${relation.relation.targetEntity?uncap_first}Adapter.open(this.getDb());
					result = ${relation.relation.targetEntity?uncap_first}Adapter.query(${relation.name?uncap_first}Id);
				}
				<#else>
				id = Integer.parseInt(uri.getPathSegments().get(1));
					<#if relation.relation.type == "ManyToMany">
				${relation.relation.joinTable}SQLiteAdapter ${relation.name}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
				${relation.name}Adapter.open(this.getDb());
						<#if (relation.relation.orders?? && relation.relation.orders?size > 0) >
				result = ${relation.name}Adapter.getBy${curr.name}(id, ${project_name?cap_first}Contract.${relation.relation.targetEntity}.ALIASED_COLS, selection, selectionArgs, "<#list relation.relation.orders?keys as orderKey>${orderKey} ${relation.relation.orders[orderKey]}<#if orderKey_has_next> AND </#if></#list>");
						<#else>
				result = ${relation.name}Adapter.getBy${curr.name}(id, ${project_name?cap_first}Contract.${relation.relation.targetEntity}.ALIASED_COLS, selection, selectionArgs, null);
						</#if>
					<#else>
				${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
				${relation.name}Adapter.open(this.getDb());
						<#if (relation.relation.orders?? && relation.relation.orders?size > 0) >
				result = ${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(id, ${project_name?cap_first}Contract.${relation.relation.targetEntity}.ALIASED_COLS, selection, selectionArgs, "<#list relation.relation.orders?keys as orderKey>${orderKey} ${relation.relation.orders[orderKey]}<#if orderKey_has_next> AND </#if></#list>");
						<#else>
				result = ${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(id, ${project_name?cap_first}Contract.${relation.relation.targetEntity}.ALIASED_COLS, selection, selectionArgs, null);
						</#if>
					</#if>
				</#if>
				break;

				</#if>
			</#list>
			</#if>
			default:
				result = null;
				break;
		}

		return result;
	}

	@Override
	public int update(
			final Uri uri,
			final ContentValues values,
			String selection,
			String[] selectionArgs) {
		<#if (curr.options.sync??)>values.put(${project_name?cap_first}Contract.${curr.name}.COL_SYNC_UDATE,
					new DateTime().toString(ISODateTimeFormat.dateTime()));</#if>
		<#if inherited && joinedInheritance>ContentValues ${curr.name?uncap_first}Values = DatabaseUtil.extractContentValues(values, ${project_name?cap_first}Contract.${curr.name}.COLS);</#if>
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
				.match(uri);
		int result = -1;
		switch (matchedUri) {
			<#if (hasIds)>
			case ${curr.name?upper_case}_ONE:
				String id = uri.getPathSegments().get(1);
				<#if inherited>
					<#if joinedInheritance>
				Uri parentUri = Uri.withAppendedPath(${curr.inheritance.superclass.name?cap_first}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI,
						String.valueOf(id));
				result = this.ctx.getContentResolver().update(
						parentUri,
						values,
						null,
						null);
				result += this.adapter.update(
						${curr.name?uncap_first}Values,
						${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)} + " = ?",
						new String[]{String.valueOf(id)});
					<#else>
				result = this.adapter.update(
						values,
						${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)} + " = ?"
							+ " AND " + ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?",
						new String[]{id, ${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER});
					</#if>
				<#else>
				result = this.adapter.update(
						values,
						${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)} + " = "
						+ id,
						selectionArgs);
				</#if>
				break;
			</#if>
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
					<#if joinedInheritance>
				// Query the ids of the changing fields.
				Cursor idsCursor = this.adapter.query(
						new String[]{${project_name?cap_first}Contract.${curr_ids[0].owner}.ALIASED_${NamingUtils.alias(curr_ids[0].name)}},
						selection,
						selectionArgs,
						null,
						null,
						null);
				// If there are ids
				if (idsCursor.getCount() > 0) {
					// If there are values in this table
					if (${curr.name?uncap_first}Values.size() > 0) {
						CriteriasBase currentCrit = this.cursorToIDSelection(
								idsCursor,
								${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)});

						String currentSelection = currentCrit.toSQLiteSelection();
						String[] currentSelectionArgs = currentCrit
								.toSQLiteSelectionArgs();
						// Update the current table
						result += this.adapter.update(
								${curr.name?uncap_first}Values,
								currentSelection,
								currentSelectionArgs);
					}
					// If there are still values to be updated in parents
					if (values.size() > 0) {
						CriteriasBase parentCrit = this.cursorToIDSelection(
								idsCursor,
								${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.${NamingUtils.alias(curr_ids[0].name)});

						String parentSelection = parentCrit.toSQLiteSelection();
						String[] parentSelectionArgs = parentCrit
								.toSQLiteSelectionArgs();
						// Update the parents tables
						result = this.ctx.getContentResolver().update(
								${curr.inheritance.superclass.name}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI,
								values,
								parentSelection,
								parentSelectionArgs);
					}
				}
					<#else>
				if (Strings.isNullOrEmpty(selection)) {
					selection = 
						${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?";
					selectionArgs = new String[]{${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER};
				} else {
					selection += " AND " 
							+ ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)}
							+ " = ?";
					selectionArgs = ObjectArrays.concat(selectionArgs,
							${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER);
				}
				
				result = this.adapter.update(
							values,
							selection,
							selectionArgs);
					</#if>
				<#else>
				result = this.adapter.update(
							values,
							selection,
							selectionArgs);
				</#if>
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}

	<#if inherited && joinedInheritance>
	/**
	 * Transform a cursor of ids into a Criteria.
	 *
	 * @param cursor The cursor
	 * @param key The key to map the ids to
	 *
	 * @return The criteria
	 */
	protected CriteriasBase cursorToIDSelection(Cursor cursor, String key) {
		${curr.name}Criterias crit = new ${curr.name}Criterias(GroupType.AND);
		Criteria inCrit = new Criteria();
		inCrit.setKey(key);
		inCrit.setType(Type.IN);
		ArrayValue inArray = new ArrayValue();
		cursor.moveToFirst();
		do {
			inArray.addValue(cursor.getString(
				cursor.getColumnIndex(${project_name?cap_first}Contract.${curr_ids[0].owner}.${NamingUtils.alias(curr_ids[0].name)})));
		} while (cursor.moveToNext());
		inCrit.addValue(inArray);
		crit.add(inCrit);
		return crit;
	}
	</#if>


	/**
	 * Get the entity URI.
	 * @return The URI
	 */
	@Override
	public Uri getUri() {
		return ${curr.name?upper_case}_URI;
	}

	<#if (hasIds)>
	/**
	 * Query by ID.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The cursor
	 */
	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = ${project_name?cap_first}Contract.${curr_ids[0].owner}.ALIASED_${NamingUtils.alias(curr_ids[0].name)}
						+ " = ?";
		<#if curr.options.sync??>
		selection += " AND " + ${project_name?cap_first}Contract.${curr.name}.ALIASED_COL_SYNC_DTAG + " = ?";</#if>
		<#if inherited && singleTabInheritance>
		selection += " AND " + ${project_name?cap_first}Contract.${curr.inheritance.superclass.name}.ALIASED_${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?";
		String[] selectionArgs = new String[]{id<#if (curr.options.sync??)>, "0"</#if>, ${project_name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER};
		<#else>
		String[] selectionArgs = new String[]{id<#if (curr.options.sync??)>, "0"</#if>};
		</#if>

		result = this.adapter.query(
					${project_name?cap_first}Contract.${curr.name}.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
	</#if>
}

