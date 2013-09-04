<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign importDate = (FieldsUtils.hasDate(fields?values) || FieldsUtils.hasDateTime(fields?values) || FieldsUtils.hasTime(fields?values)) />
<@header?interpret />
package ${curr.controller_namespace};

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if>
import android.widget.TextView;

import ${curr.namespace}.R;
${ImportUtils.importToManyRelatedEntities(curr)}<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;</#if>
import ${project_namespace}.harmony.view.HarmonyFragment;
import ${project_namespace}.provider.utils.${curr.name?cap_first}ProviderUtils;

/** ${curr.name} show fragment.
 *
 * @see android.app.Fragment
 */
public class ${curr.name}ShowFragment extends HarmonyFragment {
	/** Model data. */
	protected ${curr.name} model;

	/* curr.fields View */
<#list fields?values as field>
	<#if (!field.internal && !field.hidden)>
	/** ${field.name} View. */
		<#if (field.type=="boolean")>
	protected CheckBox ${field.name}View;
		<#else>
	protected TextView ${field.name}View;
		</#if>
	</#if>
</#list>

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
	<#list fields?values as field>
		<#if (!field.internal && !field.hidden)>
			<#if (field.type=="boolean")>
		this.${field.name}View =
			(CheckBox) view.findViewById(
					R.id.${curr.name?lower_case}_${field.name?lower_case});
		this.${field.name}View.setEnabled(false);
			<#else>
		this.${field.name}View =
			(TextView) view.findViewById(
					R.id.${curr.name?lower_case}_${field.name?lower_case});
			</#if>
		</#if>
	</#list>
    }

    /** Load data from model to fields view. */
    public void loadData() {
<#list fields?values as field>${AdapterUtils.loadDataShowFieldAdapter(field, 2)}</#list>
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
    						   ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
        final View view =
        		inflater.inflate(
        				R.layout.fragment_${curr.name?lower_case}_show,
        				container,
        				false);

        final Intent intent =  getActivity().getIntent();
        this.model = (${curr.name?cap_first}) intent.getParcelableExtra(
        													"${curr.name}");

        this.initializeComponent(view);
        new LoadTask(this, this.model).execute();

        return view;
    }

	/**
	 * This class will find the entity into the DB.
	 * It runs asynchronously and shows a progressDialog
	 */
	public static class LoadTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private final Context ctx;
		/** Associated fragment. */
		private final ${curr.name}ShowFragment fragment;
		/** The entity to load. */
		private ${curr.name} entity;
		/** Progress dialog. */
		private ProgressDialog progress;

		/**
		 * Constructor of the task.
		 * @param entity The entity to find in the DB
		 * @param fragment The parent fragment from where the aSyncTask is
		 * called
		 */
		public LoadTask(final ${curr.name}ShowFragment fragment,
												final ${curr.name} entity) {
			super();
			this.fragment = fragment;
			this.ctx = fragment.getActivity();
			this.entity = entity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = ProgressDialog.show(this.ctx,
					this.ctx.getString(
						R.string.${curr.name?lower_case}_progress_load_title),
					this.ctx.getString(
						R.string.${curr.name?lower_case}_progress_load_message));
		}

		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			this.entity = new ${curr.name?cap_first}ProviderUtils(this.ctx).query(
				this.entity.get${curr.ids[0].name?cap_first}());

			if (this.entity != null) {
				result = 0;
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result == 0) {
				this.fragment.model = this.entity;
				this.fragment.loadData();
			} else {
				final AlertDialog.Builder builder =
						new AlertDialog.Builder(this.ctx);
				builder.setIcon(0);
				builder.setMessage(
						this.ctx.getString(
								R.string.${curr.name?lower_case}_error_load));
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
}
