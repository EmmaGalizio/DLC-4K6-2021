package compresor;

import java.util.ArrayList;

/**
 * Representa un arbol de Huffman, implementado sobre un arreglo.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Abril de 2013.
 */
public class HuffmanTree
{
      // todos los valores ASCII...
    public static final int MAXSYMBS = 256;
    
    // cantidad total de nodos del arbol si usamos todos los ASCII...
    public static final int MAXNODES = 2 * MAXSYMBS - 1;  
    
    // cantidad de simbolos originales: hojas del arbol
    private int hojas;                       
    
    // codigos de bits Huffman de cada simbolo
    private ArrayList < ArrayList <Byte> > codigos;                  
    
    // el arbol: los primeros t casilleros son las hojas
    private HuffmanTreeNode arbol[];         
    
    // los simbolos originales
    private byte simbolos[];                 
 
    /**
     *  Crea un arbol de Huffman para 256 simbolos (todo el espectro ASCII).
     */ 
    public HuffmanTree ()
    {
        this(MAXSYMBS); 
    }
 
    /**
     *  Crea un arbol de Huffman para una tabla de n simbolos de entrada.
     *  @param n la cantidad de simbolos en el alfabeto de entrada.
     */ 
    public HuffmanTree(int n)
    {
        // cantidad de simbolos (o sea, hojas) a emplear...
        hojas = n;                 
        
        // cantidad total de nodos que tendra el arbol...
        int t = 2 * hojas - 1;    

        // crear la lista para almacenar los codigos de bits Huffman de cada simbolo...
        codigos = new ArrayList < ArrayList <Byte> > (hojas);
        for(int i = 0; i < hojas; i++)
        {
           codigos.add(new ArrayList <Byte> ());
        }
        
        // crear el arbol de Huffman: un arreglo de t objetos HuffmanTreeNode...
        arbol = new HuffmanTreeNode[t];
        for(int i = 0; i < t; i++)
        {
           arbol[i] = new HuffmanTreeNode();
        }

        // crear el arreglo para guardar los simbolos originales...
        simbolos = new byte[hojas];                
    }

    /**
     *  Devuelve la cantidad de simbolos que esta manejando el arbol.
     *  @return la cantidad de simbolos (hojas) que tiene el arbol.
     */
    public int length() 
    { 
        return hojas; 
    }

    /**
     *  Agrega un simbolo al arbol.
     *  @param x el valor del simbolo a agregar.
     *  @param f la frecuencia del simbolo.
     *  @param i numero de orden que tendra el simbolo dentro del arbol.
     */
    public void setNode(byte x, int f, int i)
    {
         setSymbol(x, i);
         setFrequency(f, i);   
    }
    
    /**
     * Agrega un nodo al arbol, tomando todos los datos de un nodo nh ya creado.
     * param nh el nodo ya creado a agregar en el arbol.
     * @param i el indice de la casilla donde se agrega el nuevo nodo.
     */
    public void setNode(HuffmanTreeNode nh, int i)
    {
        if(nh != null){ arbol[i] = nh; }
    }

    /**
     *  Accede al valor de un simbolos especifico.
     *  @param i el numero del orden del simbolo a acceder.
     *  @return el valor del simbolo en la posicion i.
     */
    public byte getSymbol(int i)
    { 
        return simbolos[i];
    }
    
    /**
     *  Cambia el valor del simbolo cuyo numero de orden es i.
     *  @param x el nuevo valor del simbolo.
     *  @param i el indice o numero de orden del simbolo a cambiar.
     */
    public void setSymbol(byte x, int i)
    { 
        simbolos[i] = x;
    }

    /**
     *  Obtiene la frecuencia del simbolo cuyo numero de orden es i.
     *  @param i numero del orden del simbolo cuya frecuencia se pide.
     *  @return el valor de la frecuencia de ese simbolo.
     */
    public int getFrequency(int i)
    { 
        return arbol[i].getFrequency(); 
    }
    
    /**
     *  Cambia la frecuencia de un simbolo en el arbol.
     *  @param f la nueva frecuencia.
     *  @param i el numero de orden del simbolo cuya frecuencia se quiere cambiar.
     */
    public void setFrequency(int f, int i)
    { 
        arbol[i].setFrequency(f);
    }

    /**
     *  Obtiene el codigo Huffman del simbolo cuyo numero de orden es i.
     *  @param i el numero de orden del simbolo cuyo codigo se quiere acceder.
     *  @return la lista de bits que representa al codigo Huffman del simbolo pedido.
     */
    public ArrayList <Byte> getCodeByIndex(int i)
    { 
        return codigos.get(i); 
    }
    
   /**
     * Obtiene el codigo Huffman de un simbolo x dado. Si x no es un simbolo
     * valido para el arbol, el metodo retorna null.
     * @param x el simbolo cuyo codigo Huffman se quiere acceder.
     * @return una referencia al objeto que contiene al codigo.
     */
    public ArrayList <Byte> getCodeBySymbol(byte x)
    {
        ArrayList <Byte> r = null;
        int i = search(x);
        if(i != -1) { r = getCodeByIndex(i); }
        return r;
    }
    
