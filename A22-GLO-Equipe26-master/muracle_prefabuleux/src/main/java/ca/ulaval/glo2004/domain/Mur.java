package ca.ulaval.glo2004.domain;
 import java.awt.*;
import java.util.ArrayList;
 import java.util.LinkedList;
 import java.util.List;
//import java.lang.Math;
/**
 *
 * @author equipe26
 */
public class Mur extends Element {
    private Cote mCote;
    private Polygon mPolygoneElevation;
    private Polygon mPolygonePlan;
    private MesureImperial mPolygoneEpaisseurMur1;
    private MesureImperial mPolygoneEpaisseurMur2;
    private MesureImperial mPolygoneBandeSoudage1;
    private MesureImperial mPolygoneBandeSoudage2;
    private MesureImperial mPolygoneMarge12;
    private MesureImperial mPolygoneMarge11;
    private MesureImperial mPolygoneMarge22;
    private MesureImperial mPolygoneMarge21;
    private MesureImperial mLongueurExterieur;
    private MesureImperial mLongueurInterieur;

    private Point mPoint;
    private Point mPointElevation;
    private Color color;
    private List<Accessoire> listeAccessoires;
    private List<Separateur> listeSeparateurs;
    private List<Polygon> decoupeInterieur;
    private List<Polygon> decoupeExterieur;
    


    public Mur(Point point, Cote cote) {
        super(point);
        this.color = Color.BLACK;
        this.listeAccessoires = new LinkedList<Accessoire>();
        this.listeSeparateurs = new LinkedList<Separateur>();
        this.mCote = cote;
        this.mPoint = point;
        this.mPointElevation = point;
        this.mPolygonePlan = new Polygon();
        this.mLongueurExterieur = new MesureImperial(100,0,1);
        this.mLongueurInterieur = new MesureImperial(100,0,1);
        this.mPolygoneEpaisseurMur1 = new MesureImperial(30,0,1);
        this.mPolygoneEpaisseurMur2 = new MesureImperial(30,0,1);
        this.decoupeInterieur = new ArrayList<>();
        this.decoupeExterieur = new ArrayList<>();
    }
    
    public List<Polygon> getDecoupeExterieur(){
        return decoupeExterieur;
    }
     public List<Polygon> getDecoupeInterieur(){
        return decoupeInterieur;
    }

    public void ajouterSeparateur(Separateur separateur)
    {
        listeSeparateurs.add(separateur);
    }
    public Color getColor() {
        return this.color;
    }

    public void add(Accessoire aAccessoire) {
        this.listeAccessoires.add(aAccessoire);
    }

    public List<Accessoire> getListeAccessoires() {
        return this.listeAccessoires;
    }

    public void setmPolygoneEpaisseurMur1(MesureImperial epaisseur) {
        this.mPolygoneEpaisseurMur1 = epaisseur;
    }

    public void setmPointElevation(Point mPointElevation) {
        this.mPointElevation = mPointElevation;
    }

    public Point getmPointElevation() {
        return mPointElevation;
    }

    public void setmLongueurExterieur(MesureImperial longueurExterieur) {
        this.mLongueurExterieur = longueurExterieur;
    }

    public void setmLongueurInterieur(MesureImperial longueurInterieur) {
        this.mLongueurInterieur = longueurInterieur;
    }

    public Polygon getmPolygonePlan() {
        return mPolygonePlan;
    }

    public MesureImperial getmLongueurExterieur() {
        return mLongueurExterieur;
    }
    
    public MesureImperial getmLongueurInterieur()
    {
        return mLongueurInterieur;
    }

    public void setPoint(Point point) {
        this.mPoint = point;
    }

    public Point getPoint() {
        return this.mPoint;
    }

    public Cote getmCote() {
        return mCote;
    }

    public List<Separateur> getListeSeparateurs() {
        return listeSeparateurs;
    }

