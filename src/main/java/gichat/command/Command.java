package gichat.command;

/**
 * Represent a command with its type and arguments
 */
public class Command {

    private CommandType type;
    private String arguments;

    /**
     * Constructs a command with the specified type
     *
     * @param type The type of the commmand
     */
    public Command(CommandType type) {
        this.type = type;
        this.arguments = "";
    }

    /**
     * Constructs a command with the specified type and arguments
     *
     * @param type The type of command
     * @param arguments The arguments for the command
     */
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

    /**
     * Checks if this is an exit command
     *
     * @return True if this is a bye command, false otherwise
     */
    public boolean isExit() {
        return this.type.equals(CommandType.BYE);
    }
}
