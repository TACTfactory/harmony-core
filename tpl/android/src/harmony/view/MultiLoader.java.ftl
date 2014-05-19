<@header?interpret />
package ${project_namespace}.harmony.view;

import java.util.LinkedHashMap;
import java.util.Map;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

/**
 * Multiple load class for entities.
 * This helps you do a lot of provider requests simultaneously
 * with only one manager.
 *
 */
public class MultiLoader implements LoaderManager.LoaderCallbacks<android.database.Cursor> {
	/** Uri map. */
	protected Map<Uri, UriLoadedCallback> uriMap =
			new LinkedHashMap<Uri, UriLoadedCallback>();
	/** Associated fragment. */
	protected HarmonyFragment fragment;
	
	/**
	 * Constructor.
	 *
	 * @param fragment The fragment.
	 */
	public MultiLoader(HarmonyFragment fragment) {
		this.fragment = fragment;
	}

	/**
	 * Add an uri to load.
	 *
	 * @param uri The uri to load
	 * @param callback The associated callback
	 */
	public void addUri(Uri uri, UriLoadedCallback callback) {
		this.uriMap.put(uri, callback);
	}
	
	/**
	 * Init the loaders.
	 */
	public void init() {
		for (int  i = 0; i < this.uriMap.size(); i++) {
			this.fragment.getLoaderManager().restartLoader(i, null, this);
		}
	}
	
	@Override
	public Loader<android.database.Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return this.getLoader((Uri) uriMap.keySet().toArray()[arg0]);
	}

	@Override
	public void onLoadFinished(Loader<android.database.Cursor> arg0, android.database.Cursor arg1) {
		Uri uri = ((CursorLoader) arg0).getUri();
		if (this.uriMap.containsKey(uri)) {
			if (arg1 != null) {
		
				arg1.setNotificationUri(
						((CursorLoader) arg0).getContext().getContentResolver(),
						uri);
	
			}
			this.uriMap.get(uri).onLoadComplete(arg1);
		}
	}
	
	@Override
	public void onLoaderReset(Loader<android.database.Cursor> arg0) {
		Uri uri = ((CursorLoader) arg0).getUri();
		if (this.uriMap.containsKey(uri)) {
			this.uriMap.get(uri).onLoaderReset();
		}
	}
	
	/**
	 * Gets a new loader for the given uri.
	 *
	 * @return the new cursor loader
	 */
	public CursorLoader getLoader(Uri uri) {
		return new CursorLoader(
				this.fragment.getActivity(),
				uri,
				null,
				null,
				null,
				null);
	}
	
	/** Interface for loading callback. */
	public interface UriLoadedCallback {
		/** On load complete. 
		 * @param c The loaded cursor
		 */
		void onLoadComplete(android.database.Cursor c);
		/** On load reset. */
		void onLoaderReset();
	}
}
