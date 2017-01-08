package pl.maniak.wikidiary.ui.todo;

import java.util.List;

import pl.maniak.wikidiary.models.Task;
import pl.maniak.wikidiary.ui.BaseContract;

public interface TodoContract {

    interface View extends BaseContract.View {
        void showTasks(List<Task> list);
        void showOptionsDialog();
        void showNewTaskEditor();
        void showEditTaskEditor(Task task);
    }

    interface Router extends BaseContract.Router {
     }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onResumed();
        void onPauseCalled();
        void onNewTaskButtonClicked();
        void onTaskClicked(Task task);
        void onEditTaskOptionClicked();
        void onDeleteTaskOptionClicked();
        void onCommitButtonClicked(Task task);
    }
}
