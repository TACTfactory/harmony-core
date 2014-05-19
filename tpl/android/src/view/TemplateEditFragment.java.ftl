<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign relations = ViewUtils.getAllRelations(curr) />
<#assign hasDate = FieldsUtils.hasDate(fields?values) />
<#assign hasTime = FieldsUtils.hasTime(fields?values) />
<#assign hasDateTime = FieldsUtils.hasDateTime(fields?values) />
<#assign hasToManyRelation=FieldsUtils.hasToManyRelations(fields?values) />
<#assign hasToOneRelation=MetadataUtils.hasToOneRelations(curr) />
<#assign hasRelation=FieldsUtils.hasRelations(fields?values) />
<@header?interpret />
package ${curr.controller_namespace};

<#if (hasRelation)>import java.util.ArrayList;</#if>

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
<#if hasToManyRelation></#if>
import android.database.sqlite.SQLiteException;
<#if hasToManyRelation>import android.net.Uri;</#if>
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;<#if (ViewUtils.hasTypeBoolean(fields?values))>
import android.widget.CheckBox;</#if><#if ViewUtils.shouldImportEditText(fields?values)>
import android.widget.EditText;</#if>
import android.widget.Toast;

import com.google.common.base.Strings;
import ${curr.namespace}.R;
${ImportUtils.importRelatedEntities(curr, true)}
${ImportUtils.importRelatedEnums(curr)}
import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
import ${project_namespace}.harmony.view.HarmonyFragment;
<#if (hasDate || hasTime || hasDateTime)>
    <#if (hasDate)>
import ${curr.namespace}.harmony.widget.DateWidget;
    </#if>
    <#if (hasTime)>
import ${curr.namespace}.harmony.widget.TimeWidget;
    </#if>
    <#if (hasDateTime)>
import ${curr.namespace}.harmony.widget.DateTimeWidget;
    </#if>
</#if><#if hasToManyRelation>
import ${project_namespace}.harmony.widget.MultiEntityWidget;</#if><#if hasToOneRelation>
import ${project_namespace}.harmony.widget.SingleEntityWidget;</#if><#if (ViewUtils.hasTypeEnum(fields?values))>
import ${project_namespace}.harmony.widget.EnumSpinner;</#if>
import ${project_namespace}.menu.SaveMenuWrapper.SaveMenuInterface;
<#if hasToManyRelation>import ${project_namespace}.provider.${curr.name}ProviderAdapter;</#if>
${ImportUtils.importRelatedProviderUtils(curr, true)}
<#list relations as field>
    <#if (!field.internal && !field.hidden && field.writable)>
        <#if (field.relation.type == "ManyToMany") || (field.relation.type == "OneToMany" && MetadataUtils.getInversingField(field).internal)>
import ${project_namespace}.data.${field.relation.targetEntity}SQLiteAdapter;
        </#if>
    </#if>
</#list>
import ${project_namespace}.provider.contract.${curr.name?cap_first}Contract;
<#if (InheritanceUtils.isExtended(curr))>
import ${project_namespace}.provider.contract.${curr.inheritance.superclass.name?cap_first}Contract;
</#if>
<#assign allRelations = curr.relations />
<#if (singleTabInheritance && curr.inheritance.superclass??) && entities[curr.inheritance.superclass.name]??>
    <#assign allRelations = allRelations + entities[curr.inheritance.superclass.name].relations />
</#if>
<#list allRelations as relation>
import ${project_namespace}.provider.contract.${relation.relation.targetEntity?cap_first}Contract;
</#list>

/** ${curr.name} create fragment.
 *
 * This fragment gives you an interface to edit a ${curr.name}.
 *
 * @see android.app.Fragment
 */
