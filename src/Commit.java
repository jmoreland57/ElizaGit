import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

	public Commit(String summary, String author, String pTree, Commit parent) throws IOException
	{
		this.pTree = pTree;
		sum = summary;
		auth = author;
		
		d = getDate();
		
		par = parent;
		oth = null;
		
		writeToFile();
	}
	
	public LocalDate getDate()
	{
		LocalDate d = java.time.LocalDate.now();
		return d;
	}
	
	public String generateSha1(String input)
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
	
	public String getContentOfFile()
	{
		String content = "";
		content += ("objects/" + pTree);
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
		content += "This commit is amazing!";
		
		return content;
	}
	public void writeToFile()
	{
        Path p = Paths.get("objects/" + generateSha1(getContentOfFile()));
        try {
            Files.writeString(p, "example", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}
		 
		 /*
		  * read a file to a string
		  * replace the third line of the string with ""
		  * put that string into a file
		  */
	
	public String readFile (String fileName) throws IOException
	{
		Path filePath = Paths.get(fileName);
		String content = Files.readString(filePath);
		System.out.println (content);
		return content;
	}
	
	public String replaceThirdLine (String content)
	{
		int first = 0;
		int second = content.indexOf("\n");
		String temp = "";
		for (int pos = content.indexOf("\n"); pos != -1; pos = content.indexOf("\n", pos + 1)) 
		{
			  second = pos;
			  if (content.substring(first, second).equals("\n"+auth))
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
		return content;
	}
	
	public String putInFile (String newContent)
	{
		return "hey";
	}
		/*
		if (par != null)
		{
			String filePath = "objects/" + generateSha1(getContentOfFile());
			setVariable(3, filePath, "objects/" + par.generateSha1(par.getContentOfFile()));
		}
		else
		{
			return;
		}
	}
	
	public static void setVariable(int lineNumber, String data, String fileName) throws IOException 
	{
	    Path path = Paths.get(fileName);
	    ArrayList<String> lines = (ArrayList<String>)Files.readAllLines(path, StandardCharsets.UTF_8);
	    //lines.set(lineNumber-1, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	*/

	public static void main (String [] args) throws IOException
	{
		Commit test1 = new Commit ("summary", "author", "Eliza Koblentz", null);
		Commit test2 = new Commit ("summary", "author", "Amelia Koblentz", test1);
		
		//test1.readFile("temporary.txt");
		System.out.println (test2.getContentOfFile());
		//String read = "Hey there\nMy name is Eliza\nI am a computer\n";
		//System.out.println(test1.replaceThirdLine(read));
	}
	
}
