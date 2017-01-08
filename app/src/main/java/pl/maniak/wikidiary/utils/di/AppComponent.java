package pl.maniak.wikidiary.utils.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.maniak.wikidiary.activitys.MainActivity;
import pl.maniak.wikidiary.fragments.MainFragment;
import pl.maniak.wikidiary.fragments.PreparingNoteFragment;
import pl.maniak.wikidiary.fragments.SettingsFragment;
import pl.maniak.wikidiary.modals.AddTagDialogFragment;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(SettingsFragment fragment);

    void inject(PreparingNoteFragment preparingNoteFragment);

    void inject(MainFragment mainFragment);

    void inject(MainActivity mainActivity);

    void inject(AddTagDialogFragment addTagDialogFragment);
}
