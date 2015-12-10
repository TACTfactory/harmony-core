<@header?interpret />
package ${entity_namespace}.base;

import java.io.Serializable;

import org.joda.time.DateTime;

public interface Resource {
    /**
     * Get the EntityBase server id.
     * @return The EntityBase server id
     */
    Integer getServerId();

    /**
     * @return the path
     */
    String getPath();

    /**
     * @param value the path to set
     */
    void setPath(final String value);

    /**
     * @return the local path
     */
    String getLocalPath();

    /**
     * @param value the local path to set
     */
    void setLocalPath(final String value);
}