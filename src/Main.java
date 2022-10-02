import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
	
	public static void main (String [] args) throws IOException, NoSuchAlgorithmException
	{
		Index index = new Index();
		index.initialize();
		index.addBlobs("Amelia.txt");
		index.addBlobs("ProgressAndPoverty");
		Commit test1 = new Commit ("summary1", "author1", "Eliza Koblentz", null);
		index.clear();
		//System.out.println ("Test 1 content:\n" + test1.getContentOfFile());
		index.addBlobs("Ava.txt");
		index.addBlobs("notes.txt");
		Commit test2 = new Commit ("summary2", "author2", "Amelia Koblentz", test1);
		index.clear();
		index.addBlobs("test.txt");
		index.addBlobs("temporary.txt");
		//System.out.println ("Test 2 content:\n" + test2.getContentOfFile());
		Commit test3 = new Commit ("summary3", "author3", "Ava Lastname", test2);
		index.clear();
		index.addBlobs("temporary.txt");
		index.addBlobs("HenryGeorge");
		index.removeBlobs("temporary.txt");
		Commit test4 = new Commit ("summary4", "author4", "Henry George", test3);
		
	}

}
