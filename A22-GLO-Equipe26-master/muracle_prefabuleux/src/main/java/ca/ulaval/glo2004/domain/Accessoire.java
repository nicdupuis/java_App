/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;
 import java.awt.Rectangle;
 import java.awt.Color;
 import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
 import java.util.LinkedList;
 import java.util.List;
//import sun.security.ec.point.ProjectivePoint;
/**
 *
 * @author equipe26
 */
public class Accessoire extends Element implements Serializable{
    private boolean mPerceExterieur;
    private boolean mPerceInterieur;
    private MesureImperial mLargeur;
    private MesureImperial mHauteur;
    private Polygon mPolygon;
    private MesureImperial mMargeFenetre;
    private Point point;
    private Polygon PolygonePlan;
    private Polygon PolygoneElevationExterieur;
    private Polygon PolygoneElevationInterieur;
    private Polygon polygonSelect;
    private List<Polygon> listPoly;
    private String mNom;
    private Color color;
    private Type mType;
    private boolean mTrouDessus;
    private boolean selectionStatus;
    private int offsetSelectHauteur;
    private int offsetSelectLargeur;
      
    //Createur d'accessoires fait le bon appelle de constructeur, le premier call d'un constructeur doit etre le call Super  
    //et sans savoir le type 
    public static Accessoire AccessoireBuilder(String pNom, Point pPoint, Type pType, List<MesureImperial> listeMesures, Cote pCote){
        if (pType == Type.PRISE){
            if(listeMesures.isEmpty()){
                System.out.println("TestPrise");
        return new Accessoire(pPoint, new MesureImperial(2,1,2), new MesureImperial(3,0,1), new MesureImperial(0,0,1), "Prise électrique" , Color.BLUE , false, true, false, false, Type.PRISE);
            } else { return new Accessoire(pPoint,listeMesures.get(0), listeMesures.get(1), listeMesures.get(2), "Prise électrique", Color.BLUE,false,true,false,false, Type.PRISE);
        }
        }//End cas prise
        else if(pType== Type.FENETRE){
                if(listeMesures.isEmpty()){
        return new Accessoire(pPoint, new MesureImperial(60,0,2), new MesureImperial(40,0,1), new MesureImperial(0,1,8), "Fenêtre" , Color.LIGHT_GRAY ,true, true, false, false, Type.FENETRE);
            } else { return new Accessoire(pPoint,listeMesures.get(0), listeMesures.get(1), listeMesures.get(2), "Fenêtre", Color.LIGHT_GRAY,true,true,false,false, Type.FENETRE);
        }       
        }
         else if(pType== Type.RETOURDAIR){
                if(listeMesures.isEmpty()){
                   int y = 50 + pCote.getmSalle().getmHauteur().getEntier() - pCote.getmSalle().getDistanceSolRetourAir().getEntier();
                   Point pConstruit = new Point(pPoint.x,y);
                   MesureImperial largeurRetourAir= pCote.getmSalle().getHauteurRetourAir();
        return new Accessoire(pConstruit, largeurRetourAir , new MesureImperial(12,0,1), new MesureImperial(0,0,1), "Retour d'air" , Color.GREEN , false, true, true , false, Type.RETOURDAIR);
            } else { return new Accessoire(pPoint,listeMesures.get(0), listeMesures.get(1), listeMesures.get(2), "Retour d'air", Color.GREEN,false,true,true,false, Type.RETOURDAIR);
        }       
        }
        else if(pType== Type.PORTE){
                if(listeMesures.isEmpty()){
        return new Accessoire(pPoint, new MesureImperial(33,0,0), new MesureImperial(84,0,1), new MesureImperial(0,0,1), "Porte" , Color.RED , true, true, false, false, Type.PORTE);
            } else { return new Accessoire(pPoint,listeMesures.get(0), listeMesures.get(1), listeMesures.get(2), "Porte", Color.RED,true,true,false,false, Type.PORTE);
        }       
        }//fin cas Porte
        //type inconnu
        return null;
    } //EndBuilder
    
