//<Designed Developed By Gokhan Guney
//st081101028@etu.edu.tr
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ParserAll {

static String FILE_NAME = "E:\\Downloads\\Bitirme\\Datas\\Data";
static ParserNew p = new ParserNew();

public static int recordCount=0;
public static int suc=0,err=0;

	public static void main(String[] args) {
		

	    File file = new File(Logger.LOG_FILE_NAME);
	    file.delete();
	    Connection conn = null;
	    String url = "jdbc:mysql://localhost:3306/";
	    String dbName = "bil496";
	    String driver = "com.mysql.jdbc.Driver";
	    String userName = "root";
	    String password = ""; 
	    Statement komut = null;
	    String query;
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
		query = "drop table `bil496`.`main`;";
		System.out.println("Table 'main' dropped.");

        try {
			komut.executeUpdate(query);
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();

		}	
        
		query = "CREATE  TABLE `bil496`.`Main` (`ArticleID` INT NOT NULL AUTO_INCREMENT ,`PT` VARCHAR(200) NULL ,`AF` TEXT NULL ,`TI` TEXT NULL ,`SO` TEXT NULL ,`LA` VARCHAR(200) NULL ,`DT` TEXT NULL ,`DE` TEXT NULL ,`ID` TEXT NULL ,`AB` TEXT NULL ,`C1` LONGTEXT NULL ,`RP` TEXT NULL ,`EM` TEXT NULL ,`RI` TEXT NULL ,`CR` LONGTEXT NULL ,`NR` TEXT NULL ,`TC` TEXT NULL ,`Z9` TEXT NULL ,`PU` TEXT NULL ,`PI` TEXT NULL ,`PA` TEXT NULL ,`SN` TEXT NULL ,`J9` TEXT NULL ,`JI` TEXT NULL ,`PD` VARCHAR(100) NULL ,`PY` YEAR NULL ,`VL` VARCHAR(45) NULL ,`IS` VARCHAR(45) NULL ,`BP` VARCHAR(45) NULL ,`EP` VARCHAR(45) NULL ,`PN` VARCHAR(45) NULL ,`DI` VARCHAR(200) NULL ,`PG` VARCHAR(45) NULL ,`WC` TEXT NULL ,`SC` TEXT NULL ,`GA` VARCHAR(45) NULL ,`UT` VARCHAR(200) NULL ,PRIMARY KEY (`ArticleID`) ) COMMENT = 'Main Resource Table';";
		
        try {
			komut.executeUpdate(query);
			System.out.println("Table 'main' created.");

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}	
        
        Scanner input=new Scanner(System.in); 

        System.out.print("Waiting to continue...");  
        String a =input.next(); 
        System.out.println("Continuing.");  
        input.close();
		
		System.out.println("System started...");
		long before = System.currentTimeMillis();
		scanForFolders(FILE_NAME);
		long after = System.currentTimeMillis();
		double totalRunTime = (after - before)/60000.0;
		System.out.println("System Stopped...");
		Logger l2 = new Logger();
		l2.write("System Stopped...");
		System.out.println("Total Run Time: "+ totalRunTime + " mins.");
		l2.write("Total Run Time: "+ totalRunTime + " mins.");
		System.out.println("With Success: "+suc+" Error: "+err);
		l2.write(ParserNew.newline);
		l2.write("With Success: "+suc+" Error: "+err+ParserNew.newline);
		l2.close();
	}
	public static void scanForFolders (String folderName)
	{
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isDirectory()) {
	
				scanForFiles(file.getPath().toString());
		    }
		}
	}
	public static void scanForFiles (String folderName)
	{
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
				p.read(folderName+"\\"+file.getName());
		    //System.out.println(folderName+"\\"+file.getName());
		    }
		}
	}

}
