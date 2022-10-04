import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BPartTester {

	private static Git git;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		git = new Git();
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
	void test() {
		fail("Not yet implemented");
	}

}
