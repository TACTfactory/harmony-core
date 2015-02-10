<@header?interpret />
package ${project_namespace}.harmony.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * List adapter for <T> entity.
 */
public abstract class HarmonyCursorAdapter<T> extends CursorAdapter {
    /**
     * Constructor.
     * @param ctx context
     */
    public HarmonyCursorAdapter(Context ctx) {
        super(ctx, null, 0);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public HarmonyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    
    /** Convert cursor to item. Must be call Contract class. */
    protected abstract T cursorToItem(Cursor cursor);
    
    /** Get the column name (not aliased) from Contract class. */
    protected abstract String getColId();
    
    /**
     * Get a new {@link HarmonyViewHolder} for the item <T>.
     * @param context
     * @param cursor
     * @param group
     * @return
     */
    protected abstract HarmonyViewHolder<T> getNewViewHolder(
            Context context, Cursor cursor, ViewGroup group);
    
    @SuppressWarnings("unchecked")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        HarmonyViewHolder<T> viewHolder = (HarmonyViewHolder<T>) view.getTag();
        viewHolder.populate(this.cursorToItem(cursor));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup group) {
        final HarmonyViewHolder<T> viewHolder = this.getNewViewHolder(
                context, cursor, group);
        
        viewHolder.populate(this.cursorToItem(cursor));
        
        return viewHolder.getView();
    }
    
    @Override
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == this.mCursor) {
            return null;
        }
        
        Cursor oldCursor = this.mCursor;
        
        if (oldCursor != null) {
            if (this.mChangeObserver != null) {
                oldCursor.unregisterContentObserver(this.mChangeObserver);
            }
            
            if (this.mDataSetObserver != null) {
                oldCursor.unregisterDataSetObserver(this.mDataSetObserver);
            }
        }
        
        mCursor = newCursor;
        
        if (newCursor != null) {
            if (this.mChangeObserver != null) {
                newCursor.registerContentObserver(this.mChangeObserver);
            }
            
            if (this.mDataSetObserver != null) {
                newCursor.registerDataSetObserver(this.mDataSetObserver);
            }
            
            this.mRowIDColumn = newCursor.getColumnIndexOrThrow(
                    this.getColId());
            
            this.mDataValid = true;
            // notify the observers about the new cursor
            this.notifyDataSetChanged();
        } else {
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            // notify the observers about the lack of a data set
            this.notifyDataSetInvalidated();
        }
        
        return oldCursor;
    }
}
