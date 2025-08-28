package gichat.storage;

import gichat.task.Deadline;
import gichat.task.Event;
import gichat.task.Task;
import gichat.task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading tasks from file and savings tasks to file
 */

public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path
     *
     * @param filePath That path to the data file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Load tasks from the data file
     *
     * @return An Array List of tasks loaded from the file
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                return tasks; // return empty list

            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String nextLine = fileScanner.nextLine();
                Task task = getTaskFromString(nextLine);
                if (task != null) {
                    tasks.add(task);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Save tasks to the data file
     *
     * @param tasks An Array list of tasks to be saved
     */
    public void save (ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(taskToString(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error savings tasks: " + e.getMessage());
        }
    }

    private Task getTaskFromString(String line) {
        // to use | need to have a \ but to have that need another \
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("X");
        String description = parts[2];

        Task task = null;

        switch (type) {
        case "T":
            task = new ToDo(description);
            break;

        case "D":
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            task = new Event(description, parts[3], parts[4]);
            break;
        }
        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    private String taskToString (Task task) {
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
