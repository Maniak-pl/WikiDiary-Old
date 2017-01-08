package pl.maniak.wikidiary.domain.todo.interactor;

import java.util.List;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.todo.repository.TodoRepository;

@RequiredArgsConstructor
public class TodoUseCaseImpl implements TodoUseCase {

    private final TodoRepository repository;

    @Override
    public Task getTask(Long id) {
        return repository.getTaskById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.getAllTasks();
    }

    @Override
    public void save(Task todo) {
        repository.saveTask(todo);
    }

    @Override
    public void delete(Long id) {
        repository.deleteTaskById(id);
    }
}
