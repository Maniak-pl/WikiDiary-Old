package pl.maniak.wikidiary.utils.di.todo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.data.TodoRepositoryImpl;
import pl.maniak.wikidiary.domain.DBHelper;
import pl.maniak.wikidiary.domain.todo.interactor.TodoUseCase;
import pl.maniak.wikidiary.domain.todo.interactor.TodoUseCaseImpl;
import pl.maniak.wikidiary.domain.todo.repository.TodoRepository;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteUseCase;
import pl.maniak.wikidiary.domain.wikinote.interactor.WikiNoteUserCaseImpl;
import pl.maniak.wikidiary.domain.wikinote.repository.WikiNoteRepository;
import pl.maniak.wikidiary.domain.wikinote.repository.datasource.WikiNoteRepositoryImpl;
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
    TodoContract.Presenter providePresenter(ObservableList<Task> observableList, TodoUseCase useCase) {
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
    TodoRepository provideTodoRepository(){
        return new TodoRepositoryImpl(activity);
    }

    @Provides
    TodoUseCase provideTodoUseCase(TodoRepository repository, WikiNoteUseCase useCase) {
        return new TodoUseCaseImpl(repository, useCase);
    }

    @Provides
    WikiNoteUseCase provideWikiNoteUseCase(WikiNoteRepository repository) {
        return new WikiNoteUserCaseImpl(repository);
    }

    @Provides
    WikiNoteRepository provideWikiNoteRepository() {
        return new WikiNoteRepositoryImpl(App.getAppComponent().getDBHelper());
    }

}
