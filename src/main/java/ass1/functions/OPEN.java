package ass1.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OPEN {
	
	public static ArrayList<String> openfile(String fname,String fpath,File file){
		if(file == null) {
			if(fpath == null||fname == null) {
				System.out.println("no file path or no file name");
				return null;}
			else {file=new File(fpath,fname);}
		}
		
		try {
		String ftype=fname.substring(fname.indexOf("."), fname.length());
			if(ftype.equalsIgnoreCase(".odt")) {return null;}
			else {
				ArrayList<String> lines = new ArrayList<String>();
				BufferedReader buffer = new BufferedReader(new FileReader(file));
				
				String line = buffer.readLine();
				while(line!=null) {
					lines.add(line+"\r\n");
					line=buffer.readLine();}
				buffer.close();
				return lines;
				
				
				}}
		catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException("Read File Failed");}
		}
			
}
