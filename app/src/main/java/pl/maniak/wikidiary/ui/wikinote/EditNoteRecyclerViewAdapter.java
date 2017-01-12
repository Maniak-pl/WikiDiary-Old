package pl.maniak.wikidiary.ui.wikinote;

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
public class EditNoteRecyclerViewAdapter extends RecyclerView.Adapter<EditNoteViewHolder> {

    private final List<WikiNote> dataSet;

    @Setter
    private OnEditNoteClickedListener onClickListener;


    @Override
    public EditNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edit_note, parent, false);
        EditNoteViewHolder viewHolder = new EditNoteViewHolder(view);
        viewHolder.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EditNoteViewHolder holder, int position) {
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
