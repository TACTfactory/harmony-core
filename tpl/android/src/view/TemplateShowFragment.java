package ${localnamespace};

import java.text.SimpleDateFormat;

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
	<#if !relation.internal>
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
		<#if !field.relation??>
    protected ${field.customShowType} ${field.name}View;
    	<#else>
    protected ${field.customShowType} ${field.name}View;
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
		this.${field.name}View = (${field.customShowType}) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case});
		</#if>
	</#foreach>
    }
    
    /** Load data from model to fields view */
    public void loadData() {
    	<#foreach field in fields>
			<#if !field.internal && !field.hidden>
				<#if !field.relation??>
		    		<#if (field.customEditType == "EditText") >
    					<#if (field.type == "String")>
		this.${field.name}View.setText(this.model.get${field.name?cap_first}()); 
						</#if>
						<#if (field.type == "Date")>
		this.${field.name}View.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.model.get${field.name?cap_first}())); 
						</#if>
						<#if (field.type == "int")>
		this.${field.name}View.setText(String.valueOf(this.model.get${field.name?cap_first}())); 
						</#if>
					<#elseif (field.customEditType == "CheckBox") >
		this.${field.name}View.setSelected(this.model.${field.name?uncap_first}()); 
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
