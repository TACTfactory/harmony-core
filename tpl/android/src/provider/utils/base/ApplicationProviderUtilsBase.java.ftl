<@header?interpret />
package ${project_namespace}.provider.utils.base;

import java.util.ArrayList;
import android.net.Uri;

import android.content.Context;

import ${project_namespace}.criterias.base.CriteriasBase;


/**
 * Generic Proxy class for the provider calls.
 * @param <T>	 The entity type
 */
public abstract class ProviderUtilsBase<T> {
	/**
	 * Context.
	 */
	private Context context;


	/**
	 * Constructor.
	 * @param context Context
	 */
	public ProviderUtilsBase(Context context) {
		this.context = context;
	}

	/**
	 * Send the item of type T to the content provider of the application.
	 * @param item The item
	 * @return The Uri
	 */
	public abstract Uri insert(final T item);

	/**
	 * Delete the item of type T from the content provider of the application.
	 * @param item The item
	 * @return The number of delete items
	 */
	public abstract int delete(final T item);

	/**
	 * Query an object of type T by the provider.
	 * @param T the item with its ids set
	 * @return The T entity
	 */
	public abstract T query(final T item);

	/**
	 * Query all the objects of type T by the provider.
	 * @return The list of T entities
	 */
	public abstract ArrayList<T> queryAll();

	/**
	 * Query the DB to get the entities filtered by criteria.
	 * @param criteria The criteria defining the selection and selection args
	 * @return ArrayList<T>
	 */
	public abstract ArrayList<T> query(CriteriasBase<T> criteria);

	/**
	 * Update a T item through the provider.
	 * @param item The item to update
	 * @return The number of updated items
	 */
	public abstract int update(final T item);

	/**
	 * Returns the context.
	 * @return the context
	 */
	public Context getContext() {
		return this.context;
	}
}

