package soporte;

/**
 *  Esta clase se usa para representar un arco en un grafo. Cada objeto de esta clase almacena un valor que será 
 *  el "peso" del arco (y que simplemente suponemos que es un valor numérico) En implementaciones más detalladas,
 *  el peso de un arco puede ser definido en si mismo como un objeto complejo.
 *  @author Ing. Valerio Frittelli.
 *  @version Noviembre de 2009.
 */

public class Arco 
{
   private int peso;
   private boolean existe;
   
   public Arco ( )
   {
   }
   
   public Arco ( int p )
   {
      peso = p;
      existe = true;
   }
   
   public int getPeso()
   {
      return peso;    
   }
   
   public void setPeso(int p)
   {
      peso = p;    
   }
   
   public boolean exists()
   {
       return existe;
   }
   
   public void set (boolean e)
   {
       existe = e;
   }
   
   @Override
   public String toString()
   {
      return "Peso: " + peso;    
   }
}
