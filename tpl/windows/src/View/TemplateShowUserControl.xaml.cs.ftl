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

using com.tactfactory.demact.Data;
using com.tactfactory.demact.Entity;
using com.tactfactory.demact.Harmony.Util.StateMachine;
using com.tactfactory.demact.View.PoneyView;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

// The User Control item template is documented at http://go.microsoft.com/fwlink/?LinkId=234236

namespace com.tactfactory.demact.View.JockeyView.UsersControls
{
    public sealed partial class JockeyDetailUserControl : UserControl
    {
        private JockeySQLiteAdapter adapter = new JockeySQLiteAdapter(new DemactSQLiteOpenHelper());

        public JockeySQLiteAdapter Adapter
        {
            get { return adapter; }
        }

        public Jockey JockeyItem { get; set; }

        public StackPanel Stackpanel_btn { get; set; }
        public Button Btn_show_poney { get; set; }

        public JockeyDetailUserControl()
        {
            this.InitializeComponent();
            this.DataContext = this;
            this.Stackpanel_btn = this.stackpanel_btn;
            this.Btn_show_poney = this.btn_show_poney;
        }

        private void btn_show_poney_Tapped(object sender, TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(Transition.PoneyListPage, new PoneyListPage());
        }
    }
}
