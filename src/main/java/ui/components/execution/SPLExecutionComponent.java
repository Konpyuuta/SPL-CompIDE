package ui.components.execution;

import javafx.scene.layout.HBox;

/**
 *
 * @author Maurice Amon
 */

public class SPLExecutionComponent extends HBox {

    private SPLOutput output = new SPLOutput();

    private SPLDebugger debugger = new SPLDebugger();

    public SPLExecutionComponent() {
        this.getChildren().addAll(output, debugger);
    }

    public SPLOutput getOutput() {
        return output;
    }

    public SPLDebugger getDebugger() {
        return debugger;
    }
}
