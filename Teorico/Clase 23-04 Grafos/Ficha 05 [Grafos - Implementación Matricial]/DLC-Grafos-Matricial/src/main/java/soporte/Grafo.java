package soporte;

/**
 *  Clase para representar un grafo dirigido, con posibilidad de incluir factores de peso, en forma matricial. 
 *  Se incorporan métodos que permiten calcular el cierre transitivo del grafo, más determinar si existen ciclos 
 *  y la forma de determinar si es posible definir un orden topológico entre sus nodos.
 *  @author Ing. Valerio Frittelli.
 *  @version Noviembre de 2009.
 */
public class Grafo
{
     // atributos esenciales
     protected Object[] nodos;    // vector de nodos
     protected Arco     [][]ady;    // matriz de adyacencias
     
     // requerido por el algoritmo de Warshall
     protected boolean  [][]trans;  // matriz para almacenar el cierre transitivo
     
     // usada como auxiliar en el algoritmo para calcular el orden topológico
     private int indice;   
     
     /**
      * Inicializa el grafo para un máximo de 10 nodos.
      */
     public Grafo ()
     {
       this(10);
     }

     /**
      * Inicializa el grafo para un máximo de n nodos.
      * @param n el número máximo de nodos que serán representados en este grafo.
      */ 
     public Grafo ( int n )
     {
           // el vector de nodos, con todas su casillas valiendo null...
           nodos = new Object[n];
           
           // la matriz de adyacencias...
           ady   = new Arco [n][n];
           for(int i = 0; i < n; i++)
           {
               for(int j = 0; j < n; j++)
               {
                   //... llena de Arcos con "existe" valiendo false
                   ady[i][j] = new Arco();
               }
           }

           // la matriz de cierre transitivo, con todos sus casilleros valiendo false...
           trans = new boolean [n][n];
     }

     /**
      * Indica la cantidad de nodos representados en este grafo.
      * @return la cantidad de nodos del grafo.
      */
     public int length()
     {
        return nodos.length;   
     }

     /**
      * Permite unir dos nodos n1 y n2 con un arco cuyo factor de peso se asume igual a cero. El arco partira del 
      * nodo n1 y llegará al nodo n2 en caso de poder hacerse la unión. Si alguno de los nodos n1 o n2 no existe 
      * en el grafo, la operación no se llevará a cabo. Lo mismo vale si los objetos n1 o n2 no son de la misma 
      * clase que los objetos que ya están en el grafo.
      * @param n1 el nodo de partido del arco.
      * @param n2 el nodo de llegada del arco.
      * @return true si la operación se llevó a cabo - false en caso contrario.
      */
     public boolean unir ( Object n1, Object n2 )
     {
         return unir(n1, n2, 0);
     }
     
     /**
      * Permite unir dos nodos n1 y n2 con un arco cuyo factor de peso será igual a p. El arco partirá del nodo 
      * n1 y llegará al nodo n2 en caso de poder hacerse la unión. Si alguno de los nodos n1 o n2 no existe en 
      * el grafo, la operación no se llevará a cabo. Lo mismo vale si los objetos n1 o n2 no son de la misma clase 
      * que los objetos que ya están en el grafo.
      * @param n1 el nodo de partida del arco.
      * @param n2 el nodo de llegada del arco.
      * @param p el peso del arco que unirá a n1 y n2.
      * @return true si la operación se llevó a cabo - false en caso contrario.
      */
     public boolean unir ( Object n1, Object n2, int p )
     {
           boolean out = false;
           int i1 = buscar (n1);
           int i2 = buscar (n2);
           if( i1 != -1 && i2 != -1 )
           {
              ady[i1][i2] = new Arco(p);
              out = true;
           }
           return out;
     }
     
     /**
      * Permite cortar el arco que une a los nodos n1 y n2, suponiendo que el arco partía del nodo n1 y 
      * llegaba al nodo n2. El peso del arco (si existía el arco) se perderá. Si alguno de los nodos n1 
      * o n2 no existe en el grafo, la operación no se llevará a cabo. Lo mismo vale si los objetos n1 o 
      * n2 no son de la misma clase que los objetos que ya están en el grafo.
      * @param n1 el nodo de partida del arco.
      * @param n2 el nodo de llegada del arco.
      * @return true si la operación se llevó a cabo - false en caso contrario.
      */
     public boolean cortar ( Object n1, Object n2 )
     {
           boolean out = false;
           int i1 = buscar (n1);
           int i2 = buscar (n2);
           if( i1 != -1 && i2 != -1 )
           {
              ady[i1][i2].set(false);
              out = true;
           }
           return out;
     }
     
     /**
      * Retorna true si había un arco que partiera de n1 y llegara a n2, y false en caso contrario.
      * @param n1 Nodo de partida del arco chequeado.
      * @param n2 Nodo de llegada del arco chequeado.
      * @return true si había un arco partiendo de n1 y llegando a n2 - false en caso contrario.
      */
     public boolean hayArco ( Object n1, Object n2 )
     {
       boolean out = false;
       int i1 = buscar (n1);
       int i2 = buscar (n2);
       if( i1 != -1 && i2 != -1 )
       {
          out = ady[i1][i2].exists();
       }
       return out;
     }

