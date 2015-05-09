package wiki.type;

public class PageTester{

    public static void main(String[] args){
	Page p = new Page ("http://en.wikipedia.org/wiki/List_of_Presidents_of_the_United_States");
	p.toWord("simpleWord");
    }

}