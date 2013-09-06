<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign relations = ViewUtils.getAllRelations(curr) />
<#assign hasDate = FieldsUtils.hasDate(fields?values) />
<#assign hasTime = FieldsUtils.hasTime(fields?values) />
<#assign hasDateTime = FieldsUtils.hasDateTime(fields?values) />
<#assign hasToManyRelation=FieldsUtils.hasToManyRelations(fields?values) />
<#assign hasRelation=FieldsUtils.hasRelations(fields?values) />
<@header?interpret />
package ${curr.controller_namespace};
<#if (hasRelation)>
	<#if (hasToManyRelation)>
import java.util.ArrayList;
	</#if>
import java.util.List;
</#if>
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;<#if (hasRelation)>
import android.view.View.OnClickListener;</#if>
import android.view.ViewGroup;<#if (hasRelation)>
import android.widget.Button;</#if><#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if><#if ViewUtils.shouldImportEditText(fields?values)>
import android.widget.EditText;</#if>
import android.widget.Toast;

import ${curr.namespace}.R;
${ImportUtils.importRelatedEntities(curr, true)}
${ImportUtils.importRelatedEnums(curr)}
import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
import ${project_namespace}.harmony.view.HarmonyFragment;
<#if (hasDate || hasTime || hasDateTime)>
	<#if (hasDate)>
import ${curr.namespace}.harmony.widget.DateWidget;
	</#if>
	<#if (hasTime)>
import ${curr.namespace}.harmony.widget.TimeWidget;
	</#if>
	<#if (hasDateTime)>
import ${curr.namespace}.harmony.widget.DateTimeWidget;
	</#if>
</#if>
import ${project_namespace}.harmony.widget.ValidationButtons;
import ${project_namespace}.harmony.widget.ValidationButtons.OnValidationListener;
${ImportUtils.importRelatedProviderUtils(curr, true)}

/** ${curr.name} create fragment.
 *
 * @see android.app.Fragment
 */
