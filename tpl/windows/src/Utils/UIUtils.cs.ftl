<@header?interpret />

using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Windows.ApplicationModel.Core;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Media;

namespace ${project_namespace}.Utils
{
    /// <summary>
    /// UI utils mechanism provided for C# UI management.
    /// </summary>
    public class UIUtil
    {
        /// <summary>
        /// Used to find item on current displayed screen referencing by is x:Name.
        /// Use example :
        ///     Button myBtn = UIUtil.FindChildControl<Button>(Window.Current.Content, "btn_name") as Button;
        /// </summary>
        /// <typeparam name="T">Any control type.</typeparam>
        /// <param name="control">Object where we check.</param>
        /// <param name="ctrlName">Name of the control we need.</param>
        /// <returns>Wished item.</returns>
        public static DependencyObject FindViewControl<T>(DependencyObject control, string ctrlName)
        {
            int childNumber = VisualTreeHelper.GetChildrenCount(control);
            for (int i = 0; i < childNumber; i++)
            {
                DependencyObject child = VisualTreeHelper.GetChild(control, i);
                FrameworkElement fe = child as FrameworkElement;

                /* Not a framework element or is null */
                if (fe == null) return null;

                if (child is T && fe.Name == ctrlName)
                {
                    /* Found the control so return */
                    return child;
                }
                else
                {
                    /* Not found it - search children */
                    DependencyObject nextLevel = FindViewControl<T>(child, ctrlName);
                    if (nextLevel != null)
                        return nextLevel;
                }
            }
            return null;
        }

        /// <summary>
        /// Run an action on UI.
        /// </summary>
        /// <param name="action">The action.</param>
        public static Task RunOnUI(Action action)
        {
            return CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal, () =>
            {
                action();
            }).AsTask();
        }
    }
}
