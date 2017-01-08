package pl.maniak.wikidiary.domain.wikinote;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
        this.Tag = tag;
        this.Description = description;
        this.Date = date;
    }

    @Override
    public String toString() {
        return "WikiNote{" +
                "Tag='" + Tag + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
