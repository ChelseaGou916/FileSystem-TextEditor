package ass1;

import java.io.File;
import java.util.ArrayList;

import ass1.functions.OPEN;
import ass1.functions.SAVE;
import ass1.functions.SEARCH;

import javax.swing.text.BadLocationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



public class tests 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public tests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( tests.class );
    }

    /**
     * Rigorous Test :-)
     */
    public void testopen()
    {
//   test OPEN function
        ArrayList<String> content = new ArrayList<String>();
        content.add("test\r\n");
        
        File file = new File("opentest.txt");//already have an opentext.txt, open it and read its contents, 
                                             //checking if it is the same as a known string
        ArrayList<String> s = OPEN.openfile("opentest.txt","/",file);
        assertEquals(content, s);
    }
    
     public void testsave() {   
//   test SAVE function
//Create a new file, savetest.txt, and write a string in the file,then open the file to 
//read the contents of the file, see if it is the same as the target string
        File newfile = new File("savetest.txt");
        SAVE.savefile(newfile, "./", null, "testsave\r\n");
        ArrayList<String> teststring = new ArrayList<String>();
        teststring.add("testsave\r\n");
   
        ArrayList<String> word = OPEN.openfile("savetest.txt","/",newfile);
        assertEquals(teststring, word);
    }
//   test SEARCH function
		public void testsearch()throws BadLocationException{
			String text = "swsb";
			String inputContent = "s";
			int i =0;
			ArrayList<ArrayList<Integer>> AL =	new ArrayList<ArrayList<Integer>>();
			AL = SEARCH.searchtext(text, inputContent);
			
			while (i<AL.size()) {
				assertEquals(inputContent, text.substring(AL.get(i).get(0),AL.get(i).get(1)));
				i+=1;
			}
			text = text.replace(inputContent, "a");
			assertEquals(false,text.contains(inputContent));
		}

}

     