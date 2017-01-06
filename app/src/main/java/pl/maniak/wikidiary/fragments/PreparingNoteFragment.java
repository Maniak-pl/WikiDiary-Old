package pl.maniak.wikidiary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.db.DBHelper;
import pl.maniak.wikidiary.helpers.WikiHelper;

/**
 * Created by maniak on 02.03.16.
 */
public class PreparingNoteFragment extends Fragment {

    @Bind(R.id.preparingNotePage)
    TextView mPage;

    @Inject
    public DBHelper dbHelper;


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
            mPage.setText(WikiHelper.preparingEntryOnWiki(dbHelper.getWikiNotes()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }






}
