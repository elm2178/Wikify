package wiki.type;

public class HtmlParser{
    private String[] page;
    private int x;
    private int y;

    public HtmlParser(String[] page){
        this.page = page;
        x = 0; //array index
        y = 0; //String index
    }

   public String[] returnInfobox(){
    	x=0;y=0;
    	String special = "infobox";
    	boolean subtitle=false;
    	boolean title=false;
    	int tableCounter=0;
    	String[] output = new String[1000];
    	int parCount=0;
    	String tag="";
    	String tempout="";
    	while(!end()){

    	goback1:
    	if(show(1).equals("<")){
    	tag = tagger();
    	if(tag.length()>5){
    	if(tag.substring(0,5).equals("table")&&tag.contains(special)){
    	tableCounter++;
    	special="asdkfjalsdjv";
    	while(true){
    		while(show(1).equals("<")){
    			tag = tagger(); //gets tag. position of vector is now right after tag
    			if(tag.length()>5){
    				if(tag.substring(0,5).equals("table")){
    				tableCounter++;
    				}
    			}
    			if(tag.length()>3){
    				if(tag.substring(0,2).equals("th")){
    				title=true;
    				}
    			}                            
    			if(tag.length()>3){
    				if(tag.substring(0,2).equals("td")){
    				subtitle=true;
    				}
    			}

    			if(tag.equals("/table")){ //if endtag
    				tableCounter--;
    				if(tableCounter==0){
    					System.out.println(tempout);
    					output[parCount++] = tempout;
    					tempout = "";
    					break goback1;
    					}
    					}
    				}
    				String temp="";
    				if(title){
    					tempout+="\n";
    				title=false;
    				}
    				if(subtitle){
    					tempout+="\n\t";
    					subtitle=false;
 				}

    				temp += getString(ahead("<"));
    				if(tempout!=null)
    					tempout+=temp+" ";
    				}
    			}
    		}

    		}
    		else{
    		skip(ahead("<"));
		}
    	}
    	String[] temp = new String[parCount]; //Get rid off nulls
    	for(int x=0; x<3; x++)
    		temp[x] = output[x];
    	output = temp;
    	return output;
    	
    }   




    //returns an array containing the infobox material
    public void getInfobox(){
	x=0;
	y=0;
	String special = "infobox";
	boolean subtitle=false;
	boolean title=false;
	int tableCounter=0;
	String[] output = new String[1000];
	int parCount=0;
	String tag="";
	String tempout="";
        while(!end()){

goback1:
            if(show(1).equals("<")){
                tag = tagger();
		if(tag.length()>5){
                    if(tag.substring(0,5).equals("table")&&tag.contains(special)){
			tableCounter++;
			special="asdkfjalsdjv";
			while(true){
			while(show(1).equals("<")){
                            tag = tagger(); //gets tag. position of vector is now right after tag
			    if(tag.length()>5){
                    		if(tag.substring(0,5).equals("table")){
				   tableCounter++;
			    	}
			    }
			    if(tag.length()>3){
				if(tag.substring(0,2).equals("th")){
				    title=true;
				}
			    }                            
                            if(tag.length()>3){
                                if(tag.substring(0,2).equals("td")){
                                    subtitle=true;
                                }
                            }

			    if(tag.equals("/table")){ //if endtag
				tableCounter--;
				if(tableCounter==0){
				    System.out.println(tempout);
                                    output[parCount++] = tempout;
                                    tempout = "";
                                    break goback1;
				}
                            }
			}
			String temp="";
			if(title){
			    tempout+="\n";
			    title=false;
			}
			if(subtitle){
			    tempout+="\n\t";
			    subtitle=false;
			}
			
			temp += getString(ahead("<"));
			if(tempout!=null)
				tempout+=temp+" ";
		    }
		    }
		}
	     	
	     }
	     else{
		 skip(ahead("<"));
	     }
	}
    }                            

    public void getTables(){
        x=0;
        y=0;
        String[] output = new String[1000];
        int parCount=0;
        String tag="";
        String tempout="";
        while(!end()){

goback1:
            if(show(1).equals("<")){
                tag = tagger();
                if(tag.length()>5){
                    if(tag.substring(0,5).equals("table")){
                        while(true){
                        while(show(1).equals("<")){
                            tag = tagger(); //gets tag. position of vector is now right after tag
                            if(tag.equals("/table")){ //if endtag
                                System.out.println(tempout);
                                output[parCount++] = tempout;
                                tempout = "";
                                break goback1;
                            }
                        }
                        tempout += getString(ahead("<"))+" ";
                    }
                    }
                }

             }
             else{
                 skip(ahead("<"));
             }
        }
    }

