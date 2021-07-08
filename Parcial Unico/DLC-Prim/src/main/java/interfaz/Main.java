package interfaz;

import soporte.UndirectedGraph;

public class Main {

    public static void main(String[] args) {

        UndirectedGraph<String> ug1 = Main.getFirstGraph();
        System.out.println("Primer grafo (no conexo)");
        System.out.println("Cantidad de vertices original: "+ ug1.countNodes());
        System.out.println("Cantidad de arcos original: " + ug1.countEdges());
        System.out.println(ug1);
        System.out.println("===============================================");
        System.out.println("Arbol de expanción mínimo del primer gráfo");
        UndirectedGraph<String> aemUg1 = ug1.getMSTValue_Prim_NEW();
        System.out.println("Cantidad de vértices AEM: " + aemUg1.countNodes());
        System.out.println("Cantidad de arcos AEM: " + aemUg1.countEdges());
        System.out.println(aemUg1);
        //System.out.println("Peso AEM ug1: " + aemUg1.getMSTValue_Prim_NEW_Sum());

        System.out.println("==================================================");
        UndirectedGraph<Integer> ug2 = Main.getSecondGraph();
        System.out.println("Segundo grafo (conexo)");
        System.out.println("Cantidad de vertices original: "+ ug2.countNodes());
        System.out.println("Cantidad de arcos original: " + ug2.countEdges());
        System.out.println(ug2);
        System.out.println("===============================================");
        System.out.println("Arbol de expanción mínimo del segundo gráfo");
        UndirectedGraph<Integer> aemUg2 = ug2.getMSTValue_Prim_NEW();
        System.out.println("Cantidad de vértices AEM: " + aemUg2.countNodes());
        System.out.println("Cantidad de arcos AEM: " + aemUg2.countEdges());
        System.out.println(aemUg2);
        //System.out.println("Peso AEM ug1: " + aemUg2.getMSTValue_Prim_NEW_Sum());

    }

    private static UndirectedGraph<String> getFirstGraph(){
        UndirectedGraph <String> ug1 = new UndirectedGraph<>(true);
        ug1.add("a");
        ug1.add("b");
        ug1.add("c");
        ug1.add("d");
        ug1.add("e");
        ug1.add("f");
        ug1.add("g");
        ug1.add("h");
        ug1.add("i"); //Nodo ahislado
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
        ug1.addArc("d", "f", 2);
        ug1.addArc("e", "f", 1);
        ug1.addArc("g", "h", 2); // grafo no conexo...
        ug1.addArc("j", "k", 3);
        ug1.addArc("k", "l", 2);
        ug1.addArc("l", "p", 7);
        ug1.addArc("k", "j", 2);
        ug1.addArc("o", "r", 2);
        ug1.addArc("a", "q", 1);
        ug1.addArc("n", "q", 2);
        ug1.addArc("r", "o", 2);
        ug1.addArc("p", "q", 3);
        ug1.addArc("o", "r", 2);
        ug1.addArc("r", "q", 2);
        ug1.addArc("p", "b", 2);
        ug1.addArc("p","m",2);
        return ug1;

    }

    private static UndirectedGraph<Integer> getSecondGraph(){

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

        return ug1;
    }

}
