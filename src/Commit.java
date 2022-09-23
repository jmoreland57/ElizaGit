import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class Commit {
private String pTree;

public String sum;
public String auth;
public LocalDate d;

private Commit par;
private String oth;
String s;

	public Commit(String summary, String author, String pTree, Commit parent) throws IOException, NoSuchAlgorithmException
	{
		this.pTree = pTree;
		sum = summary;
		auth = author;
		
		d = getDate();
		
		par = parent;
		oth = null;
		
		s = generateSha1(getContentOfFile());
		
		writeToFile();
		if (par !=null || oth != null)
		{
			String sha = par.generateSha1(par.getContentOfFile());
			changeContents(sha);
		}
	}
	
	public LocalDate getDate()
	{
		LocalDate d = java.time.LocalDate.now();
		return d;
	}
	
	public void setCurrentToChildOfParent()
	{
		par.oth = s;
	}
	
	 public String generateSha1(String input)
	            throws NoSuchAlgorithmException, UnsupportedEncodingException
	        {
	            MessageDigest md = MessageDigest.getInstance("SHA1");
	            md.reset();
	            byte[] buffer = input.getBytes("UTF-8");
	            md.update(buffer);
	            byte[] digest = md.digest();

	            String hexStr = "";
	            for (int i = 0; i < digest.length; i++) {
	                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	            }
	            return hexStr;
	        }
	
	public String getContentOfFile() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String content = "";
		content += ("objects/" + generateSha1(pTree));
		if (par != null)
		{
			content += ("\nobjects/" + par.generateSha1(par.getContentOfFile()) + "\n");
		}
		else
		{
			content += "\n";
		}
			
		if (oth != null)
		{
			content += ("objects/" + s + "\n");
		}
		else
		{
			content += "\n";
		}
			
		content += auth+"\n";
		content += d +"\n";
		content +=sum + "\n";

		return content;
	}
	
	public void writeToFile() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String sha = generateSha1(getContentOfFile());
        Path p = Paths.get("objects/" + sha);
        
        try {
            Files.writeString(p, getContentOfFile(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
	}
	
	public void changeContents (String fileName) throws IOException, NoSuchAlgorithmException
	{
		if (par != null) {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String content = "";
			
			content += reader.readLine() + "\n";
			setCurrentToChildOfParent();
			content += "\n";
			content += "objects/" + s;
			reader.readLine();
			content += reader.readLine() + "\n";
			content += reader.readLine() + "\n";
			content += reader.readLine() + "\n";
			/*
			 * I'm writing this current node but I need to find the parent and point it to this current node
			 * 
			 */
			File file = new File ("objects/" + fileName);
			PrintWriter print = new PrintWriter (file);
			print.print(content);
			print.close();
			
			/*
			String parSha = par.generateSha1(par.getContentOfFile());
			System.out.println (parSha);
			
			BufferedReader reader2 = new BufferedReader(new FileReader(parSha));
			content += reader2.readLine() + "\n";
			reader2.readLine();
			content += "\n";
			content += "objects/" + s + "\n";
			content += reader2.readLine() + "\n";
			content += reader2.readLine() + "\n";
			content += reader2.readLine() + "\n";
			*/
		}
		
	}
		
		
	public static void main (String [] args) throws IOException, NoSuchAlgorithmException
	{
		Commit test1 = new Commit ("summary1", "author1", "Eliza Koblentz", null);
		//System.out.println ("Test 1 content:\n" + test1.getContentOfFile());
		Commit test2 = new Commit ("summary2", "author2", "Amelia Koblentz", test1);
		//System.out.println ("Test 2 content:\n" + test2.getContentOfFile());
		
	}
	 
}
