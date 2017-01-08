package pl.maniak.wikidiary;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import de.greenrobot.event.EventBus;
import pl.maniak.wikidiary.utils.di.AppComponent;
import pl.maniak.wikidiary.utils.di.AppModule;
import pl.maniak.wikidiary.events.CommandEvent;
import pl.maniak.wikidiary.utils.di.DaggerAppComponent;

/**
 * Created by maniak on 01.03.16.
 */
public class App extends Application {

    private static App instance = new App();
    private static AppComponent appComponent;
    SharedPreferences preferences;

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





    public static void postMessage(int resMsg) {
        EventBus.getDefault().post(new CommandEvent(CommandEvent.SHOW_ERROR, App.getInstance().getString(resMsg)));
    }

    public static void postMessage(String msg) {
        EventBus.getDefault().post(new CommandEvent(CommandEvent.SHOW_ERROR, msg));
    }

    public static void postEvent(int syncEvent) {
        EventBus.getDefault().post(new CommandEvent(syncEvent, ""));
    }
    public static void postEvent(int syncEvent, String mesage) {
        EventBus.getDefault().post(new CommandEvent(syncEvent, mesage));
    }

    private SharedPreferences getPreferences() {
        if(preferences == null ) {
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
        }
        return preferences;
    }

    public String getPrefString(String key) {
        return getPreferences().getString(key, "");
    }

    public Boolean getPrefBoolea(String key){
        return getPreferences().getBoolean(key, false);
    }


}
