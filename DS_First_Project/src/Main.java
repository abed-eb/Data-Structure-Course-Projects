import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Welcome panel = new Welcome();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible (true);
        File file;
        while ((file = panel.getFile())==null){assert true;}
        if (panel.getOnlyConsole())
        {
            frame.dispose();
            MasterDrug masterDrug = new MasterDrug();
            masterDrug.initialize(file);
            showOptions();
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext())
            {
                String line = sc.nextLine();
                int option = Integer.parseInt(line);
                Long start = 0L;
                StringTokenizer st ;
                switch (option)
                {
                    case 1:
                        System.out.println("Enter drug name:");
                        line=sc.nextLine();
                        start = System.nanoTime();
                        masterDrug.findDrugsAndEffects(line);
                        break;
                    case 2:
                        System.out.println("Enter first drug and second drug in form of(First_Drug,Second_Drug):");
                        line=sc.nextLine();
                        start = System.nanoTime();
                        st = new StringTokenizer(line,",");
                        masterDrug.findEffect(st.nextToken(),st.nextToken());
                        break;
                    case 3:
                        System.out.println("Enter drugs and effect in form of(First_Drug,Second_Drug,effect):");
                        line=sc.nextLine();
                        start = System.nanoTime();
                        st = new StringTokenizer(line,",");
                        if (masterDrug.loadDrug(st.nextToken(),
                                st.nextToken(),
                                st.nextToken())==0)
                            System.out.println("This record already exists!");
                        else
                            System.out.println("Record added successfully!");
                        break;
                    case 4:
                        System.out.println("Enter drug name:");
                        line=sc.nextLine();
                        start = System.nanoTime();
                        System.out.println(masterDrug.deleteDrug(line));
                        break;
                    case 5:
                        System.out.println("Enter record in form of(First_Drug,Second_Drug,effect):");
                        line=sc.nextLine();
                        start = System.nanoTime();
                        st = new StringTokenizer(line,",");
                        masterDrug.deleteRecord(st.nextToken(),st.nextToken(),st.nextToken());
                        break;
                    default:
                        System.out.println("Unknown Input!");
                }
                Long end = System.nanoTime();
                System.out.println((float)(end-start)/1000000 + "Milli Seconds!");
                showOptions();
            }
        }
        else
        {
            frame.setVisible(false);
            frame.getContentPane().removeAll();
            JPanel jPanel = new JPanel();
            JLabel loadingLabel = new JLabel ("Loading DataBase...");
            jPanel.setPreferredSize (new Dimension(190, 75));
            jPanel.setLayout (null);
            jPanel.add(loadingLabel);
            loadingLabel.setBounds (35, 25, 125, 25);
            frame.getContentPane().add(jPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible (true);
            MainPage mainPage = new MainPage(file);
            frame.setVisible(false);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(mainPage);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible (true);
        }
    }
    private static void showOptions()
    {
        System.out.println("Enter the number of option you want to do?");
        System.out.println("1.Drugs effected by an specific drug");
        System.out.println("2.Effect first drug have on second drug");
        System.out.println("3.Add a new record");
        System.out.println("4.Delete a drug and all related saved data");
        System.out.println("5.delete an specific record");
    }
}