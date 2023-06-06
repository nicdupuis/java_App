/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;

import ca.ulaval.glo2004.domain.Utilitaire.ObjectCloner;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Salle extends Element{
    private MesureImperial mEpaisseurMur;
    private MesureImperial mMarge;
    private MesureImperial mLongueur;
    private MesureImperial mLargeur;
    private MesureImperial mProfondeur;
    
    private Stack<Salle> undoStack = new Stack<>();
    private Stack<Salle> redoStack = new Stack<>();

    private MesureImperial mHauteur;
    private boolean isVuePlan;

    private vueType vueCourante;
    public enum listeCoteActif {Nord,Sud,Est,Ouest};
    public enum listeTypeCote {Exterieur, Interieur}
    public enum vueType {Plan, Elevation, Decoupe};
    private List<Cote> listeCote;
    
    private boolean grilleActive = false;
    private double tailleGrille;
    
    private File path;

    private Polygon mPolygonePlanExterieur;
    private Polygon mPolygonePlanInterieur;

    private Polygon mPolygoneElevationExterieur;
    private Polygon mPolygoneElevationInterieur;
    private Point mPoint;

    private listeCoteActif coteActif;
    private listeTypeCote typeCoteActif;
    
    private MesureImperial hauteurRetourAir;
    private MesureImperial largeurRetourAir;
    private MesureImperial distanceSolRetourAir;
    
    private MesureImperial epaisseurMaterial;
    private MesureImperial margePli;
    private double angle;
    
    
    
    public Salle(Point point)
    {
        super(point);
        this.vueCourante = vueType.Plan;
        this.mPoint = point;
        this.coteActif = listeCoteActif.Nord;
        this.typeCoteActif = listeTypeCote.Exterieur;
        this.setLongueur(new MesureImperial(500,0,1));
        this.setLargeur(new MesureImperial(500,0,1));
        this.setProfondeur(new MesureImperial(20,0,1));
        this.setMarge(new MesureImperial(2,0,1));
        this.setEpaisseurMur(new MesureImperial(50,0,1));
        this.setmHauteur(new MesureImperial(800,0,1));
        this.setVuePlan(true);
        epaisseurMaterial = new MesureImperial(1,1,2);
        margePli = new MesureImperial(1,1,2);

        this.listeCote = new ArrayList<>();
        Cote nord = new Cote(point, this, Cote.listeCote.Nord);
        Cote est = new Cote(new Point(point.x + mLargeur.getEntier() ,point.y), this, Cote.listeCote.Est);
        Cote ouest = new Cote(new Point(point.x ,point.y + mLongueur.getEntier()), this, Cote.listeCote.Ouest);
        Cote sud = new Cote(new Point(point.x + mLargeur.getEntier(),point.y + mLongueur.getEntier()), this, Cote.listeCote.Sud);
        this.ajouterCote(nord);
        this.ajouterCote(sud);
        this.ajouterCote(est);
        this.ajouterCote(ouest);
        this.setHauteurRetourAir(new MesureImperial(12,0,1));
        this.setLargeurRetourAir(new MesureImperial(8,0,1));
        this.setDistanceSolRetourAir(new MesureImperial(40,0,1));
        for (Cote cote : this.listeCote)
        {
            cote.recalculePointsElevation(cote.getListeMurs());
        }
    }
    
    public Salle(Point point, MesureImperial pLongueur, MesureImperial pLargeur, MesureImperial pHauteur, MesureImperial pEpaisseur)
    {
        super(point);
        if(pLongueur.getEntier()==0){
        pLongueur = new MesureImperial(500,0,1);
        }
        if (pLargeur.getEntier()==0){
        pLargeur = new MesureImperial(500,0,1);
        } 
        if (pHauteur.getEntier()==0){
        pHauteur = new MesureImperial(500,0,1);
        }
        if (pEpaisseur.getEntier()==0){
        pEpaisseur = new MesureImperial(40,0,1);
        }
        this.vueCourante = vueType.Plan;
        this.mPoint = point;
        this.coteActif = listeCoteActif.Nord;
        this.typeCoteActif = listeTypeCote.Exterieur;
        this.setLongueur(pLongueur);
        this.setLargeur(pLargeur);
        this.setProfondeur(new MesureImperial(20,0,1));
        this.setMarge(new MesureImperial(2,0,1));
        this.setEpaisseurMur(pEpaisseur);
        this.setmHauteur(pHauteur);
        this.setVuePlan(true);
        epaisseurMaterial = new MesureImperial(1,1,2);
        margePli = new MesureImperial(1,1,2);

        this.listeCote = new ArrayList<>();
        Cote nord = new Cote(point, this, Cote.listeCote.Nord);
        Cote est = new Cote(new Point(point.x + mLargeur.getEntier() ,point.y), this, Cote.listeCote.Est);
        Cote ouest = new Cote(new Point(point.x ,point.y + mLongueur.getEntier()), this, Cote.listeCote.Ouest);
        Cote sud = new Cote(new Point(point.x + mLargeur.getEntier(),point.y + mLongueur.getEntier()), this, Cote.listeCote.Sud);
        this.ajouterCote(nord);
        this.ajouterCote(sud);
        this.ajouterCote(est);
        this.ajouterCote(ouest);
        this.setHauteurRetourAir(new MesureImperial(12,0,1));
        this.setLargeurRetourAir(new MesureImperial(8,0,1));
        this.setDistanceSolRetourAir(new MesureImperial(40,0,1));
        for (Cote cote : this.listeCote)
        {
            cote.recalculePointsElevation(cote.getListeMurs());
        }
    }
    
    public void setHauteurRetourAir(MesureImperial pmesure){
    this.hauteurRetourAir = pmesure;
    if(listeCote!=null){
        for (Cote cote : listeCote){
            for (Accessoire accessoire : cote.getListeAccessoires()){
                if (accessoire.getTrouDessus()){
                    accessoire.setHauteur(pmesure);
                }
                        
            }
        }    
        }
    }
    
    public void setAngle(double pAngle){
        angle = pAngle;
    }
    
    public double getAngle(){
        return angle;
    }
                
    public MesureImperial getHauteurRetourAir() {
        return hauteurRetourAir;
    }
    
    public void setLargeurRetourAir(MesureImperial mesure){
    this.largeurRetourAir = mesure;
    }
    
    public MesureImperial getLargeurRetourAir(){
    return largeurRetourAir;
    }
    
    public void setDistanceSolRetourAir(MesureImperial mesure){
        this.distanceSolRetourAir= mesure;
            if(listeCote!=null){
            for(Cote cote : listeCote){
                for(Accessoire accessoire: cote.getListeAccessoires()){
                    if (accessoire.getTrouDessus()){
                        int x = accessoire.getPoint().x;
                        int y = (50+ mHauteur.getEntier() - mesure.getEntier());
                        accessoire.setPoint(new Point(x ,y));
                    }
                }
            }
            }
    }
    
    public MesureImperial getepaisseurMaterial(){
        return epaisseurMaterial;
    }
    
    public void setEpaisseurMaterial(MesureImperial p_epaisseur){
        this.epaisseurMaterial = p_epaisseur;
    }
    
    public MesureImperial getMargePli(){
        return  margePli;
    }
    
    public void setMargePli(MesureImperial p_Marge){
        this.margePli = p_Marge;
    }   
            
            
    public MesureImperial getDistanceSolRetourAir(){
    return distanceSolRetourAir;
    }
    
    
    public void ajouterCote(Cote cote)
    {
        listeCote.add(cote);
    }
    
    public List<Cote> getListeCote()
    {
        return listeCote;
    }
    
    public int getNumberCotes()
    {
        return listeCote.size();
    }
    
    public MesureImperial getLargeur()
    {
        return mLargeur;
    }
    
    public MesureImperial getLongueur()
    {
        return mLongueur;
    }

    public void setLargeur(MesureImperial largeur)
    {
        this.mLargeur = largeur;
    }
    
    public void setLongueur(MesureImperial hauteur)
    {
        this.mLongueur = hauteur;
    }

    public listeCoteActif getCoteActif() {
        return coteActif;
    }

    public void setCoteActif(listeCoteActif coteActif) {
        this.coteActif = coteActif;
    }

    public listeTypeCote getTypeCoteActif() {
        return typeCoteActif;
    }

    public void setTypeCoteActif(listeTypeCote typeCoteActif) {
        this.typeCoteActif = typeCoteActif;
    }
    
    public MesureImperial getMarge()
    {
        return mMarge;
    } 
    
    public MesureImperial getEpaisseurMur()
    {
        return mEpaisseurMur;
    }

    public Polygon getmPolygoneElevationInterieur() {
        return mPolygoneElevationInterieur;
    }

    public MesureImperial getProfondeur()
    {
        return mProfondeur;
    }
    
    public void setProfondeur(MesureImperial profondeur)
    {
        this.mProfondeur = profondeur;
    }
    
    public void setMarge(MesureImperial marge)
    {
        mMarge = marge;
    }
    
    public void setEpaisseurMur(MesureImperial epaisseurMur)
    {
        this.mEpaisseurMur = epaisseurMur;
    }

    public MesureImperial getmHauteur() {
        return mHauteur;
    }

    public void setmHauteur(MesureImperial mHauteur) {
        this.mHauteur = mHauteur;
    }

    public boolean getVuePlan()
    {
        return isVuePlan;
    }
    
    public void setVuePlan(boolean f)
    {
        this.isVuePlan = f;
    }
    
    public void modifierTailleGrille (double p_tailleGrille)
    {
        tailleGrille = p_tailleGrille;
    }
    
    public void modifierGrilleActive (boolean p_grilleActive)
    {
        grilleActive = p_grilleActive;
    }
    
    public boolean isGrilleActive()
    {
        return grilleActive;
    }
    
    public double tailleGrille()
    {
        return tailleGrille;
    }
    
       
    public Point pointPlusProcheGrille(int largeurPanel, int hauteurPanel, Point clickedPoint)
    {
        List<Point> pointsGrille = new LinkedList<Point>();
        double tailleGrilleActive = tailleGrille();
        int x = 0;
        while (x <= largeurPanel)
        {
            int y = 0;
            while (y <= hauteurPanel)
            {
                pointsGrille.add(new Point(x,y));
                y += tailleGrilleActive;
            }
            x += tailleGrilleActive;
        }
        
        Point closestPoint = new Point(0,0);
        double distance = 10000;
        for (Point unPoint: pointsGrille)
        {
            int distanceX = clickedPoint.x - unPoint.x;
            int distanceY = clickedPoint.y - unPoint.y;
            double distanceTotal = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
            if (distanceTotal < distance)
            {
                closestPoint = new Point(unPoint.x, unPoint.y);
                distance = distanceTotal;
            }
        }
        return closestPoint;
    }
    
    public void setPath(File path)
    {
        this.path = path;
    }
    
    public File getPath()
    {
        return this.path;
    }
    
    protected void createPolygon()
    {
        
    }

    public void calculeDisposition()
    {
        if(this.getVueCourante() == vueType.Plan) {
            mPolygonePlanExterieur = new Polygon();
            mPolygonePlanInterieur = new Polygon();
            mPolygonePlanExterieur.addPoint(50, 50);
            mPolygonePlanExterieur.addPoint(50 + mLargeur.getEntier(), 50);
            mPolygonePlanExterieur.addPoint(50 + mLargeur.getEntier(), 50 + mLongueur.getEntier());
            mPolygonePlanExterieur.addPoint(50, 50 + mLongueur.getEntier());

            mPolygonePlanInterieur.addPoint(50 + mEpaisseurMur.getEntier(), 50 + mEpaisseurMur.getEntier());
            mPolygonePlanInterieur.addPoint(50 + mLargeur.getEntier() - mEpaisseurMur.getEntier(), 50 + mEpaisseurMur.getEntier());
            mPolygonePlanInterieur.addPoint(50 + mLargeur.getEntier() - mEpaisseurMur.getEntier(), 50 + mLongueur.getEntier() - mEpaisseurMur.getEntier());
            mPolygonePlanInterieur.addPoint(50 + mEpaisseurMur.getEntier(), 50 + mLongueur.getEntier() - mEpaisseurMur.getEntier());
        }
        else if (this.getVueCourante() == vueType.Elevation){
            mPolygoneElevationExterieur = new Polygon();
            mPolygoneElevationInterieur = new Polygon();

            if (this.coteActif == listeCoteActif.Nord || this.coteActif == listeCoteActif.Sud) {
                mPolygoneElevationExterieur.addPoint(50, 50);
                mPolygoneElevationExterieur.addPoint(50 + mLargeur.getEntier(), 50);
                mPolygoneElevationExterieur.addPoint(50 + mLargeur.getEntier(), 50 + mHauteur.getEntier());
                mPolygoneElevationExterieur.addPoint(50, 50 + mHauteur.getEntier());
            }
            else
            {
                mPolygoneElevationExterieur.addPoint(50, 50);
                mPolygoneElevationExterieur.addPoint(50 + mLongueur.getEntier(), 50);
                mPolygoneElevationExterieur.addPoint(50 + mLongueur.getEntier(), 50 + mHauteur.getEntier());
                mPolygoneElevationExterieur.addPoint(50, 50 + mHauteur.getEntier());
            }

            if (this.coteActif == listeCoteActif.Nord || this.coteActif == listeCoteActif.Sud) {
                mPolygoneElevationInterieur.addPoint(50 + mEpaisseurMur.getEntier(), 50);
                mPolygoneElevationInterieur.addPoint(50 + mLargeur.getEntier() - mEpaisseurMur.getEntier(), 50);
                mPolygoneElevationInterieur.addPoint(50 + mLargeur.getEntier() - mEpaisseurMur.getEntier(), 50 + mHauteur.getEntier());
                mPolygoneElevationInterieur.addPoint(50 + mEpaisseurMur.getEntier(), 50 + mHauteur.getEntier());
            }
            else
            {
                mPolygoneElevationInterieur.addPoint(50 + mEpaisseurMur.getEntier(), 50);
                mPolygoneElevationInterieur.addPoint(50 + mLongueur.getEntier() - mEpaisseurMur.getEntier(), 50);
                mPolygoneElevationInterieur.addPoint(50 + mLongueur.getEntier() - mEpaisseurMur.getEntier(), 50 + mHauteur.getEntier());
                mPolygoneElevationInterieur.addPoint(50 + mEpaisseurMur.getEntier(), 50 + mHauteur.getEntier());
            }
        }
    }

    public Polygon getmPolygonePlanExterieur() {
        return mPolygonePlanExterieur;
    }
    public Polygon getmPolygonePlanInterieur() {
        return mPolygonePlanInterieur;
    }

    public vueType getVueCourante() {
        return vueCourante;
    }

    public void setVueCourante(vueType vueCourante) {
        this.vueCourante = vueCourante;
    }

    public Polygon getmPolygoneElevationExterieur() {
        return mPolygoneElevationExterieur;
    }

    public void calculePointCote()
    {
        for(Cote cote : listeCote)
        {
            switch (cote.getNom())
            {
                case Est:
                    cote.setPoint(new Point(mPoint.x + mLargeur.getEntier() ,mPoint.y));
                    break;
                case Ouest:
                    cote.setPoint(new Point(mPoint.x ,mPoint.y + mLongueur.getEntier()));
                    cote.setmPointElevation(new Point(mPoint.x + mLargeur.getEntier(),mPoint.y));
                    break;
                case Sud:
                    cote.setPoint(new Point(mPoint.x + mLargeur.getEntier(),mPoint.y + mLongueur.getEntier()));
                    cote.setmPointElevation(new Point(mPoint.x + mLargeur.getEntier(),mPoint.y));
                    break;

            }
        }

    }

    public void redimension() {
        calculePointCote();
        for (Cote cote : listeCote) {
            cote.recalculeLongueurMur(cote);
            cote.recalculePoints(cote.getListeMurs());
            cote.recalculePointsElevation(cote.getListeMurs());
            for (Mur murs : cote.getListeMurs()) {
                boolean edge = false;
                murs.getListeSeparateurs().get(0).calculeDisposition(edge);
            }
        }
    }


    public void ajouterSeparateur(String coteString, int x, int y){
        if(Objects.equals(coteString, "Nord")){
            Cote cote = getListeCote().get(0);
            if(cote.getPoint().x + getEpaisseurMur().getEntier()< x && x < getListeCote().get(2).getPoint().x - getEpaisseurMur().getEntier()) {
                Point point = new Point(x, cote.getPoint().y);
                Mur mur = new Mur(point, cote);
                Separateur separateur = new Separateur(point, mur);

                int index = cote.getIndexMur(cote, mur);
                cote.getListeMurs().add(index, mur);
                cote.recalculeLongueurMur(cote);
                cote.recalculePoints(cote.getListeMurs());
                cote.recalculePointsElevation(cote.getListeMurs());
                mur.ajouterSeparateur(separateur);
                mur.getListeSeparateurs().get(0).calculeDisposition(false);
            }
        }

        if(Objects.equals(coteString, "Sud")){
            Cote cote = getListeCote().get(1);
            if(x < cote.getPoint().x - getEpaisseurMur().getEntier() && x > getListeCote().get(3).getPoint().x + getEpaisseurMur().getEntier()) {
                Point point = new Point(x, cote.getPoint().y);
                Mur mur = new Mur(point, cote);
                Separateur separateur = new Separateur(point, mur);

                int index = cote.getIndexMur(cote, mur);
                cote.getListeMurs().add(index, mur);
                cote.recalculeLongueurMur(cote);
                mur.ajouterSeparateur(separateur);
                mur.getListeSeparateurs().get(0).calculeDisposition(false);
                cote.recalculePointsElevation(cote.getListeMurs());
            }
        }

        if(Objects.equals(coteString, "Ouest")){
            Cote cote = getListeCote().get(3);
            if (vueCourante == vueType.Plan) {
                if(y < cote.getPoint().y - getEpaisseurMur().getEntier() && y > getListeCote().get(0).getPoint().y + getEpaisseurMur().getEntier()) {
                    Point point = new Point(cote.getPoint().x, y);
                    Mur mur = new Mur(point, cote);
                    Separateur separateur = new Separateur(point, mur);

                    int index = cote.getIndexMur(cote, mur);
                    cote.getListeMurs().add(index, mur);
                    cote.recalculeLongueurMur(cote);
                    mur.ajouterSeparateur(separateur);
                    mur.getListeSeparateurs().get(0).calculeDisposition(false);
                    cote.recalculePointsElevation(cote.getListeMurs());
                }
            }
            else if (vueCourante == vueType.Elevation){
                if (x > getPoint().x + getEpaisseurMur().getEntier() && x < getPoint().x + getLargeur().getEntier() - getEpaisseurMur().getEntier()) {
                    Point point = new Point(cote.getPoint().x, x);
                    Mur mur = new Mur(point, cote);
                    Separateur separateur = new Separateur(point, mur);

                    int index = cote.getIndexMur(cote, mur);
                    cote.getListeMurs().add(index, mur);
                    cote.recalculeLongueurMur(cote);
                    mur.ajouterSeparateur(separateur);
                    mur.getListeSeparateurs().get(0).calculeDisposition(false);
                    cote.recalculePointsElevation(cote.getListeMurs());
                }
            }
        }

        if(Objects.equals(coteString, "Est")) {
            Cote cote = getListeCote().get(2);
            if (vueCourante == vueType.Plan) {
                if (y > cote.getPoint().y + getEpaisseurMur().getEntier() && y < getListeCote().get(1).getPoint().y - getEpaisseurMur().getEntier()) {
                    Point point = new Point(cote.getPoint().x, y);
                    Mur mur = new Mur(point, cote);
                    Separateur separateur = new Separateur(point, mur);

                    int index = cote.getIndexMur(cote, mur);
                    cote.getListeMurs().add(index, mur);
                    cote.recalculeLongueurMur(cote);
                    mur.ajouterSeparateur(separateur);
                    mur.getListeSeparateurs().get(0).calculeDisposition(false);
                    cote.recalculePointsElevation(cote.getListeMurs());
                }
            }
            else if (vueCourante == vueType.Elevation){
                if (x > getPoint().x + getEpaisseurMur().getEntier() && x < getPoint().x + getLargeur().getEntier() - getEpaisseurMur().getEntier()) {
                    Point point = new Point(cote.getPoint().x, x);
                    Mur mur = new Mur(point, cote);
                    Separateur separateur = new Separateur(point, mur);

                    int index = cote.getIndexMur(cote, mur);
                    cote.getListeMurs().add(index, mur);
                    cote.recalculeLongueurMur(cote);
                    mur.ajouterSeparateur(separateur);
                    mur.getListeSeparateurs().get(0).calculeDisposition(false);
                    cote.recalculePointsElevation(cote.getListeMurs());
                }
            }
        }
    }

    public void supprimerSeparateur(int x,int y)
    {
        Cote coteElevation;
        Point userPoint = new Point(x,y);
        TreeMap<Integer,Mur> murs = new TreeMap<>();
        if(vueCourante == vueType.Plan){
            for(Cote cote : getListeCote()) {
                for (Mur mur : cote.getListeMurs()) {
                    int distance = (int) mur.getPoint().distanceSq(userPoint);
                    murs.put(distance,mur);
                }
            }
        }
        else if (vueCourante == vueType.Elevation){
            if(coteActif == listeCoteActif.Nord)
                coteElevation = getListeCote().get(0);
            else if(coteActif == listeCoteActif.Sud)
                coteElevation = getListeCote().get(1);
            else if(coteActif == listeCoteActif.Est)
                coteElevation = getListeCote().get(2);
            else
                coteElevation = getListeCote().get(3);

            for (Mur mur : coteElevation.getListeMurs()) {
                int distance = (int) mur.getmPointElevation().distanceSq(userPoint);
                murs.put(distance,mur);
            }
        }

        Mur plusProche = murs.firstEntry().getValue();
        Cote cote = plusProche.getmCote();
        if(cote.getListeMurs().indexOf(plusProche) != 0) {
            cote.retirerMur(plusProche);
            cote.recalculeLongueurMur(cote);
            cote.recalculePoints(cote.getListeMurs());
            cote.recalculePointsElevation(cote.getListeMurs());
        }
    }
    
    public void supprimerSeparateurSelectionne(Mur plusProche)
    {
        Cote cote = plusProche.getmCote();
        if(cote.getListeMurs().indexOf(plusProche) != 0) {
            cote.retirerMur(plusProche);
            cote.recalculeLongueurMur(cote);
            cote.recalculePoints(cote.getListeMurs());
            cote.recalculePointsElevation(cote.getListeMurs());
        }
    }
    
    public Mur separateurPlusProche(int px, int py)
    {
        Cote coteElevation;
        Point userPoint = new Point(px,py);
        TreeMap<Integer,Mur> murs = new TreeMap<>();
        if(vueCourante == vueType.Plan){
            for(Cote cote : getListeCote()) {
                for (Mur mur : cote.getListeMurs()) {
                    int distance = (int) mur.getPoint().distanceSq(userPoint);
                    murs.put(distance,mur);
                }
            }
        }
        else if (vueCourante == vueType.Elevation){
            if(coteActif == listeCoteActif.Nord)
                coteElevation = getListeCote().get(0);
            else if(coteActif == listeCoteActif.Sud)
                coteElevation = getListeCote().get(1);
            else if(coteActif == listeCoteActif.Est)
                coteElevation = getListeCote().get(2);
            else
                coteElevation = getListeCote().get(3);

            for (Mur mur : coteElevation.getListeMurs()) {
                int distance = (int) mur.getmPointElevation().distanceSq(userPoint);
                murs.put(distance,mur);
            }
        }

        Mur plusProche = murs.firstEntry().getValue();
        return plusProche;
    }
    
    public void supprimerAccessoire(Accessoire acc)
    {
        Cote cote;
        if(coteActif == listeCoteActif.Nord)
            cote = getListeCote().get(0);
        else if(coteActif == listeCoteActif.Sud)
            cote = getListeCote().get(1);
        else if(coteActif == listeCoteActif.Est)
            cote = getListeCote().get(2);
        else
            cote = getListeCote().get(3);
        
        cote.getListeAccessoires().remove(acc);
    }
    
    public void undo()
    {
        Salle mSalle = undoStack.pop();
        this.redoStack.push(mSalle);
        
        Salle salleCopie = this.undoStack.peek();
        
        this.mPolygonePlanExterieur = salleCopie.mPolygonePlanExterieur;
        this.mPolygonePlanInterieur = salleCopie.mPolygonePlanInterieur;
        this.mPolygoneElevationExterieur = salleCopie.mPolygoneElevationExterieur;
        this.mPolygoneElevationInterieur = salleCopie.mPolygoneElevationInterieur;
        this.mEpaisseurMur = salleCopie.mEpaisseurMur;
        this.mHauteur = salleCopie.mHauteur;
        this.mLargeur = salleCopie.mLargeur;
        this.mLongueur = salleCopie.mLongueur;
        this.mProfondeur = salleCopie.mProfondeur;
        this.mMarge = salleCopie.mMarge;
        
        for(int index=0; index<4; index++)
        {
            this.listeCote.get(index).setListeAccessoires(salleCopie.listeCote.get(index).getListeAccessoires());
            this.listeCote.get(index).setListeMurs(mSalle.listeCote.get(index).getListeMurs());
            this.listeCote.get(index).setListeSeparateurs(mSalle.listeCote.get(index).getListeSeparateurs());
        }
        
        this.redimension();
    }
    
    public void redo()
    {
        Salle mSalle = redoStack.pop();
        this.undoStack.push(mSalle);

        this.mPolygonePlanExterieur = mSalle.mPolygonePlanExterieur;
        this.mPolygonePlanInterieur = mSalle.mPolygonePlanInterieur;
        this.mPolygoneElevationExterieur = mSalle.mPolygoneElevationExterieur;
        this.mPolygoneElevationInterieur = mSalle.mPolygoneElevationInterieur;
        this.mEpaisseurMur = mSalle.mEpaisseurMur;
        this.mHauteur = mSalle.mHauteur;
        this.mLargeur = mSalle.mLargeur;
        this.mLongueur = mSalle.mLongueur;
        this.mProfondeur = mSalle.mProfondeur;
        this.mMarge = mSalle.mMarge;

        for(int index=0; index<4; index++)
        {
            this.listeCote.get(index).setListeAccessoires(mSalle.listeCote.get(index).getListeAccessoires());
            this.listeCote.get(index).setListeMurs(mSalle.listeCote.get(index).getListeMurs());
            this.listeCote.get(index).setListeSeparateurs(mSalle.listeCote.get(index).getListeSeparateurs());
        }
        this.redimension();
    }
    
    public void copierSalle(Salle mSalle)
    {
        try 
        {
            Salle salleCopie = (Salle) ObjectCloner.deepCopy(mSalle);
            this.undoStack.push(salleCopie);
            this.redoStack.clear();   
            System.out.println("copierSalle");
        }
        catch(Exception e)
        {
            System.out.println("copierSalle: Erreur");
        }        
    }
    
    
    
}




