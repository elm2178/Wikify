package wiki.type;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class Page{
    private String url;
    private String[] html;
    private HtmlParser parser;
    
    public Page() {
        this.url = "";
        this.parser = null;
    }

    public Page(String url) {
        this.url = url;
        html = getHtml();
        this.parser = new HtmlParser(html);
    }

    public void url(String url) {
        this.url = url;
        html = getHtml();
        this.parser = new HtmlParser(html);
    }

    public String getUrl() {
        return url;
    }

    public String[] getHtml() {
        try {
            String line;
            String[] buffer = new String[1000];
            URL myURL = new URL(this.url);
            InputStream is = myURL.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int i = 0;
            while((line = br.readLine()) != null) {

                if(i == buffer.length)
                    buffer = Arrays.copyOf(buffer, buffer.length *2);

                buffer[i] = line;
                i++;
            }
            buffer = Arrays.copyOf(buffer, i);
            return buffer;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[] getParagraphs() {
        return parser.getParagraphs();
    }
	
    public void getInfobox(){
	System.out.println("Looking for tables!!!");
	parser.getInfobox();
    }

    //asks user for input --> will automatically search wiki for search terms
    public void urlPrompt(){
	try{
	System.out.println("Please input your search term(s): ");
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String s = br.readLine();
	s = s.replace(",","");
	s = s.replace(";","");
	s = s.replace(" ","_");
	this.url = "http://www.wikipedia.org/wiki/"+s;
	this.html = getHtml();
        this.parser = new HtmlParser(html);
	} catch (Exception e){
		e.printStackTrace();
	}
    }

}
