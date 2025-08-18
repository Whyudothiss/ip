import java.util.ArrayList;
import java.util.Scanner;

public class GiChat {
    // Change to an array list of Task for level 3
    private static ArrayList<Task> tasks = new ArrayList<>();
    public static void main(String[] args) {
        String border = "______________________________________________";
        System.out.println(border);
        System.out.println("Hello I'm your GiChat \nWhat can I do for you");
        System.out.println(border);

        Scanner scanner = new Scanner(System.in);
        String line;

        // Scans the next line of input and check if the user says "bye"
        while (!(line = scanner.nextLine()).equals("bye")) {
            // Check if the user types out "List" if so call listTask else call addTask
            if (line.equals("list")) {
                listTasks(border);
            } else {
                addTask(line, border);
            }
        }
        System.out.println(border);
        System.out.println("Bye. Hope to see you again!");
        System.out.println(border);
    }

    // Used arraylist to track the task list so that it can be called at listTask()
    private static void addTask(String taskDescription, String barrier) {
        Task task = new Task(taskDescription);
        tasks.add(task);
        System.out.println(barrier);
        System.out.println("added: " + task);
        System.out.println(barrier);
    }
    // First check if array is empty, if not run through array then print out the tasks
    private static void listTasks(String barrier) {
        System.out.println(barrier);
        if (tasks.isEmpty()) {
            System.out.println("No tasks in your list :)");
            System.out.println(barrier);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
            System.out.println(barrier);
        }
    }

}
