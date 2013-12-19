<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />
package ${curr.controller_namespace};

import java.util.List;

import ${curr.namespace}.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if>
import android.widget.SectionIndexer;
import android.widget.TextView;

<#assign importDate=false />
<#list fields?values as field>
	<#if !field.internal && !field.hidden>
		<#if (!importDate && field.type?lower_case=="datetime")>
			<#assign importDate=true />
		</#if>
	</#if>
</#list>
<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;
</#if>
import ${curr.namespace}.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import ${curr.namespace}.entity.${curr.name};

/**
 * List adapter for ${curr.name} entity.
 */
public class ${curr.name}ListAdapter
		extends HeaderAdapter<${curr.name}>
		implements PinnedHeaderAdapter {
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ${curr.name}ListAdapter(Context ctx) {
		super(ctx);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ${curr.name}ListAdapter(Context context,
			int resource,
			int textViewResourceId,
			List<${curr.name}> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     *
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ${curr.name}ListAdapter(Context context,
			int resource,
			int textViewResourceId,
			${curr.name}[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param resource The resource
	 * @param textViewResourceId The resource id of the text view
	 */
	public ${curr.name}ListAdapter(Context context,
			int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ${curr.name}ListAdapter(Context context,
			int textViewResourceId,
			List<${curr.name}> objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 * @param objects The list of objects of this adapter
	 */
	public ${curr.name}ListAdapter(Context context,
			int textViewResourceId,
			${curr.name}[] objects) {
		super(context, textViewResourceId, objects);
	}

	/**
     * Constructor.
     * 
	 * @param context The context
	 * @param textViewResourceId The resource id of the text view
	 */
	public ${curr.name}ListAdapter(Context context,
			int textViewResourceId) {
		super(context, textViewResourceId);
	}

	/** Holder row. */
	private static class ViewHolder extends SelectionItemView {

		/**
		 * Constructor.
		 *
		 * @param context The context
		 */
		public ViewHolder(Context context) {
			this(context, null);
		}
		
		/**
		 * Constructor.
		 *
		 * @param context The context
		 * @param attrs The attribute set
		 */
		public ViewHolder(Context context, AttributeSet attrs) {
			super(context, attrs, R.layout.row_${curr.name?lower_case});
		}

		/** Populate row with a ${curr.name}.
		 *
		 * @param model ${curr.name} data
		 */
		public void populate(final ${curr.name} model) {
			View convertView = this.getInnerLayout();
			<#list fields?values as field>
				<#if (!field.internal && !field.hidden)>
					<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
						<#if (field.type=="boolean")>
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

	/** Section indexer for this entity's list. */
	public static class ${curr.name}SectionIndexer
					extends HeaderSectionIndexer<${curr.name}>
					implements SectionIndexer {

		/**
		 * Constructor.
		 * @param items The items of the indexer
		 */
		public ${curr.name}SectionIndexer(List<${curr.name}> items) {
			super(items);
		}
		
		@Override
		protected String getHeaderText(${curr.name} item) {
			return "Your entity's header name here";
		}
	}

	@Override
    protected View bindView(View itemView,
				int partition,
				${curr.name} item,
				int position) {
    	final ViewHolder view;
    	
    	if (itemView != null) {
    		view = (ViewHolder) itemView;
    	} else {
    		view = new ViewHolder(this.getContext());
		}

    	if (!((HarmonyFragmentActivity) this.getContext()).isDualMode()) {
    		view.setActivatedStateSupported(false);
		}
    	
    	view.populate(item);
        this.bindSectionHeaderAndDivider(view, position);
        
        return view;
    }

	@Override
	public int getPosition(${curr.name} item) {
		int result = -1;
		if (item != null) {
			for (int i = 0; i < this.getCount(); i++) {
				if (item.get${curr_ids[0].name?cap_first}() == this.getItem(i).get${curr_ids[0].name?cap_first}()) {
					result = i;
				}
			}
		}
		return result;
	}
}
