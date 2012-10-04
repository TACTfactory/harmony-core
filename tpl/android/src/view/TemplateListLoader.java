package ${localnamespace};

import java.util.ArrayList;
import java.util.List;

import com.tactfactory.mda.test.demact.data.${name}Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import ${namespace}.R;
import ${namespace}.entity.${name};

/**
 * ${name} Loader
 */
public class ${name}ListLoader extends AsyncTaskLoader<List<${name}>> {
    private List<${name}> m${name}s;
    private Context context;
    
    public ${name}ListLoader(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * This is where the bulk of our work is done.  This function is
     * called in a background thread and should generate a new set of
     * data to be published by the loader.
     */
    @Override 
    public List<${name}> loadInBackground() {
    	List<${name}> result = new ArrayList<${name}>();
    	
    	// TODO Query of data

    	${name}Adapter adapter = new ${name}Adapter(context);
		SQLiteDatabase db = adapter.open();
    	try {
    		db.beginTransaction();
    		result = adapter.getAll();

    		db.setTransactionSuccessful();
    		/*StootieWebService service = new StootieWebServiceImpl(getContext());
    		if (mStoot.getAsker() == null)
    			service.getStoot(mStoot);
    		
    		service = new StootieWebServiceImpl(getContext());
    		result = service.getMessages(mStoot);
    		
    		for (int i = 0 ; i < result.size(); i++) {
    			service = new StootieWebServiceImpl(getContext());
    			User userFinded = result.get(i).getSender(); // User find by object
    			
    			User userToFind = null; // User finded in cache
    			for (User user : users) {
					if (user.getId() == userFinded.getId()){
						userToFind = user; // Found in cache
						break;			   // Optim
					}
				}
    			
    			// Full load network !!
    			if (userToFind == null) {
    				userToFind = service.getUser(userFinded);
    				users.add(userToFind);
    			}
    			
    			// Set to model
    			result.get(i).setSender(userToFind);
    		}*/
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			adapter.close();
		}
    	
        // Sort the list.
        
        // Done!
        return result;
    }

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override 
    public void deliverResult(List<${name}> items) {
        if (this.isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (items != null) {
                this.onReleaseResources(items);
            }
        }
        List<${name}> oldItems = items;
        this.m${name}s = items;

        if (this.isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(items);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldItems != null) {
            this.onReleaseResources(oldItems);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override 
    protected void onStartLoading() {
        if (this.m${name}s != null) {
            // If we currently have a result available, deliver it
            // immediately.
            this.deliverResult(this.m${name}s);
        }

        if (this.takeContentChanged() || this.m${name}s == null) {
        	// If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            this.forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override 
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        this.cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override 
    public void onCanceled(List<${name}> items) {
        super.onCanceled(items);
        
        this.onReleaseResources(items);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override 
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        this.onStopLoading();

        // At this point we can release the resources associated with '${name}'
        // if needed.
        if (this.m${name}s != null) {
            this.onReleaseResources(this.m${name}s);
            this.m${name}s = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<${name}> messages) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }
}