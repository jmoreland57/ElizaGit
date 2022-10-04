import java.io.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Git {

	private Index index;
	
	//creates the git objects
	public Git() {
		index = new Index();
		index.initialize();
		Path p = Paths.get("HEAD");
        try {
            Files.writeString(p, "", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public boolean Commit(String summary, String author) throws NoSuchAlgorithmException, IOException {
		Commit commit = new Commit(summary, author, "If you're reading this, you're having a big problem", null);
		
		Path p = Paths.get("HEAD");
        try {
            Files.writeString(p, commit.s, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        index.clear();
        
        return true;
	}
	
	//add a new blob
	public void addBlob(String fileName) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		index.addBlobs(fileName);
	}
	
	//remove a blob if it's been added after the most recent commit
	public void removeBlob(String fileName) throws IOException {
		index.removeBlobs(fileName);
	}
	
	//edit a blob if it was previously committed
	public void editBlob(String fileName) throws NoSuchAlgorithmException, IOException {
		index.editBlob(fileName);
	}
	
	//delete a blob if it was previously committed
	public void deleteBlob(String fileName) throws IOException {
		index.deleteBlob(fileName);
	}
}
