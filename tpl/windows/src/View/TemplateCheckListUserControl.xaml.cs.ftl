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

using ${project_namespace}.Data;
using ${project_namespace}.Entity;
using ${project_namespace}.Entity.Checkable;
using ${project_namespace}.Entity.Checkable.Manager;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace com.tactfactory.demact.View.PoneyView.UsersControls
{
    public sealed partial class PoneyCheckListUserControl : UserControl
    {
        private PoneySQLiteAdapter adapter = new PoneySQLiteAdapter(new DemactSQLiteOpenHelper());
        PoneyCheckableManager poneySelectableManager = new PoneyCheckableManager();

        private ObservableCollection<PoneyCheckable> obs;

        public PoneySQLiteAdapter Adapter
        {
            get { return adapter; }
        }

        public ObservableCollection<PoneyCheckable> Obs
        {
            get { return obs; }
        }

        public PoneyCheckListUserControl()
        {
            this.InitializeComponent();
            obs = new ObservableCollection<PoneyCheckable>();
            this.itemsList.ItemsSource = obs;
        }

        public void LoadItem()
        {
            List<Poney> items = adapter.GetAll();

            poneySelectableManager.ParseInSelectables(items);

            if (ViewStateMachine.Instance.Jockey != null)
            {
                poneySelectableManager.SetChecked(ViewStateMachine.Instance.Jockey);
            }
            else if (ViewStateMachine.Instance.Score != null)
            {
                poneySelectableManager.SetChecked(ViewStateMachine.Instance.Score);
            }

            obs.Clear();
            foreach (var item in poneySelectableManager.PoneySelectables)
            {
                obs.Add(item);
            }
        }

        private void btn_validate_Tapped(object sender, TappedRoutedEventArgs e)
        {
            poneySelectableManager.Save(ViewStateMachine.Instance.Jockey);
            ViewStateMachine.Instance.Back();
        }
    }
}