import gnu.trove.TIntArrayList;
import gnu.trove.TLinkedList;

public class SetTable
{
    TIntArrayList count;
    TLinkedList<Set> listOfSets;
    SetTable(TIntArrayList count,TLinkedList<Set> listOfSets)
    {
        this.listOfSets=listOfSets;
        this.count=count;
    }
}