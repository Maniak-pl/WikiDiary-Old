package pl.maniak.wikidiary.repository;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.todo.Task;
import pl.maniak.wikidiary.domain.todo.repository.TodoRepository;
import pl.maniak.wikidiary.utils.L;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {

  private final DBHelper helper;

    @Override
    public Task getTaskById(Long id) {
        Task task = new Task();
        try {
            task = getDao().queryForId(id);
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.getTaskById()", e);
        }
        return task;
    }

    @Override
    public void saveTask(Task task) {
        try {
            getDao().createOrUpdate(task);
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.save()", e);
        }
    }

    private Dao<Task, Long> getDao() throws SQLException {
        return helper.getTaskDao();
    }

    @Override
    public void deleteTaskById(Long id) {
        try {
            getDao().deleteById(id);
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.deleteTaskById()", e);
        }
    }

    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList();
        try {
            list = getDao().queryForAll();
        } catch (SQLException e) {
            L.e("TodoRepositoryImpl.getAllTasks()", e);
        }
        return list;
    }
}
