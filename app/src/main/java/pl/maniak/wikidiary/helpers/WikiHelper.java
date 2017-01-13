package pl.maniak.wikidiary.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.maniak.wikidiary.domain.wikinote.WikiNote;

/**
 * Created by maniak on 11.03.16.
 */
public class WikiHelper {

    private static Map<String, Map<String, List<String>>> dateMap;

    public static String preparingEntryOnWiki(List<WikiNote> noteList) {




        dateMap = new HashMap<>();


        for(WikiNote wiki: noteList) {
            addTitleWiki(wiki);
        }


        List<String> allLinesNote = new ArrayList<>();


        for(String day: dateMap.keySet()) {
            allLinesNote.add(WikiParser.addHeadline(day, 2));
            allLinesNote.add("");
            for(String category: dateMap.get(day).keySet()) {
                allLinesNote.add(WikiParser.addListBold(category, 1));
                for(String note: dateMap.get(day).get(category)) {
                    allLinesNote.add(WikiParser.addList(note, 2));
                }
                allLinesNote.add("");
            }


        }

        StringBuffer wiki = new StringBuffer("");

        for (int i = 0; i < allLinesNote.size(); i++) {
            wiki.append(allLinesNote.get(i) + "\n");
        }
        return wiki.toString();

    }

    public static void addTitleWiki(WikiNote wikiNote) {
        final String data = DateHelper.parseDateToStringWithDayName(wikiNote.getDate());
        if(!dateMap.containsKey(data)) {
            dateMap.put(data, new HashMap<String, List<String>>());
        }
        addCategory(data, wikiNote);
    }

    public static void addCategory(String date, WikiNote wikiNote) {
        Map<String, List<String>> category = dateMap.get(date);

        if(!category.containsKey(wikiNote.getTag())) {
            category.put(wikiNote.getTag(), new ArrayList<String>());
        }

        addNote(category, wikiNote);

    }

    public static void addNote(Map<String, List<String>> category, WikiNote wikiNote) {
        category.get(wikiNote.getTag()).add(wikiNote.getDescription());

    }
}
