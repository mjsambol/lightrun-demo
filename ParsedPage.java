import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class ParsedPage {

    private String title;
    private int numWords;
    private int numLinks;
    private String mostCommonWord;
    private int mostCommonWordOccurence = 0;
    private static String[] usualMostCommonWordsArr = {"the", "of", "and", "a", "to", "in", "is", "you", "that", "it", "from"};
    private static ArrayList<String> usualMostCommonWords = new ArrayList<String>(Arrays.asList(usualMostCommonWordsArr));

    public ParsedPage(Document doc) {
        title = doc.head().getElementsByAttributeValue("rel", "canonical").attr("href");
        int pos = title.lastIndexOf("/");
        if (pos >= 0) {
            title = title.substring(pos);
        }
        title = "Wikipedia - " + title;
        String fullText = doc.body().text();

        String[] allWords = fullText.split(" ");
        numWords = allWords.length;
        HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();

        for (int wnum = 0; wnum < numWords; wnum++) {
            String word = allWords[wnum];
            if (wordFrequency.containsKey(word)) {
                int theFrequency = wordFrequency.get(word) + 1;
                wordFrequency.put(word, theFrequency);
                if (theFrequency > mostCommonWordOccurence && ! usualMostCommonWords.contains(word)) {
                    mostCommonWordOccurence = theFrequency;
                    mostCommonWord = word;
                }
            } else {
                wordFrequency.put(word, 1);
            }

            Elements links = doc.select("a[href]");
            numLinks = links.size();
        }

    }

    public String getTitle() {
        return title;
    }

    public int getNumWords() {
        return numWords;
    }

    public int getNumLinks() {
        return numLinks;
    }

    public String getMostCommonWord() {
        return mostCommonWord;
    }

}
