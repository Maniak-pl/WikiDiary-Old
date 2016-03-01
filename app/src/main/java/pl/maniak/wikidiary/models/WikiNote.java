package pl.maniak.wikidiary.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    private boolean isSend;

    public WikiNote() {
    }

    public WikiNote(String tag, String description, java.util.Date date) {
        Tag = tag;
        Description = description;
        Date = date;

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

    public boolean isSend() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }

    @Override
    public String toString() {
        return "WikiNote{" +
                "Tag='" + Tag + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
