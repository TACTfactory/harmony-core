<#assign curr = entities[current_entity] />
<#import "methods.tpl" as m />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.HarmonyFragmentActivity;
import ${project_namespace}.HarmonyFragment;

import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.app.*;
import android.widget.*;


<#assign importDate=false />
<#assign importTime=false />
<#list curr.fields as field>
	<#if !field.internal && !field.hidden>
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
	<#if (!relation.internal && !relation.hidden)>
		<#assign mustImportList=true />
		<#if (!m.isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
import ${curr.namespace}.data.${relation.relation.targetEntity}SQLiteAdapter;
import ${curr.namespace}.entity.${relation.relation.targetEntity};
			<#if relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany">
				<#assign mustImportArrayList=true />
			</#if>
		</#if>
	</#if>
</#list>


<#if (mustImportArrayList)>
import java.util.ArrayList;
</#if>
<#if (mustImportList)>
import java.util.List;
</#if>
/** ${curr.name} create fragment
 * 
 * @see android.app.Fragment
 */
public class ${curr.name}CreateFragment extends HarmonyFragment implements OnClickListener {
	/* Model data */
	protected ${curr.name} model = new ${curr.name}();

	/* Fields View */
	<#list curr.fields as field>
		<#if !field.internal && !field.hidden>
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

	protected Button saveButton;

	/** Initialize view of fields 
	 * 
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		<#foreach field in curr.fields>
			<#if !field.internal && !field.hidden>
				<#if !field.relation??>
					<#if field.type=="boolean">
		this.${field.name}View = (CheckBox) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
					<#elseif field.type=="datetime" || field.type=="date" || field.type=="time">
						<#if field.type == "date" || field.type == "datetime">
						
		this.${field.name}DateView = (EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_date);			
		this.${field.name}DateView.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
		        DateTime dt = new DateTime();

		        String ${field.name}Date = ${curr.name}CreateFragment.this.${field.name}DateView.getText().toString();
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
						${curr.name}CreateFragment.this.${field.name}DateView.setText(DateUtils.formatDateToString(date));
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
				
				String ${field.name}Time = ${curr.name}CreateFragment.this.${field.name}TimeView.getText().toString();
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

						${curr.name}CreateFragment.this.${field.name}TimeView.setText(DateUtils.formatTimeToString(date));
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
		
		this.saveButton = (Button) view.findViewById(R.id.${curr.name?lower_case}_btn_save);
		this.saveButton.setOnClickListener(this);
	}
	
	<#list curr.relations as relation>
		<#if !relation.internal && !relation.hidden>
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
						${curr.name}CreateFragment.this.checked${relation.name?cap_first}[which] = isChecked;
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	//${curr.name}CreateFragment.this.onOk${relation.name?cap_first}();
		            }
		        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}CreateFragment.this.onCancel${relation.name?cap_first}();
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
		            	${curr.name}CreateFragment.this.selected${relation.name?cap_first} = Integer.parseInt(listAdapter[((AlertDialog)dialog).getListView().getCheckedItemPosition()]);
		            }
		        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}CreateFragment.this.onCancel${relation.name?cap_first}();
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
		<#foreach field in curr.fields>						
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float")>
		if(this.model.get${field.name?cap_first}()!=null){
					<#if field.type=="datetime" || field.type=="date" || field.type=="time">
						<#if field.type=="datetime" || field.type=="date">
			this.${field.name}DateView.setText(DateUtils.formatDateToString(this.model.get${field.name?cap_first}()));
						</#if>
						<#if field.type=="datetime" || field.type=="time">
			this.${field.name}TimeView.setText(DateUtils.formatTimeToString(this.model.get${field.name?cap_first}()));
						</#if>
					<#else>
			${m.setLoader(field)}			
					</#if>
		}
				<#else>
		${m.setLoader(field)}
				</#if>
			<#else>
		${field.relation.targetEntity}SQLiteAdapter ${field.name}Adapter = new ${field.relation.targetEntity}SQLiteAdapter(getActivity());
		${field.name}Adapter.open();
		this.${field.name}List = ${field.name}Adapter.getAll();
		${field.name}Adapter.close();
		init${field.name?cap_first}Dialog(${field.name}List);
			</#if>
		</#if>
		
		</#foreach>
	}
	
	/** Save data from fields view to model */
	public void saveData() {
		<#foreach field in curr.fields>
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if field.type!="boolean">
					<#if field.type=="date" || field.type=="datetime">
		if(!TextUtils.isEmpty(this.${field.name}DateView.getEditableText()))
					<#elseif field.type=="time" || field.type=="datetime">
		if(!TextUtils.isEmpty(this.${field.name}TimeView.getEditableText()))
					<#else>
		if(!TextUtils.isEmpty(this.${field.name}View.getEditableText()))
					</#if>
			${m.setSaver(field)}
				<#else>
		${m.setSaver(field)}
				</#if>
			<#elseif field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		${field.relation.targetEntity} tmp${field.name?cap_first} = new ${field.relation.targetEntity?cap_first}();
		tmp${field.name?cap_first}.setId(this.selected${field.name?cap_first});
		this.model.set${field.name?cap_first}(tmp${field.name?cap_first});
			<#else>
		ArrayList<${field.relation.targetEntity}> tmp${field.name?cap_first}List = new ArrayList<${field.relation.targetEntity?cap_first}>();
		for(int i=0; i<this.checked${field.name?cap_first}.length; i++){
			if(this.checked${field.name?cap_first}[i])
				tmp${field.name?cap_first}List.add(this.${field.name}List.get(i));
		}
		
		this.model.set${field.name?cap_first}(tmp${field.name?cap_first}List);
			</#if>
		</#if>	
		</#foreach>

	}

	/** Check data is valid
	 * 
	 * @return true if valid
	 */
	public boolean validateData() {
		return true;
	}

	/** Sets up the UI.
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_${curr.name?lower_case}_create, container, false);

		this.initializeComponent(view);
		this.loadData();
		return view;
	}

	/** 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (this.validateData()) {
			this.saveData();
			new CreateTask(this, this.model).execute();
		}
	}

	public static class CreateTask extends AsyncTask<Void, Void, Integer> {
		protected final Context context;
		protected final ${curr.name}CreateFragment fragment;
		protected final ${curr.name} entity;
		protected String errorMsg;
		protected ProgressDialog progress;

		public CreateTask(${curr.name}CreateFragment fragment, ${curr.name} entity) {
			this.fragment = fragment;
			this.context = fragment.getActivity();
			this.entity = entity;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(context,
					this.context.getString(R.string.${curr.name?lower_case}_progress_save_title),
					this.context.getString(R.string.${curr.name?lower_case}_progress_save_message));
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			${curr.name}SQLiteAdapter ${curr.name?lower_case}Adapter = new ${curr.name}SQLiteAdapter(context);
			SQLiteDatabase db = ${curr.name?lower_case}Adapter.open();
			db.beginTransaction();
			try {
				${curr.name?lower_case}Adapter.insert(this.entity);

				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
				${curr.name?lower_case}Adapter.close();

				result = 0;
			}

			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result == 0) {
				HarmonyFragmentActivity activity = (HarmonyFragmentActivity) this.context;
				activity.finish();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
				builder.setIcon(0);
				builder.setMessage(this.context.getString(R.string.${curr.name?lower_case}_error_create));
				builder.setPositiveButton(
						this.context.getString(android.R.string.yes), 
						new Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

							}
						});
				builder.show();
			}

			this.progress.dismiss();
		}
	}
}
