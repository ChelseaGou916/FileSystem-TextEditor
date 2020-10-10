package ass1.functions;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class PRINT {
	public static void printtext(JTextArea args) {
		try
		{  
			//create the arguments which will be used in calling printer API
             PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
             DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
             PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
             PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
             PrintService ser = null;
             
             ser = ServiceUI.printDialog(null, 100, 100, printService, defaultService, flavor, pras);//create a printer object
             if (ser!=null)	
             {
            	 DocPrintJob job = ser.createPrintJob(); 
                 DocAttributeSet das = new HashDocAttributeSet();
                 Object o =args.getText().getBytes();
                 Doc doc = new SimpleDoc(o, flavor, das);  
                 job.print(doc, pras); 	//print the object which contains the text
             }
         }catch(Exception e1)
          {	
        	Object[] op = { "OK"}; 
     		JOptionPane.showOptionDialog(null, "print file failed", "Infomation", 
     		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
     		null, op, op[0]); 
          }	
	}

}
