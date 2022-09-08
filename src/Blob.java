
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
	 
	public class Blob {
		public Blob(String fileName) throws IOException, FileNotFoundException, NoSuchAlgorithmException
		{
			
			/*
			 * takes in file name and makes a file with the sha1code as the name
			 */
			String nameOfFile = sha1Code(fileName);
			File tester = new File (nameOfFile);
			
			/*
			 * takes all the content in the file and puts it into new file
			 */
			BufferedReader read = new BufferedReader (new FileReader(fileName));
			String contentOfFile = "";
			while (read.ready())
			{
				contentOfFile+= (char)read.read();
			}
			
			read.close();
			
			Path filePathToWrite = Paths.get("/Users/elizakoblentz/eclipse-workspace/GitPrerequisite/objects/" + nameOfFile); 
	    	try {
	    		Files.writeString(filePathToWrite, contentOfFile, StandardCharsets.ISO_8859_1);
	    	} catch (IOException exception) {
	    		System.out.println("Write failed for " + nameOfFile); 
	    	}
		}
		

		/*
		 * creates the sha1 code
		 */
		
		public static String readFile(String path, Charset encoding) throws IOException
	    {
	        byte[] encoded = Files.readAllBytes(Paths.get(path));
	        return new String(encoded, encoding);
	    }
	    
		
	    public String sha1Code(String filePath) throws IOException, NoSuchAlgorithmException {
	        FileInputStream fileInputStream = new FileInputStream(filePath);
	        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
	        byte[] bytes = new byte[1024];
	        // read all file content
	        while (digestInputStream.read(bytes) > 0);

//	        digest = digestInputStream.getMessageDigest();
	        byte[] resultByteArry = digest.digest();
	        return bytesToHexString(resultByteArry);
	    }

	    public static String bytesToHexString(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (byte b : bytes) {
	            int value = b & 0xFF;
	            if (value < 16) {
	                // if value less than 16, then it's hex String will be only
	                // one character, so we need to append a character of '0'
	                sb.append("0");
	            }
	            sb.append(Integer.toHexString(value));
	        }
	        return sb.toString();
	    }
	    
	    /*
	     * end of creating sha1 code
	     */
		
	    public static void main (String [] args) throws FileNotFoundException, NoSuchAlgorithmException, IOException
	    { 
	    	Blob blob1 = new Blob ("Eliza.txt");
	    }
	    
	}

