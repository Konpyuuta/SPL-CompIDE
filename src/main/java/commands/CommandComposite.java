package commands;

import java.util.ArrayList;

public class CommandComposite implements Command {

    private ArrayList<Command> commands;

    public CommandComposite() {
        commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void removeCommand(Command command) {
        commands.remove(command);
    }

    @Override
    public void execute() {
        for(Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
