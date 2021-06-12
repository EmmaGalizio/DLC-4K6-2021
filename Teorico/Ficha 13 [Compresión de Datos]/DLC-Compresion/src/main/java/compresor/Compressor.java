package compresor;

/**
 * Un compresor de archivos basado en el Algoritmo de Huffman.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Abril de 2013.
 */
import java.io.*;
import java.util.ArrayList;
public class Compressor
{
    // la cantidad final de simbolos / bytes diferentes del archivo de entrada.
    private int n;

    /**
     *  Crea un compresor.
     */
    public Compressor()
    {
       n = 0;
    }

    /**
     *  Comprime un archivo usando un Arbol de Huffman para determinar el codigo
     *  de bit de cada simbolo. Genera un archivo comprimido con el mismo nombre
     *  que el original, pero con extension .huf
     *  @param fileName el nombre del archivo a comprimir
     */
    public void compress(String fileName)
    {
        try
        {  
            //obtener el nombre del archivo de entrada, sin la extension...
            String nombre = fileName.substring( 0, fileName.lastIndexOf(".") );
            
            // abrir los archivos y limpiar el contenido del archivo de salida...
            File f1 = new File(fileName);
            File f2 = new File(nombre + ".huf");            
            RandomAccessFile fuente     = new RandomAccessFile (f1, "r");
            RandomAccessFile comprimido = new RandomAccessFile (f2, "rw");
            comprimido.setLength(0);
            
            // determinar la frecuencia de aparición por byte en el archivo fuente...
            int c[] = new int[256];  
            while(fuente.getFilePointer() < fuente.length())
            {
                 int b = fuente.readUnsignedByte();  
                 c[b]++;   
            }
                       
            // determinar cuantos simbolos / bytes diferentes habia...
            n = 0;
            for(int i = 0; i < 256; i++) 
            { 
                if( c[i] != 0 ) { n++; }
            }

            // inicializar un Arbol de Huffman con espacio para n simbolos...
            HuffmanTree ht = new HuffmanTree(n); 
            int ind = 0;
            for(int i = 0;  i < 256;  i++)
            {
                  if(c[i] != 0)
                  {
                      ht.setNode((byte)i, c[i], ind);
                      ind++;
                  }
            }
            
            // crear el arbol y obtener el codigo Huffman de cada simbolo / byte...
            ht.encode();

            // guardar en el archivo de salida metadatos para el descompresor...
                        
            // ...guardar el nombre y la extension del archivo original...
            comprimido.writeUTF(fileName);
            
            // ...guardar la longitud en bytes del archivo original...
            long ta = fuente.length();  
            comprimido.writeLong(ta);
            
            // ...guardar la cantidad de simbolos (o sea, la cantidad de hojas del arbol)...
            comprimido.writeInt(n);
            
            // ...guardar la tabla de bytes / simbolos tal como esta en el arbol...
            for(int i = 0; i < n; i++)
            {
                byte simbolo = ht.getSymbol(i);
                comprimido.writeByte(simbolo);
            }

            // ...guardar ahora el arreglo que representa al arbol...
            HuffmanTreeNode a[] = ht.toArray();
            for(int i = 0; i < a.length; i++)
            {
                // ... y por cada nodo, guardar todos sus datos...
                comprimido.writeInt(a[i].getFrequency());
                comprimido.writeInt(a[i].getFather());
                comprimido.writeBoolean(a[i].isLeft());
                comprimido.writeInt(a[i].getLeft());
                comprimido.writeInt(a[i].getRight());
            }
            
            // comenzar el proceso de compresion (por fin...)
            int mascara = 0x00000080;  // el valor 0000 0000 1000 0000
            int salida  = 0x00000000;  // el valor 0000 0000 0000 0000
            int bit = 0;             // en que bit vamos?
            
            // procesar el archivo de entrada byte a byte, desde el inicio...
            fuente.seek(0);   
            while(fuente.getFilePointer() < fuente.length())
            {
                // ...leer un byte desde el archivo fuente...
                int b = fuente.readUnsignedByte();
                
                // ... pedir el codigo Huffman del byte / simbolo leido...
                ArrayList<Byte> hc = ht.getCodeBySymbol((byte)b);

                // ...analizar bit a bit el codigo de Huffman del simbolo...
                for(int i = 0; i < hc.size(); i++)
                {
                    if(hc.get(i) == 1)
                    {
                        // ...si era 1, bajarlo al byte de salida (si era cero, ya lo teníamos...)
                        salida = salida | mascara;
                    }   
                    
                    // ...correr el uno a la derecha en la mascara, rellenando con ceros a la izquierda...
                    mascara = mascara >>> 1;  
                    
                    // ...contar el bit procesado... 
                    bit++;
                    
                    // ...analizar si es hora de grabar el byte de salida...
                    if(bit == 8)
                    {
                        // ...se llenó el byte de salida... grabar el byte menos significativo...
                        comprimido.writeByte((byte)salida);
                        
                        // ... resetear variables de trabajo...
                        bit = 0;
                        mascara = 0x00000080;
                        salida  = 0x00000000;
                    }
                }
            }

            // ...controlar si el último byte de salida quedo incompleto...
            if (bit != 0) 
            {
                // ...grabar el byte menos significativo de la variable de salida...
                comprimido.writeByte((byte)salida); 
            }
            
            // cerrar archivos y terminar el proceso
            comprimido.close();
            fuente.close();
        }
        
        catch(IOException e)
        {
            System.out.println("Error de IO al comprimir: " + e.getMessage());   
        }
        
        catch(Exception e)
        {
            System.out.println("Error inesperado al comprimir: " + e.getMessage());
        }
    }

