package splprime.interpreter;

import java.util.HashMap;

public class Environment extends HashMap<String, Object> {


    public void define(String name, Object value) {
        this.put(name, value);
    }
}
