package interfaz;

import soporte.UnionFind;

/**
 * Un main para testear la estructura UnionFind.
 * @Ing. Valerio Frittelli.
 * @version Abril de 2014.
 */
public class Principal 
{
    public static void main(String args[])
    {
        UnionFind uf = new UnionFind(6);
        
        uf.union(0, 2);
        uf.union(1, 3);
        uf.union(4, 5);
        System.out.println("Cantidad de grupos luego de 3 uniones: " + uf.countGroups());
        System.out.println("Elemento 5 -> lider: " + uf.find(5));
        System.out.println("Contenido completo hasta aquí: ");
        System.out.println(uf);
        System.out.println();
        
        uf.union(2, 3);
        uf.union(3, 5);
        System.out.println("Cantidad de grupos luego de 5 uniones: " + uf.countGroups());
        System.out.println("Elemento 5 -> lider: " + uf.find(5));
        System.out.println();
        
        System.out.println("Contenido completo final: ");
        System.out.println(uf);
    }
}
