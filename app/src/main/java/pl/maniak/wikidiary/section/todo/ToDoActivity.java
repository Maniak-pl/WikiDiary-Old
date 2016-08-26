package pl.maniak.wikidiary.section.todo;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.helpers.DateHelper;

/**
 * Created by Piotr on 2016-08-23.
 */
public class TodoActivity extends AppCompatActivity {


    private static final String TAG = "Maniak";
    ListView mListView;
    private TodoDbAdapter mDbAdapter;
    private TodoSimpleCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        mListView = (ListView) findViewById(R.id.todo_lv);
        mDbAdapter = new TodoDbAdapter(this);
        mDbAdapter.open();
        if (savedInstanceState == null) {
            // wyczyść wszystkie dane
            mDbAdapter.deleteAllTodo();
        }

        Cursor cursor = mDbAdapter.fetchAllTodo();
        // z kolumn zdefiniowanych w bazie danych
        String[] from = new String[]{
                TodoDbAdapter.COL_CONTENT
        };

        //do identyfikatorów widoków w układzie graficznym
        int[] to = new int[]{
                R.id.todo_row_tv
        };

        mCursorAdapter = new TodoSimpleCursorAdapter(this, R.layout.todo_row, cursor, from, to, 0);
        // cursorAdapter(kontroler) aktualizuje ListView(widok)
        // danymi z bazy danych(model)
        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
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
                            int nId = getIdFromPosition(masterListPosition);
                            ToDo todo = mDbAdapter.fetchTodoById(nId);
                            fireCustomDialog(todo);
                            // usunięcie zadania
                        } else {
                            mDbAdapter.deleteTodoById(getIdFromPosition(masterListPosition));
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllTodo());
                        }
                        dialog.dismiss();
                    }
                });

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                                                      boolean checked) {
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.cam_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_todo:
                            for (int nC = mCursorAdapter.getCount() - 1; nC >= 0; nC--) {
                                if (mListView.isItemChecked(nC)) {
                                    mDbAdapter.deleteTodoById(getIdFromPosition(nC));
                                }
                            }
                            mode.finish();
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllTodo());
                            return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }
            });
        }

    }

    private int getIdFromPosition(int nC) {
        return (int) mCursorAdapter.getItemId(nC);
    }

    private void fireCustomDialog(final ToDo todo) {
        // własne okno dialogowe
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_todo_edit);

        TextView titleView = (TextView) dialog.findViewById(R.id.custom_title);
        final EditText editCustom =
                (EditText) dialog.findViewById(R.id.custom_edit_todo);
        Button commitButton = (Button) dialog.findViewById(R.id.custom_button_commit);
        final TextView dateTv = (TextView) dialog.findViewById(R.id.custom_date);
        LinearLayout rootLayout =
                (LinearLayout) dialog.findViewById(R.id.custom_root_layout);
        final boolean isEditOperation = (todo != null);

        // dotyczy edycji
        if (isEditOperation) {
            titleView.setText("Edycja zadania");
            dateTv.setText(DateHelper.parseDateToString(todo.getDate()));
            editCustom.setText(todo.getContent());
        } else {
            dateTv.setVisibility(View.GONE);
        }

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = editCustom.getText().toString();
                if (isEditOperation) {
                    ToDo todoEdited = new ToDo(todo.getId(),
                            todoText, new Date());
                    mDbAdapter.updateTodo(todoEdited);
                    // nowe zadania
                } else {
                    mDbAdapter.createTodo(todoText, new Date());
                }
                mCursorAdapter.changeCursor(mDbAdapter.fetchAllTodo());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_todo:
                // Utworzenie nowego zadania
                fireCustomDialog(null);
                return true;
            default:
                return false;
        }
    }
}
