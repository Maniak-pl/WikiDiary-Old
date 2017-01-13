package pl.maniak.wikidiary.ui.wikinote;

import android.support.v4.view.ViewPager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.ui.BaseActivity;
import pl.maniak.wikidiary.utils.di.wikinote.DaggerWikiNoteComponent;
import pl.maniak.wikidiary.utils.di.wikinote.WikiNoteModule;


public class WikiNoteActivity extends BaseActivity implements WikiNoteContract.View, WikiNoteContract.Router {

    @BindView(R.id.container)
    ViewPager viewPager;

    @Inject
    WikiNoteContract.Presenter presenter;

    @Inject
    WikiNotePagerAdapter pagerAdapter;

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
    public void showTag(List<Tag> list) {

    }

    @Override
    public String getContent() {
        return null;
    }
}
