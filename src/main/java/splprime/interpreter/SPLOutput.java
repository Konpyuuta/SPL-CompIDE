package splprime.interpreter;

import splprime.Observable;

/**
 * Model class for managing the consoles Output
 */

public class SPLOutput extends Observable {

    private String outputText = "";

    private static SPLOutput splOutput = null;

    private SPLOutput() {

    }

    public static SPLOutput getInstance() {
        if(splOutput == null) {
            splOutput = new SPLOutput();
        }
        return splOutput;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
        notifyObservers(this.outputText);
    }

    public void addOutputText(String outputText) {
        this.outputText += outputText;
        notifyObservers(this.outputText);
    }

    public String getOutputText() {
        return this.outputText;
    }

    public void clearOutputText() {
        this.outputText = "";
        notifyObservers(this.outputText);
    }

}
