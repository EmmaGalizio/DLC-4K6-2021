package interfaz;

import soporte.Grafo;

public class PruebaTopologico {

    public static void main(String[] args) {
        Grafo grafo = new Grafo(6);
        grafo.setNodo("a");
        grafo.setNodo("b");
        grafo.setNodo("c");
        grafo.setNodo("d");
        grafo.setNodo("e");
        grafo.setNodo("f");

        grafo.unir("a","b",4);
        grafo.unir("a","d",3);
        grafo.unir("a","e",2);
        grafo.unir("b","c",1);
        grafo.unir("b","d",2);
        grafo.unir("c","f",2);
        grafo.unir("c","d",4);
        grafo.unir("d","e",2);
        grafo.unir("d","f",3);
        grafo.unir("e","f",1);
        //grafo.unir("f","c",2);

        System.out.print("[");
        for(Object obj : grafo.ordenTopologico()){
            System.out.print(" " + obj);
        }
        System.out.println("]");
    }
}
