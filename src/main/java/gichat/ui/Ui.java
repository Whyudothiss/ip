package gichat.ui;

import gichat.task.Task;
import gichat.task.TaskList;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private static final String BORDER ="________________________________________________";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    // show welcome message
    public void showWelcome() {
        System.out.println(BORDER);
        System.out.println("Hello I'm gichat.GiChat \nWhat you want");
        System.out.println(BORDER);
    }

    // show goodbye message
    public void showGoodbye() {
        System.out.println(BORDER);
        System.out.println("Bye. Hope to see you again!");
        System.out.println(BORDER);
    }

    // read user command
    public String readCommand() {
        return scanner.nextLine();
    }
    // show list of tasks to user
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

    // show task added confirmation
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(BORDER);
        System.out.println("Roger, added the task");
        System.out.println("   " + task);
        System.out.println("Jialat, you have " + taskCount + " tasks in your list");
        System.out.println(BORDER);
    }

    // show task deleted confirmatino
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(BORDER);
        System.out.println("Orh, I removed the task");
        System.out.println(task);
        System.out.println("Now you are left with " + taskCount + " tasks in your list");
        System.out.println(BORDER);
    }

    public void showTaskMarked(Task task) {
        System.out.println(BORDER);
        System.out.println("OKAY LA, being productive I see.");
        System.out.println("I helped marked it for you.");
        System.out.println(task);
        System.out.println(BORDER);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(BORDER);
        System.out.println("oh... I have unchecked the task you lazy bum!");
        System.out.println(task);
        System.out.println(BORDER);
    }

    public void showError(String message) {
        System.out.println(BORDER);
        System.out.println(message);
        System.out.println(BORDER);
    }

    public void showMessage(String message) {
        System.out.println(BORDER);
        System.out.println(message);
        System.out.println(BORDER);
    }

}
