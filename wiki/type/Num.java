package wiki.type;
public class Num {
    private int number;

    public Num() {
    }

    public Num(int i) {
        number = i;
    }

    public static String string(int i) {
        return Integer.toString(i);
    }

    public static String binary(int i) {
        return Integer.toBinaryString(i);
    }

    public static String hex(int i) {
        return Integer.toHexString(i);
    }
}
