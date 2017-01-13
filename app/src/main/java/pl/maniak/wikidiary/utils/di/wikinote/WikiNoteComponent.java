package pl.maniak.wikidiary.utils.di.wikinote;


import dagger.Component;
import pl.maniak.wikidiary.ui.wikinote.WikiNoteActivity;
import pl.maniak.wikidiary.utils.di.AppComponent;

@WikiNoteScope
@Component(dependencies = AppComponent.class,
        modules = WikiNoteModule.class
)
public interface WikiNoteComponent {
    void inject(WikiNoteActivity activity);
}
