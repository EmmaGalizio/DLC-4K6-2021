package interfaz;

/**
 * Una clase para testear la clase GrafoNoDirigido.
 * @author Ing. Valerio Frittelli.
 * @version Noviembre de 2009.
 */
import soporte.*;
public class Principal02
{
    private static GrafoNoDirigido g;
  
    public static void cargar ()
    {
          String n1, n2;  // dos Objects...
          int i,pes;
          System.out.print("Carga de un Grafo NO dirigido CON factores de peso\n");
          System.out.print("Ingrese los valores de los nodos:\n");
          for (i=0; i<g.length(); i++)
          { 
              System.out.print("Nodo " + i + ": ");
              n1 = Consola.readLine();
              g.setNodo(n1);
          }
          System.out.print("\nPrimer nodo de un arco (0 termina): ");
          n1 = Consola.readLine();
          while ( n1.compareTo("0") != 0)
          {
                System.out.print("Segundo nodo: ");
                n2 = Consola.readLine();
                do
                {
                   System.out.print("Peso del arco ( 0 < peso < 1000): ");
                   pes = Consola.readInt();
                }
                while (pes < 0 || pes >= 1000);
                
                g.unir(n1, n2, pes);
                System.out.print("\nOtro primer nodo (con 0 termina): ");
                n1 = Consola.readLine();
          }
    }

    public static void main (String[] args)
    {
         int n, op, cant;
         String n1;
         System.out.print("Cuantos nodos desea? ");
         n = Consola.readInt();
         g = new GrafoNoDirigido(n);
         do 
         {
        	  System.out.println("\n\nGrafos NO dirigidos Ponderados (Implementacion Matricial)");
        	  System.out.print("\n1. Cargar");
        	  System.out.print("\n2. Mostrar Grafo");
        	  System.out.print("\n3. Componentes Conexas (profundidad)");
        	  System.out.print("\n4. Componentes Conexas (amplitud)");
        	  System.out.print("\n5. Arbol de Expansion Minimo (Algoritmo de Prim)");
        	  System.out.print("\n6. Camino Mas Corto (Algoritmo de Dijkstra)");
        	  System.out.print("\n\n7. Salir");
        	  System.out.print("\n\n\n\t\tIngrese: ");
        	  op = Consola.readInt();
        	  switch(op)
        	  {
            	     case 1: cargar ();
            		     break;
            
            	     case 2: System.out.print("\n\nEl Grafo es el siguiente:\n\n" + g.toString());
            		     break;
            
            	     case 3: System.out.print("Componentes conexas del grafo...");
            		     System.out.print("\n...usando busqueda en profundidad...");
            		     cant = g.contarConexasEnProfundidad();
            		     System.out.print("\n\nLa cantidad de componentes conexas (recorrido en profundidad) es: " + cant);
            		     break;
            
            	     case 4: System.out.print("Componentes conexas del grafo...");
            		     System.out.print("\n...usando busqueda en amplitud...");
            		     cant = g.contarConexasEnAmplitud();
            		     System.out.print("\n\nLa cantidad de componentes conexas (recorrido en amplitud) es: " + cant);
            		     break;
            
            	     case 5: System.out.println("Arbol de Expansión Mínimo...");
            		     System.out.println("...usando Algoritmo de Prim...");
            		     System.out.println( g.buscarAEM() );
            		     break;
            
            	     case 6: System.out.println("Caminos más cortos desde el 'primer nodo' a todos los demás...");
            		     System.out.println("\n...usando el Algoritmo de Dijkstra...");
            		     System.out.print("Nodo de partida del análisis: ");
            		     n1 = Consola.readLine();
            		     System.out.println( g.buscarCMC(n1) );
            		     break;
            
            	     case 7: ;
        	  }
        }
        while (op != 7);
    } 
} 
