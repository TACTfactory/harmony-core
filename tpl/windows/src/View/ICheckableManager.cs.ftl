<@header?interpret />

using System.Collections.Generic;

namespace ${project_namespace}.View.Checkable.Manager
{
    /// <summary>
    /// Base item to set checkable.
    /// </summary>
    /// <typeparam name="T">Item type.</typeparam>
    interface ICheckableManagerBase<T>
    {
        void ParseInCheckables(List<T> items);
        List<T> GetBaseList();
    }

    /// <summary>
    /// Parent item link to base item on where we have to save datas
    /// or find parent item linked base item.
    /// </summary>
    /// <typeparam name="T">Item type.</typeparam>
    interface ICheckableManagerParent<T>
    {
        void SetChecked(T item);
        void Save(T item);
    }
}
