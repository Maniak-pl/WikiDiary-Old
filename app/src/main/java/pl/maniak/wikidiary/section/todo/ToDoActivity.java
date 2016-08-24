package pl.maniak.wikidiary.section.todo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.maniak.wikidiary.R;

/**
 * Created by Piotr on 2016-08-23.
 */
public class ToDoActivity extends AppCompatActivity {


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


}
