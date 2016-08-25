package pl.maniak.wikidiary.section.todo;

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
import java.util.List;

import pl.maniak.wikidiary.R;

/**
 * Created by Piotr on 2016-08-23.
 */
public class ToDoActivity extends AppCompatActivity {


    private static final String TAG = "Maniak";
    ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        mListView = (ListView) findViewById(R.id.todo_lv);

        // Obiekt arrayAdapter jest w tym systemie MVC (model-vidok-kontroler) kontrolerem.
        List<String> list = new ArrayList<>();
        list.add("pierwszy wiersz");
        list.add("drugi wiersz");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.todo_row, R.id.todo_row_tv,list);
        mListView.setAdapter(arrayAdapter);
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
