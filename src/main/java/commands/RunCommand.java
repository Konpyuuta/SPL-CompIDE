package commands;

import splprime.SplPrime;
import splprime.interpreter.SPLOutput;
import ui.MainWindow;
import ui.StyleFactory;

/** Command is responsible to run the program typed in the editor ..
 *
 * @author Maurice Amon
 */

public class RunCommand implements Command {
    @Override
    public void execute() {
        SPLOutput.getInstance().addObserver(MainWindow.getInstance(new StyleFactory()));
        String code = MainWindow.getInstance(new StyleFactory()).editor.getText();
        SPLOutput.getInstance().clearOutputText();
        SplPrime.run(code);
    }
}
