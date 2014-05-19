<@header?interpret />
package ${project_namespace}.harmony.view;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import ${project_namespace}.menu.${project_name?cap_first}Menu;

/**
 * Harmony custom list Fragment.
 * @param <T> Type to show
 */
public abstract class HarmonyListFragment<T> extends SherlockListFragment
implements LoaderManager.LoaderCallbacks<android.database.Cursor> {
	/**
	 * Recall internal address (Hack Micky).
	 */
	protected static final int INTERNAL_EMPTY_ID = 0x00ff0001;
	/** progress container ID. */
	protected static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
	/** list container ID. */
	protected static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;
	/** OnClickCallBack for activity. */
	protected OnClickCallback clickCallback;
	/** OnLoadCallBack for activity. */
	protected OnLoadCallback loadCallback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
		if (this.getActivity() instanceof OnClickCallback) {
			this.clickCallback = (OnClickCallback) this.getActivity();
		}
		if (this.getActivity() instanceof OnLoadCallback) {
			this.loadCallback = (OnLoadCallback) this.getActivity();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		try {
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
											.clear(menu);
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
										  .updateMenu(menu, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;
		try {
			result = ${project_name?cap_first}Menu.getInstance(
			this.getActivity(),
			this).dispatch(item, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
			.onActivityResult(requestCode, resultCode, data, this.getActivity(),
			this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}


	/**
	 * Initialize Custom List Fragment.
	 * @param rootView The rootview
	 * @param progressLayoutId The Progress Layout id
	 * @param listContainerId The list container id
	 */
	protected void initializeHackCustomList(final View rootView,
			int progressLayoutId,
			int listContainerId) {
		// HACK Micky : Map component support ListFragment
		// Progress
		final LinearLayout progressLayout =
				(LinearLayout) rootView.findViewById(
						progressLayoutId);
		progressLayout.setId(INTERNAL_PROGRESS_CONTAINER_ID);

		// Empty
		final TextView emptyText =
				(TextView) rootView.findViewById(android.R.id.empty);
		emptyText.setId(INTERNAL_EMPTY_ID);

		// ListContainer
		final RelativeLayout listContainer =
				(RelativeLayout) rootView.findViewById(
						listContainerId);
		listContainer.setId(INTERNAL_LIST_CONTAINER_ID);
		// END HACK
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (this.clickCallback != null) {
			this.clickCallback.onListItemClick(l, v, position, id);
		}
	}

	@Override
	public void onLoadFinished(Loader<android.database.Cursor> arg0, android.database.Cursor arg1) {
		this.loadCallback.onListLoaded();
	}

	/** Click callback interface. */
	public interface OnClickCallback {
		/** On list item click.
		 * @param l The list view
		 * @param v The view clicked
		 * @param position The position of the item
		 * @param id the id of the item
		 */
		void onListItemClick(ListView l, View v, int position, long id);
	}

	/** On load callback interface. */
	public interface OnLoadCallback {
		/** On list loaded. */
		void onListLoaded();
	}
}
