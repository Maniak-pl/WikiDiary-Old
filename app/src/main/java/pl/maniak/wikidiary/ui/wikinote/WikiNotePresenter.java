package pl.maniak.wikidiary.ui.wikinote;

import java.util.Date;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteUseCase;
import pl.maniak.wikidiary.repository.tag.TagRepository;
import pl.maniak.wikidiary.utils.ObservableList;
import rx.Observable;

/**
 * Created by mac on 13.01.2017.
 */

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
    public void onTagClicked(String tag) {
        if(view != null) {
            useCase.save(new WikiNote(tag, view.getContent(), new Date()));
        }
    }


}
