<#import "methods.tpl" as m>
package ${localnamespace};

import ${namespace}.R;

import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.app.*;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import ${namespace}.data.${name}SQLiteAdapter;
import ${namespace}.entity.${name};
<#list relations as relation>
import ${namespace}.data.${relation.relation.targetEntity}SQLiteAdapter;
import ${namespace}.entity.${relation.relation.targetEntity};
</#list>

/** ${name} create fragment
 * 
 * @see android.app.Fragment
 */
public class ${name}EditFragment extends Fragment implements OnClickListener {
	/* Model data */
	protected ${name} model = new ${name}();

	/* Fields View */
	<#list fields as field>
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if field.type=="boolean">
	protected CheckBox ${field.name}View;
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
		<#foreach field in fields>
			<#if !field.internal && !field.hidden>
				<#if !field.relation??>
					<#if field.type=="boolean">
		this.${field.name}View = (CheckBox) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case});
					<#else>
		this.${field.name}View = (EditText) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case});			
					</#if>
				<#else>
		this.${field.name}Button = (Button) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case}_button);
		this.${field.name}Button.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				onClick${field.name?cap_first}Button(v);
			}
		});
				</#if>
			</#if>
		</#foreach>
		
		this.saveButton = (Button) view.findViewById(R.id.${name?lower_case}_btn_save);
		this.saveButton.setOnClickListener(this);
	}
	
	<#list relations as relation>
		<#if !relation.internal && !relation.hidden>
	/** Initialize dialog
	 * 
	 */		<#if relation.relation.type=="OneToMany" || relation.relation.type=="ManyToMany">
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
						${name}EditFragment.this.checked${relation.name?cap_first}[which] = isChecked;
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	//${name}EditFragment.this.onOk${relation.name?cap_first}();
		            }
		        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${name}EditFragment.this.onCancel${relation.name?cap_first}();
		            }
		        });
		
		${relation.name}Dialog = builder.create();
	}
			<#else>
	protected void init${relation.name?cap_first}Dialog(List<${relation.relation.targetEntity}> list){
		String[] listAdapter = new String[list.size()];
		int i=0;
		for(${relation.relation.targetEntity} item : list){
			listAdapter[i] = String.valueOf(item.getId());
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setTitle("Select ${relation.name}")
				.setSingleChoiceItems(listAdapter, 0, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						${name}EditFragment.this.selected${relation.name?cap_first} = id;
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	//${name}EditFragment.this.onOk${relation.name?cap_first}();
		            }
		        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		            	${name}EditFragment.this.onCancel${relation.name?cap_first}();
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
		<#foreach field in fields>						
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float")>
		if(this.model.get${field.name?cap_first}()!=null)
			${m.setLoader(field)}
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
		<#foreach field in fields>
		<#if !field.internal && !field.hidden>
			<#if !field.relation??>
				<#if field.type!="boolean">
		if(!this.${field.name}View.getEditableText().toString().equals(""))
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
	 * @see android.support.v4.app.Fragment#onEditView(LayoutInflater, ViewGroup, Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_${name?lower_case}_edit, container, false);

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
			new EditTask(this, this.model).execute();
		}
	}

	public static class EditTask extends AsyncTask<Void, Void, Integer> {
		protected final Context context;
		protected final ${name}EditFragment fragment;
		protected final ${name} entity;
		protected String errorMsg;
		protected ProgressDialog progress;

		public EditTask(${name}EditFragment fragment, ${name} entity) {
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
				<#list relations as relation><#if relation.internal>//TODO: Care with insert</#if></#list>
				${name?lower_case}Adapter.update(this.entity<#list relations as relation><#if relation.internal>, 0</#if></#list>);

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
				FragmentActivity activity = (FragmentActivity) this.context;
				activity.finish();
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
