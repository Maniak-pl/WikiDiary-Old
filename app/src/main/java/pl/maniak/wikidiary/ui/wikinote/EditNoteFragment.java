package pl.maniak.wikidiary.ui.wikinote;

import java.util.List;
import pl.maniak.wikidiary.domain.wikinote.WikiNote;
import pl.maniak.wikidiary.ui.BaseFragment;

public interface EditNoteFragment {

    void showNotes(List<WikiNote> list);

}
