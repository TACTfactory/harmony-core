<#import "methods.tpl" as m>
package ${controller_namespace};

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import ${namespace}.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;
import java.util.ArrayList;

import ${namespace}.harmony.util.DateUtils;
import ${namespace}.data.${name}SQLiteAdapter;
import ${namespace}.entity.${name};
<#list relations as relation>
	<#if !relation.internal && !relation.hidden>
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
		<#if field.type=="boolean">
	protected CheckBox ${field.name}View;
		<#else>
	protected TextView ${field.name}View;			
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
			<#if field.type=="boolean">
		this.${field.name}View = (CheckBox) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case});
		this.${field.name}View.setEnabled(false);
			<#else>
		this.${field.name}View = (TextView) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case});			
			</#if>
		</#if>
	</#foreach>
    }
    
    /** Load data from model to fields view */
    public void loadData() {
    	<#foreach field in fields>
			<#if !field.internal && !field.hidden>
				<#if !field.relation??>
		    			<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float")>
		if(this.model.get${field.name?cap_first}()!=null)
							<#if field.type?lower_case=="datetime" || field.type=="date" || field.type=="time">
								<#if field.type?lower_case=="datetime">
			this.${field.name}View.setText(this.model.get${field.name?cap_first}().toString());
								<#/if>
								<#if field.type=="date">
			this.${field.name}View.setText(DateUtils.formatDateToString(model.get"+field.name?cap_first+"()));
								<#/if>
								<#if field.type=="time">
			this.${field.name}View.setText(DateUtils.formatTimeToString(model.get"+field.name?cap_first+"()));					
								<#/if>
			${m.setLoader(field)}
					<#else>
		${m.setLoader(field)}
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
        new LoadTask(this, this.model).execute();
        
        return view;
    }

	public static class LoadTask extends AsyncTask<Void, Void, Integer> {
		protected final Context context;
		protected final ${name}ShowFragment fragment;
		protected ${name} entity;
		protected String errorMsg;
		protected ProgressDialog progress;

		public LoadTask(${name}ShowFragment fragment, ${name} entity) {
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
					this.context.getString(R.string.${name?lower_case}_progress_save_title),
					this.context.getString(R.string.${name?lower_case}_progress_save_message));
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;

			${name}SQLiteAdapter ${name?lower_case}Adapter = new ${name}SQLiteAdapter(context);
			SQLiteDatabase db = ${name?lower_case}Adapter.open();
			db.beginTransaction();
			try {
				this.entity = ${name?lower_case}Adapter.getByID(this.entity.getId());

				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
				${name?lower_case}Adapter.close();

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
				AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
				builder.setIcon(0);
				builder.setMessage(this.context.getString(R.string.${name?lower_case}_error_create));
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
