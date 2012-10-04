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

public class ${name}ListAdapter extends ArrayAdapter<${name}> {
    private final LayoutInflater mInflater;
    private static Context ctx;

    public ${name}ListAdapter(Context context) {
        super(context, R.layout.row_${name?lower_case});
        
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ctx = context;
        
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
			//holder.title = (TextView) convertView.findViewById(R.id.stootsListTitle);
			<#list fields as field>
			holder.${field.name} = (TextView) convertView.findViewById(R.id.row_${name}_${field.name});
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
		public TextView ${field.name};
		</#list>
		
		/** Populate row with a ${name}
		 * 
		 * @param item ${name} data
		 */
		public void populate(${name} item) {
			
			<#list fields as field>
			<#if (field.type="Date")>
			this.${field.name}.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(item.get${field.name?cap_first}())) );
			<#else>
			this.${field.name}.setText(String.valueOf(item.get${field.name?cap_first}()) );
			</#if>
			</#list>
		}
	}
}