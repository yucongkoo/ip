package duke;

import command.Command;
import command.DeadlineCommand;
import command.DeleteCommand;
import command.EmptyCommand;
import command.EventCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.MarkCommand;
import command.TodoCommand;
import command.UnmarkCommand;
import task.Deadline;
import task.Event;
import task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Represents a parser class that will be in charge of parsing user commands
 * for Duke.
 */
public class Parser {
    private static final String FLAG_BYE = "bye";
    private static final String FLAG_LIST = "list";
    private static final String FLAG_MARK = "mark";
    private static final String FLAG_UNMARK = "unmark";
    private static final String FLAG_TODO = "todo";
    private static final String FLAG_DEADLINE = "deadline";
    private static final String FLAG_EVENT = "event";
    private static final String FLAG_DELETE = "delete";
    private static final String FLAG_FIND = "find";

    /**
     * Parses the input passed in and return the respective command.
     *
     * @param input The user input.
     * @return The command represented by input.
     * @throws DukeException If the command format is invalid or unrecognized.
     */
    public static Command parseCommand(String input) throws DukeException {
        input = input.trim();

        // skip past empty lines
        if (input.isEmpty()) {
            return new EmptyCommand();
        }

        String[] parts = input.split("\\s+", 2);
        String command = parts[0];
        String value = parts.length >= 2 ? parts[1].trim() : "";

        switch (command) {
            case FLAG_BYE:
                return parseByeCommand(value);
            case FLAG_LIST:
                return parseListCommand(value);
            case FLAG_MARK:
                return parseMarkCommand(value);
            case FLAG_UNMARK:
                return parseUnmarkCommand(value);
            case FLAG_TODO:
                return parseTodoCommand(value);
            case FLAG_DEADLINE:
                return parseDeadlineCommand(value);
            case FLAG_EVENT:
                return parseEventCommand(value);
            case FLAG_DELETE:
                return parseDeleteCommand(value);
            case FLAG_FIND:
                return parseFindCommand(value);
            default:
                throw new DukeException("Oops!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static Command parseByeCommand(String value) throws DukeException{
        if (!value.isEmpty()) {
            throw new DukeException("Oops!!! The bye command should not be followed by any description");
        }
        return new ExitCommand();
    }

    private static Command parseListCommand(String value) throws DukeException {
        if (!value.isEmpty()) {
            throw new DukeException("Oops!!! The list command should not be followed by any description");
        }
        return new ListCommand();
    }

    private static Command parseMarkCommand(String value) throws DukeException {
        Scanner tempSc = new Scanner(value);

        if (!tempSc.hasNextInt()) {
            tempSc.close();
            throw new DukeException("Oops!!! Invalid argument of a mark command");
        }

        int index = tempSc.nextInt();
        if (tempSc.hasNext()) {
            // means invalid formatting for done command
            tempSc.close();
            throw new DukeException("Oops!!! Invalid argument of a mark command");
        }
        tempSc.close();

        return new MarkCommand(index);
    }

    private static Command parseUnmarkCommand(String value) throws DukeException {
        Scanner tempSc = new Scanner(value);

        if (!tempSc.hasNextInt()) {
            tempSc.close();
            throw new DukeException("Oops!!! Invalid argument of an unmark command");
        }

        int index = tempSc.nextInt();
        if (tempSc.hasNext()) {
            // means invalid formatting for done command
            tempSc.close();
            throw new DukeException("Oops!!! Invalid argument of an unmark command");
        }

        return new UnmarkCommand(index);
    }

    private static Command parseTodoCommand(String value) throws DukeException {
        if (value.isEmpty()) {
            throw new DukeException("Oops!!! The description of a todo task cannot be empty");
        }

        return new TodoCommand(value);
    }

    private static Command parseDeadlineCommand(String value) throws DukeException {
        String[] tempParts = value.split("/by");

        // did not provide the /by argument
        if (tempParts.length < 2) {
            throw new DukeException("Oops!!! You forgot to provide a deadline for the deadline task");
        }

        String taskName = tempParts[0].trim();
        String deadline = tempParts[1].trim();
        if (taskName.isEmpty()) {
            throw new DukeException("Oops!!! The description of a deadline task cannot be empty");
        }
        if (deadline.isEmpty()) {
            throw new DukeException("Oops!!! You forgot to provide a deadline for the deadline task");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new DukeException("Oops!! the date format of deadline is incorrect, "
                    + "please use the format yyyy-mm-dd");
        }

        return new DeadlineCommand(taskName, date);
    }

    private static Command parseEventCommand(String value) throws DukeException {
        String[] tempParts = value.split("/from|/to");

        // did not provide the /from /to arguments
        if (tempParts.length < 3) {
            throw new DukeException("Oops!!! Please provide a proper period for the event task");
        }

        String taskName = tempParts[0].trim();
        String start = tempParts[1].trim();
        String end = tempParts[2].trim();
        if (taskName.isEmpty()) {
            throw new DukeException("Oops!!! The description of an event task cannot be empty");
        }
        if (start.isEmpty() || end.isEmpty()) {
            throw new DukeException("Oops!!! Please provide a proper period for the event task");
        }

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
            if (endDate.isBefore(startDate)) {
                throw new DukeException("Oops!!! End date of an event should "
                        + "not be earlier than the start date.");
            }
        } catch (DateTimeParseException e) {
            throw new DukeException("Oops!! the date format of event is incorrect, "
                    + "please use the format yyyy-mm-dd");
        }

        return new EventCommand(taskName, startDate, endDate);
    }

    private static Command parseDeleteCommand(String value) throws DukeException {
        Scanner tempSc = new Scanner(value);
        if (!tempSc.hasNextInt()) {
            tempSc.close();
            throw new DukeException("Oops!!! Invalid argument of a delete command");
        }

        int index = tempSc.nextInt();
        if (tempSc.hasNext()) {
            // means invalid formatting for done command
            tempSc.close();
            throw new DukeException("Oops!!! Invalid argument of a delete command");
        }
        tempSc.close();

        return new DeleteCommand(index);
    }

    private static Command parseFindCommand(String value) throws DukeException {
        if (value.isEmpty()) {
            throw new DukeException("Oops!!! Please provide an input to find");
        }

        return new FindCommand(value);
    }
}
