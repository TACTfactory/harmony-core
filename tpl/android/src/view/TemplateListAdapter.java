<#assign curr = entities[current_entity] />
<#import "methods.tpl" as m />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

<#assign importDate=false />
<#list curr.fields as field>
	<#if (!importDate && (field.type=="date" || field.type=="time"))>
		<#assign importDate=true />
	</#if>
</#list>
<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;
</#if>
import ${curr.namespace}.entity.${curr.name};

public class ${curr.name}ListAdapter extends ArrayAdapter<${curr.name}> {
	private final LayoutInflater mInflater;

	public ${curr.name}ListAdapter(Context context) {
		super(context, R.layout.row_${curr.name?lower_case});

		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/** Set Array of ${curr.name}
	 * 
	 * @param data the array
	 */
	public void setData(List<${curr.name}> data) {
		this.clear();

		if (data != null) {
			for (${curr.name} item : data) {
				this.add(item);
			}
		}
	}

	/** (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override 
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = this.mInflater.inflate(R.layout.row_${curr.name?lower_case}, parent, false);

			holder = new ViewHolder();
			<#list curr.fields as field>
				<#if (!field.internal && !field.hidden)>
					<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>  
						<#if (field.type=="boolean")>
			holder.${field.name}View = (CheckBox) convertView.findViewById(R.id.row_${curr.name?lower_case}_${field.name?lower_case});
			holder.${field.name}View.setEnabled(false);
						<#else>
			holder.${field.name}View = (TextView) convertView.findViewById(R.id.row_${curr.name?lower_case}_${field.name?lower_case});			
						</#if>
					</#if>
				</#if>
			</#list>
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		${curr.name} item = getItem(position);
		if ( item != null && holder != null)
			holder.populate(item);

		return convertView;
	}

	/** Holder row */
	private static class ViewHolder {
		<#list curr.fields as field>
			<#if (!field.hidden && !field.internal)>
				<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>  
					<#if (field.type=="boolean")>
		protected CheckBox ${field.name}View;
					<#else>
		protected TextView ${field.name}View;			
					</#if>
				</#if>
			</#if>
		</#list>

		/** Populate row with a ${curr.name}
		 * 
		 * @param item ${curr.name} data
		 */
		public void populate(${curr.name} model) {
			<#list curr.fields as field>
				<#if (!field.internal && !field.hidden)>
					<#if (!field.relation??)>
						<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float")>
			if(model.get${field.name?cap_first}()!=null)
				${m.setAdapterLoader(field)}
						<#else>
			${m.setAdapterLoader(field)}
						</#if>
					<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			this.${field.name}View.setText(String.valueOf(model.get${field.name?cap_first}().getId()) );	
					</#if>
				</#if>
			</#list>
		}
	}
}
