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

import ${namespace}.data.${name}Adapter;
import ${namespace}.entity.${name};
<#list relations as relation>
import ${namespace}.data.${relation.relation.targetEntity}Adapter;
import ${namespace}.entity.${relation.relation.targetEntity};
</#list>

/** ${name} create fragment
 * 
 * @see android.app.Fragment
 */
public class ${name}CreateFragment extends Fragment implements OnClickListener {
	/* Model data */
	protected ${name} model = new ${name}();

	/* Fields View */
	<#list fields as field>
		<#if !field.relation??>
	protected ${field.customEditType} ${field.name}View;
		</#if>
	</#list>
	<#list relations as relation>
		<#if relation.relation.type=="ManyToOne" || relation.relation.type=="OneToOne">
	protected ${relation.customEditType} ${relation.name}View;
	protected List<${relation.type}> ${relation.name}List;
		</#if>
	</#list>
	
	protected Button saveButton;

	/** Initialize view of fields 
	 * 
	 * @param view The layout inflating
	 */
	protected void initializeComponent(View view) {
		<#foreach field in fields>
			<#if !field.relation??>
		this.${field.name}View = (${field.customEditType}) view.findViewById(R.id.${name?lower_case}_${field.name?lower_case}); 
			</#if>
		</#foreach>
		<#list relations as relation>
			<#if (relation.relation.type=="OneToOne" | relation.relation.type=="ManyToOne")>
		this.${relation.name}View = (${relation.customEditType}) view.findViewById(R.id.${name?lower_case}_${relation.name?lower_case}_spinner);
			</#if>
		</#list>
		this.saveButton = (Button) view.findViewById(R.id.${name?lower_case}_btn_save);
		this.saveButton.setOnClickListener(this);
	}

	/** Load data from model to fields view */
	public void loadData() {
		<#foreach field in fields>						
		<#if !field.internal>
			<#if !field.relation??>
				<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="phone") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float")>
		if(this.model.get${field.name?cap_first}()!=null)
				</#if>
				<#if (field.customEditType == "EditText") >
					<#if (field.type == "String") || field.type == "email" || field.type == "login" || field.type == "password" || field.type == "city" || field.type=="text" || field.type== "country">
			this.${field.name}View.setText(this.model.get${field.name?cap_first}()); 
					</#if>
					<#if (field.type == "Date") || field.type == "DateTime" || field.type == "Time">
			this.${field.name}View.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.model.get${field.name?cap_first}())); 
					</#if>
					<#if (field.type == "int") || (field.type=="long") || (field.type=="phone") || (field.type=="ean") || (field.type=="zipcode") || field.type=="float">
			this.${field.name}View.setText(String.valueOf(this.model.get${field.name?cap_first}())); 
					</#if>
				<#elseif (field.customEditType == "CheckBox") >
			this.${field.name}View.setSelected(this.model.${field.name?uncap_first}()); 
				</#if>			
			<#elseif field.relation.type=="ManyToOne" || field.relation.type=="OneToOne">

		${field.type}Adapter ${field.name}Adapter = new ${field.type}Adapter(getActivity());
		${field.name}Adapter.open();
		this.${field.name}List = ${field.name}Adapter.getAll();
		${field.name}Adapter.close();
		
		List<String> ${field.name}Strings = new ArrayList<String>();
		for(${field.type} item : this.${field.name}List) {
			${field.name}Strings.add( String.valueOf(item.getId()) );
		}
		
		ArrayAdapter<String> ${field.name}DataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, ${field.name}Strings);
		this.${field.name}View.setAdapter(${field.name}DataAdapter);

			</#if>
		</#if>
		</#foreach>
	}
	
	/** Save data from fields view to model */
	public void saveData() {
		<#foreach field in fields>
		<#if !field.internal>
			<#if !field.relation??>
		if(!this.${field.name}View.getEditableText().toString().equals(""))
				<#if (field.customEditType == "EditText") >
					<#if (field.type == "String") || field.type == "email" || field.type == "password" || field.type == "login" || field.type == "city" || field.type=="text" || field.type== "country">
		this.model.set${field.name?cap_first}(this.${field.name}View.getEditableText().toString());
					<#elseif (field.type == "Date")>
		try {
			this.model.set${field.name?cap_first}(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(this.${field.name}View.getEditableText().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					<#elseif (field.type == "DateTime")>
		try {
			this.model.set${field.name?cap_first}(new DateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(this.${field.name}View.getEditableText().toString()).getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					<#elseif (field.type == "int") || field.type == "zipcode" || field.type == "phone">
		this.model.set${field.name?cap_first}(Integer.parseInt(this.${field.name}View.getEditableText().toString()));
					<#elseif (field.type == "float")>
		this.model.set${field.name?cap_first}(Float.parseFloat(this.${field.name}View.getEditableText().toString()));
					<#elseif (field.type == "boolean")>
		this.model.set${field.name?cap_first}(this.${field.name}View.getEditableText().toString().equals("true"));
					</#if>
				</#if>
				<#if (field.customEditType == "CheckBox") >
		this.model.set${field.name?cap_first}(this.${field.name}View.isChecked());
				</#if>
			<#elseif field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		${field.relation.targetEntity} tmp${field.name?cap_first} = new ${field.relation.targetEntity?cap_first}();
		tmp${field.name?cap_first}.setId(Integer.parseInt(this.${field.name}View.getSelectedItem().toString()));
		this.model.set${field.name?cap_first}(tmp${field.name?cap_first});
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
		View view = inflater.inflate(R.layout.fragment_${name?lower_case}_create, container, false);

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
		protected final ${name}CreateFragment fragment;
		protected final ${name} entity;
		protected String errorMsg;
		protected ProgressDialog progress;

		public CreateTask(${name}CreateFragment fragment, ${name} entity) {
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

			${name}Adapter ${name?lower_case}Adapter = new ${name}Adapter(context);
			SQLiteDatabase db = ${name?lower_case}Adapter.open();
			db.beginTransaction();
			try {
				<#list relations as relation><#if relation.internal>//TODO: Care with insert</#if></#list>
				${name?lower_case}Adapter.insert(this.entity<#list relations as relation><#if relation.internal>, 0</#if></#list>);

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
