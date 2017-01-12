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
import pl.maniak.wikidiary.ui.BaseFragment;
import pl.maniak.wikidiary.utils.di.wikinote.DaggerEditNoteFragmentComponent;
import pl.maniak.wikidiary.utils.di.wikinote.EditNoteFragmentModule;

import static pl.maniak.wikidiary.App.getAppComponent;

/**
 * Created by mac on 12.01.2017.
 */

public class EditNoteFragmentImpl extends BaseFragment implements EditNoteFragment {

    @BindView(R.id.edit_note_recycler_view)
    RecyclerView recyclerView;

    @Inject
    RecyclerView.LayoutManager layoutManager;

    @Inject
    EditNoteRecyclerViewAdapter adapter;

    public static EditNoteFragmentImpl newInstance() {

        Bundle args = new Bundle();
        EditNoteFragmentImpl fragment = new EditNoteFragmentImpl();
        fragment.setArguments(args);
        return fragment;
    }

    public EditNoteFragmentImpl() {
        // Required empty public constructor
    }

    @Override
    protected void initDaggerComponent() {
        DaggerEditNoteFragmentComponent.builder()
                .appComponent(getAppComponent())
                .editNoteFragmentModule(new EditNoteFragmentModule(this))
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
        showNotes(App.getAppComponent().getWikiNoteRepository().getNotes());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_edit_note;
    }

    @Override
    public void showNotes(List<WikiNote> list) {
        adapter.updateDataSet(list);
    }
}
