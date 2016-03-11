<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.Data;
using ${project_namespace}.Entity;
using ${project_namespace}.Harmony.Util.StateMachine;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name}.UsersControls
{
    /// <summary>
    /// ${curr.name}ListUserControl contain real List display mechanism for ${curr.name} entity.
    /// </summary>
    public sealed partial class ${curr.name}ListUserControl : UserControl
    {
        private ${curr.name}SQLiteAdapter adapter = 
            new ${curr.name}SQLiteAdapter(new ${project_name}SQLiteOpenHelper());

        /// <summary>
        /// Current ${curr.name}SQLiteAdapter managing database ${curr.name} 
        /// item finding for ${curr.name}ListUserControl.
        /// </summary>
        public ${curr.name}SQLiteAdapter Adapter
        {
            get { return adapter; }
        }

        /// <summary>
        /// Current list for ${curr.name} items.
        /// </summary>
        public ObservableCollection<${curr.name}> Obs {get; set;}

        /// <summary>
        /// Constructor.
        /// Setup item .cs list and .xaml list. 
        /// </summary>
        public ${curr.name}ListUserControl()
        {
            this.InitializeComponent();
            Obs = new ObservableCollection<${curr.name}>();
            this.itemsList.ItemsSource = Obs;
        }

        /// <summary>
        /// Current list for ${curr.name} items.
        /// </summary>
        public void LoadItem()
        {
            List<${curr.name}> items;

            <#assign wishedrelation = []/>
            <#list curr.relations as rel>
                <#if ((rel.relation.type=="ManyToOne") || (rel.relation.type=="ManyToMany"))>
                    <#assign wishedrelation = wishedrelation + [rel.relation.targetEntity]/>
                </#if>
            </#list>
            <#if wishedrelation?has_content>
                <#list wishedrelation as targetEntity> 
                    <#if (targetEntity?is_first)>
            if (ViewStateMachine.Instance.${targetEntity} != null)
            {
                items = adapter.GetByParentId(ViewStateMachine.Instance.${targetEntity});
            }    
                    <#else>
            else if (ViewStateMachine.Instance.${targetEntity} != null)
            {
                items = adapter.GetByParentId(ViewStateMachine.Instance.${targetEntity});
            }
                    </#if> 
                </#list>
            else
            {
                items = adapter.GetAll();
            }  
            <#else>
            items = adapter.GetAll();
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
            ViewStateMachine.Instance.SetTransition(Transition.${curr.name}DetailPage, 
                new ${curr.name}.${curr.name}DetailPage(), e.ClickedItem);
        }
    }
}

