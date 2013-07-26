<@header?interpret />
package ${project_namespace}.provider.utils.base;

import java.util.ArrayList;

import android.content.Context;

/**
 * Generic Proxy class for the provider calls.
 * @param <T>	 The entity type
 */ 
public abstract class ProviderUtilsBase<T> {
	/** 
	 * Send the item of type T to the content provider of the application.
	 * @param ctx The context
	 * @param item The item
	 * @return 0 if everything went well. -1 otherwise.
	 */
	public abstract int insert(final Context ctx, final T item);

	/** 
	 * Delete the item of type T from the content provider of the application.
	 * @param ctx The context
	 * @param item The item
	 * @return The number of delete items
	 */
	public abstract int delete(final Context ctx, final T item);

	/** 
	 * Query an object of type T by the provider.
	 * @param ctx The context
	 * @param id The item id
	 * @return The T entity
	 */
	public abstract T query(final Context ctx, final int id);

	/** 
	 * Query all the objects of type T by the provider.
	 * @param ctx The context
	 * @return The list of T entities
	 */
	public abstract ArrayList<T> queryAll(final Context ctx);

	/** 
	 * Update a T item through the provider.
	 * @param ctx The context
	 * @param item The item to update
	 * @return The number of updated items
	 */
	public abstract int update(final Context ctx, final T item);
}

