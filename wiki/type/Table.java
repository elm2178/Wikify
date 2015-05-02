package wiki.type;
import java.util.*;
import java.io.File;
import jxl.*;
import jxl.write.*;

public class Table extends DataType{
    private String[][] table;
    private int rows, cols;

    /**
     * empty constructor
     */
    public Table(){
        rows = 0;
        cols = 0;
        table = null;
    }

    public void setRowCol(int row, int col) {
        this.cols = col;
        this.rows = row;
        table = new String[row][col]; 
    }

    /**
     * copies contents from input to table
     * @param input: 2D String array, must have known size, individual entries can be null
     */
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

    /**
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

    public void excel(String fileName){
        int offset = 0;
        fileName = fileName.split("\\.")[0]+".xls"; 
        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        try {
            File f = new File(fileName);
            if(f.isFile()) { 
                Workbook old = Workbook.getWorkbook(new File(fileName));
                workbook = Workbook.createWorkbook(new File(fileName), old);
                sheet = workbook.getSheet(0);
                offset = sheet.getRows() + 2;
            }
            else {
                workbook = Workbook.createWorkbook(new File(fileName));
                sheet = workbook.createSheet("sheet", 0); 
            }

            for (int i=0; i<rows; i++){
                for (int j=0; j<cols; j++){
                    Label label = new Label(j,i+offset,table[i][j]);
                    sheet.addCell(label);
                }
            }
            workbook.write();
            workbook.close();

        } 
        catch (Exception e) {
            e.printStackTrace();
        } 

    }

    public int getType() {
        return DataType.TABLE;
    }

}
