package command;

import duke.DiskManager;
import duke.DukeException;
import duke.TaskManager;
import duke.Ui;

public class MarkCommand extends Command {
    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskManager taskManager, DiskManager diskManager, Ui ui) throws DukeException {
        ui.printOutput(taskManager.markTask(index, true));
        diskManager.saveToDisk(taskManager);
    }
}