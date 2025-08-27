enum CommandType {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN
}
public class Command {
    private CommandType type;
    private String arguments;

    public Command(CommandType type) {
        this.type = type;
        this.arguments = "";
    }

    public Command(CommandType type, String arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    public CommandType getType() {
        return this.type;
    }

    public String getArguments() {
        return this.arguments;
    }

    public boolean isExit() {
        return this.type.equals(CommandType.BYE);
    }
}
