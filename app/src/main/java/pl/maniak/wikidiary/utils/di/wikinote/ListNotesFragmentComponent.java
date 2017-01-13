package pl.maniak.wikidiary.utils.di.wikinote;


import dagger.Component;
import pl.maniak.wikidiary.ui.wikinote.ListNotesFragmentImpl;
import pl.maniak.wikidiary.utils.di.AppComponent;

@WikiNoteScope
@Component(
        dependencies = AppComponent.class,
        modules = ListNotesFragmentModule.class
)
public interface ListNotesFragmentComponent {

    void inject(ListNotesFragmentImpl editNoteFragment);
}
