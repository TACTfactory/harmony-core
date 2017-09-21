<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />
<#assign wishedrelation = []/>
<#list curr.relations as rel>
    <#if ((rel.relation.type=="OneToMany") || (rel.relation.type=="ManyToMany") || (rel.relation.type=="OneToOne") || (rel.relation.type=="ManyToOne"))>
        <#assign wishedrelation = wishedrelation + [rel.relation.targetEntity]/>
    </#if>
</#list>

using ${project_namespace}.Utils;
using ${project_namespace}.Utils.StateMachine;
<#list curr.relations as rel>
    <#if !rel.internal && ((rel.relation.type=="OneToMany") || (rel.relation.type=="ManyToMany") || (rel.relation.type=="OneToOne") || (rel.relation.type=="ManyToOne"))>
using ${project_namespace}.View.${rel.relation.targetEntity?cap_first};
    </#if>
</#list>
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace ${project_namespace}.View.${curr.name?cap_first}.UsersControls
{
    /// <summary>
    /// ${curr.name?cap_first}ShowUserControl contain real show display mechanism for ${curr.name?cap_first} entity.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}ShowUserControl : BindingUserControl
    {
        private Entity.${curr.name?cap_first} ${curr.name?lower_case}Item;

        /// <summary>
        /// ${curr.name?cap_first}Item use to fill view with item datas.
        /// </summary>
        public Entity.${curr.name?cap_first} ${curr.name?cap_first}Item
        {
            get
            {
                return this.${curr.name?lower_case}Item;
            }

            set
            {
                this.${curr.name?lower_case}Item = value;
                base.OnPropertyChanged("${curr.name?cap_first}Item");
            }
        }

        /// <summary>
        /// Provide access to this buttons and button stackpanel
        /// to be able to update UI in ${curr.name?cap_first} state.
        /// </summary>
        public StackPanel Stackpanel_btn { get; set; }
        <#list fields?values as field>
            <#if (!field.internal && !field.hidden && field.relation??)>
                <#if (field.relation.type == "OneToMany" || field.relation.type == "ManyToMany")>
        public Button Btn_list_related_${field.relation.targetEntity?lower_case} { get; set; }
                <#else>
        public Button Btn_show_${field.relation.targetEntity?lower_case} { get; set; }
                </#if>
            </#if>
        </#list>

        /// <summary>
        /// Default constructor.
        /// Initialize current DataContext and ${curr.name?cap_first}Item
        /// with appropriate datas extract from ViewStateMachine.
        /// </summary>
        public ${curr.name?cap_first}ShowUserControl()
        {
            this.InitializeComponent();
            this.DataContext = this;
            this.${curr.name?cap_first}Item = ViewStateMachine.Instance.${curr.name?cap_first};
            this.Stackpanel_btn = this.stackpanel_btn;
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden && field.relation??)>
                    <#if (field.relation.type == "OneToMany" || field.relation.type == "ManyToMany")>
            this.Btn_list_related_${field.relation.targetEntity?lower_case} = this.btn_list_related_${field.relation.targetEntity?lower_case};
                    <#else>
            this.Btn_show_${field.relation.targetEntity?lower_case} = this.btn_show_${field.relation.targetEntity?lower_case};
                    </#if>
                </#if>
            </#list>
        }

        <#list fields?values as field>
            <#if (!field.internal && !field.hidden && field.relation??)>
                <#if (field.relation.type == "OneToMany" || field.relation.type == "ManyToMany")>
        /// <summary>
        /// Use to make statemachine navigate to ${field.relation.targetEntity?cap_first} list display state.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_list_related_${field.relation.targetEntity?lower_case}_Tapped(object sender, TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(Transition.${curr.name?cap_first}MultiTo${field.relation.targetEntity?cap_first}ListPage,
                new ${field.relation.targetEntity?cap_first}ListPage());
        }

                <#else>
        /// <summary>
        /// Use to make statemachine navigate to ${field.relation.targetEntity?cap_first} show display state.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_show_${field.relation.targetEntity?lower_case}_Tapped(object sender, TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(Transition.${curr.name?cap_first}SoloTo${field.relation.targetEntity?cap_first}ShowPage,
                new ${field.relation.targetEntity?cap_first}ShowPage());
        }
                </#if>
            </#if>
        </#list>
    }
}
