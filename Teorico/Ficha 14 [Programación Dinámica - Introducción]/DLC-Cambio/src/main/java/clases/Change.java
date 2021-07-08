package clases;

/**
 * Un par de algoritmos para el problema del cambio de monedas: uno recursivo,
 * y el otro basado en programación dinámica.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Mayo de 2014.
 */
public class Change 
{
    /**
     * Algoritmo recursivo: el problema se divide en dos partes
     * que se resuelven cada una en forma recursiva... en este problema, esta
     * solución es MUY ineficiente: el árbol de llamadas recursivas es inmenso 
     * y el tiempo de ejecución es muy alto (probar con cambio = 53...) El 
     * parámetro <b>values</b> es un arreglo con los valores nominales de las 
     * monedas disponibles, incluyendo siempre al valor 1. El método calcula la 
     * cantidad mínima de monedas a retornar para cubrir el valor de 
     * <b>amount</b>.
     * @param values un arreglo con los valores nominales de la monedas disponibles.
     * @param amount el monto a cubrir con las monedas disponibles.
     * @return la cantidad mínima de monedas a usar para llegar al valor amount.
     */
    public int recursive_change(int values[], int amount)
    {
            // la cantidad de valores nominales disponibles...
            int n = values.length;
            
            // empezamos suponiendo que el mínimo es igual a tantas monedas de
            // valor 1 como sea el valor de amount... 
            int min = amount;

            // buscamos una moneda que sea ella misma el cambio exacto...
            for(int i = 0; i < n; i++)
            {
                // si es el caso, con una sóla cubrimos el valor de amount...
                if(values[i] == amount) { return 1; }
            }

            // no encontramos esa moneda... resolvemos recursivamente...
            for(int j = 1; j <= amount / 2; j++)
            {
                int candidate = recursive_change(values, j) + recursive_change(values, amount - j);
                if(candidate < min)
                {
                    min = candidate;
                }
            }

            // ... y retornamos el mínimo valor que hayamos visto...
            return min;
    }

    /**
     * Algoritmo basado en programación dinámica: los resultados ya conocidos se
     * almacenan en una tabla, y se consulta esa tabla para obtener datos que
     * sirvan para resolver nuevos casos.
     * @param values un arreglo con los valores nominales de la monedas disponibles.
     * @param amount el monto a cubrir con las monedas disponibles.
     * @return la cantidad mínima de monedas a usar para llegar al valor amount.
     */
    public int change(int values[], int amount)
    {
            // la cantidad de valores nominales disponibles...
            int n = values.length;
            
            // la tabla para los resultados de los subproblemas anteriores...
            int used[] = new int[amount + 1];
            
            // caso base: necesito 0 monedas para llegar a un amount de 0...
            used[0] = 0;
            
            // calcular el mínimo para cada posible valor de v con 1 <= v <= amount
            for(int v = 1; v <= amount; v++ )
            {
                    // empezamos suponiendo que el mínimo es el propio v...
                    int min = v;
                    
                    // ... y verificamos si hay alguna combinación menor...
                    for(int j = 0; j < n; j++)
                    {
                            // si values[j] se pasa, seguir el ciclo...
                            if(values[j] > v) { continue; }

                            int ui = v - values[j];
                            if(used[ui] + 1 < min)
                            {
                                    min = used[ui] + 1;
                            }
                    }

                    // almacenar el mínimo encontrado para v en used[v]
                    used[v] = min;
            }
            
            // retornar el mínimo para amount, que está en la última casilla...
            return used[amount];
    }   
}
