package modelo;

/**
 * Representa un nodo del arbol de Huffman, que se implementa sobre un vector
 * (por eso el nodo no tiene "punteros" en el sentido clasico).
 * 
 * @author Ing. Valerio Frittelli.
 * @version Abril de 2013.
 */
public class HuffmanTreeNode
{
    // frecuencia del simbolo representado...
    private int  frecuencia;    
    
    // indice del padre dentro del arreglo que soporta al arbol...
    private int  padre;         
    
    // indica si este nodo es hijo izquierdo o no de su padre...
    private boolean esIzquierdo;   
    
    // los indices de los hijos...
    private int izq, der;       

    /**
     *  Crea un nodo con padre, izq y der en -1, y los demas en su valor default.
     */
    public HuffmanTreeNode()
    {
        // aun no hay padre definido...
        padre = -1;             
        
        // aun no hay hijos definidos...
        izq = der = -1;         
    }
    
    /**
     *  Crea un nodo con sus atributos inicializados segun los parametros.
     * @param f la frecuencia del simbolo representado.
     * @param p el indice del nodo padre.
     * @param e true: es hijo izquierdo de su padre.
     * @param i el indice del hijo izquierdo para este nodo.
     * @param d el indice del hijo derecho para este nodo.
     */
    public HuffmanTreeNode(int f, int p, boolean e, int i, int d)
    {
        frecuencia = f;
        padre = p;
        esIzquierdo = e;
        izq = i;
        der = d;
    }
    
    /**
     *  Acceso a la frecuencia del simbolo en el nodo.
     *  @return el valor de la frecuencia del simbolo.
     */
    public int  getFrequency() 
    { 
        return frecuencia; 
    }
    
    /**
     *  Cambia el valor de la frecuencia del simbolo en el nodo.
     *  @param x nueva frecuencia del simbolo.
     */
    public void setFrequency(int x) 
    { 
        frecuencia = x;
    }

    /**
     *  Acceso al indice del padre del nodo.
     *  @return el indice del padre del nodo.
     */
    public int getFather() 
    { 
        return padre; 
    }
    
    /**
     *  Cambia el valor del indice del padre del nodo.
     *  @param x nuevo indice del padre del nodo.
     */
    public void setFather(int x) 
    { 
        padre = x;
    }

    /**
     *  Acceso al atributo indicador de hijo izquierdo o no respecto del padre.
     *  @return el valor del flag de hijo izquierdo (true: es hijo izquierdo - false: es hijo derecho).
     */
    public boolean isLeft() 
    {
        return esIzquierdo; 
    }
    
    /**
     *  Cambia el indicador de hijo izquierdo respecto de su padre.
     *  @param x nuevo valor del indicador (true: es hijo izquierdo - false: es hijo derecho).
     */
    void left(boolean x) 
    { 
        esIzquierdo = x; 
    }

    /**
     *  Acceso al indice del hijo izquierdo del nodo.
     *  @return el indice del hijo izquierdo del nodo.
     */
    public int  getLeft() 
    { 
        return izq; 
    }

    /**
     *  Cambia el valor del indice del hijo izquierdo del nodo.
     *  @param x nuevo indice del hijo izquierdo del nodo.
     */
    public void setLeft(int x) 
    { 
        izq = x;
    }

    /**
     *  Acceso al indice del hijo derecho del nodo.
     *  @return el indice del hijo derecho del nodo.
     */ 
    public int  getRight() 
    { 
        return der; 
    }

    /**
     *  Cambia el valor del indice del hijo derecho del nodo.
     *  @param x nuevo indice del hijo derecho del nodo.
     */
    public void setRight(int x) 
    { 
        der = x;
    }

    @Override
    public String toString()
    {
    	return "Frecuencia: " + frecuencia + "Izquierdo?: " + esIzquierdo;
    }
}
