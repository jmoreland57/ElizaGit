import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
private String ppar; //parent path / hash code
private String oth; //child
String s; //sha

	//commits something; the existence of ptree and parent are not needed as their initialization in the constructor is deprecated and superceded by use of head
	public Commit(String summary, String author, String pTree, Commit parent) throws IOException, NoSuchAlgorithmException
	{
		sum = summary;
		auth = author;
		
		d = getDate();
		
		par = parent;
		oth = null;
		
		s = getFileName();
		
		ppar = Files.readString(Path.of("HEAD"));
		
		ArrayList<String> treeInitList = getIndex();
		
		
		
		
		
//		if (par !=null)
//		{
//			String sha = par.generateSha1(par.getContentOfFile());
//			changeContents(par.s);
//			
			addCurrentToParent();
//		}
		
		
		
		Tree tree = new Tree(treeInitList);
		ptree = tree.getSha();
		writeToFile();
	}
	
	public ArrayList<String> getIndex() throws IOException, NoSuchAlgorithmException
	{
		ArrayList<String> out = new ArrayList<String>();
		String readName = "index";
		BufferedReader r = new BufferedReader(new FileReader(readName));
		ArrayList<String> deletees = new ArrayList<String>();
		boolean hasEdits = false;
		while (r.ready()) {
			String line = r.readLine();
			if (line.charAt(0)=='*') { //handles new material for edits and deletions (recording deleted file names and adding new entries for edited files)
				String[]halves = line.split("d* ");
				String fileName = halves[1];
				String oldFileSha = generateSha1(fileName);
				deletees.add(fileName);
				if (halves[0].equals("*edited*")) { //handles adding edited file with new corrected sha
					Blob blobby = new Blob (fileName); 
					Path p = Paths.get("objects/" + blobby.sha1Code(fileName));
					String newFileSha = Files.readString(p);
					out.add("blob : " + newFileSha + " " + fileName);
				}
				hasEdits = true;
			}
			else {
				out.add(reformatBlob(line));
			}
		}
		
//		if ((!hasEdits) && (par != null)) {
//			out.add("tree : " + par.getTreePath());
//		}
		
		if (!ppar.equals("")) {
			out.addAll(handleDeletions(deletees));
		}
		
		r.close();
		return out;
	}
	
	private ArrayList<String> handleDeletions(ArrayList<String> deletees) throws IOException{ //returns all blobs created after the first file being deleted was originally created, plus the last untainted tree (immediately before the oldest file being deleted now was created originally)
		String commit = "objects/" + ppar;
		BufferedReader treeGetter = new BufferedReader(new FileReader(commit));
		String ptree = treeGetter.readLine().substring(8);
		ArrayList<String> out = new ArrayList<String>();
		while (!deletees.isEmpty()) { //keeps going through older and older trees until it's found all deletees
			commit = "objects/" + ppar;
			BufferedReader rcommit = new BufferedReader(new FileReader(commit));
			BufferedReader rtree = new BufferedReader(new FileReader(rcommit.readLine()));
			String line = rtree.readLine();
			while (line!=null && line.charAt(0)=='b') { //keeps going until it encounters a tree listing (which should be found last)
				boolean isDeletee = false;
				for (int i = 0; i < deletees.size(); i++) { //loops through all remaining deletees
					String deletee = deletees.get(i);
					if (line.length()>=48+deletee.length() && line.substring(48, 48 + deletee.length()).equals(deletee)) { //sees if current deletee is found on current line
						isDeletee = true;
						deletees.remove(i);
						i--;
					}
				}
				if (isDeletee == false) {
					out.add(line);
				}
				line = rtree.readLine();
			}
			
			if (line!=null) {
				ptree = line.substring(15);
			}
			else {
				ptree = "";
			}
			if (ptree.length() == 39) {
				ptree = "" + line.charAt(7) + ptree;
			}
			rtree.close();
			String potentialParent = rcommit.readLine();
			if (potentialParent.length()!=0) { //check for if we're currently not in the first commit
				ppar = potentialParent.substring(8);
			}
			else {
				ppar = "";
			}
			
			rcommit.close();
		}
		if (!ptree.equals("")) { //check for if we're currently not in the first commit
			out.add("tree : " + ptree);
		}
		
		
		return out;
	}
	
	private String reformatBlob (String old) { //reformats a line from how it appears in index (filename : hashcode) to how it appears in tree (blob : hashcode filename)
		String[]halves = old.split(" : ");
		String out = "blob : ";
		out += halves[1] + " ";
		out += halves[0];
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
		if (!ppar.equals(""))
		{
			content += ("objects/" + ppar);
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
		if (!ppar.equals("")) {
//			String fileName = ppar;
//			Path p = Paths.get(fileName);
//			BufferedReader r = new BufferedReader(new FileReader("objects/" + fileName));
//			String out = r.readLine() + "\n" + r.readLine() + "\n" + r.readLine() + "objects/" + s + "\n" + r.readLine() + "\n" + r.readLine() + "\n" + r.readLine();
//	        try {
//	        	System.out.println(out);
//	            Files.writeString(p, out, StandardCharsets.ISO_8859_1);
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	            System.out.println("fail");
//	        }
////	        par.oth = s;
//	        r.close();
			Path path = Paths.get("objects/" + ppar);
		    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		    lines.set(2, "objects/" + s);
		    Files.write(path, lines, StandardCharsets.UTF_8);
		}
		
	}
	
	public String getTreePath() {
		return ptree;
	}
		
		
	
	 
}
