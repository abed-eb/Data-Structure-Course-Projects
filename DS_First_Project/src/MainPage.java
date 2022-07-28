import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

class MainPage extends JPanel
{


    MainPage(File file)
    {
        MasterDrug masterDrug = new MasterDrug();
        JLabel searchLabel1 = new JLabel ("search for Drugs effected by an specific drug:");
        JTextField searchText1 = new JTextField (5);
        JButton searchButton1 = new JButton ("Search");
        JTextArea output = new JTextArea (5, 5);
        JLabel searchLabel2 = new JLabel ("search for the Effect first drug have on second drug:");
        JTextField searchText2 = new JTextField (5);
        JButton searchButton2 = new JButton ("Search");
        JLabel addRecordLabel = new JLabel ("Add a new record:");
        JTextField addRecordText = new JTextField (5);
        JButton addButton = new JButton ("Add");
        JLabel deleteLabel1 = new JLabel ("Delete a drug and all related saved data:");
        JTextField deleteText1 = new JTextField (5);
        JButton deleteButton1 = new JButton ("Delete");
        JLabel deleteLabel2 = new JLabel ("Delete an specific record:");
        JTextField deleteText2 = new JTextField (5);
        JButton deleteButton2 = new JButton ("Delete");
        JLabel message = new JLabel ("");
        JLabel timeLabel = new JLabel ("");
        timeLabel.setText("Database has been loaded successfully in " +masterDrug.initialize(file)+ " seconds!");

        setPreferredSize (new Dimension (710, 410));
        setLayout (null);

        add (searchLabel1);
        add (searchText1);
        add (searchButton1);
        add (output);
        add (searchLabel2);
        add (searchText2);
        add (searchButton2);
        add (addRecordLabel);
        add (addRecordText);
        add (addButton);
        add (deleteLabel1);
        add (deleteText1);
        add (deleteButton1);
        add (deleteLabel2);
        add (deleteText2);
        add (deleteButton2);
        add (message);
        add (timeLabel);

        searchLabel1.setBounds (15, 10, 270, 25);
        searchText1.setBounds (15, 35, 125, 30);
        searchButton1.setBounds (150, 35, 80, 30);
        output.setBounds (330, 50, 370, 295);
        searchLabel2.setBounds (15, 90, 300, 25);
        searchText2.setBounds (15, 115, 170, 30);
        searchButton2.setBounds (195, 115, 80, 30);
        addRecordLabel.setBounds (15, 160, 115, 25);
        addRecordText.setBounds (15, 185, 225, 30);
        addButton.setBounds (250, 185, 70, 30);
        deleteLabel1.setBounds (15, 235, 235, 30);
        deleteText1.setBounds (15, 265, 125, 30);
        deleteButton1.setBounds (150, 265, 80, 30);
        deleteLabel2.setBounds (15, 310, 285, 30);
        deleteText2.setBounds (15, 340, 230, 30);
        deleteButton2.setBounds (250, 340, 70, 30);
        message.setBounds (330, 15, 375, 30);
        timeLabel.setBounds (330, 360, 380, 30);

        searchButton1.addActionListener(actionEvent -> {
            long start =System.nanoTime();
            ArrayList<String> temp = masterDrug.findDrugsAndEffectsGUI(searchText1.getText());
            if (temp.size()==0)
            {
                message.setText("Can't find drug in our database!");
                output.setText("");
            }
            else
            {
                output.setText("");
                message.setText("found:");
                for (String s : temp) {
                    output.append(s);
                    output.append("\n");
                }
            }
            long end = System.nanoTime();
            timeLabel.setText( "Operation took " +(float)(end-start)/1000000 + " Milli Seconds!");
        });
        searchButton2.addActionListener(e -> {
            long start =System.nanoTime();
            StringTokenizer st = new StringTokenizer(searchText2.getText(),",");
            String temp =masterDrug.findEffectGUI(st.nextToken(),st.nextToken());
            if (temp.equals("###000"))
            {
                message.setText("First drug not found!");
                output.setText("");
            }
            else if (temp.equals("###111"))
            {
                message.setText("First drug doesn't have any effect on second drug!");
                output.setText("");
            }
            else
            {
                message.setText("found:");
                output.setText(temp);
            }
            long end = System.nanoTime();
            timeLabel.setText( "Operation took " +(float)(end-start)/1000000 + "Milli Seconds!");
        });
        addButton.addActionListener(e -> {
            long start =System.nanoTime();
            String line = addRecordText.getText();
            int firstComma = line.indexOf(',');
            int secondComma = line.lastIndexOf(',');
            if (masterDrug.loadDrug(line.substring(0,firstComma),
                    line.substring(firstComma+1,secondComma),
                    line.substring(secondComma+1))==0)
            {
                message.setText("This record already exists!");
                output.setText("");
            }
            else
            {
                message.setText("Record added successfully!");
                output.setText("");
            }
            long end = System.nanoTime();
            timeLabel.setText( "Operation took " +(float)(end-start)/1000000 + "Milli Seconds!");
        });
        deleteButton1.addActionListener(e -> {
            long start =System.nanoTime();
            message.setText(masterDrug.deleteDrug(deleteText1.getText()));
            output.setText("");
            long end = System.nanoTime();
            timeLabel.setText( "Operation took " +(float)(end-start)/1000000 + "Milli Seconds!");
        });
        deleteButton2.addActionListener(e -> {
            long start =System.nanoTime();
            StringTokenizer st = new StringTokenizer(deleteText2.getText(),",");
            message.setText(masterDrug.deleteRecordGUI(st.nextToken(),st.nextToken(),st.nextToken()));
            output.setText("");
            long end = System.nanoTime();
            timeLabel.setText( "Operation took " +(float)(end-start)/1000000 + "Milli Seconds!");
        });
    }
}
