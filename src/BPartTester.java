import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BPartTester {

	private static Git git;
	private static ArrayList<Commit> commits;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		git = new Git();
		git.addBlob("Amelia.txt");
		git.addBlob("ProgressAndPoverty");
		git.Commit("s1", "a1");
		git.addBlob("Ava.txt");
		git.addBlob("notes.txt");
		git.Commit("s2", "a2");
		Path p = Paths.get("objects/Amelia.txt");
	        try {
	            Files.writeString(p, "new amieowjfioewjfnweuqo", StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		git.editBlob("Amelia.txt");
		git.addBlob("temporary.txt");
		git.addBlob("HenryGeorge");
		git.Commit("s3", "a3");
		git.addBlob("test.txt");
		git.deleteBlob("HenryGeorge");
		git.addBlob("Eliza.txt");
		git.Commit("s4", "a4");
		git.addBlob("index.txt");
		git.Commit("s5,", "a5");
		commits = git.getCommits();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File directoryToBeDeleted = new File("objects");
		File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            file.delete();
	        }
	    }
	    directoryToBeDeleted.delete();
	    new File("HEAD").delete();
	    new File("index").delete();
	}

	@Test
	void testGit1() throws IOException {
		System.out.println("a1\n2022-10-4\ns1");
		assertTrue(checkIfFileContains("a1\n2022-10-04\ns1"));
	}
	
	@Test
	void testGit2() throws IOException {
		System.out.println("a1\n2022-10-4\ns1");
		assertTrue(checkIfFileContains("a2\n2022-10-04\ns2"));
	}
	
	@Test
	void testGit3() throws IOException {
		System.out.println("a1\n2022-10-4\ns1");
		assertTrue(checkIfFileContains("a3\n2022-10-04\ns3"));
	}
	
	@Test
	void testGit4() throws IOException {
		System.out.println("a1\n2022-10-4\ns1");
		assertTrue(checkIfFileContains("a4\n2022-10-04\ns4"));
	}
	
	@Test
	void testTree1() throws IOException {
		assertTrue(checkIfFileContains(commits.get(0).getTreePath() + "\n\n" + commits.get(1).s));
	}
	
	
	@Test
	void testTree2() throws IOException {
		assertTrue(checkIfFileContains(commits.get(1).getTreePath()));
	}
	
	@Test
	void testTree3() throws IOException {
		assertTrue(checkIfFileContains(commits.get(2).getTreePath()));
	}
	
	@Test
	void testTree4() throws IOException {
		assertTrue(checkIfFileContains(commits.get(3).getTreePath()));
	}
	
	
	
	@Test
	void test() throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		fail("Not yet implemented");
		
	}
	
	private boolean checkIfFileContains(String text) throws IOException {
		File objects = new File("objects");
		File[] allContents = objects.listFiles();
		boolean out = false;
	    if (allContents != null) {
	        for (File file : allContents) {
	            file.getPath();
	            String content = Files.readString(Path.of(file.getPath()));
	            if (content.contains(text)) {
	            	out = true;
	            }
	        }
	    }
	    return out;
	}

}
