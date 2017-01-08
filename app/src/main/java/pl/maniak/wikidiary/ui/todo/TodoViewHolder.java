package pl.maniak.wikidiary.ui.todo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.utils.config.ResourceProvider;

public class TodoViewHolder extends RecyclerView.ViewHolder {

    private final int WEEK = 1000 * 60 * 60 * 24 * 7;

    @BindView(R.id.todo_row_tv)
    TextView content;

    @BindView(R.id.todo_row_tab)
    View tab;

    private final Context context;
    private Task task;

    @Setter
    private TodoRecyclerViewAdapter.OnTodoClickedListener onClickedListener;

    public TodoViewHolder(View view) {
        super(view);

        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setItem(Task task) {
        this.task = task;

        content.setText(task.getContent());
        if (isDateOlderThanWeek(task)) {
            tab.setBackgroundColor(ContextCompat.getColor(context,R.color.orange));
        } else {
            tab.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
    }

    @OnClick(R.id.todo_row_root)
    void onRootClicked() {
        if (onClickedListener != null) {
            onClickedListener.onTaskClicked(task);
        }
    }

    private boolean isDateOlderThanWeek(Task task) {
        return task.getDate().getTime() < (new Date().getTime() - WEEK);
    }
}