public class ${curr.name}EditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected ${curr.name} model = new ${curr.name}();

    /** curr.fields View. */
    <#list fields?values as field>
        <#if (!field.internal && !field.hidden && field.writable)>
            <#if (field.harmony_type?lower_case != "relation")>
                <#switch FieldsUtils.getJavaType(field)?lower_case>
                    <#case "boolean">
    /** ${field.name} View. */
    protected CheckBox ${field.name}View;
                        <#break />
                    <#case "datetime">
                        <#if (field.harmony_type=="datetime")>
    /** ${field.name} DateTime View. */
    protected DateTimeWidget ${field.name}View;
                        <#elseif (field.harmony_type=="date")>
    /** ${field.name} Date View. */
    protected DateWidget ${field.name}View;
                        <#elseif (field.harmony_type=="time")>
    /** ${field.name} Time View. */
    protected TimeWidget ${field.name}View;
                        </#if>
                        <#break />
                    <#case "enum">
    /** ${field.name} View. */
    protected EnumSpinner ${field.name}View;
                        <#break />
                    <#default>
    /** ${field.name} View. */
    protected EditText ${field.name}View;
                        <#break />
                </#switch>
            <#else>
                <#if field.relation.type=="OneToMany" || field.relation.type=="ManyToMany">
    /** The ${field.name} chooser component. */
    protected MultiEntityWidget ${field.name}Widget;
    /** The ${field.name} Adapter. */
    protected MultiEntityWidget.EntityAdapter<${field.relation.targetEntity}>
            ${field.name}Adapter;
                <#else>
    /** The ${field.name} chooser component. */
    protected SingleEntityWidget ${field.name}Widget;
    /** The ${field.name} Adapter. */
    protected SingleEntityWidget.EntityAdapter<${field.relation.targetEntity}>
            ${field.name}Adapter;
                </#if>
            </#if>
        </#if>
    </#list>

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        <#list fields?values as field>
            <#if (!field.internal && !field.hidden && field.writable)>
                <#if field.harmony_type?lower_case != "relation">
                    <#switch FieldsUtils.getJavaType(field)?lower_case>
                        <#case "boolean">
        this.${field.name}View = (CheckBox) view.findViewById(
                R.id.${curr.name?lower_case}_${field.name?lower_case});
                            <#break />
                        <#case "datetime">
                            <#if (field.harmony_type?lower_case == "datetime")>
        this.${field.name}View = (DateTimeWidget) view.findViewById(
                R.id.${curr.name?lower_case}_${field.name?lower_case});
                            <#elseif (field.harmony_type?lower_case == "date")>
        this.${field.name}View = (DateWidget) view.findViewById(
                R.id.${curr.name?lower_case}_${field.name?lower_case});
                            <#elseif (field.harmony_type?lower_case == "time")>
        this.${field.name}View = (TimeWidget) view.findViewById(
                R.id.${curr.name?lower_case}_${field.name?lower_case});
                            </#if>
                            <#break />
                        <#case "enum">
        this.${field.name}View = (EnumSpinner) view.findViewById(
                R.id.${curr.name?lower_case}_${field.name?lower_case});
        this.${field.name}View.setEnum(${field.enum.targetEnum}.class);
                            <#break />
                        <#default>
        this.${field.name}View = (EditText) view.findViewById(
                R.id.${curr.name?lower_case}_${field.name?lower_case});
                            <#break />
                    </#switch>
                <#else>
                    <#if field.relation.type == "ManyToMany" || field.relation.type == "OneToMany">
        this.${field.name}Adapter =
                new MultiEntityWidget.EntityAdapter<${field.relation.targetEntity}>() {
            @Override
            public String entityToString(${field.relation.targetEntity} item) {
                return String.valueOf(item.get${entities[field.relation.targetEntity].ids[0].name?cap_first}());
            }
        };
        this.${field.name}Widget = (MultiEntityWidget) view.findViewById(
                        R.id.${curr.name?lower_case}_${field.name?lower_case}_button);
        this.${field.name}Widget.setAdapter(this.${field.name}Adapter);
        this.${field.name}Widget.setTitle(R.string.${field.owner?lower_case}_${field.name?lower_case}_dialog_title);
                    <#else>
        this.${field.name}Adapter =
                new SingleEntityWidget.EntityAdapter<${field.relation.targetEntity}>() {
            @Override
            public String entityToString(${field.relation.targetEntity} item) {
                return String.valueOf(item.get${entities[field.relation.targetEntity].ids[0].name?cap_first}());
            }
        };
        this.${field.name}Widget =
            (SingleEntityWidget) view.findViewById(R.id.${curr.name?lower_case}_${field.name?lower_case}_button);
        this.${field.name}Widget.setAdapter(this.${field.name}Adapter);
        this.${field.name}Widget.setTitle(R.string.${field.owner?lower_case}_${field.name?lower_case}_dialog_title);
                    </#if>
                </#if>
            </#if>
        </#list>
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {
<#list fields?values as field>${AdapterUtils.loadDataCreateFieldAdapter(field, 2)}</#list>

<#if hasRelation>        new LoadTask(this).execute();</#if>
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {
<#list fields?values as field>${AdapterUtils.saveDataFieldAdapter(field, 2)}</#list>
    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;
<#list fields?values as field>${AdapterUtils.validateDataFieldAdapter(field, 2)}</#list>
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }
    @Override
    public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(R.layout.fragment_${curr.name?lower_case}_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (${curr.name}) intent.getParcelableExtra(
                ${ContractUtils.getContractParcel(curr)});

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final ${curr.name} entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final ${curr.name}EditFragment fragment,
                    final ${curr.name} entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.${curr.name?lower_case}_progress_save_title),
                    this.ctx.getString(
                            R.string.${curr.name?lower_case}_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new ${curr.name?cap_first}ProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("${curr.name}EditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.${curr.name?lower_case}_error_edit));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
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


<#if hasRelation>
    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private ${curr.name}EditFragment fragment;
        <#list relations as relation>
            <#if !relation.internal && !relation.hidden>
        /** ${relation.name} list. */
        private ArrayList<${relation.relation.targetEntity}> ${relation.name}List;
                <#if relation.relation.type == "ManyToMany" || (relation.relation.type == "OneToMany" && MetadataUtils.getInversingField(relation).internal) >
    /** ${relation.name} list. */
        private ArrayList<${relation.relation.targetEntity}> associated${relation.name?cap_first}List;
                </#if>
            </#if>
        </#list>

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ${curr.name}EditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.${curr.name?lower_case}_progress_load_relations_title),
                this.ctx.getString(
                    R.string.${curr.name?lower_case}_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            <#list relations as field>
                <#if (!field.internal && !field.hidden && field.writable)>
            this.${field.name}List = 
                new ${field.relation.targetEntity}ProviderUtils(this.ctx).queryAll();
                    <#if (field.relation.type == "ManyToMany") || (field.relation.type == "OneToMany" && MetadataUtils.getInversingField(field).internal)>
            Uri ${field.name}Uri = ${curr.name}ProviderAdapter.${curr.name?upper_case}_URI;
            ${field.name}Uri = Uri.withAppendedPath(${field.name}Uri, 
                                    String.valueOf(this.fragment.model.get${curr.ids[0].name?cap_first}()));
            ${field.name}Uri = Uri.withAppendedPath(${field.name}Uri, "${field.name}");
            android.database.Cursor ${field.name}Cursor = 
                    this.ctx.getContentResolver().query(
                            ${field.name}Uri,
                            new String[]{${field.relation.targetEntity}Contract.ALIASED_COL_${entities[field.relation.targetEntity].ids[0].name?upper_case}},
                            null,
                            null, 
                            null);
            
            this.associated${field.name?cap_first}List = new ArrayList<${field.relation.targetEntity}>();
            if (${field.name}Cursor != null && ${field.name}Cursor.getCount() > 0) {
                while (${field.name}Cursor.moveToNext()) {
                    <#list entities[field.relation.targetEntity].ids as id>
                    ${FieldsUtils.getJavaType(id)} ${field.name}${id.name?cap_first} = ${field.name}${AdapterUtils.getCursorGet(id)?cap_first}
                            ${field.name}Cursor.getColumnIndex(${ContractUtils.getContractCol(id)}));
                    </#list>
                    for (${field.relation.targetEntity} ${field.name} : this.${field.name}List) {
                        if (<#list entities[field.relation.targetEntity].ids as id>${field.name}.get${id.name?cap_first}()<#if MetadataUtils.isPrimitive(id)> == <#else>.equals(</#if> ${field.name}${id.name?cap_first}<#if !MetadataUtils.isPrimitive(id)>)</#if><#if id_has_next>
                                && </#if></#list>) {
                            this.associated${field.name?cap_first}List.add(${field.name});
                        }
                    }
                }
                ${field.name}Cursor.close();
            }
                    </#if>
                </#if>
            </#list>
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            <#list relations as field>
                <#if (!field.internal && !field.hidden && field.writable)>
                    <#if (field.relation.type == "ManyToMany") || (field.relation.type == "OneToMany" && MetadataUtils.getInversingField(field).internal)>
            this.fragment.model.set${field.name?cap_first}(this.associated${field.name?cap_first}List);
                    </#if>
            this.fragment.on${field.name?cap_first}Loaded(this.${field.name}List);
                </#if>
            </#list>

            this.progress.dismiss();
        }
    }
