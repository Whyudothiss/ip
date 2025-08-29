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
    private Ui ui;

    /**
     * Construct a new GiChat instance with the specified file path
     *
     * @param filePath The path to the data file
     */
    public GiChat(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showError("Error loading tasks: " + e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     *  Main method to start application
     *
     * @param args Command line argument
     */
    public static void main(String[] args) {
        new GiChat("data/tasks.txt").run();
    }

    /**
     * Runs the main application loop
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullcomand = ui.readCommand();
                Command command = Parser.parse(fullcomand);
                executeCommand(command);
                isExit = command.isExit();
            } catch (Exception e) {
                ui.showError("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Execute the given command
     *
     * @param command The command to execute
     */
    public void executeCommand(Command command) {
        switch (command.getType()) {
        case BYE:
            storage.save(tasks.getAllTasks());
            ui.showGoodbye();
            break;
        case LIST:
            ui.showTasksList(tasks);
            break;
        case MARK:
            handleMarkTask(command.getArguments(), true);
            break;
        case UNMARK:
            handleMarkTask(command.getArguments(), false);
            break;
        case TODO:
            handleAddTodo(command.getArguments());
            break;
        case DEADLINE:
            handleAddDeadline(command.getArguments());
            break;
        case EVENT:
            handleAddEvent(command.getArguments());
            break;
        case DELETE:
            handleDeleteTask(command.getArguments());
            break;
        case FIND:
            handleFindTasks(command.getArguments());
            break;
        case UNKNOWN:
            ui.showError("Erm... you need to give me a valid command...\n" +
                    "Can list, mark, unmark, todo, deadline, event, delete, find");
            break;
        }
    }

    /**
     * Handles marking and unmarking of tasks
     *
     * @param arguments Task Number
     * @param markDone True to mark as done, false to mark as undone
     */
    private void handleMarkTask(String arguments, boolean markDone) {
        try {
            int taskIndex = Parser.parseTaskNumber(arguments);

            if (taskIndex < 0 || taskIndex >= tasks.getSize()) {
                ui.showError("Alamak this task number does not exist");
                return;
            }

            Task task = tasks.getTask(taskIndex);

            if (markDone) {
                if (!task.getStatus()) {
                    task.markAsDone();
                    ui.showTaskMarked(task);
                    storage.save(tasks.getAllTasks());
                } else {
                    ui.showError("eh you already finished this task la");
                }
            } else {
                if (task.getStatus()) {
                    task.uncheck();
                    ui.showTaskUnmarked(task);
                    storage.save(tasks.getAllTasks());
                } else {
                    ui.showError("eh this task is already unmark, choose again");
                }
            }
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles adding a todo task
     *
     * @param arguments Todo description
     */
    private void handleAddTodo(String arguments) {
        try {
            String description = Parser.parseTodo(arguments);
            ToDo newTodo = new ToDo(description);
            tasks.addTask(newTodo);
            storage.save(tasks.getAllTasks());
            ui.showTaskAdded(newTodo, tasks.getSize());
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles adding a deadline task
     *
     * @param arguments deadline description
     */
    private void handleAddDeadline(String arguments) {
        // dont have to check whether user input is correct as its done by the parser class
        try {
            String[] parts = Parser.parseDeadline(arguments);
            Deadline newDeadline = new Deadline(parts[0], parts[1]);
            tasks.addTask(newDeadline);
            storage.save(tasks.getAllTasks());
            ui.showTaskAdded(newDeadline, tasks.getSize());
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles adding an Event task
     *
     * @param arguments Event description
     */
    private void handleAddEvent(String arguments) {
        try {
            String[] parts = Parser.parseEvent(arguments);
            Event newEvent = new Event(parts[0], parts[1], parts[2]);
            tasks.addTask(newEvent);
            storage.save(tasks.getAllTasks());
            ui.showTaskAdded(newEvent, tasks.getSize());
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles deleting a task
     *
     * @param arguments Task number to delete
     */
    private void handleDeleteTask (String arguments) {
        try {
            int taskIndex = Parser.parseTaskNumber(arguments);

            if (taskIndex < 0 || taskIndex >= tasks.getSize()) {
                ui.showError("The task number does not exist...");
                return;
            }

            Task deletedTask = tasks.deleteTask(taskIndex);
            storage.save(tasks.getAllTasks());
            ui.showTaskDeleted(deletedTask, tasks.getSize());
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleFindTasks(String arguments) {
        try {
            String keyword = Parser.parseFind(arguments);
            TaskList foundTask = tasks.findTasks(keyword);
            ui.showTaskFound(foundTask);
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }
}