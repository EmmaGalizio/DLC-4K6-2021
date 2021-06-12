package interfaz;

/**
 * Contiene un main para testear la clase Grafo.
 * @author Ing. Valerio Frittelli.
 * @version Noviembre de 2009.
 */
import soporte.*;
public class Principal01
{
    private static Grafo g;
  
    private static void cargar ()
    {
        String n1, n2;  // dos Objects...

        // primero cargamos todos los nodos
        for(int i = 0; i<g.length(); i++)
        {
           System.out.print("Nombre del nodo[" + i + "]: ");
           n1 = Consola.readLine();
           g.setNodo(n1);
        }
        
        // luego cargamos todos los arcos
        System.out.print("\nNombre del nodo de partida de un arco (con 0 (cero) termina): ");
        n1 = Consola.readLine();
        while ( n1.compareTo("0") != 0 )
        {
            System.out.print("Ingrese el nombre del nodo de llegada: ");
            n2 = Consola.readLine();
            g.unir(n1, n2);
            System.out.print("\nNombre de un nodo de partida, para otro arco (con 0 (cero) termina): ");
            n1 = Consola.readLine();
        }
    }

    public static void main (String[] args)
    {
         int n, op;
         System.out.print("Cuantos nodos desea? ");
         n = Consola.readInt();
         g = new Grafo(n);
         do 
         {
    	  System.out.println("\n    Grafos dirigidos (Implementacion Matricial)");
    	  System.out.print("\n1. Cargar");
    	  System.out.print("\n2. Mostrar");
     	  System.out.print("\n3. Cierre Transitivo ( Algoritmo de Warshall )");
    	  System.out.print("\n4. Orden Topologico");
    	  System.out.print("\n5. Buscar Ciclos");
    	  System.out.print("\n6. Salir");
    	  System.out.print("\n\n\n\t\tIngrese: ");
    	  op = Consola.readInt();
   	  
    	  switch(op)
    	  {
    	     case 1: 
    	                 cargar ();
   		         break;
    
    	     case 2: 
    	                 System.out.print("\n\nEl Grafo es el siguiente:\n\n");
    		         System.out.print(g.toString());
    		         break;
        
    	     case 3: 
    	                 System.out.print("Se calcula el cierre transitivo... (algoritmo de Warshall)");
    		         g.cierre();
    		         System.out.print("\nHecho... ");
    		         break;
    
    	     case 4: 
    	                 System.out.print("Se busca un orden topologico...");
    		         if(g.ordenTopologico()!=null)
    		         {
    		            Object []v = g.ordenTopologico();
    		            System.out.println("Un orden topológico válido es: ");
    		            for(int j = 0; j<g.length(); j++)
    		            {
    		               System.out.println(v[j].toString());   
    		            }
    		         }
    		         else
    		         {
    		            System.out.println("\nNo hay un orden topológico posible: el grafo tiene ciclos");   
    		         }
    		         break;
    
    	     case 5: 
    	                 System.out.print("Se va a verificar la existencia de ciclos...");
    		         if (g.ciclos()) { System.out.print("\n\nHay al menos un ciclo... "); }
    			     else { System.out.print("\n\nNo hay ciclos..."); }
    		         break;
    
    	     case 6: ;
    	  }
         }
         while (op != 6);
         System.exit(0);
    } 
} 
