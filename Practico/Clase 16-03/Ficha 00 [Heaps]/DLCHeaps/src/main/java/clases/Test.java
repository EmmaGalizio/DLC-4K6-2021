package clases;

public class Test
{
    public static void main(String args[])
    {
        TSBHeap <Integer> ah = new TSBHeap<>(6, true);
        ah.add(8);
        ah.add(3);
        ah.add(7);
        ah.add(9);
        ah.add(4);
        ah.add(2);
        ah.add(1);
        System.out.println("Heap ascendente: " + ah);
        
        System.out.print("Mostramos en orden de extraccion: \n[ ");
        while( ! ah.isEmpty() )
        {
            int x = ah.remove();
            System.out.print(x + " ");
        }
        System.out.println("]\n");
        
        TSBHeap <Integer> dh = new TSBHeap<>(6, false);
        dh.add(8);
        dh.add(3);
        dh.add(7);
        dh.add(9);
        dh.add(4);
        dh.add(2);
        dh.add(1);
        System.out.println("Heap descendente: " + dh);
        
        System.out.print("Mostramos en orden de extraccion: \n[ ");
        while( ! dh.isEmpty() )
        {
            int x = dh.remove();
            System.out.print(x + " ");
        }
        System.out.println("]\n");
        
        TSBHeap<Cliente> h = new TSBHeap<>();
        h.add(new Cliente(2));
        h.add(new Cliente(8));
        h.add(new Cliente(4));
        h.add(new Cliente(1));
        h.add(new Cliente(9));
        h.add(new Cliente(3));
        System.out.println("Heap ascendente de clientes: " + h);
        
        System.out.print("Mostramos en orden de extraccion: \n[ ");
        while( ! h.isEmpty() )
        {
            Cliente x = h.remove();
            System.out.print(x + " ");
        }
        System.out.println("]\n");
    }
}
