public class DataType<T> {
    /* List of Different Types */
    public static final int UNDEFINED = -1;
    public static final int INT = 0;
    public static final int STR = 1;
    /**************************/

    private T data;

    public DataType() {
        data = null;
    }

    public DataType(T data) {
        this.data = data;
    }

    public int getType() {
        if(data.getClass() == Integer.class)
            return INT;
        if(data.getClass() == String.class)
            return STR;

        return UNDEFINED;
    }

    public T getContent() {
        return data;
    }
}

        
