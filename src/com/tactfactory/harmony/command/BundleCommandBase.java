package com.tactfactory.harmony.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.plateforme.TargetPlatform;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Base of Bundle command.<br/>
 * In many case the bundle does override the core function.
 * @param <T> The adapter interface of the bundle
 */
public abstract class BundleCommandBase<T extends IAdapter> extends BaseCommand {

    protected HashMap<TargetPlatform, Class<? extends T>> adapterMapping = 
            new HashMap<TargetPlatform, Class<? extends T>>();

    /**
     * Register and transform to typed Adapter of this Bundle.
     */
    @Override
    public void registerAdapters(ArrayList<IAdapter> adapters) {
        ArrayList<IAdapter> lazyTypes = new ArrayList<IAdapter>();
        
        if (adapters.size() > 0) {
            for (TargetPlatform mappedPlatform : adapterMapping.keySet()) {
                for (IAdapter coreAdapter : adapters) {
                    TargetPlatform targetPlatform = TargetPlatform.parse(coreAdapter);
                    
                    if (mappedPlatform.equals(targetPlatform)) {
                        T newAdapter = null;
                        try {
                            newAdapter = (T)adapterMapping.get(mappedPlatform).getConstructor(IAdapter.class).newInstance(coreAdapter);
                        } catch (InstantiationException | IllegalAccessException
                                | IllegalArgumentException
                                | InvocationTargetException | NoSuchMethodException
                                | SecurityException e) {
                            ConsoleUtils.displayError(targetPlatform.name() + " platform not supported !");
                        }
                        if (newAdapter != null) {
                            lazyTypes.add(newAdapter);
                        }
                    }
                }
            }
            super.registerAdapters(lazyTypes);
        } else {
            // If not override... relay all core adapter.
            super.registerAdapters(adapters);
        }
    }

    /**
     * Get typed bundle adapter available for this bundle.
     * @return Array of typed adapter
     */
    @SuppressWarnings("unchecked")
    public ArrayList<T> getBundleAdapters() {
        ArrayList<T> lazyTypes = new ArrayList<T>();
        Class<T> type = ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);

        ArrayList<IAdapter> baseAdapters = this.getAdapters();
        for (IAdapter baseAdapter : baseAdapters) {
            if (baseAdapter.getClass().equals(type)) {
                lazyTypes.add((T)baseAdapter);
            }
        }
        return lazyTypes;
    }

    /**
     * Set the lazy inheritance adapter mapping.<br/><br/>
     * if your adapter (of platform) override the adapter of the core.
     * Declare in this method the mapping between platform vs extended bundle adapter.<br />
     * 
     */
    public abstract void initBundleAdapter();
}
