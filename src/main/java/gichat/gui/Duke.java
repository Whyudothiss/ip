package gichat.gui;

import gichat.GiChat;
import gichat.command.Parser;
import gichat.command.Command;
import gichat.storage.Storage;
import gichat.task.TaskList;
import gichat.task.Task;
import gichat.task.Event;
import gichat.task.ToDo;
import gichat.task.Deadline;

/**
 * GUI Bridge class that connects the JavaFX GUI to GiChat functionality
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private GiChat giChat;

    public Duke() {
        this.giChat = new GiChat("data/tasks.txt");
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return giChat.getResponse(input);
    }
}