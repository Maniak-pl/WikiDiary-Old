package pl.maniak.wikidiary.domain.wikinote.interactor;

import java.util.List;

import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;

public interface WikiNoteUseCase {
    WikiNote getWikiNote(Long id);
    List<WikiNote> getAllWikiNotes();
    void save(WikiNote wikiNote);
    void delete(Long id);
    void deleteAll();
}
