package wiki.type;

import java.util.Scanner;

public class ParseTester{

	public static void main(String[] args){

		String inputURL;
		Scanner in = new Scanner(System.in);

		//System.out.println("URL: ");
		//inputURL = in.nextLine();

		inputURL = "http://en.wikipedia.org/wiki/Robert_Johnson";

<<<<<<< HEAD
		Page html = new Page(inputURL);

		String[] paras = html.getParagraphs();

		for(int i=0; i<paras.length; i++)
			System.out.println(paras[i]+"\n");

		System.out.println("LENGTH: "+paras.length);
=======
		Page p1 = new Page(inputURL);
                String[] strarray = p1.getHtml();
                HtmlParser parser = new HtmlParser(strarray);
                //String img = parser.getImg();
>>>>>>> 1892dbf34c98a8e9019707254318d6a822b36673
	}
}
