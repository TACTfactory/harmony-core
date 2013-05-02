<#assign curr = entities[current_entity] />
package ${curr.controller_namespace};

import java.util.List;

import ${project_namespace}.criterias.${curr.name?cap_first}Criterias;
import ${data_namespace}.${curr.name?cap_first}SQLiteAdapter;
import ${project_namespace}.harmony.view.DeletableList;
import ${project_namespace}.harmony.view.DeleteDialog;
import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

import ${project_namespace}.harmony.view.HarmonyListFragment;

import ${curr.namespace}.R;
import ${curr.namespace}.entity.${curr.name};
import ${curr.namespace}.view.${curr.name?lower_case}.${curr.name}ListLoader;

public class ${curr.name}ListFragment extends HarmonyListFragment<${curr.name}> 
	implements DeletableList {

	// Recall internal address (Hack Micky)
	static final int INTERNAL_EMPTY_ID = 0x00ff0001;
	static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
	static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;

	protected ${curr.name}ListAdapter mAdapter;
	protected static ${curr.name}ListFragment instance;

	/** (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(adroid.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		//inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		View view = inflater.inflate(R.layout.fragment_${curr.name?lower_case}_list, null);

		this.initializeHackCustomList(view);

		return view;
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onActivityCreated(android.os.Bundle)
	 */
	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(getString(R.string.${curr.name?lower_case}_empty_list));

		// We have a menu item to show in action bar.
		//this.setHasOptionsMenu(true);

		// Create an empty adapter we will use to display the loaded data.
		this.mAdapter = new ${curr.name}ListAdapter(this.getActivity(), this);
		this.setListAdapter(this.mAdapter);

		// Start out with a progress indicator.
		this.setListShown(false); 

		// Prepare the loader.  Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		${curr.name} item = (${curr.name}) l.getItemAtPosition(position);

		final Intent intent = new Intent(getActivity(), ${curr.name}ShowActivity.class);
		intent.putExtra("${curr.name}", item);

		this.startActivity(intent);
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.LoaderManager#onCreateLoader(int, android.os.Bundle)
	 */
	@Override 
	public Loader<List<${curr.name}>> onCreateLoader(int id, Bundle bundle) { 
		${curr.name?cap_first}Criterias crit = null;
		if (bundle!=null){
			crit = (${curr.name?cap_first}Criterias)bundle.get(${curr.name?cap_first}Criterias._PARCELABLE);
		}
			
		return new ${curr.name?cap_first}ListLoader(getActivity(), crit );
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.LoaderManager#onLoadFinished()
	 */
	@Override 
	public void onLoadFinished(Loader<List<${curr.name}>> loader, List<${curr.name}> data) {
		// Set the new data in the adapter.
		this.mAdapter.setData(data);

		// The list should now be shown.
		if (this.isResumed()) {
			this.setListShown(true);
		} else {
			this.setListShownNoAnimation(true);
		}
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.LoaderManager#onLoaderReset()
	 */
	@Override 
	public void onLoaderReset(Loader<List<${curr.name}>> loader) {
		// Clear the data in the adapter.
		this.mAdapter.setData(null);
	}

	/** Initialize Custom List Fragment
	 * 
	 * @param rootView
	 */
	private void initializeHackCustomList(View rootView) {
		// HACK Micky : Map component support ListFragment
		// Progress
		LinearLayout progressLayout = (LinearLayout) rootView.findViewById(R.id.${curr.name?lower_case}ProgressLayout);
		progressLayout.setId(INTERNAL_PROGRESS_CONTAINER_ID);

		// Empty
		TextView emptyText = (TextView) rootView.findViewById(android.R.id.empty);
		emptyText.setId(INTERNAL_EMPTY_ID);

		// ListContainer
		RelativeLayout listContainer = (RelativeLayout) rootView.findViewById(R.id.${curr.name?lower_case}ListContainer);
		listContainer.setId(INTERNAL_LIST_CONTAINER_ID);
		// END HACK
	}


	protected void onClickEdit(int position) {
		${curr.name} item = this.mAdapter.getItem(position);
		final Intent intent = new Intent(getActivity(), ${curr.name}EditActivity.class);
		intent.putExtra("${curr.name}", item);

		this.getActivity().startActivityForResult(intent,0);
	}	
	
	
	protected void onClickDelete(int position) {
		new DeleteDialog(this.getActivity(), this, position).show();
	}

	public void delete(int position) {
		${curr.name?cap_first} item = this.mAdapter.getItem(position);
		new DeleteTask(this.getActivity(), item).execute();
	}

	private class DeleteTask extends AsyncTask<Void, Void, Integer> {
		private Context context;
		private ${curr.name?cap_first} item;
		
		public DeleteTask(Context context, ${curr.name?cap_first} item) {
			this.context = context;
			this.item = item;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;
			
			ContentResolver prov = this.context.getContentResolver();
			Bundle b = new Bundle();
			b.putSerializable(${curr.name?cap_first}ProviderAdapter.ITEM_KEY, this.item);
			Bundle ret = 
					prov.call(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, 
							${curr.name?cap_first}ProviderAdapter.METHOD_DELETE_${curr.name?upper_case}, 
							null,
							b);

			result = ret.getInt("result",  0); 
			return result;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			if (result > 0) {
				${curr.name?cap_first}ListFragment.this.getLoaderManager().restartLoader(0,
					null, 
					${curr.name?cap_first}ListFragment.this);
			}
		}
		
	}


}
