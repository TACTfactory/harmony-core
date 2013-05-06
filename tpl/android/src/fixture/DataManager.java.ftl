package ${fixture_namespace};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.mda.meta.ClassMetadata;

import java.util.HashMap;
import java.util.Map;

import ${data_namespace}.base.SQLiteAdapterBase;
<#list entities?values as entity>
	<#if ((entity.fields?size>0) && !(entity.internal?? && entity.internal=='true'))>
import ${data_namespace}.${entity.name?cap_first}SQLiteAdapter;
import ${project_namespace}.entity.${entity.name?cap_first};
	</#if>
</#list>

/**
 * DataManager.
 */
public class DataManager {
	protected Map<String, SQLiteAdapterBase<?>> adapters = 
			new HashMap<String, SQLiteAdapterBase<?>>();
	protected boolean isSuccessfull = true;
	protected boolean isInInternalTransaction = false;
	protected SQLiteDatabase db;
	
	/**
	 * Constructor.
	 * @param ctx The context
	 * @param db The DB to work in
	 */
	public DataManager(final Context ctx, final SQLiteDatabase db) {
		this.db = db;
		<#list entities?values as entity>
			<#if ((entity.fields?size>0) && !(entity.internal?? && entity.internal=='true'))>
		this.adapters.put("${entity.name}", 
				new ${entity.name?cap_first}SQLiteAdapter(ctx));
		this.adapters.get("${entity.name}").open(this.db);
			</#if>
		</#list>
	}
	/**
     * Finds a object by its identifier.
     *
     * This is just a convenient shortcut for getRepository($className)
     * ->find($id).
     *
     * @param nameClass
     * @param id
     * @return object
     */
    public Object find(final String nameClass, final int id) {
    	Object ret = null;
    	this.beginTransaction();
    	
    	<#list entities?values as entity>
    		<#if ((entity.fields?size>0) && (entity.ids?size>0) && !(entity.internal?? && entity.internal=='true'))>
    	if (nameClass.equals("${entity.name}")) {
        	ret = ((${entity.name}SQLiteAdapter) 
        							   this.adapters.get(nameClass)).query(id);
    	}
    		</#if>
    	</#list>
    	
    	return ret;
    }

    /**
     * Tells the ObjectManager to make an instance managed and persistent.
     *
     * The object will be entered into the database as a result of the <br />
     * flush operation.
     *
     * NOTE: The persist operation always considers objects that are not<br />
     * yet known to this ObjectManager as NEW. Do not pass detached <br />
     * objects to the persist operation.
     *
     * @param object $object The instance to make managed and persistent.
     * @return Count of objects entered into the DB
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int persist(final Object object) {
    	int result;
    
    	this.beginTransaction();
    	try {
    		final SQLiteAdapterBase adapter = this.getRepository(object);
    			
    		result = (int) adapter.insert(object);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		this.isSuccessfull = false;
    		result = 0;
    	}    	
    	
    	return result;
    }

    /**
     * Removes an object instance.
     *
     * A removed object will be removed from the database as a result of <br />
     * the flush operation.
     *
     * @param object $object The object instance to remove.
     */
    public void remove(final Object object) {
    	this.beginTransaction();
    	try {
    	<#list entities?values as entity>
    		<#if ((entity.fields?size>0 && entity.ids?size>0) && !(entity.internal?? && entity.internal=='true'))>
    		if (object instanceof ${entity.name}) {
    			((${entity.name}SQLiteAdapter) 
    					this.adapters.get("${entity.name}"))
    						.remove(((${entity.name}) object).getId());
    		}
    		</#if>
    	</#list>
    	} catch (Exception ex) {
    		this.isSuccessfull = false;
    	}   
    }

//    /**
//     * Merges the state of a detached object into the persistence context
//     * of this ObjectManager and returns the managed copy of the object.
//     * The object passed to merge will not become associated/managed with 
//	   * this ObjectManager.
//     *
//     * @param object $object
//     */
//    public void merge(Object object) {
//    	
//    }
//
//    /**
//     * Clears the ObjectManager. All objects that are currently managed
//     * by this ObjectManager become detached.
//     *
//     * @param objectName $objectName if given, only objects of this type will
//     * get detached
//     */
//    public void clear(String objectName) {
//    	
//    }
//
//    /**
//     * Detaches an object from the ObjectManager, causing a managed object to
//     * become detached. Unflushed changes made to the object if any
//     * (including removal of the object), will not be synchronized to the 
//     * database.
//     * Objects which previously referenced the detached object will continue 
//     * to reference it.
//     *
//     * @param object $object The object to detach.
//     */
//    public void detach(Object object) {
//    	
//    }
//
//    /**
//     * Refreshes the persistent state of an object from the database,
//     * overriding any local changes that have not yet been persisted.
//     *
//     * @param object $object The object to refresh.
//     */
//    public void refresh(Object object) {
//    	
//    }

    /**
     * Flushes all changes to objects that have been queued up to now to <br />
     * the database. This effectively synchronizes the in-memory state of<br />
     * managed objects with the database.
     */
    public void flush() {
    	if (this.isInInternalTransaction) {
    		if (this.isSuccessfull) {
    			this.db.setTransactionSuccessful();
    		}
    		this.db.endTransaction();
    		this.isInInternalTransaction = false;
    	}
    }

    /**
     * Gets the repository for a class.
     *
     * @param className $className
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    public SQLiteAdapterBase<?> getRepository(final String className) {
    	return this.adapters.get(className);
    }
    
    
    /**
     * Gets the repository for a given object.
     *
     * @param o object
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
	private SQLiteAdapterBase<?> getRepository(final Object o) {
		final String className = o.getClass().getSimpleName();
	
		return this.getRepository(className);
	}

    /**
     * Returns the ClassMetadata descriptor for a class.
     *
     * The class name must be the fully-qualified class name without a <br />
     * leading backslash (as it is returned by get_class($obj)).
     *
     * @param className $className
     * @return \Doctrine\Common\Persistence\Mapping\ClassMetadata
     */
    public ClassMetadata getClassMetadata(final String className) {
    	return null;
    }

    /**
     * Check if the object is part of the current UnitOfWork and therefore
     * managed.
     *
     * @param object $object
     * @return bool
     */
    public boolean contains(final Object object) {
    	return false;
    }
    
    /**
     * Called before any transaction to open the DB
     */
    private void beginTransaction() {    	
    	// If we are not already in a transaction, begin it
    	if (!this.isInInternalTransaction) {
    		this.db.beginTransaction();
    		this.isSuccessfull = true;
    		this.isInInternalTransaction = true;
    	}
    }

}
