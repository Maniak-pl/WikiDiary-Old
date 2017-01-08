package pl.maniak.wikidiary.domain.wikinote.repository;

import java.util.List;

import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;

public interface WikiNoteRepository {
    WikiNote getWikiNoteById(Long id);
    List<WikiNote> getWikiNotesWithTag(String tag);
    List<WikiNote> getAllWikiNotes();
    void saveWikiNote(WikiNote wikiNote);
    void deleteWikiNoteById(Long id);
    void deleteAllWikiNote();
}
