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
    public sealed partial class ${curr.name}ListUserControl : UserControl
    {
        private ${curr.name}SQLiteAdapter adapter = new ${curr.name}SQLiteAdapter(new ${project_name}SQLiteOpenHelper());

        public ${curr.name}SQLiteAdapter Adapter
        {
            get { return adapter; }
        }

        public ObservableCollection<${curr.name}> Obs {get; set;}

        public ${curr.name}ListUserControl()
        {
            this.InitializeComponent();
            Obs = new ObservableCollection<${curr.name}>();
            this.itemsList.ItemsSource = Obs;
        }

        public void LoadItem()
        {
            List<${curr.name}> items;

            <#assign wishedrelation = []/>
            <#list curr.relations as relation>
                <#if relation.type=="ManyToOne" || relation.type=="ManyToMany">
                    <#assign wishedrelation = wishedrelation + relation/>
                </#if>
            </#list>
            <#if wishedrelation?has_content>
                <#list wishedrelation as relation>
                    <#if relation?is_first && !relation?is_last>
            if (ViewStateMachine.Instance.${relation.targetEntity} != null)
            {
                items = adapter.GetByParentId(ViewStateMachine.Instance.${relation.targetEntity});
            }        
                    <#elseif !relation?is_first && relation?is_last>
            else if (ViewStateMachine.Instance.${relation.targetEntity} != null)
            {
                items = adapter.GetByParentId(ViewStateMachine.Instance.${relation.targetEntity});
            }        
                    <#else>
            else
            {
                items = adapter.GetAll();
            }
                    </#if> 
                </#list>
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
            ViewStateMachine.Instance.SetTransition(Transition.${curr.name}DetailPage, new ${curr.name}.${curr.name}DetailPage(), e.ClickedItem);
        }
    }
}

