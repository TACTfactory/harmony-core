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
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if>
import android.widget.RelativeLayout;
import android.widget.TextView;

import ${curr.namespace}.R;
${ImportUtils.importToManyRelatedEntities(curr)}<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;</#if>
import ${project_namespace}.harmony.view.HarmonyFragment;
import ${project_namespace}.menu.CrudEditDeleteMenuWrapper.CrudEditDeleteMenuInterface;
import ${project_namespace}.provider.utils.${curr.name?cap_first}ProviderUtils;

/** ${curr.name} show fragment.
 *
 * @see android.app.Fragment
 */
public class ${curr.name}ShowFragment
		extends HarmonyFragment
		implements CrudEditDeleteMenuInterface {
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
	/** Data layout. */
	protected RelativeLayout dataLayout;
	/** Text view for no ${curr.name}. */
	protected TextView emptyText;


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

		this.dataLayout =
				(RelativeLayout) view.findViewById(
						R.id.${curr.name?lower_case}_data_layout);
		this.emptyText =
				(TextView) view.findViewById(
						R.id.${curr.name?lower_case}_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
    	if (this.model != null) {

    		this.dataLayout.setVisibility(View.VISIBLE);
    		this.emptyText.setVisibility(View.GONE);

<#list fields?values as field>${AdapterUtils.loadDataShowFieldAdapter(field, 2)}</#list>
		} else {
    		this.dataLayout.setVisibility(View.GONE);
    		this.emptyText.setVisibility(View.VISIBLE);
    	}
    }

    @Override
    public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

    	// Inflate the layout for this fragment
        final View view =
        		inflater.inflate(
        				R.layout.fragment_${curr.name?lower_case}_show,
        				container,
        				false);

        
        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((${curr.name}) intent.getParcelableExtra("${curr.name}"));

        return view;
    }

	/**
	 * Updates the view with the given data.
	 *
	 * @param item The ${curr.name} to get the data from.
	 */
	public void update(${curr.name} item) {
    	this.model = item;
    	
		this.loadData();
		
		if (this.model != null) {
	        new LoadTask(this, this.model).execute();
		}
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

	/**
	 * Calls the ${curr.name}EditActivity.
	 * @param position position
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									${curr.name}EditActivity.class);
		intent.putExtra("${curr.name}", (Parcelable) this.model);

		this.getActivity().startActivity(intent);
	}

	/**
	 * Shows a confirmation dialog.
	 * @param position position
	 */
	@Override
	public void onClickDelete() {
		//int position = ((${curr.name}ListAdapter) this.getListAdapter()).getPosition(item);
		//new DeleteDialog(this.getActivity(), this, position).show();
		new DeleteTask(this.getActivity(), this.model).execute();
	}

	/**
	 * Creates an aSyncTask to delete the row.
	 * @param position position
	 */
	public void delete(final int position) {
		//final ${curr.name?cap_first} item = this.mAdapter.getItem(position);
		//new DeleteTask(this.getActivity(), item).execute();
	}

	/**
	 * This class will remove the entity into the DB.
	 * It runs asynchronously.
	 */
	private class DeleteTask extends AsyncTask<Void, Void, Integer> {
		/** AsyncTask's context. */
		private Context ctx;
		/** Entity to delete. */
		private ${curr.name?cap_first} item;

		/**
		 * Constructor of the task.
		 * @param item The entity to remove from DB
		 * @param ctx A context to build ${curr.name?cap_first}SQLiteAdapter
		 */
		public DeleteTask(final Context ctx,
					final ${curr.name?cap_first} item) {
			super();
			this.ctx = ctx;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;

			result = new ${curr.name?cap_first}ProviderUtils(this.ctx)
					.delete(this.item);

			return result;
		}

	}
}

