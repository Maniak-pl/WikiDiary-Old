package pl.maniak.wikidiary.utils.di.wikinote;



import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteUseCase;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteUserCaseImpl;
import pl.maniak.wikidiary.modals.CommandDialogFragment;
import pl.maniak.wikidiary.repository.wikinote.WikiNoteRepository;
import pl.maniak.wikidiary.ui.wikinote.WikiNoteActivity;
import pl.maniak.wikidiary.ui.wikinote.WikiNoteContract;
import pl.maniak.wikidiary.ui.wikinote.WikiNotePagerAdapter;
import pl.maniak.wikidiary.ui.wikinote.WikiNotePresenter;
import pl.maniak.wikidiary.utils.ObservableList;
import pl.maniak.wikidiary.utils.ObservableListImpl;

@Module
@RequiredArgsConstructor
public class WikiNoteModule {

    private final WikiNoteActivity activity;

    @Provides
    ObservableList<WikiNote> provideObservableList() {
        return new ObservableListImpl<>();
    }

    @Provides
    ObservableList<Tag> provideTagObservableList() {
        return new ObservableListImpl<>();
    }

    @Provides
    WikiNoteContract.Presenter providePresenter(ObservableList<WikiNote> wikiNoteObservableList, ObservableList<Tag> tagObservableList, WikiNoteUseCase useCase) {
        return new WikiNotePresenter(wikiNoteObservableList, tagObservableList, useCase);
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @Provides
    WikiNotePagerAdapter providePagerAdapter(FragmentManager fragmentManager) {
        return new WikiNotePagerAdapter(fragmentManager);
    }

    @Provides
    WikiNoteUseCase provideNoteUseCase(WikiNoteRepository repository) {
        return new WikiNoteUserCaseImpl(repository);
    }

    @Provides
    CommandDialogFragment provideCommandDialog() {
        return CommandDialogFragment.newInstance();
    }


}
