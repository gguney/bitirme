//<Designed Developed By Gokhan Guney
//st081101028@etu.edu.tr
package tableToTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.commons.lang3.text.WordUtils;



//import org.apache.commons.lang3.text.WordUtils;


public class ParserMainToOthersNEW {


	final static Charset ENCODING = StandardCharsets.UTF_8;
	public static String newline = System.getProperty("line.separator");
	static boolean sameIns = false;
	static boolean sameInsTry = false;

	static long before, after;
	static long before2, after2;
	static double totalRunTime;
	static double percentageRunTime;
	static Logger2 logger2 = new Logger2();
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


	
	public static void main(String[] args) {


		try {
			
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			Statement statement = conn.createStatement();

			String query = "Select ArticleID from bil496.main order by ArticleID desc limit 1;";
			try {
				ResultSet rs = statement.executeQuery(query);
				while (rs.next()) {
					totalCount = rs.getInt("ArticleID");
					System.out.println("Total Record Count: " + totalCount);
					//l.write("Total Record Count: " + totalCount + newline);
				}
				rs.close();
			}
			catch (SQLException e1) {
				System.out.println("There is no database");
			}
			statement.close();
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		//l.write("System started..." + newline);
		before = System.currentTimeMillis();
		before2 = System.currentTimeMillis();

		double percentage = totalCount / 1000;
		
		 
		for (int i = 1; i <= 100000; i++) {
		//for (int i =200001;i <= totalCount; i++) {
			String queryOne = null; 
					queryOne = "select ArticleID,AF,TI,SO,LA,AB,C1,CR,PY,SC,UT from bil496.real where ArticleID = " + i + ";";
			String AF = null;
			String TI = null;
			String SO = null;
			String LA = null;
			String AB = null;
			String C1 = null;
			String CR = null;
			int PY = 0;
			String SC = null;
			String UT = null;
			//sameIns = false;
			//sameInsTry = false;

			try {
				Statement statementOne = conn.createStatement();
				ResultSet rs = statementOne.executeQuery(queryOne);
				while (rs.next()) {
					// System.out.println(i);
					AF = rs.getString("AF");
					TI = rs.getString("TI");
					SO = rs.getString("SO");
					LA = rs.getString("LA");
					AB = rs.getString("AB");
					C1 = rs.getString("C1");
					CR = rs.getString("CR");
					PY = rs.getInt("PY");
					SC = rs.getString("SC");
					UT = rs.getString("UT");

					
					//System.out.println(i);
					parseAll(i, AF, TI, SO, CR, C1, LA, AB, SC, PY);
					
					if (i%percentage == 0) {
						after2 = System.currentTimeMillis();
						percentageRunTime = (after2 - before2) / 60000.0;
						System.out.println((int) (i / percentage) + " / 1000 is done.");
						System.out.println("It Took: "+percentageRunTime+" mins.");
						System.out.println("Probably It Will Take: " +percentageRunTime*1000+" mins. to complete for all process.");
						before2 = System.currentTimeMillis();
						double currentMemory = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
						System.out.println(currentMemory);
					}
					if (i%100 == 0)
					{
						System.out.println("#"+i+" Article has parsed.");
					}

				}
				statementOne.close();
				rs.close();

			}
			catch (SQLException e1) {
				System.out.println(e1);
				e1.printStackTrace();

			}

		}

		after = System.currentTimeMillis();
		totalRunTime = (after - before) / 60000.0;
		//l.write("Total Run Time: " + totalRunTime + " mins." + newline);
		//l.write("System Stopped..." + newline);
		System.out.println("Total Run Time: " + totalRunTime + " mins."); 
		System.out.println("System Stopped...");
		logger2.close();
		//l.close();

		// Logging

	}

	public static void parseAll(int articleID, String Authors,String Title, String Subject, String References, String Institutions, String Languages, String Abstract,String Categories, int PY) {
		String tmpAuthors2 =null;
		tmpAuthors2 = Authors;
		int authorCount = (( tmpAuthors2.length() -tmpAuthors2.replaceAll("<br>", "").length())/4 )+ 1;

		if (authorCount >270 )
		{	
				System.out.println("ALERT ! "+authorCount +" "+ articleID);
				logger2.write(""+authorCount +" "+ articleID+", "+newline);

				try {
					Thread.sleep(500);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return;
		}

		else
		{


		// ------------------------------------------------------------------------------------
		// SUBJECT PARSING
		//
		int SID = 0;
		SID = getSubjectID(Subject);
		incCounters("subjects","subAnalysis","SID",SID,PY);
		//***************incSubject****************
		
		// We have 1 SID
		// ------------------------------------------------------------------------------------

	

	
		int authorsArray[] = new int[authorCount];
		
				
		// ------------------------------------------------------------------------------------
		// AUTHOR PARSING
		//

		String tmpAuthors = Authors;
		int counterForAuthorsArray = 0;

		if (Authors.equals("***NULL***")) {
			System.out.println("Author is null");
		}
		else if (!Authors.contains("<br>")) {
			int AUID = 0;
			int IID = 0;
			Authors = Authors.trim();
			IID = Integer.parseInt(parseInstitutions(Authors.trim(), Institutions,tmpAuthors ,false)[0]);
			incCounters("institutions","instAnalysis","IID",IID,PY);
			String AUFullAddress = parseInstitutions(Authors.trim(), Institutions, tmpAuthors ,false)[1];
			int CiID = Integer.parseInt(parseInstitutions(Authors.trim(), Institutions, tmpAuthors ,false)[2]);
			int CoID = Integer.parseInt(parseInstitutions(Authors.trim(), Institutions, tmpAuthors ,false)[3]);
			incCounters("cities","ciAnalysis","CiID",CiID,PY);
			incCounters("countries","coAnalysis","CoID",CoID,PY);
			AUID = getAuthorID(Authors, IID, AUFullAddress);
			incCounters("authors","auAnalysis","AUID",AUID,PY);			
			insert("INSERT INTO bil496.aas (AID,AUID,SID) VALUES (" + articleID + "," + AUID + "," + SID + ");");
			insert("INSERT INTO bil496.ais (AID,IID,SID) VALUES (" + articleID + "," + IID + "," + SID + ")");
			parseCategoryToAACandAIC(Categories, articleID, AUID, IID, PY);
		}
		else {
			while (Authors.contains("<br>")) {
				int IID2 = 0;
				int AUID2 = 0;
				String AUFullAddress2 = "ambiguous";
				IID2 = Integer.parseInt(parseInstitutions(Authors.substring(0, Authors.indexOf("<br>")).trim(), Institutions,tmpAuthors , true)[0]);
				incCounters("institutions","instAnalysis","IID",IID2,PY);
				AUFullAddress2 = parseInstitutions(Authors.substring(0, Authors.indexOf("<br>")).trim(), Institutions,tmpAuthors , true)[1];
				int CiID = Integer.parseInt(parseInstitutions(Authors.substring(0, Authors.indexOf("<br>")).trim(), Institutions, tmpAuthors ,true)[2]);
				int CoID = Integer.parseInt(parseInstitutions(Authors.substring(0, Authors.indexOf("<br>")).trim(), Institutions, tmpAuthors ,true)[3]);
				incCounters("cities","ciAnalysis","CiID",CiID,PY);
				incCounters("countries","coAnalysis","CoID",CoID,PY);
				AUID2 = getAuthorID(Authors.substring(0, Authors.indexOf("<br>")).trim(), IID2, AUFullAddress2);
				authorsArray[counterForAuthorsArray] = AUID2;
				counterForAuthorsArray++;
				incCounters("authors","auAnalysis","AUID",AUID2,PY);
				insert("INSERT INTO bil496.aas (AID,AUID,SID) VALUES (" + articleID + "," + AUID2 + "," + SID + ");");
				insert("INSERT INTO bil496.ais (AID,IID,SID) VALUES (" + articleID + "," + IID2 + "," + SID + ")");
				parseCategoryToAACandAIC(Categories, articleID, AUID2, IID2,PY);
				Authors = Authors.substring(Authors.indexOf("<br>") + 4);
			}
			int IID3 = 0;
			int AUID3 = 0;
			String AUFullAddress3 = "ambiguous";
			IID3 = Integer.parseInt(parseInstitutions(Authors.trim(), Institutions,tmpAuthors , true)[0]);
			incCounters("institutions","instAnalysis","IID",IID3,PY);
			AUFullAddress3 = parseInstitutions(Authors.trim(), Institutions, tmpAuthors ,true)[1];
			int CiID = Integer.parseInt(parseInstitutions(Authors.trim(), Institutions,tmpAuthors , true)[2]);
			int CoID = Integer.parseInt(parseInstitutions(Authors.trim(), Institutions,tmpAuthors , true)[3]);
			incCounters("cities","ciAnalysis","CiID",CiID,PY);
			incCounters("countries","coAnalysis","CoID",CoID,PY);
			AUID3 = getAuthorID(Authors.trim(), IID3, AUFullAddress3);
			authorsArray[counterForAuthorsArray] = AUID3;
			incCounters("authors","auAnalysis","AUID",AUID3,PY);
			insert("INSERT INTO bil496.aas (AID,AUID,SID) VALUES (" + articleID + "," + AUID3 + "," + SID + ");");
			insert("INSERT INTO bil496.ais (AID,IID,SID) VALUES (" + articleID + "," + IID3 + "," + SID + ")");
			parseCategoryToAACandAIC(Categories, articleID, AUID3, IID3,PY);
		}
		// ------------------------------------------------------------------------------------

		// ------------------------------------------------------------------------------------
		// REFERENCE PARSING
		//
		if (References.equals("***NULL***")) {
			// System.out.println("null");
		}
		else if (!References.contains("<br>")) {
			References = References.trim();
			insert("INSERT INTO bil496.asr (AID,SID,RID) VALUES (" + articleID + "," + SID + "," + getReferenceID(References) + ");");
		}
		else {
			while (References.contains("<br>")) {
				insert("INSERT INTO bil496.asr (AID,SID,RID) VALUES (" + articleID + "," + SID + "," + getReferenceID(References.substring(0, References.indexOf("<br>")).trim())+ ");");
				References = References.substring(References.indexOf("<br>") + 4);
			}
			insert("INSERT INTO bil496.asr (AID,SID,RID) VALUES (" + articleID + "," + SID + "," +  getReferenceID(References.trim()) + ");");

		}
		// ------------------------------------------------------------------------------------
		if (authorCount !=0 ) // Co author parsing
		{

			for(int i=0; i<authorCount;i++){
				for(int j=0;j<authorCount;j++)
				{
					if ( i!=j)
					{
						insert("INSERT INTO bil496.auCoAu (AUID,CoAUID) VALUES ("+authorsArray[i]+","+authorsArray[j]+");");

					}
				}
				
			}
		}
	
		parseCategoryForAnalysis(Categories,PY);
		insert("INSERT INTO bil496.titles (AID,TI,PY) VALUES ("+articleID+",'"+Title+"',"+PY+")");
		insert("INSERT INTO bil496.abstracts (AID,AB) VALUES ("+articleID+",'"+Abstract+"')");
		}

	}

	private static void parseCategoryForAnalysis(String Categories, int PY) {
		int CID = 0;
		if (!Categories.contains(";")) {
			Categories = Categories.trim();
			CID = getCategoryID(Categories);
			incCounters("categories","catAnalysis","CID",CID,PY);
		}
		else {
			while (Categories.contains(";")) {
				int CID2 = 0;
				CID2 = getCategoryID(Categories.substring(0, Categories.indexOf(";")).trim());
				incCounters("categories","catAnalysis","CID",CID2,PY);

				Categories = Categories.substring(Categories.indexOf(";") + 1);
			}
			int CID3 = 0;
			CID3 = getCategoryID(Categories.trim());
			incCounters("categories","catAnalysis","CID",CID3,PY);


		}

		
	}

	private static String[] parseInstitutions(String Author, String Institutions,String Authors, boolean multi) {
		
		
		String result[] = new String[4];// IID,AUFullAddress,CiID,CoID
		
		String fullAddress = null;
				fullAddress = "ambiguous";
		String tmpIns = null;
		tmpIns = Institutions;
		String tmpIns2 = null;
		tmpIns2 = Institutions;
		String tmpIns3 = null;
		tmpIns3 = Institutions;
		String univ = "ambiguous";
		String city = "ambiguous";
		String country = "ambiguous";
		String authors = null;
				authors = Authors;
				if (Institutions.equals("***NULL***"))
				{
					
				}
				else if (multi) {
			if (Institutions.contains(Author)) {
				try {
					fullAddress = tmpIns.substring(tmpIns.indexOf(Author));
					fullAddress = fullAddress.substring(fullAddress.indexOf("]") + 1, fullAddress.indexOf("<br>")).trim();
				}
				catch (StringIndexOutOfBoundsException e) {
					tmpIns = Institutions;
					fullAddress = tmpIns.substring(tmpIns.indexOf(Author));
					fullAddress = fullAddress.substring(fullAddress.indexOf("]") + 1).trim();
				}
			}
			else {//Institutions dont contain author name
				fullAddress = "ambiguous";
				
				int a= tmpIns.length() - tmpIns.replaceAll("<br>","").length();
				int b= Authors.length() - Authors.replaceAll("<br>","").length();
				tmpIns = Institutions;
				if (a==b)//If Institutions seperated by <br> and every author has address 
				{
					authors = authors.substring(0,authors.indexOf(Author));
					int count = authors.length()- authors.replace("<br>", "").length();
					count = count/4;
			
						for(int i = 0;i<count;i++)
						{
							tmpIns = tmpIns.substring(tmpIns.indexOf("<br>")+4);
						}
						try{
							fullAddress = tmpIns.substring(0,tmpIns.indexOf("<br>")).trim();

						}
						catch(StringIndexOutOfBoundsException e)
						{
							fullAddress = tmpIns.trim();
						}
				
				}
				else if (a!=b)
				{
					if(!sameInsTry)
					{
						while (tmpIns2.contains("<br>") && !sameInsTry)
						{
							String ins =null;
							ins = tmpIns2.substring(0,tmpIns2.indexOf("<br>"));
							ins = ins.substring(0,ins.indexOf(",")).trim();
							tmpIns2= tmpIns2.substring(tmpIns2.indexOf("<br>")+4);
							String ins2 = null;
							try{
								ins2 = tmpIns2.substring(0,tmpIns2.indexOf("<br>"));
							}
							catch(StringIndexOutOfBoundsException e)
							{
								ins2 = tmpIns2;
							}
							ins2 = ins2.substring(0,ins2.indexOf(",")).trim();

							if (ins.equals(ins2))
							{
								sameIns = true;
							}
							else
							{
								sameIns = false;
								sameInsTry=true;
								break;
							}
				
						}
						if (sameIns)
						{	
							System.out.println(tmpIns3);
							try
							{
								fullAddress = tmpIns3.substring(0,tmpIns3.indexOf("<br>")).trim();

							}
							catch(StringIndexOutOfBoundsException e)
							{
								fullAddress = tmpIns3.trim();

							}
						}
						else
						{		
							
								fullAddress = "ambiguous";
						}
					}
					}

			}
		}
		else {//if there is only 1 author
			if (Institutions.contains("<br>"))
			{
				fullAddress = Institutions.substring(0,Institutions.indexOf("<br>")).trim();
			}
			else if (Institutions.contains("<br"))
			{
				fullAddress = Institutions.substring(0,Institutions.indexOf("<br")).trim();

			}
			fullAddress = Institutions;
		}

		if (!fullAddress.equals("ambiguous") ) {
			if(!fullAddress.equals("***NULL***"))
			{
				fullAddress = makeFirstLettersUpperCase(fullAddress);
				univ = fullAddress.substring(0, fullAddress.indexOf(",")).trim();
				country = fullAddress.substring(fullAddress.lastIndexOf(",") + 1).trim();
				country = country.replace(".", "");
				if (country.contains("USA"))
					country = "USA";
				city = fullAddress.substring(0, fullAddress.lastIndexOf(","));
				city = city.substring(city.lastIndexOf(",") + 1);
				if (city.contains("Tr-")) {
					city = city.replace("Tr-", "");
				}
				city = city.replaceAll("\\P{L}", "");
				city = city.trim();

			}
		}
		else {
			
			
		}
		// AuthorAddress will be added and turn IID plus full Address so Author can be added

		int CiID = 0;
		int CoID = 0;
		CiID = getCityID(city);
		CoID = getCountryID(country);
		result[2] = CiID + "";
		result[3] = CoID + "";
		result[0] = getInstitutionID(univ, CiID, CoID);
		result[1] = fullAddress;

		return result;

	}



	private static int getCountryID(String country) {
		int CoID = check("SELECT CoID,CoName FROM bil496.countries where CoName='" + country + "';", "CoID");

		if (CoID == 0) {
			insert("INSERT INTO bil496.countries (CoID,CoName,TCount) VALUES (" + "null" + ",'" + country + "',0);");
			CoID = getLast("countries", "CoID");
		}

		return CoID;

	}

	private static int getCityID(String city) {
		int CiID = check("SELECT CiID,CiName FROM bil496.cities where CiName='" + city + "';", "CiID");

		if (CiID == 0) {
			insert("INSERT INTO bil496.cities (CiID,CiName,TCount) VALUES (" + "null" + ",'" + city + "',0);");
			CiID = getLast("cities", "CiID");
		}

		return CiID;
	}

	private static void parseCategoryToAACandAIC(String Categories, int articleID, int AUID, int IID,int PY) {
		// ------------------------------------------------------------------------------------
		// CATEGORY PARSING
		//
		int CID = 0;
		if (!Categories.contains(";")) {
			Categories = Categories.trim();
			CID = getCategoryID(Categories);

				insert("INSERT INTO bil496.aac (AID,AUID,CID) VALUES (" + articleID + "," + AUID + "," + CID + ");");
				insert("INSERT INTO bil496.aic (AID,IID,CID) VALUES (" + articleID + "," + IID + "," + CID + ");");

		}
		else {
			while (Categories.contains(";")) {
				CID = 0;
				CID = getCategoryID(Categories.substring(0, Categories.indexOf(";")).trim());


					insert("INSERT INTO bil496.aac (AID,AUID,CID) VALUES (" + articleID + "," + AUID + "," + CID + ");");
					insert("INSERT INTO bil496.aic (AID,IID,CID) VALUES (" + articleID + "," + IID + "," + CID + ");");

				Categories = Categories.substring(Categories.indexOf(";") + 1);
			}
			CID = 0;
			CID = getCategoryID(Categories.trim());
				insert("INSERT INTO bil496.aac (AID,AUID,CID) VALUES (" + articleID + "," + AUID + "," + CID + ");");
				insert("INSERT INTO bil496.aic (AID,IID,CID) VALUES (" + articleID + "," + IID + "," + CID + ");");

		}

		// We have multiple CID
		// ------------------------------------------------------------------------------------
	}

	

	private static String getInstitutionID(String univ, int CiID, int CoID) {

		int IID = check("SELECT IID,IName FROM bil496.institutions where IName='" + univ + "';", "IID");
		if (IID == 0) {
			insert("INSERT INTO bil496.institutions (IID,IName,ICityID,ICountryID,TCount,IMore1,IMore2) VALUES (" + "null" + ",'" + univ + "'," + CiID + "," + CoID + ",0,"+ "null" +","+ "null" +");");
			IID = getLast("institutions", "IID");
		}
		return IID + "";
	}

	private static int getAuthorID(String Author, int IID, String AUFullAddress) {

		int AUID = check("SELECT AUID,AUName FROM bil496.authors where AUName='" + Author + "';", "AUID");

		if (AUID == 0) {
			insert("INSERT INTO bil496.authors (AUID,AUName,AUAddressID,AUFullAddress,TCount,AUMore1,AUMore2) VALUES (" + "null" + ",'" + Author + "'," + IID + ",'" + AUFullAddress + "',0,"+ "null" +","+ "null" +");");
			AUID = getLast("authors", "AUID");
		}
		return AUID;
	}

	private static int getReferenceID(String Reference) {
		//int RID = 0 ;
		//int RID = check("SELECT RID,RName FROM bil496.references where RName='" + Reference + "';", "RID");
		//if (RID == 0) {

		//}
		String DOI = "***NULL***";
		String RName = Reference.trim();
		//System.out.println(Reference);

		if (RName.contains("DOI")) {
			DOI = RName.substring(RName.indexOf("DOI") + 3).trim();
		}
		insert("INSERT INTO bil496.references (RID,RName,RDOI) VALUES (" + "null" + ",'" + RName + "','" + DOI + "');");
		
		return getLast("references", "RID");

	}

	private static int getSubjectID(String Subject) {
		int SID = check("SELECT SID,SName FROM bil496.subjects where SName='" + Subject + "';", "SID");

		if (SID == 0) {
			insert("INSERT INTO bil496.subjects (SID,SName,TCount) VALUES (" + "null" + ",'" + Subject + "',0);");
			SID = getLast("subjects", "SID");
		}
		return SID;
	}

	private static int getCategoryID(String Category) {
		int CID = check("SELECT CID,CName FROM bil496.categories where CName='" + Category + "';", "CID");

		if (CID == 0) {
			insert("INSERT INTO bil496.categories (CID,CName,TCount) VALUES (" + "null" + ",'" + Category + "',0);");
			CID = getLast("categories", "CID");
		}
		return CID;
	}

	private static void incCounters(String mainTableName,String analysisTableName,String columnName,int ID, int PY) {
		insert ("UPDATE bil496."+mainTableName+" SET TCount = TCount + 1 WHERE "+columnName+" = "+ID+";");

		if (check("SELECT "+columnName+" from bil496."+analysisTableName+" WHERE "+columnName+"="+ID+" and PY="+PY+";",columnName) == 0)
		{
			insert ("INSERT INTO bil496."+analysisTableName+" ("+columnName+",PY,TCount) VALUES ("+ID+","+PY+",0);");
		}

		insert ("UPDATE bil496."+analysisTableName+" SET TCount = TCount + 1 WHERE "+columnName+" = "+ID+" and PY="+PY+";");

	}


	private static int check(String query, String IDColumn) {
		int ID = 0;
		try {
			Statement checkStatement = conn.createStatement ();
			ResultSet result = checkStatement.executeQuery(query);
			while (result.next()) {
				if (result.getInt(IDColumn) != 0) {
					ID = result.getInt(IDColumn);
					break;
				}

			}
			checkStatement.close();
			result.close();
			
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return ID;
	}

	private static void insert(String query) {
		Statement insertStatement = null;

		try {
			insertStatement = conn.createStatement();
			insertStatement.executeUpdate(query);
			insertStatement.close();
		}
		catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			//e.printStackTrace();
			try {
				insertStatement.close();
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		}
		catch (SQLException e) {
				//e.printStackTrace();
			try {
				insertStatement.close();
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
	}

	private static int getLast(String table, String IDColumn) {
		int ID = 0;
		try {
			Statement getLastStatement = conn.createStatement();
			String query = "Select "+IDColumn+" from bil496."+table+" ORDER BY "+IDColumn+" DESC limit 1;";
		//	String query = "Select MAX(" + IDColumn + ") from bil496." + table + ";";
			ResultSet result2 = getLastStatement.executeQuery(query);
			while (result2.next()) {
				ID = result2.getInt(IDColumn);
			}
			getLastStatement.close();
			result2.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ID;
	}
	private static String makeFirstLettersUpperCase(String fullAddress) {
		if (fullAddress.contains(","))
		{
			if (!fullAddress.contains(", "))
			{
				fullAddress = fullAddress.replaceAll(",",", ");

			}
		}
		if (fullAddress.contains("&"))
		{
			if (!fullAddress.contains(" & "))
			{
				fullAddress = fullAddress.replaceAll("&"," & ");
			}
		}
		fullAddress = fullAddress.toLowerCase();
		Character c = new Character(' ');
		fullAddress =  WordUtils.capitalize(fullAddress, c);

		if (fullAddress.contains("Univ")) {
			fullAddress = fullAddress.replace("Univ", "University");

		}
		if (fullAddress.contains("Hosp")) {
			fullAddress = fullAddress.replace("Hosp", "Hospital");

		}
		if (fullAddress.contains("Usa")) {
			fullAddress = fullAddress.replace("Usa", "USA");
		}
		if (fullAddress.contains("Istanbull")) {
			fullAddress = fullAddress.replace("Istanbull", "Istanbul");
		}
		if (fullAddress.contains("Istanbuly")) {
			fullAddress = fullAddress.replace("Istanbuly", "Istanbul");
		}
		if (fullAddress.contains("SarayAnkara")) {
			fullAddress = fullAddress.replace("SarayAnkara", "Ankara");
		}
		
		if (fullAddress.contains("BalgatAnkara")) {
			fullAddress = fullAddress.replace("BalgatAnkara", "Ankara");
		}
		if (fullAddress.contains("SarayAnkara")) {
			fullAddress = fullAddress.replace("SarayAnkara", "Ankara");
		}
		if (fullAddress.contains("CebeciAnkara")) {
			fullAddress = fullAddress.replace("CebeciAnkara", "Ankara");
		}
		if (fullAddress.contains("YenimahalleAnkara")) {
			fullAddress = fullAddress.replace("YenimahalleAnkara", "Ankara");
		}
		if (fullAddress.contains("HipodromAnkara")) {
			fullAddress = fullAddress.replace("HipodromAnkara", "Ankara");
		}
		if (fullAddress.contains("UlusAnkara")) {
			fullAddress = fullAddress.replace("UlusAnkara", "Ankara");
		}
		if (fullAddress.contains("SogutozuAnkara")) {
			fullAddress = fullAddress.replace("SogutozuAnkara", "Ankara");
		}
		if (fullAddress.contains("BahcelievlerAnkara")) {
			fullAddress = fullAddress.replace("BahcelievlerAnkara", "Ankara");
		}
		if (fullAddress.contains("IncekAnkara")) {
			fullAddress = fullAddress.replace("IncekAnkara", "Ankara");
		}
		
		if (fullAddress.contains("TuzlaIstanbul")) {
			fullAddress = fullAddress.replace("TuzlaIstanbul", "Istanbul");
		}
		if (fullAddress.contains("UskudarIstanbul")) {
			fullAddress = fullAddress.replace("UskudarIstanbul", "Istanbul");
		}
		if (fullAddress.contains("BuyukcekmeceIstanbul")) {
			fullAddress = fullAddress.replace("BuyukcekmeceIstanbul", "Istanbul");
		}
		if (fullAddress.contains("KayisdagiIstanbul")) {
			fullAddress = fullAddress.replace("KayisdagiIstanbul", "Istanbul");
		}
		if (fullAddress.contains("LaleliIstanbul")) {
			fullAddress = fullAddress.replace("LaleliIstanbul", "Istanbul");
		}
		if (fullAddress.contains("KartalIstanbul")) {
			fullAddress = fullAddress.replace("KartalIstanbul", "Istanbul");
		}
		if (fullAddress.contains("MaslakIstanbul")) {
			fullAddress = fullAddress.replace("MaslakIstanbul", "Istanbul");
		}
		if (fullAddress.contains("MackaIstanbul")) {
			fullAddress = fullAddress.replace("MackaIstanbul", "Istanbul");
		}
		
		if (fullAddress.contains("Aegan University"))
		{
			fullAddress = fullAddress.replace("Aegan", "Aegean");
		}
		else if(fullAddress.contains("Akdinez University"))
		{
			fullAddress = fullAddress.replace("Akdeniz", "University");
		}
		else if(fullAddress.contains("Ankara University Hos"))
		{
			String name = "Ankara University Hospital";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;
		}
		else if(fullAddress.contains("Ankara University"))
		{
			String name = "Ankara University";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;
		}
		else if(fullAddress.contains("Ankara Ataturk"))
		{
			String name = "Ankara Ataturk Training And Research Hospital";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;	
		}
		
		else if(fullAddress.contains("Ankara On"))
		{
			String name = "Ankara Oncology Hospital";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;	
		}
		else if(fullAddress.contains("Ankara Numune"))
		{
			String name = "Ankara Numune Training & Research Hospital";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;	
		}
		
		else if(fullAddress.contains("Ankara Diskapi"))
		{
			String name = "Ankara Diskapi Yildirim Beyazit Training and Research Hospital";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;	
		}
		else if(fullAddress.contains("Bilkant University"))
		{
			fullAddress = fullAddress.replace("Bilkant University", "Bilkent University");
		}
		else if(fullAddress.contains("Bilkeut University"))
		{
			fullAddress = fullAddress.replace("Bilkeut University", "Bilkent University");
		}
		else if(fullAddress.contains("Black Sea University"))
		{
			fullAddress = fullAddress.replace("Black Sea University", "Black Sea Technical University");
		}
		else if(fullAddress.contains("Blacksea Technology University"))
		{
			fullAddress = fullAddress.replace("Blacksea Technology University", "Black Sea Technical University");
		}
		else if(fullAddress.contains("Black Sea Tech University"))
		{
			fullAddress = fullAddress.replace("Black Sea TechUniversity", "Black Sea Technical University");
		}
		else if(fullAddress.contains("Bahkesir University"))
		{
			fullAddress = fullAddress.replace("Bahkesir University", "Balikesir University");
		}

		else if(fullAddress.contains("Cumhuriyet University Sci"))
		{
			fullAddress = fullAddress.replace("Cumhuriyet University Sci", "Cumhuriyet University");
		}
		else if(fullAddress.contains("Dicle University Diyarbakir"))
		{
			fullAddress = fullAddress.replace("Dicle University Diyarbakir", "Dicle University");
		}
		else if(fullAddress.contains("Dokuz Eylu University"))
		{
			fullAddress = fullAddress.replace("Dokuz Eylu University", "Dokuz Eylul University");
		}
		else if(fullAddress.contains("Diskapi Yildirim Beyaz Res & Educ Hospital"))
		{
			fullAddress = fullAddress.replace("Diskapi Yildirim Beyaz Res & Educ Hospital", "Diskapi Yildirim Beyaziz Education & Research Hospital");
		}
		else if(fullAddress.contains("Diskapi Yildirim Beyazit Educ & Res Hospital"))
		{
			fullAddress = fullAddress.replace("Diskapi Yildirim Beyazit Educ & Res Hospital", "Diskapi Yildirim Beyaziz Education & Research Hospital");
		}
		else if(fullAddress.contains("Eastern Med University"))
		{
			fullAddress = fullAddress.replace("Eastern Med University", "Eastern Mediterranean University");
		}
		else if(fullAddress.contains("E Mediterranean University"))
		{
			fullAddress = fullAddress.replace("E Mediterranean University", "Eastern Mediterranean University");
		}
		else if(fullAddress.contains("Ercives University"))
		{
			fullAddress = fullAddress.replace("Ercives University", "Erciyes University");
		}

		else if(fullAddress.contains("Gaziosmapasa University"))
		{
			fullAddress = fullAddress.replace("Gaziosmapasa University", "Gaziosmanpasa University");
		}
		
		else if(fullAddress.contains("Istanbul Technology University"))
		{
			fullAddress = fullAddress.replace("Istanbul Technology University", "Istanbul Technical University");
		}
		else if(fullAddress.contains("Istabul Technology University"))
		{
			fullAddress = fullAddress.replace("Istabul Technology University", "Istanbul Technical University");
		}
		
		else if(fullAddress.contains("Istanbul Tec"))
		{
			String name = "Istanbul Technical University";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;				}
		
		else if(fullAddress.contains("Istanbul Tek"))
		{
			String name = "Istanbul Technical University";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;		
		}

		else if(fullAddress.contains("Istanbul University Hos"))
		{
			String name = "Istanbul University Hospital";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;		
		}
		else if(fullAddress.contains("Istanbul Bilgi Uni"))
		{
			String name = "Istanbul Bilgi University";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;		
		}
		else if(fullAddress.contains("Istanbul Bilim University"))
		{
			String name = "Istanbul Bilim University";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;		
		}
		else if(fullAddress.contains("University Istanbul"))
		{
			String name = "Istanbul University";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;		
		}
		else if(fullAddress.contains("Karadeniz Technology University"))
		{
			fullAddress = fullAddress.replace("Karadeniz Technology University", "Black Sea Technical University");
		}
		
		else if(fullAddress.contains("Karadeniz Tekn University"))
		{
			fullAddress = fullAddress.replace("Karadeniz Tekn University", "Black Sea Technical University");
		}
		
		else if(fullAddress.contains("Karadeniz University"))
		{
			fullAddress = fullAddress.replace("Karadeniz University", "Black Sea Technical University");
		}
		else if(fullAddress.contains("Hacettepe Hosp"))
		{
			String name = "Hacettepe Hospital";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;
			
		}		
		else if(fullAddress.contains("Hacettepe"))
		{
			String name = "Hacettepe University";
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;
			
		}
		
		else if(
				fullAddress.contains("Metu") 
			|| fullAddress.contains("Middle E Tech University")
			|| fullAddress.contains("Middle East Tech University")
			|| fullAddress.contains("Middle Tech University")
			|| fullAddress.contains("Middle & Tech University")
			|| fullAddress.contains("Middle E Inst")
			|| fullAddress.contains("Middle E Technol University")
			|| fullAddress.contains("Middle East Techn University")
			|| fullAddress.contains("Middle East Technical University Subaqua Soc")
			|| fullAddress.contains("Middle East Technical University Elect & Elect Engn Dept")
			|| fullAddress.contains("Middle East Technical University Ncc")
			|| fullAddress.contains("Middle East Technical University Comp Engn")
			|| fullAddress.contains("Middle East Technical University Mech Engn")
			|| fullAddress.contains("Middle East Technical University Civil Engn Odtu")
			|| fullAddress.contains("Middle East Technical University Biomat Biotechnol Res Unit")
			|| fullAddress.contains("Middle East Technical University Psikoloji Bolumu")
			|| fullAddress.contains("Middle East University")
			|| fullAddress.contains("Middle Eastern Tech University")
			|| fullAddress.contains("Orta Doga Tekn University")
			|| fullAddress.contains("Orta Dogu Tek University")
			|| fullAddress.contains("Orta Dogu Tekn Psikol Bl")
			|| fullAddress.contains("Orta Dogu Tekn University")
			|| fullAddress.contains("Orta Dogu Tekn Uni")
			|| fullAddress.contains("Orta Dogu Tekn University Psikoloji Bolumu")
			|| fullAddress.contains("Orta Dogu Tekn University Ankara")
			|| fullAddress.contains("Orta Dogu Teknik University")
			|| fullAddress.contains("Middle East Technical University Ankara")
			|| fullAddress.contains("Mid East Tech University")

			
				)
		{
			String name = "Middle East Technical University";
			/*
			fullAddress = fullAddress.replace("Metu", name);
			fullAddress = fullAddress.replace("Middle East Technical University Ankara", name);
			fullAddress = fullAddress.replace("Middle E Tech University", name);
			fullAddress = fullAddress.replace("Middle East Tech University", name);
			fullAddress = fullAddress.replace("Middle Tech University", name);
			fullAddress = fullAddress.replace("Middle & Tech University", name);
			fullAddress = fullAddress.replace("Middle E Inst", name);
			fullAddress = fullAddress.replace("Middle E Technol University", name);
			fullAddress = fullAddress.replace("Middle East Techn University", name);
			fullAddress = fullAddress.replace("Middle East Technical University Subaqua Soc", name);
			fullAddress = fullAddress.replace("Middle East Technical University Elect & Elect Engn Dept", name);
			fullAddress = fullAddress.replace("Middle East Technical University Ncc", name);
			fullAddress = fullAddress.replace("Middle East Technical University Comp Engn", name);
			fullAddress = fullAddress.replace("Middle East Technical University Mech Engn", name);
			fullAddress = fullAddress.replace("Middle East Technical University Civil Engn Odtu", name);
			fullAddress = fullAddress.replace("Middle East Technical University Psikoloji Bolumu", name);
			fullAddress = fullAddress.replace("Middle East Technical University Biomat Biotechnol Res Unit", name);
			fullAddress = fullAddress.replace("Middle East University", name);
			fullAddress = fullAddress.replace("Middle Eastern Tech University", name);
			fullAddress = fullAddress.replace("Orta Doga Tekn University", name);
			fullAddress = fullAddress.replace("Orta Dogu Tek University", name);
			fullAddress = fullAddress.replace("Orta Dogu Tekn Psikol Bl", name);
			fullAddress = fullAddress.replace("Orta Dogu Tekn University", name);
			fullAddress = fullAddress.replace("Orta Dogu Tekn University Psikoloji Bolumu", name);
			fullAddress = fullAddress.replace("Orta Dogu Tekn University Ankara", name);
			fullAddress = fullAddress.replace("Orta Dogu Teknik University", name);
*/
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;
			
			
			

		}
		
		else if(
				fullAddress.contains("Tobb University Econ & Tech")
				|| fullAddress.contains("Tobb Econ & Tech University")
				|| fullAddress.contains("Tobb Ekon & Teknol University")
				|| fullAddress.contains("Tobb Ekon Teknol University")
				|| fullAddress.contains("Tobb Ekon Technol University")
				|| fullAddress.contains("Tobb Ekon Tecknol University")
				|| fullAddress.contains("Tobb Econ & Technol University")
				|| fullAddress.contains("Tobb Econn & Technol University")
				|| fullAddress.contains("Tobb Ekon & Teknol University")
				|| fullAddress.contains("Tobb Ekonomi & Teknol University")
				|| fullAddress.contains("Tobb Ekonomi & Teknoloji University")
				|| fullAddress.contains("Tobb Ekonomi Teknol University")
				|| fullAddress.contains("Tobb Elect & Teknol University")
				|| fullAddress.contains("Tobb Etu")
				|| fullAddress.contains("Tobb Etu Econ & Technol University")
				|| fullAddress.contains("Tobb Etu Uluslararasi Iliskiler Bolumu Ogretim Uy")
				|| fullAddress.contains("Tobb Etu University Econ & Technol")
				|| fullAddress.contains("Tobb University")
				|| fullAddress.contains("Tobb Econ")
				|| fullAddress.contains("Tobb Ekon")
				|| fullAddress.contains("Tobb Ekon & Teknoloji University")
				|| fullAddress.contains("Tobb Ind Econ & Technol University Hospital")

				)
		{
			String name = "TOBB Economy & Technology University";
			/*
			fullAddress = fullAddress.replace("Tobb University Econ & Tech", name);
			fullAddress = fullAddress.replace("Tobb Econ & Tech University", name);
			fullAddress = fullAddress.replace("Tobb Ekon Teknol University", name);
			fullAddress = fullAddress.replace("Tobb Ekon Technol University", name);
			fullAddress = fullAddress.replace("Tobb Econ & Technol University", name);
			fullAddress = fullAddress.replace("TOBB Economy & Technology Universitynol", name);
			fullAddress = fullAddress.replace("Tobb Econn & Technol University", name);
			fullAddress = fullAddress.replace("Tobb Ekon & Teknol University", name);
			fullAddress = fullAddress.replace("Tobb Ekonomi & Teknol University", name);
			fullAddress = fullAddress.replace("Tobb Ekonomi & Teknoloji University", name);
			fullAddress = fullAddress.replace("Tobb Ekonomi Teknol University", name);
			fullAddress = fullAddress.replace("Tobb Ekon Tecknol University", name);
			fullAddress = fullAddress.replace("Tobb Elect & Teknol University", name);
			fullAddress = fullAddress.replace("Tobb Etu", name);
			fullAddress = fullAddress.replace("Tobb Etu Econ & Technol University", name);
			fullAddress = fullAddress.replace("Tobb Etu Uluslararasi Iliskiler Bolumu Ogretim Uy", name);
			fullAddress = fullAddress.replace("Tobb Etu University Econ & Technol", name);
			fullAddress = fullAddress.replace("Tobb University", name);
			*/
			
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;

		}
		else if(fullAddress.contains("Osman Gazi University")
				)
		{
			String name = "Osmangazi University";
			fullAddress = fullAddress.replace("Osman Gazi University", name);
		}
		
		else if(fullAddress.contains("Yuzuncu Yil Unic")
				||fullAddress.contains("Yuzuncu Yul University")
				)
		{
			String name = "Yuzuncu Yil University";
			fullAddress = fullAddress.replace("Yuzuncu Yil Unic", name);
			fullAddress = fullAddress.replace("Yuzuncu Yul University", name);

		}
		
		else if(fullAddress.contains("University Gaziantep")
				)
		{
			String name = "Gaziantep University";
			fullAddress = fullAddress.replace("University Gaziantep", name);
		}
		else if(fullAddress.contains("SogutozuAnkara")
				)
		{
			String name = "Ankara";
			fullAddress = fullAddress.replace("SogutozuAnkara", name);
		}
		

		else if(
				fullAddress.contains("Yyldyz Tech University")
				|| fullAddress.contains("Yildiz Tekn University")
				|| fullAddress.contains("Yildiz University")
				|| fullAddress.contains("Yildiz Tekn Evrenkenti")
				|| fullAddress.contains("Yildiz Tech University")				
				)
		{
			String name = "Yildiz Technical University";
			fullAddress = fullAddress.replace("Yyldyz Technology University", name);
			fullAddress = fullAddress.replace("Yildiz Tekn University", name);
			fullAddress = fullAddress.replace("Yildiz University", name);
			fullAddress = fullAddress.replace("Yildiz Tekn Evrenkenti", name);
			fullAddress = fullAddress.replace("Yildiz Tech University", name);
			fullAddress = fullAddress.substring(fullAddress.indexOf(","));
			fullAddress = name + fullAddress;
		}		
		
		else if(fullAddress.contains("Cumburiyet University"))
		{
			fullAddress = fullAddress.replace("Cumburiyet University", "Cumhuriyet University");
		}	
		return fullAddress;
	}

}