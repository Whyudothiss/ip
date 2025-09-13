package gichat;
import gichat.ui.Ui;
import gichat.storage.Storage;
import gichat.command.Parser;
import gichat.command.Command;
import gichat.task.TaskList;
import gichat.task.Task;
import gichat.task.Event;
import gichat.task.ToDo;
import gichat.task.Deadline;

/**
 * Main class for the GiChat bot
 */
public class GiChat {
    private Storage storage;
    private TaskList tasks;
//    private Ui ui;

    /**
     * Construct a new GiChat instance with the specified file path
     *
     * @param filePath The path to the data file
     */
    public GiChat(String filePath) {
//        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (Exception e) {
//            ui.showError("Error loading tasks: " + e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Processors users input and returns a response string
     * @param input User's input command
     * @return Response string to display on GUI
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            return executeCommand(command);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

    }

    /**
     *  Main method to start application
     *
     * @param args Command line argument
     */
//    public static void main(String[] args) {
//        new GiChat("data/tasks.txt").run();
//    }

    /**
     * Runs the main application loop
     */
//    public void run() {
////        ui.showWelcome();
//        boolean isExit = false;
//
//        while (!isExit) {
//            try {
//                String fullcomand = ui.readCommand();
//                Command command = Parser.parse(fullcomand);
//                executeCommand(command);
//                isExit = command.isExit();
//            } catch (Exception e) {
//                ui.showError("Error: " + e.getMessage());
//            }
//        }
//    }

    /**
     * Execute the given command and return response string
     *
     * @param command The command to execute
     * @return Response String for the GUI
     */
    public String executeCommand(Command command) {
        assert command != null : "Command should not be null";

        switch (command.getType()) {
        case BYE:
            storage.save(tasks.getAllTasks());
            return "Bye, don't come back soon";
        case LIST:
            return getTasksListString();
        case MARK:
            handleMarkTask(command.getArguments(), true);
        case UNMARK:
            handleMarkTask(command.getArguments(), false);
        case TODO:
            handleAddTodo(command.getArguments());
        case DEADLINE:
            handleAddDeadline(command.getArguments());
        case EVENT:
            handleAddEvent(command.getArguments());
        case DELETE:
            handleDeleteTask(command.getArguments());
        case FIND:
            handleFindTasks(command.getArguments());
        case UNKNOWN:
            return "Erm... you need to give me a valid command...\n" +
                    "Can list, mark, unmark, todo, deadline, event, delete, find";
        default:
            return "Unknown command";
        }
    }

    private String getTasksListString() {
        if (tasks.isEmpty()) {
            return "Wah so free ah you, got no tasks to do";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.getSize(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.getTask(i)).append("\n");
            }
            return sb.toString().trim();
        }

    }

    /**
     * Handles marking and unmarking of tasks
     *
     * @param arguments Task Number
     * @param markDone True to mark as done, false to mark as undone
     * @return GUI response after mark/unmark is called
     *
     */
    private String handleMarkTask(String arguments, boolean markDone) {
        try {
            int taskIndex = Parser.parseTaskNumber(arguments);

            if (taskIndex < 0 || taskIndex >= tasks.getSize()) {
                return "Alamak this task number does not exist";
            }

            Task task = tasks.getTask(taskIndex);

            if (markDone) {
                if (!task.getStatus()) {
                    task.markAsDone();
                    storage.save(tasks.getAllTasks());
                    return "OKAY LA, being productive I see.\nI helped marked it for you.\n" + task;
                } else {
                    return "eh you already finished this task la";
                }
            } else {
                if (task.getStatus()) {
                    task.uncheck();
                    storage.save(tasks.getAllTasks());
                    return "oh... I have unchecked the task for you lazy bum\n";
                } else {
                   return "eh this task is already unmark, choose again";
                }
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Handles adding a todo task
     *
     * @param arguments Todo description
     * @return GUI response after adding a todo task
     */
    private String handleAddTodo(String arguments) {
        try {
            String description = Parser.parseTodo(arguments);
            ToDo newTodo = new ToDo(description);
            tasks.addTask(newTodo);
            storage.save(tasks.getAllTasks());
            return "Roger, added the task\n   " + newTodo +
                    "\nJialat, you have " + tasks.getSize() + " tasks in your list";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Handles adding a deadline task
     *
     * @param arguments deadline description
     * @return GUI response after adding deadline task
     */
    private String handleAddDeadline(String arguments) {
        // dont have to check whether user input is correct as its done by the parser class
        try {
            String[] parts = Parser.parseDeadline(arguments);
            Deadline newDeadline = new Deadline(parts[0], parts[1]);
            tasks.addTask(newDeadline);
            storage.save(tasks.getAllTasks());
            return "Roger, added the task\n   " + newDeadline +
                    "\nJialat, you have " + tasks.getSize() + " tasks in your list";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Handles adding an Event task
     *
     * @param arguments Event description
     * @return GUI response after adding an Event task
     */
    private String handleAddEvent(String arguments) {
        try {
            String[] parts = Parser.parseEvent(arguments);
            Event newEvent = new Event(parts[0], parts[1], parts[2]);
            tasks.addTask(newEvent);
            storage.save(tasks.getAllTasks());
            return "Roger, added the task\n   " + newEvent +
                    "\nJialat, you have " + tasks.getSize() + " tasks in your list";
        } catch (IllegalArgumentException e) {
           return e.getMessage();
        }
    }

    /**
     * Handles deleting a task
     *
     * @param arguments Task number to delete
     * @return GUI response after deleting a task
     */
    private String handleDeleteTask (String arguments) {
        try {
            int taskIndex = Parser.parseTaskNumber(arguments);

            if (taskIndex < 0 || taskIndex >= tasks.getSize()) {
                return "The task number does not exist...";
            }

            Task deletedTask = tasks.deleteTask(taskIndex);
            storage.save(tasks.getAllTasks());
            return "Orh, I removed the task\n" + deletedTask +
                    "\nNow you are left with " + tasks.getSize() + " tasks in your list";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Handles finding tasks with relevant keywords
     *
     * @param arguments keyword to be found
     * @return GUI response after calling find command
     */
    private String handleFindTasks(String arguments) {
        try {
            String keyword = Parser.parseFind(arguments);
            TaskList foundTasks = tasks.findTasks(keyword);

            if (foundTasks.isEmpty()) {
                return "Erm.. I can't find any tasks with that keyword leh";
            } else {
                StringBuilder sb = new StringBuilder("These are the tasks I could find\n");
                for (int i = 0; i < foundTasks.getSize(); i++) {
                    sb.append((i+1)).append(".").append(foundTasks.getTask(i)).append("\n");
                }
                return sb.toString().trim();
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}