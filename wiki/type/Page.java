package wiki.type;
import java.net.*;
import java.io.*;

public class Page {
    private String url;
    
    public Page() {
        this.url = "";
    }

    public Page(String url) {
        this.url = url;
    }

    public void url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String html() {
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
}
