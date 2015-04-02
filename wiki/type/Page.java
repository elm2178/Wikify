package wiki.type;
import java.net.*;
import java.io.*;

public class Page {
    private String url;
    private String html;
    private WikiParser parser;
    
    public Page() {
        this.url = "";
        this.parser = null;
    }

    public Page(String url) {
        this.url = url;
        html = getHtml();
        this.parser = new WikiParser(html.split("\n"));
    }

    public void url(String url) {
        this.url = url;
        html = getHtml();
        this.parser = new WikiParser(html.split("\n"));
    }

    public String getUrl() {
        return url;
    }

    public String getHtml() {
        try {
            String line;
            String buffer = "";
            URL myURL = new URL(this.url);
            InputStream is = myURL.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while((line = br.readLine()) != null) {
                buffer += line;
            }
            return buffer;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String[] getParagraphs() {
        return parser.getParagraphs();
    }
}
