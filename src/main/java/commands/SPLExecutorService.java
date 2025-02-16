package commands;

import java.util.ArrayList;

/**
 *
 * @author Maurice Amon
 */

public class SPLExecutorService {

    private static ArrayList<Command> executedCommands = new ArrayList<>();

    private static Integer pointer = 0;

    private static SPLExecutorService instance;

    private Command command;

    public SPLExecutorService(Command command) {
        this.command = command;
    }

    public void runExecution() {
        command.execute();
        pointer++;
        executedCommands.add(command);
    }

    public void undoExecution() {
        executedCommands.get(pointer).undo();
        pointer--;
    }
}
