package clases;

/**
 *
 * @author Ing. Valerio Frittelli.
 * @version Mayo de 2014.
 */
public class Test 
{
    public static void main(String args[])
    {
        Change ch = new Change();
        
        // los valores nominales disponibles...
        int monedas[] = {1, 5, 10, 23, 50};
	
        System.out.print("Cuánto es el cambio en centavos que desea?: ");
	    int amount = Consola.readInt();
        int min;

        //*
        // aplicamos la primera solucion...
	    min = ch.recursive_change(monedas, amount);
	    System.out.println("1. Cantidad minima de monedas para un cambio de " + amount + ": " + min + " [con recursividad]");
        //*/

        //*
	    // aplicamos la segunda solución...
	    min = ch.change(monedas, amount);
	    System.out.println("2. Cantidad minima de monedas para un cambio de " + amount + ": " + min + " [con programación dinámica]");
        //*/
    }   
}
