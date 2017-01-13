package pl.maniak.wikidiary.ui.wikinote;


import java.util.List;

import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.ui.BaseContract;

public interface WikiNoteContract {

    interface View extends BaseContract.View {
        void showTag(List<Tag> list);
        String getContent();
    }

    interface Router extends BaseContract.Router {

    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onTagClicked(String tag);
    }

}
