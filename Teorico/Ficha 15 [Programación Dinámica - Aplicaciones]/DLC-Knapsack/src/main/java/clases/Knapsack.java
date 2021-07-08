package clases;
public class Knapsack
{
    // la tabla de recurrencias...
    private int a[][] = null;
    
    // la capacidad máxima de la mochila usada...
    private int w;  
        
    public Knapsack(Item d[], int w)
    {        
        // cantidad de items...
        int n = d.length;  
        
        // capacidad de la mochila...
        this.w = w;
        
        // la matriz de recurrencias, todos sus casilleros inicialmente en cero...
        // tamaño [n+1][w+1] para incluir a n y a w como indices válidos... 
        // y mantener el cero.
        a = new int[n+1][w+1];
        
        // llenado de la matriz...
        for(int i = 1; i <= n; i++)
        {
            // peso del objeto numero i (que en el vector d está en (i-1)... 
            int wi = d[i-1].getWeight();
            
            // valor de ese objeto...
            int vi = d[i-1].getValue();

            // para cada posible capacidad residual...
            for(int x = 0; x <= w; x++)
            {                
                int v1 = a[i-1][x];
                int v2 = (wi > x) ? v1 : a[i-1][x - wi] + vi;
                a[i][x] = (v1 > v2)? v1 : v2;
            }
        }
    }
    
    public int[][] getMatrix()
    {
        // retornar la tabla completa... solución en a[n][w]...
        return a;    
    }

    public int getOptimusValue()
    {
        return a[a.length - 1][w];        
    }
        
    @Override
    public String toString()
    {
        StringBuilder r = new StringBuilder("[\n");
        for(int f = 0; f < a.length; f++)
        {
            r.append("\t[ ");
            for(int c=0; c < a[f].length; c++)
            {
                r.append(a[f][c]).append(" ");
            }
            r.append("]\n");
        }
        r.append("]");
        return r.toString();
    }
}
