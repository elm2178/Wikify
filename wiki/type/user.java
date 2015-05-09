package wiki.type;
import java.io.*;
import java.util.Scanner;

public class user{
    public static String string;
    public user() {
	this.string="";	
    }
    public static String input(){
	Scanner scan = new Scanner(System.in);
	string = scan.next();
	return string;
    }
     public static String[] input(String path) throws IOException{
	    	String[] string = null;
	    	File file = new File(path);
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	String line;
	    	string = new String[10];
	    	int i=0;
	    	while((line=br.readLine())!=null){
	    		if(i==string.length){
	    			String[] temp = new String[2*string.length];
	    			for(int j=0; j<string.length;j++){
	    				temp[j]=string[j];
	    			}
	    			string = temp;
	    		}
	    		string[i]=line;
	    		i++;
	    	}
	    	return string;
	}
}
