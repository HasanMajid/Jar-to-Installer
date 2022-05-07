import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.Runtime;
import java.nio.file.DirectoryIteratorException;

public class Main extends JFrame {
    static JButton b1 = new JButton("Path");
    public static void main(String[] args) {

        JPanel p1;
        JButton b1, b2;
        JTextField t1, t2, t3;
        JLabel l1, l2, l3, l4;
        JFrame frame = new JFrame();

        Color grey = new Color(34, 34, 34);

        p1 = new JPanel();
        l1 = new JLabel("Name of installer: ");
        l2 = new JLabel("Output Location: ");
        l3 = new JLabel("JAR file location: ");

        t1 = new JTextField();
        t1.setPreferredSize(new Dimension(200, 10));
        t2 = new JTextField();
        t2.setPreferredSize(new Dimension(200, 10));
        t3 = new JTextField();
        t3.setPreferredSize(new Dimension(200, 10));

        b1 = new JButton("Path");
        b1.setPreferredSize(new Dimension(100,10));

        JButton build = new JButton("Build");
        build.setPreferredSize(new Dimension(100, 10));

        JButton outputLocation = new JButton("Output Path");
        build.setPreferredSize(new Dimension(100, 10));

        p1.setLayout(new GridLayout(5, 1));
        // p1.setBackground(grey);
        p1.add(l1);
        p1.add(t1); // input
        p1.add(l2);
        p1.add(t2); // input
        p1.add(l3);
        p1.add(t3); // input
        p1.add(b1);
        p1.add(build);
        p1.add(outputLocation);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kills the program on close
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null); // centers the frame
        frame.setTitle("J2Package");
        frame.add(p1);
        // frame.pack();
        frame.setVisible(true);

        class Action implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b1) {
                    String filePath = path();
                    t3.setText(filePath);
                }
                if (e.getSource() == build) {
                    try {
                        build(t1, t2, t3);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                if (e.getSource() == outputLocation) {
                    String dirPath = dirPath();
                    t2.setText(dirPath);
                }
                //if (e.getSource() == b2) System.out.println("Pressed Button2");
                
            }
        }

        Action action = new Action();
        b1.addActionListener(action);
        build.addActionListener(action);
        outputLocation.addActionListener(action);
        
        //Runtime.getRuntime().exec("explorer.exe /select," + path);

    }

      public static String path(){
        String filePath = "";
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        File[] f = fd.getFiles();
        if(f.length > 0) {
        filePath = fd.getFiles()[0].getAbsolutePath();
        //System.out.println(fd.getFiles()[0].getAbsolutePath());
        }
        return filePath;
    }
    public static String dirPath(){
        String filePath = "";

        JFileChooser chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select an output directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = chooser.showOpenDialog(new JFrame());
        filePath = chooser.getSelectedFile().toString();

        return filePath;
    }

    public static void build(JTextField name, JTextField output, JTextField jarPath ) throws IOException{
        String installerName = name.getText();
        int index = jarPath.getText().lastIndexOf('\\');
        String jarLocation = "\""+jarPath.getText().substring(0,index) + "\"";
        String jarFile = "\""+jarPath.getText().substring(index+1) +"\"";
        ///////////////////////////////////////////////////////////////////////
        String outputPath = "";
        if (output.getText() != ""){
            outputPath =  "-d \"" + output.getText() + "\"";
        }
        //System.out.println(outputPath);

        ////////////////////////////////////////////////////////////////////////
        
        //System.out.println(jarLocation);
        //System.out.println(jarFile);

        ////////////////////////////////////////////////////////////////////////
        String command =  "jpackage -t exe --name " + installerName + " --input " + jarLocation +  " --main-jar " + jarFile + " " + outputPath;
        System.out.println(command);
        Runtime.getRuntime().exec(command);

    }

}