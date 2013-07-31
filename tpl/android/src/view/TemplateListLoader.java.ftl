<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.controller_namespace};

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import ${project_namespace}.criterias.${curr.name?cap_first}Criterias;

/**
 * ${curr.name} Loader.
 */
public class ${curr.name}ListLoader 
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param crit ${curr.name?cap_first}Criterias
	 */
	public ${curr.name}ListLoader(final Context ctx, 
					final ${curr.name?cap_first}Criterias crit) {
		super(ctx);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param selection The selection
	 * @param selectionArgs The selection Args
	 * @param sortOrder The sort order
	 */
	public ${curr.name}ListLoader(Context ctx,
					Uri uri,
					String[] projection,
					String selection,
					String[] selectionArgs,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				selection,
				selectionArgs,
				sortOrder);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param uri The URI associated with this loader
	 * @param projection The projection to use
	 * @param criterias ${curr.name?cap_first}Criterias
	 * @param sortOrder The sort order
	 */
	public ${curr.name}ListLoader(Context ctx,
					Uri uri,
					String[] projection,
					${curr.name?cap_first}Criterias criterias,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				criterias.toSQLiteSelection(),
				criterias.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
