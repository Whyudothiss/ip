package gichat.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() {
        this.taskList = new TaskList();
        this.task1 = new ToDo("Buy groceries");
        this.task2 = new Deadline("Submit assignment", "2023-10-20");
        this.task3 = new Event("Team meeting", "2023-10-15", "2023-10-16");

        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);
    }

    @Test
    public void deleteTask_validIndex_returnsDeletedTask() {
        Task deletedTask = taskList.deleteTask(1);

        assertEquals(task2, deletedTask);
        assertEquals(2, taskList.getSize());
        assertEquals(task1, taskList.getTask(0));
        assertEquals(task3, taskList.getTask(1));
    }

    @Test
    public void deleteTask_firstIndex_returnsDeletedTask() {
        Task deletedTask = taskList.deleteTask(0);

        assertEquals(task1, deletedTask);
        assertEquals(2, taskList.getSize());
        assertEquals(task2, taskList.getTask(0));
        assertEquals(task3, taskList.getTask(1));
    }
}
