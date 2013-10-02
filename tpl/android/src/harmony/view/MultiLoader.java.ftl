package ${project_namespace}.harmony.view;

import java.util.LinkedHashMap;
import java.util.Map;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class MultiLoader<T> implements LoaderManager.LoaderCallbacks<Cursor> {
	//protected ArrayList<Uri> uriList = new ArrayList<Uri>();
	protected Map<Uri, UriLoadedCallback> uriMap = new LinkedHashMap<Uri, UriLoadedCallback>();
	protected T model;
	protected HarmonyFragment fragment;
	
	public MultiLoader(HarmonyFragment fragment, T model) {
		this.model = model;
		this.fragment = fragment;
	}


	public void addUri(Uri uri, UriLoadedCallback callback) {
		this.uriMap.put(uri, callback);
	}
	
	public void init() {
		for(int  i = 0; i < this.uriMap.size(); i++) {
			this.fragment.getLoaderManager().initLoader(i, null, this);
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return this.getLoader((Uri) uriMap.keySet().toArray()[arg0]);
	}
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
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
	public void onLoaderReset(Loader<Cursor> arg0) {
		Uri uri = ((CursorLoader) arg0).getUri();
		if (this.uriMap.containsKey(uri)) {
			this.uriMap.get(uri).onLoadComplete(null);
		}
	}
	
	public CursorLoader getLoader(Uri uri) {
		return new CursorLoader(
				this.fragment.getActivity(),
				uri,
				null,
				null,
				null,
				null);
	}
	
	public interface UriLoadedCallback {
		public void onLoadComplete(Cursor c);
	}
}
