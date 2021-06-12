package interfaz;

/**
 * Contiene el main para testear el arbol de Huffman.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Abril de 2013.
 */

import modelo.HuffmanTree;
public class Principal
{
    private static HuffmanTree t;
    
    public static void cargar () 
    {
        int n = t.length();
    
        for(int i = 0; i<n; i++)
        {
           System.out.print("Simbolo[" + i + "]: ");
           char x = Consola.readChar();
           
           System.out.print("Frecuencia: ");
           int f = Consola.readInt();
           
           byte b = (byte)x;
           t.setNode(b,f,i);
        }
    }

    public static void main (String args[])
    {
             int op;
             System.out.print("Cuantos simbolos tiene la tabla?: ");
             int n = Consola.readInt();
             t = new HuffmanTree(n);
             
             do 
             {
                  System.out.print("\nArbol de Huffman");
                  System.out.print("\n1. Cargar simbolos en el arbol");
                  System.out.print("\n2. Calcular codigos de Huffman de cada simbolo");
                  System.out.print("\n3. Mostrar simbolos y sus codigos Huffman");
                  System.out.print("\n4. Salir");
                  System.out.print("\n\t\tIngrese: ");
                  op = Consola.readInt();
                  
                  switch(op)
                  {
                     case 1: 
                             cargar();
                             break;
            
                     case 2: 
                             t.encode();
                             System.out.println("Hecho... Codigos de Huffman generados");
                             break;
            
                     case 3: 
                             System.out.print("\n\nTabla de simbolos:\n\n");
                             System.out.println(t.toString());
                             break;
                         
                     case 4: ;
                  }
             }
             while (op != 4);
    } 
}
