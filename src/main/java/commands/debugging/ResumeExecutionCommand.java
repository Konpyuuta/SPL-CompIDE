package commands.debugging;

import commands.Command;

/**
 *
 * @author Maurice Amon
 */

public class ResumeExecutionCommand implements Command {

    public ResumeExecutionCommand() {

    }

    @Override
    public void execute() {
        DebugCommand.resumeExecution();
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
