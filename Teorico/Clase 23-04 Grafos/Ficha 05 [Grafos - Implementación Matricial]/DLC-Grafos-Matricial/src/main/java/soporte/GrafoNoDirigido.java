package soporte;

/**
 * Una clase para representar un Grafo No Dirigido, con posibilidad de incluir factores de peso, 
 * mediante matriz de adyacencia. Simplemente, consideramos que un grafo no dirigido ES UN GRAFO 
 * DIRIGIDO pero simétrico: toda vez que existe el arco ady[i][j] existe también el arco ady[j][i].
 * @author  Ing. Valerio Frittellli.
 * @version Noviembre de 2009.
 */
public class GrafoNoDirigido extends Grafo
{
    /**
     * Crea un GrafoNoDirigido con espacio para un máximo de 10 nodos.
     */
    public GrafoNoDirigido()
    {
         this (10);
    }

    /**
     * Crea un GrafoNoDirigido con espacio para un máximo de n nodos.
     * @param n el número máximo de nodos a representar.
     */
    public GrafoNoDirigido (int n)
    {
       super(n);
    }

     /**
      * Permite unir dos nodos n1 y n2 con un arco cuyo factor de peso será igual a p. Si alguno 
      * de los nodos n1 o n2 no existe en el grafo, la operación no se llevará a cabo. Lo mismo 
      * vale si los objetos n1 o n2 no son de la misma clase que los objetos que ya están en el grafo.
      * @param n1 el primer nodo que define al arco.
      * @param n2 el segundo nodo que define al arco.
      * @param p el peso del arco que unirá a n1 y n2.
      * @return true si la operación se llevó a cabo - false en caso contrario.
      */
     @Override
     public boolean unir ( Object n1, Object n2, int p )
     {
           boolean out = false;
           int i1 = buscar (n1);
           int i2 = buscar (n2);
           if( i1 != -1 && i2 != -1 )
           {
              ady[i1][i2] = new Arco(p);
              ady[i2][i1] = ady[i1][i2];
              out = true;
           }
           return out;
     }    
    
    
    /**
     *  Determina cuántas componentes conexas tiene el grafo, recorriendo el mismo en profundidad.
     *  @return la cantidad de componentes conexas del grafo.
     */ 
    public int contarConexasEnProfundidad ()
    { 
          int k, cx = 0, n = nodos.length;
          boolean []visitados = new boolean[n];
            
          //aplica el recorrido...
          for (k=0; k<n; k++)
          {   
               if ( ! visitados[k] ) 
               {
                      // encontro una nueva componente conexa...
                      // ... la cuenta y la recorre en profundidad...
                      cx++;
                      visitarEnProfundidad(k, visitados);
               }
          }
          return cx;
    }

    /**
     *  Determina cuántas componentes conexas tiene el grafo, recorriendo el mismo en amplitud.
     *  @return la cantidad de componentes conexas del grafo.
     */ 
    public int contarConexasEnAmplitud ()
    {
         // Cuantas componentes conexas? (busqueda en amplitud)
         int k, n = nodos.length, cx = 0;
         boolean []visitados = new boolean[n];
                  
         //comienza el recorrido...
         for (k=0; k<n; k++)
         { 
              if ( ! visitados[k] ) 
              {
                      // encontro una nueva componente conexa...
                      // ... la cuenta y la recorre en amplitud...
                      cx++;
                      visitarEnAmplitud(k, visitados);
              }
         }
         return cx;
    }
    
