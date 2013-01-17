package ${fixture_namespace};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.mda.meta.ClassMetadata;
import java.util.HashMap;

import ${data_namespace}.${project_name?cap_first}SQLiteAdapterBase;
<#list entities?values as entity>
	<#if (entity.fields?size>0)>
import ${data_namespace}.${entity.name?cap_first}SQLiteAdapter;
import ${project_namespace}.entity.${entity.name?cap_first};
	</#if>
</#list>

public class DataManager {
	protected HashMap<String, ${project_name?cap_first}SQLiteAdapterBase> adapters = new HashMap<String, ${project_name?cap_first}SQLiteAdapterBase>();
	protected boolean isSuccessfull = true;
	protected boolean isInInternalTransaction = false;
	protected SQLiteDatabase db;
	
	public DataManager(Context ctx, SQLiteDatabase db){
		this.db = db;
		<#list entities?values as entity>
			<#if (entity.fields?size>0)>
		this.adapters.put("${entity.name}", new ${entity.name?cap_first}SQLiteAdapter(ctx));
		this.adapters.get("${entity.name}").open(this.db);		
			</#if>
		</#list>
	}
	/**
     * Finds a object by its identifier.
     *
     * This is just a convenient shortcut for getRepository($className)->find($id).
     *
     * @param nameClass
     * @param id
     * @return object
     */
    public Object find(String nameClass, int id) {
    	Object ret = null;
    	this.beginTransaction();
    	
    	<#list entities?values as entity>
    		<#if (entity.fields?size>0) && (entity.ids?size>0)>
    	if(nameClass.equals("${entity.name}")){
        	ret = ((${entity.name}SQLiteAdapter)this.adapters.get(nameClass)).query(id);
    	}
    		</#if>
    	</#list>
    	
    	return ret;
    }

    /**
     * Tells the ObjectManager to make an instance managed and persistent.
     *
     * The object will be entered into the database as a result of the flush operation.
     *
     * NOTE: The persist operation always considers objects that are not yet known to
     * this ObjectManager as NEW. Do not pass detached objects to the persist operation.
     *
     * @param object $object The instance to make managed and persistent.
     */
    public void persist(Object object) {
    	this.beginTransaction();
    	try{
    	<#list entities?values as entity>
    		<#if (entity.fields?size>0)>
    		if(object instanceof ${entity.name}){
    		
    			((${entity.name}SQLiteAdapter)this.adapters.get("${entity.name}")).insert((${entity.name})object);
    		}
    		</#if>
    	</#list>
    	} catch (Exception ex) {
    		this.isSuccessfull = false;
    	}    	
    }

    /**
     * Removes an object instance.
     *
     * A removed object will be removed from the database as a result of the flush operation.
     *
     * @param object $object The object instance to remove.
     */
    public void remove(Object object) {
    	this.beginTransaction();
    	try{
    	<#list entities?values as entity>
    		<#if (entity.fields?size>0 && entity.ids?size>0)>
    		if(object instanceof ${entity.name}){
    			((${entity.name}SQLiteAdapter)this.adapters.get("${entity.name}")).remove(((${entity.name})object).getId());
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
//     * The object passed to merge will not become associated/managed with this ObjectManager.
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
//     * @param objectName $objectName if given, only objects of this type will get detached
//     */
//    public void clear(String objectName) {
//    	
//    }
//
//    /**
//     * Detaches an object from the ObjectManager, causing a managed object to
//     * become detached. Unflushed changes made to the object if any
//     * (including removal of the object), will not be synchronized to the database.
//     * Objects which previously referenced the detached object will continue to
//     * reference it.
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
     * Flushes all changes to objects that have been queued up to now to the database.
     * This effectively synchronizes the in-memory state of managed objects with the
     * database.
     */
    public void flush() {
    	if(this.isInInternalTransaction){
    		if (this.isSuccessfull)
    			this.db.setTransactionSuccessful();
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
    public ${project_name?cap_first}SQLiteAdapterBase getRepository(String className) {
    	return this.adapters.get(className);
    }

    /**
     * Returns the ClassMetadata descriptor for a class.
     *
     * The class name must be the fully-qualified class name without a leading backslash
     * (as it is returned by get_class($obj)).
     *
     * @param className $className
     * @return \Doctrine\Common\Persistence\Mapping\ClassMetadata
     */
    public ClassMetadata getClassMetadata(String className) {
    	return null;
    }

    /**
     * Check if the object is part of the current UnitOfWork and therefore
     * managed.
     *
     * @param object $object
     * @return bool
     */
    public boolean contains(Object object) {
    	return false;
    }
    
    private void beginTransaction(){    	
    	// If we are not already in a transaction, begin it
    	if(!this.isInInternalTransaction){
    		this.db.beginTransaction();
    		this.isSuccessfull = true;
    		this.isInInternalTransaction = true;
    	}
    }

}