    public void calculeDisposition(Boolean edge, boolean last) {

        // Cas ou le mur possede aucun separateur
        if (mCote.getNumberMurs() == 1) {
            int decalage = (int) ((mLongueurExterieur.getEntier() - mLongueurInterieur.getEntier()) / 2);
            if (mCote.getNom() == Cote.listeCote.Ouest) {
                this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + decalage);
                this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(),
                        ((this.mPoint.y + mLongueurExterieur.getEntier()) - decalage));
                this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());

            }
            if (mCote.getNom() == Cote.listeCote.Nord) {
                this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                this.mPolygonePlan.addPoint(this.mPoint.x + decalage, this.mPoint.y + mPolygoneEpaisseurMur1.getEntier());
                this.mPolygonePlan.addPoint(((this.mPoint.x + mLongueurExterieur.getEntier()) - decalage),
                        this.mPoint.y + mPolygoneEpaisseurMur1.getEntier());
                this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
            }

            if (mCote.getNom() == Cote.listeCote.Sud) {
                this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                this.mPolygonePlan.addPoint(this.mPoint.x + decalage, this.mPoint.y - mPolygoneEpaisseurMur1.getEntier());
                this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier() - decalage,
                        this.mPoint.y - mPolygoneEpaisseurMur1.getEntier());
                this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
            }

            if (mCote.getNom() == Cote.listeCote.Est) {
                this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                this.mPolygonePlan.addPoint(this.mPoint.x - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + decalage);
                this.mPolygonePlan.addPoint(this.mPoint.x - decalage,
                        ((this.mPoint.y + mLongueurExterieur.getEntier()) - decalage));
                this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());
            }

        } else {

            if (edge) {
                // Cas mur de coin
                if (this.mCote.getNom() == Cote.listeCote.Ouest) {
                    if (!last) {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mLongueurExterieur.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mPolygoneEpaisseurMur2.getEntier());
                    } else {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mLongueurExterieur.getEntier() - mPolygoneEpaisseurMur2.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());
                    }
                } else if (this.mCote.getNom() == Cote.listeCote.Nord) {
                    if (!last) {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y + mPolygoneEpaisseurMur2.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mPolygoneEpaisseurMur2.getEntier());
                    } else {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier() - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mPolygoneEpaisseurMur2.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mPolygoneEpaisseurMur1.getEntier());
                    }
                }
                else if (this.mCote.getNom() == Cote.listeCote.Est) {
                    if (!last) {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mLongueurExterieur.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mPolygoneEpaisseurMur2.getEntier());
                    } else {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mLongueurExterieur.getEntier() - mPolygoneEpaisseurMur2.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());
                    }
                } else if (this.mCote.getNom() == Cote.listeCote.Sud) {
                    if (!last) {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y - mPolygoneEpaisseurMur2.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y - mPolygoneEpaisseurMur2.getEntier());
                    } else {
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
                        this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier() - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y - mPolygoneEpaisseurMur2.getEntier());
                        this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y - mPolygoneEpaisseurMur2.getEntier());
                    }
                }

            } else {
                // Cas Mur normal
                if (this.mCote.getNom() == Cote.listeCote.Ouest) {
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());
                    this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mLongueurExterieur.getEntier());
                    this.mPolygonePlan.addPoint(this.mPoint.x + mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y);
                } else if (this.mCote.getNom() == Cote.listeCote.Nord){
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                    this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
                    this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y + mPolygoneEpaisseurMur1.getEntier());
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mPolygoneEpaisseurMur1.getEntier());
                } else if (this.mCote.getNom() == Cote.listeCote.Est) {
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y + mLongueurExterieur.getEntier());
                    this.mPolygonePlan.addPoint(this.mPoint.x - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y + mLongueurExterieur.getEntier());
                    this.mPolygonePlan.addPoint(this.mPoint.x - mPolygoneEpaisseurMur1.getEntier(), this.mPoint.y);
                } else {
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y);
                    this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y);
                    this.mPolygonePlan.addPoint(this.mPoint.x + mLongueurExterieur.getEntier(), this.mPoint.y - mPolygoneEpaisseurMur2.getEntier());
                    this.mPolygonePlan.addPoint(this.mPoint.x, this.mPoint.y - mPolygoneEpaisseurMur2.getEntier());
                }
            }


            // Cas
        }

    }
    
    public void calculDecoupe(boolean edge_gauche, boolean edge_droit){
        int epaisseurMur = mCote.getmSalle().getEpaisseurMur().getEntier();
        int hauteurMur = mCote.getmSalle().getmHauteur().getEntier();
        int largeurExt = mLongueurExterieur.getEntier();//??
        int largeurInt = mLongueurInterieur.getEntier();
        int retraitPli = mCote.getmSalle().getepaisseurMaterial().getEntier();
        int margePli = mCote.getmSalle().getMargePli().getEntier();
        int tabDimensions = mCote.getmSalle().getMarge().getEntier();
        double phi = Math.toRadians(mCote.getmSalle().getAngle());
        int x = (int) Math.round(tabDimensions*Math.tan(phi));
        //Puisque l'angle de rencontre est 45 degree, hypothenuse est racine carre de 2 * le cote du triangle. Aller voir Pythagore pour plus d'explication.
        int hypothenuse = (int) Math.round(Math.sqrt(2)*epaisseurMur);
        
        Polygon p = new Polygon();
        int x0 = 50;
        int y0 = 50;
        //Ajouter Polygon Exterieur 
        Polygon pExt = new Polygon();
        //Creer une translation pour afficher les decoupes cotes a cotes
        int x1 = 100 + largeurInt+epaisseurMur;
        int y1 = 50;
        //Calcul pour le coin decouper pour faciliter la soudure.
        int y = (int) Math.round(tabDimensions*Math.tan(phi));
        //Calcul a proprement des polygones pour cote interieur et exterieur
                
        if(!edge_gauche){
           p.addPoint(x0,y0+hauteurMur+epaisseurMur+margePli+tabDimensions);//1 -- si pas un coin gauche  
           p.addPoint(x0+retraitPli,y0+hauteurMur+epaisseurMur+margePli+tabDimensions); //2
           p.addPoint(x0+retraitPli, y0+hauteurMur+2*epaisseurMur+2*margePli+tabDimensions);//3
           p.addPoint(x0+retraitPli + x ,y0+hauteurMur+2*epaisseurMur+2*margePli+2*tabDimensions);//4
        }else{
           p.addPoint(x0,y0+hauteurMur+epaisseurMur+margePli+tabDimensions);
           p.addPoint(x0+retraitPli,y0+hauteurMur+epaisseurMur+margePli+tabDimensions); //2
           p.addPoint(x0+retraitPli-epaisseurMur, y0+hauteurMur+2*epaisseurMur+2*margePli+tabDimensions);//3
           p.addPoint(x0+retraitPli-epaisseurMur+ x ,y0+hauteurMur+2*epaisseurMur+2*margePli+2*tabDimensions);//4
        }
        if(!edge_droit){
           p.addPoint(x0+largeurInt+epaisseurMur-retraitPli-x, y0+hauteurMur+2*epaisseurMur+2*margePli+2*tabDimensions);//5
           p.addPoint(x0+largeurInt+epaisseurMur-retraitPli, y0+hauteurMur+2*epaisseurMur+2*margePli+tabDimensions);//6
           p.addPoint(x0+largeurInt-retraitPli, y0+hauteurMur+epaisseurMur+margePli+tabDimensions);//7
           p.addPoint(x0+largeurInt, y0+hauteurMur+epaisseurMur+margePli+tabDimensions);//8
           p.addPoint(x0+largeurInt, y0+margePli+epaisseurMur+tabDimensions);//9
           p.addPoint(x0+largeurInt-retraitPli, y0+margePli+epaisseurMur+tabDimensions);//10
           p.addPoint(x0+largeurInt+epaisseurMur-retraitPli, y0+tabDimensions);//11
           p.addPoint(x0+largeurInt+epaisseurMur-retraitPli-x, y0);//12
        }else{
           p.addPoint(x0+largeurInt-retraitPli-x, y0+hauteurMur+2*epaisseurMur+2*margePli+2*tabDimensions);//5
           p.addPoint(x0+largeurInt-retraitPli, y0+hauteurMur+2*epaisseurMur+2*margePli+tabDimensions);//6
           p.addPoint(x0+largeurInt-retraitPli, y0+hauteurMur+epaisseurMur+margePli+tabDimensions);//7
           p.addPoint(x0+largeurInt, y0+hauteurMur+epaisseurMur+margePli+tabDimensions);//8
           p.addPoint(x0+largeurInt, y0+margePli+epaisseurMur+tabDimensions);//9
           p.addPoint(x0+largeurInt-retraitPli, y0+margePli+epaisseurMur+tabDimensions);//10
           p.addPoint(x0+largeurInt-retraitPli, y0+tabDimensions);//11
           p.addPoint(x0+largeurInt-retraitPli-x, y0);//12
        }
        if(!edge_gauche){
           p.addPoint(x0+retraitPli + x, y0);//13
           p.addPoint(x0+retraitPli,  y0+tabDimensions);//14
           p.addPoint(x0+retraitPli, y0+tabDimensions+margePli+epaisseurMur);//15
           p.addPoint(x0, y0+margePli+epaisseurMur+tabDimensions);
        }else{
           p.addPoint(x0+retraitPli-epaisseurMur + x, y0);//13
           p.addPoint(x0+retraitPli-epaisseurMur,  y0+tabDimensions);//14
           p.addPoint(x0+retraitPli, y0+tabDimensions+margePli+epaisseurMur);//15
           p.addPoint(x0, y0+margePli+epaisseurMur+tabDimensions);
        }
           this.decoupeInterieur.add(p);        
           //Calcul polyExterieur
           if(!edge_gauche){
                pExt.addPoint(x1,y1);//1
                pExt.addPoint(x1, y1+retraitPli);//2
                pExt.addPoint(x1-margePli-epaisseurMur,y1+retraitPli);//3
                pExt.addPoint(x1-margePli-epaisseurMur-tabDimensions, y1+retraitPli+y);//4
                pExt.addPoint(x1-margePli-epaisseurMur-tabDimensions, y1-y-retraitPli+hauteurMur);//5
                pExt.addPoint(x1-margePli-epaisseurMur, y1+hauteurMur-retraitPli );//6
                pExt.addPoint(x1,y1+hauteurMur-retraitPli);//7
                pExt.addPoint(x1,y1+hauteurMur);//8
           }else{
                pExt.addPoint(x1-epaisseurMur,y1);//1
                pExt.addPoint(x1-epaisseurMur, y1+retraitPli);//2
                pExt.addPoint(x1-epaisseurMur-hypothenuse-margePli,y1+retraitPli);//3
                pExt.addPoint(x1-epaisseurMur-hypothenuse-margePli-tabDimensions, y1+retraitPli+y);//4
                pExt.addPoint(x1-epaisseurMur-hypothenuse-margePli-tabDimensions, y1-y-retraitPli+hauteurMur);//5
                pExt.addPoint(x1-epaisseurMur-hypothenuse-margePli, y1+hauteurMur-retraitPli );//6
                pExt.addPoint(x1-epaisseurMur,y1+hauteurMur-retraitPli);//7
                pExt.addPoint(x1-epaisseurMur,y1+hauteurMur);//8
           }        
           if(!edge_droit){
                pExt.addPoint(x1+largeurInt,y1+hauteurMur);//9           
                pExt.addPoint(x1+largeurInt,y1+hauteurMur-retraitPli);//10
                pExt.addPoint(x1+largeurInt+margePli+epaisseurMur,y1+hauteurMur-retraitPli);//11
                pExt.addPoint(x1+largeurInt+margePli+epaisseurMur+tabDimensions,y1+hauteurMur-retraitPli-y);//12
                pExt.addPoint(x1+largeurInt+margePli+epaisseurMur+tabDimensions, y1+y+retraitPli);//13
                pExt.addPoint(x1+largeurInt+margePli+epaisseurMur, y1+retraitPli);//14
                pExt.addPoint(x1+largeurInt,y1+retraitPli);//15
                pExt.addPoint(x1+largeurInt,y1);//16
           }else{
                pExt.addPoint(x1+largeurInt+epaisseurMur,y1+hauteurMur);//9           
                pExt.addPoint(x1+largeurInt+epaisseurMur,y1+hauteurMur-retraitPli);//10
                pExt.addPoint(x1+largeurInt+epaisseurMur+hypothenuse+margePli,y1+hauteurMur-retraitPli);//11
                pExt.addPoint(x1+largeurInt+epaisseurMur+hypothenuse+margePli+tabDimensions,y1+hauteurMur-retraitPli-y);//12
                pExt.addPoint(x1+largeurInt+epaisseurMur+hypothenuse+margePli+tabDimensions, y1+y+retraitPli);//13
                pExt.addPoint(x1+largeurInt+epaisseurMur+hypothenuse+margePli, y1+retraitPli);//14
                pExt.addPoint(x1+largeurInt+epaisseurMur,y1+retraitPli);//15
                pExt.addPoint(x1+largeurInt+epaisseurMur,y1);//16
           }
           
           this.decoupeExterieur.add(pExt);
           //Ajoutons decoupe des accessoires ! 
           for(Accessoire a : mCote.getListeAccessoires()){
               Rectangle r = new Rectangle(a.getPoint().x,a.getPoint().y , a.getLargeur().getEntier(),a.getHauteur().getEntier());
               if(true){
                    if(a.getPerceInterieur()){
                    // creons un deep copy des polygones puis translations pour le faire apparaitre a la bonne place !
                        Polygon polycopy  =  new Polygon(a.getPolygone().xpoints,a.getPolygone().ypoints, a.getPolygone().npoints);
                        polycopy.translate(0, margePli+epaisseurMur+tabDimensions);
                        decoupeInterieur.add(polycopy);
                    }
                    if(a.getPerceExterieur()){
                        Polygon polycopy  =  new Polygon(a.getPolygone().xpoints,a.getPolygone().ypoints, a.getPolygone().npoints);
                        polycopy.translate(x1-50+margePli+epaisseurMur+tabDimensions,0);
                        decoupeExterieur.add(polycopy);
                    }
                        if(a.getTrouDessus()){
                        int xra = a.getPoint().x;
                        int hauteurRetourAir = mCote.getmSalle().getHauteurRetourAir().getEntier();
                        int yra = y0 +tabDimensions+ margePli +  (int) Math.round((mCote.getmSalle().getHauteurRetourAir().getEntier() - epaisseurMur)/2);
                        Polygon retourAir = new Polygon();
                        retourAir.addPoint(xra,yra);
                        retourAir.addPoint(xra,yra+hauteurRetourAir);
                        retourAir.addPoint(xra+a.getLargeur().getEntier(), yra+hauteurRetourAir);
                        retourAir.addPoint(xra+a.getLargeur().getEntier(), yra);
                        }
                }
           }  
    }//----finCalculDecoupe----
    private void Polyimperial(boolean estuncoindroit, boolean estuncoingauche){
    
    
    }
    
   
}


