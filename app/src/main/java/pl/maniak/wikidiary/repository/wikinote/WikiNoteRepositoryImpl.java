package pl.maniak.wikidiary.repository.wikinote;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.repository.DBHelper;
import pl.maniak.wikidiary.utils.L;

@RequiredArgsConstructor
public class WikiNoteRepositoryImpl implements WikiNoteRepository {

    private final DBHelper helper;

    private Dao<WikiNote, Long> getDao() throws SQLException {
        return helper.getWikiNoteDao();
    }

    @Override
    public List<WikiNote> getNotes() {
        List<WikiNote> list = new ArrayList();
        try {
            list = getDao().queryForAll();
        } catch (SQLException e) {
            L.e("WikiNoteRepositoryImpl.getNotes()", e);
        }
        return list;
    }

    @Override
    public List<WikiNote> getWikiNotesWithTag(String tag) {
        List<WikiNote> list = new ArrayList();
        try {
            list = getDao().queryBuilder().where().eq("tag", tag).query();
        } catch (SQLException e) {
            L.e("WikiNoteRepositoryImpl.getWikiNotesWithTag()", e);
        }
        return list;
    }

    @Override
    public void saveNote(WikiNote note) {
        L.i("WikiNoteRepositoryImpl.saveNote() called with " + "wikiNote = [" + note.toString() + "]");
        try {
            getDao().createOrUpdate(note);
        } catch (SQLException e) {
            L.e("WikiNoteRepositoryImpl.saveNote()", e);
        }
    }

    @Override
    public void deleteNoteById(long id) {
        try {
            getDao().deleteById(id);
        } catch (SQLException e) {
            L.e("WikiNoteRepositoryImpl.deleteNoteById()", e);
        }
    }

    @Override
    public void deleteAllNotes() {
        try {
            List<WikiNote> list = getNotes();
            if (list != null && list.size() != 0) {
                getDao().delete(getNotes());
            }
        } catch (SQLException e) {
            L.e("WikiNoteRepositoryImpl.deleteAllNotes()", e);
        }
    }
}
