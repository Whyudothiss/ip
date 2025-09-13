package gichat.command;

/**
 * Represents the information for editing a task
 */
public class EditInfo {
    private int taskIndex;
    private String description;
    private String by;
    private String from;
    private String to;

    /**
     * Constructs an EditInfo instance with the task index
     * @param taskIndex
     */
    public EditInfo(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public int getTaskIndex() {
        return this.taskIndex;
    }

    public String getDescription() {
        return this.description;
    }

    public String getBy() {
        return this.by;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBy(String by){
        this.by = by;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Checks if any edist have been made
     * @return True if at least one field has been set for editing
     */
    public boolean hasAnyEdits() {
        return this.description != null || this.by != null || this.from != null || this.to != null;
    }

}
