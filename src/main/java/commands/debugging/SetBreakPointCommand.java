package commands.debugging;

import commands.Command;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.BreakpointModel;


public class SetBreakPointCommand implements Command, EventHandler<MouseEvent> {


    private Integer lineNumber;

    public IntegerProperty lineValue;

    public final ListProperty<Integer> listValue;

    private final ObservableList<Integer> list;

    private Boolean isBreakpoint;

    private BreakpointModel breakpointModel = BreakpointModel.getInstance();


    public SetBreakPointCommand(Integer lineNumber, ListProperty<Integer> listValue, ObservableList<Integer> list,
                                Boolean isBreakPoint) {
        this.list = list;
        this.listValue = listValue;
        this.lineNumber = lineNumber;
        this.isBreakpoint = isBreakPoint;
    }

    @Override
    public void execute() {
        System.out.println("Zeilennummer: " + lineNumber);
        if(isBreakpoint) {
            int index = list.indexOf(lineNumber);
            if (index > -1) {
                list.remove(index);
            }
        } else {
            if (!list.contains(lineNumber)){
                list.add(lineNumber);
            }
        }
        breakpointModel.setBreakpointLineNumbers(list);
        //listValue.add(Integer.valueOf(lineNumber));
        //DemoEditor. breakPoints..add(Integer.valueOf(lineValue.get()));
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        execute();
    }
}
