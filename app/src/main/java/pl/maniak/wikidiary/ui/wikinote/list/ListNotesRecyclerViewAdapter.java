package pl.maniak.wikidiary.ui.wikinote.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;

@RequiredArgsConstructor
public class ListNotesRecyclerViewAdapter extends RecyclerView.Adapter<ListNotesViewHolder> {

    private final List<WikiNote> dataSet;

    @Setter
    private OnEditNoteClickedListener onClickListener;


    @Override
    public ListNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wiki_note, parent, false);
        ListNotesViewHolder viewHolder = new ListNotesViewHolder(view);
        viewHolder.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListNotesViewHolder holder, int position) {
        WikiNote note = dataSet.get(position);
        holder.setItem(note);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateDataSet(List<WikiNote> dataSet){
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public interface OnEditNoteClickedListener {
        void onEditNoteClicked(long id);
    }
}
