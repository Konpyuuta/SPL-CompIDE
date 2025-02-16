package ui.components;

import com.example.splcompiler.CodeAreaWithLineIndicator;
import commands.debugging.SPLDebuggingCommandClient;
import commands.debugging.SetBreakPointCommand;
import commands.editor.KeywordHighlightingCommand;
import commands.editor.UpdateFileCommand;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;
import org.reactfx.collection.ListModification;
import org.reactfx.value.Val;
import splprime.lexer.KeywordMap;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.beans.binding.Bindings.createBooleanBinding;

/**
 *
 * @author Maurice Amon
 */

public class DemoEditor extends StackPane {

    private Stage stage = new Stage();

    private static final String[] KEYWORDS = KeywordMap.map.keySet().toArray(new String[0]);

    private static DemoEditor demoEditor;

    private CodeArea codeArea;

    private Integer openedFileId = null;

    public  final ObservableList<Integer> olistValue = FXCollections.observableArrayList();

    public  final ListProperty<Integer> listValue = new SimpleListProperty<Integer>(olistValue);

    public final static List<Integer> breakPoints = new ArrayList<>();


    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"   // for whole text processing (text blocks)
            + "|" + "/\\*[^\\v]*" + "|" + "^\\h*\\*([^\\v]*|/)";  // for visible paragraph processing (line by line)

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[] {
            "\n" +
                    "// Input\n" +
                    "var n = 10;\n" +
                    "\n" +
                    "// Here we go\n" +
                    "var a = 0;\n" +
                    "var b = 1;\n" +
                    "\n" +
                    "var i = 1;\n" +
                    "while ( i <= n ) {\n" +
                    "print a ;\n" +
                    "var next = a + b ;\n" +
                    "a = b ;\n" +
                    "b = next ;\n" +
                    "i = i + 1;\n" +
                    "}\n"});


    public DemoEditor() {
        codeArea = new CodeArea();
        IntFunction<Node> numberFactory = LineNumberFactory.get(codeArea);
        IntFunction<Node> arrowFactory = new ManualArrowFactory2(listValue, olistValue);
        IntFunction<Node> graphicFactory = line -> {
            HBox hbox = new HBox(
                    numberFactory.apply(line),
                    arrowFactory.apply(line));
            hbox.setAlignment(Pos.CENTER_LEFT);
            return hbox;
        };
        codeArea.setParagraphGraphicFactory(graphicFactory);
        codeArea.setOnKeyTyped(new KeywordHighlightingCommand(codeArea));
        codeArea.getContent().textProperty().addListener(new UpdateFileCommand(openedFileId));
        codeArea.getVisibleParagraphs().addModificationObserver
                (
                        new VisibleParagraphStyler<>( codeArea, this::computeHighlighting )
                );
        final Pattern whiteSpace = Pattern.compile( "^\\s+" );
        codeArea.addEventHandler( KeyEvent.KEY_PRESSED, KE ->
        {
            if ( KE.getCode() == KeyCode.ENTER ) {
                int caretPosition = codeArea.getCaretPosition();
                int currentParagraph = codeArea.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher( codeArea.getParagraph( currentParagraph-1 ).getSegments().get( 0 ) );
                if ( m0.find() ) Platform.runLater( () -> codeArea.insertText( caretPosition, m0.group() ) );
            }
        });

        this.getChildren().add(new VirtualizedScrollPane<>(codeArea));
        this.setMinHeight(400);
    }

    public void setCode(Integer openedFileid, String code) {
        this.openedFileId = openedFileid;
        codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(100))
                .subscribe(ignore -> codeArea.setStyleSpans(0, DemoEditor.getInstance().computeHighlighting(codeArea.getText())));
        codeArea.replaceText(0, 0, code);

    }

    public static DemoEditor getInstance() {
        if (demoEditor == null) {
            demoEditor = new DemoEditor();
        }
        return demoEditor;
    }

    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private class VisibleParagraphStyler<PS, SEG, S> implements Consumer<ListModification<? extends Paragraph<PS, SEG, S>>>
    {
        private final GenericStyledArea<PS, SEG, S> area;
        private final Function<String,StyleSpans<S>> computeStyles;
        private int prevParagraph, prevTextLength;

        public VisibleParagraphStyler(GenericStyledArea<PS, SEG, S> area, Function<String, StyleSpans<S>> computeStyles )
        {
            this.computeStyles = computeStyles;
            this.area = area;
        }

        @Override
        public void accept( ListModification<? extends Paragraph<PS, SEG, S>> lm )
        {
            if ( lm.getAddedSize() > 0 ) Platform.runLater( () ->
            {
                int paragraph = Math.min( area.firstVisibleParToAllParIndex() + lm.getFrom(), area.getParagraphs().size()-1 );
                String text = area.getText( paragraph, 0, paragraph, area.getParagraphLength( paragraph ) );

                if ( paragraph != prevParagraph || text.length() != prevTextLength )
                {
                    if ( paragraph < area.getParagraphs().size()-1 )
                    {
                        int startPos = area.getAbsolutePosition( paragraph, 0 );
                        area.setStyleSpans( startPos, computeStyles.apply( text ) );
                    }
                    prevTextLength = text.length();
                    prevParagraph = paragraph;
                }
            });
        }
    }

    public String getText() {
        return codeArea.getText();
    }
}

class ManualArrowFactory2 implements IntFunction<Node> {

    private final ListProperty<Integer> shownLines;

    private final ObservableList<Integer> olistValue;

    public ManualArrowFactory2(ListProperty<Integer> shownLine, ObservableList<Integer> olistValue) {
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
