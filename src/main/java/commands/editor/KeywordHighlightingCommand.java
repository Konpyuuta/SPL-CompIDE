package commands.editor;

import commands.Command;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.reactfx.Subscription;
import ui.components.editor.StandardEditor;

import java.time.Duration;

public class KeywordHighlightingCommand implements Command, EventHandler<KeyEvent> {

    private CodeArea codeArea;

    public KeywordHighlightingCommand(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        Subscription subscription = codeArea
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(100))
                .subscribe(ignore -> codeArea.setStyleSpans(0, StandardEditor.getInstance().computeHighlighting(codeArea.getText())));
    }
}
