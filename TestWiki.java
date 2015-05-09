import wiki.type.Page;
import wiki.type.Num;
import wiki.type.Table;
public class TestWiki { 
public static int add(int  a, int  b) {
return a+b;
} 
public static void printArr(int  arr[]) {
for(int i = 0;i<arr.length;++i) { 
System.out.print(arr[i]);
}
System.out.print("\n");
} 
public static int sub(int  c, int  d) {
return c-d;
} 
public static void println(String  word) {
System.out.print(word+"\n");
} 
public static void println(int  word) {
System.out.print(word+"\n");
} 
public static String strcat(String  pre, String  suf) {
return pre+suf;
} 
public static void main(String[] args) {
int a = 5+5;
int b = 5*5;
System.out.print(a);
System.out.print("\n");
println( b);
String name = "eric";
String word = "hello";
String words = strcat( 
word, name);
System.out.print(words+"\n");
b = add( a, b);
System.out.print(b);
System.out.print("\n");
b = sub( b, a);
System.out.print(b);
System.out.print("\n");
boolean truebool = true;
System.out.print(truebool);
System.out.print("\n");
truebool = true||false;
System.out.print(truebool);
System.out.print(true||false);
System.out.print("\n");
if (!truebool) {
println( "if statement working");
}
else {
println( "else statement also working");
}
int i = 0;
while( i<10) {
if (i<9) {
System.out.print(i);
}
else {
println( i);
}
i = i+1;
}
for(int k = 0;k<10;++k) { 
System.out.print(k);
b = add( k, k);
System.out.print(b);
}
System.out.print("\n");
for(int j = 0;j<10;++j) { 
if (j==5) {b = j;
break;
}else {};
}
println( b);
int  array[][]= new int [5][10];
int arr[] = {5,3,2,1};
printArr( arr);
Page  p1 = new Page ();
p1.url("http://en.wikipedia.org/wiki/Solar_eclipse_of_March_20,_2015");
println( p1.getUrl());
String binary = Num.binary(arr[0]);
println( binary);
String hex = Num.hex(157);
println( hex);
String nstr = Num.str(arr[0]);
println( nstr);
String paras[] = p1.getParagraphs();
println( paras[1]);
Page  p2 = new Page ();
p2.urlPrompt();
println( p2.getUrl());
String s[] = p2.getParagraphs();
println( s[0]);
String f[] = p2.getParagraphs();
println( f[0]);
p2.getInfobox();
}
}
