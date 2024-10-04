package splprime.interpreter;

import java.util.HashMap;

/** Provides the environment for the lifetime of all variables during the execution of the program ..
 *
 * @author Maurice Amon
 */

public class Environment extends HashMap<String, Object> {


    public void define(String name, Object value) {
        this.put(name, value);
    }
}
