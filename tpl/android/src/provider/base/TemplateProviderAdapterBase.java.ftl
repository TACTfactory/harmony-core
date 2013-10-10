<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign inherited = false />
<#assign ext = curr.name?cap_first />
<#if (curr.internal)>
	<#assign ext = "Void" />
</#if>
<#if (curr.extends?? && entities[curr.extends]??)>
	<#assign extends = curr.extends />
	<#assign inherited = true />
</#if>
<@header?interpret />
package ${local_namespace}.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import ${data_namespace}.${curr.name}SQLiteAdapter;
<#if (!internal)>
import ${entity_namespace}.${curr.name};
</#if>import ${local_namespace}.${project_name?cap_first}Provider;
<#if (inherited)>

import ${local_namespace}.${extends?cap_first}ProviderAdapter;
import ${data_namespace}.${extends?cap_first}SQLiteAdapter;

import ${project_namespace}.criterias.${curr.name}Criterias;
import ${project_namespace}.criterias.base.Criteria;
import ${project_namespace}.criterias.base.Criteria.Type;
import ${project_namespace}.criterias.base.CriteriasBase;
import ${project_namespace}.criterias.base.CriteriasBase.GroupType;
import ${project_namespace}.criterias.base.value.ArrayValue;
</#if>
${ImportUtils.importRelatedSQLiteAdapters(curr, false, true)}

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
		this.uriIds.add(${curr.name?upper_case}_ONE);
		<#list curr.relations as relation>
			<#if !relation.internal>
		this.uriIds.add(${curr.name?upper_case}_${relation.name?upper_case});
			</#if>
		</#list>
	}

	@Override
	public String getType(final Uri uri) {
		String result = null;
		final String single =
				"vnc.android.cursor.item/" + ${project_name?cap_first}Provider.authority + ".";
		final String collection =
				"vnc.android.cursor.collection/" + ${project_name?cap_first}Provider.authority + ".";

		int matchedUri = ${project_name?cap_first}ProviderBase
				.getUriMatcher().match(uri);

		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				result = single + "${curr.name?lower_case}";
				break;
			case ${curr.name?upper_case}_ALL:
				result = collection + "${curr.name?lower_case}";
				break;
			<#list curr.relations as relation>
				<#if !relation.internal>
			case ${curr.name?upper_case}_${relation.name?upper_case}:
				result = <#if relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany">collection<#else>single</#if> + "${curr.name?lower_case}";
				break;
				</#if>
			</#list>
		}

		return result;
	}

	@Override
	public int delete(
			final Uri uri,
			String selection,
			String[] selectionArgs) {

		int matchedUri = ${project_name?cap_first}ProviderBase
					.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				<#if inherited>
				Uri motherUri = Uri.withAppendedPath(
						${extends?cap_first}ProviderAdapter.${extends?upper_case}_URI, String.valueOf(id));
				result = this.ctx.getContentResolver().delete(motherUri,
						selection, selectionArgs);
				<#else>
				selection = ${curr.name?cap_first}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)}
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection,
						selectionArgs);
				</#if>
				break;
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
				// Query the ids of the changing fields.
				Cursor idsCursor = this.adapter.query(
						new String[]{${curr.name}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr.ids[0].name)}},
						selection,
						selectionArgs,
						null,
						null,
						null);
				// If there are ids
				if (idsCursor.getCount() > 0) {
					CriteriasBase parentCrit = this.cursorToIDSelection(idsCursor, ${curr.extends}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr.ids[0].name)});
					String parentSelection = parentCrit.toSQLiteSelection();
					String[] parentSelectionArgs = parentCrit.toSQLiteSelectionArgs();
					result = this.ctx.getContentResolver().delete(
							${curr.extends}ProviderAdapter.${curr.extends?upper_case}_URI,
							parentSelection,
							parentSelectionArgs);
				}
				<#else>
				result = this.adapter.delete(
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
	
	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		int matchedUri = ${project_name?cap_first}ProviderBase
				.getUriMatcher().match(uri);
		<#if inherited>ContentValues ${curr.name?uncap_first}Values =
			this.extractContentValues(values);</#if>
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
				Uri newUri = this.ctx.getContentResolver().insert(
						${extends}ProviderAdapter.${extends?upper_case}_URI,
						values);
				int newId = Integer.parseInt(newUri.getPathSegments().get(1));
				${curr.name?uncap_first}Values.put(${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)}, newId);
				id = (int) this.adapter.insert(null, ${curr.name?uncap_first}Values);
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
		int id = 0;

		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				result = this.queryById(uri.getPathSegments().get(1));
				break;

			case ${curr.name?upper_case}_ALL:
				result = this.adapter.query(
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder);
				break;
			
			<#list curr.relations as relation>
				<#if !relation.internal>
			case ${curr.name?upper_case}_${relation.name?upper_case}:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				<#if relation.relation.type == "OneToOne" || relation.relation.type == "ManyToOne">
				${curr.name?uncap_first}Cursor = this.queryById(uri.getPathSegments().get(1));
				
				if (${curr.name?uncap_first}Cursor.getCount() > 0) {
					${curr.name?uncap_first}Cursor.moveToFirst();
					int ${relation.name?uncap_first}Id = ${curr.name?uncap_first}Cursor.getInt(${curr.name?uncap_first}Cursor.getColumnIndex(
									${curr.name}SQLiteAdapter.COL_${relation.name?upper_case}));
					
					${relation.relation.targetEntity}SQLiteAdapter ${relation.relation.targetEntity?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
					${relation.relation.targetEntity?uncap_first}Adapter.open(this.getDb());
					result = ${relation.relation.targetEntity?uncap_first}Adapter.query(${relation.name?uncap_first}Id);
				}
				<#else>
					<#if relation.relation.type == "ManyToMany">
				${relation.relation.joinTable}SQLiteAdapter ${relation.name}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
				${relation.name}Adapter.open(this.getDb());
				result = ${relation.name}Adapter.getBy${curr.name}(id);
					<#else>
				${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
				${relation.name}Adapter.open(this.getDb());
					<#if relation.relation.inversedBy??>
				result = ${relation.name}Adapter.getBy${relation.relation.inversedBy?cap_first}(id);
					<#else>
				result = ${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(id);
					</#if>
					</#if>
				</#if>
				break;

				</#if>
			</#list>
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
			final String selection,
			final String[] selectionArgs) {

		<#if inherited>ContentValues ${curr.name?uncap_first}Values = this.extractContentValues(values);</#if>
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
				.match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				String id = uri.getPathSegments().get(1);
				<#if inherited>
				Uri parentUri = Uri.withAppendedPath(${extends?cap_first}ProviderAdapter.${extends?upper_case}_URI,
						String.valueOf(id));
				result = this.ctx.getContentResolver().update(
						parentUri,
						values,
						null,
						null);
				result += this.adapter.update(
						${curr.name?uncap_first}Values,
						${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)} + " = ?",
						new String[]{String.valueOf(id)});
				<#else>
				result = this.adapter.update(
						values,
						${curr.name?cap_first}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)} + " = "
						+ id,
						selectionArgs);
				</#if>
				break;
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
				// Query the ids of the changing fields.
				Cursor idsCursor = this.adapter.query(
						new String[]{${curr.name}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr.ids[0].name)}},
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
								${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)});

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
								${curr.extends}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)});

						String parentSelection = parentCrit.toSQLiteSelection();
						String[] parentSelectionArgs = parentCrit
								.toSQLiteSelectionArgs();
						// Update the parents tables
						result = this.ctx.getContentResolver().update(
								${curr.extends}ProviderAdapter.${curr.extends?upper_case}_URI,
								values,
								parentSelection,
								parentSelectionArgs);
					}
				}
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

	<#if inherited>
	protected ContentValues extractContentValues(ContentValues from) {
		ContentValues to = new ContentValues();
		for (String colName : ${curr.name}SQLiteAdapter.COLS) {
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

	protected CriteriasBase cursorToIDSelection(Cursor cursor, String key) {
		${curr.name}Criterias crit = new ${curr.name}Criterias(GroupType.AND);
		Criteria inCrit = new Criteria();
		inCrit.setKey(key);
		inCrit.setType(Type.IN);
		ArrayValue inArray = new ArrayValue();
		cursor.moveToFirst();
		do {
			inArray.addValue(cursor.getString(
				cursor.getColumnIndex(${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)})));
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

	private Cursor queryById(String id) {
		Cursor result = null;
		String selection = ${curr.name}SQLiteAdapter.ALIASED_COL_ID
						+ " = ?";
		String[] selectionArgs = new String[]{id};
		result = this.adapter.query(
					${curr.name}SQLiteAdapter.ALIASED_COLS,
					selection,
					selectionArgs,
					null,
					null,
					null);
		return result;
	}
}

