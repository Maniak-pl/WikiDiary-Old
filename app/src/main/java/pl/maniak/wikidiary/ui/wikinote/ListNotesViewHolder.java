package pl.maniak.wikidiary.ui.wikinote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.helpers.DateHelper;

/**
 * Created by mac on 12.01.2017.
 */
public class ListNotesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_notes_content)
    TextView content;

    @BindView(R.id.list_notes_date)
    TextView date;

    @BindView(R.id.list_notes_tag)
    TextView tag;

    private final Context context;
    private WikiNote note;

    @Setter
    private ListNotesRecyclerViewAdapter.OnEditNoteClickedListener onClickListener;

    public ListNotesViewHolder(View itemView) {
        super(itemView);

        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setItem(WikiNote note) {
        this.note = note;
        content.setText(note.getDescription());
        date.setText(DateHelper.parseDateToString(note.getDate()));
        tag.setText(note.getTag());
    }

    @OnClick(R.id.list_notes_card_view)
    void onNoteClicked() {
        if(onClickListener != null) {
            onClickListener.onEditNoteClicked(note.getDbId());
        }
    }

}
