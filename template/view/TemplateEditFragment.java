package ${localnamespace};

import ${namespace}.R;

import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import ${namespace}.entity.${name};

public class ${name}EditFragment extends Fragment {

	<#foreach field in fields>
	private ${field}; 
	</#foreach>
	
	/**
	 * Sets up the UI.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
		// Inflate the layout for this fragment
	    View view = inflater.inflate(R.layout.fragment_${name?lower_case}_edit, container, false);
	    
	    this.initializeComponent(view);
	    
	    return view;
	}
	
	protected void initializeComponent(View view) {
	
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
			
			this.progress = ProgressDialog.show(context, "", ""); //this.context.getString(R.string.${name?lower_case}_progress_save));
		}
	
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			Integer result = -1;
			
			try {
				// TODO to Implement
				// REST call
				//StootieWebServiceImpl service = new StootieWebServiceImpl(this.context);
				//result = service.createStoot(this.stoot);
				
			} catch (Exception e) {
				e.printStackTrace();
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
				//this.fragment.resetAll(true);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
			    builder.setIcon(0);
			    builder.setMessage("");
			    		//this.context.getString(R.string.${name?lower_case}_error_edit));
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