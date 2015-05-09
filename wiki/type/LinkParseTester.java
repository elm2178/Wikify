import java.util.Scanner;

public class LinkParseTester{

	public static void main(String[] args){

		String inputURL;
		Scanner in = new Scanner(System.in);

		System.out.println("INPUT URL: ");
		inputURL = in.nextLine();

		Page html = new Page(inputURL);

		String[] links = html.getLinks();

		for(int i=0; i<links.length; i++)
			System.out.println(links[i]+"\n");

		System.out.println("LENGTH: "+links.length);
	}
}