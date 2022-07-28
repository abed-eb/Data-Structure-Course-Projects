import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataCreator
{
    public static void main(String[] args)
    {
        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\Data.txt"));
            for (int k = 525000;k>0;k--)
            {
                ArrayList<Integer> used = new ArrayList<>();
                String temp = "";
                for (int h = (int)(Math.random()*100)+1;h>0;h--)
                {
                    int index = (int)(Math.random()*100);
                    while (used.indexOf(index) != -1)
                        index += Math.pow(-1,h);
                    used.add(index);
                    temp += "A"+ index;
                    if (h != 1)
                        temp += ";";
                }
                bufferedWriter.write(temp);
                if (k!=1)
                    bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (IOException err)
        {
            System.err.println();
        }
    }
}
