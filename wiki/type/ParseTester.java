package wiki.type;

import java.util.Scanner;

public class ParseTester{

	public static void main(String[] args){

		String inputURL;
		Scanner in = new Scanner(System.in);

		//System.out.println("URL: ");
		//inputURL = in.nextLine();

		inputURL = "http://en.wikipedia.org/wiki/Robert_Johnson";

		Page p1 = new Page(inputURL);
                String[] strarray = p1.getHtml();
                HtmlParser parser = new HtmlParser(strarray);
                //String img = parser.getImg();
	}
}
