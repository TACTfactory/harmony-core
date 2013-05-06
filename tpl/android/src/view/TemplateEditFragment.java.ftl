<#assign curr = entities[current_entity] />
<#import "methods.ftl" as m />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
import ${project_namespace}.harmony.view.HarmonyFragment;
import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;

import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Context;
import android.content.ContentResolver;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


<#assign importDate=false />
<#assign importTime=false />
<#list curr.fields as field>
	<#if !field.internal && !field.hidden>
		<#if (field.type=="date" || field.type=="time" || field.type=="datetime")>
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
import ${curr.namespace}.entity.${curr.name};
<#assign mustImportArrayList=false />
<#assign mustImportList=false />
<#assign import_array = [] />
<#list curr.relations as relation>
	<#if (!relation.internal && !relation.hidden)>
		<#assign mustImportList=true />
		<#if (!m.isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
import ${curr.namespace}.entity.${relation.relation.targetEntity};
import ${project_namespace}.provider.${relation.relation.targetEntity?cap_first}ProviderAdapter;
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

/** ${curr.name} create fragment.
 * 
 * @see android.app.Fragment
 */
public class ${curr.name}EditFragment extends HarmonyFragment implements OnClickListener {
	/** Model data. */
	protected ${curr.name} model = new ${curr.name}();

	/** curr.fields View. */
	<#list curr.fields as field>
		<#if (!field.internal && !field.hidden)>
			<#if (!field.relation??)>
				<#if (field.type=="boolean")>
	protected CheckBox ${field.name}View;
				<#elseif (field.type=="datetime" || field.type=="date" || field.type=="time")>
					<#if (field.type=="datetime" || field.type=="date")>
	protected EditText ${field.name}DateView;
					</#if>
					<#if (field.type=="datetime" || field.type=="time")>
	protected EditText ${field.name}TimeView;
					</#if>
				<#else>
	protected EditText ${field.name}View;			
				</#if>
			<#else>
	protected Button ${field.name}Button;
	protected List<${field.relation.targetEntity}> ${field.name}List;
	protected Dialog ${field.name}Dialog;
				<#if (field.relation.type=="OneToMany" || field.relation.type=="ManyToMany")>
	protected boolean[] checked${field.name?cap_first};
				<#else>
	protected int selected${field.name?cap_first};
				</#if>
			</#if>
		</#if>
	</#list>
	protected Button saveButton;

