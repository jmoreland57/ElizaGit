
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Index {
private HashMap<String, String> hash;
	public Index()
	{
		hash = new HashMap<String, String>();
	}
	
	public void initialize()
	{
		/*
		 * makes file called index
		 */
		 Path p = Paths.get("index");
	        try {
	            Files.writeString(p, "", StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		/*
		 * make directory
		 */
		File theDir = new File("/path/objects");
		if (!theDir.exists())
		{
		    theDir.mkdirs();
		}
	}
//	"flsdfk : lksjdafkldsj"
//	String[]arr = s.split(" : ")
	
	public void addBlobs(String fileName) throws FileNotFoundException, NoSuchAlgorithmException, IOException
	{
		/*
		 * puts name and Sha1 value into map
		 */
		Blob blobby = new Blob (fileName); //THINK THIS IS WRONG GO TO THEISS
		String code = blobby.sha1Code(fileName);
		hash.put(fileName, code);
		
		String out = fileName + " : " + code + "\n";
		
		try {
		    Files.write(Paths.get("index"), out.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		
		/*
		 * clears the whole file
		 */
//		 FileWriter fwOb = new FileWriter("index", false); 
//	        PrintWriter pwOb = new PrintWriter(fwOb, false);
//	        pwOb.flush();
//	        pwOb.close();
//	        fwOb.close();
		/*
		 * takes content of current hashmap and adds it to file
		 * 
		 */  
//        BufferedWriter bf = null;
//  
//        try {
//  
//            // create new BufferedWriter for the output file
//            bf = new BufferedWriter(new FileWriter("index"));
//  
//            // iterate map entries
//            for (Map.Entry<String, String> entry :
//                 hash.entrySet()) {
//  
//                // put key and value separated by a colon
//                bf.write(entry.getKey() + " : "
//                         + entry.getValue());
//  
//                // new line
//                bf.newLine();
//            }
//  
//            bf.flush();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//  
//            try {
//  
//                // always close the writer
//                bf.close();
//            }
//            catch (Exception e) {
//            }
//        }
    }
	
	private void removeBlobFromHashmap(String fileName) {
		if (hash.containsKey(fileName))
		{
			hash.remove(fileName);
		}
	}
	
	public void removeBlobs (String fileName) throws IOException
	{
		
		removeBlobFromHashmap(fileName);
		 
		 BufferedReader r = new BufferedReader(new FileReader("index"));
		 String out = "";
		 while (r.ready()) {
			 String line = r.readLine();
			 if (!line.contains(fileName)) {
				 out += (line);
				 if (r.ready()) { //unsatisfactory but eh
					 out += ("\n");
				 }
			 }
			 
		 }
//	        PrintWriter pwOb = new PrintWriter(fwOb, false);
//	        pwOb.flush();
//	        pwOb.close();
		 FileWriter fwOb = new FileWriter("index", false);
		 fwOb.append(out);
	     fwOb.close();
	     r.close();
		/*
		 * takes content of current hashmap and adds it to file
		 * 
		 */  
//        BufferedWriter bf = null;
  
//        try {
//  
            // create new BufferedWriter for the output file
//            bf = new BufferedWriter(new FileWriter("index"));
//  
            // iterate map entries
//            for (Map.Entry<String, String> entry :
//                 hash.entrySet()) {
//  
//                // put key and value separated by a colon
//                bf.write(entry.getKey() + " : "
//                         + entry.getValue());
//  
//                // new line
//                bf.newLine();
//            }
//            
//  
//            bf.flush();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//  
//            try {
//  
//                // always close the writer
//                bf.close();
//            }
//            catch (Exception e) {
//            }
//        }
		
	}
	
	public void clear() throws IOException {
		new FileWriter("index", false).close();
		hash = new HashMap<String, String>();
	}
	
	public void deleteBlob(String blob) throws IOException {
	    Path filePath = Path.of("index");
	    String content = Files.readString(filePath);
	    Path p = Paths.get("index");
        try {
            Files.writeString(p, content + "*deleted* " + blob + "\n", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public void editBlob(String blob) throws IOException, NoSuchAlgorithmException {
		Path filePath = Path.of("index");
	    String content = Files.readString(filePath);
	    Path p = Paths.get("index");
        try {
            Files.writeString(p, content + "\n*edited* " + blob, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public static void main (String [] args) throws FileNotFoundException, NoSuchAlgorithmException, IOException
	{
		Index test1 = new Index();
		test1.addBlobs("Amelia.txt");
		test1.addBlobs("Ava.txt");
		test1.removeBlobs("Amelia.txt");
		test1.removeBlobs("Ava.txt");
	}
}
