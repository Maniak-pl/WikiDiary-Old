package pl.maniak.wikidiary.utils.di.todo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.data.TodoDBHelper;
import pl.maniak.wikidiary.domain.todo.interactor.TodoUseCase;
import pl.maniak.wikidiary.domain.todo.interactor.TodoUseCaseImpl;
import pl.maniak.wikidiary.domain.todo.repository.TodoRepository;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.ui.todo.TodoActivity;
import pl.maniak.wikidiary.ui.todo.TodoContract;
import pl.maniak.wikidiary.ui.todo.TodoPresenter;
import pl.maniak.wikidiary.ui.todo.TodoRecyclerViewAdapter;
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
    TodoContract.Presenter providePresenter(ObservableList<Task> observableList, TodoUseCase useCase) {
        return new TodoPresenter(observableList, useCase);
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

    @Provides
    TodoUseCase provideUseCase(TodoRepository repository) {
        return new TodoUseCaseImpl(repository);
    }

}
