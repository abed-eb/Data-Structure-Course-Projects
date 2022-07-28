import gnu.trove.TIntArrayList;
import gnu.trove.TIntHashSet;
import gnu.trove.TLinkedList;
import jfastparser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Data
{
    TLinkedList<Set> list = new TLinkedList<>();
    Parser parser;
    void addLine(String input)
    {
        parser = new Parser(input);
        TIntHashSet set = new TIntHashSet();
        parser.eatChar('A');
        set.add(parser.eatInt());
        while (parser.more())
        {
            parser.eatChar(';');
            parser.eatChar('A');
            set.add(parser.eatInt());
        }
        list.add(new Set(set));
    }
    void initialize(File file)
    {
        try
        {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            System.out.println("Loading lines...");
            Long start = System.nanoTime();
            while ((line = reader.readLine()) != null)
            {
                addLine(line);
            }
            Long end = System.nanoTime();
            System.out.println("Database has been loaded successfully in " + (float)(end-start)/1000000000 + " seconds!");
            reader.close();
        }
        catch (IOException error)
        {
            System.out.println("Error while trying to reach file!"+error);
        }
    }
    float sup(String input)
    {
        int counter = 0;
        parser = new Parser(input);
        TIntHashSet set = new TIntHashSet();
        parser.eatChar('A');
        set.add(parser.eatInt());
        while (parser.more())
        {
            parser.eatChar(';');
            parser.eatChar('A');
            set.add(parser.eatInt());
        }
        Set current = list.getFirst();
        while (current!=null)
        {
            if (current.getSet().containsAll(set))
                counter++;
            current = current.getNext();
        }
        return (float)(100*counter)/list.size();
    }
    float sup(String input1,String input2)
    {
        Long start = System.nanoTime();
        float temp = sup(input1+";"+input2);
        Long end = System.nanoTime();
        System.out.println("Operation finished successfully in "+(float)(end-start)/1000000000 + "seconds");
        return temp;
    }
    float conf(String input1,String input2)
    {
        Long start = System.nanoTime();
        float result = sup(input1+";"+input2)/sup(input1);
        Long end = System.nanoTime();
        System.out.println("Operation finished successfully in "+(float)(end-start)/1000000000 + "seconds");
        return result*100;
    }
    void apriori(int minRepeat)
    {
        System.out.println("------------------------");
        Long start = System.nanoTime();
        KElementSubSet(minRepeat*list.size()/100, new TLinkedList<>(),allElements(minRepeat*list.size()/100));
        Long end = System.nanoTime();
        System.out.println("Operation finished successfully in "+(float)(end-start)/1000000000 + "seconds");
    }
    void KElementSubSet(int minRepeat, TLinkedList<Set> removed, SetTable input)
    {
        boolean con = false;
        TLinkedList<Set> elements = input.listOfSets;
        TLinkedList<Set> removedOfNextCall = new TLinkedList<>();
        TLinkedList<Set> elementsOfNextCall = new TLinkedList<>();
        TIntArrayList counter = new TIntArrayList();


        //steps which shows program progress
//        System.out.println("Removed:");
//        for (int j = 0; j< removed.size();j++)
//            System.out.println(removed.get(j).getSet().toString());
//        System.out.println("Added:");
//        for (int j = 0; j< elements.size();j++)
//            System.out.println(elements.get(j).getSet().toString()+" --> with sup of " + input.count.get(j));
//        System.out.println("------------------------");


        for (int i = 0; i < elements.size(); i++)
        {
            for (int j = i + 1; j < elements.size(); j++)
            {
                TIntHashSet newSet = combine(elements.get(i).getSet(), elements.get(j).getSet());
                if (newSet.size() == 0)
                    continue;
                for (int k = 0;k<removedOfNextCall.size();k++)
                {
                    if (removedOfNextCall.get(k).getSet().containsAll(newSet))
                        con =true;
                }
                for (int k = 0;k<elementsOfNextCall.size();k++)
                {
                    if (elementsOfNextCall.get(k).getSet().containsAll(newSet))
                        con =true;
                }
                if (con)
                {
                    con =false;
                    continue;
                }
                for (int k = 0; k < removed.size(); k++)
                {
                    if (newSet.containsAll(removed.get(k).getSet()))
                        continue;
                }
                Set pointer = list.getFirst();
                int c = 0;
                while (pointer!= null)
                {
                    if (pointer.getSet().containsAll(newSet))
                        c++;
                    pointer = pointer.getNext();
                }
                Set temp = new Set(newSet);
                if (c >= minRepeat)
                {
                    counter.add(c);
                    elementsOfNextCall.add(temp);
                }
                else
                    removedOfNextCall.add(temp);
            }
        }
        if (elementsOfNextCall.size() == 0)
        {
            System.out.println("all sets with sup more than " + 100*minRepeat/list.size()+"%(with repeat of "+ minRepeat +")" + " :");
            for (int k = 0;k<input.count.size();k++)
            {
                int[] array = elements.get(k).getSet().toArray();
                System.out.print("A"+array[0]);
                for (int d = 1;d<array.length;d++)
                    System.out.print(";"+"A"+array[d]);
                System.out.println(" --> with sup of "+100*input.count.get(k)/list.size()+"%(with repeat of "+ input.count.get(k) +")");
            }
            System.out.println("------------------------");
            return;
        }
        KElementSubSet(minRepeat, removedOfNextCall, new SetTable(counter,elementsOfNextCall));
        for (int z = 0; z < input.count.size(); z++)
        {
            for (int h = 0;h<counter.size();h++)
            {
                if (elementsOfNextCall.get(h).getSet().containsAll(input.listOfSets.get(z).getSet()))
                {
                    if (counter.get(h) < input.count.get(z))
                    {
                        int[] array = input.listOfSets.get(z).getSet().toArray();
                        System.out.print("A"+array[0]);
                        for (int d = 1;d<array.length;d++)
                            System.out.print(";"+"A"+array[d]);
                        System.out.println(" --> with sup of "+100*input.count.get(z)/list.size()+"%(with repeat of "+ input.count.get(z) +")");
                    }
                    break;
                }
            }
        }
        System.out.println("------------------------");
        return ;
    }
    TIntHashSet combine(TIntHashSet s1,TIntHashSet s2)
    {
        TIntHashSet output = new TIntHashSet();
        output.addAll(s2);
        output.addAll(s1);
        if (output.size() > s1.size()+1)
            return new TIntHashSet();
        return output;
    }
    SetTable allElements(int minRepeat)
    {
        TIntArrayList set = new TIntArrayList();
        TIntArrayList counter = new TIntArrayList();
        Set pointer = list.getFirst();
        while (pointer!=null)
        {
            int[] array = pointer.getSet().toArray();
            for (int k = 0;k<pointer.getSet().size();k++)
            {
                if (!set.contains(array[k]))
                {
                    set.add(array[k]);
                    counter.add(1);
                }
                else
                {
                    int index = set.indexOf(array[k]);
                    counter.set(index,counter.get(index) + 1);
                }
            }
            pointer = pointer.getNext();
        }
        TLinkedList<Set> output = new TLinkedList<>();
        TIntArrayList c = new TIntArrayList();
        for (int k= 0;k<set.size();k++)
        {
            if (counter.get(k) < minRepeat)
                continue;
            TIntHashSet temp0 = new TIntHashSet();
            temp0.add(set.get(k));
            output.add(new Set(temp0));
            c.add(counter.get(k));
        }
        return new SetTable(c,output);
    }
}
