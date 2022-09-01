
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
	 
	public class Blob {
		public Blob()
		{

			this.newFile(sha1Code("/Users/elizakoblentz/eclipse-workspace/GitPrerequisite/" + "myFile.txt"));
			
		}
		
		public void newFile(String pathOfFile, String myHash)
	    {
	        String strPath = "", strName = "";
	  
	        // Try-catch Block
	        try {
	  
	            // Creating BufferedReadered object
	            BufferedReader br = new BufferedReader(
	                new InputStreamReader(System.in));
	            System.out.println("SHA1 Hash");
	  
	            // Reading File name
	            strName = br.readLine();
	            System.out.println("/Users/elizakoblentz/eclipse-workspace/GitPrerequisite/objects/" + );
	  
	            // Reading File Path
	            strPath = br.readLine();
	  
	            // Creating File Object
	            File file1
	                = new File(strPath + "" + strName + ".txt");
	  
	            // Method createNewFile() method creates blank
	            // file.
	            file1.createNewFile();
	        }
	  
	        // Try-Catch Block
	        catch (Exception ex1) {
	        }
	    }
		public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
	        Blob fileHash = new Blob();
	        System.out.println(fileHash.sha1Code("Untitled.txt"));

	        /*
	         * Input file name: testfile.txt
	         * Input file content: (only one line bellow)
	         * This is a file for test sha1 hash code
	         *
	         * Output:
	         * 7465503EADC8799AE6F64E03EE87AB747B9D08F5
	         *
	         */
	    }

	    /**
	     * Generate a file 's sha1 hash code.
	     * @param filePath file path
	     * @return sha1 hash code of this file
	     * @throws IOException if file doesn't or other IOException
	     * @throws NoSuchAlgorithmException
	     */
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

	    /**
	     * Convert a array of byte to hex String. <br/>
	     * Each byte is covert a two character of hex String. That is <br/>
	     * if byte of int is less than 16, then the hex String will append <br/>
	     * a character of '0'.
	     *
	     * @param bytes array of byte
	     * @return hex String represent the array of byte
	     */
	    public static String bytesToHexString(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (byte b : bytes) {
	            int value = b & 0xFF;
	            if (value < 16) {
	                // if value less than 16, then it's hex String will be only
	                // one character, so we need to append a character of '0'
	                sb.append("0");
	            }
	            sb.append(Integer.toHexString(value).toUpperCase());
	        }
	        return sb.toString();
	    }
		/*
	    public static String encryptThisString(String input)
	    {
	        try {
	            // getInstance() method is called with algorithm SHA-1
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	 
	            // digest() method is called
	            // to calculate message digest of the input string
	            // returned as array of byte
	            byte[] messageDigest = md.digest(input.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	 
	            // Add preceding 0s to make it 32 bit
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	 
	            // return the HashText
	            return hashtext;
	        }
	 
	        // For specifying wrong message digest algorithms
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	        
	    }
	 
	    // Driver code
	    public static void main(String args[]) throws
	                                       NoSuchAlgorithmException
	    {
	 
	        System.out.println("HashCode Generated by SHA-1 for: ");
	 
	        String s1 = "GeeksForGeeks";
	        System.out.println("\n" + s1 + " : " + encryptThisString(s1));
	 
	        String s2 = "hello world";
	        System.out.println("\n" + s2 + " : " + encryptThisString(s2));
	    }
	    */
	}

