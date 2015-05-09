package wiki.type;
import java.util.Scanner;

public class User{
    public String string;
    public User() {
	this.string="";	
    }
    public String input(){
	Scanner scan = new Scanner(System.in);
	this.string = scan.next();
	return string;
    }
}
