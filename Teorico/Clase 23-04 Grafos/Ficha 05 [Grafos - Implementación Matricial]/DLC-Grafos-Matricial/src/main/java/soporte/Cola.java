package soporte;

/**
 *  Una cola genérica.
 *  @author  Ing. Valerio Frittelli.
 *  @version Septiembre de 2007.
 */
public class Cola
{
      private Nodo frente;
      private Nodo fondo;
      
      /** 
       * Constructor por defecto
       */
      public Cola ()
      {
        frente = null;
        fondo  = null;
      }
      
      /**
       *  Acceso a la dirección del primer Nodo
       *  @return dirección del primer Nodo
       */
      public Nodo getFrente()
      {
         return frente;   
      }
      
      /**
       *  Acceso a la dirección del último Nodo
       *  @return dirección del último Nodo
       */
      public Nodo getFondo()
      {
         return fondo;   
      }

      /**
       * Determina si la cola está vacía
       * @return true: está vacía  -  false: no está vacía
       */
      public boolean vacia()
      {
         return (frente == null);    
      }
      
      /**
       *  Inserta un nodo al final de la cola
       *  @param x el objeto a almacenar en el nuevo nodo
       */
      public void insertar(Comparable x)
      {
        if (x!=null)
        {
           if(frente!=null && x.getClass()!=frente.getInfo().getClass()) { return; }
           
           Nodo p = new Nodo(x, null);
           if(fondo == null) { frente = p; }
           else { fondo.setNext(p); } 
           fondo = p;
        }
      } 
      
      /**
       *  Elimina el primer nodo
       *  @return una referencia al info del nodo eliminado, o null la cola estaba vacía
       */
      public Comparable borrar() 
      {
        Comparable p = null;
        if(frente != null)
        {
           p = frente.getInfo();
           frente = frente.getNext();
           if(frente == null) { fondo = null; }
        }  
        return p;
      } 
      

      /**
       *  Redefine el metodo toString
       *  @return el contenido de la lista convertido a String
       */
      @Override
      public String toString()
      {
         Nodo p = frente;
         StringBuilder res = new StringBuilder("Contenido: ");
         while(p != null)
         {
            res.append( p.toString() );
            p = p.getNext();
         }
         return res.toString();
      }
}
