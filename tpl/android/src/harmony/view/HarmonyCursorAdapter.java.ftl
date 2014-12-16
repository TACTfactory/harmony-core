<@header?interpret />
package ${project_namespace}.harmony.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * List adapter for <T> entity.
 */
public abstract class HarmonyCursorAdapter<T> extends CursorAdapter {
    
    /** Android {@link Context}. */
    private Context context;
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public HarmonyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }
    
    /**
     * @return {@link Context}
     */
    protected android.content.Context getContext() {
        return this.context;
    }
    
    /** Convert cursor to item. Must be call Contract class. */
    protected abstract T cursorToItem(Cursor cursor);
    
    /** Get the column name (not aliased) from Contract class. */
    protected abstract String getColId();
    
    /** Return a ViewHolder instance. */
    protected abstract ViewHolder getViewHolder(
            Context context, ViewGroup group);
    
    @SuppressWarnings("unchecked")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.populate(this.cursorToItem(cursor));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup group) {
        final ViewHolder viewHolder = this.getViewHolder(context, group);
        
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
    
    /** Holder row. */
    protected abstract class ViewHolder {
        private View convertView;
        
        /**
         * Constructor.
         *
         * @param context The context
         */
        public ViewHolder(Context context, ViewGroup parent) {
            this.convertView = LayoutInflater.from(context).inflate(
                    this.getContentLayout(), parent, false);
            
            this.convertView.setTag(this);
        }
        
        protected abstract int getContentLayout();
        
        /**
         * @return Holder view
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
}
