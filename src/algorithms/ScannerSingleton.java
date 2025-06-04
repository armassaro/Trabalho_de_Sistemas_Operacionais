package algorithms;

import java.util.Scanner;

/**
 *
 * @author armassaro
 */
public abstract class ScannerSingleton {
    private static Scanner s = new Scanner(System.in);
    
    public static Scanner getInstance() { 
        return s;
    }
}
