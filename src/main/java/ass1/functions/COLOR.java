package ass1.functions;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
 
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class COLOR implements DocumentListener {
    private Set<String> keyword1;
    private Set<String> keyword2;
    private Set<String> keyword3;
    private Set<String> keyword4;
    private Style ks1;
    private Style ks2;
    private Style ks3;
    private Style ks4;
    private Style ns;
 
    public COLOR(JTextArea textArea) {
       
    	ns = ((StyledDocument) textArea.getDocument()).addStyle("Keyword_Style", null);
        ks1 = ((StyledDocument) textArea.getDocument()).addStyle("Keyword_Style", null);
        ks2 = ((StyledDocument) textArea.getDocument()).addStyle("Keyword_Style", null);
        ks3 = ((StyledDocument) textArea.getDocument()).addStyle("Keyword_Style", null);
        ks4 = ((StyledDocument) textArea.getDocument()).addStyle("Keyword_Style", null);
        keyword1 = new HashSet<String>();
        keyword2 = new HashSet<String>();
        keyword3 = new HashSet<String>();
        keyword4 = new HashSet<String>();
        StyleConstants.setForeground(ks1, Color.blue);
        StyleConstants.setForeground(ks2, Color.green);
        StyleConstants.setForeground(ks3, Color.red);
        StyleConstants.setForeground(ks4, Color.orange);
        StyleConstants.setForeground(ns, Color.BLACK);
 
        
        
        //keyword
        keyword1.add("public");
        keyword1.add("protected");
        keyword1.add("private");
        keyword1.add("float");
        keyword1.add("new");
        keyword1.add("double");

        //keyword2
        keyword2.add("import");
        keyword2.add("@author");
        keyword2.add("class");
        keyword2.add("extend");
        keyword2.add("new");
        keyword2.add("@version");
        
        //keyword3
        keyword3.add("void");
        keyword3.add("Random");
        keyword3.add("int");
        keyword3.add("java.util.random");
        keyword3.add("java.lang.");
        keyword3.add("println");
        keyword3.add("static");
        
        //keyword4
        keyword4.add("random");
        keyword4.add("barVal");
        keyword4.add("System.out");
        keyword4.add("java.lang.");
        keyword4.add("main");
        
    }
 
    public void color(StyledDocument doc, int pos, int len) throws BadLocationException {
        
        int begin = indexOfWordStart(doc, pos);
        int end = indexOfWordEnd(doc, pos + len);
 
        char c;
        while (begin < end) {
            c = getCharAt(doc, begin);
            if ( c == '_' || Character.isLetter(c) ) {
                begin = cw(doc, begin);
            } else {
                SwingUtilities.invokeLater(new ColouringTask(doc, begin, 1, ns));
                ++begin;
            }
        }
    }
 
    int cw(StyledDocument doc, int pos) throws BadLocationException {
        int we = indexOfWordEnd(doc, pos);
        int w_p=we - pos;
        String want = doc.getText(pos, w_p);
 
        if (keyword1.contains(want)) {
            SwingUtilities.invokeLater(new ColouringTask(doc, pos, w_p, ks1));
        }
        if (keyword2.contains(want)) {
            SwingUtilities.invokeLater(new ColouringTask(doc, pos, we, ks2));
        } 
        if (keyword3.contains(want)) {
            SwingUtilities.invokeLater(new ColouringTask(doc, pos, we, ks3));
        } 
        if (keyword4.contains(want)) {
            SwingUtilities.invokeLater(new ColouringTask(doc, pos, we, ks4));
        } 
        return we;
    }
     
    
    
    public void insertUpdate(DocumentEvent e) {
        try {
            color((StyledDocument) e.getDocument(), e.getOffset(), e.getLength());
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
 
    public void removeUpdate(DocumentEvent e) {
        try {
            color((StyledDocument) e.getDocument(), e.getOffset(), 0);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
    
    class ColouringTask implements Runnable {
        private StyledDocument doc;
        private Style style;
        private int pos;
        private int len;
 
    public ColouringTask(StyledDocument doc, int pos, int len, Style style) {
        this.doc = doc;
        this.pos = pos;
        this.len = len;
        this.style = style;
        }
 
    public void run() {
         try {
             doc.setCharacterAttributes(pos, len, style, true);
            } catch (Exception e) {}
        }
    }

	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	public char getCharAt(Document doc, int pos) throws BadLocationException {
	      return doc.getText(pos, 1).charAt(0);
	}
	 
	    
	public int indexOfWordStart(Document doc, int pos) throws BadLocationException {
          
	      for (; pos > 0 && isWordCharacter(doc, pos - 1); --pos);
	 
	        return pos;
	 }
	 

	public int indexOfWordEnd(Document doc, int pos) throws BadLocationException {

	      for (; isWordCharacter(doc, pos); ++pos);
	 
	      return pos;
	    }
	 
	 boolean isWordCharacter(Document doc, int pos) throws BadLocationException {
	      char c = getCharAt(doc, pos);
	      if (Character.isDigit(c) ||Character.isLetter(c) ||  c == '_') { return true; }
	      return false;
	    }
	 

	
}

