package clases;

import java.util.ArrayList;
/**
 * La clase representa en forma directa un Path Graph (un grafo lineal) mediante
 * una lista de vértices cuyos valores representan sus pesos como números 
 * enteros que pueden ser negativos, cero o positivos. La clase se propone como
 * una forma simple de introducir un algoritmo de programación dinámica para el
 * problema del Subconjunto Independiente con Máxima Suma de Pesos: esto es, el
 * subconjunto de vértices no adyacentes cuya suma de pesos sea máxima.
 * 
 * En ese sentido, un grafo lineal g se entiende como un grafo no ponderado con 
 * n vértices numérados entre 0 y n-1, pero tal que sólo existen los arcos 
 * (g[i], g[i+1]) para todo vértice i en [0..n-1]. A los efectos del problema 
 * del Subconjunto Independiente de Peso Máximo, no importa si g es dirigido o 
 * no dirigido.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Junio de 2014.
 */
public class PathGraph 
{
    // el grafo lineal...
    private ArrayList<Integer> g = new ArrayList<>();
    
    /**
     * Crea un PathGraph vacío.
     */
    public PathGraph()
    {
    }
    
    /**
     * Retorna el peso del vértice i. Si i está fuera del rango, el método lanza
     * una excepción.
     * @param i el vértice cuyo peso será cambiado.
     * @return el peso del vértice i.
     */
    public int getVertex(int i)
    {
        return g.get(i);
    }
    
    /**
     * Ajusta el peso del vértice i al valor w. Si i está fuera del rango, el 
     * método lanza una excepción.
     * @param i el vértice cuyo peso será cambiado.
     * @param w el nuevo peso del vértice i.
     */
    public void setVertex(int i, int w)
    {
        g.add(i, w);
    }
    
    /**
     * Llena el grafo a partir de los valores del arreglo v, en el mismo orden
     * en el que vienen en v. Si v es null, el método lanza una excepción.
     * @param v el arreglo con los valores de los pesos de los vértices.
     */
    public void addArray(int v[])
    {
        for(int i = 0; i < v.length; i++)
        {
            g.add(i, v[i]);
        }
    }
    
    /**
     * Calcula el valor de la suma de los vértices del Subconjunto Independiente
     * de Suma de Pesos Máxima en el grafo (esto es, la suma de los pesos de los
     * vértices del máximo subconjunto de vértices no adyacentes del grafo). El 
     * algoritmo aplica programación dinámica.
     * @return la suma de pesos del subconjunto independiente de mayor peso.
     */
    public int maxIndependentSet()
    {
            // la cantidad de vértices...
            int n = g.size();
            
            // la tabla con los pesos máximos de los subconjuntos anteriores...
            int mw[] = new int[n + 1];
            
            // iniciar la tabla con los pesos de cada vértice (con mw[0] = 0...)
            for(int i = 1; i <= n; i++)
            {
                mw[i] = g.get(i-1);
            }
            
            // lanzar la recurrencia básica (llenado de la tabla)...
            for(int i = 2; i <= n; i++)
            {
                int v1 = mw[i-1];
                int v2 = mw[i-2] + mw[i];
                mw[i] = (v1 > v2)? v1 : v2;
            }
            
            // retornar el peso máximo...
            return mw[n];
    }
}
