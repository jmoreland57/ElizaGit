import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Tree {
	
	private String shaCode;
	
	public Tree (ArrayList<String> list) throws NoSuchAlgorithmException, IOException
	{
		String holder = "";
		for (int index = 0; index < list.size()-1; index++)
		{
			holder = holder + list.get(index) +"\n";
		}
		holder += list.get(list.size()-1);
		
		try {
	           
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] messageDigest = md.digest(holder.getBytes());
 

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
 
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            shaCode = hashtext;
	     
	     }
		catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
		
		
		FileWriter writer = new FileWriter(new File("./objects", shaCode));
        int size = list.size();
        for (int i=0;i<size;i++) {
            String str = list.get(i).toString();
            writer.write(str);
            if(i < size-1)//This prevent creating a blank like at the end of the file**
                writer.write("\n");
        }
        writer.close();
		
	}
    public static void main (String [] args) throws NoSuchAlgorithmException, IOException
    {
    	ArrayList<String> test1 = new ArrayList<String>();
    	test1.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
    	test1.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
    	test1.add("blob: f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
    	test1.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
    	test1.add("tree: e7d79898d3342fd15daf6ec36f4cb095b52fd976");
    	
    	Tree tree1 = new Tree (test1);
    }
    
    public String getSha() {
    	return shaCode;
    }
    
}
