import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Main {
	
	public static void main (String [] args) throws IOException, NoSuchAlgorithmException
	{
//		Index index = new Index();
//		index.initialize();
//		index.addBlobs("Amelia.txt");
//		index.addBlobs("ProgressAndPoverty");
//		Commit test1 = new Commit ("summary1", "author1", "Eliza Koblentz", null);
//		index.clear();
//		//System.out.println ("Test 1 content:\n" + test1.getContentOfFile());
//		index.addBlobs("Ava.txt");
//		index.addBlobs("notes.txt");
//		Commit test2 = new Commit ("summary2", "author2", "Amelia Koblentz", test1);
//		index.clear();
//		index.addBlobs("test.txt");
//		index.addBlobs("Eliza.txt");
//		//System.out.println ("Test 2 content:\n" + test2.getContentOfFile());
//		Commit test3 = new Commit ("summary3", "author3", "Ava Lastname", test2);
//		index.clear();
//		Path p = Paths.get("test.txt");
//        try {
//            Files.writeString(p, "examplejfiowejqoifewiqp483782", StandardCharsets.ISO_8859_1);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//		index.addBlobs("temporary.txt");
//		index.addBlobs("HenryGeorge");
//		index.removeBlobs("temporary.txt");
//		index.editBlob("test.txt");
//		Commit test4 = new Commit ("summary4", "author4", "Henry George", test3);
		
//		BufferedReader r = new BufferedReader(new FileReader("objects/" + "4e99a748ea9a2bcd8028a1a1c7f6bf36ac7b7d5a"));
//		String line = r.readLine();
//		while (line.charAt(0)=='b') { //keeps going until it encounters a tree listing (which should be found last)
//			System.out.println(line);
//			line = r.readLine();
//		}
//		r.close();
		
		Git git = new Git();
		git.addBlob("Amelia.txt");
		git.addBlob("ProgressAndPoverty");
		git.Commit("1 added gamers", "eliza wooooo");
		git.addBlob("Ava.txt");
		git.addBlob("notes.txt");
		git.Commit("2 less interesting files", "elliot licthament");
		git.addBlob("temporary.txt");
		git.addBlob("HenryGeorge");
		git.Commit("3 drug dealer type beat", "the main man");
		git.addBlob("test.txt");
		git.deleteBlob("HenryGeorge");
		git.addBlob("Eliza.txt");
		git.Commit("4 deleted my boy, added somf iles", "a pure unadulterated sicko");
		
	}

}
