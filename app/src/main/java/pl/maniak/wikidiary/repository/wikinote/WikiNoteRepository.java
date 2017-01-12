package pl.maniak.wikidiary.repository.wikinote;

import java.util.List;

import pl.maniak.wikidiary.domain.wikinote.WikiNote;

public interface WikiNoteRepository {

    List<WikiNote> getNotes();
    List<WikiNote> getWikiNotesWithTag(String tag);
    void saveNote(WikiNote note);
    void deleteNoteById(long id);
    void deleteAllNotes();
}
