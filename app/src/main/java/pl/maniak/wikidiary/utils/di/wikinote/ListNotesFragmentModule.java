package pl.maniak.wikidiary.utils.di.wikinote;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.ui.wikinote.ListNotesFragmentImpl;
import pl.maniak.wikidiary.ui.wikinote.ListNotesRecyclerViewAdapter;

@Module
@RequiredArgsConstructor
public class ListNotesFragmentModule {

    private final ListNotesFragmentImpl fragment;

    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Provides
    ListNotesRecyclerViewAdapter provideAdapter() {
        return new ListNotesRecyclerViewAdapter(new ArrayList<>());
    }
}
