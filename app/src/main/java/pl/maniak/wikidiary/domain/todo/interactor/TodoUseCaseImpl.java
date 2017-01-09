package pl.maniak.wikidiary.domain.todo.interactor;

import java.util.Date;
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
        prepareNewTasNotekAndSave(task);
        repository.saveTask(task);
    }

    @Override
    public void update(Task task) {
        String content = repository.getTaskById(task.getId()).getContent();
        prepareEditTaskNoteAndSave(task, content);
        repository.saveTask(task);
    }

    @Override
    public void delete(Long id) {
        prepareDeleteTaskNoteAndSave(getTask(id));
        repository.deleteTaskById(id);
    }

    @Override
    public void done(Task task) {
        prepareDoneTaskNoteAndSave(task);
        task.setDone(true);
        repository.saveTask(task);
    }

    private void prepareNewTasNotekAndSave(Task task) {
        saveWikiNote("Nowe zadanie: **"+task.getContent()+"**", task.getDate());
    }

    private void prepareDeleteTaskNoteAndSave(Task task) {
        saveWikiNote("Usunięto zadanie: <del>"+task.getContent()+"</del>", new Date());
    }

    private void prepareEditTaskNoteAndSave(Task task, String content) {
        saveWikiNote("Edytowano zadanie z: **"+content+"** na **"+task.getContent()+"**", new Date());
    }

     private void prepareDoneTaskNoteAndSave(Task task) {
        saveWikiNote("Wykonano zadanie: **"+task.getContent()+"**", new Date());
    }



    private void saveWikiNote(String content, Date date) {
        useCase.save(new WikiNote(Constants.TAG_TODO, content,date));
    }
}
