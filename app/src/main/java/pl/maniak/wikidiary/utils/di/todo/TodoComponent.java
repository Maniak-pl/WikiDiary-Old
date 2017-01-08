package pl.maniak.wikidiary.utils.di.todo;

import javax.inject.Singleton;

import dagger.Component;
import pl.maniak.wikidiary.utils.di.AppComponent;
import pl.maniak.wikidiary.ui.todo.TodoActivity;

@TodoScope
@Component(
        dependencies = AppComponent.class,
        modules = TodoModule.class
)
public interface TodoComponent {

    void inject(TodoActivity activity);
}
