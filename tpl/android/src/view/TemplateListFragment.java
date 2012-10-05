package ${localnamespace};

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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

import ${namespace}.R;
import ${namespace}.entity.${name};
import ${namespace}.view.${name?lower_case}.${name}ListActivity;
import ${namespace}.view.${name?lower_case}.${name}ListLoader;


public class ${name}ListFragment extends ListFragment
implements LoaderManager.LoaderCallbacks<List<${name}>> {

	// Recall internal address (Hack Micky)
	static final int INTERNAL_EMPTY_ID = 0x00ff0001;
	static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
	static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;

	protected ${name}ListAdapter mAdapter;
	protected static ${name}ListFragment instance;

	/** (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(adroid.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		//inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		View view = inflater.inflate(R.layout.fragment_${name?lower_case}_list, null);

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
		this.setEmptyText(getString(R.string.${name?lower_case}_empty_list));

		// We have a menu item to show in action bar.
		//this.setHasOptionsMenu(true);

		// Create an empty adapter we will use to display the loaded data.
		this.mAdapter = new ${name}ListAdapter(getActivity());
		this.setListAdapter(this.mAdapter);

		// Start out with a progress indicator.
		this.setListShown(false); 

		// Prepare the loader.  Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);

		this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> l, View v, final int position, long id) {
				${name} item = (${name}) l.getItemAtPosition(position);

				final Intent intent = new Intent(getActivity(), ${name}EditActivity.class);
				intent.putExtra("${name}", item);

				getActivity().startActivityForResult(intent,0);
				return true;
			}
		});
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		${name} item = (${name}) l.getItemAtPosition(position);

		final Intent intent = new Intent(getActivity(), ${name}ShowActivity.class);
		intent.putExtra("${name}", item);

		this.startActivity(intent);
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.LoaderManager#onCreateLoader(int, android.os.Bundle)
	 */
	@SuppressWarnings("unchecked")
	@Override 
	public Loader<List<${name}>> onCreateLoader(int id, Bundle bundle) { 
		//return ((LoaderManager.LoaderCallbacks<List<${name}>>)getActivity()).onCreateLoader(0, bundle);
		return new ${name}ListLoader(getActivity());
	}

	/** (non-Javadoc)
	 * @see android.support.v4.app.LoaderManager#onLoadFinished()
	 */
	@Override 
	public void onLoadFinished(Loader<List<${name}>> loader, List<${name}> data) {
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
	public void onLoaderReset(Loader<List<${name}>> loader) {
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
		LinearLayout progressLayout = (LinearLayout) rootView.findViewById(R.id.${name?lower_case}ProgressLayout);
		progressLayout.setId(INTERNAL_PROGRESS_CONTAINER_ID);

		// Empty
		TextView emptyText = (TextView) rootView.findViewById(android.R.id.empty);
		emptyText.setId(INTERNAL_EMPTY_ID);

		// ListContainer
		RelativeLayout listContainer = (RelativeLayout) rootView.findViewById(R.id.${name?lower_case}ListContainer);
		listContainer.setId(INTERNAL_LIST_CONTAINER_ID);
		// END HACK
	}
}