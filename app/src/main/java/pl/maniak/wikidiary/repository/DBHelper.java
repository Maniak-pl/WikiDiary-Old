package pl.maniak.wikidiary.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.utils.L;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "wikidiary-database";
    private static final int DATABASE_VERSION = 1;

    private Dao<WikiNote, Long> wikiNoteDao = null;
    private Dao<Tag, Long> tagDao = null;
    private Dao<Task, Long> taskDao = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, WikiNote.class);
            TableUtils.createTable(connectionSource, Tag.class);
            TableUtils.createTable(connectionSource, Task.class);
        } catch (SQLException e) {
            L.e("DBHelper.onCreate()", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, WikiNote.class, true);
            TableUtils.dropTable(connectionSource, Tag.class, true);
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            L.e("DBHelper.onUpgrade()", e);
        }
    }

    public Dao<Task, Long> getTaskDao() throws SQLException {
        if (taskDao == null) {
            taskDao = getDao(Task.class);
        }
        return taskDao;
    }

    public Dao<WikiNote, Long> getWikiNoteDao() throws SQLException {
        if (wikiNoteDao == null) {
            wikiNoteDao = getDao(WikiNote.class);
        }
        return wikiNoteDao;
    }

    public Dao<Tag, Long> getTagDao() throws SQLException{
        if(tagDao == null){
            tagDao = getDao(Tag.class);
        }
        return tagDao;
    }
}
