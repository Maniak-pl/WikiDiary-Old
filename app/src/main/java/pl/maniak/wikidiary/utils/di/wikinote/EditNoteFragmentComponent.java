package pl.maniak.wikidiary.utils.di.wikinote;


import dagger.Component;
import pl.maniak.wikidiary.ui.wikinote.EditNoteFragment;
import pl.maniak.wikidiary.ui.wikinote.EditNoteFragmentImpl;
import pl.maniak.wikidiary.utils.di.AppComponent;

@WikiNoteScope
@Component(
        dependencies = AppComponent.class,
        modules = EditNoteFragmentModule.class
)
public interface EditNoteFragmentComponent {

    void inject(EditNoteFragmentImpl editNoteFragment);
}
