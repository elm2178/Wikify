package wiki.type;
import java.util.*;
import java.net.*;
import java.io.*;

//using Jsoup for now, will try regex when have time
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Test {
	public static void main(String[] args) {
	
	//testing basic Table class functions	
	String[][] input = {{"a","b","c","f"},{"c","d","e","g"}};
	Table table1= new Table(input);
	table1.remove(0, 2);
	//table1.excel("output.xlsx");
	table1.insert("D",0, 2);
	table1.excel("output.xlsx");
	//System.out.println(table1.getSize());
	//System.out.println(table1.search("c"));
	
	
	//testing getTables method(see below)
	String url = "http://en.wikipedia.org/wiki/List_of_Presidents_of_the_United_States";
    ArrayList<Table> tables = getTables(url);
    for (Table table:tables){
    	table.excel("output");
    }
	}
    /**
     * need import statement of Jsoup
     * @param urlAddress: of type String
     * @return an ArrayList of Table objects
     */
    public static ArrayList<Table> getTables (String urlAddress){
    	 ArrayList<Table> tables = new ArrayList<Table>();
    	 Document doc = null; 
	     
    	 //store html in doc
    	 try {
		    doc = Jsoup.connect(urlAddress).get();
    	 }
    	 catch (IOException e) {
	    	e.printStackTrace();
    	 }
	     
    	 //get only wikitables, not just any table
		 Elements wikiTables = doc.getElementsByClass("wikitable");
		 for (Element e : wikiTables){
			Table table = null;
			//first pass to get dimensions of table
			int rows = 0;
			int cols = 0;
			for (Element row : e.select("tr")){
				Elements tds = row.select("td");
				if (tds.size() == 0) continue; //this row has no columns
				cols = Math.max(cols, tds.size());
				rows++;
			}
			
			//construct new Table object 
			if (rows==0) continue;
			String[][] input = new String[rows][cols];
			table = new Table(input);
			
			//second pass to store content in table
			int i = 0;
			for (Element row : e.select("tr")){
				Elements tds = row.select("td");
				if (tds.size() == 0) continue;
				int j = 0;
				for (Element td : tds){
					table.insert(td.text(),i,j);
					j++;
				}
				i++;	
			}
			
			//add table to ArrayList
			tables.add(table);
		 }
		 return tables;
     }
}
