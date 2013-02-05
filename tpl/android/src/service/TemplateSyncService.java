package ${service_namespace};

import java.util.ArrayList;
import org.joda.time.DateTime;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; 

import com.tactfactory.mda.test.demact.${project_name?cap_first}Application;
import com.tactfactory.mda.test.demact.data.UserSQLiteAdapter;
import com.tactfactory.mda.test.demact.data.UserWebServiceClientAdapter;
import com.tactfactory.mda.test.demact.entity.User;
import com.tactfactory.mda.test.demact.entity.base.EntityBase;

public class ${project_name?cap_first}SyncService extends Thread{
	private final static String TAG = "SyncService";
	
	private ArrayList<EntityBase> base;
	private ArrayList<EntityBase> baseCopy;
	
	private Context context;
	
	public DateTime lastSyncDate;
	public DateTime startSync;
	
	private UserSQLiteAdapter client;
	
	ArrayList<EntityBase> deleted = new ArrayList<EntityBase>();
	ArrayList<EntityBase> inserted = new ArrayList<EntityBase>();
	ArrayList<EntityBase> updated = new ArrayList<EntityBase>();
	ArrayList<EntityBase> merged = new ArrayList<EntityBase>();
	
	public ${project_name?cap_first}SyncService(Context ctx){
		super();
		
		this.context = ctx;
		
		this.client = new UserSQLiteAdapter(ctx);
	}
	
	@Override
	public void run() {			
		this.sync(this.getLocalData());			
	}
	
	public ArrayList<EntityBase> getLocalData(){
		this.client.open();
		
		ArrayList<User> users = this.client.getAll();
		
		this.client.close();
		
		ArrayList<EntityBase> items = new ArrayList<EntityBase>();
		
		for(User u : users){
			items.add(u);
		}
		
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public void sync(ArrayList<EntityBase> base){
		this.base = base;
		this.baseCopy = (ArrayList<EntityBase>) this.base.clone();
		
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
				
				UserWebServiceClientAdapter server = new UserWebServiceClientAdapter(this.context);
				this.startSync = server.syncTime();
				
				server = new UserWebServiceClientAdapter(this.context);
				server.sync(${project_name?cap_first}Application.getLastSyncDate(), startSync, this.deleted, this.inserted, this.updated, this.merged);
				
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
		for (EntityBase entity : this.baseCopy) {
			if (entity.sync_uDate.isAfter(lastSyncDate) && entity.sync_dtag) {
				this.deleted.add(entity);
			}
		}
	}

	/**
	 * 
	 */
	private void checkInsertItem() {
		for (EntityBase entity : this.baseCopy) {
			if (!entity.sync_dtag && entity.serverId == null && 
					!deleted.contains(((User)entity).getId())) {
				this.inserted.add(entity);
			}
		}
	}

	/**
	 * 
	 */
	private void checkUpdateItem() {
		for (EntityBase entity : this.baseCopy) {
			if (entity.sync_uDate.isAfter(this.lastSyncDate) &&
					!entity.sync_dtag && 
					entity.serverId != null &&
					!deleted.contains(((User)entity).getId()) &&
					!inserted.contains(((User)entity).getId())) {					
				this.updated.add(entity);
			}
		}
	}
	
	/**
	 * 
	 */
	private void updateItem() {
		// Remove sync entities
		for (EntityBase deletedEntity : this.deleted) {
			this.client.delete(((User)deletedEntity).getId());
		}
					
		// Refresh insert sync entities (for id) 
		for (EntityBase insertedEntity : this.inserted) {
			this.client.update((User)insertedEntity);
		}
		
		// Refresh updated sync entities (for all reason)
		for (EntityBase updatedEntity : this.updated) {
			this.client.update((User)updatedEntity);
		}
		
		// Complex Merge delta entities from last sync...
		this.mergeItems();
	}
	
	/**
	 * 
	 */
	private void mergeItems() {		
		for (EntityBase entityBase : this.merged) {
			
			// Find mobile entity
			EntityBase oldEntity = this.client.getByServerID(entityBase.serverId);
			
			if (oldEntity != null) {
				// If entity exists locally
				
				if (entityBase.sync_dtag) {
					// Delete
					//**********************************************************
					this.client.remove(((User)oldEntity).getId());
					
					Log.d(TAG, "Delete !");
				} else {
					// Update
					//**********************************************************
					
					// Sync data
					((User)entityBase).setId(((User)oldEntity).getId());
					this.client.update((User)entityBase);
					
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
					this.client.insert((User)entityBase);
					
					Log.d(TAG, "Create !");
				}
			}
		}
	}
}
