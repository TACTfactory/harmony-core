package ${bundle_namespace}.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.command.base.CommandBundleBase;

import ${bundle_namespace}.platform.${bundle_name?cap_first}Adapter;


/**
 * TODO : Javadoc
 * Default command generated for ${bundle_name} Bundle.
 */
@PluginImplementation
public class ${bundle_name?cap_first}Command extends CommandBundleBase<${bundle_name?cap_first}Adapter> {

    @Override
    public void execute(String action, String[] args, String option) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void summary() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isAvailableCommand(String command) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void initBundleAdapter() {
        // TODO Auto-generated method stub
        
    }
}
