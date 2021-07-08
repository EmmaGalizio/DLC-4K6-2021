package clases;

public class Item
{
    private int value;
    private int weight;
    
    public Item(int v, int w)
    {
        value = v;
        weight = w;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    @Override
    public String toString()
    {
        return "(V: " + value + " W: " + weight + ")";
    }
    
    @Override
    public boolean equals(Object x)
    {
        if(x == null) { return false; }
        if(! (x instanceof Item) ) { return false; }
        Item p = (Item)x;
        return (this.value == p.value) && (this.weight == p.weight);
    }
    
    @Override
    public int hashCode()
    {
        return value;
    }
}
