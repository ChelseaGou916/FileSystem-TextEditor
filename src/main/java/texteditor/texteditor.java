package text_editor;

import 215ass1.functions.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import com.itextpdf.text.DocumentException;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class texteditor extends JFrame {

    private JPanel firstPane;
    private JPanel secondPanel;
    private JLabel jLabel;
    private String time_format = "yyyy-MM-dd hh:mm:ss";
    private String time;
    private int x=1000;
    private FileDialog open= null;
    private FileDialog save= null;
    private File file = null;



    //launch the program
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    final JFrame jFrame = new texteditor();

                    jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jFrame.addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent e){
                            jFrame.dispose();
                        }
                    });

                    new texteditor();
                    jFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //create the frame
    public texteditor() {


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450	, 300);

        final JTextArea jTextArea= new JTextArea();

        this.setTitle("Text Editor");
        //the menu bar at the top of the frame
        final JMenuBar jMenuBar = new JMenuBar();
        //the first item of menu bar when click it the frame will show you another list
        setJMenuBar(jMenuBar);
        final JMenu jMenu= new JMenu("File");
        jMenuBar.add(jMenu);

        //the item in the list that will show after click file
        final JMenuItem jMenuItem= new JMenuItem("New");

        jMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                final JFrame frame2 =new texteditor();
                frame2.setVisible(true);
                frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame2.addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        frame2.dispose();
                    }
                });
            }
        });
        jMenu.add(jMenuItem);


        //select a file and save it
        final JMenuItem jMenuItem1= new JMenuItem("Open");
        open = new FileDialog(this, "Open File", FileDialog.LOAD);
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jTextArea.setText("");
                open.setVisible(true);
                String dirPath = open.getDirectory();
                String fileName = open.getFile();
                if(fileName!=null) {
                    String type = fileName.substring(fileName.indexOf("."), fileName.length());
                    if(type.equalsIgnoreCase(".odt")) {
                        JOptionPane.showMessageDialog(null, "Use cmd in the location of OpenOffice.exe located and input\n"
                                + " soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard");
                    }
                    else if(type.equalsIgnoreCase(".java")||type.equalsIgnoreCase(".py")||type.equalsIgnoreCase(".cpp")) {
                        jTextArea.getDocument().addDocumentListener(new COLOR(jTextArea));
                    }
                }
                ArrayList<String> outputs = OPEN.openfile(fileName,dirPath,file);
                if(!(outputs == null)) {
                    JOptionPane.showMessageDialog(null, "Opened");
                    for(int i = 0;i<outputs.size();i++) {
                        jTextArea.append(outputs.get(i));
                    }
                }
                else return ;
            }
        });
        jMenu.add(jMenuItem1);


        //save the text into a file you select
        final JMenuItem jMenuItem2= new JMenuItem("Save");
        save= new FileDialog(this, "Save File", FileDialog.SAVE);
        jMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jTextArea.getText();
                save.setVisible(true);
                String dirPath = save.getDirectory();
                String fileName = save.getFile();
                SAVE.savefile(file, dirPath,fileName, text);
                if(dirPath!=null && fileName != null)
                    JOptionPane.showMessageDialog(null, "Saved");
            }
        });
        jMenu.add(jMenuItem2);



        final JMenuItem jMenuItem3= new JMenuItem("Print");
        jMenuItem3.addActionListener(new ActionListener() {
            //call the print api
            public void actionPerformed(ActionEvent arg0) {
                PRINT.printtext(jTextArea);
            }

        });
        jMenu.add(jMenuItem3);


        //convert the text into a pdf file
        final JMenuItem jMenuItem4= new JMenuItem("Convert to PDF");
        jMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    PDF.pdfconversation(save, jTextArea,file);
                } catch (DocumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        jMenu.add(jMenuItem4);


        //close all the editor
        final JMenuItem jMenuItem5 = new JMenuItem("Exit");
        jMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EXIT.exit();
            }
        });
        jMenu.add(jMenuItem5);


        //read the all text and find the key word and highlight them
        final JButton jButton= new JButton("Search");
        jButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String	text = jTextArea.getText();
                String inputContent = JOptionPane.showInputDialog(null, "what you want to search:");
                ArrayList<ArrayList<Integer>> indexs = SEARCH.searchtext(text, inputContent);
                jTextArea.getHighlighter().removeAllHighlights();
                int i=0;
                if(indexs!=null) {
                    while (i<indexs.size()) {
                        try {
                            jTextArea.getHighlighter().addHighlight(indexs.get(i).get(0), indexs.get(i).get(1),DefaultHighlighter.DefaultPainter);
                        } catch (BadLocationException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        i+=1;
                    }
                }



            }
        });
        jMenuBar.add(jButton);



        //show somr info about the authors
        final JButton jButton1= new JButton("About");
        jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ABOUT.displaymessage();
            }
        });
        jMenuBar.add(jButton1);


        //change the font style when click manage
        final JButton jButton2= new JButton("Help");
        final JButton jButton3= new JButton("Manage");
        jMenuBar.add(jButton3);
        jButton3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                File file=new File("pom.json");
                String content;
                try {
                    content = FileUtils.readFileToString(file,"UTF-8");
                    JSONObject jsonObject=new JSONObject(content);
                    Font font = new Font(jsonObject.getString("font"),Font.HANGING_BASELINE,11);
                    jButton1.setFont(font);
                    jButton1.setFont(font);
                    jButton.setFont(font);
                    jButton2.setFont(font);
                    jButton3.setFont(font);
                    jMenu.setFont(font);
                    jMenuItem.setFont(font);
                    jMenuItem1.setFont(font);
                    jMenuItem2.setFont(font);
                    jMenuItem5.setFont(font);
                    jMenuItem3.setFont(font);
                    jMenuItem4.setFont(font);
                    jLabel.setFont(font);
                    jTextArea.setFont(font);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });


        //show some info for the user
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JOptionPane.showMessageDialog(null,"HELP \n"
                        + "This text editor could used to open docx,odt and many other type of files.\n "
                        + "When typing in it, you could click right mouse click to use PCSC function.\n "
                        + "It could also print text you write."
                        + "You can click thee manage button to change the font style.");

            }
        });
        jMenuBar.add(jButton2);

        secondPanel= new JPanel();
        jLabel = new JLabel();
        configTimeArea();

        secondPanel.add(jLabel);
        jMenuBar.add(secondPanel);


        secondPanel.add(jLabel);


        firstPane= new JPanel();
        firstPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        firstPane.setLayout(new BorderLayout(0, 0));
        setContentPane(firstPane);




        //the rightclick menu,it will show when you click right mouse in the textarea
        final JPopupMenu jPopupMenu= new JPopupMenu();
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jTextArea.copy();
            }
        });
        jPopupMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jTextArea.paste();
            }
        });
        jPopupMenu.add(pasteItem);
        JMenuItem selectItem = new JMenuItem("Select");
        selectItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jTextArea.selectAll();
            }
        });
        jPopupMenu.add(selectItem);
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jTextArea.cut();
            }
        });
        jPopupMenu.add(cutItem);
        jTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    jPopupMenu.show(jTextArea, e.getX(), e.getY());
                }
            }
        });

        firstPane.add(jTextArea, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(jTextArea);
        firstPane.add(scrollPane, BorderLayout.CENTER);
    }

    //show the time at the  top right of the editor
    private void configTimeArea() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), x);
    }


    protected class JLabelTimerTask extends TimerTask {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                time_format);

        @Override
        public void run() {
            time = dateFormatter.format(Calendar.getInstance().getTime());
            jLabel.setText(time);
        }
    }
}

