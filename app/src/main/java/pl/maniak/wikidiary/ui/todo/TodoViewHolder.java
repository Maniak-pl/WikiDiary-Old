package pl.maniak.wikidiary.ui.todo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import lombok.Setter;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.todo.repository.TodoRepository;
import pl.maniak.wikidiary.utils.L;
import pl.maniak.wikidiary.utils.config.ResourceProvider;

public class TodoViewHolder extends RecyclerView.ViewHolder {


    private final long DAY = 1000 * 60 * 60 * 24;
    private final long THREE_DAYS = DAY * 3;
    private final long SEVEN_DAYS = DAY * 7;
    private final long THIRTY_DAYS = DAY * 30;

    @BindView(R.id.todo_row_tv)
    TextView content;

    @BindView(R.id.todo_row_tab)
    View tab;

    @BindView(R.id.todo_row_cb)
    CheckBox checkBox;

    private final Context context;
    private Task task;

    @Setter
    private TodoRecyclerViewAdapter.OnTodoClickedListener onClickedListener;
    @Setter
    private TodoRecyclerViewAdapter.OnTodoCheckListener onCheckListener;

    public TodoViewHolder(View view) {
        super(view);

        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setItem(Task task) {
        this.task = task;

        content.setText(task.getContent());
        if (isDateOlderThanSetInterval(task, THIRTY_DAYS)) {
            tab.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        } else if (isDateOlderThanSetInterval(task, SEVEN_DAYS)) {
            tab.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        } else if (isDateOlderThanSetInterval(task, THREE_DAYS)) {
            tab.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
        } else {
            tab.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }

       if(task.isDone()) {
           tab.setVisibility(View.GONE);
           checkBox.setVisibility(View.INVISIBLE);
       } else {
           tab.setVisibility(View.VISIBLE);
           checkBox.setVisibility(View.VISIBLE);
       }
    }

    @OnClick(R.id.todo_row_root)
    void onRootClicked() {
        if (onClickedListener != null && !task.isDone()) {
            onClickedListener.onTaskClicked(task.getId());
        }
    }

    @OnCheckedChanged(R.id.todo_row_cb)
    void onRootChecked() {
        if(onCheckListener != null) {
            onCheckListener.onTaskChecked(task.getId());
        }
    }

    private boolean isDateOlderThanSetInterval(Task task, long interval) {
        return task.getDate().getTime() < (new Date().getTime() - interval);
    }
}
