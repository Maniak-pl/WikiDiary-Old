package pl.maniak.wikidiary.helpers;

/**
 * Created by maniak on 02.03.16.
 */
public class WikiParser {

    public static String addHeadline(String note, int level) {
        String mark = "";

        switch (level) {
            case 1:
                mark = "======";
                break;
            case 2:
                mark = "=====";
                break;
            case 3:
                mark = "====";
                break;
            case 4:
                mark = "===";
                break;
            default:
                mark = "==";
                break;
        }

        return mark+" "+note+" "+mark;
    }

    public static String addListBold(String note, int level) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i< level; i++) {
            str.append("  ");
        }
        str.append("* **");
        str.append(note);
        str.append("**");
        return str.toString();
    }
    public static String addList(String note, int level) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i< level; i++) {
            str.append("  ");
        }
        str.append("* ");
        str.append(note);

        return str.toString();
    }
}
