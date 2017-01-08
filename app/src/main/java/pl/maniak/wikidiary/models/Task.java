package pl.maniak.wikidiary.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable
public class Task {

    @DatabaseField(generatedId = true)
    protected Long id;
    @DatabaseField
    protected String content;
    @DatabaseField
    protected Date date;

    public Task() {
    }

    public Task(String content, Date date) {
        this.content = content;
        this.date = date;
    }

}
