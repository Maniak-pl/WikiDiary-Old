package pl.maniak.wikidiary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.utils.helpers.WikiHelper;
import pl.maniak.wikidiary.repository.wikinote.WikiNoteRepository;


public class PreparingNoteFragment extends Fragment {

    @BindView(R.id.preparingNotePage)
    TextView mPage;

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
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mPage != null) {
            mPage.setText(WikiHelper.preparingEntryOnWiki(repository.getNotes()));
        }
    }
}
