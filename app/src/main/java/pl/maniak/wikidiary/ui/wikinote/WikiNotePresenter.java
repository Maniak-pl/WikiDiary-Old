package pl.maniak.wikidiary.ui.wikinote;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteUseCase;
import pl.maniak.wikidiary.utils.ObservableList;

@RequiredArgsConstructor
public class WikiNotePresenter implements WikiNoteContract.Presenter {

    private final ObservableList<WikiNote> noteObservableList;
    private final ObservableList<Tag> tagObservableList;

    private WikiNoteContract.View view;
    private WikiNoteContract.Router router;

    @Inject
    WikiNoteUseCase useCase;

    @Override
    public void attachView(WikiNoteContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRouter(WikiNoteContract.Router router) {
        this.router = router;
    }

    @Override
    public void detachRouter() {
        view = null;
    }

    @Override
    public void detachView() {
        router = null;
    }

    @Override
    public void onSettingsClicked() {
        if(router!= null) {
            router.navigateToSettings();
        }
    }

    @Override
    public void onNewTaskClicked() {
        if(router!=null){
            router.navigateToNewTask();
        }
    }
}
