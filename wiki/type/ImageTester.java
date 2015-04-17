package wiki.type;

import java.io.IOException;

public class ImageTester {

    public static void main(String[] args) throws IOException{
        Image myImage= new Image();
        myImage.url("http://en.wikipedia.org/wiki/Columbia_University");
        myImage.showImage(0);// show the first image on the columbia university wiki page
    }

}
