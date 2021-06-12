package inicio;

public class Principal
{
    public static void main( String args[] )
    {
        //**** secuencia 1: asignaciones de valores octal y hexa
        System.out.println("************************Secuencia 1: asignaciones de valores octal y hexa...");
        short a = 0x28f1;
        short b = 0341;
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        
        // ninguno de los siguientes compila:
        // byte x = 200;  	// no compila: 200 es mayor a 127
        // byte y = 0777;       // no compila: 0777 en octal es 511 en base 10, y es mayor a 127
        // byte z = 0xF1;  	// no compila: 0xF1 en hexa es 241 en base 10, y es mayor a 127
        
        //**** secuencia 2: conversi�n a cadenas binaria, hexa y octal
        System.out.println("\n************************Secuencia 2: conversi�n a cadenas binaria, hexa y octal...");
        //a = Short.MAX_VALUE; //21439;  // un n�mero cualquiera en base 10
        System.out.println("Valor en base 10: " + a);
        System.out.println("Valor en base 2:  " + Integer.toBinaryString(a));
        System.out.println("Valor en base 8:  " + Integer.toOctalString(a));
        System.out.println("Valor en base 16: " + Integer.toHexString(a));
        System.out.println("Valor en base 13: " + Integer.toString(a, 13));
        
        //**** secuencia 3: asignaci�n de secuencias en variables m�s grandes
        System.out.println("\n************************Secuencia 3: asignaci�n de secuencias en variables m�s grandes");
        short z = 0x00f1;
        System.out.println("Primer valor de z (en base 10):  " + z);   
        System.out.println("Valor z (en base 2):  " + Integer.toBinaryString(z));
        z = 0xf1;  
        System.out.println("Segundo valor de z (en base 10): " + z);   
        System.out.println("Valor z (en base 2):  " + Integer.toBinaryString(z));
        z = 241;  
        System.out.println("Tercer valor de z (en base 10):  " + z);   
        System.out.println("Valor z (en base 2):  " + Integer.toBinaryString(z));
        
        //**** secuencia 4: n�meros negativos y complemento a dos
        System.out.println("\n************************Secuencia 4: n�meros negativos y complemento a dos");
        byte n = 127;
        System.out.println("Valor de n (positivo, en base 10):  " + n);   
        System.out.println("Valor n (en base 2):  " + Integer.toBinaryString(n));
        n = -5;
        System.out.println("Valor de n (negativo, en base 10):  " + n);   
        System.out.println("Valor n (en base 2):  " + Integer.toBinaryString(n));
        byte v1 = 5;
        byte v2 = 127;
        byte v3 = (byte)(v1 + v2);
        short v4 = (short)(v1 + v2);
        System.out.println("Suma de " + v1 + " y " + v2 + " (precisi�n byte):  " + v3);
        System.out.println("En binario (para ver el desborde): " + Integer.toBinaryString(v3));
        System.out.println("Suma de " + v1 + " y " + v2 + " (precisi�n short): " + v4);
        System.out.println("En binario: " + Integer.toBinaryString(v4));

        //**** secuencia 5: operadores a nivel de bits
        System.out.println("\n************************Secuencia 5: operadores a nivel de bits");
        int n1 = 0x4C; // 0100 1100
        int n2 = 0x2F; // 0010 1111
        int n3 = n1 & n2;
        int n4 = n1 | n2;
        int n5 = n1 ^ n2;
        int n6 = ~n1;
        int n7 = n1 << 3;
        int n8 = n6 >>> 3;
        int n9 = n6 >> 3;
        System.out.println("n1: " + n1);
        System.out.println("n1 en binario: " + Integer.toBinaryString(n1));
        
        System.out.println("\nn2: " + n2);
        System.out.println("n2 en binario: " + Integer.toBinaryString(n2));
        
        System.out.println("\nn1 & n2: " + n3);
        System.out.println("n1 & n2 en binario: " + Integer.toBinaryString(n3));
        
        System.out.println("\nn1 | n2: " + n4);
        System.out.println("n1 | n2 en binario: " + Integer.toBinaryString(n4));
        
        System.out.println("\nn1 ^ n2: " + n5);
        System.out.println("n1 ^ n2 en binario: " + Integer.toBinaryString(n5));
        
        System.out.println("\n~n1: " + n6 + " (n6)");
        System.out.println("~n1 en binario: " + Integer.toBinaryString(n6)+ " (n6)");

        System.out.println("\nn1 << 3: " + n7);
        System.out.println("n1 << 3 en binario: " + Integer.toBinaryString(n7));
        
        System.out.println("\nn6 >>> 3: " + n8);
        System.out.println("n6 >>> 3 en binario: " + Integer.toBinaryString(n8) + " (son 29 bits en pantalla...)" );
   
        System.out.println("\nn6 >> 3: " + n9);
        System.out.println("n6 >> 3 en binario: " + Integer.toBinaryString(n9)+ " son 32 bits en pantalla...)" );
    }
}
