package commands;

/** Interface for all commands ..
 *
 * @author Maurice Amon
 */
public interface Command {

    void execute();

    void undo();
}
