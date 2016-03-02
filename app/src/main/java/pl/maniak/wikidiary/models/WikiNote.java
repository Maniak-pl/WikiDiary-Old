package pl.maniak.wikidiary.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Maniak on 2015-10-22.
 */
@DatabaseTable(tableName = "wikinotes")
public class WikiNote {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private long dbId;
    @DatabaseField
    private String Tag;
    @DatabaseField
    private String Description;
    @DatabaseField
    private java.util.Date Date;
    @DatabaseField
    private java.util.Date CreationDate;

    public WikiNote() {
    }

    public WikiNote(String tag, String description, java.util.Date date, java.util.Date creationDate) {
        Tag = tag;
        Description = description;
        Date = date;
        CreationDate = creationDate;

    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date mDate) {
        this.Date = mDate;
    }

    public java.util.Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(java.util.Date creationDate) {
        CreationDate = creationDate;
    }

    @Override
    public String toString() {
        return "WikiNote{" +
                "Tag='" + Tag + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
