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
import ${curr.namespace}.harmony.view.HarmonyCursorAdapter;
${ImportUtils.importRelatedContracts(curr, true, true)}

/**
 * List adapter for ${curr.name} entity.
 */
public class ${curr.name}ListAdapter extends HarmonyCursorAdapter<${curr.name}> {
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ${curr.name}ListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected ${curr.name} cursorToItem(Cursor cursor) {
        return ${curr.name}Contract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ${InheritanceUtils.getClassWithId(curr).name}Contract.COL_ID;
    }

    @Override
    protected ViewHolder getViewHolder(
            android.content.Context context, ViewGroup group) {
        return new ${curr.name}ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ${curr.name}ViewHolder extends ViewHolder {
    
        /**
         * Constructor.
         *
         * @param context The context
         */
        public ${curr.name}ViewHolder(
                android.content.Context context, ViewGroup parent) {
            super(context, parent);
        }
        
        @Override
        protected int getContentLayout() {
            return R.layout.row_${curr.name?lower_case};
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
                (CheckBox) this.getView().findViewById(
                        R.id.row_${curr.name?lower_case}_${field.name?lower_case});
            ${field.name}View.setEnabled(false);
                        <#else>
            TextView ${field.name}View =
                (TextView) this.getView().findViewById(
                        R.id.row_${curr.name?lower_case}_${field.name?lower_case});
                        </#if>
                    </#if>
                </#if>
            </#list>

<#list fields?values as field>${AdapterUtils.populateViewHolderFieldAdapter(field, 3)}</#list>
        }
    }
}