    /**
     *  Cambia el codigo Huffman de un simbolo, salvo que el parametro sea null, en cuyo
     *  caso deja el codigo como estaba.
     *  @param c una referencia a la lista de bits con el nuevo codigo Huffman.
     *  @param i el numero de orden del simbolo cuyo codigo se desea cambiar.
     */
    public void setCodeByIndex(ArrayList <Byte> c, int i)
    { 
        if( c != null ) { codigos.set(i, c); }
    }
    
    /**
     * Cambia el codigo Huffman de un simbolo x dado. Si x no es un simbolo 
     * valido para el arbol, el metodo no hace nada.
     * @param c la lista con el nuevo codigo Huffman para x.
     * @param x el simbolo cuyo codigo Huffman se desea cambiar.
     */
    public void setCodeBySymbol(ArrayList <Byte> c, byte x)
    {
        if( c != null )
        {
            int i = search(x);
            if(i != -1) { codigos.set(i, c); }  
        }
    }
    
    /**
     *  Devuelve el arreglo que representa al Arbol de Huffman.
     *  @return una referencia al arreglo que contiene al Arbol de Huffman.
     */
    public HuffmanTreeNode[] toArray()
    {
        return arbol;   
    }
    
    /**
     * Contruye el arbol completo, y almacena los codigos de Huffman de cada
     * simbolo en la lista respectiva.
     */
    public void encode()
    {
        makeTree();
        makeCodes();
    }

    /**
     * Construye el arbol completo, con todos sus nodos ya enlazados. El arbol
     * queda listo para ser recorrido y obtener a partir de el los codigos de
     * Huffman de cada simbolo representado.
     */
    public void makeTree()
    {
        // crear una cola de prioridades ordenada por las frecuencias de los simbolos...
        Heap cp = new Heap();
        for(int i = 0; i < hojas; i++)
        {
            Frequency p = new Frequency(this.getFrequency(i), i);
            cp.add(p);
        }
                
        // p apunta al siguiente nodo libre disponible en el arbol... 
        for(int p = hojas; p < 2 * hojas - 1; p++)
        {
            // ...tomar los descriptores de frecuencia p1 y p2 con menor frecuencia de la cola de prioridad...
            Frequency p1 = (Frequency) cp.remove();
            Frequency p2 = (Frequency) cp.remove();
    
            // ...ajustar ambos nodos como hijos del nuevo nodo p...
            int ip1 = p1.getIndice();
            int ip2 = p2.getIndice();
            arbol[ip1].setFather(p);
            arbol[ip1].left(true);
            arbol[ip2].setFather(p);
            arbol[ip2].left(false);
            
            // calcular la frecuencia nodo union que se acaba de crear...
            int f = arbol[ip1].getFrequency() + arbol[ip2].getFrequency();
            
            // guardar esa frecuencia en el nodo union..
            arbol[p].setFrequency(f);
            
            // guardar los indices de sus hijos...
            arbol[p].setLeft(ip1);
            arbol[p].setRight(ip2);
            
            // agregar el descriptor de frecuencia del nodo union a la cola de prioridad...
            Frequency nf = new Frequency (f, p);
            cp.add(nf);
        }
    }
    
    /**
     * Obtiene el codigo de Huffman de cada simbolo en el arbol, y almacena esos
     * codigos en la lista respectiva.
     */
    public void makeCodes() 
    {
        // indice del nodo raiz del arbol (siempre queda en el ultimo casillero)...
        int ir = arbol.length - 1;
    
        // ... armar los codigos Huffman desde el arbol y guardarlos en la lista de codigos...
        for(int i = 0; i < hojas; i++)
        {
            int p = i;
            while(p != ir)
            {
                // ... obtener la lista con el codigo Huffman del nodo i...
                ArrayList <Byte> ch = codigos.get(i);
                
                // si p es un hijo izquierdo, guardar un cero al principio de la lista... sino guardar un uno...
                if(arbol[p].isLeft()) { ch.add(0, (byte)0); }
                else { ch.add(0, (byte)1); }
                
                // subir al nodo padre en el arbol...
                p = arbol[p].getFather();
            }
        }
    }

    /**
     *  Obtiene una tabla con los simbolos y sus codigos Huffman, en forma de String.
     *  @return un string con la tabla de simbolos y sus codigos.
     */
    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder("Simbolos Codificados");
 
        for(int i = 0; i < hojas; i++)
        {
            res.append("\nSimbolo: ").append((char) simbolos[i]).append("\tCodigo: ").append(codigos.get(i).toString());
        }
  
        return res.toString();
    }


    private int search(byte x)
    {
       for(int i = 0; i < hojas; i++)
       {
           if(x == simbolos[i]) { return i; }
       }
       return -1;
    }
}
