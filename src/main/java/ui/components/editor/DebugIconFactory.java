package ui.components.editor;

import commands.debugging.SetBreakPointCommand;
import javafx.beans.property.ListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.reactfx.value.Val;

import java.util.function.IntFunction;

/**
 *
 * @author Maurice Amon
 */

public class DebugIconFactory implements IntFunction<Node> {

    private final ListProperty<Integer> shownLines;

    private final ObservableList<Integer> olistValue;

    public DebugIconFactory(ListProperty<Integer> shownLine, ObservableList<Integer> olistValue) {
        this.shownLines = shownLine;
        this.olistValue = olistValue;
    }

    @Override
    public Node apply(int lineIndex) {
        StackPane stackPane = new StackPane();
        Circle circle = new Circle(5);
        circle.setFill(Color.RED);
        circle.setStroke(Color.DARKRED);
        circle.setStrokeWidth(1);
        Rectangle rectangle = new Rectangle(20,20);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setCursor(Cursor.HAND);
        rectangle.setOnMouseClicked(new SetBreakPointCommand(lineIndex+1, shownLines, olistValue, false));

        stackPane.getChildren().addAll(rectangle, circle);
        circle.setOnMouseClicked(new SetBreakPointCommand(lineIndex+1, shownLines, olistValue, true));

        circle.setCursor(Cursor.HAND);
        ObservableValue<Boolean> visible = Val.map(shownLines, sl -> sl.contains(lineIndex+1));

        circle.visibleProperty().bind(
                Val.flatMap(circle.sceneProperty(), scene -> {
                    return scene != null ? visible : Val.constant(false);
                }));

        return stackPane;
    }
}