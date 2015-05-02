run: 
	# use below export CLASSPATH line to set classpath before running make"
	#export CLASSPATH=".:antlr-4.0-complete.jar:$$CLASSPATH"
	java -jar antlr-4.0-complete.jar Wiki.g4
	javac Wiki*.java
	javac Test.java
	cat file.wiki | java Test > TestWiki.java && javac TestWiki.java && java TestWiki

clean:
	rm *.class *.tokens WikiBaseListener.java WikiParser.java  WikiLexer.java WikiListener.java
