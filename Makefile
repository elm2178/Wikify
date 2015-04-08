run: 
	cat file.wiki | java Test > TestWiki.java && javac TestWiki.java && java TestWiki

clean:
	rm *.class *.tokens WikiBaseListener.java WikiParser.java  WikiLexer.java WikiListener.java