    //Constructeur d'accessoire
    //J'ai mis prive pour s'assurer de passer par le Builder. 
    private Accessoire(Point pPoint, MesureImperial pLargeur, MesureImperial pHauteur, MesureImperial pMargeFen, String pNom, Color pColor, boolean pPerceExt, boolean pPerceInt, boolean pTrouDessus, boolean pselectionStatus, Type type){
        super(pPoint);
        this.point = super.getPoint();
        this.color = pColor;
        this.mHauteur=pHauteur;
        this.mLargeur = pLargeur;
        this.mMargeFenetre=pMargeFen;
        this.mNom=pNom;
        this.mPerceExterieur=pPerceExt;
        this.mPerceInterieur=pPerceInt;
        this.mTrouDessus=pTrouDessus;
        this.selectionStatus=pselectionStatus;
        this.mType = type;
        this.offsetSelectHauteur = (int)Math.floor(mHauteur.getEntier() * 0.10);
        this.offsetSelectLargeur = (int)Math.floor(mLargeur.getEntier() * 0.10);
    }
    
//    private void CalculGeometrie(){
//        mPolygon = new Polygon();
//        Point point = super.getPoint();
//        
//        mPolygon.addPoint(100,100);
//        mPolygon.addPoint(105, 100);
//        mPolygon.addPoint(100, 105);
//        mPolygon.addPoint(105, 105);
//        
//    }

    public Polygon getPolygonSelected()
    {
        return polygonSelect;
    }
        
    public Type getType()
    {
        return mType;
    }
    
    public void setType(Type type)
    {
       this.mType = type;
    }
  
    
    public boolean getPerceExterieur()
    {
        return mPerceExterieur;
    }
    
    public boolean getPerceInterieur()
    {
        return mPerceInterieur;
    }
    
    public void setPerceInterieur(boolean perceInterieur)
    {
        this.mPerceInterieur = perceInterieur;
    }
    
    public void setPerceExterieur(boolean perceExterieur)
    {
        this.mPerceExterieur = perceExterieur;
    }
    
    
    @Override
    public void setLargeur(MesureImperial largeur)
    {
        this.mLargeur = largeur;
    }
    

    public void setHauteur(MesureImperial hauteur, Salle pSalle)
    {
        this.mHauteur = hauteur;
        if (this.mTrouDessus){
        pSalle.setHauteurRetourAir(hauteur);
        }
    }
    
    public void setHauteur(MesureImperial hauteur)
    {
        this.mHauteur = hauteur;
    }
    
    public MesureImperial getLargeur()
    {
        return mLargeur;
    }
    
    public MesureImperial getHauteur()
    {
        return mHauteur;
    }
    
    public String getName()
    {
        return mNom;
    }

    public void setName(String nom)
    {
        this.mNom = nom;
    }
    
    public boolean getTrouDessus()
    {
        return mTrouDessus;
    }

    public void setTrouDessus(boolean t)
    {
        this.mTrouDessus = t;
    }
    
    public MesureImperial getMargeFenetre()
    {
        return mMargeFenetre;
    }

