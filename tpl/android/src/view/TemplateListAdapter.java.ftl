<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />
package ${curr.controller_namespace};


import ${curr.namespace}.R;
<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if>
import android.database.Cursor;
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
import ${curr.namespace}.harmony.view.HarmonyViewHolder;
${ImportUtils.importRelatedContracts(curr, true, true)}

/**
 * List adapter for ${curr.name} entity.
 */
public class ${curr.name}ListAdapter extends HarmonyCursorAdapter<${curr.name}> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ${curr.name}ListAdapter(android.content.Context context) {
        super(context);
    }
    
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
    protected HarmonyViewHolder<${curr.name}> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<${curr.name}> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_user);
        }

        /**
         * Populate row with a {@link ${curr.name}}.
         *
         * @param model {@link ${curr.name}} data
         */
        public void populate(final ${curr.name} model) {
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
                        <#if (field.harmony_type?lower_case == "boolean")>
            CheckBox ${field.name}View = (CheckBox) this.getView().findViewById(
                    R.id.row_${curr.name?lower_case}_${field.name?lower_case});
            ${field.name}View.setEnabled(false);
            
                        <#else>
            TextView ${field.name}View = (TextView) this.getView().findViewById(
                    R.id.row_${curr.name?lower_case}_${field.name?lower_case});
                    
                        </#if>
                    </#if>
                </#if>
            </#list>
<#list fields?values as field>${AdapterUtils.populateViewHolderFieldAdapter(field, 3)}</#list>
        }
    }
}
