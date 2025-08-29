package gichat.ui;

import gichat.task.Task;
import gichat.task.TaskList;

import java.util.Scanner;

/**
 * Handles instruction with the user
 */
public class Ui {
    private Scanner scanner;
    private static final String BORDER ="________________________________________________";

    /**
     * Construct the new Ui instance
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Show welcome message to user
     */
    public void showWelcome() {
        System.out.println(BORDER);
        System.out.println("Hello I'm gichat.GiChat \nWhat you want");
        System.out.println(BORDER);
    }

    /**
     * Show Goodbye message to user
     */
    public void showGoodbye() {
        System.out.println(BORDER);
        System.out.println("Bye. Hope to see you again!");
        System.out.println(BORDER);
    }

    /**
     * Reads a command from user
     *
     * @return The user's input command
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Shows the list of tasks to user
     *
     * @param taskList The task list to display
     */
    public void showTasksList(TaskList taskList) {
        System.out.println(BORDER);
        if (taskList.isEmpty()) {
            System.out.println("Wah so free ah you, got no tasks to do");
            System.out.println(BORDER);
        } else {
            for (int i = 0; i < taskList.getSize(); i++) {
                System.out.println((i + 1) + ". " + taskList.getTask(i));
            }
            System.out.println(BORDER);
        }
    }

    /**
     * Show a task add confirmation
     *
     * @param task Task that was added
     * @param taskCount The total number of tasks in the list
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(BORDER);
        System.out.println("Roger, added the task");
        System.out.println("   " + task);
        System.out.println("Jialat, you have " + taskCount + " tasks in your list");
        System.out.println(BORDER);
    }

    /**
     * Shows a task deletion confirmation
     *
     * @param task The task that is to be deleted
     * @param taskCount The total number of tasks left in the list
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(BORDER);
        System.out.println("Orh, I removed the task");
        System.out.println(task);
        System.out.println("Now you are left with " + taskCount + " tasks in your list");
        System.out.println(BORDER);
    }

    /**
     * Shows a task marked as done confirmation
     *
     * @param task The task that has been marked
     */
    public void showTaskMarked(Task task) {
        System.out.println(BORDER);
        System.out.println("OKAY LA, being productive I see.");
        System.out.println("I helped marked it for you.");
        System.out.println(task);
        System.out.println(BORDER);
    }

    /**
     * Shows a task unmarked confirmation
     *
     * @param task The task that has been unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(BORDER);
        System.out.println("oh... I have unchecked the task you lazy bum!");
        System.out.println(task);
        System.out.println(BORDER);
    }

    /**
     * Shows all the tasks found matching the keyword
     *
     * @param foundTasks A Task List of tasks that matches the keyword
     */
    public void showTaskFound(TaskList foundTasks) {
        System.out.println(BORDER);
        if (foundTasks.isEmpty()) {
            System.out.println("Erm.. I can't find any tasks with that keyword leh");
        } else {
            System.out.println("These are the tasks I could find");
            for (int i = 0; i < foundTasks.getSize(); i++) {
                System.out.println((i+1) + "." + foundTasks.getTask(i));
            }
        }
    }
    /**
     * Shows an error message to the user
     *
     * @param message Message to be shown to user
     */
    public void showError(String message) {
        System.out.println(BORDER);
        System.out.println(message);
        System.out.println(BORDER);
    }

    /**
     * Shows a message to the user
     *
     * @param message Message to be shown to user
     */
    public void showMessage(String message) {
        System.out.println(BORDER);
        System.out.println(message);
        System.out.println(BORDER);
    }

}
