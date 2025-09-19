package gichat;
import gichat.ui.Ui;
import gichat.storage.Storage;
import gichat.command.Parser;
import gichat.command.Command;
import gichat.command.EditInfo;
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

    /**
     * Construct a new GiChat instance with the specified file path
     *
     * @param filePath The path to the data file
     */
    public GiChat(String filePath) {
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (Exception e) {
            System.out.println("Could not load existing tasks, starting with empty task list. Error: "
                    + e.getMessage());
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
            return handleMarkTask(command.getArguments(), true);
        case UNMARK:
            return handleMarkTask(command.getArguments(), false);
        case TODO:
            return handleAddTodo(command.getArguments());
        case DEADLINE:
            return handleAddDeadline(command.getArguments());
        case EVENT:
            return handleAddEvent(command.getArguments());
        case DELETE:
            return handleDeleteTask(command.getArguments());
        case FIND:
            return handleFindTasks(command.getArguments());
        case EDIT:
            return handleEditTask(command.getArguments());
        case UNKNOWN:
            return "Erm... you need to give me a valid command...\n" +
                    "Can list, mark, unmark, todo, deadline, event, delete, find, edit";
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
                if (task.getStatus()) {
                    return "eh you already finished this task la";
                }
                task.markAsDone();
                storage.save(tasks.getAllTasks());
                return "OKAY LA, being productive I see.\nI helped marked it for you.\n" + task;
            } else {
                if (!task.getStatus()) {
                    return "eh this task is already unmark, choose again";
                }
                task.uncheck();
                storage.save(tasks.getAllTasks());
                return "oh... I have unchecked the task for you lazy bum\n";
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

    /**
     * Handles editing a task
     * @param arguments Edit Parameters
     * @return Response after editing a task
     */
    private String handleEditTask(String arguments) {
        try {
            EditInfo editInfo = Parser.parseEdit(arguments);
            int taskIndex = editInfo.getTaskIndex();

            if (taskIndex < 0 || taskIndex >= tasks.getSize()) {
                return "Alamak this task number does not exist";
            }

            Task task = tasks.getTask(taskIndex);
            String originaltask = task.toString();

            // need to edit based on the type of task
            if (task instanceof  ToDo) {
                if (editInfo.getDescription() == null) {
                    return "Yo TODO task, you can only change description with /desc";
                }
//                ToDo todo = (ToDo) task;
                boolean wasMarked = task.getStatus();
                ToDo newTodo = new ToDo(editInfo.getDescription());
                if (wasMarked) {
                    newTodo.markAsDone();
                }
                tasks.replaceTask(taskIndex, newTodo);
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                String newDesc = editInfo.getDescription() != null
                        ? editInfo.getDescription()
                        : deadline.getDescription();
                String newBy = editInfo.getBy() != null
                        ? editInfo.getBy()
                        : deadline.getBy();

                boolean wasMarked = task.getStatus();
                Deadline newDeadline = new Deadline(newDesc, newBy);
                if (wasMarked) {
                    newDeadline.markAsDone();
                }
                tasks.replaceTask(taskIndex, newDeadline);
            } else if (task instanceof Event) {
                Event event = (Event) task;
                String newDesc = editInfo.getBy() != null
                        ? editInfo.getDescription()
                        : event.getDescription();
                String newFrom = editInfo.getFrom() != null
                        ? editInfo.getFrom()
                        : event.getFrom();
                String newTo = editInfo.getTo() != null
                        ? editInfo.getTo()
                        : event.getTo();

                boolean wasMarked = task.getStatus();
                Event newEvent = new Event(newDesc, newFrom, newTo);
                if (wasMarked) {
                    newEvent.markAsDone();
                }
                tasks.replaceTask(taskIndex, newEvent);
            }

            storage.save(tasks.getAllTasks());
            Task updatedTask = tasks.getTask(taskIndex);

            return "Roger, I updated your task\n" +
                    "Old: " + originaltask + "\n" +
                    "New: " + updatedTask;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Error editing task: " + e.getMessage();
        }
    }
}