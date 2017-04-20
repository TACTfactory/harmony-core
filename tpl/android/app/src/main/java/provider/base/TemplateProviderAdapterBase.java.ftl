<#include utilityPath + "all_imports.ftl" />
<#assign inherited = joinedInheritance || singleTabInheritance && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]?? />
<#assign ext = curr.name?cap_first />
<#assign hasIds = (curr_ids?? && curr_ids?size > 0) />
<#if (curr.internal)>
    <#assign ext = "Void" />
</#if>
<@header?interpret />
package ${project_namespace}.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

<#if (curr.inheritance?? && curr.inheritance.superclass??) >import com.google.common.base.Strings;</#if>

<#if (curr.options.sync??)>
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
</#if>
<#if (!curr.internal)>
import ${entity_namespace}.${curr.name};
</#if>import ${project_namespace}.provider.ProviderAdapter;
import ${project_namespace}.provider.${project_name?cap_first}Provider;
import ${project_namespace}.provider.contract.${curr.name?cap_first}Contract;
<#if (inherited && curr.inheritance.superclass??)>
import ${project_namespace}.provider.contract.${curr.inheritance.superclass.name?cap_first}Contract;
</#if>
<#list curr.relations as relation>
    <#if (!relation.internal && (relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany"))>
import ${project_namespace}.provider.contract.${relation.relation.targetEntity?cap_first}Contract;
    </#if>
</#list>
<#if curr.resource>
import ${project_namespace}.provider.contract.ResourceContract;
</#if>
<#if (inherited)>
    <#if joinedInheritance>
import ${project_namespace}.provider.${curr.inheritance.superclass.name?cap_first}ProviderAdapter;
import ${data_namespace}.${curr.inheritance.superclass.name?cap_first}SQLiteAdapter;

