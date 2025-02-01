package TaskManager;


import java.util.ArrayList;
import java.util.List;

public class TaskServiceImpl extends TaskServicePOA {
    private List<Task> taskList = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        taskList.add(task);
    }

    @Override
    public void removeTask(String id) {
        taskList.removeIf(task -> task.id.equals(id));
    }

    @Override
    public Task[] getTasks() {
        return taskList.toArray(new Task[0]);
    }

    @Override
    public void updateTaskStatus(String id, TaskStatus status) {
        for (Task task : taskList) {
            if (task.id.equals(id)) {
                task.status = status;
                break;
            }
        }
    }

    @Override
    public void assignTask(String id, String user) {
        for (Task task : taskList) {
            if (task.id.equals(id)) {
                task.assignedTo = user;
                break;
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).id.equals(task.id)) {
                taskList.set(i, task);
                break;
            }
        }
    }
}