package pl.maniak.wikidiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.utils.L;

/**
 * Created by Sony on 2015-10-22.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "wikidiary-database";

    private static final int DATABASE_VERSION = 1;


    private Dao<WikiNote, Long> wikiNoteDao = null;
    private Dao<Tag, Long> tagDao = null;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, WikiNote.class);
            TableUtils.createTable(connectionSource, Tag.class);

        } catch (SQLException e) {
            L.e("DBHelper.onCreate()", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, WikiNote.class, true);
            TableUtils.dropTable(connectionSource, Tag.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            L.e("DBHelper.onUpgrade()", e);
        }
    }

    public Dao<WikiNote, Long> getDao() throws SQLException {
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

    public List<Tag> getAllTags(List<String> tagsNames) throws SQLException {
        List<Tag> tagsResult;

        tagsResult = getTagDao().queryBuilder().where().in("tag", tagsNames).query();

        return tagsResult;
    }

    public void addTag(Tag tag) throws SQLException{
        getTagDao().createOrUpdate(tag);
    }

    public void deleteTag(String tag) throws SQLException{
        DeleteBuilder<Tag,Long> delete = getTagDao().deleteBuilder();
        delete.where().eq("tag", tag);
        delete.delete();
    }

    public List<Tag> getAllTags() throws SQLException{
        List<Tag> tags = new ArrayList<>(getTagDao().queryForAll());

        return tags;
    }


    public void addWikiNote(WikiNote wikiNote) {
        L.i("DBHelper.addWikiNote() called with " + "wikiNote = [" + wikiNote.toString() + "]");
        try {
            getDao().createOrUpdate(wikiNote);
        } catch (SQLException e) {
            L.e("DBHelper.addWikiNote()", e);
        }
    }

    public List<WikiNote> getWikiNotes() {
        List<WikiNote> list = new ArrayList();
        try {
            list = getDao().queryForAll();
        } catch (SQLException e) {
            L.e("DBHelper.getWikiNotes()", e);
        }
        return list;
    }

    public List<WikiNote> getWikiNotesWithTag(String tag) {
        List<WikiNote> list = new ArrayList();
        try {
            list = getDao().queryBuilder().where().eq("Tag", tag).query();
        } catch (SQLException e) {
            L.e("DBHelper.getWikiNotes()", e);
        }
        return list;
    }

    public void deleteAllWikiNote() {
        try {
            List<WikiNote> list = getWikiNotes();
            if (list != null && list.size() != 0) {
                getDao().delete(getWikiNotes());
            } else {
                L.d("Baza nie istnieje lub jest pusta ");
            }
        } catch (SQLException e) {
            L.e("DBHelper.deleteAllWikiNote()", e);
        }

    }
}
