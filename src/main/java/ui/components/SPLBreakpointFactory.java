package ui.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import org.reactfx.value.Val;

import java.util.function.IntFunction;

public class SPLBreakpointFactory {
}

class ManualArrowFactory implements IntFunction<Node> {

    private final IntegerProperty lineNumberProperty;

    public ManualArrowFactory(IntegerProperty lineNumberProperty) {
        this.lineNumberProperty = lineNumberProperty;
    }
    @Override
    public Node apply(int lineNumber) {
        Circle circle = new Circle();
        circle.setFill(Color.RED);

        ObservableValue<Boolean> visible = Val.map(lineNumberProperty, sl -> sl.intValue()-1 == lineNumber);

        circle.visibleProperty().bind(
                Val.flatMap(circle.sceneProperty(), scene -> {
                    return scene != null ? visible : Val.constant(false);
                }));

        return circle;
    }
}