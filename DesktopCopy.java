// A desktop application to copy data from one location to another

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static javax.swing.JFileChooser.*;

public class GUI extends Component implements ActionListener, ChangeListener {

    private JFrame frame;
    private JPanel panel;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JButton browseFrom;
    private JButton browseTo;
    private JButton startCopy;
    private JTextField fromField;
    private JTextField toField;
    private JLabel progressbar;
    private JProgressBar myprogress;
    private File fromFile;
    private File toFile;
    private String fileName;
    private long fileLength;

    public GUI() {
        init();
    }

    private void init() {
        frame = new JFrame();

        fromLabel = new JLabel("Copy Source");
        fromField = new JTextField();

        browseFrom = new JButton("Browse From");
        browseFrom.addActionListener(this);

        toLabel = new JLabel("Destination Source");
        toField = new JTextField();

        browseTo = new JButton("Browse To");
        browseTo.addActionListener(this);

        startCopy = new JButton("Start Copying");
        startCopy.addActionListener(this);

        myprogress = new JProgressBar();
        myprogress.addChangeListener(this);

        progressbar = new JLabel("Progress");
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createMatteBorder(50,100,50,100,Color.lightGray));
        panel.setLayout(new GridLayout(3, 8));
        panel.add(fromLabel);
        panel.add(fromField);
        panel.add(browseFrom);
        panel.add(toLabel);
        panel.add(toField);
        panel.add(browseTo);
        panel.add(progressbar);
        panel.add(myprogress);
        panel.add(startCopy);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("Easy Copy App");
        frame.pack();
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseFrom) {
            JFileChooser input = new JFileChooser();
            input.setFileSelectionMode(FILES_ONLY);

            int dc = input.showOpenDialog(this);
            if (dc == APPROVE_OPTION) {
                fromFile = input.getSelectedFile();
                fileName = fromFile.getName();
                fromField.setText(fromFile.getAbsolutePath());
            }
        }
        if (e.getSource() == browseTo) {
            JFileChooser input = new JFileChooser();
            input.setFileSelectionMode(DIRECTORIES_ONLY);

            int dc = input.showOpenDialog(this);
            if (dc == APPROVE_OPTION) {
                toFile = input.getSelectedFile();
                if (fileName != null) {
                    fileName = fromFile.getName();
                }
                toField.setText(toFile.getAbsolutePath() + "\\" + fileName);
            }

                fromFile = new File(fromField.getText());
                toFile = new File(toField.getText());
        }
        if (e.getSource() == startCopy) {
            if ("".equals(fromField.getText())){
                JOptionPane.showMessageDialog(startCopy,"Source is Empty!");
                return;
            }
            if ("".equals(toField.getText())){
                JOptionPane.showMessageDialog(startCopy,"Destination Path is Blank!");
                return;
            }
           if (toFile.exists()) {
                int dc = JOptionPane.showConfirmDialog(this, "File already exist! Overwrite?",
                        "Confirm Window", JOptionPane.YES_NO_OPTION);
                if (dc == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(myprogress, "Cancelled by user");
                    fromField.setText("");
                    toField.setText("");
                    return;
                }
            }
            fileLength = fromFile.length();
            myprogress.setMaximum((int) fileLength);

            Runnable d1 = () -> {
                try {
                    FileInputStream inputstream = new FileInputStream(fromFile);
                    FileOutputStream outputStream = new FileOutputStream(toFile);

                    byte[] buffer = new byte[2048];
                    int space = 0;
                    int flag = 0;
                    while ((space = inputstream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, space);
                        flag += space;
                        myprogress.setValue(flag);
                    }
                    inputstream.close();
                    outputStream.close();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            };
            Thread t1 = new Thread(d1);
            t1.start();
        }
    }
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == myprogress){
            if (myprogress.getValue() == myprogress.getMaximum()) {
                JOptionPane.showMessageDialog(myprogress,fileName + " Copied Successfully!");
                myprogress.setValue(0);
                fromField.setText("");
                toField.setText("");
            }
        }
    }
    public static void main (String[]args) {
        new GUI();
    }
}

