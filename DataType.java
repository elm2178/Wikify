package wikify;

public class DataType {
    /* List of Different Types */
    public static final int UNDEFINED = -1;
    public static final int INT = 0;
    public static final int STR = 1;
    /**************************/

    private int type;
    private String str;
    private int value;

    public DataType() {
        type = UNDEFINED; 
        str = null;
        value = 0;
    }

    public DataType(String str) {
        type = STR;
        this.str = str;
        value = 0;
    }

    public DataType(int value) {
        type = INT;
        str = null;
        this.value = value;

    }
        
    public int getType() {
        return type;
    }

    public String getString() {
        return str;
    }
    
    public int getInt() {
        return value;
    }
}

        
