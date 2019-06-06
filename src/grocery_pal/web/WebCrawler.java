package grocery_pal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// The following class draws inspiration from Stephen's web crawler implementation that he posted on 'Net Instructions.
// To view Stphen's implementation, please visit http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/.

public class WebCrawler
{
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private static Queue<String> links = new LinkedList<String>();
    private static Document htmlDocument;

    public static boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDoc = connection.get();
            htmlDocument = htmlDoc;
            if(connection.response().statusCode() == 200)
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(connection.response().statusCode() == 404)
            {
            	System.out.println("Resource Not Found.");
            	return false;
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            for(Element link : linksOnPage)
            {
                links.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException ioe)
        {
            return false;
        }
        catch(IllegalArgumentException iae) {
        	System.out.println("Illegal Argument");
        	return false;
        }
    }

    public static boolean searchForWord(String searchWord)
    {
        if(htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        String bodyText = htmlDocument.body().text();
        if(bodyText.toLowerCase().contains(searchWord.toLowerCase())) {
        	 return true;
        }
       return false;
    }
    
    public static ArrayList<String> getFilteredText(String searchWord) {
    	String bodyText = htmlDocument.body().text();
		String[] tokens = bodyText.split("\\s+");
		
		int wordIndex = 0;
		for(int i = 0; i < tokens.length; i++) {
			if(tokens[i].equalsIgnoreCase(searchWord))
				wordIndex = i;
		}
		return trimCollection(wordIndex, tokens);
    }
    private static ArrayList<String> trimCollection(int wordIndex, String[] tokens) {
		ArrayList<String> result = new ArrayList<>();
		int maxBefore = 10;
		int maxAfter = 10;
		int i = wordIndex; 
		int j = wordIndex;
		while((i > 0) && (j < tokens.length) && (maxBefore > 0) && (maxAfter > 0)) {
			i--;
			j++;
			maxBefore--;
			maxAfter--;
			result.add(tokens[i]);
			result.add(tokens[j]);
		}
		return result;
	}

	public static Queue<String> getLinks()
    {
        return links;
    }
}