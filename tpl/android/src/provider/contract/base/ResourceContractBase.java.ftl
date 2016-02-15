<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>
<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
package ${project_namespace}.provider.contract.base;

import android.content.ContentValues;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import ${entity_namespace}.base.EntityResourceBase;

import ${project_namespace}.provider.contract.ResourceContract;
import ${project_namespace}.harmony.util.DateUtils;

/** ${project_name?cap_first} contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ResourceContractBase {

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ResourceContract.TABLE_NAME + "." + COL_ID;


    /** path. */
    public static final String COL_PATH =
            "path";
    /** Alias. */
    public static final String ALIASED_COL_PATH =
            ResourceContract.TABLE_NAME + "." + COL_PATH;

<#if sync>
    /** serverId. */
    public static final String COL_SERVERID =
            "serverId";
    /** Alias. */
    public static final String ALIASED_COL_SERVERID =
            ResourceContract.TABLE_NAME + "." + COL_SERVERID;

    /** sync_dtag. */
    public static final String COL_SYNC_DTAG =
            "sync_dTag";
    /** Alias. */
    public static final String ALIASED_COL_SYNC_DTAG =
            ResourceContract.TABLE_NAME + "." + COL_SYNC_DTAG;

    /** sync_uDate. */
    public static final String COL_SYNC_UDATE =
            "sync_uDate";
    /** Alias. */
    public static final String ALIASED_COL_SYNC_UDATE =
            ResourceContract.TABLE_NAME + "." + COL_SYNC_UDATE;

    /** hash. */
    public static final String COL_UUID =
            "hash";
    /** Alias. */
    public static final String ALIASED_COL_UUID =
            ResourceContract.TABLE_NAME + "." + COL_UUID;
</#if>

    /** Discriminator column. */
    public static final String COL_DISCRIMINATORCOLUMN =
            "inheritance_type";
    /** Alias. */
    public static final String ALIASED_COL_DISCRIMINATORCOLUMN =
            ResourceContract.TABLE_NAME + "." + COL_DISCRIMINATORCOLUMN;


    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Resource";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Resource";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            ResourceContract.COL_ID,
            ResourceContract.COL_PATH<#if sync>,
            ResourceContract.COL_SERVERID,
            ResourceContract.COL_SYNC_DTAG,
            ResourceContract.COL_SYNC_UDATE,
            ResourceContract.COL_UUID</#if>
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            ResourceContract.ALIASED_COL_ID,
            ResourceContract.ALIASED_COL_PATH<#if sync>,
            ResourceContract.ALIASED_COL_SERVERID,
            ResourceContract.ALIASED_COL_SYNC_DTAG,
            ResourceContract.ALIASED_COL_SYNC_UDATE,
            ResourceContract.ALIASED_COL_UUID</#if>
    };


    /**
     * Converts a Resource into a content values.
     *
     * @param item The Resource to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final EntityResourceBase item) {
        final ContentValues result = new ContentValues();

            result.put(ResourceContract.COL_ID,
                String.valueOf(item.getId()));

            if (item.getPath() != null) {
                result.put(ResourceContract.COL_PATH,
                    item.getPath());
            }

            <#if sync>if (item.getServerId() != null) {
                result.put(ResourceContract.COL_SERVERID,
                    String.valueOf(item.getServerId()));
            } else {
                result.put(ResourceContract.COL_SERVERID, (String) null);
            }

            result.put(ResourceContract.COL_SYNC_DTAG,
                item.isSync_dTag() ? 1 : 0);

            if (item.getSync_uDate() != null) {
                result.put(ResourceContract.COL_SYNC_UDATE,
                    item.getSync_uDate().toString(ISODateTimeFormat.dateTime()));
            } else {
                result.put(ResourceContract.COL_SYNC_UDATE, (String) null);
            }

            if (item.getUuid() != null) {
                result.put(ResourceContract.COL_UUID,
                    item.getUuid());
            } else {
                result.put(ResourceContract.COL_UUID, (String) null);
            }</#if>

        return result;
    }

    /**
     * Converts a Cursor into a Resource.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Resource
     */
    public static EntityResourceBase cursorToItem(final android.database.Cursor cursor) {
        EntityResourceBase result = new EntityResourceBase();
        ResourceContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Resource entity.
     * @param cursor Cursor object
     * @param result Resource entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final EntityResourceBase result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndexOrThrow(ResourceContract.COL_ID);
            result.setId(
                    cursor.getInt(index));

            index = cursor.getColumnIndexOrThrow(ResourceContract.COL_PATH);
            result.setPath(
                    cursor.getString(index));

            <#if sync>index = cursor.getColumnIndexOrThrow(ResourceContract.COL_SERVERID);
            if (!cursor.isNull(index)) {
                result.setServerId(
                    cursor.getInt(index));
            }

            index = cursor.getColumnIndexOrThrow(ResourceContract.COL_SYNC_DTAG);
            result.setSync_dTag(
                    cursor.getInt(index) == 1);

            index = cursor.getColumnIndexOrThrow(ResourceContract.COL_SYNC_UDATE);
            if (!cursor.isNull(index)) {
                final DateTime dtSync_uDate =
                    DateUtils.formatISOStringToDateTime(
                            cursor.getString(index));
                if (dtSync_uDate != null) {
                        result.setSync_uDate(
                            dtSync_uDate);
                } else {
                    result.setSync_uDate(new DateTime());
                }
            }

            index = cursor.getColumnIndexOrThrow(ResourceContract.COL_UUID);
            if (!cursor.isNull(index)) {
                result.setUuid(
                    cursor.getString(index));
            }</#if>

        }
    }

    /**
     * Convert Cursor of database to Array of Resource entity.
     * @param cursor Cursor object
     * @return Array of Resource entity
     */
    public static ArrayList<EntityResourceBase> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<EntityResourceBase> result = new ArrayList<EntityResourceBase>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            EntityResourceBase item;
            do {
                item = ResourceContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }

    /**
     * Converts a Cursor into a Resource.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Resource
     */
    public static EntityResourceBase cursorToItemLight(final android.database.Cursor cursor) {
        EntityResourceBase result = new EntityResourceBase();
        ResourceContract.cursorToItemLight(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Resource entity.
     * @param cursor Cursor object
     * @param result Resource entity
     */
    public static void cursorToItemLight(final android.database.Cursor cursor, final EntityResourceBase result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ResourceContract.COL_PATH);
            result.setPath(cursor.getString(index));
        }
    }
}
