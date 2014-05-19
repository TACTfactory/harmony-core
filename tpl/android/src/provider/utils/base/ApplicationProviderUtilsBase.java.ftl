<@header?interpret />
package ${project_namespace}.provider.utils.base;

import java.util.ArrayList;
import android.net.Uri;



import ${project_namespace}.criterias.base.CriteriaExpression;


/**
 * Generic Proxy class for the provider calls.
 * @param <T>     The entity type
 */
public abstract class ProviderUtilsBase<T> {
    /**
     * android.content.Context.
     */
    private android.content.Context context;


    /**
     * Constructor.
     * @param context android.content.Context
     */
    public ProviderUtilsBase(android.content.Context context) {
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
     * Query the DB to get the entities filtered by the given expression.
     * @param expression The expression defining the selection and selection args
     * @return ArrayList<T>
     */
    public abstract ArrayList<T> query(CriteriaExpression expression);

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
    public android.content.Context getContext() {
        return this.context;
    }
}

