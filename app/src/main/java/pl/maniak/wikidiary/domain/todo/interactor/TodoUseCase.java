package pl.maniak.wikidiary.domain.todo.interactor;

import java.util.List;

import pl.maniak.wikidiary.domain.todo.Task;

public interface TodoUseCase {
    Task getTask(Long id);
    List<Task> getAllTasks();
    void save(Task todo);
    void delete(Long id);
}
