<@header?interpret />

using System.Collections.Generic;

namespace ${project_namespace}.View.Radioable.Manager
{
    /// <summary>
    /// Base item to set radioable.
    /// </summary>
    /// <typeparam name="T">Item type.</typeparam>
    interface IRadioableManagerBase<T>
    {
        void ParseInRadioables(List<T> items);
        T GetBaseItem();
    }

    /// <summary>
    /// Parent item link to base item on where we have to save datas 
    /// or find parent item linked base item.
    /// </summary>
    /// <typeparam name="T">Item type.</typeparam>
    interface IRadioableManagerParent<T>
    {
        void Save(T item);
    }
}
