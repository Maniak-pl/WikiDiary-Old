package pl.maniak.wikidiary.section.todo;

import java.util.Date;

/**
 * Created by Piotr on 2016-08-25.
 */
public class ToDo {

    private int mId;
    private String mContent;
    private Date mDate;

    public ToDo(int id, String content, Date date) {
        mId = id;
        mContent = content;
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "mId=" + mId +
                ", mContent='" + mContent + '\'' +
                ", mDate=" + mDate +
                '}';
    }
}
