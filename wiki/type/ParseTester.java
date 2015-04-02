import java.util.Scanner;

public class ParseTester{

	public static void main(String[] args){

		String inputURL;
		Scanner in = new Scanner(System.in);

		//System.out.println("URL: ");
		//inputURL = in.nextLine();

		inputURL = "http://en.wikipedia.org/wiki/Robert_Johnson";

		Page html = new Page(inputURL);
		String[] paras = html.getParagraphs();

		for(int i=0; i<paras.length; i++)
			System.out.println(paras[i++]+"\n");

	}
}