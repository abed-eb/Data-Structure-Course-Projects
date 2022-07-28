import java.util.ArrayList;

class Drug
{
    String drugName;
    Drug right;
    Drug left;
    ArrayList<Effect> effect = new ArrayList<>();
    Drug(String drugName)
    {
        this.drugName = drugName;
    }
    String findEffect(String drug)
    {
        for (Effect value : effect) {
            if (value.effectiveDrug.equals(drug))
                return value.effect;
        }
        return null;
    }
    String deleteEffect(String input1,String input2)
    {
        boolean found=false;
        for (int k = 0; k < effect.size();k++)
        {
            if (effect.get(k).effectiveDrug.equals(input1))
            {
                found=true;
                if (effect.get(k).effect.equals(input2))
                {
                    effect.remove(k);
                    return "Drug removed successfully!";
                }
                break;
            }
        }
        if (found)
            return "first drug doesn't have the specific effect you entered on second drug!";
        else
            return "First drug doesn't have any effect on second drug!";
    }
}
class Effect
{
    String effect;
    String effectiveDrug;
    Effect(String effectiveDrug,String effect)
    {
        this.effect=effect;
        this.effectiveDrug=effectiveDrug;
    }
    @Override
    public String toString()
    {
        return "Drug:"+effectiveDrug +" ,Effect:"+ effect;
    }
}