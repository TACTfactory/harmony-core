package ${local_namespace};

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ${project_name?cap_first}Provider extends ${project_name?cap_first}ProviderBase {
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = 0;
		switch (uriMatcher.match(uri)) {
			default:
				result = super.delete(uri, selection, selectionArgs);
				break;
		}
		return result;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri result = null;

		switch (uriMatcher.match(uri)) {
			default:
				result = super.insert(uri, values);
				break;
		}

		return result;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		Cursor result = null;

		switch (uriMatcher.match(uri)) {
			default:
				result = super.query(uri,
					projection, 
					selection, 
					selectionArgs, 
					sortOrder);
				break;
		}

		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int result = 0;

		switch (uriMatcher.match(uri)) {
			default:
				result = super.update(uri, values, selection, selectionArgs);
				break;
		}
		return result;
	}
}
