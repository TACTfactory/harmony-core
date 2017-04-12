<#assign curr = entities[current_entity] />
<@header?interpret />
package ${data_namespace};

import ${data_namespace}.base.${curr.name}SQLiteAdapterBase;


/**
 * ${curr.name} adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class ${curr.name}SQLiteAdapter extends ${curr.name}SQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public ${curr.name}SQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
