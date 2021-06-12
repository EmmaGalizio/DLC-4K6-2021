package modelo;

/**
 * Clase para representar el info de la Cola de Prioridad.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2004
 */
public class Frequency implements Comparable<Frequency>
{
    private int indice;      // indice del vector que representa al arbol donde esta este valor
    private int frecuencia;  // frecuencia que tiene este signo

    /**
     *  Constructor por defecto. Coloca el indice en -1 y deja frecuencia en cero
     */
    public Frequency()
    {
       indice = -1;
    }
    
    /**
     *  Constructor. Coloca el indice en i y deja frecuencia en f
     */
    public Frequency (int f, int i)
    {
       indice = i;
       frecuencia = f;
    }

    /**
     *  Acceso al indice
     *  @return el valor del indice
     */
    public int getIndice() 
    {
        return indice;
    }
    
    /**
     *  Modifica el indice
     *  @param i el nuevo indice
     */
    public void setIndice(int i) 
    {
        indice = i;
    }

    /**
     *  Acceso a la frecuencia
     *  @return el valor de la frecuencia
     */
    public int  getFrecuencia() 
    { 
        return frecuencia; 
    }
    
    /**
     *  Modifica la frecuencia
     *  @param x la nueva frecuencia
     */
    public void setFrecuencia(int x) 
    { 
        frecuencia = x; 
    }

    /**
     *  Definicion del metodo pedido por Comparable
     *  @return Compara las frecuencias: retorna 0 si eran iguales, >0 si la primera era mayor, o <0 en caso contrario
     */
    public int compareTo(Frequency x)
    {
        return frecuencia - x.frecuencia;
    }
    
    /**
     *  Obtiene la representacion como String del objeto
     *  @return Un string con la representacion del objeto
     */
    @Override
    public String toString()
    {
        return "Frecuencia: " + frecuencia + " Indice: " + indice;
    }
}
