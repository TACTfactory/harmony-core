<#assign curr = entities[current_entity] />
<@header?interpret />

using System;
using System.Data.Linq;
using System.Data.Linq.Mapping;


namespace ${project_namespace}.Entity
{
    [Table]
    public class ${curr.name}
    {
        private int id;
        
        [Column(
            IsPrimaryKey = true,
            IsDbGenerated = true,
            DbType = "INT NOT NULL Identity",
            CanBeNull = false,
            AutoSync = AutoSync.OnInsert)]
        public int Id
        {
            get
            {
                return this.id;
            }

            set
            {
                this.id = value;
            }
        }
    
        public ${curr.name}()
        {
        
        }
    }
}