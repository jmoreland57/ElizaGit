import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
private String ptree;

public String sum; //summary
public String auth; //author
public LocalDate d; //date

private Commit par; //parents
private String oth; //child
String s; //sha

	public Commit(String summary, String author, String pTree, Commit parent) throws IOException, NoSuchAlgorithmException
	{
		sum = summary;
		auth = author;
		
		d = getDate();
		
		par = parent;
		oth = null;
		
		s = getFileName();
		
		ArrayList<String> treeInitList = getIndex();
		
		
		
		writeToFile();
		if (par !=null)
		{
//			String sha = par.generateSha1(par.getContentOfFile());
//			changeContents(par.s);
			treeInitList.add("tree : " + par.getTreePath());
			addCurrentToParent();
		}
		
		
		
		Tree tree = new Tree(treeInitList);
		ptree = tree.getSha();
	}
	
	public ArrayList<String> getIndex() throws IOException
	{
		ArrayList<String> out = new ArrayList<String>();
		String fileName = "index";
		BufferedReader r = new BufferedReader(new FileReader(fileName));
		while (r.ready()) {
			String[]halves = r.readLine().split(" : ");
			String add = "blob : ";
			add += halves[1] + " : ";
			add += halves[0];
			out.add(add);
		}
		
		return out;
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
	
	 
	public String getFileName() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//The inputs for the SHA1 are the SUBSET OF FILE CONTENTS file:
		//Summary, Date, Author, and (possibly null) pointer to parent Commit file
		return generateSha1(sum + ", " + d + ", " + auth + "," + par);
		
	}
	 
	//creates the string of what should go in the current commit file
	public String getContentOfFile() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String content = "";
		content += ("objects/" + ptree + "\n");
		if (par != null)
		{
			content += ("objects/" + par.s);
		}
		
		content += "\n";
			
		if (oth != null)
		{
			content += ("objects/" + s);
		}
		
		content += "\n";
			
		content += auth+"\n";
		content += d +"\n";
		content +=sum;

		return content;
	}
	
	//write 
	public void writeToFile() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
        Path p = Paths.get("objects/" + s);
        
        try {
            Files.writeString(p, getContentOfFile(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
	}
	
//	public void changeContents (String fileName) throws IOException, NoSuchAlgorithmException
//	{
//		if (par != null) {
//			BufferedReader reader = new BufferedReader(new FileReader("objects/" + fileName));
//			String content = "";
//			
//			content += reader.readLine() + "\n";
//			setCurrentToChildOfParent();
//			content += "\n";
//			content += "objects/" + s;
//			reader.readLine();
//			content += reader.readLine() + "\n";
//			content += reader.readLine() + "\n";
//			content += reader.readLine() + "\n";
//			/*
//			 * I'm writing this current node but I need to find the parent and point it to this current node
//			 * 
//			 */
//			File file = new File ("objects/" + fileName);
//			PrintWriter print = new PrintWriter (file);
//			print.print(content);
//			print.close();
//			
//			/*
//			String parSha = par.generateSha1(par.getContentOfFile());
//			System.out.println (parSha);
//			
//			BufferedReader reader2 = new BufferedReader(new FileReader(parSha));
//			content += reader2.readLine() + "\n";
//			reader2.readLine();
//			content += "\n";
//			content += "objects/" + s + "\n";
//			content += reader2.readLine() + "\n";
//			content += reader2.readLine() + "\n";
//			content += reader2.readLine() + "\n";
//			*/
//		}
//		
//	}
	
	private void addCurrentToParent() throws IOException {
		String fileName = "objects/" + par.s;
		Path p = Paths.get(fileName);
		BufferedReader r = new BufferedReader(new FileReader(fileName));
		String out = r.readLine() + "\n" + r.readLine() + "\n" + r.readLine() + s + "\n" + r.readLine() + "\n" + r.readLine() + "\n" + r.readLine();
        try {
            Files.writeString(p, out, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        par.oth = s;
	}
	
	public String getTreePath() {
		return ptree;
	}
		
		
	
	 
}
