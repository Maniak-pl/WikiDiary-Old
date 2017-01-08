package pl.maniak.wikidiary.utils.di.todo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.api.TodoDBHelper;
import pl.maniak.wikidiary.api.TodoRepository;
import pl.maniak.wikidiary.models.Task;
import pl.maniak.wikidiary.ui.todo.TodoActivity;
import pl.maniak.wikidiary.ui.todo.TodoContract;
import pl.maniak.wikidiary.ui.todo.TodoPresenter;
import pl.maniak.wikidiary.ui.todo.TodoRecyclerViewAdapter;
import pl.maniak.wikidiary.utils.L;
import pl.maniak.wikidiary.utils.ObservableList;
import pl.maniak.wikidiary.utils.ObservableListImpl;

@Module
@RequiredArgsConstructor
public class TodoModule {

    private final TodoActivity activity;

    @Provides
    ObservableList<Task> provideObservableList() {
        return new ObservableListImpl<>();
    }

    @Provides
    TodoContract.Presenter providePresenter(ObservableList<Task> observableList, TodoRepository repository) {
        return new TodoPresenter(observableList, repository);
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Provides
    TodoRecyclerViewAdapter provideRecyclerViewAdapter() {
        return new TodoRecyclerViewAdapter(new ArrayList<>());
    }

    @Provides
    TodoRepository provideRepository(){
        return new TodoDBHelper(activity);
    }

}
