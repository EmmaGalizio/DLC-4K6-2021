package interfaz;

import soporte.*;

/**
 * Una clase para contener un main de prueba para la implementación de grafos
 * por listas de adyacencia.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */
public class Principal 
{
    public static void main(String args[])
    {
        UndirectedGraph <String> ug1 = new UndirectedGraph<>(true);
        ug1.add("a");
        ug1.add("b");
        ug1.add("c");
        //ug1.add("y");
        ug1.add("d");
        ug1.add("e");
        ug1.add("f");
        ug1.add("g");
        ug1.add("h");

        ug1.add("j");
        ug1.add("k");
        ug1.add("l");
        ug1.add("m");
        ug1.add("n");
        ug1.add("o");
        ug1.add("p");
        ug1.add("q");
        ug1.add("r");

        
        ug1.addArc("a", "b", 4);
        ug1.addArc("a", "d", 3);  
        ug1.addArc("a", "e", 2);
        ug1.addArc("b", "c", 1);
        ug1.addArc("b", "d", 2);
        ug1.addArc("c", "d", 4);
        ug1.addArc("c", "f", 2);
        ug1.addArc("c", "f", 2); // probar con este arco paralelo... ok!!
        ug1.addArc("d", "e", 2);
        ug1.addArc("d", "f", 3);
        ug1.addArc("e", "f", 1);
        ug1.addArc("g", "h", 2); // grafo no conexo...
        //ug1.addArc("g", "y", 2);
        ug1.addArc("j", "k", 2);
        ug1.addArc("k", "l", 2);
        ug1.addArc("l", "j", 2);
        ug1.addArc("l", "j", 2);

        ug1.addArc("m", "q", 2);
        ug1.addArc("n", "q", 2);
        ug1.addArc("r", "o", 2);
        ug1.addArc("p", "r", 2);
        ug1.addArc("o", "r", 2);
        ug1.addArc("r", "q", 2);
        ug1.addArc("p", "n", 2);

        System.out.println("Grafo 1 (no dirigido - sin arcos paralelos: ");
        System.out.println(ug1);
        System.out.println();


        System.out.println("Grafo 1: Valor del AEM (Prim): " + ug1.getMSTValue_Prim());
        System.out.println("Grafo 1: Valor del AEM (Prim New): " + ug1.getMSTValue_Prim_NEW());

    }
}
