package pl.maniak.wikidiary.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.maniak.wikidiary.db.DBHelper;
import pl.maniak.wikidiary.models.Tag;
import pl.maniak.wikidiary.models.Task;
import pl.maniak.wikidiary.utils.L;

public class TodoDBHelper extends OrmLiteSqliteOpenHelper implements TodoRepository {

    private static final String DATABASE_NAME = "todo-database";
    private static final int DATABASE_VERSION = 1;

    private Dao<Task, Long> dao = null;


    public TodoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        L.e("TodoDBHelper() - Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Task.class);
            L.e("TodoDBHelper.onCreate()");

        } catch (SQLException e) {
            L.e("TodoDBHelper.onCreate()", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            L.e("TodoDBHelper.onUpgrade()", e);
        }
    }

    private Dao<Task, Long> getDao() throws SQLException {
        if (dao == null) {
            dao = getDao(Task.class);
        }
        return dao;
    }

    @Override
    public Task getTask(Long id) {
        Task task = new Task();
        try {
            task = getDao().queryForId(id);
        } catch (SQLException e) {
            L.e("TodoDBHelper.getTask()", e);
        }
        return task;
    }

    @Override
    public void save(Task task) {
        try {
            getDao().createOrUpdate(task);
        } catch (SQLException e) {
            L.e("TodoDBHelper.save()", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            L.e("TodoDBHelper.delete()", e);
        }
    }

    public List<Task> getTasks() {
        List<Task> list = new ArrayList();
        try {
            list = getDao().queryForAll();
        } catch (SQLException e) {
            L.e("TodoDBHelper.getTasks()", e);
        }
        return list;
    }
}
