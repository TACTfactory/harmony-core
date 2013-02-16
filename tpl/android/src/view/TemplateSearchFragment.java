<#assign curr = entities[current_entity] />
<#import "methods.tpl" as m />
package ${curr.controller_namespace};

import ${curr.namespace}.R;
import ${project_namespace}.HarmonyFragment;
import ${project_namespace}.HarmonyFragmentActivity;

import ${curr.namespace}.criterias.${curr.name?cap_first}Criterias ;
import ${curr.namespace}.criterias.base.Criteria.Type ;

import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.app.*;
import android.widget.*;


<#assign importDate=false />
<#assign importTime=false />
<#list curr.fields as field>
	<#if !field.internal && !field.hidden && field.options.search??>
		<#if field.type=="date" || field.type=="time" || field.type=="datetime">
			<#if ((field.type=="date" || field.type=="datetime") && !importDate)>
				<#assign importDate=true />
			</#if>
			<#if ((field.type=="time" || field.type=="datetime") && !importTime)>
				<#assign importTime=true />
			</#if>
		</#if>
	</#if>
</#list>
<#if (importDate || importTime)>
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

	<#if (importDate)>
import ${curr.namespace}.harmony.widget.CustomDatePickerDialog;
	</#if>
	<#if (importTime)>
import ${curr.namespace}.harmony.widget.CustomTimePickerDialog;
	</#if>
	
import ${curr.namespace}.harmony.util.DateUtils;
</#if>

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};
<#assign mustImportArrayList=false />
<#assign mustImportList=false />
<#assign import_array = [] />
<#list curr.relations as relation>
	<#if (!relation.internal && !relation.hidden && relation.options.search??)>
		<#assign mustImportList=true />
		<#if (!m.isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
import ${curr.namespace}.data.${relation.relation.targetEntity}SQLiteAdapter;
import ${curr.namespace}.entity.${relation.relation.targetEntity};
import ${curr.namespace}.criterias.${relation.relation.targetEntity}Criterias;
			<#if relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany">
				<#assign mustImportArrayList=true />
			</#if>
		</#if>
	</#if>
</#list>

<#if (import_array?size>0 || importDate || importTime)>
import android.app.*;
import android.content.DialogInterface;
</#if>

<#if (mustImportArrayList)>
import java.util.ArrayList;
import java.util.HashMap;
</#if>
<#if (mustImportList)>
import java.util.List;
</#if>
/** ${curr.name} search fragment
 * 
 * @see android.app.Fragment
 */
public class ${curr.name}SearchFragment extends HarmonyFragment implements OnClickListener {
	/* Model data */
	protected ${curr.name} model = new ${curr.name}();

	/* Fields View */
	<#list curr.fields as field>
		<#if !field.internal && !field.hidden && field.options.search??>
			<#if !field.relation??>
				<#if field.type=="boolean">
	protected CheckBox ${field.name}View;
				<#elseif field.type=="datetime" || field.type=="date" || field.type=="time">
					<#if field.type=="datetime" || field.type=="date">
	protected EditText ${field.name}DateView;
					</#if>
					<#if field.type=="datetime" || field.type=="time">
	protected EditText ${field.name}TimeView;
					</#if>
				<#else>
	protected EditText ${field.name}View;			
				</#if>
			<#else>
	protected Button ${field.name}Button;
	protected List<${field.relation.targetEntity}> ${field.name}List;
	protected Dialog ${field.name}Dialog;
				<#if field.relation.type=="OneToMany" || field.relation.type=="ManyToMany">
	protected boolean[] checked${field.name?cap_first};
				<#else>
	protected int selected${field.name?cap_first};
				</#if>
			</#if>
		</#if>
	</#list>

	protected Button searchButton;

