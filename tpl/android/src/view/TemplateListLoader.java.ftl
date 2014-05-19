<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.controller_namespace};


import android.net.Uri;
import android.support.v4.content.CursorLoader;

import ${project_namespace}.criterias.base.CriteriaExpression;

/**
 * ${curr.name} Loader.
 */
public class ${curr.name}ListLoader
				extends CursorLoader {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ${curr.name}ListLoader(
			final android.content.Context ctx) {
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
	public ${curr.name}ListLoader(
					android.content.Context ctx,
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
	 * @param expression The CriteriaExpression
	 * @param sortOrder The sort order
	 */
	public ${curr.name}ListLoader(
					android.content.Context ctx,
					Uri uri,
					String[] projection,
					CriteriaExpression expression,
					String sortOrder) {
		super(ctx,
				uri,
				projection,
				expression.toSQLiteSelection(),
				expression.toSQLiteSelectionArgs(),
				sortOrder);
	}
}
