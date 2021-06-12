package interfaz;

/**
 * Contiene un main para testear el proceso de compresion.
 *
 * @author Ing. Valerio Frittelli.
 * @version Abril de 2013.
 */
import compresor.Compressor;
public class Principal
{
    public static void main (String args[])
    {
             Compressor compresor = new Compressor();      
             System.out.println("Prueba de Compresion y Descompresion");

             int op;
             do
             {
                 System.out.println("1. Compresion de un archivo...");
                 System.out.println("2. Descompresion de un archivo...");
                 System.out.println("3. Salir");
                 System.out.print("\n\t\tIngrese opcion: ");
                 op = Consola.readInt();
                 
                 switch(op)
                 {
                     case 1: compresor.compress(".//test-files//1k1paisaje.jpg");
                             System.out.println("Hecho...");
                             break;
                             
                     case 2: compresor.decompress(".//test-files//1k1paisaje.huf");
                             System.out.println("Hecho...");
                             break;
                             
                     case 3: ;
                 }
             }
             while(op != 3);
    }
}