	/** Initialize view of curr.fields.
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
		this.${field.name}DateView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		        DateTime dt = new DateTime();
		        
				if (!TextUtils.isEmpty(${curr.name}EditFragment.this.${field.name}DateView.getText())) {
					final String strInputDate = ${curr.name}EditFragment.this.${field.name}DateView.getText().toString();
					dt = DateUtils.formatStringToDate(strInputDate);
				}
				
			    final CustomDatePickerDialog ${field.name}Dpd = new CustomDatePickerDialog(getActivity(), dt, R.string.${curr.name?lower_case}_${field.name?lower_case}_date_title);
			    ${field.name}Dpd.setPositiveButton(getActivity().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final DatePicker dp = ((CustomDatePickerDialog) dialog).getDatePicker();
						final DateTime date = new DateTime(dp.getYear(), dp.getMonth() + 1, dp.getDayOfMonth(), 0, 0);
						${curr.name}EditFragment.this.${field.name}DateView.setText(DateUtils.formatDateToString(date));
					}
				});

			    ${field.name}Dpd.show();
			}
		});			
						</#if>
						<#if field.type == "time" || field.type == "datetime">
		this.${field.name}TimeView = (EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_time);
		this.${field.name}TimeView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DateTime dt = new DateTime(); 
		        
				if (!TextUtils.isEmpty(${curr.name}EditFragment.this.${field.name}TimeView.getText())) {
					final String strInputDate = ${curr.name}EditFragment.this.${field.name}TimeView.getText().toString();
					dt = DateUtils.formatStringToTime(strInputDate);
				}
				
			    final CustomTimePickerDialog ${field.name}Tpd = new CustomTimePickerDialog(getActivity(), dt, 
			    		android.text.format.DateFormat.is24HourFormat(getActivity()), R.string.${curr.name?lower_case}_${field.name?lower_case}_time_title);
			    ${field.name}Tpd.setPositiveButton(getActivity().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final TimePicker tp = ((CustomTimePickerDialog) dialog).getTimePicker();
						
						DateTime date = new DateTime(0);
						date = new DateTime(date.getYear(), date.getDayOfMonth(), date.getDayOfMonth(), 
								tp.getCurrentHour(), tp.getCurrentMinute());

						${curr.name}EditFragment.this.${field.name}TimeView.setText(DateUtils.formatTimeToString(date));
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
		this.${field.name}Button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
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
	/** Initialize dialog.
	 * 
	 */		
	 <#if relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany">
	protected void init${relation.name?cap_first}Dialog(final List<${relation.relation.targetEntity}> list) {
		String[] listAdapter = new String[list.size()];
		boolean[] checks = new boolean[list.size()];
		this.checked${relation.name?cap_first} = new boolean[list.size()];
		int i = 0;
		for (final ${relation.relation.targetEntity} item : list) {
			listAdapter[i] = String.valueOf(item.getId());
			checks[i] = false;
			i++;
		}
		final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setTitle(R.string.${curr.name?lower_case}_${relation.name?uncap_first}_dialog_title)
				.setMultiChoiceItems(listAdapter, checks, new DialogInterface.OnMultiChoiceClickListener() {
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						${curr.name}EditFragment.this.checked${relation.name?cap_first}[which] = isChecked;
					}
				}).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	//${curr.name}EditFragment.this.onOk${relation.name?cap_first}();
		            }
		        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}EditFragment.this.onCancel${relation.name?cap_first}();
		            }
		        });
		
		${relation.name}Dialog = builder.create();
	}
			<#else>
	protected void init${relation.name?cap_first}Dialog(final List<${relation.relation.targetEntity}> list) {
		String[] listAdapter = new String[list.size()];
		int i = 0;
		for (final ${relation.relation.targetEntity} item : list) {
			listAdapter[i] = String.valueOf(item.getId());
			i++;
		}
		final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setTitle(R.string.${curr.name?lower_case}_${relation.name?uncap_first}_dialog_title)
				.setSingleChoiceItems(listAdapter, 0, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						${curr.name}EditFragment.this.selected${relation.name?cap_first} = id;
					}
				}).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	//${curr.name}EditFragment.this.onOk${relation.name?cap_first}();
		            }
		        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}EditFragment.this.onCancel${relation.name?cap_first}();
		            }
		        });
		
		${relation.name}Dialog = builder.create();
	} 
	 		</#if>
	/**
	 * Called when the user clicks on cancel.
	 * 
	 */
	protected void onCancel${relation.name?cap_first}() {
		//TODO : Don't change the list
	}
	
	/**
	 * Called when the user clicks on ${relation.name?cap_first} button.
	 * It shows the dedicated dialog.
	 */
	protected void onClick${relation.name?cap_first}Button(View v) {
		${relation.name}Dialog.show();
	}
		</#if>
	</#list>

	/** Load data from model to curr.fields view. */
	public void loadData() {
		<#if (import_array?size > 0)>
		ContentResolver prov = this.getActivity().getContentResolver();
		</#if>

		<#foreach field in curr.fields>						
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float")>
		if (this.model.get${field.name?cap_first}() != null) {
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
		Bundle ${field.name}ResultBundle = prov.call(${field.relation.targetEntity}ProviderAdapter.${field.relation.targetEntity?upper_case}_URI,
				${field.relation.targetEntity}ProviderAdapter.METHOD_QUERY_${field.relation.targetEntity?upper_case},
				null,
				null);
		
		this.${field.name}List = (List<${field.relation.targetEntity}>) ${field.name}ResultBundle.getSerializable(${field.relation.targetEntity}ProviderAdapter.ITEM_KEY);
		init${field.name?cap_first}Dialog(this.${field.name}List);
			</#if>
		</#if>
		</#foreach>
	}
	
	/** Save data from curr.fields view to model. */
	public void saveData() {
		<#foreach field in curr.fields>
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if field.type!="boolean">
					<#if field.type=="date" || field.type=="datetime">
			if (!TextUtils.isEmpty(this.${field.name}DateView.getEditableText())) {
						<#elseif field.type=="time" || field.type=="datetime">
			if (!TextUtils.isEmpty(this.${field.name}TimeView.getEditableText())) {
						<#else>
			if (!TextUtils.isEmpty(this.${field.name}View.getEditableText())) {
					</#if>
			${m.setSaver(field)}
			}
				<#else>
		${m.setSaver(field)}
				</#if>
			<#elseif field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		final ${field.relation.targetEntity} tmp${field.name?cap_first} = new ${field.relation.targetEntity?cap_first}();
		tmp${field.name?cap_first}.setId(this.selected${field.name?cap_first});
		this.model.set${field.name?cap_first}(tmp${field.name?cap_first});
			<#else>
		ArrayList<${field.relation.targetEntity}> tmp${field.name?cap_first}List = new ArrayList<${field.relation.targetEntity?cap_first}>();
		for (int i = 0; i < this.checked${field.name?cap_first}.length; i++) {
			if (this.checked${field.name?cap_first}[i]) {
				tmp${field.name?cap_first}List.add(this.${field.name}List.get(i));
			}
		}
		
		this.model.set${field.name?cap_first}(tmp${field.name?cap_first}List);
			</#if>
		</#if>	
		</#foreach>

	}

	/** Check data is valid.
	 * 
	 * @return true if valid
	 */
	public boolean validateData() {
		return true;
	}

	/** Sets up the UI.
	 * 
	 * @see android.support.v4.app.Fragment#onEditView(LayoutInflater, ViewGroup, Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
		// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.fragment_${curr.name?lower_case}_edit, container, false);

		final Intent intent =  getActivity().getIntent();
		this.model = (${curr.name}) intent.getSerializableExtra("${curr.name}");
		
		this.initializeComponent(view);
		this.loadData();
		
		return view;
	}

	/** 
	 * @see android.view.View.OnClickListener#onClick(android.view.View).
	 */
	@Override
	public void onClick(View v) {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}
	
	/**
	 * This class will update the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class EditTask extends AsyncTask<Void, Void, Integer> {
		protected final Context context;
		protected final ${curr.name} entity;
		protected String errorMsg;
		protected ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is called 
		 */
		public EditTask(final ${curr.name}EditFragment fragment, final ${curr.name} entity) {
			super();
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
			ContentResolver prov = this.context.getContentResolver();
			Bundle b = new Bundle();
			b.putSerializable(${curr.name?cap_first}ProviderAdapter.ITEM_KEY, this.entity);
			Bundle ret = 
					prov.call(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, 
							${curr.name?cap_first}ProviderAdapter.METHOD_UPDATE_${curr.name?upper_case}, 
							null,
							b);

			result = ret.getInt("result",  0); 

			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result > 0) {
				final HarmonyFragmentActivity activity = (HarmonyFragmentActivity) this.context;
				activity.setResult(HarmonyFragmentActivity.RESULT_OK);
				activity.finish();
			} else {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
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
