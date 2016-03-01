package pl.maniak.wikidiary.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by pliszka on 01.03.16.
 */
@DatabaseTable
public class Tag implements Comparable {



    @DatabaseField(generatedId = true)
    protected Long id;

    @DatabaseField
    protected String tag;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int compareTo(Object another) {
        return tag.compareTo(((Tag) another).getTag());

    }
}
