<#assign curr = entities[current_entity] />
<@header?interpret />
<#assign wishedrelation = []/>
<#list curr.relations as rel>
    <#if ((rel.relation.type=="ManyToOne") || (rel.relation.type=="ManyToMany"))>
        <#assign wishedrelation = wishedrelation + [rel.relation.targetEntity]/>
    </#if>
</#list>

using ${project_namespace}.Data;
using ${project_namespace}.Entity;
using ${project_namespace}.Harmony.Util.StateMachine;
<#if wishedrelation?has_content>
    <#list wishedrelation as targetEntity> 
using ${project_namespace}.View.${targetEntity};
    </#list>
</#if>
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace ${project_namespace}.View.${curr.name}.UsersControls
{
    public sealed partial class ${curr.name}CreateUserControl : UserControl
    {
        public ${curr.name?cap_first}SQLiteAdapter ${curr.name?cap_first}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(new DemactSQLiteOpenHelper());
        
        public ${curr.name?cap_first} ${curr.name?cap_first}Item { get; private set; }
        
        <#if wishedrelation?has_content>
            <#list wishedrelation as targetEntity> 
        public ${targetEntity?cap_first} ${targetEntity?cap_first}Item { get; internal set; }
            </#list>
        </#if>

        public ${curr.name}CreateUserControl()
        {
            this.InitializeComponent();
        }

        private void btn_validate_Tapped(object sender, TappedRoutedEventArgs e)
        {
            <#if wishedrelation?has_content>
                <#list wishedrelation as targetEntity>
                    <#if (targetEntity?is_first)>
            if (ViewStateMachine.Instance.${targetEntity?cap_first} != null)
            {
                int result = ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(
                        <#list fields?values as field>
                            <#if (!field.internal && !field.hidden)>
                                <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
                                    <#if (field.harmony_type?lower_case == "boolean")>
                this.checkbox_${field.name}.IsChecked,
                                    <#else>
                this.text_box_${field.name}.Text,
                                    </#if>
                                </#if>
                            </#if>
                        </#list>
                )
                , ${targetEntity?cap_first}Item);
                ViewStateMachine.Instance.${curr.name} = ${curr.name?cap_first}Adapter.GetById(result);
            }
                    <#else>

                    </#if>
                </#list>
            </#if>
            else
            {
                int result = ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(this.text_box_name.Text, this.text_box_surname.Text));
                ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?cap_first}Adapter.GetById(result);
            }
            ViewStateMachine.Instance.Back();
        }

        private void btn_add_poney_Tapped(object sender, TappedRoutedEventArgs e)
        {
            JockeySQLiteAdapter adapter = new JockeySQLiteAdapter(new DemactSQLiteOpenHelper());
            int result = adapter.Insert(new Jockey(this.text_box_name.Text, this.text_box_surname.Text));
            ViewStateMachine.Instance.Jockey = adapter.GetById(result);
            ViewStateMachine.Instance.SetTransition(Transition.PoneyListPage, new PoneyListPage());
        }

        public void UpdateUI()
        {
            if (ViewStateMachine.Instance.Poney != null || ViewStateMachine.Instance.User != null)
            {
                this.stackpanel_btn.Children.Remove(this.btn_add_poney);
            }
        }
    }
}