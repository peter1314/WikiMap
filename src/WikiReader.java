import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class WikiReader {

    public static ArrayList<Article> getChildren(Article wikiArticle) throws Exception{
        try {
            URL article = new URL("https://en.wikipedia.org/wiki/" + wikiArticle.getTitle());
            BufferedReader in = new BufferedReader(new InputStreamReader(article.openStream()));

            String inputLine;
            ArrayList<Article> children = new ArrayList<>();
            boolean reading = false;

            while((inputLine = in.readLine()) != null) {
                if(inputLine.contains("<p>")) reading = true;
                while (inputLine.contains("a href=\"/wiki/") && reading) {

                    int linkStart = inputLine.indexOf("/wiki/") + 6;
                    int linkEnd = inputLine.indexOf("\"", inputLine.indexOf("\"") + 1);

                    if (linkStart < linkEnd) {
                        String linkedArticle = inputLine.substring(linkStart, linkEnd);
                        if(!children.contains(new Article(linkedArticle))) {
                            //System.out.println("Searching article: " + linkedArticle);
                            children.add(new Article(linkedArticle, wikiArticle));
                        }
                    }
                    inputLine = inputLine.substring(linkEnd + 6);
                }
                if(inputLine.contains("</p>")) reading = false;
            }
            in.close();
            return children;
        }
        catch (FileNotFoundException f){
            return new ArrayList<Article>();
        }
    }
}