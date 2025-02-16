package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 *
 * @author Maurice Amon
 */

public class BreakpointModel {

    private static BreakpointModel breakpointModel = null;

    private ArrayList<Integer> breakpointLineNumber = new ArrayList<>();

    private BreakpointModel() {

    }

    public static BreakpointModel getInstance() {
        if (breakpointModel == null) {
            breakpointModel = new BreakpointModel();
        }
        return breakpointModel;
    }

    public void setBreakpointLineNumbers(ObservableList<Integer> breakpointLineNumbers) {
        this.breakpointLineNumber.clear();
        for(int i = 0; i < breakpointLineNumbers.size(); i++) {
            this.breakpointLineNumber.add(breakpointLineNumbers.get(i));
        }
    }

    public ArrayList<Integer> getBreakpointLineNumbers() {
        return breakpointLineNumber;
    }
}
