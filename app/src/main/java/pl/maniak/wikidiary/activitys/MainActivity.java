package pl.maniak.wikidiary.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.Constants;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.db.DBHelper;
import pl.maniak.wikidiary.events.CommandEvent;
import pl.maniak.wikidiary.fragments.MainFragment;
import pl.maniak.wikidiary.fragments.PreparingNoteFragment;
import pl.maniak.wikidiary.fragments.SettingsFragment;
import pl.maniak.wikidiary.helpers.WikiHelper;
import pl.maniak.wikidiary.helpers.WikiParser;
import pl.maniak.wikidiary.modals.AddTagDialogFragment;
import pl.maniak.wikidiary.modals.NumberKeyboardDialogFragment;
import pl.maniak.wikidiary.modals.VoiceNoteDialogFragment;
import pl.maniak.wikidiary.models.WikiNote;
import pl.maniak.wikidiary.section.todo.TodoActivity;
import pl.maniak.wikidiary.utils.L;
import pl.maniak.wikidiary.utils.Mail;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    @Inject
    public DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        App.getAppComponent().inject(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
//        testFilmWebApi();
    }

//    private void testFilmWebApi() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                FilmwebApi fa = new FilmwebApi();
//                List<FilmSearchResult> filmInfoList = fa.getFilmList("X-Men Geneza");
//
//                for (FilmSearchResult film : filmInfoList) {
//
//                    /**
//                     * Możemy przejrzeć dane z wyszukiwarki...
//                     */
//                    L.e("run: "
////                          +  "\ngetId: "+        film.getId()           // ID filmu
//                    +  "\ngetTitle: "+ film.getTitle()        // Tytuł filmu
//                    +  "\ngetPolishTitle: "+ film.getPolishTitle()  // Polski tytuł
////                    +  "\ngetImageURL: "+ film.getImageURL()     // Adres URL plakatu
//                    +  "\ngetDescriptions: "+ fa.getDescriptions(film.getId())     // Adres URL plakatu
//                    );
//
//                }
//            }
//        }).start();
//    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startSettings();
                return true;
            case R.id.action_todo:
                startToDo();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startSettings();
                break;
            case R.id.nav_s_health:
                startSHealthDialog();
                break;
            case R.id.nav_time:
                break;
            case R.id.nav_mic:
                startVoiceRecognitionDialog();
                break;
            case R.id.nav_send:
                sendMail();
                break;
            case R.id.nav_add_tag:
                showAddTagDialog();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendMail() {
        if (App.getInstance().getPrefBoolea(Constants.SEND_EMAIL_MESSAGE)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendEmail(WikiHelper.preparingEntryOnWiki(dbHelper.getWikiNotes()));
                }
            }).start();
        } else {
            App.postMessage(R.string.turn_on_mail);
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return MainFragment.newInstance();
                case 1:
                    return PreparingNoteFragment.newInstance();
                default:
                    return SettingsFragment.newInstance();
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Main";
                case 1:
                    return "Preparing Note";
                case 2:
                    return "Settings";
            }
            return null;
        }
    }


    public void onEventMainThread(CommandEvent event) {
        L.w("BaseActivity.onEventMainThread() called with " + "syncEvent = [" + event.getStatus() + "]");

        switch (event.getStatus()) {
            case CommandEvent.START:
                showProgress();
                break;
            case CommandEvent.STOP:
                stopProgress();
                break;
            case CommandEvent.CLEAR:
                stopProgress();
                dbHelper.deleteAllWikiNote();
                break;
            case CommandEvent.SHOW_ERROR:
                showError(event.getMessage());
                break;
            case CommandEvent.SHOW_HEALTH_RESULT:
                int steps = Integer.parseInt(event.getMessage());
                dbHelper.addWikiNote(new WikiNote(Constants.TAG_S_HEALTH, WikiParser.putHealth(steps), new Date()));
                break;
            case CommandEvent.SHOW_VOICE:
                startVoiceRecognitionDialog();
                break;
            case CommandEvent.SHOW_HEALTH:
                startSHealthDialog();
                break;
            case CommandEvent.SHOW_ADD_TAG:
                showAddTagDialog();
                break;
            case CommandEvent.SHOW_VOICE_RESULT:
                final String note = event.getMessage();
                mViewPager.setCurrentItem(0);

                App.postEvent(CommandEvent.SHOW_VOICE_NOTE, note);
                break;

        }
    }

    public void sendEmail(String str) {
        Mail mail = new Mail(App.getInstance().getPrefString(Constants.SEND_EMAIL_FROM), App.getInstance().getPrefString(Constants.SEND_EMAIL_PASSWORD));
        App.postEvent(CommandEvent.START);
        String[] toArr = {App.getInstance().getPrefString(Constants.SEND_EMAIL_TO)}; // This is an array, you can add more emails, just separate them with a coma
        mail.setTo(toArr); // load array to setTo function
        mail.setFrom(App.getInstance().getPrefString(Constants.SEND_EMAIL_FROM)); // who is sending the email
        mail.setSubject(App.getInstance().getPrefString(Constants.SEND_EMAIL_TITLE));
        mail.setBody(str);

        try {
            if (mail.send()) {
                // success
                App.postMessage(R.string.email_successfully);
                App.postEvent(CommandEvent.CLEAR);

            } else {
                // failure
                App.postMessage(R.string.email_failure);

            }
        } catch (Exception e) {
            Log.e("Maniak", "MainActivity.sendEmail() ", e);
            App.postMessage(getString(R.string.email_failure) + "\nException: " + e.getMessage());
        } finally {
            App.postEvent(CommandEvent.STOP);
        }
    }

    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    private void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startToDo() {
        Intent intent = new Intent(this, TodoActivity.class);
        startActivity(intent);
    }

    private void startSHealthDialog() {
        NumberKeyboardDialogFragment keyboardDialog = NumberKeyboardDialogFragment.newInstance("S Health");
        keyboardDialog.show(getSupportFragmentManager(), "Number_Keybord");
    }

    private void showAddTagDialog() {
        AddTagDialogFragment fragment = AddTagDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), "Add Tag");
    }



    public void startVoiceRecognitionDialog() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Wprowadź notatkę");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            VoiceNoteDialogFragment voiceNoteDialogFragment = VoiceNoteDialogFragment.newInstance(matches);
            voiceNoteDialogFragment.show(getSupportFragmentManager(), "VoiceNote");

        }
    }





         /*
   _____ _                     _____
  / ____| |                   |  __ \
 | (___ | |__   _____      __ | |__) | __ ___   __ _ _ __ ___  ___ ___
  \___ \| '_ \ / _ \ \ /\ / / |  ___/ '__/ _ \ / _` | '__/ _ \/ __/ __|
  ____) | | | | (_) \ V  V /  | |   | | | (_) | (_| | | |  __/\__ \__ \
 |_____/|_| |_|\___/ \_/\_/   |_|   |_|  \___/ \__, |_|  \___||___/___/
                                                __/ |
                                               |___/
     */

    private ProgressDialog mProgressDialog;


    public void showProgress() {
//        L.i("BaseActivity.showProgress() ");

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(R.layout.progres_bar);
    }

    public void stopProgress() {
//        L.i("BaseActivity.stopProgress() ");
        if (mProgressDialog != null)
            mProgressDialog.dismiss();

    }
}
