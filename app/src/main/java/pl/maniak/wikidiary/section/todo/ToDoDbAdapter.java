package pl.maniak.wikidiary.section.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by Piotr on 2016-08-25.
 */
public class TodoDbAdapter {

    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_DATE = "date";

    // dotyczące ich indeksy
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_DATE = INDEX_ID + 2;

    //używane w dzienniku zdarzeń
    private static final String TAG = "Maniak DB";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_todo";
    private static final String TABLE_NAME = "tbl_todo";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    // polecenie SQL służące do wtowrzenia bazy danych
    private static final String DATABASE_CREATE = "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
            COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
            COL_CONTENT + " TEXT, " +
            COL_DATE + " INTEGER );";

    public TodoDbAdapter(Context ctx) {
        mCtx = ctx;
    }

    // otwarcie
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    // zamknięcie
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    // TWORZENIE
    // pamiętaj, że id zostanie utworzony automatycznie
    public void createTodo(String name, Date date) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, name);
        values.put(COL_DATE, date.getTime());
        mDb.insert(TABLE_NAME, null, values);
    }

    // przeciążenie pobierające obiekt przypomnienia
    public long createTodo(ToDo todo) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, todo.getContent()); // treść
        values.put(COL_DATE, todo.getDate().getTime()); // data
        // wstawianie wiersza
        return mDb.insert(TABLE_NAME, null, values);
    }

    // ODCZYT
    public ToDo fetchTodoById(int id) {
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_CONTENT, COL_DATE}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();

        return new ToDo(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_CONTENT),
                new Date(cursor.getLong(INDEX_DATE))
        );
    }

    public Cursor fetchAllTodo() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_CONTENT, COL_DATE},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // AKTUALIZACJA
    public void updateTodo(ToDo todo) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, todo.getContent());
        values.put(COL_DATE, todo.getDate().getTime());
        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(todo.getId())});
    }

    // USUNIĘCIE
    public void deleteTodoById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }

    public void deleteAllTodo() {
        mDb.delete(TABLE_NAME, null, null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Aktualizacja bazy danych z wersji " + oldVersion + " do wersji " + newVersion + ", co powoduje wyczyszcenie zawartości bazy danych.");
            db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
            onCreate(db);
        }
    }

}
