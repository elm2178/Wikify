package wiki.type;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Image extends DataType{

    String inputURL;
    ArrayList<String> picLinks;

    public Image() {

        this.inputURL = "";
        picLinks=new ArrayList<String>();

    }

    public void url(String url) { // will load all the picture links in the given url into picLinks

        this.inputURL = url;

        Page html = new Page(inputURL);
        String[] sourceCodeArray = html.getHtml();

        //ArrayList<String> picLinks=new ArrayList<String>();
        Pattern p = Pattern.compile("//upload.wikimedia.org(.*?)(.png|.jpeg)");// find the following pattern for pictures
        for(int i=0; i<sourceCodeArray.length ;i++){

            String sourceCodeline= sourceCodeArray[i];
            Matcher m = p.matcher(sourceCodeline);

            while(m.find()) {

                //System.out.println("http:"+ m.group(0));

                picLinks.add("http:"+ m.group(0));

                /*
                 * eg. if picPosition is 0, 
                 * download( int picPosition) downloads the first pic on the wikepedia page
                 * and stores this picture as wikiDownload.jpg
                 */

            }
        }
    }

    // eg. if picPosition is 1, download( int picPosition) downloads the first pic on the wikepedia page
    public void download( int picPosition) throws IOException{         URL url = null;

        try {
            //url = new URL("http://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Flag_of_Ghana.svg/23px-Flag_of_Ghana.svg.png");
            url= new URL(picLinks.get(picPosition));   

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        try {
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] response = out.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("wikiDownload.jpg"); // for now the downloaded file will always be called wikiDownload.jpg
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {

            fos.write(response);

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
        try {
            fos.close();

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();
        }
    }

    public void showImage( int picPosition) throws IOException{
        if (picPosition<0 || picPosition > picLinks.size()){ // if user requests for a picture at a negative position of the wikepedia page
            System.out.println("there is no picture at the requested position");// or if the user requests for the picture at a position greater than the number of 
            // pictures, tell user this is impossible
        }
        else{ // the user has requested for a picture at a valid position
            this.download(picPosition);// first download the image at the requested position. This is 
            // automatically stored as wikiDownload.jpg

            BufferedImage img=ImageIO.read(new File("wikiDownload.jpg"));// next show the downloaded image
            ImageIcon icon=new ImageIcon(img);
            JFrame frame=new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(200,300);
            JLabel lbl=new JLabel();
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.setVisible(true);
        }

    }
    
    public int getType() {
        return DataType.IMAGE; 
    }
}
