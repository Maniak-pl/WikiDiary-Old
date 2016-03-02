package pl.maniak.wikidiary.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.maniak.wikidiary.fragments.PreparingNoteFragment;
import pl.maniak.wikidiary.fragments.SettingsFragment;

/**
 * Created by Maniak on 2016-02-25.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(SettingsFragment fragment);

    void inject(PreparingNoteFragment preparingNoteFragment);
}
