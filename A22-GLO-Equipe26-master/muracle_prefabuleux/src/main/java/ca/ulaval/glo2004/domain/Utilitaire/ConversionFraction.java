/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain.Utilitaire;

/**
 *
 * @author jc_ho
 */
public class ConversionFraction {
    
    public ConversionFraction ()
    {
    }
    
    static boolean estPuissanceDeDeux (int n)
    {
        if (n <= 0)
            return false;
 
        while (n != 1) {
            if (n % 2 != 0)
                return false;
            n = n / 2;
        }
        return true;
    }
    
    public String conversionInputString(String longueurPo)
    {
        int partieEntier =0;
        String[] listeNumber = longueurPo.split("\\s+|/");
        partieEntier = Integer.parseInt(listeNumber[0]);
            
        return String.valueOf(partieEntier);
    }
    
}