    /**
     *  Descomprime un archivo que fue comprimido en base a un Arbol de Huffman. 
     *  El archivo creado tendrá el mismo nombre que originalmente tenía. Se asume que 
     *  el archivo comprimido tiene extensión .huf (caso contrario, el metodo lanza
     *  una excepcion.
     *  @param fileName el nombre del archivo comprimido que se quiere descomprimir.
     */
    public void decompress(String fileName)
    {
        try
        {
            // ...controlar si el archivo comprimido tiene extension .huf...
            int pos = fileName.lastIndexOf(".");
            if(pos == -1) { throw new Exception ("El archivo no parece un archivo comprimido..."); }

            String ext = fileName.substring( pos + 1 );
            if( ext.compareTo("huf") != 0 ) { throw new Exception ("El archivo no tiene la extension huf..."); }
            
            // ...abrir el archivo comprimido...
            File f1 = new File(fileName);
            RandomAccessFile comprimido = new RandomAccessFile(f1, "r");    
            
            // ...recuperar el nombre del archivo original...
            String original = comprimido.readUTF();
            
            // ...crear un archivo vacio con el nombre del original...
            File f2 = new File(original);
            RandomAccessFile nuevo = new RandomAccessFile(f2, "rw");
            nuevo.setLength(0);
            
            // ...recuperar todos los datos que el compresor puso adelante...
            
            // ...empezar por la longitud del archivo original...
            long ta = comprimido.readLong();
            
            // ...luego la cantidad de simbolos de la tabla original (o sea, la cantidad de hojas)...
            n = comprimido.readInt();
            
            // ...crear de nuevo el Arbol de Huffman en memoria...
            HuffmanTree ht = new HuffmanTree(n);
            
            // ...recuperar uno a uno los simbolos originales, guardandolos de nuevo en el arbol...
            for(int i = 0; i < n; i++)
            {
                byte simbolo = comprimido.readByte();
                ht.setSymbol(simbolo, i);
            }
            
            // ...leer ahora los datos del arbol y reasignarlos...
            int t = n * 2 - 1;  // ...cantidad total de nodos del arbol...
            for(int i = 0; i < t; i++)
            {
                // ...por cada nodo, recuperar sus datos y volver a armar el arbol...
                int f  = comprimido.readInt();           // frecuencia
                int padre = comprimido.readInt();        // padre
                boolean left = comprimido.readBoolean(); // es izquierdo?
                int hi = comprimido.readInt();           // hijo izquierdo
                int hd = comprimido.readInt();           // hijo derecho
                HuffmanTreeNode nh = new HuffmanTreeNode(f, padre, left, hi, hd);
                ht.setNode(nh, i);
            }
            
            // ...volver a crear los codigos de Huffman de cada simbolo / byte...
            ht.makeCodes();
                       
            // ...obtener el arreglo que representa al arbol y el indice de la raiz...
            HuffmanTreeNode a[] = ht.toArray();
            int raiz =  a.length - 1;  
            
            // ...comenzar la fase de descompresion (por fin...)
            
            // ...ubicar un indice auxiliar en la raiz del arbol...
            int nodo = raiz;
            
            // ...contador de bytes que se llevan descomprimidos...
            long cb = 0;
            
            // ...leer y analizar byte por byte el archivo comprimido...
            while(comprimido.getFilePointer() < comprimido.length())
            {
                // ...leer un byte sin signo desde el archivo...
                int b = comprimido.readUnsignedByte();
                
                // ... iniciar la mascara para el analisis bit a bit...
                int mascara = 0x00000080;
                
                // ...y comenzar el analisis bit a bit...
                for(int bit = 0; bit < 8 && cb != ta; bit++)
                {
                    // ...consultar el valor del bit actual...
                    int aux = b & mascara;
                    
                    // ... y bajar en el arbol por la rama que corresponda...
                    if(aux == mascara)
                    {
                        // ...si el bit era un uno, bajar por la derecha...
                        nodo = a[nodo].getRight();    
                    }
                    else 
                    {
                        // ...si el bit era un cero, bajar por la izquierda...
                        nodo = a[nodo].getLeft();
                    }
                    
                    // ...correr el 1 a la derecha en la mascara...
                    mascara = mascara >>> 1;  

                    // ...controlar si se llego a una hoja (un simbolo original)...
                    if (a[nodo].getLeft() == -1)
                    {
                        // ...es una hoja... grabar el byte / simbolo que hay en ella...
                        byte salida = ht.getSymbol(nodo);
                        nuevo.writeByte(salida);
                        
                        // ...contar el byte que se acaba de recuperar...
                        cb++;

                        // ...y volver a la raiz del arbol...
                        nodo = raiz;
                    }
                }
            }
            
            // ...cerrar todo y terminar...
            nuevo.close();
            comprimido.close();
        }

        catch(IOException e)
        {
            System.out.println("Error de IO: " + e.getMessage());   
        }

        catch(Exception e)
        {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}
