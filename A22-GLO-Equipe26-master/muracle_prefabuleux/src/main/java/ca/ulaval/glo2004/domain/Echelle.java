package ca.ulaval.glo2004.domain;
import ca.ulaval.glo2004.gui.*;
import java.awt.*;
import java.io.Serializable;
/**
 * Classe Echelle qui nous redonne le facteur d'echelle pour le calcul des Polygones
 * @author equipe26
 */
public class Echelle implements Serializable{
    private PointImperial mTranslation;
    private double FacteurEchelle = 1; // Temporaire a changer lorsque Zoom fonctionnelle
    //private double FacteurEchellePrecedent;
    private int pixelParPouce;
    private static Echelle instance = new Echelle();
    private static final int SMALLESTFRACTION = 128; // mesure precise au 128e de pouce.
    //private static final double MMTOINCH = 25.4; 
    
    
    /*
    * Singleton on veut un seul facteur d'echelle et ce pour toute l'application
    * @return la seule instance de la classe Echelle.
    *
    */
    public static Echelle getInstance(){
        return instance;
    }
    /*
        Contructeur de la Classe Echelle. Doit etre prive car Singleton.
        On s'attends a ce qu'il n'y ait qu'une seul instance de Echelle.
    */
    private Echelle()
    {
        if (instance == null) {
        // 1 Pouce a l'ecran = 1 pied en mesure relle
        this.pixelParPouce = java.awt.Toolkit.getDefaultToolkit().getScreenResolution()/12;
        }
    }
    
    public int getPPI()
    {
        return this.pixelParPouce;
    }
    
    public void setPPI(int pPPI){
        this.pixelParPouce = pPPI;
    }
    
   public PointImperial pixelsVersImperial(int pixelX, int pixelY){
       MesureImperial mX = PixeltoMesureImperial(pixelX);
       MesureImperial mY = PixeltoMesureImperial(pixelY);
       return new PointImperial(mX, mY);
    }
   
   private MesureImperial PixeltoMesureImperial(int pPixel){
       float fImperial = (float) (pPixel/pixelParPouce*FacteurEchelle);
       int pEntiere = (int) Math.floor(fImperial);
       int numerateur = (int) Math.round((fImperial-pEntiere)*SMALLESTFRACTION);
       int i = 0;
       while(numerateur%2==0){
           numerateur = numerateur/2;
           i++;
       }
       int denominateur = SMALLESTFRACTION/(int)Math.pow(2,i);
       return new MesureImperial(pEntiere,numerateur, denominateur);
   }
   
   public Point imperialVersPixel(MesureImperial pX, MesureImperial pY){
      int pixelX = (int) ((pX.getEntier()+(pX.getNumerateur()/pX.getDenominateur()))*pixelParPouce/FacteurEchelle);
      int pixelY = (int) ((pY.getEntier()+(pY.getNumerateur()/pY.getDenominateur()))*pixelParPouce/FacteurEchelle);
      return new Point(pixelX, pixelY);
   } 
}

    
