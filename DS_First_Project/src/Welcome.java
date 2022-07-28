import javax.swing.*;
import java.awt.*;
import java.io.File;

class Welcome extends JPanel
{
    volatile private File file;
    volatile private boolean onlyConsole;

    File getFile()
    {
        return file;
    }
    Boolean getOnlyConsole()
    {
        return onlyConsole;
    }

    Welcome()
    {
        JButton start = new JButton ("Start");
        JButton loadFile = new JButton ("Choose File");
        JFileChooser fileChooser = new JFileChooser ("src\\Drug_Input.txt");
        JLabel chooseLabel = new JLabel ("choose DataBase file:");
        JLabel welcome = new JLabel ("Hi!");
        JCheckBox defaultLocation = new JCheckBox ("Use default location");
        JCheckBox console = new JCheckBox ("Proceed using console instead of GUI");
        JLabel errorNotFound = new JLabel ("");

        setPreferredSize (new Dimension(330, 195));
        setLayout (null);

        add (start);
        add (loadFile);
        add (chooseLabel);
        add (welcome);
        add (defaultLocation);
        add (console);
        add (errorNotFound);

        start.setBounds (200, 155, 100, 20);
        loadFile.setBounds (30, 65, 100, 20);
        fileChooser.setPreferredSize(new Dimension(500, 400));
        chooseLabel.setBounds (30, 25, 325, 30);
        welcome.setBounds (30, 5, 100, 25);
        defaultLocation.setBounds (170, 60, 140, 30);
        console.setBounds (30, 110, 255, 25);
        errorNotFound.setBounds (30, 150, 165, 30);

        loadFile.addActionListener(actionEvent -> fileChooser.showOpenDialog(null));
        start.addActionListener(actionEvent -> {
            if (defaultLocation.isSelected())
                file = new File("src\\Drug_Input.txt");
            else
            {
                if (fileChooser.getSelectedFile()==null)
                    errorNotFound.setText("Choose a File!");
                else
                {
                    String[] temp = fileChooser.getSelectedFile().getName().split("\\.");
                    if (!temp[temp.length-1].equals("txt"))
                        errorNotFound.setText("input file should be text!");
                    else
                        file = fileChooser.getSelectedFile();
                }
            }
        });
        defaultLocation.addActionListener(actionEvent -> {
            if (defaultLocation.isSelected())
                loadFile.setEnabled(false);
            else
                loadFile.setEnabled(true);

        });
        console.addActionListener(actionEvent -> onlyConsole= console.isSelected());
    }
}
