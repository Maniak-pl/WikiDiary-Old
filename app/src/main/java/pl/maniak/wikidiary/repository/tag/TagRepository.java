package pl.maniak.wikidiary.repository.tag;

import java.util.List;

import pl.maniak.wikidiary.domain.tag.Tag;

public interface TagRepository {

    public List<Tag> getTagsByName(List<String> tagsNames);
    public List<Tag> getAllTags();
    public void addTag(String tagName);
    public void deleteTag(String tag);
}
