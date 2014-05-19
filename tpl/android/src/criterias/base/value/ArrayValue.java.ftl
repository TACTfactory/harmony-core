<@header?interpret />
package ${project_namespace}.criterias.base.value;

import java.util.ArrayList;

/** An array Value.
 * (Use only with IN type criterias)
 * Example : SELECT * FROM table1 WHERE id IN (values[1], values[2], values[3]);
 */
public class ArrayValue extends CriteriaValue {
    /**
     * List of values.
     */
    private ArrayList<String> values = new ArrayList<String>();

    @Override
    public String toSQLiteString() {
        String valuesString = "(";
        String delimiter = "";
        for (final String value : this.values) {
            valuesString += delimiter + value;
            delimiter = ", ";
        }
        valuesString += ")";
        return valuesString;
    }

    @Override
    public String toSQLiteSelection() {
        String valuesString = "(";
        for (int i = 0; i < this.values.size(); i++) {
            valuesString += "?";
            if (i != this.values.size() - 1) {
                valuesString += ", ";
            } else {
                valuesString += ")";
            }
        }

        return valuesString;
    }

    @Override
    public void toSQLiteSelectionArgs(final ArrayList<String> array) {
        array.addAll(this.values);
    }



    /**
     * Get the Criteria value.
     * @return The Criteria's value
     */
    public ArrayList<String> getValues() {
        return this.values;
    }


    /**
     * Set the Criteria value.
     * @param value The new value to set
     */
    public void addValue(final String value) {
        this.values.add(value);
    }
}
