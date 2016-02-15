<#include utilityPath + "all_imports.ftl" />
<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>

<@header?interpret />
package ${data_namespace}.base;

import java.util.ArrayList;

import android.content.ContentValues;

import ${project_namespace}.${project_name?cap_first}Application;
import ${data_namespace}.SQLiteAdapter;

<#list entities?values as entity>
    <#if entity.resource>
import ${data_namespace}.${entity.name?cap_first}SQLiteAdapter;
    </#if>
</#list>
import ${entity_namespace}.base.EntityResourceBase;
import ${project_namespace}.provider.contract.ResourceContract;


/** Resource adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit Resource Adapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ResourceSQLiteAdapterBase
                        extends <#if sync>SyncSQLiteAdapterBase<EntityResourceBase><#else>SQLiteAdapter<EntityResourceBase></#if> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ResourceDBAdapter";


    /**
     * Get the table name used in DB for your Resource entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ResourceContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Resource entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ResourceContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Resource entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ResourceContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
         + ResourceContract.TABLE_NAME    + " ("
         + ResourceContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ResourceContract.COL_PATH    + " VARCHAR NOT NULL,"<#if sync>
         + ResourceContract.COL_SERVERID    + " integer,"
         + ResourceContract.COL_SYNC_DTAG    + " boolean NOT NULL,"
         + ResourceContract.COL_SYNC_UDATE    + " datetime,"
         + ResourceContract.COL_UUID    + " VARCHAR,"</#if>
         + ResourceContract.COL_DISCRIMINATORCOLUMN + "  VARCHAR"
        <#list entities?values as entity>
            <#if ((entity.resource) && (entity.fields?size>1))>
         + "," + ${entity.name?cap_first}SQLiteAdapter.getSchemaColumns()
            </#if>
        </#list>
         + ");";
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ResourceSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Resource entity to Content Values for database.
     * @param item Resource entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final EntityResourceBase item) {
        return ResourceContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Resource entity.
     * @param cursor android.database.Cursor object
     * @return Resource entity
     */
    public EntityResourceBase cursorToItem(final android.database.Cursor cursor) {
        return ResourceContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Resource entity.
     * @param cursor android.database.Cursor object
     * @param result Resource entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final EntityResourceBase result) {
        ResourceContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Resource by id in database.
     *
     * @param id Identify of Resource
     * @return Resource entity
     */
    public EntityResourceBase getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final EntityResourceBase result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All Resources entities.
     *
     * @return List of Resource entities
     */
    public ArrayList<EntityResourceBase> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<EntityResourceBase> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Resource entity into database.
     *
     * @param item The Resource entity to persist
     * @return Id of the Resource entity
     */
    public long insert(final EntityResourceBase item) {
        if (${project_name?cap_first}Application.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ResourceContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ResourceContract.itemToContentValues(item);
        values.remove(ResourceContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ResourceContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Resource entity into database whether.
     * it already exists or not.
     *
     * @param item The Resource entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final EntityResourceBase item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a Resource entity into database.
     *
     * @param item The Resource entity to persist
     * @return count of updated entities
     */
    public int update(final EntityResourceBase item) {
        if (${project_name?cap_first}Application.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ResourceContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ResourceContract.itemToContentValues(item);
        final String whereClause =
                 ResourceContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Resource entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (${project_name?cap_first}Application.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ResourceContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ResourceContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param Resource The entity to delete
     * @return count of updated entities
     */
    public int delete(final EntityResourceBase Resource) {
        return this.remove(Resource.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Resource corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (${project_name?cap_first}Application.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ResourceContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ResourceContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Resource entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ResourceContract.ALIASED_COL_ID + " = ?";


        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ResourceContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

<#if sync>    @Override
    public void completeEntityRelationsServerId(EntityResourceBase item) {
    }

    @Override
    public ArrayList<EntityResourceBase> getAllForSync() {
        final ArrayList<EntityResourceBase> result;

        final android.database.Cursor cursor = this.query(this.getCols(),
                ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN + " IS NULL",
                new String[]{},
                null,
                null,
                null);

        result = this.cursorToItems(cursor);

        return result;
    }</#if>

}

