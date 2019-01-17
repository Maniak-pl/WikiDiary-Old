package pl.maniak.wikidiary.utils.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.maniak.wikidiary.ui.wikinote.MainActivity;
import pl.maniak.wikidiary.repository.DBHelper;
import pl.maniak.wikidiary.ui.wikinote.add.MainFragment;
import pl.maniak.wikidiary.ui.wikinote.prepare.PreparingNoteFragment;
import pl.maniak.wikidiary.ui.settings.fragments.SettingsFragment;
import pl.maniak.wikidiary.ui.wikinote.add.dialogs.AddTagDialogFragment;
import pl.maniak.wikidiary.repository.wikinote.WikiNoteRepository;
import pl.maniak.wikidiary.utils.config.ResourceProvider;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    ResourceProvider getResourceProvider();

    void inject(SettingsFragment fragment);

    void inject(PreparingNoteFragment preparingNoteFragment);

    void inject(MainFragment mainFragment);

    void inject(MainActivity mainActivity);

    void inject(AddTagDialogFragment addTagDialogFragment);

    DBHelper getDBHelper();

    WikiNoteRepository getWikiNoteRepository();
}
