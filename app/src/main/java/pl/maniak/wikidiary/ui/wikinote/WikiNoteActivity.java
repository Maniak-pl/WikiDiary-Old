package pl.maniak.wikidiary.ui.wikinote;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.activitys.SettingsActivity;
import pl.maniak.wikidiary.modals.CommandDialogFragment;
import pl.maniak.wikidiary.ui.BaseActivity;
import pl.maniak.wikidiary.ui.todo.TodoActivity;
import pl.maniak.wikidiary.ui.wikinote.add.AddNoteFragment;
import pl.maniak.wikidiary.utils.di.wikinote.DaggerWikiNoteComponent;
import pl.maniak.wikidiary.utils.di.wikinote.WikiNoteModule;


public class WikiNoteActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, WikiNoteContract.View, WikiNoteContract.Router, AddNoteFragment.FragmentCallback {

    @BindView(R.id.container)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Inject
    WikiNoteContract.Presenter presenter;

    @Inject
    WikiNotePagerAdapter pagerAdapter;

    @Inject
    CommandDialogFragment commandDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wiki_note;
    }

    @Override
    protected void initDaggerComponent() {
        DaggerWikiNoteComponent.builder()
                .appComponent(getAppComponent())
                .wikiNoteModule(new WikiNoteModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        presenter.attachView(this);
        presenter.attachRouter(this);

        setViewPager();
        setToolBar();
        setNavigationView();
        setDrawerLayout();
        setTabLayout();
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNavigationView() {

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
    }

    private void setViewPager() {
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void clear() {
        presenter.detachView();
        presenter.detachRouter();
        viewPager.clearOnPageChangeListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                presenter.onSettingsClicked();
                return true;
            case R.id.action_todo:
                presenter.onNewTaskClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                presenter.onSettingsClicked();
                break;
            case R.id.nav_s_health:
//                presenter.startSHealthDialog();
                break;
            case R.id.nav_time:
                break;
            case R.id.nav_mic:
//                presenter.startVoiceRecognitionDialog();
                break;
            case R.id.nav_send:
//                presenter.sendMail();
                break;
            case R.id.nav_add_tag:
//                presenter.showAddTagDialog();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void navigateToSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void navigateToNewTask() {
        startActivity(new Intent(this, TodoActivity.class));
    }

    @Override
    public void onCommandClicked() {
        commandDialog.show(getSupportFragmentManager(), "QuickCommands");
    }
}
