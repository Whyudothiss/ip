package gichat.task;

/**
 * Represents a task
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task instance with the given description
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public boolean getStatus() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Mark the task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmark the task
     */
    public void uncheck() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}
