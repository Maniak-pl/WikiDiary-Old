package pl.maniak.wikidiary.ui.wikinote.prepare;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.repository.wikinote.WikiNoteRepository;
import pl.maniak.wikidiary.utils.log.L;
import pl.maniak.wikidiary.utils.helpers.WikiHelper;

import static android.content.Context.CLIPBOARD_SERVICE;


public class PreparingNoteFragment extends Fragment {

    @BindView(R.id.preparingNotePage)
    TextView mPage;

    @BindView(R.id.copyButton)
    TextView mCopyButton;

    @BindView(R.id.switchButton)
    TextView mSwitchButton;

    @BindView(R.id.textScrean)
    ScrollView mTextScrean;

    @BindView(R.id.webScrean)
    WebView mWebScrean;

    boolean isVisible = false;

    @Inject
    public WikiNoteRepository repository;

    public static PreparingNoteFragment newInstance() {
        Bundle args = new Bundle();
        PreparingNoteFragment fragment = new PreparingNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_preparing_note, null);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this, root);
        initListener();
        return root;
    }

    private void initListener() {
        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(mPage.getText());
            }
        });

        mSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVisible();
            }
        });
    }

    private void changeVisible() {
        L.e("changeVisible() isVisibile = "+isVisible);
        if(isVisible) {
            mWebScrean.setVisibility(View.GONE);
            mTextScrean.setVisibility(View.VISIBLE);
        } else {
            mWebScrean.setVisibility(View.VISIBLE);
            mTextScrean.setVisibility(View.GONE);
        }
        isVisible = !isVisible;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mPage != null) {
            initScrean();
        }
    }

    private void initScrean() {
        mPage.setText(WikiHelper.preparingEntryOnWiki(repository.getNotes()));
        mWebScrean.loadUrl("http://google.pl");
        mWebScrean.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

    }

}
