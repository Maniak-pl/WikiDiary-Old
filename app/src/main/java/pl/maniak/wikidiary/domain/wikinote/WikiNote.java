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
    private String tag;
    @DatabaseField
    private String description;
    @DatabaseField
    private java.util.Date date;
    @DatabaseField
    private boolean isSend;

    public WikiNote() {
    }

    public WikiNote(String tag, String description, java.util.Date date) {
        this.tag = tag;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return "WikiNote{" +
                "Tag='" + tag + '\'' +
                ", Description='" + description + '\'' +
                '}';
    }
}
