package pl.maniak.wikidiary.api;

import java.util.List;

import pl.maniak.wikidiary.models.Task;

public interface TodoRepository {
    Task getTask(Long id);
    List<Task> getTasks();
    void save(Task todo);
    void delete(Long id);
}
