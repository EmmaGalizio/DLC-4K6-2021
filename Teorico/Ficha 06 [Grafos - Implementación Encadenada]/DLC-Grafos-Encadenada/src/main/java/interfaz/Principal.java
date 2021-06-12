package interfaz;

import soporte.*;

/**
 * Una clase para contener un main de prueba para la implementación de grafos
 * por listas de adyancencia.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */
public class Principal 
{
    public static void main(String args[])
    {
        UndirectedGraph <String> ug1 = new UndirectedGraph<>();
        ug1.add("a");
        ug1.add("b");
        ug1.add("c");
        ug1.add("d");
        ug1.addArc("a", "b", 2);
        ug1.addArc("a", "b", 2);  // paralelo: no debe permitirlo...
        ug1.addArc("b", "c", 3);
        ug1.addArc("b", "d", 1);
        ug1.addArc("d", "c", 4);
        System.out.println("Grafo 1 (no dirigido - sin arcos paralelos: ");
        System.out.println(ug1);
        System.out.println();
                
        UndirectedGraph <String> ug2 = new UndirectedGraph<>(true);
        ug2.add("a");
        ug2.add("b");
        ug2.add("c");
        ug2.add("d");
        ug2.addArc("a", "b", 2);
        ug2.addArc("a", "b", 2);  // paralelo: debe permitirlo... 
        ug2.addArc("b", "c", 3);
        ug2.addArc("b", "d", 1);
        ug2.addArc("d", "c", 4);
        System.out.println("Grafo 2 (no dirigido - con arcos paralelos: ");
        System.out.println(ug2);        
        System.out.println();

        DirectedGraph <String> dg1 = new DirectedGraph<>();
        dg1.add("a");
        dg1.add("b");
        dg1.add("c");
        dg1.add("d");
        dg1.addArc("a", "b", 2);
        dg1.addArc("a", "b", 2);  // paralelo: no debe permitirlo...
        dg1.addArc("c", "b", 3);
        dg1.addArc("b", "d", 1);
        dg1.addArc("d", "c", 4);
        System.out.println("Grafo 3 (dirigido - sin arcos paralelos: ");
        System.out.println(dg1);
        System.out.println();

        DirectedGraph <String> dg2 = new DirectedGraph<>(true);
        dg2.add("a");
        dg2.add("b");
        dg2.add("c");
        dg2.add("d");
        dg2.addArc("a", "b", 2);
        dg2.addArc("a", "b", 2);  // paralelo: debe permitirlo...
        dg2.addArc("c", "b", 3);
        dg2.addArc("b", "d", 1);
        dg2.addArc("d", "c", 4);
        System.out.println("Grafo 4 (dirigido - con arcos paralelos: ");
        System.out.println(dg2);
        
        DirectedGraph <String> dg3 = null;
        try
        {
            dg3 = (DirectedGraph<String>)dg2.clone();
        }
        catch(CloneNotSupportedException e)
        {
        }
        System.out.println("Grafo 5 (dirigido - con arcos paralelos [clonado del grafo 4]: ");
        System.out.println(dg3);
    }
}
