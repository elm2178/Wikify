import java.util.Scanner;

public class ParagraphParseTester{

	public static void main(String[] args){

		String inputURL;
		Scanner in = new Scanner(System.in);

		System.out.println("INPUT URL: ");
		inputURL = in.nextLine();

		Page html = new Page(inputURL);

		String[] paras = html.getParagraphs();

		for(int i=0; i<paras.length; i++)
			System.out.println(paras[i]+"\n");

		System.out.println("LENGTH: "+paras.length);
	}
}