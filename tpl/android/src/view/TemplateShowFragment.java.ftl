<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign importDate = (FieldsUtils.hasDate(fields?values) || FieldsUtils.hasDateTime(fields?values) || FieldsUtils.hasTime(fields?values)) />
<@header?interpret />
package ${curr.controller_namespace};

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if>
import android.widget.RelativeLayout;
import android.widget.TextView;

import ${curr.namespace}.R;
${ImportUtils.importToManyRelatedEntities(curr)}<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;</#if>
import ${project_namespace}.harmony.view.DeleteDialog;
import ${project_namespace}.harmony.view.HarmonyFragment;
import ${project_namespace}.harmony.view.MultiLoader;
import ${project_namespace}.harmony.view.MultiLoader.UriLoadedCallback;
<#if curr.deleteAction>import ${project_namespace}.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;</#if>
<#if curr.editAction>import ${project_namespace}.menu.CrudEditMenuWrapper.CrudEditMenuInterface;</#if>
import ${project_namespace}.provider.utils.${curr.name?cap_first}ProviderUtils;
import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;
import ${project_namespace}.provider.contract.${curr.name?cap_first}Contract;
<#list curr.relations as relation>
import ${project_namespace}.provider.contract.${relation.relation.targetEntity?cap_first}Contract;
</#list>

/** ${curr.name} show fragment.
 *
 * This fragment gives you an interface to show a ${curr.name}.
 * 
 * @see android.app.Fragment
 */
public class ${curr.name}ShowFragment
		extends HarmonyFragment
		<#if curr.deleteAction || curr.editAction>implements </#if><#if curr.deleteAction>CrudDeleteMenuInterface,
				DeleteDialog.DeleteDialogCallback<#if curr.editAction>,</#if></#if>
				<#if curr.editAction>CrudEditMenuInterface</#if> {
	/** Model data. */
	protected ${curr.name} model;
<#if curr.deleteAction>
	/** DeleteCallback. */
	protected DeleteCallback deleteCallback;
</#if>

	/* This entity's fields views */
<#list fields?values as field>
	<#if (!field.internal && !field.hidden)>
	/** ${field.name} View. */
		<#if (field.harmony_type?lower_case == "boolean")>
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
			<#if (field.harmony_type?lower_case == "boolean")>
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
<#if curr.deleteAction>
        if (this.getActivity() instanceof DeleteCallback) {
        	this.deleteCallback = (DeleteCallback) this.getActivity();
        }
</#if>

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((${curr.name}) intent.getParcelableExtra(${curr.name}Contract.${curr.name}.PARCEL));

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
			MultiLoader loader = new MultiLoader(this);
			String baseUri = 
					${curr.name}ProviderAdapter.${curr.name?upper_case}_URI 
					+ "/" 
					+ this.model.get${curr_ids[0].name?cap_first}();

			loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					${curr.name}ShowFragment.this.on${curr.name}Loaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
			<#list curr.relations as relation>
				<#if !relation.internal>
			loader.addUri(Uri.parse(baseUri + "/${relation.name?lower_case}"), 
					new UriLoadedCallback() {

				@Override
				public void onLoadComplete(Cursor c) {
					${curr.name}ShowFragment.this.on${relation.name?cap_first}Loaded(c);
				}

				@Override
				public void onLoaderReset() {

				}
			});
				</#if>
			</#list>
			loader.init();
		}
    }

	/**
	 * Called when the entity has been loaded.
	 * 
	 * @param c The cursor of this entity
	 */
	public void on${curr.name}Loaded(Cursor c) {
		if (c.getCount() > 0) {
			c.moveToFirst();
			
			${curr.name}Contract.${curr.name}.cursorToItem(
						c,
						this.model);
			this.loadData();
		}
	}
	<#list curr.relations as relation>
		<#if !relation.internal>
	/**
	 * Called when the relation has been loaded.
	 * 
	 * @param c The cursor of this relation
	 */
	public void on${relation.name?cap_first}Loaded(Cursor c) {
		if (this.model != null) {
			if (c != null) {
		<#if relation.relation.type == "ManyToOne" || relation.relation.type == "OneToOne">
				if (c.getCount() > 0) {
					c.moveToFirst();
					this.model.set${relation.name?cap_first}(${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity}.cursorToItem(c));
					this.loadData();
				}
		<#else>
			this.model.set${relation.name?cap_first}(${relation.relation.targetEntity?cap_first}Contract.${relation.relation.targetEntity}.cursorToItems(c));
			this.loadData();
		</#if>
			} else {
				this.model.set${relation.name?cap_first}(null);
					this.loadData();
			}
		}
	}
		</#if>
	</#list>

	<#if curr.editAction>
	/**
	 * Calls the ${curr.name}EditActivity.
	 */
	@Override
	public void onClickEdit() {
		final Intent intent = new Intent(getActivity(),
									${curr.name}EditActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable(${curr.name}Contract.${curr.name}.PARCEL, this.model);
		intent.putExtras(extras);

		this.getActivity().startActivity(intent);
	}
	</#if>
	<#if curr.deleteAction>
	/**
	 * Shows a confirmation dialog.
	 */
	@Override
	public void onClickDelete() {
		new DeleteDialog(this.getActivity(), this).show();
	}

	@Override
	public void onDeleteDialogClose(boolean ok) {
		if (ok) {
			new DeleteTask(this.getActivity(), this.model).execute();
		}
	}
	
	/** 
	 * Called when delete task is done.
	 */	
	public void onPostDelete() {
		if (this.deleteCallback != null) {
			this.deleteCallback.onItemDeleted();
		}
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

		@Override
		protected void onPostExecute(Integer result) {
			if (result >= 0) {
				${curr.name}ShowFragment.this.onPostDelete();
			}
			super.onPostExecute(result);
		}
		
		

	}

	/**
	 * Callback for item deletion.
	 */ 
	public interface DeleteCallback {
		/** Called when current item has been deleted. */
		void onItemDeleted();
	}
	</#if>
}

