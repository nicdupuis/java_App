/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;

import java.io.Serializable;

/**
 *
 * @author equipe26
 */
public class MesureImperial implements Serializable{
    private int entier;
    private int numerateur;
    private int denominateur;
    
    /*
    *
    * Constructeur de MesureImperial
    *
    */
    public MesureImperial(int partieEntiere, int numerateur, int denominateur){
    this.entier = partieEntiere;
    this.numerateur = numerateur;
    this.denominateur = denominateur;
    
    }
    /*
    * constructeur Copie
    * @param Le point a copier
    */
    public MesureImperial(MesureImperial pMesure){
        this.entier = pMesure.getEntier();
        this.numerateur = pMesure.getNumerateur();
        this.denominateur = pMesure.getDenominateur();
        
    }
    
    public MesureImperial(float mesure){}
 
    public int getEntier () {
        return this.entier;
    }
    
    public int getNumerateur () {
        return this.numerateur;
    }
    
    public int getDenominateur () {
        return this.denominateur;
    }
    
    public void setEntier(int entier) {
        this.entier = entier;
    }
    
    public void setNumerateur(int numerateur) {
        this.numerateur = numerateur;
    }
    
    public void setDenominateur(int denominateur) {
        this.denominateur = denominateur;       
    }
    
        
    public static MesureImperial add(MesureImperial m1, MesureImperial m2){
        int newEntier = m1.getEntier()+m2.getEntier();
        int newDenominteur = m1.getDenominateur()*m2.getDenominateur();
        int newNumerateur = m1.getNumerateur()*m2.getDenominateur()+m2.getNumerateur()*m1.getDenominateur();
        int gcd = gcd(newDenominteur, newNumerateur);
        newDenominteur = newDenominteur/gcd;
        newNumerateur = newNumerateur/gcd;
        return new MesureImperial(newEntier,newNumerateur,newDenominteur);
    }
    
    public static MesureImperial substract(MesureImperial m1, MesureImperial m2){
        int newEntier = m1.getEntier()-m2.getEntier();
        int newDenominteur = m1.getDenominateur()*m2.getDenominateur();    
        int newNumerateur =  m1.getNumerateur()*m2.getDenominateur()-m2.getNumerateur()*m1.getDenominateur();
        //simplification de la fraction. 
        int gcd = gcd(newDenominteur, newNumerateur);
        newDenominteur = newDenominteur/gcd;
        newNumerateur = newNumerateur/gcd;
        return new MesureImperial(newEntier,newNumerateur,newDenominteur);
    }
    
    //Calcul d'aire 
    public static float multiply(MesureImperial m1, MesureImperial m2){
     return (m1.getEntier()+ (m1.numerateur / m1.denominateur)) *(m2.getEntier()+ (m2.numerateur / m2.denominateur));
    }
    
   private static int gcd(int a, int b) {
       if (b==0) return a;
       return gcd(b,a%b);
   }
   
   public String toString(MesureImperial toConvert)
   {
       String converti = "" + toConvert.entier + " " + toConvert.numerateur + "/" + toConvert.denominateur;
       return converti;
   }
   
}
