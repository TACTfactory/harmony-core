<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name}
{
    /// <summary>
    /// ${curr.name}ListPage allow to create a new item based on ${curr.name} entity
    /// This ${curr.name}ListPage load graphical context and be managed by ${curr.name}ListState 
    /// </summary>
    public partial class ${curr.name}ListPage : Page
    {
        // Used to manage ${curr.name}List UserControl in ${curr.name}ListState
        public ${curr.name}ListUserControl ${curr.name}ListUserControl { get; set; }
        
        // Used to manage NavigationBrowser in ${curr.name}ListState
        public NavigationBrowser NavigationBrowser { get; set; }

        public ${curr.name}ListPage()
        {
            InitializeComponent();

            this.${curr.name}ListUserControl = this.${curr.name?lower_case}_list_usercontrol;
            this.NavigationBrowser = this.navigation_broswer;
        }
    }
}

using com.tactfactory.demact.Harmony.Util.StateMachine;
using com.tactfactory.demact.View.JockeyView;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;

namespace com.tactfactory.demact.View.Navigation.States
{
    class JockeyListState : ViewStateMachineState
    {
        private JockeyListPage jockeyListPage;

        public JockeyListState()
        {
            this.stateID = StateID.JockeyListPageEnter;
        }

        public override void DoBeforeEntering(Page page)
        {
            this.jockeyListPage = page as JockeyListPage;
            base.DoBeforeEntering(page);
        }

        public override void DoBeforeLeaving(Page page)
        {
            this.jockeyListPage.NavigationBrowser.Btn_New.Tapped -= Btn_New_Tapped;
            this.jockeyListPage.NavigationBrowser.Btn_Erase_All.Tapped -= Btn_Erase_All_Tapped;
            this.jockeyListPage.NavigationBrowser.Btn_Back.Tapped -= Btn_Back_Tapped;
            this.jockeyListPage.NavigationBrowser.Btn_Existing.Tapped -= Btn_Existing_Tapped;
        }

        public override void DoAfterEntering()
        {
            this.jockeyListPage.JockeyItemsList.LoadItem();

            this.jockeyListPage.NavigationBrowser.Btn_New.Tapped += Btn_New_Tapped;
            this.jockeyListPage.NavigationBrowser.Btn_Erase_All.Tapped += Btn_Erase_All_Tapped;
            this.jockeyListPage.NavigationBrowser.Btn_Existing.Tapped += Btn_Existing_Tapped;
            this.jockeyListPage.NavigationBrowser.Btn_Back.Tapped += Btn_Back_Tapped;

            this.UpdateUI();
        }

        private void UpdateUI()
        {
            if (ViewStateMachine.Instance.Poney == null)
            {
                this.jockeyListPage.NavigationBrowser.Stackpanel_Btn.Children.Remove(jockeyListPage.NavigationBrowser.Btn_Existing);
            }
        }

        private void Btn_Back_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.Back();
        }

        private void Btn_Erase_All_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            if (ViewStateMachine.Instance.Poney != null)
            {
                this.jockeyListPage.JockeyItemsList.Adapter.Clear(ViewStateMachine.Instance.Poney.Id);
            }
            else
            {
                this.jockeyListPage.JockeyItemsList.Adapter.Clear();
            }
            this.jockeyListPage.JockeyItemsList.Obs.Clear();
        }

        private void Btn_New_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(Transition.JockeyCreatePage, new JockeyView.JockeyCreatePage());
        }

        private void Btn_Existing_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(Transition.JockeyCheckListPage, new JockeyView.JockeyCheckListPage());
        }
    }
}

