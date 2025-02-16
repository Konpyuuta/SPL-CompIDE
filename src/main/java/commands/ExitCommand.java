package commands;

/** Command to exit the application ..
 *
 * @author Maurice Amon
 */
public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
