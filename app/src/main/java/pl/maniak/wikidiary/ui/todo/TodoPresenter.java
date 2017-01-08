package pl.maniak.wikidiary.ui.todo;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.todo.interactor.TodoUseCase;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.utils.ObservableList;
import rx.schedulers.Schedulers;

@RequiredArgsConstructor
public class TodoPresenter implements TodoContract.Presenter {

    private final ObservableList<Task> tasks;
    private final TodoUseCase useCase;
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
        tasks.addAll(useCase.getAllTasks());
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
            view.showEditTaskEditor(useCase.getTask(currentTaskId));
        }
    }

    @Override
    public void onDeleteTaskOptionClicked() {
        useCase.delete(currentTaskId);
        updateTasks();
    }

    @Override
    public void onCommitButtonClicked(Task task) {
        useCase.save(task);
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
