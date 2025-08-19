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
            // split input into 2, one is command so either mark/unmark/list
            String[] parts = line.split(" ", 2);
            String command = parts[0];

            switch (command) {
                case "list":
                    listTasks(border);
                    break;
                case "mark":
                    if (parts.length > 1) {
                        markTask(parts[1], border, true);
                    } else {
                        System.out.println(border);
                        System.out.println("Woi you want me to mark which number, can say? E.g mark 1");
                        System.out.println(border);
                    }
                    break;
                case "unmark":
                    if (parts.length > 1) {
                        markTask(parts[1], border, false);
                    } else {
                        System.out.println(border);
                        System.out.println("wlao which one do you want to mark off, can say anot? E.g unmark 3");
                        System.out.println(border);
                    }
                    break;
                default:
                    addTask(line, border);
                    break;
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
        System.out.println("added: " + taskDescription);
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

    private static void markTask(String taskNumber, String barrier, boolean markDone) {}


}
