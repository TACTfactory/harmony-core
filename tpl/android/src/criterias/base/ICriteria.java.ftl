<@header?interpret />
package ${project_namespace}.criterias.base;

import java.util.ArrayList;

/**
 * Interface for storing criterias in criterias.
 */
public interface ICriteria {
    /**
     * Convert the criteria to a SQLite String.
     * @return The SQLite String representation of the criteria. ex : <br />
     * "(price > 15.0)"
     */
    String toSQLiteString();

    /**
     * Convert the criteria to a SQLite Selection String.
     * @return The SQLite Selection String representation of the criteria.<br />
     * ex : "(price > ?)"
     */
    String toSQLiteSelection();

    /**
     * Convert the criteria to a SQLite Selection Args array.
     * @param array The SQLite SelectionArgs array of String . ex : <br />
     * ["15"]
     */
    void toSQLiteSelectionArgs(ArrayList<String> array);
}
