package wiki.type;
import java.util.*;

public class Table {
	private String[][] table;
	private int rows, cols;
	
	/**
	 * copies contents from input to table
	 * @param input: 2D String array, must have known size, individual entries can be null
	 */
        public Table() {
            rows = 0;
            cols = 0;
            table = null;
        }

        public void setRowCol(int row, int col) {
            this.cols = col;
            this.rows = row;

            //create a string[][]
            table = new String[row][col]; 
        }

	public Table(String[][] input){
		rows = input.length;
		cols = input[0].length;
		table = new String[rows][cols];
		for (int i=0; i<rows; i++){
			for (int j=0; j<cols; j++){
				table[i][j]=input[i][j];
			}
		}	
	}
	
	/*
	 * set content of cell(x,y) to new entry
	 * @param entry: a string you want to insert
	 * @param x: row index
	 * @param y: column index
	 * @return true if insertion is successful, false o/w
	 */
	public boolean insert(String entry, int x, int y){
		if (x>=0 && x<rows && y>=0 && y<cols){
			table[x][y] = entry;
			return true;
		}
		return false;
	}
	
	/**
	 * set content of cell(x,y) to empty string
	 * @param x row index
	 * @param y column index
	 * @return true if removal is successful, false o/w
	 */
	public boolean remove(int x, int y){
		if (x>=0 && x<rows && y>=0 && y<cols){
			table[x][y] = "";
			return true;
		}
		return false;
	}
	/**
	 * return the content of cell(x,y)
	 * @param x
	 * @param y
	 * @return null if coordinates are out of bounds, or entry is uninitialized.
	 */
	public String getEntry(int x, int y){
		if (x>=0 && x<rows && y>=0 && y<cols){
			return table[x][y];
		}
		else {return null;}
	}

	/**
	 * return the size in form of String
	 * @return e.g "3 rows 2 cols"
	 */
	public String getSize(){
		return rows + " rows " + cols + " cols ";
	}
	
	/**
	 * search keyword in the table, case insensitive
	 * @param keyword
	 * @return a list of coordinates that contain keyword
	 */
	public List<String> search(String keyword){
		List<String> results = new ArrayList<String>();
		for (int i=0; i<rows; i++){
			for (int j=0; j<cols; j++){
				if (table[i][j].toLowerCase().contains(keyword.toLowerCase())){
					results.add("("+i+","+j+")");
				}
			}
		}
		//System.out.println(results);
		return results;
	}
	
	/**
	 * print to the console in the form:
	 * row 0 xxx; xxx; ...
	 * row 1 xxx; xxx; ...
	 * ....
	 */
	public void print(){
		int rows = table.length;
		int cols = table[0].length;
		System.out.println("\nTable");
		for (int i=0; i<rows; i++){
			System.out.print("row " +i+" ");
			for (int j=0; j<cols; j++){
				System.out.print(table[i][j]+";");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//printToFile method?
	
}
