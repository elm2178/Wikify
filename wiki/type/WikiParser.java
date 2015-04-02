public class WikiParser{
	private String[] page;
	private int x;
	private int y;

	public WikiParser(String[] page){
		this.page = page;
		x = 0; //array index
		y = 0; //String index
	}

	public String[] getParagraphs(){ //Returns an array with all paragraphs on a Wikipage

        String[] output = new String[1000];
        int parCount = 0;
        String tag = "";
        String tempout = "";

        while(!end()){

        	goback:
            if(show(1).equals("<")){
           		
           		tag = tagger();

           		if(tag.equals("p")){

           			while(true){

            			while(show(1).equals("<")){
            			    tag = tagger(); //gets tag. position of vector is now right after tag
            			    if(tag.equals("/p")){ //if endtag
            			    	output[parCount++] = tempout;
            			    	tempout = "";
            			        break goback;
            			    }
            			    else if(tag.substring(0,1).equals("/"))
            			        ;
            			    else if(tag.substring(0,1).equals("a"))            //takes out link tags
            			        ;
            			    else if(tag.equals("b"))                            //takes out bold tags
            			        ;
            			    else if(tag.equals("span"))
            			        skipper("</span>");
            			    else if(tag.length()>2 && tag.substring(0,3).equals("sup")){
            			        skipper("</sup>");
            			    }
            			    else
            			        System.out.println("New Tag: " +tag);
            			}
            			tempout += getString(ahead("<"));
            		}
            	}    	
            }
            else{
            	skip(ahead("<"));
            }
        }

        String[] temp = new String[parCount]; //Get rid off nulls
        	for(int x=0; x<parCount; x++)
                temp[x] = output[x];
            output = temp;
        return output;
    }

    private String tagger(){ //finds and returns tag
        String tag = "";
        next();
        int x = ahead(">");
		tag = getString(x);
		next();
		//System.out.println("TAG: "+tag);
		return tag;
    }

    private void skipper(String s){ //Skips until after first occurence of s
        skip(ahead(s)+s.length());
    }

	private String get(){ //returns a "size" long String. Position vector moves on
		String out = page[x].substring(y,y+1);
		next();
		return out;
	}

	private String getString(int size){ //returns a "size" long String. Position vector moves on
		String out = "";
		for(int i=0; i<size; i++)
			out += get();
		return out;
	}

	private String show(int size){ //returns the next "size" number of chars
		String out = getString(size);
		reverse(size);
		return out;
	}

	private int ahead(String s){ //Looks ahead for a certain String, returns the distance to the first char of String
		int a=x;
		int b=y;
		int counter=1;
		String temp = "";

		for(int i=0; i<s.length(); i++){
			if(b<page[a].length()-1)
				b++;
			else if(a!=page.length-1){
				a++;
				while(page[a].equals(""))
					a++;
				b=0;
			}
			else{
				System.err.println("End of HTML reached!");
				System.err.println("ahead(String s) failed.");
				System.exit(-1);
			}

			temp+=page[a].substring(b,b+1);
		}

		while(!s.equals(temp)){
			counter++;

			if(b<page[a].length()-1)
				b++;
			else if(a!=page.length-1){
				a++;
				while(page[a].equals(""))
					a++;
				b=0;
			}
			else{
				System.err.println("End of HTML reached!");
				System.err.println("ahead(String s) failed.");
				System.exit(-1);
			}

			temp = temp.substring(1,s.length()) + page[a].substring(b,b+1);
		}

		return counter;
	}

	private void skip(int a){	//skips "a" chars
		for(int i=0; i<a; i++)
			next();
	}

	private void next(){	//changes position vector to point at next char
		if(y<page[x].length()-1)
			y++;
		else if(x!=page.length-1&&page[x+1]!=null){
			x++;
			while(page[x].equals(""))
				x++;
			//System.out.println("NEWLINE: "+page[x]);
			y=0;
		}
		else{
			System.out.println("End of array reached!");
			x = page.length-1;
			y = page[x].length()-1;
		}
	}

	private void reverse(int a){	//changes vector to point at prior char
		for(int i=0; i<a; i++){
			if(y!=0){
				y--;
			}
			else if(x!=0){
				x--;
				y = page[x].length()-1;
			}
		}
	}

	private boolean end(){
		return((x == page.length-1)&&(y == page[x].length()-1));
	}
}