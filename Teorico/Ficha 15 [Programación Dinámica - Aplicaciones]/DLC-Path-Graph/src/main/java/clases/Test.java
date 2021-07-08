package clases;

/**
 * Clase para testear el algoritmo de programación dinámica para el problema del
 * Subconjunto Independiente de Mayor Peso de un grafo.
 *
 * @author Ing. Valerio Frittelli.
 * @version Junio de 2014.
 */
public class Test 
{
    public static void main(String args[])
    {
        // los valores de los pesos...        
        int pesos[] = {2, 5, 4, 6, 3};
        
        // int pesos[] = {1, 4, 5, 4};
        // int pesos[] = {1, 10, 1, 1, 3};
        // int pesos[] = {1, 4, 5, 4, 6, 9, 1};

        PathGraph pg = new PathGraph();
        pg.addArray(pesos);
	
	    int sm = pg.maxIndependentSet();
        System.out.println("Suma de pesos del mayor subconjunto independiente: " + sm);        
    }   
}
