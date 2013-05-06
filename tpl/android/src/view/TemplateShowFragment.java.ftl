<#assign curr = entities[current_entity] />
<#import "methods.ftl" as m />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;
import android.content.ContentResolver;
import android.widget.CheckBox;
import android.widget.TextView;


<#assign importDate=false />
<#list curr.fields as field>
	<#if !field.hidden>
		<#if (!importDate && (field.type=="date" || field.type=="time" || field.type=="datetime"))>
			<#assign importDate=true />
		</#if>
	</#if>
</#list>
<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;
</#if>
import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};
<#assign import_array = [] />
<#list curr.relations as relation>
	<#if (!relation.internal && !relation.hidden && (relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany"))>
		<#if (!m.isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
import ${curr.namespace}.entity.${relation.relation.targetEntity};
		</#if>
	</#if>
</#list>

/** ${curr.name} show fragment.
 * 
 * @see android.app.Fragment
 */
public class ${curr.name}ShowFragment extends HarmonyFragment {
	/* Model data */
	protected ${curr.name} model;
	
	/* curr.fields View */
<#list curr.fields as field>
	<#if (!field.internal && !field.hidden)>
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
	<#foreach field in curr.fields>
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
	</#foreach>
    }
    
    /** Load data from model to fields view. */
    public void loadData() {
    <#foreach field in curr.fields>
		<#if (!field.internal && !field.hidden)>
			<#if (!field.relation??)>
		    	<#if ((field.type!="int") && 
		    	(field.type!="boolean") && 
		    	(field.type!="long") && 
		    	(field.type!="ean") && 
		    	(field.type!="zipcode") && 
		    	(field.type!="float"))>
		if (this.model.get${field.name?cap_first}() != null) {
					<#if (field.type=="datetime" || field.type=="date" || field.type=="time")>
						<#if (field.type=="datetime")>
			this.${field.name}View.setText(
					DateUtils.formatDateTimeToString(
							model.get${field.name?cap_first}()));
						</#if>
						<#if (field.type=="date")>
			this.${field.name}View.setText(
					DateUtils.formatDateToString(
							model.get${field.name?cap_first}()));
						</#if>
						<#if (field.type=="time")>
			this.${field.name}View.setText(
					DateUtils.formatTimeToString(
							model.get${field.name?cap_first}()));					
						</#if>
					<#else>
			${m.setLoader(field)}
					</#if>
		} 
				<#else>
		${m.setLoader(field)}
				</#if>
			<#elseif (field.relation.type=="OneToOne" || field.relation.type=="ManyToOne")>
		this.${field.name}View.setText(
				String.valueOf(this.model.get${field.name?cap_first}().getId())); 
			<#else>
		String ${field.name}Value = "";
		for (${field.relation.targetEntity} item : this.model.get${field.name?cap_first}()) {
			${field.name}Value += item.getId() + ",";
		}
		this.${field.name}View.setText(${field.name}Value);
			</#if>
		</#if>
	</#foreach>
    }
    
    /** Sets up the UI.
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView
	 * (LayoutInflater, ViewGroup, Bundle)
     */
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
        this.model = (${curr.name?cap_first}) intent.getSerializableExtra(
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
		private final Context context;
		private final ${curr.name}ShowFragment fragment;
		private ${curr.name} entity;
		private String errorMsg;
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
					this.context.getString(
						R.string.${curr.name?lower_case}_progress_load_title),
					this.context.getString(
						R.string.${curr.name?lower_case}_progress_load_message));
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;
			
			ContentResolver prov = this.context.getContentResolver();
			Bundle b = new Bundle();
			b.putSerializable("id", this.entity.getId());
			Bundle ret = 
					prov.call(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, 
							${curr.name?cap_first}ProviderAdapter.METHOD_QUERY_${curr.name?upper_case}, 
							null,
							b);

			this.entity = (${curr.name?cap_first}) ret.getSerializable(
					${curr.name?cap_first}ProviderAdapter.ITEM_KEY); 
			
			if (this.entity != null) {
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
				this.fragment.model = this.entity;
				this.fragment.loadData();
			} else {
				final AlertDialog.Builder builder = 
						new AlertDialog.Builder(this.context);
				builder.setIcon(0);
				builder.setMessage(
						this.context.getString(
								R.string.${curr.name?lower_case}_error_load));
				builder.setPositiveButton(
						this.context.getString(android.R.string.yes), 
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
