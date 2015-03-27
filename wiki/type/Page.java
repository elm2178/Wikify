package wiki.type;

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
}
