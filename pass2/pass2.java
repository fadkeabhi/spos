
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


public class pass2 {
 
		static HashMap<String, ArrayList<String>> mnemonic = new HashMap<String, ArrayList<String>>();
		static HashMap<Integer, ArrayList<String>> symboltab = new HashMap<Integer, ArrayList<String>>();
		static HashMap<Integer, ArrayList<String>> littable = new HashMap<Integer, ArrayList<String>>();
		static HashMap<String, Integer>registers = new HashMap<String, Integer>();
		static int lc=0;
		

//Mnemonic Table		
		public static void CreateMnemonicTable() throws FileNotFoundException
		{
			FileReader fr=new FileReader("mot.txt");    
	        BufferedReader br=new BufferedReader(fr); 
			String s=null;
			try {
				while((s=br.readLine())!=null){
					StringTokenizer tokens = new StringTokenizer(s," ",false);
					ArrayList<String> arrayList= new ArrayList<>();
					while(tokens.hasMoreTokens()){
						arrayList.add(tokens.nextToken());
					}
					String val=arrayList.get(0);
					arrayList.remove(0); 
					mnemonic.put(val, arrayList);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		

//Registers		
		public static void initRegisters() {
			registers.put("AREG",1);
			registers.put("BREG",2);
			registers.put("CREG",3);
			registers.put("DREG",4);
		}
	
		
//Symbol Table
		private static void createSymbolTable() {

			FileReader fr = null;
			try {
				fr = new FileReader("symtable.txt");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    
	        BufferedReader br=new BufferedReader(fr); 
			String s=null;
			try {
				while((s=br.readLine())!=null){
					StringTokenizer tokens = new StringTokenizer(s," ",false);
					ArrayList<String> arrayList= new ArrayList<>();
					while(tokens.hasMoreTokens()){
						arrayList.add(tokens.nextToken());
					}
					Integer val=Integer.parseInt(arrayList.get(0));
					ArrayList<String> temp= new ArrayList<>();	
					temp.add(arrayList.get(1));
					temp.add(arrayList.get(2));
					symboltab.put(val, temp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
//Literal Table
		private static void createLiteralTable() {
			
			FileReader fr = null;
			try {
				fr = new FileReader("littable.txt");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    
	        BufferedReader br=new BufferedReader(fr); 
			String s=null;
			try {
				while((s=br.readLine())!=null){
					StringTokenizer tokens = new StringTokenizer(s," ",false);
					ArrayList<String> arrayList= new ArrayList<>();
					while(tokens.hasMoreTokens()){
						arrayList.add(tokens.nextToken());
					}
					int val=Integer.parseInt(arrayList.get(0));
					ArrayList<String> temp= new ArrayList<>();	
					temp.add(arrayList.get(1));
					temp.add(arrayList.get(2));
					littable.put(val, temp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	
//Main
	public static void main(String[] args) throws FileNotFoundException {
		CreateMnemonicTable();
		initRegisters();
		createLiteralTable();
		createSymbolTable();
		
		//creating file pointers
		FileReader fr_input=new FileReader("input_ic.txt");
		FileWriter output = null;
		try {
			output = new FileWriter("final_output.txt");
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		try {					
			BufferedReader br_input=new BufferedReader(fr_input); 
			
			String s = null;
			int lineno=0;
			
			while((s=br_input.readLine())!=null){
				
				if(lineno==0)
				{
					lineno++;
					continue;
				}
				ArrayList<String> arrayList= new ArrayList<>();
				StringTokenizer tokens = new StringTokenizer(s," ",false);
				
				while(tokens.hasMoreTokens()){
					arrayList.add(tokens.nextToken());
				}
				int tokenCount=arrayList.size();
				String temp="";
				System.out.println(s);
				System.out.print("arraylist: ");
				for(int i=0;i<arrayList.size();i++)
				{
					System.out.print(arrayList.get(i)+" ");
				}
				
				System.out.println(" ");
				System.out.print(tokenCount+"=>");
				if(tokenCount==1)					//Assembler directive instructions
				{
					continue;
				}
				else if(tokenCount==2)
				{
					
					temp+=arrayList.get(0)+"  ";
					temp+=arrayList.get(1)+"\n";
					System.out.println(temp);
				}
				else if(tokenCount==3)    			//Declarative statements
				{
					temp+=arrayList.get(0)+"  ";
					temp+=arrayList.get(1).substring(1, 3)+"  ";
					String Slast_token=arrayList.get(2);
					int index=0;
					String si="";
					for(int i=3; (Slast_token.charAt(i)!=')' && i<Slast_token.length()); i++)
					{
						si+=Slast_token.charAt(i);
					}
					temp+=si+"\n";
					System.out.println(temp);
				}
				else if(tokenCount==4)    			//Imperative statements with register
				{
					System.out.println("4th token"+arrayList.get(3));
					temp+=arrayList.get(0)+"  ";// address
					temp+=arrayList.get(1).substring(1, 3)+"  "; 	//opcode
					
					String Slast_token=arrayList.get(2);    		//operand 1
					if(Slast_token.charAt(0)=='1'||Slast_token.charAt(0)=='2'||Slast_token.charAt(0)=='3'||Slast_token.charAt(0)=='4')
					{
						temp+=Slast_token+" ";
					}
					else if(Slast_token.charAt(1)=='L') 			//literal 
					{
						int index=0;
						String si="";
						for(int i=3; (Slast_token.charAt(i)!=')' && i<Slast_token.length()); i++)
						{
							si+=Slast_token.charAt(i);
						}
						index=Integer.parseInt(si);
						temp+=littable.get(index).get(1)+"  ";
						
					}
					else if(Slast_token.charAt(1)=='S') 
					{
						int index=0;
						String si="";
						for(int i=3; (Slast_token.charAt(i)!=')' && i<Slast_token.length()); i++)
						{
							si+=Slast_token.charAt(i);
						}
						index=Integer.parseInt(si);
						temp+=symboltab.get(index).get(1)+"  ";
					}
					else if(Slast_token.charAt(1)=='C') 
						{
							int index=0;
							String si="";
							for(int i=3; (Slast_token.charAt(i)!=')' && i<Slast_token.length()); i++)
							{
								si+=Slast_token.charAt(i);
							}
							index=Integer.parseInt(si);
							temp+=si;
						}

					Slast_token=arrayList.get(3);
					if(Slast_token.charAt(1)=='L') 	//literal 
					{
						int index=0;
						String si="";
						for(int i=3; (Slast_token.charAt(i)!=')' && i<Slast_token.length()); i++)
						{
							si+=Slast_token.charAt(i);
						}
						index=Integer.parseInt(si);
						temp+=littable.get(index).get(1)+"  ";
						
					}
					else if(Slast_token.charAt(1)=='S') 
					{
						int index=0;
						String si="";
						for(int i=3; (Slast_token.charAt(i)!=')' && i<Slast_token.length()); i++)
						{
							si+=Slast_token.charAt(i);
						}
						index=Integer.parseInt(si);
						temp+=symboltab.get(index).get(1)+"  ";
					}
					else if(Slast_token.charAt(1)=='C') 
						{
							int index=0;
							String si="";
							for(int i=3; (Slast_token.charAt(i)!=')' && i<Slast_token.length()); i++)
							{
								si+=Slast_token.charAt(i);
							}
							index=Integer.parseInt(si);
							temp+=si;
						}
					temp+="\n";
					System.out.println(temp);
				}
			  output.write(temp);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		finally {
			try {
				if (output != null) {
					output.flush();
					output.close();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
