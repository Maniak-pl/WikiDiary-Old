package pl.maniak.wikidiary.utils.di;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.maniak.wikidiary.api.TodoDBHelper;
import pl.maniak.wikidiary.api.TodoRepository;
import pl.maniak.wikidiary.utils.Constants;
import pl.maniak.wikidiary.db.DBHelper;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    public Context provideContext() {
        return context;
    }

    @Provides
    public SharedPreferences getPreferences() {
        return context.getSharedPreferences(Constants.PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public DBHelper getDBHelper() {
        return new DBHelper(context);
    }

}
