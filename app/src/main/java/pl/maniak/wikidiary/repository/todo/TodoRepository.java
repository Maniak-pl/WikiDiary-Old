package pl.maniak.wikidiary.repository.todo;

import java.util.List;

import pl.maniak.wikidiary.domain.todo.Task;

public interface TodoRepository {
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    void saveTask(Task todo);
    void deleteTaskById(Long id);
}
