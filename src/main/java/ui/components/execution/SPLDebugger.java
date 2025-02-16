package ui.components.execution;

import commands.debugging.SPLDebuggingCommandClient;
import javafx.scene.layout.VBox;

/**
 *
 * @author Maurice Amon
 */

public class SPLDebugger extends VBox {

    private TypeInspectorUI typeInspectorUI;

    private ResumeExecutionButton resumeExecutionButton;

    private SPLDebuggingCommandClient splDebuggingCommandClient;

    public SPLDebugger() {
        typeInspectorUI = new TypeInspectorUI();
        resumeExecutionButton = new ResumeExecutionButton();
        splDebuggingCommandClient = new SPLDebuggingCommandClient();
        initializeActions();
        initialieComponents();
    }

    private void initialieComponents() {
        this.getChildren().addAll(resumeExecutionButton, typeInspectorUI);
    }

    private void initializeActions() {
        resumeExecutionButton.setOnAction(splDebuggingCommandClient);
    }

    public TypeInspectorUI getTypeInspectorUI() {
        return typeInspectorUI;
    }

    public ResumeExecutionButton getResumeExecutionButton() {
        return resumeExecutionButton;
    }
}
