
import java.io.File;

public class Index {
	public Index()
	{
		makesFile();
		makesDirectory();
	}
	
	public void makesFile()
	{
		try {
	         File empty = new File("index.txt");
	         empty.createNewFile();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public void makesDirectory()
	{
		File theDir = new File("/path/objects");
		if (!theDir.exists())
		{
		    theDir.mkdirs();
		}
	}
	
	public static void main (String [] args)
	{
		Index test1 = new Index();
		
	}
}
