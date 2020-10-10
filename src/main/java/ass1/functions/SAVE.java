package ass1.functions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SAVE {
	public static void savefile(File file, String fpath,String fname, String textoutput) {
		if(file == null) {
			if(fpath==null||fname==null) {
				System.out.println("no file path or no file name");
				return;}
			else {file=new File(fpath,fname);}
		}
		try {
			BufferedWriter bWriter= new BufferedWriter(new FileWriter(file));
			bWriter.write(textoutput);
			bWriter.close();
			}
		catch(Exception e) {
			throw new RuntimeException("Save File Failed");
		}
		
	}

}