    public void setMargeFenetre(MesureImperial margeFenetre)
    {
        this.mMargeFenetre = margeFenetre;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public void setColor(Color newColor)
    {
        color = newColor;
    }
    
    @Override
    public boolean contains(double x, double y)
    {
        return (xIsInsideItemWidth(x) && yIsInsideItemHeight(y));
    }
    
    private boolean xIsInsideItemWidth(double x)
    {
        //besoin de convertir MesureImperial avant d'utiliser ça
        return (x < point.getX() + (this.mLargeur.getEntier()/2)) && ( x > point.getX() - (this.mLargeur.getEntier()/2));
    }
    
    private boolean yIsInsideItemHeight(double y)
    {
        //besoin de convertir MesureImperial avant d'utiliser ça
        return (y < point.getY() + (this.mHauteur.getEntier()/2)) && ( y > point.getY() - (this.mHauteur.getEntier()/2));
    }
    
    public void translate(Point delta) 
    {
        this.point.x = (int) (this.point.x + delta.x);
        this.point.y = (int) (this.point.y + delta.y);
    }
    
    public void calculeDisposition(Cote pCote)
    {   //vider les liste contenant les polygones
//        if(listePolygonesElevationExterieur!=null){
//            listePolygonesElevationExterieur.clear();
//        }
//        if(listePolygonesElevationInterieur!=null){
//            listePolygonesElevationInterieur.clear();
//        }
//        if(listePolygonesPlan!=null){
//           listePolygonesPlan.clear();
//        }

        // calcul rectangle vue exterieur... il faut tenir compte du retour d'air qui doit etre tous a la meme hauteur. 
        
        if(mPerceInterieur){ 
            mPolygon = new Polygon();
            mPolygon.addPoint(this.point.x - this.mMargeFenetre.getEntier(), this.point.y + this.mMargeFenetre.getEntier());//bas gauche
            mPolygon.addPoint(this.point.x - this.mMargeFenetre.getEntier(), this.point.y - this.mHauteur.getEntier() - this.mMargeFenetre.getEntier());//haut gauche
            mPolygon.addPoint(this.point.x + this.mLargeur.getEntier() + this.mMargeFenetre.getEntier(), this.point.y - this.mHauteur.getEntier() - this.mMargeFenetre.getEntier());//haut droit
            mPolygon.addPoint(this.point.x + this.mLargeur.getEntier() + this.mMargeFenetre.getEntier(), this.point.y + this.mMargeFenetre.getEntier());//bas droit
            
            this.offsetSelectHauteur = (int)Math.floor(mHauteur.getEntier() * 0.10);
            this.offsetSelectLargeur = (int)Math.floor(mLargeur.getEntier() * 0.10);
        
            polygonSelect =  new Polygon();
            polygonSelect.addPoint(this.point.x - this.mMargeFenetre.getEntier() - offsetSelectLargeur, this.point.y + this.mMargeFenetre.getEntier() + offsetSelectHauteur);//bas gauche
            polygonSelect.addPoint(this.point.x - this.mMargeFenetre.getEntier() - offsetSelectLargeur, this.point.y - this.mHauteur.getEntier() - this.mMargeFenetre.getEntier() - offsetSelectHauteur);//haut gauche
            polygonSelect.addPoint(this.point.x + this.mLargeur.getEntier() + this.mMargeFenetre.getEntier() + offsetSelectLargeur, this.point.y - this.mHauteur.getEntier() - this.mMargeFenetre.getEntier() - offsetSelectHauteur);//haut droit
            polygonSelect.addPoint(this.point.x + this.mLargeur.getEntier() + this.mMargeFenetre.getEntier() + offsetSelectLargeur, this.point.y + this.mMargeFenetre.getEntier() + offsetSelectHauteur);//bas droit
        }//calcul rectangle vue interieur si necessaire.//le calcul est presentement n'importe quoi...
        if(mPerceExterieur){
            PolygoneElevationExterieur = new Polygon();
            PolygoneElevationExterieur.addPoint(this.point.x - (this.mLargeur.getEntier()+this.mMargeFenetre.getEntier())/2, this.point.y + (this.mHauteur.getEntier()+this.mMargeFenetre.getEntier())/2);//haut gauche
            PolygoneElevationExterieur.addPoint(this.point.x + (this.mLargeur.getEntier()+this.mMargeFenetre.getEntier())/2, this.point.y + (this.mHauteur.getEntier()+this.mMargeFenetre.getEntier())/2);//haut droit
            PolygoneElevationExterieur.addPoint(this.point.x + (this.mLargeur.getEntier()+this.mMargeFenetre.getEntier())/2, this.point.y - (this.mHauteur.getEntier()+this.mMargeFenetre.getEntier())/2);//bas droit
            PolygoneElevationExterieur.addPoint(this.point.x - (this.mLargeur.getEntier()+this.mMargeFenetre.getEntier())/2, this.point.y - (this.mHauteur.getEntier()+this.mMargeFenetre.getEntier())/2);//bas gauche
            
            polygonSelect = new Polygon();
            polygonSelect.addPoint(this.point.x - this.mMargeFenetre.getEntier() - offsetSelectLargeur, this.point.y - this.mMargeFenetre.getEntier() + offsetSelectHauteur);//bas gauche
            polygonSelect.addPoint(this.point.x - this.mMargeFenetre.getEntier() - offsetSelectLargeur, this.point.y - this.mHauteur.getEntier() - this.mMargeFenetre.getEntier() - offsetSelectHauteur);//haut gauche
            polygonSelect.addPoint(this.point.x + this.mLargeur.getEntier() + this.mMargeFenetre.getEntier() + offsetSelectLargeur, this.point.y - this.mHauteur.getEntier() - this.mMargeFenetre.getEntier() - offsetSelectHauteur);//haut droit
            polygonSelect.addPoint(this.point.x + this.mLargeur.getEntier() + this.mMargeFenetre.getEntier() + offsetSelectLargeur, this.point.y + this.mMargeFenetre.getEntier() + offsetSelectHauteur);//bas droit
        }//calcul rectangle 
        if(mTrouDessus) {
            int largeur = pCote.getmSalle().getLargeurRetourAir().getEntier();
            int epaisseurMur = pCote.getmSalle().getEpaisseurMur().getEntier();
            int marge = Math.floorDiv((epaisseurMur-largeur), 2);
            System.out.println(marge);
            PolygonePlan = new Polygon();
            if(pCote.getNom()==Cote.listeCote.Nord){
                int y = 50 + marge;
                int x = this.point.x;
                PolygonePlan.addPoint(x,y);
                PolygonePlan.addPoint(x+mLargeur.getEntier(), y);
                PolygonePlan.addPoint(x+mLargeur.getEntier(), y+pCote.getmSalle().getLargeurRetourAir().getEntier());
                PolygonePlan.addPoint(x, y+pCote.getmSalle().getLargeurRetourAir().getEntier());
            }
            if(pCote.getNom()==Cote.listeCote.Sud){
                int y = 50+pCote.getmSalle().getLongueur().getEntier()-marge;
                int x = this.point.x;
                PolygonePlan.addPoint(x,y);
                PolygonePlan.addPoint(x+mLargeur.getEntier(), y);
                PolygonePlan.addPoint(x+mLargeur.getEntier(), y-pCote.getmSalle().getLargeurRetourAir().getEntier());
                PolygonePlan.addPoint(x, y-pCote.getmSalle().getLargeurRetourAir().getEntier());
            }
            if(pCote.getNom()==Cote.listeCote.Ouest){
                int y = this.point.x;
                int x = 50 + marge;
                PolygonePlan.addPoint(x,y);
                PolygonePlan.addPoint(x+pCote.getmSalle().getLargeurRetourAir().getEntier(), y);
                PolygonePlan.addPoint(x+pCote.getmSalle().getLargeurRetourAir().getEntier(), y+mLargeur.getEntier());
                PolygonePlan.addPoint(x,y+mLargeur.getEntier());         
            }
                if(pCote.getNom()==Cote.listeCote.Est){
                int y = this.point.x;
                int x = 50 + pCote.getmSalle().getLargeur().getEntier()-marge;
                PolygonePlan.addPoint(x,y);
                PolygonePlan.addPoint(x-pCote.getmSalle().getLargeurRetourAir().getEntier(), y);
                PolygonePlan.addPoint(x-pCote.getmSalle().getLargeurRetourAir().getEntier(), y+mLargeur.getEntier());
                PolygonePlan.addPoint(x,y+mLargeur.getEntier());         
            } 
        }
        
        //Polygon pour signifier la sélection
        
    }
    
    public Polygon getPolygone() 
    {
        return mPolygon;
    }
    
    public Polygon getPolygonInterieur(){
        return PolygoneElevationInterieur;
    }
    
    public Polygon getPolygoneDessus(){
    return PolygonePlan;
    }
    
    @Override
    public void setPoint(Point p){
    super.setPoint(p);
    this.point = p;
    }
}
