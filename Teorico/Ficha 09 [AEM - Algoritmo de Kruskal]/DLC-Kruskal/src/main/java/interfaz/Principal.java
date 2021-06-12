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
        UndirectedGraph<Integer> ug1 = new UndirectedGraph<>();
        ug1.add(1);
        ug1.add(2);
        ug1.add(3);
        ug1.add(4);
        ug1.add(5);
        ug1.add(6);
        ug1.add(7);
        ug1.add(8);
        ug1.add(9);
        ug1.add(10);
        ug1.add(11);
        ug1.add(12);
        ug1.add(13);

        ug1.addArc(1,2,5);
        ug1.addArc(1,3,3);
        ug1.addArc(3,5,1);
        ug1.addArc(4,7,2);
        ug1.addArc(7,5,3);
        ug1.addArc(2,6,2);
        ug1.addArc(6,8,1);
        ug1.addArc(1,9,7);
        ug1.addArc(3,10,5);
        ug1.addArc(4,12,8);
        ug1.addArc(8,13,3);
        ug1.addArc(1,2,5);
        ug1.addArc(13,11,3);
        ug1.addArc(9,2,3);
        ug1.addArc(11,7,1);
        ug1.addArc(1,13,5);
        
        System.out.println("Grafo 1 (no dirigido - sin arcos paralelos: ");
        System.out.println(ug1);
        System.out.println();
        
        System.out.println("Grafo 1: Valor del AEM (Prim): " + ug1.getMSTValue_Prim());
        System.out.println("Grafo 1: Valor del AEM (Kruskal): " + ug1.getMSTValue_Kruskal());        
    }
}
