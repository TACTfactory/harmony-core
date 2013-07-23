<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<#assign hasToManyRelation=MetadataUtils.hasToManyRelations(curr) />
<#assign hasRelation=MetadataUtils.hasRelations(curr) />
<@header?interpret />
package ${curr.controller_namespace};

<#if (hasRelation)>
	<#if (hasToManyRelation)>
import java.util.ArrayList;
	</#if>
import java.util.List;
</#if><#if (hasDate || hasTime || hasDateTime)>
import org.joda.time.DateTime;
</#if>
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;<#if (hasDate || hasTime || hasDateTime)>
import android.text.TextUtils;</#if>
import android.view.LayoutInflater;
import android.view.View;<#if (hasDate || hasTime || hasDateTime || hasRelation)>
import android.view.View.OnClickListener;</#if>
import android.view.ViewGroup;<#if (hasRelation)>
import android.widget.Button;</#if><#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if><#if (hasDate || hasDateTime)>
import android.widget.DatePicker;</#if><#if ViewUtils.shouldImportEditText(fields?values)>
import android.widget.EditText;</#if><#if (hasTime || hasDateTime)>
import android.widget.TimePicker;</#if>

import ${curr.namespace}.R;
${ImportUtils.importRelatedEntities(curr)}
${ImportUtils.importRelatedEnums(curr)}import ${curr.namespace}.entity.${curr.name};<#if (hasDate || hasTime || hasDateTime)>
import ${curr.namespace}.harmony.util.DateUtils;</#if>
import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
import ${project_namespace}.harmony.view.HarmonyFragment;<#if (hasDate || hasDateTime)>
import ${curr.namespace}.harmony.widget.CustomDatePickerDialog;</#if><#if (hasTime || hasDateTime)>
import ${curr.namespace}.harmony.widget.CustomTimePickerDialog;</#if>
import ${project_namespace}.harmony.widget.ValidationButtons;
import ${project_namespace}.harmony.widget.ValidationButtons.OnValidationListener;
${ImportUtils.importRelatedProviderUtils(curr)}
import ${project_namespace}.provider.utils.${curr.name?cap_first}ProviderUtils;

/** ${curr.name} create fragment.
 * 
 * @see android.app.Fragment
 */
