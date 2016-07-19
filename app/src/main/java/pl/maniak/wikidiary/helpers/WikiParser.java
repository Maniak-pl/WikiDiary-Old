package pl.maniak.wikidiary.helpers;

import java.util.Date;

import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.models.WikiNote;
import pl.maniak.wikidiary.utils.L;

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

    public static String putHealth(int steps) {

        String img = "";
        if (steps >= 10000) {
            img = "  {{ :wikidiary:s-health-samsung-logo.jpg?direct&200 |}}";
        } else {
            img = "  {{ :wikidiary:s-health-samsung-logo-fail.jpg?direct&200 |}}";
        }

         return ("Kroków " + steps + "/10000" + img);
    }
}
