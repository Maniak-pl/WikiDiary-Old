package pl.maniak.wikidiary.utils.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.maniak.wikidiary.activitys.MainActivity;
import pl.maniak.wikidiary.domain.DBHelper;
import pl.maniak.wikidiary.fragments.MainFragment;
import pl.maniak.wikidiary.fragments.PreparingNoteFragment;
import pl.maniak.wikidiary.fragments.SettingsFragment;
import pl.maniak.wikidiary.modals.AddTagDialogFragment;
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
}
