<@header?interpret />
package ${project_namespace}.test.base;

import android.app.Service;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.ServiceTestCase;;


/** Base test abstract class for Service Test <br/>
 * <b><i>This class will be overwrited whenever
 * you regenerate the project with Harmony.</i></b>
 */
public abstract class TestServiceBase<T extends Service>
        extends ServiceTestCase<T> {

	/** The Mocked android {@link Context}. */
    protected TestContextMock testContextMock;

	protected TestServiceBase(Class<T> serviceClass) {
        super(serviceClass);
        this.testContextMock = new TestContextMock(this);
    }

	/* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.testContextMock.setUp();
    }
}
