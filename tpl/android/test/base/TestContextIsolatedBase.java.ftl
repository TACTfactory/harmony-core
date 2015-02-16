<@header?interpret />
package ${project_namespace}.test.base;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;

/** Context isolated base test class. */
public class TestContextIsolatedBase extends IsolatedContext {

    /**
     * Constructor.
     * @param mockContentResolver {@link MockContentResolver}
     * @param targetContextWrapper {@link RenamingDelegatingContext}
     */
    public TestContextIsolatedBase(MockContentResolver mockContentResolver,
            RenamingDelegatingContext targetContextWrapper) {
        super(mockContentResolver, targetContextWrapper);
    }
    
    @Override
    public Object getSystemService(String name) {
        return this.getSystemService(name);
    }
    
    @Override
    public void sendOrderedBroadcast(
            Intent intent, String receiverPermission) {
        this.sendOrderedBroadcast(intent, receiverPermission);
    }
    
    @Override
    public Intent registerReceiver(
            BroadcastReceiver receiver,
            IntentFilter filter) {
        return this.registerReceiver(receiver, filter);
    }
}
