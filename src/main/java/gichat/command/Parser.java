package gichat.command;
// Handles the parsing of user commands

public class Parser {

    public static Command parse(String fullMessage) {
        String[] parts = fullMessage.split(" ", 2);
        String commandWord = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        switch (commandWord) {
        case "bye":
            return new Command(CommandType.BYE);
        case "list":
            return new Command(CommandType.LIST);
        case "mark":
            return new Command(CommandType.MARK, rest);
        case "unmark":
            return new Command(CommandType.UNMARK, rest);
        case "todo":
            return new Command(CommandType.TODO, rest);
        case "deadline":
            return new Command(CommandType.DEADLINE, rest);
        case "event":
            return new Command(CommandType.EVENT, rest);
        case "delete":
            return new Command(CommandType.DELETE, rest);
        default:
            return new Command(CommandType.UNKNOWN);
        }
    }

    // parse a todo command and return task description
    public static String parseTodo(String arguments) throws IllegalArgumentException {
        if (arguments.trim().isEmpty()) {
            throw new IllegalArgumentException("Eh can you give a valid todo task");
        }
        return arguments.trim();
    }

    // parses a deadline command and return the description and the deadline
    public static String[] parseDeadline(String arguments) throws IllegalArgumentException {
        String[] parts = arguments.split("/by");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Hey specify the deadline with /by");
        }
        return new String[] {parts[0].trim(), parts[1].trim()};
    }

    // parses an event deadline command and return the description, start time and end time
    public static String[] parseEvent(String arguments) throws IllegalArgumentException {
        String[] parts = arguments.split("/from");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Hey specify the event with /from and /to");
        }
        String[] timeline = parts[1].split("/to");
        if (timeline.length < 2) {
            throw new IllegalArgumentException("Hey specify the event with /from and /to");
        }
        return new String[] {parts[0].trim(), timeline[0].trim(), timeline[1].trim()};
    }

    public static int parseTaskNumber(String arguments) throws IllegalArgumentException{
        if (arguments.trim().isEmpty()) {
            throw new IllegalArgumentException("You need to give me a task number");
        }
        try {
            int taskNumber = Integer.parseInt(arguments.trim());
            return taskNumber - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hais you need to enter a task number, not some gibberish");
        }
    }

}