	/** Initialize view of fields 
	 * 
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		<#foreach field in curr.fields>
			<#if !field.internal && !field.hidden && field.options.search??>
				<#if !field.relation??>
					<#if field.type=="boolean">
		this.${field.name}View = (CheckBox) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
					<#elseif field.type=="datetime" || field.type=="date" || field.type=="time">
						<#if field.type == "date" || field.type == "datetime">
						
		this.${field.name}DateView = (EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_date);			
		this.${field.name}DateView.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
		        DateTime dt = new DateTime();

		        String ${field.name}Date = ${curr.name}SearchFragment.this.${field.name}DateView.getText().toString();
				if (!TextUtils.isEmpty(${field.name}Date)) {
					String strInputDate = ${field.name}Date;
					dt = DateUtils.formatStringToDate(strInputDate);
				}
				
			    CustomDatePickerDialog ${field.name}Dpd = new CustomDatePickerDialog(getActivity(), dt, R.string.${curr.name?lower_case}_${field.name?lower_case}_date_title);
			    ${field.name}Dpd.setPositiveButton(getActivity().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DatePicker dp = ((CustomDatePickerDialog) dialog).getDatePicker();
						DateTime date = new DateTime(dp.getYear(), dp.getMonth() + 1, dp.getDayOfMonth(), 0, 0);
						${curr.name}SearchFragment.this.${field.name}DateView.setText(DateUtils.formatDateToString(date));
					}
				});

			    ${field.name}Dpd.show();
			}
		});			
						</#if>
						<#if field.type == "time" || field.type == "datetime">
						
		this.${field.name}TimeView = (EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_time);
		this.${field.name}TimeView.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				DateTime dt = new DateTime(); 
				
				String ${field.name}Time = ${curr.name}SearchFragment.this.${field.name}TimeView.getText().toString();
				if (!TextUtils.isEmpty(${field.name}Time)) {
					String strInputTime = ${field.name}Time;
					dt = DateUtils.formatStringToTime(strInputTime);
				}
				
			    CustomTimePickerDialog ${field.name}Tpd = new CustomTimePickerDialog(getActivity(), dt, 
			    		android.text.format.DateFormat.is24HourFormat(getActivity()), R.string.${curr.name?lower_case}_${field.name?lower_case}_time_title);
			    ${field.name}Tpd.setPositiveButton(getActivity().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						TimePicker tp = ((CustomTimePickerDialog) dialog).getTimePicker();
						
						DateTime date = new DateTime(0);
						date = new DateTime(date.getYear(), date.getDayOfMonth(), date.getDayOfMonth(), 
								tp.getCurrentHour(), tp.getCurrentMinute());

						${curr.name}SearchFragment.this.${field.name}TimeView.setText(DateUtils.formatTimeToString(date));
					}
				});

			    ${field.name}Tpd.show();
			}
		});
						</#if>
					<#else>
		this.${field.name}View = (EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
					</#if>
				<#else>
		this.${field.name}Button = (Button) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_button);
		this.${field.name}Button.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				onClick${field.name?cap_first}Button(v);
			}
		});
				</#if>
			</#if>
		</#foreach>
		
		this.searchButton = (Button) view.findViewById(R.id.${curr.name?lower_case}_btn_search);
		this.searchButton.setOnClickListener(this);
	}
	
	<#list curr.relations as relation>
		<#if !relation.internal && !relation.hidden && relation.options.search??>
	/** Initialize dialog
	 * 
	 */		
<#if relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany">
	protected void init${relation.name?cap_first}Dialog(List<${relation.relation.targetEntity}> list){
		String[] listAdapter = new String[list.size()];
		boolean[] checks = new boolean[list.size()];
		this.checked${relation.name?cap_first} = new boolean[list.size()];
		int i=0;
		for(${relation.relation.targetEntity} item : list){
			listAdapter[i] = String.valueOf(item.getId());
			checks[i] = false;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setTitle("Select ${relation.name}")
				.setMultiChoiceItems(listAdapter, checks, new DialogInterface.OnMultiChoiceClickListener(){
					public void onClick(DialogInterface dialog, int which, boolean isChecked){
						${curr.name}SearchFragment.this.checked${relation.name?cap_first}[which] = isChecked;
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	String value = "";
		            	for(int i=0;i<checked${relation.name?cap_first}.length;i++){
		            		if(checked${relation.name?cap_first}[i]){
		            			value+=(i+1)+" ";
		            		}		            			
		            	}
		            	
	            		value = value.trim().replace(" ", ", ");
		            	${curr.name?cap_first}SearchFragment.this.${relation.name?uncap_first}Button.setText(value);
		            }
		        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}SearchFragment.this.onCancel${relation.name?cap_first}();
		            }
		        });
		
