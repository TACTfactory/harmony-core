<#assign curr = entities[current_entity] />
<@header?interpret />
using System.Data.Linq;
using ${project_namespace}.Data.Base;

namespace ${project_namespace}.Data
{
    public class ${curr.name}SqlAdapter : ${curr.name}SqlAdapterBase
    {
        private const string TAG = "${curr.name}SqlAdapterBase";

        public ${curr.name}SqlAdapter()
        {

        }

        public ${curr.name}SqlAdapter(DataContext context)
            : base(context)
        {

        }
    }
}
