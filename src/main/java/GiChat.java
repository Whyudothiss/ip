import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GiChat {
    // Change to an array list of Task for level 3
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final String DATA_FILE = "tasks.txt";
    public static void main(String[] args) {
        String border = "________________________________________________";
        System.out.println(border);
        System.out.println("Hello I'm GiChat \nWhat you want");
        System.out.println(border);

        // need to load the tasks that are saved locally in the computer
        // then add it into the array tasks
        loadTasks();

        Scanner scanner = new Scanner(System.in);
        String line;

        // Scans the next line of input and check if the user says "bye"
        while (!(line = scanner.nextLine()).equals("bye")) {
            // split input into 2, one is command so either mark/unmark/list
            String[] parts = line.split(" ", 2);
            String command = parts[0];

            // check which command user inputs so the bot knows how to handle it
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
                case "todo":
                    if (parts.length > 1) {
                        addTodo(parts[1], border);
                    } else {
                        System.out.println(border);
                        System.out.println("Eh can you give a valid todo task");
                        System.out.println(border);
                    }
                    break;
                case "deadline":
                    if (parts.length > 1) {
                        addDeadline(parts[1], border);
                    } else {
                        System.out.println(border);
                        System.out.println("Eh can you give a valid deadline task");
                        System.out.println(border);
                    }
                    break;
                case "event":
                    if (parts.length > 1) {
                        addEvent(parts[1], border);
                    } else {
                        System.out.println(border);
                        System.out.println("Eh can you give a valid Event task");
                        System.out.println(border);
                    }
                    break;
                case "delete":
                    if (parts.length > 1) {
                        deleteTask(parts[1], border);
                    } else {
                        System.out.println(border);
                        System.out.println("You need to give me a task number");
                        System.out.println(border);
                    }
                    break;
                default:
                    System.out.println(border);
                    System.out.println("Erm... you need to give me a valid command...");
                    System.out.println("Can list, mark, unmark, todo, deadline, event");
                    System.out.println(border);
                    break;
            }
        }
        saveTasks();

        System.out.println(border);
        System.out.println("Bye. Hope to see you again!");
        System.out.println(border);
    }

    // Used arraylist to track the task list so that it can be called at listTask()
    /* Don't actually need this method anymore after level-4 since a task is one of
    *  the 3 subclasses of task*/
    /*private static void addTask(String taskDescription, String barrier) {
        Task task = new Task(taskDescription);
        tasks.add(task);
        System.out.println(barrier);
        System.out.println("added: " + taskDescription);
        System.out.println(barrier);
    }*/
    // First check if array is empty, if not run through array then print out the tasks
    private static void listTasks(String barrier) {
        System.out.println(barrier);
        if (tasks.isEmpty()) {
            System.out.println("Wah so free ah you, got no tasks to do");
            System.out.println(barrier);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
            System.out.println(barrier);
        }
    }

    private static void markTask(String taskNumber, String barrier, boolean markDone) {
        System.out.println(barrier);
        // Need to convert the taskNumber to an integer then minus 1 to follow array list
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;

            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task task = tasks.get(taskIndex);

                if (markDone) { // If user wants to mark task as done
                    if (!task.getStatus()) {
                        task.markAsDone();
                        System.out.println("OKAY LA, being productive I see.");
                        System.out.println("I helped marked it for you.");
                        System.out.println(task);
                    } else {
                        System.out.println("eh you already finished this task la");
                    }
                } else { // If user wants to unmark
                    if (task.getStatus()) {
                        task.uncheck();
                        System.out.println("oh... I have unchecked the task you lazy bum!");
                        System.out.println(task);
                    } else {
                        System.out.println("eh this task is already unmark, choose again");
                    }
                }

            } else {
                System.out.println("Alamak this task number does not exist!");
            }

            System.out.println(barrier);
        } catch (NumberFormatException e) {
            System.out.println("Need to enter a valid task number leh");
        }
    }

    private static void addTodo(String description, String barrier) {
        ToDo newTodo = new ToDo(description);
        tasks.add(newTodo);
        System.out.println(barrier);
        System.out.println("Roger, added the task");
        System.out.println("   " + newTodo);
        System.out.println("Jialat, you have " + tasks.size() + " tasks in your list");
        System.out.println(barrier);
    }

    private static void addDeadline(String input, String barrier) {
        String[] parts = input.split("/by");
        if (parts.length < 2) {
            System.out.println(barrier);
            System.out.println("Hey specify the deadline with /by");
            System.out.println(barrier);
        } else {
            Deadline newDeadline = new Deadline(parts[0], parts[1]);
            tasks.add(newDeadline);
            System.out.println(barrier);
            System.out.println("Roger, added the task");
            System.out.println("   " + newDeadline);
            System.out.println("Jialat, you have " + tasks.size() + " tasks in your list");
            System.out.println(barrier);
        }
    }

    private static void addEvent(String input, String barrier) {
        String[] parts = input.split("/from");
        if (parts.length < 2) {
            System.out.println(barrier);
            System.out.println("Hey specify the Event with /from and /to");
            System.out.println(barrier);
            return;
        }
        String[] timeline = parts[1].split("/to");
        if (timeline.length < 2) {
            System.out.println(barrier);
            System.out.println("Hey specify the Event with /from and /to");
            System.out.println(barrier);
        } else {
            Event newEvent = new Event(parts[0], timeline[0], timeline[1]);
            tasks.add(newEvent);
            System.out.println(barrier);
            System.out.println("Roger, added the task");
            System.out.println("   " + newEvent);
            System.out.println("Jialat, you have " + tasks.size() + " tasks in your list");
            System.out.println(barrier);
        }
    }

    private static void deleteTask(String taskNumber, String barrier) {
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;

            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                System.out.println(barrier);
                System.out.println("The task number does not exist...");
                System.out.println(barrier);
                return;
            }

            Task taskToDelete = tasks.get(taskIndex);
            tasks.remove(taskToDelete);

            System.out.println(barrier);
            System.out.println("Orh, I removed the task");
            System.out.println(taskToDelete);
            System.out.println("Now you are left with " + tasks.size() + " tasks in your list");
            System.out.println(barrier);
        } catch (NumberFormatException e) {
            System.out.println("Hais you need to enter a task number, not some gibberish");
        }
    }


    private static void loadTasks() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                return; // no file to load from
            }

            Scanner fileScanner = new Scanner(file);
            // Adds task that were saved into local file into the tasks array to be listed
            while(fileScanner.hasNextLine()) {
                String nextLine = fileScanner.nextLine();
                Task task = getTaskFromString(nextLine);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
    // convert the line from text file into a task object
    private static Task getTaskFromString(String line) {}

    private static void saveTasks() {
        try {
            FileWriter writer = new FileWriter(DATA_FILE);

            for (Task task : tasks) {
                writer.write(taskToString(task) + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    public static String taskToString(Task task) {
        String type;
        String details = "";
        if (task instanceof ToDo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            Deadline deadline = (Deadline) task;
            details = " | " + deadline.getBy();
        } else if (task instanceof  Event) {
            type = "E";
            Event event = (Event) task;
            details = " | " + event.getFrom() + " | " + event.getTo();
        } else {
            type = "T"; // just fall back to todo
        }

        return type + " | " + task.getStatusIcon() + " | " + task.getDescription() + details;
    }
}
