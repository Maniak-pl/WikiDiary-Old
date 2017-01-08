package pl.maniak.wikidiary.domain.wikinote.repository.datasource;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.DBHelper;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.domain.wikinote.repository.WikiNoteRepository;
import pl.maniak.wikidiary.utils.L;

@RequiredArgsConstructor
public class WikiNoteRepositoryImpl implements WikiNoteRepository {

    private final DBHelper dbHelper;

    @Override
    public WikiNote getWikiNoteById(Long id) {
        return null;
    }

    @Override
    public List<WikiNote> getWikiNotesWithTag(String tag) {
        return null;
    }

    @Override
    public List<WikiNote> getAllWikiNotes() {
        return null;
    }

    @Override
    public void saveWikiNote(WikiNote wikiNote) {
        L.e("WikiNoteRepositoryImpl.saveWikiNote() called with " + "wikiNote = [" + wikiNote.toString() + "]");
        try {
            dbHelper.getWikiNoteDao().createOrUpdate(wikiNote);
        } catch (SQLException e) {
            L.e("WikiNoteRepositoryImpl.saveWikiNote()", e);
        }
    }

    @Override
    public void deleteWikiNoteById(Long id) {

    }

    @Override
    public void deleteAllWikiNote() {

    }
}
