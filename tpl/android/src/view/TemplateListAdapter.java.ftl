<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />
package ${curr.controller_namespace};


import ${curr.namespace}.R;
<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if>
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

<#assign importDate=false />
<#list fields?values as field>
    <#if !field.internal && !field.hidden>
        <#if (!importDate && FieldsUtils.getJavaType(field)?lower_case=="datetime")>
            <#assign importDate=true />
        </#if>
    </#if>
</#list>
<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;
</#if>

import ${curr.namespace}.entity.${curr.name};
import ${curr.namespace}.provider.contract.${curr.name?cap_first}Contract;

/**
 * List adapter for ${curr.name} entity.
 */
public class ${curr.name}ListAdapter extends CursorAdapter {
    
    /** Android {@link Context}. */
    private android.content.Context context;
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ${curr.name}ListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }
    
    /**
     * @return {@link Context}
     */
    protected android.content.Context getContext() {
        return this.context;
    }
    
    @Override
    public void bindView(View view,
            android.content.Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.populate(${curr.name}Contract.cursorToItem(cursor));
    }

    @Override
    public View newView(android.content.Context context,
            Cursor cursor, ViewGroup group) {
        final ViewHolder viewHolder = new ViewHolder(this.getContext(), group);
        
        viewHolder.populate(${curr.name}Contract.cursorToItem(cursor));
        
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
                    ${curr.name}Contract.COL_ID);
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
    private class ViewHolder {
        private View convertView;
        
        /**
         * Constructor.
         *
         * @param context The context
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            this.convertView = LayoutInflater.from(context).inflate(
                    R.layout.row_${curr.name?lower_case}, parent, false);
            
            this.convertView.setTag(this);
        }
        
        /**
         * @return Holder view
         */
        public View getView() {
            return this.convertView;
        }

        /**
         * Populate row with a Server.
         *
         * @param model Server data
         */
        public void populate(final ${curr.name} model) {
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
                        <#if (field.harmony_type?lower_case == "boolean")>
            CheckBox ${field.name}View =
                (CheckBox) convertView.findViewById(
                        R.id.row_${curr.name?lower_case}_${field.name?lower_case});
            ${field.name}View.setEnabled(false);
                        <#else>
            TextView ${field.name}View =
                (TextView) convertView.findViewById(
                        R.id.row_${curr.name?lower_case}_${field.name?lower_case});
                        </#if>
                    </#if>
                </#if>
            </#list>

<#list fields?values as field>${AdapterUtils.populateViewHolderFieldAdapter(field, 3)}</#list>
        }
    }
}
