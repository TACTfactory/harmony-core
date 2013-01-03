<#import "methods.tpl" as m>
package ${localnamespace};

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import ${namespace}.R;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.List;
import java.util.ArrayList;
import ${namespace}.entity.${name};
<#list relations as relation>
	<#if !relation.internal && !relation.hidden>
import ${namespace}.entity.${relation.relation.targetEntity};
	</#if>
</#list>

/** ${name} show fragment
 * 
 * see android.app.Fragment
 */
public class ${name}ShowFragment extends Fragment {
	/* Model data */
	protected ${name} model;
	
	/* Fields View */
<#list fields as field>
	<#if !field.internal && !field.hidden>
		<#if field.type=="boolean">
	protected CheckBox ${field.name}View;
		<#else>
	protected TextView ${field.name}View;			
		</#if>
	</#if>
</#list>
    
    /** Initialize view of fields 
     * 
     * param view The layout inflating
     */
    protected void initializeComponent(View view) {
	<#foreach field in fields>
		<#if !field.internal && !field.hidden>
			<#if field.type=="boolean">
		this.${field.name}View = (CheckBox) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case});
		this.${field.name}View.setEnabled(false);
			<#else>
		this.${field.name}View = (TextView) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case});			
			</#if>
		</#if>
	</#foreach>
    }
    
    /** Load data from model to fields view */
    public void loadData() {
    	<#foreach field in fields>
			<#if !field.internal && !field.hidden>
				<#if !field.relation??>
		    			<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float")>
		if(this.model.get${field.name?cap_first}()!=null)
			${m.setLoader(field)}
					<#else>
		${m.setLoader(field)}
					</#if>
				<#elseif field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		this.${field.name}View.setText(String.valueOf(this.model.get${field.name?cap_first}().getId())); 
				<#else>
		String ${field.name}Value = "";
		for(${field.relation.targetEntity} item : this.model.get${field.name?cap_first}()){
			${field.name}Value+=item.getId()+",";
		}
		this.${field.name}View.setText(${field.name}Value);
				</#if>
			</#if>
	</#foreach>
    }
    
    /** Sets up the UI.
	 * 
	 * see android.support.v4.app.Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
    	// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_${name?lower_case}_show, container, false);

        Intent intent =  getActivity().getIntent();
        this.model = (${name?cap_first}) intent.getSerializableExtra("${name}");
        		
        this.initializeComponent(view);
        this.loadData();
        
        return view;
    }
}
