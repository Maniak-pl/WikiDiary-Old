package pl.maniak.wikidiary.utils.di.wikinote.list;


import dagger.Component;
import pl.maniak.wikidiary.ui.wikinote.list.ListNotesFragmentImpl;
import pl.maniak.wikidiary.utils.di.AppComponent;

@ListNotesFragmentScope
@Component(
        dependencies = AppComponent.class,
        modules = ListNotesFragmentModule.class
)
public interface ListNotesFragmentComponent {

    void inject(ListNotesFragmentImpl editNoteFragment);
}
