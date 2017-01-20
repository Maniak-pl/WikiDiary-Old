package pl.maniak.wikidiary.utils.di.todo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.domain.todo.interactor.TodoServiceImpl;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteService;
import pl.maniak.wikidiary.repository.DBHelper;
import pl.maniak.wikidiary.repository.todo.TodoRepositoryImpl;
import pl.maniak.wikidiary.domain.todo.interactor.TodoService;
import pl.maniak.wikidiary.repository.todo.TodoRepository;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteServiceImpl;
import pl.maniak.wikidiary.repository.wikinote.WikiNoteRepository;
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
    Context provideContext() {
        return activity.getBaseContext();
    }

    @Provides
    ObservableList<Task> provideObservableList() {
        return new ObservableListImpl<>();
    }

    @Provides
    TodoContract.Presenter providePresenter(ObservableList<Task> observableList, TodoService useCase) {
        return new TodoPresenter(observableList, useCase);
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        return layoutManager;
    }

    @Provides
    TodoRecyclerViewAdapter provideRecyclerViewAdapter() {
        return new TodoRecyclerViewAdapter(new ArrayList<>());
    }

    @Provides
    TodoRepository provideTodoRepository(DBHelper helper){
        return new TodoRepositoryImpl(helper);
    }

    @Provides
    TodoService provideTodoUseCase(TodoRepository repository, WikiNoteService service) {
        return new TodoServiceImpl(repository, service);
    }

    @Provides
    WikiNoteService provideWikiNoteUseCase(WikiNoteRepository repository) {
        return new WikiNoteServiceImpl(repository);
    }

}