    public String[] getParagraphs(){ //Returns an array with all paragraphs on a Wikipage
	x=0;
	y=0;
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
                            else{}
                                //System.out.println("New Tag: " +tag);
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
        for(int x=0; x<3; x++)
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

        while(!s.equals(temp)&&!end()){
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
            //System.out.println("End of array reached!");
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
        return(x == page.length-1);//&&(y == page[x.length()-1].length()-1));
    }

///////////////////////
public class Matrix {
	double[][] data;
	public int row;
	public int column;

	public Matrix(int rows, int columns){

		this.row = rows;
		this.column = columns;
		data = new double[rows][columns];
	}
	
	//multi
	public Matrix scM(double a){
		Matrix M = this;
		for(int i=0; i<row; i++){
			for(int j=0; j<column; j++){
				M.data[i][j] *= a;
			}
		}
		return M;
	}
	
	public double sta(){
		Matrix M = this;
		double m = 0.0; 
		for(int i = 0; i<M.row; i++){
			m += M.data[i][0]*M.data[i][0];
		}
		m = Math.pow(m, 0.5);
		return m;
	}
	
	public void swap(int i, int j){
		Matrix M = this;
		double[] temp = M.data[i];
		M.data[i] = M.data[j];
		M.data[j] = temp;
	}

	public Matrix transpose() {
		Matrix M = new Matrix(column, row);
		for (int i = 0; i<row; i++){
			for (int j = 0; j<column; j++){
				M.data[j][i] = this.data[i][j];
			}
		}
		//M.printTest();
		return M;
	}

	//A*B
	public Matrix matrixMult(Matrix B){
		Matrix A = this;
		Matrix C = new Matrix(A.row, B.column);
		for (int i = 0; i<C.row; i++)
			for (int j = 0; j<C.column; j++)
				for (int k = 0; k<A.column; k++)
					C.data[i][j]+=(A.data[i][k]*B.data[k][j]);
		//C.printTest();
		return C;
	}

	//invert square matrix
	public Matrix invert(){
		Matrix A = this;
		Matrix inverse = new Matrix(A.row, A.column);
		for(int i = 0; i<inverse.row; i++){
			inverse.data[i][i] = 1.0;
		}
		//eliminate
		for (int i = 0; i<row; i++) {
			int max = i;
			for (int j=i+1; j<row; j++)
				if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
					max = j;
			
			//swaps in both matrices
			A.swap(i, max);
			inverse.swap(i, max);
			
			for (int j=i+1; j<row; j++) {
				double m = A.data[j][i] / A.data[i][i];
				for (int k = 0; k < column; k++) {
					A.data[j][k] -= A.data[i][k]*m;
					inverse.data[j][k] -= inverse.data[i][k]*m;
				}
				A.data[j][i] = 0.0;
			}
		}
		int y = A.column-1;
		int x = A.row-1;
		
		while(x > 0){
			//inverse.printTest();
			double piv = A.data[x][y];
			int w;
			for(w = x-1; w >= 0; w--){
				if(A.data[w][y] != 0){
					break;
				}
			}
			if(A.data[w][y] != 0){
				double m = A.data[w][y] / piv;
				A.data[w][y] = 0;
				for(int t = A.column-1; t>=0; -- t){
					inverse.data[w][t] -= m*inverse.data[x][t];
				}
			}y--;
			x--;
		}
		//Make pivots 1
		for(int k = 0; k<A.row; k++){
			int l = 0; 
			while(l<A.column){
				inverse.data[k][l] = inverse.data[k][l]/A.data[k][k];
				l++;
			}
			A.data[k][k] = 1;
		}
		
		//return inverse matrix to caller
		return inverse;
	}

	//tester for printing matrices
	public void printTest(){
		Matrix A = this;
		for(int i = 0; i<A.row; i++){
			for(int j = 0; j<A.column; j++){
				System.out.print(A.data[i][j]+" ");
			}
			System.out.println();
		}
	}	
}









//////////////////
}
