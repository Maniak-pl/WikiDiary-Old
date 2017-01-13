package pl.maniak.wikidiary.ui.wikinote;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.repository.wikinote.WikiNoteRepository;
import pl.maniak.wikidiary.ui.BaseFragment;
import pl.maniak.wikidiary.utils.di.wikinote.DaggerListNotesFragmentComponent;
import pl.maniak.wikidiary.utils.di.wikinote.ListNotesFragmentModule;

import static pl.maniak.wikidiary.App.getAppComponent;

/**
 * Created by mac on 12.01.2017.
 */

public class ListNotesFragmentImpl extends BaseFragment implements ListNotesFragment {

    @BindView(R.id.edit_note_recycler_view)
    RecyclerView recyclerView;

    @Inject
    RecyclerView.LayoutManager layoutManager;

    @Inject
    ListNotesRecyclerViewAdapter adapter;

    @Inject
    WikiNoteRepository repository;


    public static ListNotesFragmentImpl newInstance() {

        Bundle args = new Bundle();
        ListNotesFragmentImpl fragment = new ListNotesFragmentImpl();
        fragment.setArguments(args);
        return fragment;
    }

    public ListNotesFragmentImpl() {
        // Required empty public constructor
    }

    @Override
    protected void initDaggerComponent() {
        DaggerListNotesFragmentComponent.builder()
                .appComponent(getAppComponent())
                .listNotesFragmentModule(new ListNotesFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void setFragmentCallback(Context context) {
    }

    @Override
    protected void init() {
        adapter.setOnClickListener(noteId -> {
            Toast.makeText(getContext(), "Id = "+noteId, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        showNotes(repository.getNotes());
    }

    @Override
    protected void clear() {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(null);
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_list_notes;
    }

    @Override
    public void showNotes(List<WikiNote> list) {
        adapter.updateDataSet(list);
    }
}
