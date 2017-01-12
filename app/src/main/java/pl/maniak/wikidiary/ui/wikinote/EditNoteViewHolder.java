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

/**
 * Created by mac on 12.01.2017.
 */
public class EditNoteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.edit_note_content)
    TextView content;

    private final Context context;
    private WikiNote note;

    @Setter
    private EditNoteRecyclerViewAdapter.OnEditNoteClickedListener onClickListener;

    public EditNoteViewHolder(View itemView) {
        super(itemView);

        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setItem(WikiNote note) {
        this.note = note;
        content.setText(note.getDescription());
    }

    @OnClick(R.id.edit_note_card_view)
    void onNoteClicked() {
        if(onClickListener != null) {
            onClickListener.onEditNoteClicked(note.getDbId());
        }
    }

}
