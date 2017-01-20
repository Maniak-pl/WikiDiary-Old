package pl.maniak.wikidiary.domain.wikinote.interactor;

import java.util.List;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.repository.wikinote.WikiNoteRepository;


@RequiredArgsConstructor
public class WikiNoteServiceImpl implements WikiNoteService {

    private final WikiNoteRepository repository;

    @Override
    public WikiNote getWikiNote(Long id) {
        return null;
    }

    @Override
    public List<WikiNote> getAllWikiNotes() {
        return null;
    }

    @Override
    public void save(WikiNote wikiNote) {
        repository.saveNote(wikiNote);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
