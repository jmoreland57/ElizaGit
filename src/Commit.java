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
private Commit oth;

	public Commit(String summary, String author, String pTree, Commit parent) throws IOException, NoSuchAlgorithmException
	{
		this.pTree = pTree;
		sum = summary;
		auth = author;
		
		d = getDate();
		
		par = parent;
		oth = null;
		
		writeToFile();
		/*
		if (par !=null || oth != null)
		{
			String sha = par.generateSha1(par.getContentOfFile());
			System.out.println (sha);
			readFile(sha);
			
		}
		*/	
		
	}
	
	public LocalDate getDate()
	{
		LocalDate d = java.time.LocalDate.now();
		return d;
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
	/*
	public String generateSha1(String input)
	{
		if (input == null)
		{
			String sha1 = "";
		    try
		    {
		        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		        crypt.reset();
		        crypt.update(input.getBytes("UTF-8"));
		        sha1 = byteToHex(crypt.digest());
		    }
		    catch(NoSuchAlgorithmException e)
		    {
		        e.printStackTrace();
		    }
		    catch(UnsupportedEncodingException e)
		    {
		        e.printStackTrace();
		    }
		    return sha1;
		}
		else
		{
			return "";
		}
		
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
*/
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
			content += ("objects/" + oth.generateSha1(par.getContentOfFile()) + "\n");
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
		//System.out.println ("this is content" + sha);
        Path p = Paths.get(sha);
       // System.out.println (p.toAbsolutePath());
        
        try {
            Files.writeString(p, getContentOfFile(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}
	/*		
	public void changeParent() throws IOException
	{
		if (par != null)
		{
			String spot = "objects/" + generateSha1(getContentOfFile());
			setVariable(3, spot, ("objects/" + par.generateSha1(par.getContentOfFile())));
		}
		else
		{
			return;
		}
	}
	
	public static void setVariable(int lineNumber, String data, String nameOfFile) throws IOException {
	    Path path = Paths.get(nameOfFile);
	    ArrayList<String> lines = (ArrayList<String>)Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(lineNumber-3, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
		*/ 
		 /*
		  * read a file to a string
		  * replace the third line of the string with ""
		  * put that string into a file
		  */
	

	public void readFile (String fileName) throws IOException, NoSuchAlgorithmException
	{
		if (par != null) {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String content = "";
			
			content += reader.readLine() + "/n";
			content += reader.readLine() + "/n";
			content += generateSha1(getContentOfFile());
			content += reader.readLine() + "/n";
			content += reader.readLine() + "/n";
			content += reader.readLine() + "/n";
			
			File file = new File ("./objects/" + fileName);
			PrintWriter print = new PrintWriter (file);
			print.print(content);
			print.close();
		}
		else
		{
			return;
		}
		
		/*
		Path filePath = Paths.get(fileName);
		String content = Files.readString(filePath);
		System.out.println (content);
		return content;
		*/
	}
	/*
	public String replaceThirdLine (String content)
	{
		if (par != null) 
		{
			int first = 0;
			int second = content.indexOf("\n");
			String temp = "";
			for (int pos = content.indexOf("\n"); pos != -1; pos = content.indexOf("\n", pos + 1)) 
			{
				  second = pos;
				  if (content.substring(first, second).equals("\n"))
				  {
					  temp += "\n";
					  first = second;
				  }
				  else
				  {
					  temp += content.substring(first, second);
					  first = second;
				  }
			}
			content = temp;
		}
		
		return content;
	}
	
	public void putInFile (String newContent)
	{
		 Path p = Paths.get("./objects/temporary.txt");
	        try {
	            Files.writeString(p, replaceThirdLine(readFile("temporary.txt")), StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	}
*/
	public static void main (String [] args) throws IOException, NoSuchAlgorithmException
	{
		Commit test1 = new Commit ("summary1", "author1", "Eliza Koblentz", null);
		System.out.println ("Test 1 content:\n" + test1.getContentOfFile());
		//Commit test2 = new Commit ("summary2", "author2", "Amelia Koblentz", test1);
		//System.out.println ("Test 2 content:\n" + test2.getContentOfFile());
		//test1.readFile("temporary.txt");
		//System.out.println (test1.getContentOfFile());
		//String read = "Hey there\nMy name is Eliza\nI am a computer\n";
		//System.out.println(test1.replaceThirdLine(read));
	}
	 
}
