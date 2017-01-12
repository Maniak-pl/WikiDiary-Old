package pl.maniak.wikidiary.utils.di;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.repository.tag.TagRepository;
import pl.maniak.wikidiary.repository.tag.TagRepositoryImpl;
import pl.maniak.wikidiary.utils.Constants;
import pl.maniak.wikidiary.repository.DBHelper;
import pl.maniak.wikidiary.utils.config.ResourceProvider;
import pl.maniak.wikidiary.utils.config.ResourceProviderImpl;

@Module
@RequiredArgsConstructor
public class AppModule {

    private final App application;

    @Provides
    @Singleton
    Context provideAppContext() {
        return application;
    }

    @Provides
    ResourceProvider provideResourceProvider() {
        return new ResourceProviderImpl(application);
    }

    @Provides
    public SharedPreferences getPreferences() {
        return application.getSharedPreferences(Constants.PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public DBHelper getDBHelper() {
        return new DBHelper(application);
    }

    @Provides
    public TagRepository provideTagRepository(DBHelper helper) {
        return new TagRepositoryImpl(helper);
    }
}