</#if>

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

<#list relations as relation>
    <#if !relation.internal && !relation.hidden>
    /**
     * Called when ${relation.name} have been loaded.
     * @param items The loaded items
     */
    protected void on${relation.name?cap_first}Loaded(ArrayList<${relation.relation.targetEntity}> items) {
        this.${relation.name}Adapter.loadData(items);
        <#if (relation.relation.type == "OneToMany")>
            <#if (MetadataUtils.getInversingField(relation).internal)>
        this.${relation.name}Adapter.setCheckedItems(this.model.get${relation.name?cap_first}());
            <#else>
        ArrayList<${relation.relation.targetEntity}> modelItems = new ArrayList<${relation.relation.targetEntity}>();
        for (${relation.relation.targetEntity} item : items) {
            if (item.get${MetadataUtils.getInversingField(relation).name?cap_first}().getId() == this.model.getId()) {
                modelItems.add(item);
                this.${relation.name}Adapter.checkItem(item, true);
            }
        }
        this.model.set${relation.name?cap_first}(modelItems);
            </#if>
        <#elseif (relation.relation.type == "ManyToMany") >
        this.${relation.name}Adapter.setCheckedItems(this.model.get${relation.name?cap_first}());
        <#else>
        
        for (${relation.relation.targetEntity} item : items) {
            if (item.get${entities[relation.relation.targetEntity].ids[0].name?cap_first}() == this.model.get${relation.name?cap_first}().get${entities[relation.relation.targetEntity].ids[0].name?cap_first}()) {
                this.${relation.name}Adapter.selectItem(item);
            }
        }
        </#if>
    }
    </#if>
</#list>
}
