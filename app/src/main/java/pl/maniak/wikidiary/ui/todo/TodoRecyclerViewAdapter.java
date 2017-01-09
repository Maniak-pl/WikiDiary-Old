package pl.maniak.wikidiary.ui.todo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.domain.todo.Task;

@RequiredArgsConstructor
public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoViewHolder> {

    private final List<Task> dataSet;

    @Setter
    private OnTodoClickedListener onClickListener;
    @Setter
    private OnTodoCheckListener onCheckListener;

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);

        TodoViewHolder viewHolder = new TodoViewHolder(view);
        viewHolder.setOnClickedListener(onClickListener);
        viewHolder.setOnCheckListener(onCheckListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        Task task = dataSet.get(position);
        holder.setItem(task);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateDataSet(List<Task> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public interface OnTodoClickedListener {
        void onTaskClicked(Task task);
    }

    public interface OnTodoCheckListener {
        void onTaskChecked(Task task);
    }


}
