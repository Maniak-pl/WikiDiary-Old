package pl.maniak.wikidiary.section.todo;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.maniak.wikidiary.R;

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
            // dodaj przykładowe dane
            insertSomeTodo();
        }

        Cursor cursor = mDbAdapter.fetchAllTodo();
        // z kolumn zdefiniowanych w bazie danych
        String[] from = new String[] {
                TodoDbAdapter.COL_CONTENT
        };

        //do identyfikatorów widoków w układzie graficznym
        int[] to = new int[] {
                R.id.todo_row_tv
        };

        mCursorAdapter = new TodoSimpleCursorAdapter(this, R.layout.todo_row, cursor, from, to, 0);
        // cursorAdapter(kontroler) aktualizuje ListView(widok)
        // danymi z bazy danych(model)
        mListView.setAdapter(mCursorAdapter);

    }

    private void insertSomeTodo() {
        mDbAdapter.createTodo("Zakup książki", new Date(1470546000000l));
        mDbAdapter.createTodo("Wysłanie prezentu ojcu", new Date(1472101200000l));
        mDbAdapter.createTodo("Piątkowy obiad ze znajomymi", new Date(1472101200000l));
        mDbAdapter.createTodo("Gra w squasha", new Date(1472101200000l));
        mDbAdapter.createTodo("Odgarnąć i posolić podjazd", new Date(1472101200000l));
        mDbAdapter.createTodo("Przygotować program zajęć z Androida", new Date(1472101200000l));
        mDbAdapter.createTodo("Kupić nowe krzesło do biura", new Date(1472101200000l));
        mDbAdapter.createTodo("Zadzwonić do mechanika", new Date(1471410000000l));
        mDbAdapter.createTodo("Odnowić członkostwo w klubie", new Date(1472101200000l));
        mDbAdapter.createTodo("Kupić nowy telefon Android Galaxy", new Date(1471582800000l));
        mDbAdapter.createTodo("Sprzedać stary telefon android - aukcja", new Date(1472101200000l));
        mDbAdapter.createTodo("Kupić nowe wiosła do kajaka", new Date(1472101200000l));
        mDbAdapter.createTodo("Zadzwonić do księgowego", new Date(1472101200000l));
        mDbAdapter.createTodo("Kupić 300 000 akcji Google", new Date(1472101200000l));
        mDbAdapter.createTodo("Oddzwonić do Dalai Lamy", new Date(1472101200000l));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_todo:
                 // Utworzenie nowego zadania
                Log.d(TAG, "onOptionsItemSelected: Add todo");
                return true;
            default:
                return false;
        }
    }
}
