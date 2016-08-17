//<Designed Developed By Gokhan Guney
//st081101028@etu.edu.tr
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class Logger {
	public final static String LOG_FILE_NAME = "E:\\Downloads\\Bitirme\\log.txt";


    static File file;
    static FileWriter writer;
    static BufferedWriter write;
	
   

    public Logger() {
        
    	
	    file = new File(LOG_FILE_NAME);

	    try {
			writer = new FileWriter(file, true);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   write = new BufferedWriter(writer);
    }
	public void write(String line)
	{

       try {
		write.write(line);
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	public void close()
	{
		try {
			write.close();
		
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
