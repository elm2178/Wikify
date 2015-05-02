run: 
	javac Wiki*.java
	javac Test.java
	cat file.wiki | java Test && javac TestWiki.java && java TestWiki

clean:
	rm *.class *.tokens WikiBaseListener.java WikiParser.java  WikiLexer.java WikiListener.java
