import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Commit {
private String pTree;

public String sum;
public String auth;
public String d;

private Commit par;
private Commit oth;

	public Commit(String summary, String author, String pTree, Commit parent) throws IOException
	{
		this.pTree = pTree;
		sum = summary;
		auth = author;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		d = dtf.format(now); 
		System.out.println (d);
		
		par = parent;
		oth = null;
		
		writeToFile();
		changeParent();
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
		content += ("objects/" + pTree + "\n");
		if (par != null)
		{
			content += ("objects/" + par.generateSha1(par.getContentOfFile()) + "\n");
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
		
		content += auth;
		content += d;
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
	
	public void changeParent () throws IOException
	{
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
	    lines.set(lineNumber - 1, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}

	public static void main (String [] args) throws IOException
	{
		Commit test1 = new Commit ("hey everyone", "summary", "Eliza Koblentz", null);
		Commit test2 = new Commit ("live laugh love", "summary", "Amelia Koblentz", test1);
		
	}
	
}
