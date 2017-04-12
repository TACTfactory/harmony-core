<@header?interpret />
package ${project_namespace}.harmony.view;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Generic ViewHolder for {@link HarmonyCursorAdapter}.
 *
 * @param <T> Type of object used by {@link CursorAdapter}
 */
public abstract class HarmonyViewHolder<T> {
    /**
     * Android {@link Context}.
     */
    private Context context;
    
    /**
     * Recycled view.
     */
    private View convertView;
    
    /**
     * Constructor.
     *
     * @param context The context
     * @param parent Optional view to be the parent of the generated hierarchy
     * @param rowLayout Layout id of the row
     */
    public HarmonyViewHolder(Context context, ViewGroup parent, int rowLayout) {
        this.context = context;
        this.convertView = LayoutInflater.from(context).inflate(
                rowLayout, parent, false);
        
        this.convertView.setTag(this);
    }
    
    /**
     * @return The android {@link Context}
     */
    protected Context getContext() {
        return this.context;
    }
    
    /**
     * @return The current row {@link View}
     */
    public View getView() {
        return this.convertView;
    }

    /**
     * Populate row with a <T>.
     *
     * @param model <T> data
     */
    public abstract void populate(final T model);
}
