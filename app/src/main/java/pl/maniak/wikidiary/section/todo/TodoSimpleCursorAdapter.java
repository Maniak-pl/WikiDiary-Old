package pl.maniak.wikidiary.section.todo;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import java.util.Date;

import pl.maniak.wikidiary.R;

/**
 * Created by Piotr on 2016-08-25.
 */
public class TodoSimpleCursorAdapter extends SimpleCursorAdapter {


    private final int WEEK = 1000 * 60 * 60 * 24 * 7;

    public TodoSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    // aby użyć viewholder, należy przesłonić dwie poniższe metody i zdefiniować klasę ViewHolder
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.colDate = cursor.getColumnIndexOrThrow(TodoDbAdapter.COL_DATE);
            holder.listTab = view.findViewById(R.id.todo_row_tab);
            view.setTag(holder);
        }if (cursor.getLong(holder.colDate) < (new Date().getTime() - WEEK)) {
            holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.orange));
        } else {
            holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }
    static class ViewHolder {
        // zapamiętanie indeksu kolumny
        int colDate;
        // zapamiętanie widoku
        View listTab;
    }
}
