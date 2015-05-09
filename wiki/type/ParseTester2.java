import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class ParseTester2{

	public static void main(String[] args){

		String inputURL;
		Scanner in = new Scanner(System.in);

		System.out.println("URL: ");
		inputURL = in.nextLine();

		Page html = new Page(inputURL);
		PrintWriter writer = null;

		String[] links = html.getLinks();

		for(int i=0; i<links.length; i++){
			String link = links[i];
			System.out.println(link);

			try{
				writer = new PrintWriter(link.substring(link.indexOf("/wiki/")+6)+".txt", "UTF-8");
			}catch(IOException e){
				e.printStackTrace();
				System.exit(-1);
			}

			Page p = new Page(link);
			String[] paras = p.getParagraphs();

			for(int j=0; j<paras.length; j++)
				writer.println(paras[j]);

			writer.close();
		}
	}
}