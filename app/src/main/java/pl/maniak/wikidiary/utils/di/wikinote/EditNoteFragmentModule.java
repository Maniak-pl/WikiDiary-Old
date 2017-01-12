package pl.maniak.wikidiary.utils.di.wikinote;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.ui.wikinote.EditNoteFragment;
import pl.maniak.wikidiary.ui.wikinote.EditNoteFragmentImpl;
import pl.maniak.wikidiary.ui.wikinote.EditNoteRecyclerViewAdapter;

@Module
@RequiredArgsConstructor
public class EditNoteFragmentModule {

    private final EditNoteFragmentImpl fragment;

    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Provides
    EditNoteRecyclerViewAdapter provideAdapter() {
        return new EditNoteRecyclerViewAdapter(new ArrayList<>());
    }
}
