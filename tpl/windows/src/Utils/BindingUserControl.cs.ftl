<@header?interpret />

using System.ComponentModel;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.Utils
{
    public class BindingUserControl : UserControl, INotifyPropertyChanged
    {
        /// <summary>
        /// Event to send on change occurs.
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;

        /// <summary>
        /// Method binded to property to handle change.
        /// </summary>
        public void OnPropertyChanged(string name)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null)
            {
                handler(this, new PropertyChangedEventArgs(name));
            }
        }
    }
}
