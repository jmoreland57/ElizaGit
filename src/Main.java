import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
	
	public static void main (String [] args) throws IOException, NoSuchAlgorithmException
	{
		Index index = new Index();
		index.initialize();
		index.addBlobs("Amelia.txt");
		
		Commit test1 = new Commit ("summary1", "author1", "Eliza Koblentz", null);
		//System.out.println ("Test 1 content:\n" + test1.getContentOfFile());
		Commit test2 = new Commit ("summary2", "author2", "Amelia Koblentz", test1);
		//System.out.println ("Test 2 content:\n" + test2.getContentOfFile());
		
	}

}
