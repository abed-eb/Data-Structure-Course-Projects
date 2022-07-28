import gnu.trove.TIntHashSet;
import gnu.trove.impl.TLinkableAdapter;

public class Set extends TLinkableAdapter<Set>
{
    private TIntHashSet set;

    Set(TIntHashSet set ) {
        this.set = set;
    }

    public TIntHashSet getSet() {
        return set;
    }
}
