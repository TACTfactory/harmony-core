<@header?interpret />
package ${project_namespace}.test;

import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;

import ${project_namespace}.test.base.TestContextIsolatedBase;

/** Context isolated test class. */
public class TestContextIsolated extends TestContextIsolatedBase {

    /**
     * Constructor.
     * @param mockContentResolver {@link MockContentResolver}
     * @param targetContextWrapper {@link RenamingDelegatingContext}
     */
    public TestContextIsolated(MockContentResolver mockContentResolver,
            RenamingDelegatingContext targetContextWrapper) {
        super(mockContentResolver, targetContextWrapper);
    }

}
