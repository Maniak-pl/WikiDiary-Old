package pl.maniak.wikidiary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.todo.repository.TodoRepository;
import pl.maniak.wikidiary.utils.L;

public class TodoRepositoryImpl extends OrmLiteSqliteOpenHelper implements TodoRepository {

    private static final String DATABASE_NAME = "todo-database";
    private static final int DATABASE_VERSION = 1;

    private Dao<Task, Long> dao = null;


    public TodoRepositoryImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        L.e("TodoRepositoryImpl() - Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Task.class);
            L.e("TodoRepositoryImpl.onCreate()");

        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.onCreate()", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.onUpgrade()", e);
        }
    }

    private Dao<Task, Long> getDao() throws SQLException {
        if (dao == null) {
            dao = getDao(Task.class);
        }
        return dao;
    }

    @Override
    public Task getTaskById(Long id) {
        Task task = new Task();
        try {
            task = getDao().queryForId(id);
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.getTaskById()", e);
        }
        return task;
    }

    @Override
    public void saveTask(Task task) {
        try {
            getDao().createOrUpdate(task);
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.save()", e);
        }
    }

    @Override
    public void deleteTaskById(Long id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.deleteTaskById()", e);
        }
    }

    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList();
        try {
            list = getDao().queryForAll();
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.getAllTasks()", e);
        }
        return list;
    }
}