public class ${curr.name}CreateFragment extends HarmonyFragment 
			implements OnValidationListener {
	/** Model data. */
	protected ${curr.name} model = new ${curr.name}();

	/** Fields View. */
	<#list fields?values as field>
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
	/** ${field.name} View. */
				<#if field.type=="boolean">
	protected CheckBox ${field.name}View;
				<#elseif field.type?lower_case=="datetime">
					<#if field.harmony_type=="datetime" || field.harmony_type=="date">
	protected EditText ${field.name}DateView;
					</#if>
					<#if field.harmony_type=="datetime" || field.harmony_type=="time">
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
	/** Save button. */
	protected ValidationButtons validationButtons;

	/** Initialize view of fields. 
	 * 
	 * @param view The layout inflating
	 */
	protected void initializeComponent(final View view) {
		<#list fields?values as field>
			<#if !field.internal && !field.hidden>
				<#if !field.relation??>
					<#if field.type=="boolean">
		this.${field.name}View = 
				(CheckBox) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
					<#elseif field.type?lower_case == "datetime">
						<#if field.harmony_type == "date" || field.harmony_type == "datetime">
						
		this.${field.name}DateView = 
			(EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_date);			
		this.${field.name}DateView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		        DateTime dt = new DateTime();

		        final String ${field.name}Date = 
		        		${curr.name}CreateFragment.this
		        		.${field.name}DateView.getText().toString();
				if (!TextUtils.isEmpty(${field.name}Date)) {
					final String strInputDate = 
							${field.name}Date;
					dt = DateUtils.formatStringToDate(strInputDate);
				}
				
			    final CustomDatePickerDialog ${field.name}Dpd = 
			    		new CustomDatePickerDialog(getActivity(), 
			    				dt, 
			    				R.string.${field.owner?lower_case}_${field.name?lower_case}_date_title);
			    ${field.name}Dpd.setPositiveButton(getActivity().getString(android.R.string.ok), 
			    		new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final DatePicker dp = 
							((CustomDatePickerDialog) dialog).getDatePicker();
						final DateTime date = 
							new DateTime(dp.getYear(), 
									dp.getMonth() + 1, 
									dp.getDayOfMonth(), 
									0, 
									0);
						${curr.name}CreateFragment.this
							.${field.name}DateView.setText(
									DateUtils.formatDateToString(date));
					}
				});

			    ${field.name}Dpd.show();
			}
		});			
						</#if>
						<#if field.harmony_type == "time" || field.harmony_type == "datetime">
						
		this.${field.name}TimeView = 
			(EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_time);
		this.${field.name}TimeView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DateTime dt = new DateTime(); 
				
				final String ${field.name}Time = 
						${curr.name}CreateFragment.this.${field.name}TimeView.getText().toString();
				if (!TextUtils.isEmpty(${field.name}Time)) {
					final String strInputTime = ${field.name}Time;
					dt = DateUtils.formatStringToTime(strInputTime);
				}
				
			    final CustomTimePickerDialog ${field.name}Tpd =
			    		new CustomTimePickerDialog(getActivity(), 
			    		dt, 
			    		android.text.format.DateFormat.is24HourFormat(getActivity()), 
			    		R.string.${field.owner?lower_case}_${field.name?lower_case}_time_title);
			    ${field.name}Tpd.setPositiveButton(getActivity().getString(
			    										   android.R.string.ok), 
			    					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final TimePicker tp = 
							((CustomTimePickerDialog) dialog).getTimePicker();
						
						DateTime date = new DateTime(0);
						date = new DateTime(date.getYear(), 
								date.getMonthOfYear(), 
								date.getDayOfMonth(), 
								tp.getCurrentHour(), 
								tp.getCurrentMinute());

						${curr.name}CreateFragment.this
							.${field.name}TimeView.setText(
									DateUtils.formatTimeToString(date));
					}
				});

			    ${field.name}Tpd.show();
			}
		});
						</#if>
					<#else>
		this.${field.name}View = 
			(EditText) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
					</#if>
				<#else>
		this.${field.name}Button = 
			(Button) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_button);
		this.${field.name}Button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onClick${field.name?cap_first}Button(v);
			}
		});
				</#if>
			</#if>
		</#list>
		
		this.validationButtons = 
			(ValidationButtons) view.findViewById(R.id.${curr.name?lower_case}_validation);
		this.validationButtons.setListener(this);
	}
	
	<#list curr.relations as relation>
		<#if !relation.internal && !relation.hidden>
	/** Initialize dialog.
	 * @param list list
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
		final AlertDialog.Builder builder = 
				new AlertDialog.Builder(this.getActivity());
		builder.setTitle(R.string.${relation.owner?lower_case}_${relation.name?lower_case}_dialog_title)
				.setMultiChoiceItems(listAdapter, checks, 
							  new DialogInterface.OnMultiChoiceClickListener() {
					public void onClick(DialogInterface dialog, int which, 
															boolean isChecked) {
						${curr.name}CreateFragment.this
						  .checked${relation.name?cap_first}[which] = isChecked;
					}
				}).setPositiveButton(android.R.string.ok, 
										 new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	//${curr.name}CreateFragment.this
		            	//.onOk${relation.name?cap_first}();
		            }
		        }).setNegativeButton(android.R.string.cancel, 
		        						 new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}CreateFragment.this
		            					  .onCancel${relation.name?cap_first}();
		            }
		        });
		
		${relation.name}Dialog = builder.create();
	}
			<#else>
	protected void init${relation.name?cap_first}Dialog(
			final List<${relation.relation.targetEntity}> list) {
		final String[] listAdapter = new String[list.size()];
		int i = 0;
		for (final ${relation.relation.targetEntity} item : list) {
			listAdapter[i] = String.valueOf(item.getId());
			i++;
		}
		final AlertDialog.Builder builder = 
				new AlertDialog.Builder(this.getActivity());
		builder.setTitle(R.string.${relation.owner?lower_case}_${relation.name?lower_case}_dialog_title)
				.setSingleChoiceItems(listAdapter, 0, 
										 new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//${curr.name}CreateFragment.this
						//.selected${relation.name?cap_first} = 
						// Integer.parseInt(listAdapter[id]);
					}
				}).setPositiveButton(android.R.string.ok, 
										 new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}CreateFragment.this
		            		.selected${relation.name?cap_first} = 
		            			Integer.parseInt(
		            					listAdapter[((AlertDialog) dialog).getListView().getCheckedItemPosition()]);
		            }
		        }).setNegativeButton(android.R.string.cancel, 
		        						 new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}CreateFragment.this
		            					  .onCancel${relation.name?cap_first}();
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
	 * @param v View 
	 */
	protected void onClick${relation.name?cap_first}Button(View v) {
		${relation.name}Dialog.show();
	}
		</#if>
	</#list>

	/** Load data from model to fields view. */
	public void loadData() {
		<#list fields?values as field>						
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float") && (field.type!="long") && (field.type!="short") && (field.type!="double") && (field.type != "char") && (field.type != "byte")>
		if (this.model.get${field.name?cap_first}() != null) {
					<#if field.type?lower_case=="datetime">
						<#if field.harmony_type=="datetime" || field.harmony_type=="date">
			this.${field.name}DateView.setText(
					DateUtils.formatDateToString(
							this.model.get${field.name?cap_first}()));
						</#if>
						<#if field.harmony_type=="datetime" || field.harmony_type=="time">
			this.${field.name}TimeView.setText(
					DateUtils.formatTimeToString(
							this.model.get${field.name?cap_first}()));
						</#if>
					<#else>
			${ViewUtils.setLoader(field)}			
					</#if>
		}
				<#else>
		${ViewUtils.setLoader(field)}
				</#if>
			<#else>
		
		
		this.${field.name}List = new ${field.relation.targetEntity}ProviderUtils().queryAll(this.getActivity());
		init${field.name?cap_first}Dialog(this.${field.name}List);
			</#if>
		</#if>
		
		</#list>
	}
	
	/** Save data from fields view to model. */
	public void saveData() {
		<#list fields?values as field>
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if (field.type?lower_case == "datetime")>
					<#if field.harmony_type=="datetime">
		if (!TextUtils.isEmpty(this.${field.name}DateView.getEditableText()) && !TextUtils.isEmpty(this.${field.name}TimeView.getEditableText())) {
					<#elseif field.harmony_type=="date">
		if (!TextUtils.isEmpty(this.${field.name}DateView.getEditableText())) {
					<#elseif field.harmony_type=="time">
		if (!TextUtils.isEmpty(this.${field.name}TimeView.getEditableText())) {
					<#else>
		if (!TextUtils.isEmpty(this.${field.name}View.getEditableText())) {
					</#if>
			${ViewUtils.setSaver(field)}
		}
				<#else>
		${ViewUtils.setSaver(field)}
				</#if>
			<#elseif field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		final ${field.relation.targetEntity} tmp${field.name?cap_first} = 
					new ${field.relation.targetEntity?cap_first}();
		tmp${field.name?cap_first}.setId(this.selected${field.name?cap_first});
		this.model.set${field.name?cap_first}(
				tmp${field.name?cap_first});
			<#else>
		ArrayList<${field.relation.targetEntity}> tmp${field.name?cap_first}List =
				new ArrayList<${field.relation.targetEntity?cap_first}>();
		for (int i = 0; i < this.checked${field.name?cap_first}.length; i++) {
			if (this.checked${field.name?cap_first}[i]) {
				tmp${field.name?cap_first}List.add(
						this.${field.name}List.get(i));
			}
		}
		
		this.model.set${field.name?cap_first}(
				tmp${field.name?cap_first}List);
			</#if>
		</#if>	
		</#list>

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
	 * @see android.support.v4.app.Fragment#onCreateView(
	 * LayoutInflater, ViewGroup, Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {    	
		// Inflate the layout for this fragment
		final View view = inflater.inflate(
				R.layout.fragment_${curr.name?lower_case}_create, 
				container, 
				false);

		this.initializeComponent(view);
		this.loadData();
		return view;
	}

	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class CreateTask extends AsyncTask<Void, Void, Integer> {
		private final Context ctx;
		private final ${curr.name} entity;
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is 
		 * called 
		 */
		public CreateTask(final ${curr.name}CreateFragment fragment, 
				final ${curr.name} entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		/**
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(ctx,
					this.ctx.getString(
							R.string.${curr.name?lower_case}_progress_save_title),
					this.ctx.getString(
							R.string.${curr.name?lower_case}_progress_save_message));
		}

		/**
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			result = new ${curr.name?cap_first}ProviderUtils().insert(
				this.ctx,
				this.entity);

			return result;
		}

		/**
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result >= 0) {
				final HarmonyFragmentActivity activity = 
										 (HarmonyFragmentActivity) this.ctx;
				activity.finish();
			} else {
				final AlertDialog.Builder builder = 
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(
						this.ctx.getString(
								R.string.${curr.name?lower_case}_error_create));
				builder.setPositiveButton(
						this.ctx.getString(android.R.string.yes), 
						new Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog, 
									int which) {

							}
						});
				builder.show();
			}

			this.progress.dismiss();
		}
	}

	public void onValidationSelected() {
		if (this.validateData()) {
			this.saveData();
			new CreateTask(this, this.model).execute();
		}
	}

	public void onCancelSelected() {
		this.getActivity().finish();
	}
}
