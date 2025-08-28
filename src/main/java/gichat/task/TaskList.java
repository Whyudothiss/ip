package gichat.task;

import java.util.ArrayList;

/**
 * Represents a list of task and provides operations to manage them
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given tasks
     *
     * @param tasks The initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task into the array list of tasks
     *
     * @param task The task that is to be added into the task list
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Deletes a task from teh array list
     *
     * @param index The index of the task to delete (0-index)
     * @return The deleted task
     * @throws IndexOutOfBoundsException If the index is invalid
     */
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index out of bounds");
        }
        return tasks.remove(index);
    }

    /**
     * Gets a task from the task list
     *
     * @param index The index of the task (0-indexed)
     * @return The task at the specified index
     * @throws IndexOutOfBoundsException If the index is invalid
     */
    public Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index out of bounds");
        }
        return tasks.get(index);
    }

    public int getSize() {
        return this.tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
