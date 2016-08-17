//<Designed Developed By Gokhan Guney
//st081101028@etu.edu.tr
package tableToTable;



import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParserMainToOther {

	static Logger2 l= new Logger2();
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public static String newline = System.getProperty("line.separator");

	static long before, after;
	static double totalRunTime;

	static String fileName;

	static int totalCount;
	int sucCount;
	int errCount = 0;

	static Connection conn = null;
	static String url = "jdbc:mysql://localhost:3306/";
	static String dbName = "bil496";
	static String driver = "com.mysql.jdbc.Driver";
	static String userName = "root";
	static String password = "";
	static Statement statement = null,statement2=null,statement3=null,statement4=null,statement5=null,statement6=null,statement7=null;
	static String query=null,queryLA=null,querySO=null,queryAF=null,querySC=null,queryCR=null,queryC1=null;
	static String univ="***NULL***";//University Name
	static String fac="***NULL***";//Faculity Name
	static String dept="***NULL***";//Department Name
	static String postalCode="***NULL***";//Postal Code
	static String city="***NULL***";//City Name
	static String country="***NULL***";//Country Name
	
	public static void main(String[] args) {

	    File file = new File(Logger2.LOG_FILE_NAME);
	    file.delete();
		l= new Logger2();
	    
		try {

			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			statement = conn.createStatement();
			statement2 = conn.createStatement();
			statement3 = conn.createStatement();
			statement4 = conn.createStatement();
			statement5 = conn.createStatement();
			statement6 = conn.createStatement();
			statement7 = conn.createStatement();
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		query = "Select ArticleID from bil496.main order by ArticleID desc limit 1;";
		try {
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				totalCount = rs.getInt("ArticleID");
				System.out.println("Total Record Count: " + totalCount);
				l.write("Total Record Count: " + totalCount+newline);
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();

		}
		l.write("System started..."+newline);
		before = System.currentTimeMillis();
		double percentage = totalCount / 100;
		//for (int i = 6000; i <= totalCount; i++) {
	for (int i = 1; i <=8000; i++) {
			query = "select ArticleID,AF,SO,CR,C1,LA,SC,PY from bil496.main where ArticleID = "+i+";";
			try {
				ResultSet rs = statement.executeQuery(query);
				while (rs.next()) 
				{
			
					String AF = rs.getString("AF");//1toMany
					String SO = rs.getString("SO");//1to1
					String CR = rs.getString("CR");//1toMany
					String C1 = rs.getString("C1");//1toMany
					String LA = rs.getString("LA");//1to1
					String SC = rs.getString("SC");//1toMany
					int PY = rs.getInt("PY");
					//AF = replaceAll(AF);
					//SO = replaceAll(SO);
					CR = replaceAll(CR);
					//C1 = replaceAll(C1);
					//LA = replaceAll(LA);
					//SC = replaceAll(SC);
					
					//parseLA(i,LA,PY);//DONE
					//parseSO(i,SO,PY);//DONE
					//parseSC(i,SC,PY);//DONE
					//parseCR(i,CR,PY);//DONE
				   parseAFandC1(i,AF,C1,PY);

					if ((int) (i/percentage) > (int) ((i-1)/percentage))
					{
						System.out.println("% "+(int) (i/percentage) +" is done.");
					}

				}


			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(e1);

			}

		}

		after = System.currentTimeMillis();
		totalRunTime = (after - before)/60000.0;
		l.write("Total Run Time: "+ totalRunTime + " mins."+newline);
		l.write("System Stopped..."+newline);
		System.out.println("Total Run Time: "+ totalRunTime + " mins.");
		System.out.println("System Stopped...");
		l.close();

//Logging


	}
	//LANGUAGES TABLE STARTS
	private static void parseLA (int i,String LA,int PY)
	{
		queryLA = "INSERT INTO bil496.langs (ArticleID,Lang,PublicationYear) VALUES ("+i+",'"+LA+"',"+PY+");";
		try{
			statement2.executeUpdate(queryLA);
		}

		catch (SQLException eLA) {
			// TODO Auto-generated catch block
			l.write("#"+i +" article has an error on Laguage Parsing."+newline);
			l.write("SQLSyntax: "+queryLA);
			eLA.printStackTrace();
			System.out.println("SQLSyntax: "+queryLA);

		}
	}
	//LANGUAGES TABLE ENDS
	
	//SUBJECTS TABLE STARTS
	private static void parseSO (int i,String SO,int PY)
	{
		querySO = "INSERT INTO bil496.subjects (ArticleID,Subject,PublicationYear) VALUES ("+i+",'"+SO+"',"+PY+");";

		try{
			statement3.executeUpdate(querySO);
		}

		catch (SQLException eSO) {
			l.write("#"+i +" article has an error on Subject."+newline);
			l.write("SQLSyntax: "+querySO);
			eSO.printStackTrace();
			System.out.println("SQLSyntax: "+querySO);

		}
	}
	//SUBJECTS TABLE ENDS
	
	//CATEGORIES TABLE STARTS
	private static void parseSC (int i,String SC,int PY)
	{

		if(!SC.contains(";"))
		{
			querySC = "INSERT INTO bil496.categories (ArticleID,SC,PublicationYear) VALUES ("+i+",'"+SC+"',"+PY+");";
			try{
				statement5.executeUpdate(querySC);
			}

			catch (SQLException eSC) {
				l.write("#"+i +" article has an error on Categories (SC).");
				l.write("SQLSyntax: "+querySC);
				// TODO Auto-generated catch block
				eSC.printStackTrace();
				System.out.println("SQLSyntax: "+querySC);

			}
		}
		else
		{

			while(SC.contains(";"))
			{
				querySC = "INSERT INTO bil496.categories (ArticleID,SC,PublicationYear) VALUES ("+i+",'"+SC.substring(0,SC.indexOf(";"))+"',"+PY+")";
				try{
					statement5.executeUpdate(querySC);
				}

				catch (SQLException eSC) {
					l.write("#"+i +" article has an error on Categories (SC).");

					// TODO Auto-generated catch block
					eSC.printStackTrace();
					System.out.println(eSC);

				}
				SC = SC.substring(SC.indexOf(";")+2);
			}
			querySC = "INSERT INTO bil496.categories (ArticleID,SC,PublicationYear) VALUES ("+i+",'"+SC+"',"+PY+")";
			try{
				statement5.executeUpdate(querySC);
			}

			catch (SQLException eSC) {
				l.write("#"+i +" article has an error on Categories (SC).");

				// TODO Auto-generated catch block
				eSC.printStackTrace();
				System.out.println(eSC);

			}
			
		}
		


	
	}
	//CATEGORIES TABLE ENDS
	
	//CITEDREFERENCES TABLE STARTS
	private static void parseCR (int i,String CR,int PY)
	{
		String DOI=null,other=null;
	    int CRY=0;
	    String tmpCRY ="";
	    //For 1 Cited Reference
	    if (CR.equals("***NULL***"))
	    {
	    	//System.out.println("null");
	    }
	    else if(!CR.contains("<br>"))
		{
			if(CR.contains("DOI"))
			{
				DOI = CR.substring(CR.indexOf("DOI")+3);
				DOI = DOI.trim();
				other = CR;
			}
			else
			{
				DOI = "***NULL***";
				other = CR;
			}
			other= other.trim();
					tmpCRY = CR.substring(CR.indexOf(",")+1).trim().substring(0,4);
					
			 try{
					CRY = Integer.parseInt(tmpCRY);

			 }
		     catch(NumberFormatException e) 
		     { 
		   
		        CRY =0; 
		     }
			queryCR = "INSERT INTO bil496.citedreferences (ArticleID,DOI,Other,CitedReferenceYear,PublicationYear) VALUES ("+i+",'"+DOI+"','"+other+"',"+CRY+","+PY+");";
			try{
				statement6.executeUpdate(queryCR);
			}

			catch (SQLException eCR) {
				l.write("#"+i +" article has an error on CR.");
				// TODO Auto-generated catch block
				eCR.printStackTrace();
				System.out.println(queryCR);

			}
		
		}
		else
		{
			while (CR.contains("<br>"))
			{
				String tmp = CR.substring(0,CR.indexOf("<br>")-3);
				if(tmp.contains(", DOI"))
				{
					DOI = tmp.substring(tmp.indexOf(", DOI")+5);
					DOI=DOI.trim();
					other = tmp;
				}
				else
				{
					DOI = "***NULL***";
					other = tmp;
					
				}
				other= other.trim();
		 try{
				tmpCRY = tmp.substring(0,4);//Yılın ilk başta olma durumu				
				CRY = Integer.parseInt(tmpCRY);

		 }
	     catch(NumberFormatException e) 
	     { 
			try{
		    	 tmpCRY = tmp.substring(tmp.indexOf(",")+1).trim().substring(0,4);
					CRY = Integer.parseInt(tmpCRY);


			}
				catch (NumberFormatException e2)
				{
			        CRY =0; 

				}
	    	 
	     }
			queryCR = "INSERT INTO bil496.citedreferences (ArticleID,DOI,Other,CitedReferenceYear,PublicationYear) VALUES ("+i+",'"+DOI+"','"+other+"',"+CRY+","+PY+");";
				try{
					statement6.executeUpdate(queryCR);
				}

				catch (SQLException eCR) {
					l.write("#"+i +" article has an error on CR.");

					// TODO Auto-generated catch block
					eCR.printStackTrace();
					System.out.println(queryCR);

				}
				CR = CR.substring(CR.indexOf("<br>")+4);


			}
			if(CR.contains(", DOI"))
			{
				DOI = CR.substring(CR.indexOf(", DOI")+5);
				DOI=DOI.trim();
				other = CR;
				other=other.trim();
			}
			else
			{
				DOI = "***NULL***";
				other = CR;
				other=other.trim();

			}
			System.out.println(CR);

			 try{
					tmpCRY = CR.substring(0,4);//Yılın ilk başta olma durumu				
					CRY = Integer.parseInt(tmpCRY);

			 }
		     catch(NumberFormatException e) 
		     { 
				try{
			    	 tmpCRY = CR.substring(CR.indexOf(",")+1).trim().substring(0,4);
						CRY = Integer.parseInt(tmpCRY);


				}
					catch (NumberFormatException e2)
					{
				        CRY =0; 

					}
		    	 
		     }
			queryCR = "INSERT INTO bil496.citedreferences (ArticleID,DOI,Other,CitedReferenceYear,PublicationYear) VALUES ("+i+",'"+DOI+"','"+other+"',"+CRY+","+PY+");";
			try{
				statement6.executeUpdate(queryCR);
			}

			catch (SQLException eCR) {
				l.write("#"+i +" article has an error on CR.");

				// TODO Auto-generated catch block
				eCR.printStackTrace();
				System.out.println(queryCR);

			}
		
		}

	}
	//CITEDREFERENCES TABLE ENDS
	
	//AUTHORS AND DEPARTMENTS TABLE STARTS
	private static void parseAFandC1 (int i,String AF,String C1,int PY)
	{

		//System.out.println(AF);
		if (C1.equals("***NULL***"))
		{
			
		}
		
		else if(!AF.contains("<br>"))
		{
			univ = "***NULL***";
			fac = "***NULL***";
			dept = "***NULL***";
			postalCode = "***NULL***";
			city = "***NULL***";
			country = "***NULL***";
			System.out.println(i +", Tek yazar: "+AF);
			System.out.println(C1);

			parseInstitution(C1);
			System.out.println("University: "+univ);
			System.out.println("Faculity Name: "+fac);
			System.out.println("Department: "+dept);
			System.out.println("Postal Code: "+postalCode);
			System.out.println("City: "+city);
			System.out.println("Country: "+country);
			System.out.println("----------------------------------------");
			

		}
		//System.out.println(PY);

	}
	public static void parseInstitution(String institution)
	{
		String tmpIns = institution;
		univ = institution.substring(0,institution.indexOf(","));
		univ=univ.trim();
		if(institution.contains("Fac"))
		{
			fac = institution.substring(institution.indexOf("Fac")+3).substring(0,institution.substring(institution.indexOf("Fac")+3).indexOf(","));
			fac = fac.trim();
		}
		if(institution.contains("Dept"))
		{
			dept = institution.substring(institution.indexOf("Dept")+4).substring(0,institution.substring(institution.indexOf("Dept")+4).indexOf(","));
			dept = dept.trim();
		}
		if(institution.contains("Dept"))
		{
			dept = institution.substring(institution.indexOf("Dept")+4).substring(0,institution.substring(institution.indexOf("Dept")+4).indexOf(","));
			dept = dept.trim();
		}
		if(institution.contains("TR-"))
		{
			postalCode = institution.substring(institution.indexOf("TR-")).substring(0,institution.substring(institution.indexOf("TR-")).indexOf(","));
			city = postalCode.substring(postalCode.indexOf(" ")).trim();
			postalCode = postalCode.substring(0,postalCode.indexOf(" ")).trim();
		}
		else
		{
			//only City Name
		}
		country = institution.substring(institution.lastIndexOf(" "));
		if(country.contains("."))
		{
			country = country.replace(".", "");
			country= country.trim();
		}

	}
	
	/**




		if(!AF.contains("<br>"))
		{
			queryAF = "INSERT INTO bil496.authors (ArticleID,Author,Address,PublicationYear) VALUES ("+i+",'"+AF+"','"+C1+"',"+PY+");";
			try{
				statement7.executeUpdate(queryAF);
			}

			catch (SQLException eAF) {
				l.write("#"+i +" article has an error on AF and Department.");

				// TODO Auto-generated catch block
				eAF.printStackTrace();
				System.out.println(eAF);

			}
			//Insertion to C1 table
			C1= C1.trim();
			queryC1 = "INSERT INTO bil496.departments (ArticleID,C1,PublicationYear) VALUES ("+i+",'"+C1+"',"+PY+");";
			try{
				statement6.executeUpdate(queryC1);
			}

			catch (SQLException eC1) {
				l.write("#"+i +" article has an error on AF and Department.");

				// TODO Auto-generated catch block
				eC1.printStackTrace();
				System.out.println(eC1);

			}

			
		}
		else
		{
			while (AF.contains("<br>"))
			{
				String author = AF.substring(0,AF.indexOf("<br>")-3);
				
				String tmpC1 = C1;
				if(C1.contains(author))
				{
					tmpC1 = tmpC1.substring(C1.indexOf(author));
					try{
						tmpC1 =tmpC1.substring(tmpC1.indexOf("]")+1,tmpC1.indexOf("<br>"));

					}
					catch (StringIndexOutOfBoundsException e)
					{
						tmpC1 =tmpC1.substring(tmpC1.indexOf("]")+1);

					}
					tmpC1=tmpC1.trim();
					
				}
				else
				{
					
					tmpC1 = "***NULL***";
				}

				queryAF = "INSERT INTO bil496.authors (ArticleID,Author,Address,PublicationYear) VALUES ("+i+",'"+author+"','"+tmpC1+"',"+PY+");";
				try{
					statement7.executeUpdate(queryAF);
				}

				catch (SQLException eAF) {
					l.write("#"+i +" article has an error on AF and Department.");

					// TODO Auto-generated catch block
					eAF.printStackTrace();
					System.out.println(eAF);

				}
				queryC1 = "INSERT INTO bil496.departments (ArticleID,C1,PublicationYear) VALUES ("+i+",'"+tmpC1+"',"+PY+");";
				try{
					statement6.executeUpdate(queryC1);
				}

				catch (SQLException eC1) {
					l.write("#"+i +" article has an error on AF and Department.");

					// TODO Auto-generated catch block
					eC1.printStackTrace();
					System.out.println(eC1);

				}

				AF = AF.substring(AF.indexOf("<br>")+4);
			}
			//System.out.println(AF);
			String tmpC1 = C1;

			if(C1.contains(AF))
			{
				
				try{
					tmpC1 =tmpC1.substring(tmpC1.indexOf("]")+1,tmpC1.indexOf("<br>"));

				}
				catch (StringIndexOutOfBoundsException e)
				{
					tmpC1 =tmpC1.substring(tmpC1.indexOf("]")+1);

				}
				
			}
			
			else
			{
				
				tmpC1 = "***NULL***";
			}
			tmpC1 = tmpC1.trim();
			//System.out.println(tmpC1);
			
			//Authors will be parsed
			//for each author the address line should be found.
			queryAF = "INSERT INTO bil496.authors (ArticleID,Author,Address,PublicationYear) VALUES ("+i+",'"+AF+"','"+tmpC1+"',"+PY+");";
			try{
				statement7.executeUpdate(queryAF);
			}

			catch (SQLException eAF) {
				l.write("#"+i +" article has an error on AF and Department.");

				// TODO Auto-generated catch block
				eAF.printStackTrace();
				System.out.println(eAF);

			}
			queryC1 = "INSERT INTO bil496.departments (ArticleID,C1,PublicationYear) VALUES ("+i+",'"+tmpC1+"',"+PY+");";
			try{
				statement6.executeUpdate(queryC1);
			}

			catch (SQLException eC1) {
				l.write("#"+i +" article has an error on AF and Department.");

				// TODO Auto-generated catch block
				eC1.printStackTrace();
				System.out.println(eC1);

			}
			

		}
		
	}

	 */
	//AUTHORS AND DEPARTMENTS TABLE ENDS

	
	 public static String replaceAll(String a)
	 {
	 	a=a.replaceAll("'", "''");//Replace ' with ''
		return a;
	 }
}