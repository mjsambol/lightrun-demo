import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class SuperFastDemo {

    private int numRequests = 0;

    public SuperFastDemo() {
    }

    private String stripWikipediaTitlePrefixAndSuffix(ParsedPage page) {
        String title = page.getTitle().substring("Wikipedia -".length());
        int pos = title.lastIndexOf(" - Wikipedia");
        if (pos >= 0) {
            title = title.substring(0, pos);
        }
        return title;
    }

    private Document fetch(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        numRequests += 1;
        return doc;
    }

    public Document fetchRandomWikipediaArticle() throws IOException {
        return fetch("https://en.wikipedia.org/wiki/Special:Random");
    }

    public boolean testRandomArticle(String theTitle) {

        if (theTitle.startsWith("The")) {
            return false;
        }
        String firstChar = theTitle.substring(0, 1);
        return "AEIOUSTLNRHD".contains(firstChar);
    }

    public void gatherArticleStats(int numArticles) {

        for (int counter = 0; counter < numArticles; counter++) {
            try {
                Document doc = fetchRandomWikipediaArticle();
                ParsedPage article = new ParsedPage(doc);
                String cleanTitle = stripWikipediaTitlePrefixAndSuffix(article);

                if (testRandomArticle(cleanTitle)) {
                    System.out.println(cleanTitle);
                } else {
                    System.out.println("Skipping #" + numRequests);
                }
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Uh oh: " + e);
            }
        }
    }

}

