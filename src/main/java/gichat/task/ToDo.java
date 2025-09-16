package gichat.task;

/**
 * A type of task with only the description
 */
public class ToDo extends Task {

    /**
     * Constructs an instance of a task with the given description
     *
     * @param description The description of the todo task
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T][" + this.getStatusIcon() + "] " + description;
    }
}
