package ass1.functions;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF {
	public static void pdfconversation(FileDialog savedialog, JTextArea jta,File file) throws DocumentException {
		if(file == null){
			savedialog.setVisible(true);					
			String fpath = savedialog.getDirectory();					
			String fname = savedialog.getFile(); 
			
			if(fpath == null || fname == null) {
				System.out.println("no file path or no file name");
				return ;	
			}
			file = new File(fpath,fname);				
		}								
		try{					
			Document document = new Document();
			String text = jta.getText();
			
			FileOutputStream fileos=new FileOutputStream(file);
			PdfWriter writer = PdfWriter.getInstance(document, fileos);
			document.open();
			
			Paragraph p=new Paragraph(text);
			document.add(p); //add the text in the editor
			document.close();
			writer.close();
			
			JOptionPane.showMessageDialog(null, "converted successfully");
			}				
		catch (IOException ex){					
			throw new RuntimeException("convert to PDF Failed");	}		
	}

}
