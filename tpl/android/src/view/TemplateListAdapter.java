package ${localnamespace};

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ${namespace}.R;
import ${namespace}.entity.${name};

<#list relations as relation>
	<#if !relation.internal && !relation.hidden>
import ${namespace}.entity.${relation.relation.targetEntity};
	</#if>
</#list>

public class ${name}ListAdapter extends ArrayAdapter<${name}> {
	private final LayoutInflater mInflater;

	public ${name}ListAdapter(Context context) {
		super(context, R.layout.row_${name?lower_case});

		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/** Set Array of ${name}
	 * 
	 * @param data the array
	 */
	public void setData(List<${name}> data) {
		this.clear();

		if (data != null) {
			for (${name} item : data) {
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
			convertView = this.mInflater.inflate(R.layout.row_${name?lower_case}, parent, false);

			holder = new ViewHolder();
			<#list fields as field>
				<#if !field.internal && !field.hidden>
			holder.${field.name} = (TextView) convertView.findViewById(R.id.row_${name?lower_case}_${field.name});
				</#if>
			</#list>
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		${name} item = getItem(position);
		if ( item != null && holder != null)
			holder.populate(item);

		return convertView;
	}

	/** Holder row */
	private static class ViewHolder {
		<#list fields as field>
			<#if !field.hidden && !field.internal>
		public TextView ${field.name};
			</#if>
		</#list>

		/** Populate row with a ${name}
		 * 
		 * @param item ${name} data
		 */
		public void populate(${name} item) {

			<#list fields as field>
				<#if !field.internal && !field.hidden>
					<#if (!field.relation??)>
						<#if (field.type="Date")>
			this.${field.name}.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(item.get${field.name?cap_first}())) );
						<#else>
			this.${field.name}.setText(String.valueOf(item.get${field.name?cap_first}()) );
						</#if>
					<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			this.${field.name}.setText(String.valueOf(item.get${field.name?cap_first}().getId()) );
					<#elseif (field.relation.type=="OneToMany" | field.relation.type=="ManyToMany")>
			String ${field.name}String = "";
			for(${field.relation.targetEntity?cap_first} ${field.name}Entity : item.get${field.name?cap_first}()){
				${field.name}String += ${field.name}Entity.getId()+",";
			}
			this.${field.name}.setText(String.valueOf(${field.name}String) );		
					</#if>
				</#if>
			</#list>

		}
	}
}
