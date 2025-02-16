package commands;

/**
 * Null-Object Pattern ...
 *
 * @author Maurice Amon
 */
public class DoNothingCommand implements Command {
    @Override
    public void execute() {
        // Do nothing ...
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