public class ${curr.name}EditFragment extends HarmonyFragment
			implements OnValidationListener {
	/** Model data. */
	protected ${curr.name} model = new ${curr.name}();

	/** curr.fields View. */
	<#list fields?values as field>
		<#if (!field.internal && !field.hidden)>
			<#if (!field.relation??)>
				<#if (field.type=="boolean")>
	/** ${field.name} View. */
	protected CheckBox ${field.name}View;
				<#elseif field.type?lower_case=="datetime">
					<#if (field.harmony_type=="datetime")>
	/** ${field.name} DateTime View. */
	protected DateTimeWidget ${field.name}View;
					<#elseif (field.harmony_type=="date")>
	/** ${field.name} Date View. */
	protected DateWidget ${field.name}View;
					<#elseif (field.harmony_type=="time")>
	/** ${field.name} Time View. */
	protected TimeWidget ${field.name}View;
					</#if>
				<#else>
	/** ${field.name} View. */
	protected EditText ${field.name}View;
				</#if>
			<#else>
	/** The ${field.name} button. */
	protected Button ${field.name}Button;
	/** The ${field.relation.targetEntity} list. */
	protected List<${field.relation.targetEntity}> ${field.name}List;
	/** The ${field.name} dialog. */
	protected Dialog ${field.name}Dialog;
				<#if field.relation.type=="OneToMany" || field.relation.type=="ManyToMany">
	/** The array of selected ${field.relation.targetEntity}s. */
	protected boolean[] checked${field.name?cap_first};
				<#else>
	/** The selected ${field.relation.targetEntity}. */
	protected int selected${field.name?cap_first};
				</#if>
			</#if>
		</#if>
	</#list>
	/** Save button. */
	protected ValidationButtons validationButtons;;

	/** Initialize view of curr.fields.
	 *
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		<#list fields?values as field>
			<#if !field.internal && !field.hidden>
				<#if !field.relation??>
					<#if field.type=="boolean">
		this.${field.name}View =
			(CheckBox) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
					<#elseif field.type?lower_case == "datetime">
						<#if (field.harmony_type?lower_case == "datetime")>
		this.${field.name}View =
				(DateTimeWidget) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
						<#elseif (field.harmony_type?lower_case == "date")>
		this.${field.name}View =
				(DateWidget) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
						<#elseif (field.harmony_type?lower_case == "time")>
		this.${field.name}View =
				(TimeWidget) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case});
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

	<#list relations as relation>
		<#if !relation.internal && !relation.hidden>
	/** Initialize dialog.
	 * @param list list
	 */
	 <#if relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany">
	protected void init${relation.name?cap_first}Dialog(
					final List<${relation.relation.targetEntity}> list) {
		String[] listAdapter = new String[list.size()];
		boolean[] checks = new boolean[list.size()];
		this.checked${relation.name?cap_first} = new boolean[list.size()];
		int i = 0;
		for (final ${relation.relation.targetEntity} item : list) {
			listAdapter[i] = String.valueOf(item.get${entities[relation.relation.targetEntity].ids[0].name?cap_first}());
			checks[i] = false;
			i++;
		}
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		builder.setTitle(R.string.${relation.owner?lower_case}_${relation.name?lower_case}_dialog_title)
				.setMultiChoiceItems(listAdapter,
						checks,
							  new DialogInterface.OnMultiChoiceClickListener() {
								public void onClick(
										DialogInterface dialog,
										int which, boolean isChecked) {
									${curr.name}EditFragment.this
									.checked${relation.name?cap_first}[which] =
																	  isChecked;
								}
				}).setPositiveButton(android.R.string.ok,
								 new DialogInterface.OnClickListener() {
								 	@Override
								    public void onClick(
								    		DialogInterface dialog,
								          				   int id) {
								       			 //${curr.name}EditFragment.this
								 			//.onOk${relation.name?cap_first}();
								    }
		        }).setNegativeButton(android.R.string.cancel,
		        						 new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}EditFragment.this
		            					  .onCancel${relation.name?cap_first}();
		            }
		        });

		this.${relation.name}Dialog = builder.create();
	}
			<#else>
	protected void init${relation.name?cap_first}Dialog(
			final List<${relation.relation.targetEntity}> list) {
		String[] listAdapter = new String[list.size()];
		int i = 0;
		for (final ${relation.relation.targetEntity} item : list) {
			listAdapter[i] = String.valueOf(item.get${entities[relation.relation.targetEntity].ids[0].name?cap_first}());
			i++;
		}
		final AlertDialog.Builder builder =
				new AlertDialog.Builder(this.getActivity());
		builder.setTitle(R.string.${relation.owner?lower_case}_${relation.name?lower_case}_dialog_title)
				.setSingleChoiceItems(listAdapter, 0,
										 new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						${curr.name}EditFragment.this.
										selected${relation.name?cap_first} = id;
					}
				}).setPositiveButton(android.R.string.ok,
										 new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	//${curr.name}EditFragment.this
		            	//.onOk${relation.name?cap_first}();
		            }
		        }).setNegativeButton(android.R.string.cancel,
		        						 new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${curr.name}EditFragment.
		            				  this.onCancel${relation.name?cap_first}();
		            }
		        });

		this.${relation.name}Dialog = builder.create();
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
	 * @param v The button view
	 */
	protected void onClick${relation.name?cap_first}Button(View v) {
		this.${relation.name}Dialog.show();
	}
		</#if>
	</#list>

	/** Load data from model to curr.fields view. */
	public void loadData() {
<#list fields?values as field>${AdapterUtils.loadDataCreateFieldAdapter(field, 2)}</#list>

<#if hasRelation>		new LoadTask(this).execute();</#if>
	}

	/** Save data from curr.fields view to model. */
	public void saveData() {
<#list fields?values as field>${AdapterUtils.saveDataFieldAdapter(field, 2)}</#list>
	}

	/** Check data is valid.
	 *
	 * @return true if valid
	 */
	public boolean validateData() {
		boolean result = true;
<#list fields?values as field>${AdapterUtils.validateDataFieldAdapter(field, 2)}</#list>
		return result;
	}
	@Override
	public View onCreateView(
				LayoutInflater inflater,
				ViewGroup container,
				Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		final View view =
				inflater.inflate(R.layout.fragment_${curr.name?lower_case}_edit,
						container,
						false);

		final Intent intent =  getActivity().getIntent();
		this.model =
				(${curr.name}) intent.getSerializableExtra("${curr.name}");

		this.initializeComponent(view);
		this.loadData();

		return view;
	}

	/**
	 * This class will update the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class EditTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Entity to update. */
		private final ${curr.name} entity;
		/** Progress Dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public EditTask(final ${curr.name}EditFragment fragment,
												final ${curr.name} entity) {
			super();
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.${curr.name?lower_case}_progress_save_title),
					this.ctx.getString(
							R.string.${curr.name?lower_case}_progress_save_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			result = new ${curr.name?cap_first}ProviderUtils(this.ctx).update(
				this.entity);

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result > 0) {
				final HarmonyFragmentActivity activity =
						(HarmonyFragmentActivity) this.ctx;
				activity.setResult(HarmonyFragmentActivity.RESULT_OK);
				activity.finish();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(this.ctx.getString(
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


<#if hasRelation>
	/**
	 * This class will save the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public class LoadTask extends AsyncTask<Void, Void, Void> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Progress Dialog. */
		private ProgressDialog progress;
		/** Fragment. */
		private ${curr.name}EditFragment fragment;

		/**
		 * Constructor of the task.
		 * @param entity The entity to insert in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final ${curr.name}EditFragment fragment) {
			super();
			this.ctx = fragment.getActivity();
			this.fragment = fragment;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
							R.string.${curr.name?lower_case}_progress_load_relations_title),
					this.ctx.getString(
							R.string.${curr.name?lower_case}_progress_load_relations_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			<#list relations as field>
				<#if !field.internal && !field.hidden>
			this.fragment.${field.name}List = 
				new ${field.relation.targetEntity}ProviderUtils(this.ctx).queryAll();
				</#if>
			</#list>
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			<#list relations as field>
				<#if !field.internal && !field.hidden>
			this.fragment.init${field.name?cap_first}Dialog(
					this.fragment.${field.name}List);
				</#if>
			</#list>
			/*if (result >= 0) {
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
			}*/

			this.progress.dismiss();
		}
	}
</#if>

	@Override
	public void onValidationSelected() {
		if (this.validateData()) {
			this.saveData();
			new EditTask(this, this.model).execute();
		}
	}

	@Override
	public void onCancelSelected() {
		this.getActivity().finish();
	}
}
