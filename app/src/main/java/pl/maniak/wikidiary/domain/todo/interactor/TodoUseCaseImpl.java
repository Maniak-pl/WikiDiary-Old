package pl.maniak.wikidiary.domain.todo.interactor;

import java.util.List;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.todo.repository.TodoRepository;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteUseCase;
import pl.maniak.wikidiary.utils.Constants;

@RequiredArgsConstructor
public class TodoUseCaseImpl implements TodoUseCase {

    private final TodoRepository repository;
    private final WikiNoteUseCase useCase;

    @Override
    public Task getTask(Long id) {
        return repository.getTaskById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.getAllTasks();
    }

    @Override
    public void save(Task task) {
        prepareWikiNote(task);
        repository.saveTask(task);
    }

    @Override
    public void delete(Long id) {
        repository.deleteTaskById(id);
    }

    private void prepareWikiNote(Task task) {
        useCase.save(new WikiNote(Constants.TAG_TODO, task.getContent(), task.getDate()));
    }
}