    /**
     *  Calcula un árbol de expansión mínimo para el grafo, mediante el algoritmo de Prim. El grafo debería 
     *  constar de una sola y única componente conexa.
     *  @return una cadena representando el contenido del árbol de expansión mínimo calculado
     */
    public String buscarAEM()
    { 
           int n = nodos.length;
           int k, t, suma  = 0, min = n;        // la casilla [n] se usa como bandera...
           int visitados[] = new int[n + 1];    // uno más que la máxima cantidad de nodos...
           int padre[] = new int [n];           // indice del padre del nodo que entra al árbol
           int novisto = -10000;                // se usa como bandera, conviene un valor "grande"...
    
           for (k = 0; k < n; k++)
           {
                visitados[k] = novisto; // no vi a nadie todavia...
                padre[k] = -1;          // ... y por lo tanto tampoco hay padres
           }
    
           visitados[n] = novisto - 1; // se usa como bandera para buscar el minimo peso
           for (k = 0; k != n; k = min , min = n)
           {
                visitados[k] = - visitados[k];
                if (visitados[k] == - novisto) { visitados[k] = 0; }
                for (t = 0; t < n; t++)
                {
                     if (visitados[t] < 0)
                     {
                        int prioridad = ady[k][t].getPeso(); 
                        if (ady[k][t].exists() && visitados[t] < -prioridad)
                        {
                           visitados[t] = -prioridad;
                           padre[t] = k;
                        }
                        if (visitados[t] > visitados[min]) { min = t; }
                     }
                }
           }
           StringBuilder sb = new StringBuilder("Un Arbol de Expansion Minimo, contiene los arcos siguientes:\n\n");
           for (k = 1; k < n; k++)
           {
                 int i1 = padre[k];
                 sb.append("Arco: (" + nodos[i1] + "," + nodos[k] + ") con peso: " + visitados[k] + "\n");
                 suma += visitados[k];
           }
           sb.append("\n\tLa suma de pesos en el AEM es: " + suma);
           return sb.toString();
    }

     
    /**
     * Calcula el "camino más corto" (o sea, el de menor sumatoria de pesos) desde un nodo tomado 
     * como parámetro a cada uno del resto de los nodos del grafo, mediante el algoritmo de Dijkstra. 
     * @param nodoinicio el nodo desde el cual se calculan las distancias mínimas.
     * @return una cadena representando los recorridos calculados.
     */
    public String buscarCMC( Object nodoinicio )
    {  
          int n = nodos.length;
          int k, t, inicio, suma = 0, min = n; // la casilla [n] se usa como bandera...
          int visitados[] = new int [n + 1];   // uno mas que la maxima cantidad de nodos...
          int padre[] = new int[n];            // indice del padre del nodo que entra al arbol
          int novisto = -10000;                // se usa como bandera, conviene un valor "grande"...

          for (k = 0; k < n; k++)
          {
               visitados[k] = novisto;  // no vi a nadie todavia...
               padre[k] = -1;           // ... y por lo tanto npoco hay padres
          }
    
          visitados[n] = novisto - 1; // se usa como bandera para buscar el minimo peso
          inicio = buscar(nodoinicio);
          if (inicio == -1) { return "No existe el nodo inicial dado..."; }
          
          for (k = inicio; k != n; k = min , min = n)
          {
               visitados[k] = - visitados[k];
               if (visitados[k] == - novisto) { visitados[k] = 0; }
               for (t = 0; t < n; t++)
               {
                     if (visitados[t] < 0)
                     {
                        int p = ady[k][t].getPeso(); 
                        int prioridad = visitados[k] + p;
                        if (ady[k][t].exists() && visitados[t] < -prioridad)
                        {
                               visitados[t] = -prioridad;
                               padre[t] = k;
                        }
                        if (visitados[t] > visitados[min]) { min = t; }
                     }
               }
          }

          StringBuilder sb = new StringBuilder("Los caminos minimos desde " + nodoinicio + " hasta los otros nodos son:\n\n");
          for (k = 0; k < n; k++)
          {
               if (k != inicio)
               {
                     int i1 = padre[k];
                     sb.append("Longitud hasta " + nodos[k] + ": " + visitados[k]);
                     sb.append(", volviendo por los nodos: ");
                     while(i1 != -1)
                     {
                            sb.append(nodos[i1] + " - ");
                            i1 = padre[i1];
                     }
                     sb.append("\n");
               }
          }
          return sb.toString();
    }

    
    private void visitarEnProfundidad (int k, boolean []visitados)
    {
         int t, n = nodos.length;
         visitados[k] = true;
         for (t=0; t<n; t++)
         {
            if( ady[k][t].exists() )
            {
                if ( ! visitados[t] ) { visitarEnProfundidad(t, visitados); }
            }
         }
    }
    
    private void visitarEnAmplitud (int k, boolean []visitados)
    {
          int t;
          Cola cola = new Cola();
          
          cola.insertar(k);
          while( ! cola.vacia() )
          {
                k = ((Integer)cola.borrar()).intValue();
                visitados[k] = true;
                for (t = 0; t<nodos.length; t++)
                {
                     if ( ady[k][t].exists())
                     {
                           // no había sido encontrado aún...
                           if ( ! visitados[t] )  
                           {
                                 cola.insertar( t );
                                 visitados[t] = true;  // ahora esta en la cola esperando terminar...
                           }
                     }
                }
          }
    }
}
