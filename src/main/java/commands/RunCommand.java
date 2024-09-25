package commands;

import splprime.SplPrime;
import ui.MainWindow;
import ui.StyleFactory;

public class RunCommand implements Command{
    @Override
    public void execute() {
        String code = MainWindow.getInstance(new StyleFactory()).editor.getText();
        SplPrime.run(code);
    }
}
