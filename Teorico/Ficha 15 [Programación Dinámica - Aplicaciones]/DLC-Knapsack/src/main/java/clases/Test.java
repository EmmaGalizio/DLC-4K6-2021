package clases;

public class Test
{
    public static void main(String args[])
    {
        Item d[] = { new Item(1,3), new Item(2,1), new Item(3,2), new Item(4,9) };       
        //Item d[] = { new Item(1,3), new Item(2,1), new Item(3,5), new Item(4,2), new Item(5,7), new Item(6,2), new Item(7,4) };
        //Item d[] = { new Item(2,1), new Item(1,3), new Item(2,2), new Item(4,1) };       
        
        int w = 3;
        Knapsack kp1 = new Knapsack(d, w);
        System.out.println("Capacidad mochila: " + w + "\tSuma máxima de VALUES: " + kp1.getOptimusValue());        
        System.out.println("Matriz:\n" + kp1.toString());           
    }
}
