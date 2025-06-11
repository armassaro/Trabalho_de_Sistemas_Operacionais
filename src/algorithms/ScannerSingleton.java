package algorithms;

import java.util.Scanner;

/**
 *
 * @author armassaro
 */
// Coloca um único Scanner para o sistema inteiro, utilizando de Singleton
public abstract class ScannerSingleton {
    private static Scanner s = new Scanner(System.in);
    
    public static Scanner getInstance() { 
        return s;
    }
}
