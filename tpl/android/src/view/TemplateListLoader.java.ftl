<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.controller_namespace};

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import ${project_namespace}.criterias.${curr.name?cap_first}Criterias;
import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;

/**
 * ${curr.name} Loader.
 */
public class ${curr.name}ListLoader 
				extends CursorLoader {
	/** ${curr.name?cap_first}Criterias. */
	private ${curr.name?cap_first}Criterias criterias;

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit ${curr.name?cap_first}Criterias
	 */
	public ${curr.name}ListLoader(final Context ctx, 
					final ${curr.name?cap_first}Criterias crit) {
		super(ctx);
		this.criterias = crit;
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param criterias ${curr.name?cap_first}Criterias
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param selection The selection
	 * @param selectionArgs The selection Args
	 * @param sortOrder The sort order
	 */
	public ${curr.name}ListLoader(Context ctx,
					${curr.name?cap_first}Criterias criterias,
					Uri uri,
					String[] projection,
					String selection,
					String[] selectionArgs,
					String sortOrder) {
		super(ctx, uri, projection, selection, selectionArgs, sortOrder);
		this.criterias = criterias;
	}

	
	@Override
	public Cursor loadInBackground() {
		ContentResolver provider = 
				this.getContext().getContentResolver();
		ArrayList<String> array = new ArrayList<String>();
		String selection = null;
		String[] selectionArgs = null;
		if (this.criterias != null 
				&& !this.criterias.isEmpty()) {
			selection = this.criterias.toSQLiteSelection();
			this.criterias.toSQLiteSelectionArgs(array);
			selectionArgs = array.toArray(new String[array.size()]);
		}
		
		return provider.query(${curr.name}ProviderAdapter.${curr.name?upper_case}_URI,
				null,
				selection,
				selectionArgs,
				null);
	}
}
