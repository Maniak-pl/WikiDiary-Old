package pl.maniak.wikidiary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.db.DBHelper;
import pl.maniak.wikidiary.helpers.DateHelper;
import pl.maniak.wikidiary.helpers.WikiParser;
import pl.maniak.wikidiary.models.WikiNote;

/**
 * Created by maniak on 02.03.16.
 */
public class PreparingNoteFragment extends Fragment {

    @Bind(R.id.preparingNotePage)
    TextView mPage;

    @Inject
    public DBHelper dbHelper;
    private Map<String, Map<String, List<String>>> dateMap;

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
            preparingEntryOnWiki();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void preparingEntryOnWiki() {


        dateMap = new HashMap<>();
        List<WikiNote> noteList = dbHelper.getWikiNotes();

        for(WikiNote wiki: noteList) {
            addTitleWiki(wiki);
        }


        List<String> allLinesNote = new ArrayList<>();


        for(String day: dateMap.keySet()) {
            allLinesNote.add(WikiParser.addHeadline(day, 2));
            allLinesNote.add("");
            for(String category: dateMap.get(day).keySet()) {
                allLinesNote.add(WikiParser.addListBold(category, 1));
                for(String note: dateMap.get(day).get(category)) {
                    allLinesNote.add(WikiParser.addList(note, 2));
                }
                allLinesNote.add("");
            }


        }

        StringBuffer wiki = new StringBuffer("");

        for (int i = 0; i < allLinesNote.size(); i++) {
            wiki.append(allLinesNote.get(i) + "\n");
        }
        mPage.setText(wiki.toString());

    }

    public void addTitleWiki(WikiNote wikiNote) {
        final String data = DateHelper.parseDateToString(wikiNote.getDate());
        if(!dateMap.containsKey(data)) {
            dateMap.put(data, new HashMap<String, List<String>>());
        }
        addCategory(data, wikiNote);
    }

    public void addCategory(String date, WikiNote wikiNote) {
        Map<String, List<String>> category = dateMap.get(date);

        if(!category.containsKey(wikiNote.getTag())) {
            category.put(wikiNote.getTag(), new ArrayList<String>());
        }

        addNote(category, wikiNote);

    }

    public void addNote(Map<String, List<String>> category, WikiNote wikiNote) {
        category.get(wikiNote.getTag()).add(wikiNote.getDescription());

    }


}
