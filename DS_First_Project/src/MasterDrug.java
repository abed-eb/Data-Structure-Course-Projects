import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

class MasterDrug
{
    private static Drug headRef;
    private Drug search(String drug)
    {
        Drug correct = headRef;
        while (correct != null)
        {
            int temp = drug.compareTo(correct.drugName);
            if (temp == 0)
                return correct;
            if (temp > 0)
                correct = correct.right;
            else
                correct = correct.left;
        }
        return null;
    }
    String initialize(File file)
    {
        float time = 0;
        try
        {
            StringTokenizer st;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            System.out.println("Loading lines...");
            Long start = System.nanoTime();
            String line = reader.readLine();
            st = new StringTokenizer(line,",");
            headRef = new Drug(st.nextToken());
            headRef.effect.add(new Effect(st.nextToken(),st.nextToken()));
            while ((line = reader.readLine()) != null)
            {
                st = new StringTokenizer(line,",");
                loadDrug(st.nextToken(),st.nextToken(),st.nextToken());
            }
            Long end = System.nanoTime();
            System.out.println("Datasset has been loaded successfully in " + (float)(end-start)/1000000000 + " seconds!");
            reader.close();
            time = (float)(end-start)/1000000000;
        }
        catch (IOException error)
        {
            System.out.println("Error while trying to reach file!"+error);
        }
        return String.valueOf(time);
    }
    int loadDrug(String input0,String input1,String input2)
    {
        Drug correct;
        if ((correct = search(input0)) != null)
        {
            String effect = correct.findEffect(input1);
            if (effect != null && effect.equals(input2))
                return 0;
            correct.effect.add(new Effect(input1,input2));
            return 2;
        }
        Drug drug = new Drug(input0);
        drug.effect.add(new Effect(input1,input2));
        correct = headRef;
        while (true)
        {
            int temp = input0.compareTo(correct.drugName);
            if (temp > 0)
            {
                if (correct.right == null)
                {
                    correct.right = drug;
                    break;
                }
                correct = correct.right;
            }
            else
            {
                if (correct.left == null)
                {
                    correct.left = drug;
                    break;
                }
                correct = correct.left;
            }
        }
        return 1;
    }
    void findDrugsAndEffects(String input)
    {
        Drug drug = search(input);
        if (drug != null)
        {
            int k =0;
            while (drug.effect.size() > k)
            {
                System.out.println(drug.effect.get(k).toString());
                k++;
            }
        }
        else
            System.out.println("Can't find drug in our database!");
    }
    ArrayList<String> findDrugsAndEffectsGUI(String input)
    {
        ArrayList<String> temp = new ArrayList<>();
        Drug drug = search(input);
        if (drug != null)
        {
            int k =0;
            while (drug.effect.size() > k)
            {
                temp.add(drug.effect.get(k).toString());
                k++;
            }
        }
        return temp;
    }
    void findEffect(String input0,String input1)
    {
        Drug drug = search(input0);
        if (drug==null)
        {
            System.out.println("First drug not found!");
            return;
        }
        String effect;
        if ((effect = drug.findEffect(input1)) == null)
            System.out.println("First drug doesn't have any effect on second drug!");
        else
            System.out.println(input0 + " have effect of " + effect + " on " + input1);
    }
    String findEffectGUI(String input0,String input1)
    {
        Drug drug = search(input0);
        if (drug==null)
            return "###000";
        String effect;
        if ((effect = drug.findEffect(input1.trim())) == null)
            return "###111";
        else
            return input0 + " have effect of " + effect + " on " + input1;
    }
    String deleteDrug(String input)
    {
        boolean right = true;
        Drug parent = headRef ;
        Drug correct = headRef;
        while (correct != null)
        {
            int temp = input.compareTo(correct.drugName);
            if (temp == 0)
                break;
            parent = correct;
            if (temp > 0)
            {
                right = true;
                correct = correct.right;
            }
            else
            {
                right =false;
                correct = correct.left;
            }
        }
        if (correct == null)
            return "Drug not found!";
        Drug successor = correct.right;
        Drug successorParent = correct;
        if (successor!=null)
        {
            while (successor.left!=null)
            {
                successorParent = successor;
                successor = successor.left;
            }
        }

        if (successor != null)
        {
            if (right)
                parent.right = successor;
            else
                parent.left = successor;
            successorParent.left = successor.right;
            successor.right = correct.right;
            successor.left = correct.left;
        }
        else
        {
            if (right)
                parent.right = correct.left;
            else
                parent.left = correct.left;
        }
        return "Drug removed successfully!";
    }
    void deleteRecord(String input0,String input1,String input2)
    {
        Drug drug = search(input0);
        if (drug==null)
        {
            System.out.println("First drug not found!");
            return;
        }
        drug.deleteEffect(input1,input2);
        if (drug.effect.size()==0)
            deleteDrug(drug.drugName);
        System.out.println("Record deleted successfully");
    }
    String deleteRecordGUI(String input0,String input1,String input2)
    {
        Drug drug = search(input0);
        if (drug==null)
            return "First drug not found!";
        String temp = drug.deleteEffect(input1,input2);
        if (drug.effect.size()==0)
            deleteDrug(drug.drugName);
        return temp;

    }
}