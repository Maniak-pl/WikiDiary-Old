package pl.maniak.wikidiary.ui.todo;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.api.TodoRepository;
import pl.maniak.wikidiary.models.Task;
import pl.maniak.wikidiary.utils.ObservableList;
import rx.schedulers.Schedulers;

@RequiredArgsConstructor
public class TodoPresenter implements TodoContract.Presenter {

    private final ObservableList<Task> tasks;
    private final TodoRepository repository;
    private Long currentTaskId;

    private TodoContract.View view;
    private TodoContract.Router router;


    @Override
    public void onResumed() {
        showTasks();

        tasks.getObservable().subscribe(taskData -> showTasks());

        updateTasks();
    }

    private void showTasks() {
        if(view != null) {
            view.showTasks(tasks.getList());
        }
    }

    private void updateTasks() {
        clearTasks();
        tasks.addAll(repository.getTasks());
    }

    private void clearTasks() {
        currentTaskId = null;
        tasks.clear();
    }

    @Override
    public void onPauseCalled() {
        tasks.getObservable().unsubscribeOn(Schedulers.immediate());
    }

    @Override
    public void onNewTaskButtonClicked() {
        if(view != null) {
            view.showNewTaskEditor();
        }
    }

    @Override
    public void onTaskClicked(Task task) {
        if(view != null) {
            currentTaskId = task.getId();
            view.showOptionsDialog();
        }
    }

    @Override
    public void onEditTaskOptionClicked() {
        if(view != null) {
            view.showEditTaskEditor(repository.getTask(currentTaskId));
        }
    }

    @Override
    public void onDeleteTaskOptionClicked() {
        repository.delete(currentTaskId);
        updateTasks();
    }

    @Override
    public void onCommitButtonClicked(Task task) {
        repository.save(task);
        updateTasks();
    }

    @Override
    public void attachView(TodoContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRouter(TodoContract.Router router) {
        this.router = router;
    }

    @Override
    public void detachRouter() {
        router = null;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
