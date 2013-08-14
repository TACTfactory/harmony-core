<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${data_namespace}.${curr.name?cap_first}SQLiteAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Button;<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if>
import android.widget.TextView;


<#assign importDate=false />
<#list fields?values as field>
	<#if !field.internal && !field.hidden>
		<#if (!importDate && field.type?lower_case=="datetime")>
			<#assign importDate=true />
		</#if>
	</#if>
</#list>
<#if (importDate)>
import ${curr.namespace}.harmony.util.DateUtils;
</#if>
import ${curr.namespace}.entity.${curr.name};

/**
 * List adapter for ${curr.name} entity.
 */
public class ${curr.name}ListAdapter extends SimpleCursorAdapter
		implements OnClickListener {

	/**
	 * View & layoutInflater to populate.
	 */
	private final LayoutInflater mInflater;
	/** Fragment to populate. */
	private final ${curr.name?cap_first}ListFragment fragment;

	/**
	 * Constructor.
	 * @param ctx context
	 * @param fragment fragment
	 */
	public ${curr.name}ListAdapter(Context ctx,
			${curr.name?cap_first}ListFragment fragment) {
		super(ctx,
			R.layout.row_${curr.name?lower_case},
			null,
			null,
			null,
			CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		this.mAutoRequery = true;
		this.mInflater = (LayoutInflater) ctx.getSystemService(
									Context.LAYOUT_INFLATER_SERVICE);
		this.fragment = fragment;
	}

	@Override
	public Cursor swapCursor(Cursor newCursor) {
		if (newCursor == this.mCursor) {
            return null;
        }
        Cursor oldCursor = this.mCursor;
        if (oldCursor != null) {
	        if (this.mChangeObserver != null) {
			oldCursor.unregisterContentObserver(this.mChangeObserver);
		}
		if (this.mDataSetObserver != null) {
			oldCursor.unregisterDataSetObserver(this.mDataSetObserver);
		}
        }
        mCursor = newCursor;
        if (newCursor != null) {
			if (this.mChangeObserver != null) {
				newCursor.registerContentObserver(this.mChangeObserver);
			}
            if (this.mDataSetObserver != null) {
				newCursor.registerDataSetObserver(this.mDataSetObserver);
			}
            this.mRowIDColumn = newCursor.getColumnIndexOrThrow(
					${curr.name?cap_first}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)});
            this.mDataValid = true;
            // notify the observers about the new cursor
            this.notifyDataSetChanged();
        } else {
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            // notify the observers about the lack of a data set
            this.notifyDataSetInvalidated();
        }
        return oldCursor;
	}

	@Override
	public View getView(int position,
			View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = this.mInflater.inflate(
					R.layout.row_${curr.name?lower_case},
					parent,
					false);

			holder = new ViewHolder();
			<#list fields?values as field>
				<#if (!field.internal && !field.hidden)>
					<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
						<#if (field.type=="boolean")>
			holder.${field.name}View =
				(CheckBox) convertView.findViewById(
						R.id.row_${curr.name?lower_case}_${field.name?lower_case});
			holder.${field.name}View.setEnabled(false);
						<#else>
			holder.${field.name}View =
				(TextView) convertView.findViewById(
						R.id.row_${curr.name?lower_case}_${field.name?lower_case});
						</#if>
					</#if>
				</#if>
			</#list>

			// Set onClickListeners for edit and delete buttons
			holder.editButton =
				(Button) convertView.findViewById(
						R.id.row_${curr.name?lower_case}_edit_btn);
			holder.editButton.setOnClickListener(this);
			holder.deleteButton =
				(Button) convertView.findViewById(
						R.id.row_${curr.name?lower_case}_delete_btn);
			holder.deleteButton.setOnClickListener(this);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ${curr.name} item = getItem(position);
		if (item != null && holder != null) {
			holder.populate(item);
		}

		holder.editButton.setTag(position);
		holder.deleteButton.setTag(position);

		return convertView;
	}

	@Override
	public ${curr.name} getItem(int position) {
		${curr.name} result = null;
		this.mCursor.moveToPosition(position);
		${curr.name}SQLiteAdapter adapter =
				new ${curr.name}SQLiteAdapter(this.mContext);
		result = adapter.cursorToItem(this.mCursor);
		return result;
	}

	/** Holder row. */
	private static class ViewHolder {
		<#list fields?values as field>
			<#if (!field.hidden && !field.internal)>
				<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
		/** ${field.name?cap_first}'s associated view. */
					<#if (field.type=="boolean")>
		protected CheckBox ${field.name}View;
					<#else>
		protected TextView ${field.name}View;
					</#if>
				</#if>
			</#if>
		</#list>
		/** Edit button. */
		protected Button editButton;
		/** Delete button. */
		protected Button deleteButton;

		/** Populate row with a ${curr.name}.
		 *
		 * @param model ${curr.name} data
		 */
		public void populate(final ${curr.name} model) {
			<#list fields?values as field>
				<#if (!field.internal && !field.hidden)>
					<#if (!field.relation??)>
						<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float") && (field.type!="long") && (field.type!="short") && (field.type!="double") && (field.type != "char") && (field.type != "byte")>
			if (model.get${field.name?cap_first}() != null) {
				${ViewUtils.setAdapterLoader(field)}
			}
						<#else>
			${ViewUtils.setAdapterLoader(field)}
						</#if>
					<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			this.${field.name}View.setText(
					String.valueOf(model.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}()));
					</#if>
				</#if>
			</#list>
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.row_${curr.name?lower_case}_edit_btn:
				this.fragment.onClickEdit((Integer) v.getTag());
				break;
			case R.id.row_${curr.name?lower_case}_delete_btn:
				this.fragment.onClickDelete((Integer) v.getTag());
				break;
			default:
				break;
		}
	}
}
