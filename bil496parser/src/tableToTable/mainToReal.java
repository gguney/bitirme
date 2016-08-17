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

public class mainToReal {

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
	static String query=null;

	
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
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();

		}
		before = System.currentTimeMillis();
		double percentage = totalCount / 100;
		for (int i = 1; i <= totalCount; i++) {

			query = "select ArticleID,AF,TI,SO,LA,AB,C1,CR,PY,SC,UT from bil496.main where ArticleID = "+i+";";
			try {
				ResultSet rs = statement.executeQuery(query);
				while (rs.next()) 
				{
			
					String AF = rs.getString("AF").replace("'", "^").replaceAll("\\s+", " ");//1toMany
					String TI = rs.getString("TI").replace("'", "^").replaceAll("\\s+", " ");;//1to1
					String SO = rs.getString("SO").replace("'", "^").replaceAll("\\s+", " ");;//1to1
					String LA = rs.getString("LA");//1to1
					String AB = rs.getString("AB").replace("'", "^").replaceAll("\\s+", " ");;//1to1
					String C1 = rs.getString("C1").replace("'", "^").replaceAll("\\s+", " ");;//1toMany
					String CR = rs.getString("CR").replace("'", "^").replaceAll("\\s+", " ");;//1toMany
					int PY = rs.getInt("PY");
					String SC = rs.getString("SC").replace("'", "^").replaceAll("\\s+", " ");;//1toMany
					String UT = rs.getString("UT").replace("'", "^");//1to1
					
					
    	            String query2 = "INSERT INTO bil496.real "
    	                    + "(`ArticleID`,`AF`,`TI`,`SO`,`LA`,`AB`,`C1`,`CR`,`PY`,`SC`,`UT`) "
    	                    + "VALUES "
    	                    + "("
    	                    + i+","
    	                    +"\""+ AF +"\","
    	                    +"\""+ TI +"\","
    	                    +"\""+ SO +"\","
    	                    +"\""+ LA +"\","
    	                    +"\""+ AB +"\","
    	                    +"\""+ C1 +"\","
    	                    +"\""+ CR +"\","
    	                    +"\""+ PY +"\","
    	                    +"\""+ SC +"\","
    	                    +"\""+ UT +"\""
    	                    +");";
					try {
						statement2.executeUpdate(query2);

					}
					catch (SQLException e1) {
						System.out.println(e1);
						// e1.printStackTrace();

					}
					
					
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
		System.out.println("Total Run Time: "+ totalRunTime + " mins.");
		System.out.println("System Stopped...");


	}

}