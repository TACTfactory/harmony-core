<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.controller_namespace};

import java.util.ArrayList;

import ${project_namespace}.criterias.${curr.name?cap_first}Criterias;
import ${data_namespace}.${curr.name?cap_first}SQLiteAdapter;
import ${project_namespace}.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;
import ${project_namespace}.harmony.view.HarmonyListFragment;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import ${curr.namespace}.R;
import ${curr.namespace}.entity.${curr.name};

/** ${curr.name} list fragment.
 *
 * This fragment gives you an interface to list all your ${curr.name}s.
 *
 * @see android.app.Fragment
 */
public class ${curr.name}ListFragment
		extends HarmonyListFragment<${curr.name}>
		implements CrudCreateMenuInterface {

	/** The adapter which handles list population. */
	protected ${curr.name}ListAdapter mAdapter;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		final View view =
				inflater.inflate(R.layout.fragment_${curr.name?lower_case}_list,
						null);

		this.initializeHackCustomList(view,
				R.id.${curr.name?lower_case}ProgressLayout,
				R.id.${curr.name?lower_case}ListContainer);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.  In a real
		// application this would come from a resource.
		this.setEmptyText(
				getString(
						R.string.${curr.name?lower_case}_empty_list));

		// Create an empty adapter we will use to display the loaded data.
		((PinnedHeaderListView) this.getListView())
					.setPinnedHeaderEnabled(false);
		this.mAdapter = new ${curr.name}ListAdapter(this.getActivity(), this);

		// Start out with a progress indicator.
		this.setListShown(false);

		// Prepare the loader.  Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/* Do click action inside your fragment here. */
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		Loader<Cursor> result = null;
		${curr.name?cap_first}Criterias crit = null;
		if (bundle != null) {
			crit = (${curr.name?cap_first}Criterias) bundle.get(
						${curr.name?cap_first}Criterias.PARCELABLE);
		}
		<#if (curr.orders?? && curr.orders?size > 0) >
		String orderBy = "";
			<#list curr.orders?keys as orderKey>
		orderBy += "${orderKey} ${curr.orders[orderKey]}<#if orderKey_has_next> AND </#if>";
			</#list>
		</#if>

		if (crit != null) {
			result = new ${curr.name?cap_first}ListLoader(this.getActivity(),
				${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI,
				${curr.name?cap_first}SQLiteAdapter.ALIASED_COLS,
				crit,
				<#if (curr.orders?? && curr.orders?size > 0) >orderBy<#else>null</#if>);
		} else {
			result = new ${curr.name?cap_first}ListLoader(this.getActivity(),
				${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI,
				${curr.name?cap_first}SQLiteAdapter.ALIASED_COLS,
				null,
				null,
				<#if (curr.orders?? && curr.orders?size > 0) >orderBy<#else>null</#if>);
		}
		return result;
	}

	@Override
	public void onLoadFinished(
			Loader<Cursor> loader,
			Cursor data) {

		// Set the new data in the adapter.
		data.setNotificationUri(this.getActivity().getContentResolver(),
				${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI);

		ArrayList<${curr.name}> users = new ${curr.name}SQLiteAdapter(
				this.getActivity()).cursorToItems(data);
		this.mAdapter.setNotifyOnChange(false);
		this.mAdapter.setData(
				new ${curr.name}ListAdapter
					.${curr.name}SectionIndexer(users));
		this.mAdapter.setNotifyOnChange(true);
		this.mAdapter.notifyDataSetChanged();
		this.mAdapter.setPinnedPartitionHeadersEnabled(false);
		this.mAdapter.setSectionHeaderDisplayEnabled(false);

		if (this.getListAdapter() == null) {
			this.setListAdapter(this.mAdapter);
		}

		// The list should now be shown.
		if (this.isResumed()) {
			this.setListShown(true);
		} else {
			this.setListShownNoAnimation(true);
		}

		super.onLoadFinished(loader, data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Clear the data in the adapter.
		this.mAdapter.clear();
	}

	@Override
	public void onClickAdd() {
		Intent intent = new Intent(this.getActivity(),
					${curr.name}CreateActivity.class);
		this.startActivity(intent);
	}

}