     /**
      * Retorna el peso del arco que parte de n1 y llega a n2 (si tal arco existe), o retorna cero si el arco no
      * existe. Para evitar ambiguedades respecto del valor retornado, la invocación a este método debería ir 
      * precedida de una invocación al método hayArco() para validar que el arco exista.
      * @param n1 Nodo de partida del arco chequeado.
      * @param n2 Nodo de llegada del arco chequeado.
      * @return el peso del arco.
      */
     public int getPeso ( Object n1, Object n2 )
     {
       int out = 0;
       int i1 = buscar (n1);
       int i2 = buscar (n2);
       if( i1 != -1 && i2 != -1 )
       {
          out = ady[i1][i2].getPeso();
       }
       return out;
     }

     /**
      * Asigna un nuevo nodo al grafo, comprobando homogeneidad y siempre y cuando haya lugar.
      * @param x el objeto a agregar al grafo.
      * @return true si el agregado se hizo - false si no se pudo hacer.
      */
     public boolean setNodo(Object x)
     {
       if( x != null )
       {
           if( nodos[0] != null && x.getClass() != nodos[0].getClass() ) return false;
           
           for (int i=0; i<nodos.length; i++)
           { 
               if(nodos[i] == null)
               {
                  nodos[i] = x;
                  return true;
               }
           }
       }
       return false;
     }
     
     /**
      * Calcula el cierre transitivo del grafo, usando el algoritmo de Warshall. 
      */
     public void cierre ()
     {
           int i, j, k, n = nodos.length;
           for (i = 0; i<n; i++)
           {
             for (j = 0; j < n; j++)
             {
                 trans[i][j] = ady[i][j].exists();
             }
           }
    
           for (k = 0; k < n; k++)
           {
                 for (i = 0; i < n; i++)
                 {
                       if ( trans[i][k] )
                       {
                           for (j = 0; j < n; j++) { trans[i][j] = trans[i][j] || trans[k][j]; }
                       }
                 }
           }
     }

     /**
      * Retorna la matriz de cierre transitivo del grafo, tal como está en ese momento en el grafo.
      * @return la matriz de cierre transitivo.
      */
     public boolean [][]getMatrizCierre()
     {
        return trans;
     }
     
     
     /**
      * Determina si el grafo tiene un orden topológico y lo calcula.
      * @return un arreglo de objetos ordenados en forma topológica, si tal orden es posible, 
      * o null en caso contrario.
      */
     public Object []ordenTopologico ()
     {
       // si existe un ciclo, el grafo no tiene orden topológico...
       if ( ciclos() ) { return null; }
       
       int k,j, n = nodos.length;
       indice = n - 1 ; // la variable indice está definida como privada en la clase
       boolean []visitados = new boolean[n];
       Object []sort   = new Object[n];
       
       // ningún nodo fue visitado aún...
       for (k=0; k<n; k++) { visitados[k] = false; }
       
       for (k=0; k<n; k++)
       { 
         if ( ! visitados[k] )
         {
             visitar1(k, visitados, sort);
         }
       }
       return sort;
     }


     /**
      * Determina si el grafo tiene ciclos, con un algoritmo MUY INEFICIENTE...
      * return true si hay algún ciclo - false en caso contrario
      */
     public boolean ciclos ()
     {
       cierre();
       for (int i = 0; i < nodos.length; i++)
       {
          if ( trans[i][i] ) { return true; }
       }
       return false;
     }
     
     /**
      * Redefinición del método heredado desde Object
      * @return el contenido del grafo en forma de String, incluyendo la matriz de cierre transitivo
      */
     @Override
     public String toString ()
     {
       StringBuilder cad = new StringBuilder("    ");  
       int i, j, n = nodos.length;

       for(i=0; i<n; i++) 
       {
           cad.append("  " + nodos[i] + "      ");
       }
       
       cad.append("\n\nMatriz de Adyacencias\n");
       
       for (i=0; i<n; i++)
       {
         cad.append(nodos[i] + "  ");
         for ( j=0; j<n; j++) 
         {
             String c = "";
             if (ady[i][j].exists()) c += "(1,"; else c = "(0,";
             int valor = ady[i][j].getPeso();
             c = c + valor + ")";
             cad.append("  " + c + "  "); 
         }
         cad.append("\n");
       }
       
       cad.append("\n\nCierre Transitivo\n");
       for (i=0; i<n; i++)
       {
         cad.append(nodos[i] + "  ");
         for (j=0; j<n; j++)
         { 
             int valor = ( trans[i][j] )? 1 : 0;
             cad.append("  " + valor + "  "); 
         }
         cad.append("\n");
       }
       
       return cad.toString();
     }

     protected int buscar (Object n1)
     {
           if(n1 != null)
           {
               if( nodos[0] != null && n1.getClass() == nodos[0].getClass() )
               {
                   for (int i=0; i<nodos.length; i++) 
                   { 
                       if ( nodos[i].equals(n1) ) { return i; } 
                   }
               }
           }
           return -1;
     }    

     private void visitar1 (int k, boolean []visitados, Object []sort)
     {
       int t, n = nodos.length;
       visitados[k] = true;
       for (t=0; t<n; t++)
       {
         if( ady[k][t].exists() )
         {
           if ( ! visitados[t] ) { visitar1(t, visitados, sort); }
         }
       }
       sort[indice] = nodos[k];
       indice--;
     }
}
