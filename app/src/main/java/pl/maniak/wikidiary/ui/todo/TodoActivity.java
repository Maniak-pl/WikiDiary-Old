package pl.maniak.wikidiary.ui.todo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.utils.helpers.DateHelper;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.ui.BaseActivity;
import pl.maniak.wikidiary.utils.di.todo.DaggerTodoComponent;
import pl.maniak.wikidiary.utils.di.todo.TodoModule;

public class TodoActivity extends BaseActivity implements TodoContract.View, TodoContract.Router {

    @BindView(R.id.todo_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.todo_new_task_button)
    FloatingActionButton newTaskButton;

    @Inject
    TodoContract.Presenter presenter;

    @Inject
    TodoRecyclerViewAdapter adapter;

    @Inject
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo;
    }

    @Override
    protected void initDaggerComponent() {
        DaggerTodoComponent.builder()
                .appComponent(getAppComponent())
                .todoModule(new TodoModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        presenter.attachView(this);
        presenter.attachRouter(this);
        initRecycler();
    }

    private void initRecycler() {
        adapter.setOnClickListener(taksId -> presenter.onTaskClicked(taksId));
        adapter.setOnCheckListener(taksId -> presenter.onDoneChecked(taksId));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void clear() {
        presenter.detachView();
        presenter.detachRouter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResumed();
    }

    @Override
    protected void onPause() {
        presenter.onPauseCalled();
        super.onPause();
    }


    @Override
    public void showTasks(List<Task> list) {
        adapter.updateDataSet(list);
    }

    @Override
    public void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
        ListView modeListView = new ListView(TodoActivity.this);
        String[] modes = new String[]{"Edycja zadania", "Usunięcie zadania"};
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(TodoActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, modes);
        modeListView.setAdapter(modeAdapter);
        builder.setView(modeListView);
        final Dialog dialog = builder.create();
        dialog.show();
        modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // edycja zadania
                if (position == 0) {
                    presenter.onEditTaskOptionClicked();
                } else {
                    presenter.onDeleteTaskOptionClicked();
                }
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.todo_new_task_button)
    void onNewTaskClicked() {
        presenter.onNewTaskButtonClicked();
    }


    @Override
    public void showNewTaskEditor() {
        openTaskDialog(null);
    }

    @Override
    public void showEditTaskEditor(Task task) {
        openTaskDialog(task);
    }

    private void openTaskDialog(final Task task) {
        // własne okno dialogowe
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_todo_edit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView titleView = (TextView) dialog.findViewById(R.id.custom_title);
        final EditText editCustom =
                (EditText) dialog.findViewById(R.id.custom_edit_todo);
        Button commitButton = (Button) dialog.findViewById(R.id.custom_button_commit);
        final TextView dateTv = (TextView) dialog.findViewById(R.id.custom_date);
        LinearLayout rootLayout =
                (LinearLayout) dialog.findViewById(R.id.custom_root_layout);
        final boolean isEditOperation = (task != null);

        // dotyczy edycji
        if (isEditOperation) {
            titleView.setText("Edycja zadania");
            dateTv.setText(DateHelper.parseDateToStringWithDayName(task.getDate()));
            editCustom.setText(task.getContent());
        } else {
            dateTv.setVisibility(View.GONE);
        }

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditOperation) {
                    presenter.onCommitEditTaskButtonClicked(editCustom.getText().toString());
                } else {
                    presenter.onCommitNewTaskButtonClicked(editCustom.getText().toString());
                }
                dialog.dismiss();
            }
        });

        Button buttonCancel = (Button) dialog.findViewById(R.id.custom_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
