package com.example.splcompiler;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.TwoDimensional;
import org.reactfx.value.Val;

import java.util.function.IntFunction;

public class CodeAreaWithLineIndicator extends Application {
    CodeArea codeArea;
    TextField textField;
    public static final IntegerProperty lineValue = new SimpleIntegerProperty(-1) ;
    public static final ObservableList<Integer> olistValue = FXCollections.observableArrayList();
    public static final ListProperty<Integer> listValue = new SimpleListProperty<Integer>(olistValue);


/*    public final int getValue() {
        return value.get();
    }*/
/*    public final void setValue(int value) {
        this.value.set(value);
    }*/

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        codeArea = new CodeArea();
        codeArea.replaceText(0,0,"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        codeArea.setPrefHeight(400);
        IntFunction<Node> numberFactory = LineNumberFactory.get(codeArea);
        IntFunction<Node> arrowFactory = new MultiBreakPointFactory(listValue);
        IntFunction<Node> graphicFactory = line -> {
            HBox hbox = new HBox(
                    numberFactory.apply(line),
                    arrowFactory.apply(line));
            hbox.setAlignment(Pos.CENTER_LEFT);

            return hbox;
        };
        codeArea.setParagraphGraphicFactory(graphicFactory);
        VBox vbox = new VBox();
        textField = new TextField();
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int newValue = Integer.parseInt(textField.getText());
                olistValue.add(newValue);

            }
        });
        Button button = new Button("Clear");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                olistValue.clear();

            }
        });

        vbox.getChildren().addAll(textField, button, codeArea);

        Scene scene = new Scene(vbox, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class MultiBreakPointFactory implements IntFunction<Node> {

        private final ListProperty<Integer> shownLines;

        public MultiBreakPointFactory(ListProperty<Integer> shownLine) {
            this.shownLines = shownLine;
        }
        @Override
        public Node apply(int lineIndex) {
            StackPane stackPane = new StackPane();
            Circle circle = new Circle(10.0, 10.0, 6.0, Color.RED);
            Rectangle rectangle = new Rectangle(20,20);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setCursor(Cursor.HAND);
            rectangle.setOnMouseClicked(me->{
                if (!olistValue.contains(lineIndex+1)){
                    olistValue.add(lineIndex+1);
                }
            });
            stackPane.getChildren().addAll(rectangle, circle);
            circle.setOnMouseClicked(me->{
                int index = olistValue.indexOf(lineIndex+1);
                if (index>-1)
                    olistValue.remove(index);
            });
            circle.setCursor(Cursor.HAND);
            ObservableValue<Boolean> visible = Val.map(shownLines, sl -> sl.contains(lineIndex+1));

            circle.visibleProperty().bind(
                    Val.flatMap(circle.sceneProperty(), scene -> {
                        return scene != null ? visible : Val.constant(false);
                    }));

            return stackPane;
        }
    }
}