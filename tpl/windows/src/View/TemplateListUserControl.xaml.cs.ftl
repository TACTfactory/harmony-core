<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.Data;
using ${project_namespace}.Entity;
using ${project_namespace}.Utils.StateMachine;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name?cap_first}.UsersControls
{
    /// <summary>
    /// ${curr.name?cap_first}ListUserControl contain real List display mechanism for ${curr.name?cap_first} entity.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}ListUserControl : UserControl
    {
        /// <summary>
        /// Current ${curr.name?cap_first}SQLiteAdapter managing database ${curr.name?cap_first} 
        /// item finding for ${curr.name?cap_first}ListUserControl.
        /// </summary>
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);

        /// <summary>
        /// Current list for ${curr.name?cap_first} items.
        /// </summary>
        public ObservableCollection<Entity.${curr.name?cap_first}> Obs {get; set;}

        /// <summary>
        /// Constructor.
        /// Setup item .cs list and .xaml list. 
        /// </summary>
        public ${curr.name?cap_first}ListUserControl()
        {
            this.InitializeComponent();
            Obs = new ObservableCollection<Entity.${curr.name?cap_first}>();
            this.itemsList.ItemsSource = Obs;
        }

        /// <summary>
        /// Current list for ${curr.name?cap_first} items.
        /// </summary>
        public void LoadItem()
        {
            List<Entity.${curr.name?cap_first}> items;

            <#assign wishedrelation = []/>
            <#list curr.relations as rel>
                <#if ((rel.relation.type=="ManyToOne") || (rel.relation.type=="ManyToMany"))>
                    <#assign wishedrelation = wishedrelation + [rel.relation.targetEntity]/>
                </#if>
            </#list>
            <#if wishedrelation?has_content>
                <#list wishedrelation as targetEntity> 
                    <#if (targetEntity?is_first)>
            if (ViewStateMachine.Instance.${targetEntity?cap_first} != null)
            {
                items = ${curr.name?lower_case}Adapter.GetByParentId(ViewStateMachine.Instance.${targetEntity?cap_first});
            }    
                    <#else>
            else if (ViewStateMachine.Instance.${targetEntity?cap_first} != null)
            {
                items = ${curr.name?lower_case}Adapter.GetByParentId(ViewStateMachine.Instance.${targetEntity?cap_first});
            }
                    </#if> 
                </#list>
            else
            {
                items = ${curr.name?lower_case}Adapter.GetAll();
            }  
            <#else>
            items = ${curr.name?lower_case}Adapter.GetAll();
            </#if>

            Obs.Clear();
            foreach (var item in items)
            {
                Obs.Add(item);
            }
        }

        private void itemsList_ItemClick(object sender, ItemClickEventArgs e)
        {
            // Save clicked item to procced with.
            ViewStateMachine.Instance.SetTransition(Transition.${curr.name?cap_first}ShowPage, 
                new ${curr.name?cap_first}.${curr.name?cap_first}ShowPage(), e.ClickedItem);
        }
    }
}

