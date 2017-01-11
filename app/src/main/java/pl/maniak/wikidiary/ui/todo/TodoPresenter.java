package pl.maniak.wikidiary.ui.todo;

import java.util.Date;

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
    public void onTaskClicked(long taskId) {
        if(view != null) {
            currentTaskId = taskId;
            view.showOptionsDialog();
        }
    }

    @Override
    public void onDoneChecked(long taskId) {
        useCase.done(useCase.getTask(taskId));
        updateTasks();
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
    public void onCommitNewTaskButtonClicked(String content) {
        useCase.save(new Task(content, getCurrentDate()));
        updateTasks();
    }

    @Override
    public void onCommitEditTaskButtonClicked(String content) {
        updateTaskAndSaveInRepository(content);
        updateTasks();
    }

    private void updateTaskAndSaveInRepository(String content) {
        Task currentTask = useCase.getTask(currentTaskId);
        currentTask.setContent(content);
        currentTask.setDate(getCurrentDate());
        useCase.update(currentTask);
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

    private Date getCurrentDate() {
        return new Date();
    }
}
