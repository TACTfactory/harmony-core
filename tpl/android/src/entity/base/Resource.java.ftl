<@header?interpret />
package ${entity_namespace}.base;

import java.io.Serializable;

import org.joda.time.DateTime;

public interface Resource {
    /**
     * @return the resourceId
     */
    Integer getResourceId();

    /**
     * @param value the resourceId to set
     */
    void setResourceId(final Integer value);

    /**
     * @return the path
     */
    String getPath();

    /**
     * @param value the path to set
     */
    void setPath(final String value);
}