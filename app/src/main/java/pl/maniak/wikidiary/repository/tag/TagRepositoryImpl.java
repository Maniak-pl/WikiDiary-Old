package pl.maniak.wikidiary.repository.tag;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.repository.DBHelper;
import pl.maniak.wikidiary.utils.log.L;

@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final DBHelper helper;

    private Dao<Tag, Long> getDao() throws SQLException {
        return helper.getTagDao();
    }

    @Override
    public List<Tag> getTagsByName(List<String> tagsNames) {
        List<Tag> tagsResult = new ArrayList<>();
        try {
            tagsResult = getDao().queryBuilder().where().in("tag", tagsNames).query();
        } catch (SQLException e) {
            L.e("TagRepositoryImpl.getTagsByName()", e);
        }
        return tagsResult;
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        try {
            tags = new ArrayList<>(getDao().queryForAll());
        } catch (SQLException e) {
            L.e("TagRepositoryImpl.getAllTags()", e);
        }
        return tags;
    }

    @Override
    public void addTag(String tagName) {
        try {
            getDao().createOrUpdate(new Tag(tagName));
        } catch (SQLException e) {
            L.e("TagRepositoryImpl.addTag()", e);
        }
    }

    @Override
    public void deleteTag(String tag) {
        try {
            DeleteBuilder<Tag, Long> delete = getDao().deleteBuilder();
            delete.where().eq("tag", tag);
            delete.delete();
        } catch (SQLException e) {
            L.e("TagRepositoryImpl.deleteTag()", e);
        }
    }
}
