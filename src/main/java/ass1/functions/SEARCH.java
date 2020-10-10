package ass1.functions;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SEARCH {
	public static ArrayList<ArrayList<Integer>> searchtext(String text,String inputtext){
		int index=0;
		int sum=0;
		
		ArrayList<ArrayList<Integer>> indexs=new ArrayList<ArrayList<Integer>>();
		
		if(inputtext!=null) {
			index = text.indexOf(inputtext);
			}
		else {
			 JOptionPane.showMessageDialog(null,"cancelled");
			 return null;
		 }
		if(index > -1) {
			 while (! inputtext.equals("") & index > -1 ) {
				 try {
				//find the index 
					ArrayList<Integer> line = new ArrayList<Integer>();
					line.add(index);
					sum=index + inputtext.length();
					line.add(sum);
					indexs.add(line);
					index = text.indexOf(inputtext, sum);
				 }
				 catch (Exception e) {
				//handle exception
					 e.printStackTrace();
				}
			}
			 return indexs;
		 }
		 else {
			 String result=" can not find: " + inputtext;
			 JOptionPane.showMessageDialog(null,result);
			 return null;
		 }	
	}	
}