package domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WebScraper {
    private static final String WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";

    public static String scrapeWikipedia(String query) {
        try {
            // Encode query for proper URL handling
            String formattedQuery = URLEncoder.encode(query.trim().replace(" ", "_"), StandardCharsets.UTF_8);
            String url = WIKIPEDIA_URL + formattedQuery;

            // Connect to Wikipedia
            Document doc = Jsoup.connect(url).get();

            List<Element> paragraphs = doc.select("p");
            if (paragraphs.size() >= 2) {
                String firstParagraph = paragraphs.get(0).text();
                String secondParagraph = paragraphs.get(1).text();
                return firstParagraph + "\n\n" + secondParagraph;
            } else if (paragraphs.size() == 1) {
                return paragraphs.get(0).text();
            }
            return "No information found on Wikipedia.";
        } catch (IOException e) {
            return "Sorry, I couldn't fetch data from Wikipedia.";
        }
    }

}
