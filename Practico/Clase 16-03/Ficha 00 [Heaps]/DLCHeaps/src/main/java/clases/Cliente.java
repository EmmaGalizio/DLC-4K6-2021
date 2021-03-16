/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author anastrub
 */
public class Cliente implements Comparable<Cliente> 
{
    
    private int id;
    
    public Cliente(int i)
    {
        id = i;
    }
    
    @Override
    public int compareTo(Cliente x)
    {
        return this.id - x.id;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id = " + id + '}';
    }
}
