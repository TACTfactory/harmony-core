package ${service_namespace};

import java.util.ArrayList;
import org.joda.time.DateTime;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; 

import com.tactfactory.mda.test.demact.${project_name?cap_first}Application;
import com.tactfactory.mda.test.demact.data.base.SyncSQLiteAdapterBase;
import com.tactfactory.mda.test.demact.data.UserSQLiteAdapter;
import com.tactfactory.mda.test.demact.data.UserWebServiceClientAdapter;
import com.tactfactory.mda.test.demact.entity.User;
import com.tactfactory.mda.test.demact.entity.base.EntityBase;

import com.tactfactory.mda.test.demact.data.base.SyncClientAdapterBase;

public class ${project_name?cap_first}SyncService<T extends EntityBase>{
	private final static String TAG = "SyncService";
	
	private ArrayList<T> base;
	private ArrayList<T> baseCopy;
	
	private Context context;
	
	public DateTime lastSyncDate;
	public DateTime startSync;
	
	private SyncSQLiteAdapterBase<T> client;
	private SyncClientAdapterBase<T> server;
	
	ArrayList<T> deleted = new ArrayList<T>();
	ArrayList<T> inserted = new ArrayList<T>();
	ArrayList<T> updated = new ArrayList<T>();
	ArrayList<T> merged = new ArrayList<T>();
	
	public ${project_name?cap_first}SyncService(Context ctx, SyncSQLiteAdapterBase<T> client, SyncClientAdapterBase<T> server){
		super();
		
		this.client = client;
		this.server = server;
	}
	
	public void run() {			
		this.sync(this.getLocalData());			
	}
	
	public ArrayList<T> getLocalData(){
		this.client.open();
		
		ArrayList<T> items = this.client.getAll();
		
		this.client.close();
				
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public void sync(ArrayList<T> base){
		this.base = base;
		this.baseCopy = (ArrayList<T>) this.base.clone();
		
		this.lastSyncDate = ${project_name?cap_first}Application.getLastSyncDate();
		
		this.deleted.clear();	// Clean all transaction actions
		this.inserted.clear();
		this.updated.clear();
		this.merged.clear();
		
		// Sync data
		//try {
			synchronized (this.base) {
				
				this.checkDeleteItem();
				this.checkInsertItem();
				this.checkUpdateItem();
				
				this.startSync = this.server.syncTime();
				
				this.server.sync(${project_name?cap_first}Application.getLastSyncDate(), startSync, this.deleted, this.inserted, this.updated, this.merged);
				
				SQLiteDatabase db = this.client.open();
				//db.beginTransaction();
				try {
					this.updateItem();
					
					${project_name?cap_first}Application.setLastSyncDate(startSync);
					//db.setTransactionSuccessful();
				} finally {
					//db.endTransaction();
					this.client.close();
				}
				
				this.startSync = null;
				
			}
		/*} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "ERRROOOORRRRR on sync !!!");
		}*/
		
		
	}
	
	/**
	 * 
	 */
	private void checkDeleteItem() {
		for (T entity : this.baseCopy) {
			if (entity.sync_uDate.isAfter(lastSyncDate) && entity.sync_dtag) {
				this.deleted.add(entity);
			}
		}
	}

	/**
	 * 
	 */
	private void checkInsertItem() {
		for (T entity : this.baseCopy) {
			if (!entity.sync_dtag && entity.serverId == null && 
					!deleted.contains(entity.getId())) {
				this.inserted.add(entity);
			}
		}
	}

	/**
	 * 
	 */
	private void checkUpdateItem() {
		for (T entity : this.baseCopy) {
			if (entity.sync_uDate.isAfter(this.lastSyncDate) &&
					!entity.sync_dtag && 
					entity.serverId != null &&
					!deleted.contains(entity.getId()) &&
					!inserted.contains(entity.getId())) {					
				this.updated.add(entity);
			}
		}
	}
	
	/**
	 * 
	 */
	private void updateItem() {
		// Remove sync entities
		for (T deletedEntity : this.deleted) {
			this.client.delete(deletedEntity);
		}
					
		// Refresh insert sync entities (for id) 
		for (T insertedEntity : this.inserted) {
			this.client.update(insertedEntity);
		}
		
		// Refresh updated sync entities (for all reason)
		for (T updatedEntity : this.updated) {
			this.client.update(updatedEntity);
		}
		
		// Complex Merge delta entities from last sync...
		this.mergeItems();
	}
	
	/**
	 * 
	 */
	private void mergeItems() {		
		for (T entityBase : this.merged) {
			
			// Find mobile entity
			T oldEntity = this.client.getByServerID(entityBase.getServerId());
			
			if (oldEntity != null) {
				// If entity exists locally
				
				if (entityBase.sync_dtag) {
					// Delete
					//**********************************************************
					this.client.delete(oldEntity);
					
					Log.d(TAG, "Delete !");
				} else {
					// Update
					//**********************************************************
					
					// Sync data
					entityBase.setId(oldEntity.getId());
					this.client.update(entityBase);
					
					Log.d(TAG, "Update !");
				}
			} else {
				// If entity doesn't exist locally
				
				if (entityBase.sync_dtag) {
					// Trash
					//**********************************************************
					
					// Nothing to do...
				} else {
					// Create
					//**********************************************************
					this.client.insert(entityBase);
					
					Log.d(TAG, "Create !");
				}
			}
		}
	}
}