		${relation.name}Dialog = builder.create();
	}
			<#else>
	protected void init${relation.name?cap_first}Dialog(List<${relation.relation.targetEntity}> list){
		final String[] listAdapter = new String[list.size()];
		int i=0;
		for(${relation.relation.targetEntity} item : list){
			listAdapter[i] = String.valueOf(item.getId());
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setTitle("Select ${relation.name}")
				.setSingleChoiceItems(listAdapter, 0, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						//${curr.name}CreateFragment.this.selected${relation.name?cap_first} = Integer.parseInt(listAdapter[id]);
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}SearchFragment.this.${relation.name?uncap_first}Button.setText(listAdapter[((AlertDialog)dialog).getListView().getCheckedItemPosition()]);
		            }
		        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}SearchFragment.this.onCancel${relation.name?cap_first}();
		            }
		        });
		
		${relation.name}Dialog = builder.create();
	} 
	 		</#if>
	
	protected void onCancel${relation.name?cap_first}(){
		//TODO : Don't change the list
	}
	
	protected void onClick${relation.name?cap_first}Button(View v){
		${relation.name}Dialog.show();
	}
		</#if>
	</#list>	
	
	/** Load data from model to fields view */
	public void loadData() {
		<#list curr.relations as field>						
			<#if field.options.search??>
		${field.relation.targetEntity}SQLiteAdapter ${field.name}Adapter = new ${field.relation.targetEntity}SQLiteAdapter(getActivity());
		${field.name}Adapter.open();
		this.${field.name}List = ${field.name}Adapter.getAll();
		${field.name}Adapter.close();
		init${field.name?cap_first}Dialog(${field.name}List);
			</#if>
		
		</#list>
	}


	/** Sets up the UI.
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_${curr.name?lower_case}_search, container, false);

		this.loadData();

		this.initializeComponent(view);
		return view;
	}

	/** 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle b = new Bundle();
		
		
		
		b.putSerializable(${curr.name?cap_first}Criterias._PARCELABLE, this.dataToCriteria());
		intent.putExtras(b);
		
		this.getActivity().setResult(HarmonyFragmentActivity.RESULT_OK, intent);
		this.getActivity().finish();
	}
	
	public ${curr.name?cap_first}Criterias dataToCriteria(){
		
		// Fill criterias map
		${curr.name?cap_first}Criterias criterias = new ${curr.name?cap_first}Criterias(${curr.name?cap_first}Criterias.GroupType.AND);
		<#list curr.fields as field>
			<#if !field.internal && !field.hidden && field.options.search??>
				<#if field.type=="date" || field.type=="time" || field.type=="datetime">
					<#assign date=false /><#assign time=false />
					<#if field.type=="date" || field.type=="datetime"><#assign date = true /></#if>
					<#if field.type=="time" || field.type=="datetime"><#assign time = true /></#if>
		if(<#if date>!TextUtils.isEmpty(this.${field.name}DateView.getText().toString())</#if><#if (date && time)> && </#if><#if time>!TextUtils.isEmpty(this.${field.name}TimeView.getText().toString())</#if>){
				
			<#if (date && time)>
			DateTime dt = DateUtils.formatStringToDateTime(
					this.${field.name?uncap_first}DateView.getEditableText().toString(),
					this.${field.name?uncap_first}TimeView.getEditableText().toString());
			<#elseif (date)>
			DateTime dt = DateUtils.formatStringToDate(
					this.${field.name?uncap_first}DateView.getEditableText().toString());
			<#elseif (time)>
			DateTime dt = DateUtils.formatStringToTime(
					this.${field.name?uncap_first}TimeView.getEditableText().toString());
			</#if>
			<#if field.is_locale>
			criterias.add("${field.columnName}", dt.toLocalDateTime().toString(), Type.EQUALS);
			<#else>
			criterias.add("${field.columnName}", dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()), Type.EQUALS);
			</#if>
		}
				<#else>
					<#if field.relation??>
		if(!TextUtils.isEmpty(this.${field.name}Button.getText().toString())){
						<#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
			criterias.add("${field.columnName}", this.${field.name}Button.getText().toString(), Type.EQUALS);
						<#else>
			String[] values = this.${field.name}Button.getText().toString().split(",");
			${field.relation.targetEntity}Criterias ${field.name}Crits = new ${field.relation.targetEntity}Criterias(${curr.name?cap_first}Criterias.GroupType.OR);
			for(String value : values){
				${field.name}Crits.add("id", value.trim(), Type.EQUALS);
			}
			criterias.add("id", "(SELECT ${field.relation.mappedBy} FROM ${field.relation.targetEntity} WHERE "+${field.name}Crits.toSQLiteString()+")", Type.IN);				
						</#if>
		}		
					<#else>
						<#if field.type=="boolean">
						
						<#elseif field.type=="string" || field.type=="text" || field.type=="phone" || field.type=="login" || field.type=="password">
		if(!TextUtils.isEmpty(this.${field.name}View.getText().toString())){
			criterias.add("${field.columnName}", "%"+this.${field.name}View.getText().toString()+"%", Type.LIKE);
		}					
						<#else>
		if(!TextUtils.isEmpty(this.${field.name}View.getText().toString())){
			criterias.add("${field.columnName}", this.${field.name}View.getText().toString(), Type.EQUALS);
		}					
						</#if>
					</#if>
				</#if>
		
			</#if>
		</#list>
		return criterias;
	}
}
