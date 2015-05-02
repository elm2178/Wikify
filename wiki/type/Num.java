package wiki.type;
public class Num extends DataType {
    private int number;

    public Num() {
    }

    public Num(int i) {
        number = i;
    }

    public static String str(int i) {
        return Integer.toString(i);
    }

    public static String binary(int i) {
        return Integer.toBinaryString(i);
    }

    public static String hex(int i) {
        return Integer.toHexString(i);
    }

    public int getType() {
        return DataType.NUM;
    }
}
