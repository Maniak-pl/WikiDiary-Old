package pl.maniak.wikidiary.ui.wikinote;

import pl.maniak.wikidiary.ui.BaseContract;

public interface WikiNoteContract {

    interface View extends BaseContract.View {
    }

    interface Router extends BaseContract.Router {
        void navigateToSettings();
        void navigateToNewTask();

    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onSettingsClicked();
        void onNewTaskClicked();
    }

}
