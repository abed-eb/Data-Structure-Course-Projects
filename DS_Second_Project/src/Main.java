import javax.swing.*;
import java.io.File;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Welcome panel = new Welcome();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible (true);
        File file;
        while ((file = panel.getFile())==null){assert true;}
        frame.getContentPane().removeAll();
        frame.dispose();
        Data data = new Data();
        data.initialize(file);
            while (true)
            {
                try
                {
                    System.out.println("Choose what you want : ");
                    System.out.println("1.sup");
                    System.out.println("2.conf");
                    System.out.println("3.apriori");
                    int option = Integer.parseInt(sc.nextLine());
                    String temp;
                    switch (option)
                    {
                        case 1:
                            System.out.println("Enter first set in following form A1;A2;... like A5;A4 and press Enter :");
                            String temp0 = sc.nextLine();
                            System.out.println("Enter second set in following form A1;A2;... like A5;A4 and press Enter :");
                            System.out.println("The sup of this 2 sets is "+ data.sup(temp0,sc.nextLine())+"%");
                            break;
                        case 2:
                            System.out.println("Enter first set in following form A1;A2;... like A5;A4 and press Enter :");
                            temp = sc.nextLine();
                            System.out.println("Enter second set in following form A1;A2;... like A5;A4 and press Enter :");
                            System.out.println("The conf of this 2 sets is "+data.conf(temp,sc.nextLine())+"%");
                            break;
                        case 3:
                            System.out.println("Enter minimum percentage with no percentage sign:");
                            data.apriori(Integer.parseInt(sc.nextLine()));
                            break;
                        default:
                            System.out.println("Wrong input");
                    }
                }
                catch (NumberFormatException err)
                {
                    System.out.println("Wrong input");
                }
            }

    }
}
