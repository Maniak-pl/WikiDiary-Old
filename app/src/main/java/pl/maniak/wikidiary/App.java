package pl.maniak.wikidiary;

import android.app.Application;

import pl.maniak.wikidiary.di.AppComponent;
import pl.maniak.wikidiary.di.AppModule;
import pl.maniak.wikidiary.di.DaggerAppComponent;

/**
 * Created by maniak on 01.03.16.
 */
public class App extends Application {

    private static App instance = new App();
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
