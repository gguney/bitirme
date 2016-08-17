//<Designed Developed By Gokhan Guney
//st081101028@etu.edu.tr
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	final static String FILE_NAME = "C:\\Users\\user\\Dropbox\\MINE SYNC\\School\\BİTİRME PROJESİ\\savedrecs01.html";
	final static String OUTPUT_FILE_NAME = "C:\\Temp\\output.txt";
	final static Charset ENCODING = StandardCharsets.UTF_8;
	static String PT,AU,AF,TI,SO,LA,DT,DE,ID,AB,C1,RP,RI,CR,NR,TC,Z9,PU,PI,PA,SN,J9,JI,PD,PY,VL,IS,MA,BP,EP,PN,DI,PG,WC,SC,GA,UT;
	public String[] abrNames = {"PT","AU","AF","TI","SO","LA","DT","DE","ID","AB","C1","RP","RI","CR","NR","TC","Z9","PU","PI","PA","SN","J9","JI","PD","PY","VL","IS","MA","BP","EP","PN","DI","PG","WC","SC","GA","UT"};
	public String[] abrConstants = {PT,AU,AF,TI,SO,LA,DT,DE,ID,AB,C1,RP,RI,CR,NR,TC,Z9,PU,PI,PA,SN,J9,JI,PD,PY,VL,IS,MA,BP,EP,PN,DI,PG,WC,SC,GA,UT};
	public String[] AUArray = new String[1000]; 
	public String[] AFArray = new String[1000]; 
	public String[] CRArray = new String[1000]; 
	public String[] C1Array = new String[1000]; 
	public String[] RIArray = new String[1000]; 
	public int AUCount,AFCount,CRCount,C1Count,RICount = 1;
	

 	static long before ,after,totalMS;
	static double totalS;
	static long recordCount;
	public Parser ()
	{

	}
	public static void main(String args[]) {
		
		//Folder read
		Parser p = new Parser();
		p.read(FILE_NAME);
		
	}
	

	 public void read(String fileName)  {
	        Path path = Paths.get(fileName);
	        Scanner scanner = null;
	        String s = "";
	        recordCount=0;

				try {
					scanner = new Scanner(path);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				before = System.currentTimeMillis();
	        while(scanner.hasNextLine()){ 
	        	s = s + scanner.nextLine();
	            if(scanner.findInLine("</table><hr>")!=null)
	            {
	               	s = s.substring(s.indexOf("<td"));
	    	        parse(s); 
	    	        for(int i = 0; i<abrNames.length;i++)
	    	        {
	    	        	
		    	    System.out.println(abrNames[i]+": "+abrConstants[i]);

	    	        }
	    	       System.out.println("---------------------------");

	    	 		recordCount++;
	        		s= "";	//Empty the Scanner record.
	            }
	            

	        }
	        //Time calculator
            after = System.currentTimeMillis();
            totalMS=after-before;
            totalS=totalMS/1000.0;
            System.out.println("Total: "+recordCount+" records parsed. It took: "+totalS+" seconds.");

	    }
	 public void parse(String s)
		{
	        	for(int i = 0; i< abrNames.length;i++)
	        	{
	        		if(s.contains(">"+abrNames[i]+" </td><td>"))
	        		{
		        		s = s.substring(s.indexOf(">"+abrNames[i]+" </td><td>")+13);
		        		abrConstants[i] = s.substring(0,s.indexOf("</td>"));
		        		//Replace 
		        		abrConstants[i]=replace(abrConstants[i]);
		        		
		        		if(abrConstants[i].contains("<br>")){
		        			//If there is more than 1 entry for this record.  
		        			 if(abrNames[i].equals("AU"))
		        				{
				        			brParse(abrNames[i],abrConstants[i],AUArray,AUCount);
		        				}
		        			 else if(abrNames[i].equals("AF"))
		        				{
				        			brParse(abrNames[i],abrConstants[i],AFArray,AFCount);
		        				}
		        			 else if(abrNames[i].equals("CR"))
		        				{
				        			brParse(abrNames[i],abrConstants[i],CRArray,CRCount);
		        				}
		        			 
		        			 else if(abrNames[i].equals("C1"))
		        				{
				        			brParse(abrNames[i],abrConstants[i],C1Array,C1Count);
		        				}
		        			 else if(abrNames[i].equals("RI"))
		        				{
				        			brParse(abrNames[i],abrConstants[i],RIArray,RICount);
		        				}
		        		}
	        		}
	        		else{
	        			//If there is no Abr for this record
	        			abrConstants[i]="***NULL***";
	        		}  			
	        	}
		}	
	 
	 public int brCounter(String a)
	 {
			Pattern pattern = Pattern.compile("<br>");
		    Matcher matcher = pattern.matcher(a);
		    int countCharacter = 0;
		    while(matcher.find()) {
		        countCharacter++;
		    }
			return countCharacter;
	 }
	 
	 public void brParse(String abrName,String abrConstant,String[] Array,int count)
	 {
			 	count = brCounter(abrConstant)+1;
				for(int c=0;c<count;c++)
				{

					if(abrConstant.contains("<br>"))
					{
					
						Array[c]=abrConstant.substring(0,abrConstant.indexOf("<br>"));
						abrConstant= abrConstant.substring(abrConstant.indexOf("<br>")+4,abrConstant.length());
					}
					else
					{
						Array[c]=abrConstant;
					}					
				}	 
				for(int c=0;c<count;c++)
				{
					System.out.println(abrName+": "+Array[c]);
				}					     
	 }
	 
	 public String replace(String a)
	 {

 		a=a.replaceAll("&amp;", "&");
 		return a;
	 }
}
