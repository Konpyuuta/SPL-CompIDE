package ui.components.execution;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Maurice Amon
 */

public class SPLVariable {

    private final SimpleStringProperty identifier;

    private final SimpleStringProperty value;

    private final SimpleStringProperty type;

    public SPLVariable(String type, String identifier, String value) {
        this.identifier = new SimpleStringProperty(identifier);
        this.value = new SimpleStringProperty(value);
        this.type = new SimpleStringProperty(type);
    }

    public SimpleStringProperty getIdentifierProperty() {
        return identifier;
    }

    public SimpleStringProperty getValueProperty() {
        return value;
    }

    public SimpleStringProperty getTypeProperty() {
        return type;
    }
}