import ${project_namespace}.criterias.base.Criterion;
import ${project_namespace}.criterias.base.Criterion.Type;
import ${project_namespace}.criterias.base.CriteriaExpression;
import ${project_namespace}.criterias.base.CriteriaExpression.GroupType;
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
                extends ProviderAdapter<${ext}> {

    /** TAG for debug purpose. */
    protected static final String TAG = "${curr.name?cap_first}ProviderAdapter";

    /** ${curr.name?upper_case}_URI. */
    public      static Uri ${curr.name?upper_case}_URI;

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
                ${curr.name?uncap_first}Type<#list IdsUtils.getAllIdsTypesFromArray(curr_ids) as id><#if id?lower_case == "int" || id?lower_case == "integer"> + "/#"<#else> + "/*"</#if></#list>,
                ${curr.name?upper_case}_ONE);
        <#list curr.relations as relation>
            <#if !relation.internal>
        ${project_name?cap_first}Provider.getUriMatcher().addURI(
                ${project_name?cap_first}Provider.authority,
                ${curr.name?uncap_first}Type<#list IdsUtils.getAllIdsTypesFromArray(curr_ids) as id><#if id?lower_case == "int" || id?lower_case == "integer"> + "/#"<#else> + "/*"</#if></#list> + "/${relation.name?lower_case}",
                ${curr.name?upper_case}_${relation.name?upper_case});
            </#if>
        </#list>
        </#if>
    }

    /**
     * Constructor.
     * @param provider Android application provider of ${project_name?cap_first}
     */
    public ${curr.name?cap_first}ProviderAdapterBase(
            ${project_name?cap_first}ProviderBase provider) {
        super(
            provider,
            new ${curr.name?cap_first}SQLiteAdapter(provider.getContext()));

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
        <#if curr.internal>
        return null;
        <#else>
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
        </#if>
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        <#if (curr.options.sync??)>
        ContentValues deleteCv = new ContentValues();
        deleteCv.put(<#if inherited && singleTabInheritance>${ContractUtils.getContractClass(curr.inheritance.superclass)}<#else>${ContractUtils.getContractClass(curr)}</#if>.COL_SYNC_DTAG, 1);
        </#if>
        int matchedUri = ${project_name?cap_first}ProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            <#if (hasIds)>
            case ${curr.name?upper_case}_ONE:
                <#list IdsUtils.getAllIdsNamesFromArray(curr_ids) as id>
                String ${id} = uri.getPathSegments().get(${id_index + 1});
                </#list>
                <#if inherited && joinedInheritance>
                    <#assign superclassIds = [] />
                    <#list curr_ids as id><#if (id.owner != curr.name)><#assign superclassIds = superclassIds + [id] /></#if></#list>
                Uri motherUri = Uri.withAppendedPath(
                        ${curr.inheritance.superclass.name?cap_first}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI, <#list IdsUtils.getAllIdsNamesFromArray(superclassIds) as id>${id}<#if id_has_next> + "/" + </#if></#list>);
                result = this.ctx.getContentResolver().delete(motherUri,
                        selection, selectionArgs);
                <#else>
                selection =<#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id> ${id}
                        + " = ?"<#if id_has_next>
                        + " AND "
                        +</#if></#list>;
                selectionArgs = new String[${curr_ids?size}];
                <#list IdsUtils.getAllIdsNamesFromArray(curr_ids) as id>
                selectionArgs[${id_index}] = ${id};
                </#list>
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
                android.database.Cursor idsCursor = this.adapter.query(
                        new String[]{${ContractUtils.getContractCol(curr_ids[0], true)}},
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                // If there are ids
                if (idsCursor.getCount() > 0) {
                    CriteriaExpression parentCrit = this.cursorToIDSelection(idsCursor,
                                <#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>${id}<#if id_has_next>
                                + " || ::dirtHack:: || "
                                + </#if></#list>);
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
                        <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if> + " = ?";
                    selectionArgs = new String[]{
                            ${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER};
                } else {
                    selection += " AND "
                            + <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if>
                            + " = ?";
                    selectionArgs = ObjectArrays.concat(selectionArgs,
                            ${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER);
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
            DatabaseUtil.extractContentValues(values, ${ContractUtils.getContractCols(curr)});
        <#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>
        values.put(${id},
                ${curr.name?uncap_first}Values.getAsString(
                        ${id}));
        </#list></#if>
        Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case ${curr.name?upper_case}_ALL:
                <#if inherited && joinedInheritance>
                Uri newUri = this.ctx.getContentResolver().insert(
                        ${curr.inheritance.superclass.name}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI,
                        values);
                int newId = Integer.parseInt(newUri.getPathSegments().get(1));
                ${curr.name?uncap_first}Values.put(${IdsUtils.getAllIdsColsFromArray(curr_ids)[0]}, newId);
                if (${curr.name?uncap_first}Values.size() > 0) {
                    id = (int) this.adapter.insert(null, ${curr.name?uncap_first}Values);
                } else {
                    id = (int) this.adapter.insert(${IdsUtils.getAllIdsColsFromArray(curr_ids)[0]}, ${curr.name?uncap_first}Values);
                }
                <#elseif curr.resource>
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ResourceContract.COL_ID, values);
                }
                <#elseif (hasIds)>
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(${IdsUtils.getAllIdsColsFromArray(curr_ids)[0]}, values);
                }
                <#else>
                id = (int) this.adapter.insert(null, values);
                </#if>
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            ${curr.name?upper_case}_URI,
                            <#list curr_ids as id><#if id.strategy == "IDENTITY">String.valueOf(id)<#else><#if id.relation??><#list IdsUtils.getAllIdsColsFromArray([id]) as refId>values.getAsString(${refId})<#if refId_has_next>
                            + "/"
                            + </#if></#list><#else>values.getAsString(${ContractUtils.getContractCol(id)})</#if></#if><#if id_has_next>
                            + "/"
                            + </#if></#list>);
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;
        <#if MetadataUtils.hasToOneRelations(curr)>
        android.database.Cursor ${curr.name?uncap_first}Cursor;
        </#if>
        <#if MetadataUtils.hasToManyRelations(curr)>
            <#list curr.ids as id>
        ${FieldsUtils.getJavaType(id)} ${curr.name?lower_case}${id.name?cap_first};
            </#list>
        </#if>

        switch (matchedUri) {

            case ${curr.name?upper_case}_ALL:
                <#if inherited && singleTabInheritance || curr.resource>
                if (Strings.isNullOrEmpty(selection)) {
                    selection =
                        <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if> + " = ?"<#if (curr.options.sync??)>
                                + " AND " + ${ContractUtils.getContractClass(curr.inheritance.superclass)}.ALIASED_COL_SYNC_DTAG + " = ?"</#if>;
                    selectionArgs = new String[]{${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER<#if (curr.options.sync??)>, "0"</#if>};
                } else {
                    selection += " AND "
                            +  <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if>
                            + " = ?"<#if (curr.options.sync??)>
                            + " AND " + ${ContractUtils.getContractClass(curr.inheritance.superclass)}.ALIASED_COL_SYNC_DTAG
                            + " = ?"</#if>;
                    selectionArgs = ObjectArrays.concat(selectionArgs,
                            ${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER);
                    <#if (curr.options.sync??)>
                    selectionArgs = ObjectArrays.concat(selectionArgs, "0");
                    </#if>
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
                result = this.queryById(<#list IdsUtils.getAllIdsNamesFromArray(curr_ids) as id>uri.getPathSegments().get(${id_index + 1})<#if id_has_next>,
                        </#if></#list>);
                break;

            <#list curr.relations as relation>
                <#if !relation.internal>
            case ${curr.name?upper_case}_${relation.name?upper_case}:
                <#if (relation.relation.type == "OneToOne"  && !relation.relation.mappedBy??)|| relation.relation.type == "ManyToOne">
                ${curr.name?uncap_first}Cursor = this.queryById(<#list IdsUtils.getAllIdsNamesFromArray(curr_ids) as id><#if (IdsUtils.getAllIdsNamesFromArray(curr_ids)?size > 0)>
                        </#if>uri.getPathSegments().get(${id_index + 1})<#if id_has_next>,</#if></#list>);

                if (${curr.name?uncap_first}Cursor.getCount() > 0) {
                    ${curr.name?uncap_first}Cursor.moveToFirst();
                    <#assign relNames = ContractUtils.getFieldsNames(relation) />
                    <#list entities[relation.relation.targetEntity].ids as id>
                    ${FieldsUtils.getJavaType(id)} ${relation.name}${id.name?cap_first} = ${curr.name?uncap_first}${AdapterUtils.getCursorGet(id)?cap_first}
                            ${curr.name?uncap_first}Cursor.getColumnIndex(
                                    ${relNames[id_index]}));
                    </#list>

                    ${relation.relation.targetEntity}SQLiteAdapter ${relation.relation.targetEntity?uncap_first}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
                    ${relation.relation.targetEntity?uncap_first}Adapter.open(this.getDb());
                    result = ${relation.relation.targetEntity?uncap_first}Adapter.query(<#list entities[relation.relation.targetEntity].ids as id>${relation.name}${id.name?cap_first}<#if id_has_next>,
                            </#if></#list>);
                }
                <#else>
                    <#list curr.ids as id>
                ${curr.name?lower_case}${id.name?cap_first} = <#if FieldsUtils.getJavaType(id)?lower_case == "int" || FieldsUtils.getJavaType(id)?lower_case == "integer">Integer.parseInt(</#if>uri.getPathSegments().get(${id_index + 1})<#if FieldsUtils.getJavaType(id)?lower_case == "int" || FieldsUtils.getJavaType(id)?lower_case == "integer">)</#if>;
                    </#list>
                    <#if relation.relation.type == "ManyToMany">
                ${relation.relation.joinTable}SQLiteAdapter ${relation.name}Adapter = new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
                ${relation.name}Adapter.open(this.getDb());
                        <#if (relation.relation.orders?? && relation.relation.orders?size > 0) >
                result = ${relation.name}Adapter.getBy${relation.name?cap_first}(<#list curr_ids as id>${curr.name?lower_case}${id.name?cap_first}<#if id_has_next>, </#if></#list>, ${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)}, selection, selectionArgs, "<#list relation.relation.orders?keys as orderKey>${orderKey} ${relation.relation.orders[orderKey]}<#if orderKey_has_next> AND </#if></#list>");
                        <#else>
                result = ${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(<#list curr_ids as id>${curr.name?lower_case}${id.name?cap_first}<#if id_has_next>, </#if></#list>, ${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)}, selection, selectionArgs, null);
                        </#if>
                    <#else>
                ${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapter = new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
                ${relation.name}Adapter.open(this.getDb());
                        <#if (relation.relation.orders?? && relation.relation.orders?size > 0) >
                result = ${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(<#list curr_ids as id>${curr.name?lower_case}${id.name?cap_first}<#if id_has_next>, </#if></#list>, ${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)}, selection, selectionArgs, "<#list relation.relation.orders?keys as orderKey>${orderKey} ${relation.relation.orders[orderKey]}<#if orderKey_has_next> AND </#if></#list>");
                        <#else>
                result = ${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(<#list curr_ids as id>${curr.name?lower_case}${id.name?cap_first}<#if id_has_next>, </#if></#list>, ${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)}, selection, selectionArgs, null);
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
        <#if (curr.options.sync??)>
            values.put(
                    <#if inherited && singleTabInheritance>${ContractUtils.getContractClass(curr.inheritance.superclass)}<#else>${ContractUtils.getContractClass(curr)}</#if>.COL_SYNC_UDATE,
                    new DateTime().toString(ISODateTimeFormat.dateTime()));</#if>
        <#if inherited && joinedInheritance>ContentValues ${curr.name?uncap_first}Values = DatabaseUtil.extractContentValues(values, ${ContractUtils.getContractCols(curr)});</#if>
        int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            <#if (hasIds)>
            case ${curr.name?upper_case}_ONE:
                selectionArgs = new String[<#if (inherited && !joinedInheritance) || curr.resource>${curr_ids?size + 1}<#else>${curr_ids?size}</#if>];
                <#if curr.resource>
                selection = ResourceContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                <#else>
                    <#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>
                selection <#if (id_index > 0)>+= " AND " +<#else>=</#if> ${id} + " = ?";
                selectionArgs[${id_index}] = uri.getPathSegments().get(${id_index + 1});
                    </#list>
                </#if>
                <#if (inherited && !joinedInheritance) || curr.resource>
                selection += " AND " +  <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if> + " = ?";
                selectionArgs[${IdsUtils.getAllIdsNamesFromArray(curr_ids)?size}] = ${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER;
                </#if>
                <#if inherited && joinedInheritance>
                Uri parentUri = Uri.withAppendedPath(${curr.inheritance.superclass.name?cap_first}ProviderAdapter.${curr.inheritance.superclass.name?upper_case}_URI,
                        <#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>uri.getPathSegments().get(${id_index + 1})<#if id_has_next> + "/" + </#if></#list>);
                result = this.ctx.getContentResolver().update(
                        parentUri,
                        values,
                        null,
                        null);
                result += this.adapter.update(
                        ${curr.name?uncap_first}Values,
                        selection,
                        selectionArgs);
                <#else>
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                </#if>
                break;
            </#if>
            case ${curr.name?upper_case}_ALL:
                <#if inherited>
                    <#if joinedInheritance && !curr.resource>
                // Query the ids of the changing fields.
                android.database.Cursor idsCursor = this.adapter.query(
                        new String[]{${ContractUtils.getContractCol(curr_ids[0], true)}},
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                // If there are ids
                if (idsCursor.getCount() > 0) {
                    // If there are values in this table
                    if (${curr.name?uncap_first}Values.size() > 0) {
                        CriteriaExpression currentCrit = this.cursorToIDSelection(
                                idsCursor,
                                <#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>${id}<#if id_has_next>
                                + " || ::dirtHack:: || "
                                + </#if></#list>);

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
                        CriteriaExpression parentCrit = this.cursorToIDSelection(
                                idsCursor,
                                <#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>${id}<#if id_has_next>
                                + " || ::dirtHack:: || "
                                + </#if></#list>);

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
                        <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if> + " = ?";
                    selectionArgs = new String[]{${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER};
                } else {
                    selection += " AND "
                            + <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if>
                            + " = ?";
                    selectionArgs = ObjectArrays.concat(selectionArgs,
                            ${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER);
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
     * Transform a cursor of ids into a criteria expression.
     *
     * @param cursor The cursor
     * @param key The key to map the ids to
     *
     * @return The expression
     */
    protected CriteriaExpression cursorToIDSelection(android.database.Cursor cursor, String key) {
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        Criterion inCrit = new Criterion();
        inCrit.setKey(key);
        inCrit.setType(Type.IN);
        ArrayValue inArray = new ArrayValue();
        cursor.moveToFirst();
        do {
            inArray.addValue(<#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>cursor.getString(cursor.getColumnIndex(${id}))<#if id_has_next>
                    + "::dirtyHack::" +
                    </#if></#list>);
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
    private android.database.Cursor queryById(<#list IdsUtils.getAllIdsNamesFromArray(curr_ids) as id>String ${id}<#if id_has_next>, </#if></#list>) {
        android.database.Cursor result = null;
        <#if curr.resource>
        String selection = ResourceContract.ALIASED_COL_ID
                + " = ?";
        <#else>
        String selection =<#list IdsUtils.getAllIdsColsFromArray(curr_ids, true) as id> ${id}
                        + " = ?"<#if id_has_next>
                        + " AND "
                        +</#if></#list>;
        </#if>
        <#if curr.options.sync??>
        selection += " AND " + <#if inherited && singleTabInheritance>${ContractUtils.getContractClass(curr.inheritance.superclass)}<#else>${ContractUtils.getContractClass(curr)}</#if>.ALIASED_COL_SYNC_DTAG + " = ?";</#if>
        <#if inherited && singleTabInheritance>
        selection += " AND " + <#if curr.resource>ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN<#else>${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn, true)}</#if> + " = ?";
        </#if>
        <#assign argsSize = curr_ids?size />
        <#if (curr.options.sync??)><#assign argsSize = argsSize + 1 /></#if>
        <#if (inherited && singleTabInheritance)><#assign argsSize = argsSize + 1 /></#if>
        String[] selectionArgs = new String[${argsSize}];
        <#list IdsUtils.getAllIdsNamesFromArray(curr_ids) as id>
        selectionArgs[${id_index}] = ${id};
        </#list>
        <#assign index = IdsUtils.getAllIdsNamesFromArray(curr_ids)?size />
        <#if (curr.options.sync??)>selectionArgs[${index}] = String.valueOf(0);<#assign index = index + 1 /></#if>
        <#if inherited && singleTabInheritance>selectionArgs[${index}] = ${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER;<#assign index = index + 1 /></#if>

        result = this.adapter.query(
                    ${ContractUtils.getContractCols(curr, true)},
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
    </#if>
}

