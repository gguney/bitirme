//<Designed Developed By Gokhan Guney
//st081101028@etu.edu.tr
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ParserNew  {

	final static Charset ENCODING = StandardCharsets.UTF_8;
	public String PT,AU,AF,TI,SO,LA,DT,DE,ID,AB,C1,RP,EM,RI,CR,NR,TC,Z9,PU,PI,PA,SN,J9,JI,PD,PY,VL,IS,BP,EP,PN,DI,PG,WC,SC,GA,UT;
	public String[] abrNames = {"PT","AU","AF","TI","SO","LA","DT","DE","ID","AB","C1","RP","EM","RI","CR","NR","TC","Z9","PU","PI","PA","SN","J9","JI","PD","PY","VL","IS","BP","EP","PN","DI","PG","WC","SC","GA","UT"};
	public String[] abrConstants = new String[100];
	private String tmp;	
	public static String newline = System.getProperty("line.separator"); 

 	static long before ,after,totalMS;
	static double totalS;
	
    Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "bil496";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String password = ""; 

    Statement komut;
    String query;
    
    static String fileName;
    
    int sucCount,errCount=0;

	public ParserNew ()
	{
	    try {
			Class.forName(driver).newInstance();
		    conn = DriverManager.getConnection(url + dbName, userName, password);
		    System.out.println("Connected to the database");
		    komut = conn.createStatement();
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}


	 public void read(String fileName)  {
	        Path path = Paths.get(fileName);
            Logger l = new Logger();
            
	        Scanner scanner = null;
	        String s = "";

				try {
					scanner = new Scanner(path);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			    System.out.println("Parsing Started For: "+fileName);
	            l.write("Parsing Started For: "+fileName+newline);

				before = System.currentTimeMillis();
			  
				for(int i = 0; i<abrConstants.length;i++)
        		{
        			abrConstants[i]="***NULL***";
        		}
		
				
	        while(scanner.hasNextLine()){ 
	        	s = s + scanner.nextLine();
	            if(scanner.findInLine("<td>ER</td>")!=null)
	            {
	               	s = s.substring(s.indexOf("<td"));
	    	        parse(s); 

	    	        //Writing to DB

		    	       ParserAll.recordCount++;

	    	            query = "INSERT INTO bil496.Main "
	    	                    + "(`ArticleID`,`PT`,`AF`,`TI`,`SO`,`LA`,`DT`,`DE`,`ID`,`AB`,`C1`,`RP`,`EM`,`RI`,`CR`,"
	    	            		+ "`NR`,`TC`,`Z9`,`PU`,`PI`,`PA`,`SN`,`J9`,`JI`,`PD`,`PY`,`VL`,`IS`,"
	    	            		+ "`BP`,`EP`,`PN`,`DI`,`PG`,`WC`,`SC`,`GA`,`UT`"
	    	                    + ")"
	    	                    + "VALUES"
	    	                    + "("
	    	                    + "null"+","					//ArticleID
	    	                    +"\""+ abrConstants[0] +"\","	//PT
	    	                    +"\""+ abrConstants[2] +"\","	//AF
	    	                    +"\""+ abrConstants[3] +"\","	//TI
	    	                    +"\""+ abrConstants[4] +"\","	//SO
	    	                    +"\""+ abrConstants[5] +"\","	//LA
	    	                    +"\""+ abrConstants[6] +"\","	//DT
	    	                    +"\""+ abrConstants[7] +"\","	//DE
	    	                    +"\""+ abrConstants[8] +"\","	//ID
	    	                    +"\""+ abrConstants[9] +"\","	//AB
	    	                    +"\""+ abrConstants[10] +"\","	//C1
	    	                    +"\""+ abrConstants[11] +"\","	//RP
	    	                    +"\""+ abrConstants[12] +"\","	//EM
	    	                    +"\""+ abrConstants[13] +"\","	//RI
	    	                    +"\""+ abrConstants[14] +"\","	//CR
	    	                    
	    	                    +"\""+ abrConstants[15] +"\","	//NR
	    	                    +"\""+ abrConstants[16] +"\","	//TC
	    	                    +"\""+ abrConstants[17] +"\","	//Z9
	    	                    +"\""+ abrConstants[18] +"\","	//PU
	    	                    +"\""+ abrConstants[19] +"\","	//PI
	    	                    +"\""+ abrConstants[20] +"\","	//PA
	    	                    +"\""+ abrConstants[21] +"\","	//SN
	    	                    +"\""+ abrConstants[22] +"\","	//J9
	    	                    +"\""+ abrConstants[23] +"\","	//JI
	    	                    +"\""+ abrConstants[24] +"\","	//PD
	    	                    +"\""+ abrConstants[25] +"\","	//PY
	    	                    +"\""+ abrConstants[26] +"\","	//VL
	    	                    +"\""+ abrConstants[27] +"\","	//IS
	    	                    
	    	                    +"\""+ abrConstants[28] +"\","	//BP
	    	                    +"\""+ abrConstants[29] +"\","	//EP
	    	                    +"\""+ abrConstants[30] +"\","	//PN
	    	                    +"\""+ abrConstants[31] +"\","	//DI
	    	                    +"\""+ abrConstants[32] +"\","	//PG
	    	                    +"\""+ abrConstants[33] +"\","	//WC
	    	                    +"\""+ abrConstants[34] +"\","	//SC
	    	                    +"\""+ abrConstants[35] +"\","	//GA
	    	                    +"\""+ abrConstants[36] +"\""	//UT
	    	                    +");";

	    	            try {
							komut.executeUpdate(query);
							l.write( ParserAll.recordCount+") "+ abrConstants[36]  +" Added, " +newline);
							sucCount++;
							ParserAll.suc++;
						}
						catch (SQLException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();

							//StringWriter sw = new StringWriter();
							//new Throwable().printStackTrace(new PrintWriter(sw));
							//l.write( abrConstants[21] + " ERROR " + sw.toString()+ e.toString());
							String oldQuery = query;

		    	            query = "INSERT INTO bil496.Main "
		    	                    + "(`ArticleID`,`PT`,`AF`,`TI`,`SO`,`LA`,`DT`,`DE`,`ID`,`AB`,`C1`,`RP`,`EM`,`RI`,`CR`,"
		    	            		+ "`NR`,`TC`,`Z9`,`PU`,`PI`,`PA`,`SN`,`J9`,`JI`,`PD`,`PY`,`VL`,`IS`,"
		    	            		+ "`BP`,`EP`,`PN`,`DI`,`PG`,`WC`,`SC`,`GA`,`UT`"//37 columns
		    	                    + ")"
		    	                    + "VALUES"
		    	                    + "("
		    	                    + "null"+","					
		    	                    + "\"ERROR\""+","	
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","	
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","	
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","	
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+","		
		    	                    + "null"+""	
		    	                  	
		    	                    +");";

		    	            try {
								komut.executeUpdate(query);
								//l.write(abrConstants[21] + " ERROR ");
							}
							catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();

								//StringWriter sw = new StringWriter();
								//new Throwable().printStackTrace(new PrintWriter(sw));
								//l.write( abrConstants[21] + " ERROR " + sw.toString()+ e.toString());
							}				
							
							l.write(ParserAll.recordCount+") "+ abrConstants[36] + " ERROR " + e.toString()+newline);
							l.write("ERROR QUERY: "+oldQuery+newline);
							System.out.println("ERROR");
							errCount++;
							ParserAll.err++;
						}

	    	     //  System.out.println("---------------------------");

	    	            
	    	       
	    	       //INITIALIZE STARTS
	        		s= "";	//Empty the Scanner record.
	        		tmp="";
	        		for(int i = 0; i<abrConstants.length;i++)
	        		{
	        			abrConstants[i]="***NULL***";
	        		}
	        		query="";
	        		//INITIALIZE ENDS
	            }
	            

	        }
				
				
	        //Time calculator
            after = System.currentTimeMillis();
            totalMS=after-before;
            totalS=totalMS/1000.0;
		    System.out.println("Parsing ended for: "+ fileName+" in "+totalS+" seconds.");
            l.write("Parsing ended for: "+ fileName+" in "+totalS+" seconds."+newline);
            l.write("With Success: "+sucCount+" and Error: "+errCount+newline);
            l.write("------------------------------------------------------------------"+newline);
            sucCount=0;
            errCount=0;
            l.close();

	    }
	 public void parse(String s)
		{
	        	for(int i = 0; i< abrNames.length;i++)
	        	{
	        		if(s.contains(">"+abrNames[i]+" </td><td>"))
	        		{
	        			tmp = s;
		        		s = s.substring(s.indexOf(">"+abrNames[i]+" </td><td>")+13);
		        		abrConstants[i] = s.substring(0,s.indexOf("</td>"));
		        		//Replace 
		        		abrConstants[i]=replace(abrConstants[i]);
		        		s=tmp;
	        		}
	        		else{
	        			//If there is no Abr for this record
	        			//abrConstants[i]="***NULL***";
	        		}  			
	        	}
	       
		}	
	 

	
	 
	 public String replace(String a)
	 {
	 	a=a.replaceAll("\"", "'");//Replace " with '
	 	a=a.replaceAll("\\\\", "");//Replace \ with nothing
 		a=a.replaceAll("&amp;", "&");//Replace &amp with &


 		return a;
	 }
}