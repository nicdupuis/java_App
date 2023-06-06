/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;
 import java.awt.Point;
 import java.util.LinkedList;
 import java.util.List;
 import java.nio.file.Path;
import java.io.Serializable;


import ca.ulaval.glo2004.gui.DrawingPanel;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author equipe26
 */
public class Controleur implements Observable{
    private Cote mCoteCourant;
    private Mur mMurCourant;
    private boolean mDecoupe;
    private Cote cote;
    protected enum listeCote {Nord,Sud,Est,Ouest};
    private Salle mSalle;
    private String mFichier;
    private List<ControllerObserver> observers;

    
    public enum Modes
    {
        MUR, PRISE, PORTE, RETOURDAIR, SEPARATEUR, FENETRE
    }
    
    public Controleur()
    {
        //mSalle = new Salle();
        //mCoteCourant = ?
        this.observers = new ArrayList<>();        
    }
    
    public Controleur(Salle salle)
    {
        this.mSalle = salle ;
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(ControllerObserver newListener)
    {
        observers.add(newListener);
    }
    
    @Override
    public void unregisterObserver(ControllerObserver listener)
    {
        observers.remove(listener);
    }
    
    public void notifyObserversForUpdatedSalle()
    {
        for(ControllerObserver observer: observers)
        {
            observer.notifyUpdatedSalle();
        }
    }
    
    private void addMur(Point mousePoint)
    {
        // Mur newMur = new Mur(mousePoint);
        //add Mur dans la listeCote, comment accéder à la bonne liste ?
    }
    //Creer une fenetre par defaut
    private void addFenetre(Point mousePoint)
    {
    /*
    Rectangle et fait un trou dans le mur
    Jeu supplémentaire de 1/8 pouce
    */
        List<MesureImperial> listeMesure = Collections.EMPTY_LIST;
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){ 
            mCoteCourant = mSalle.getListeCote().get(0);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){ 
            mCoteCourant = mSalle.getListeCote().get(1);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){ 
            mCoteCourant = mSalle.getListeCote().get(2);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){ 
            mCoteCourant = mSalle.getListeCote().get(3);
        }        
        Accessoire newFenetre = Accessoire.AccessoireBuilder("Fenêtre", mousePoint, Element.Type.FENETRE,listeMesure,mCoteCourant);
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){
        mSalle.getListeCote().get(0).ajouterAccessoire(newFenetre);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){
        mSalle.getListeCote().get(1).ajouterAccessoire(newFenetre);
        mCoteCourant = mSalle.getListeCote().get(1);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){
        mSalle.getListeCote().get(2).ajouterAccessoire(newFenetre);
        mCoteCourant = mSalle.getListeCote().get(2);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){
        mSalle.getListeCote().get(3).ajouterAccessoire(newFenetre);
        mCoteCourant = mSalle.getListeCote().get(3);
        }
    }
    
    public void resetSelectionStatus() 
    {
        for(Cote cote1: mSalle.getListeCote())
        {
            for(Accessoire acc: cote1.getListeAccessoires())
            {
                acc.setSelectionStatus(false);
            }
        }
        
        //TODO: Parcourir séparateurs
    }
    
    
    private void addPorte(Point mousePoint)
    {
    /* PORTE
        Rectangle et fait un trou dans le mur
        Faire de cette façon --> pas besoin d'une classe différente pour chaque accessoire. Doit changer null pour valeur par défaut ici.
    */
        List<MesureImperial> listeMesure = Collections.EMPTY_LIST;
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){ 
            mCoteCourant = mSalle.getListeCote().get(0);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){ 
            mCoteCourant = mSalle.getListeCote().get(1);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){ 
            mCoteCourant = mSalle.getListeCote().get(2);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){ 
            mCoteCourant = mSalle.getListeCote().get(3);
        }   
        Accessoire newPorte = Accessoire.AccessoireBuilder("Porte", mousePoint, Element.Type.PORTE,listeMesure,mCoteCourant);
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){
        mSalle.getListeCote().get(0).ajouterAccessoire(newPorte);
        mCoteCourant = mSalle.getListeCote().get(0);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){
        mSalle.getListeCote().get(1).ajouterAccessoire(newPorte);
        mCoteCourant = mSalle.getListeCote().get(1);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){
        mSalle.getListeCote().get(2).ajouterAccessoire(newPorte);
        mCoteCourant = mSalle.getListeCote().get(2);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){
        mSalle.getListeCote().get(3).ajouterAccessoire(newPorte);
        mCoteCourant = mSalle.getListeCote().get(3);
        }
    }
    
    private void addPrise(Point mousePoint)
    {
    /* PRISE
    Intérieur seulement
    Rectangle et fait un trou dans le mur
    */
        List<MesureImperial> listeMesure = Collections.EMPTY_LIST;
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){ 
            mCoteCourant = mSalle.getListeCote().get(0);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){ 
            mCoteCourant = mSalle.getListeCote().get(1);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){ 
            mCoteCourant = mSalle.getListeCote().get(2);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){ 
            mCoteCourant = mSalle.getListeCote().get(3);
        }   
        Accessoire newPrise = Accessoire.AccessoireBuilder("Prise", mousePoint, Element.Type.PRISE,listeMesure,mCoteCourant);
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){
        mSalle.getListeCote().get(0).ajouterAccessoire(newPrise);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){
        mSalle.getListeCote().get(1).ajouterAccessoire(newPrise);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){
        mSalle.getListeCote().get(2).ajouterAccessoire(newPrise);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){
        mSalle.getListeCote().get(3).ajouterAccessoire(newPrise);
        }
    }
    
    private void addRetourdair(Point mousePoint)
    {
    /*
    Toujours centré sur la largeur au milieu du panneau
    Largeur configurable
    Trouer les panneaux intérieurs
    Même hauteur et distance avec le sol pour tous
    Trou en haut du panneau, même largeur que Retour d'air, hauteur configurable par la salle
    Visible lorsque vue de dessus
    */  
        List<MesureImperial> listeMesure  = Collections.EMPTY_LIST;
                if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){ 
            mCoteCourant = mSalle.getListeCote().get(0);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){ 
            mCoteCourant = mSalle.getListeCote().get(1);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){ 
            mCoteCourant = mSalle.getListeCote().get(2);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){ 
            mCoteCourant = mSalle.getListeCote().get(3);
        }   
        Accessoire newRetour = Accessoire.AccessoireBuilder("RetourD'air", mousePoint, Element.Type.RETOURDAIR,listeMesure,mCoteCourant);
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){
        mSalle.getListeCote().get(0).ajouterAccessoire(newRetour);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){
        mSalle.getListeCote().get(1).ajouterAccessoire(newRetour);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){
        mSalle.getListeCote().get(2).ajouterAccessoire(newRetour);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){
        mSalle.getListeCote().get(3).ajouterAccessoire(newRetour);
        }
    }
   
    /*private void addSeparateur(Point mousePoint)
    {

    }*/
            
    public void add(Modes mode, Point mousePoint)        
    {
        if(null != mode)
        switch (mode) {
            case FENETRE:
                addFenetre(mousePoint);
                break;
            case MUR:
                addMur(mousePoint);
                break;
            case PORTE:
                addPorte(mousePoint);
                break;
            case PRISE:
                addPrise(mousePoint);
                break;
            case RETOURDAIR:
                addRetourdair(mousePoint);
                break;
            //case SEPARATEUR:
                //addSeparateur(mousePoint);
                //break;
            default:
                break;
        }
        notifyObserversForUpdatedSalle();
    }
    
    public List<Cote> getListeCotes()
    {
        return mSalle.getListeCote();
    }
    
    public int getNumberCotes()
    {
        return mSalle.getNumberCotes();
    }
    
    public List<Mur> getListeMurs(Cote.listeCote c)
    {
        List<Cote> listeCote = mSalle.getListeCote();
        for(Cote cote1: listeCote)
        {
            if(cote1.getNom() == c)
            {
                return cote1.getListeMurs();
            }
        }
        return null;
    }

    public void undo()
    {
        this.mSalle.undo();
    }

    public void redo()
    {
        this.mSalle.redo();
    }
    
    public List<Accessoire> getListeAccessoires(Cote.listeCote c)
    {
        List<Cote> listeCote = mSalle.getListeCote();
        for(Cote cote1: listeCote)
        {
            if(cote1.getNom() == c)
            {
                return cote1.getListeAccessoires();
            }
        }
        return null;
    }
    
    public void switchSelectionStatus(double x, double y)
    {
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){
        mSalle.getListeCote().get(0).switchSelectionStatus(x, y);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){
        mSalle.getListeCote().get(1).switchSelectionStatus(x, y);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){
        mSalle.getListeCote().get(2).switchSelectionStatus(x, y);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){
        mSalle.getListeCote().get(3).switchSelectionStatus(x, y);
        }
        notifyObserversForUpdatedSalle();
    }
    
    public void updateSelectedItemsPositions(Point delta) {
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Nord){
        mSalle.getListeCote().get(0).updateSelectedItemsPosition(delta);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Sud){
        mSalle.getListeCote().get(1).updateSelectedItemsPosition(delta);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Est){
        mSalle.getListeCote().get(2).updateSelectedItemsPosition(delta);
        }
        if(mSalle.getCoteActif()==Salle.listeCoteActif.Ouest){
        mSalle.getListeCote().get(3).updateSelectedItemsPosition(delta);
        }
        notifyObserversForUpdatedSalle();
    }

    public Salle getSalle() { return mSalle; }
    public void setSalle(Salle salle) { this.mSalle = salle; }

    public void newSalle()
    {
        Point point = new Point(50,50);
        Salle salle = new Salle(point);
        this.setSalle(salle);
    }
    
    public void newSalle (MesureImperial pLonguer,MesureImperial pLargeur,MesureImperial pHauteur, MesureImperial pEpaisseur){
        Point point = new Point(50,50);
        this.mSalle = new Salle(point,pLonguer,pLargeur, pHauteur, pEpaisseur);
    }
    // Grille
    public void modifierGrilleActive (boolean grilleActive)
    {
        mSalle.modifierGrilleActive(grilleActive);
    }
    
    public void modifierTailleGrille (double tailleGrille)
    {
        mSalle.modifierTailleGrille(tailleGrille);
    }
    
    public boolean isGrilleActive ()
    {
        return mSalle.isGrilleActive();
    }
    
    public double tailleGrille()
    {
        return mSalle.tailleGrille();
    }
    
    public Point pointClickedAjustedGrille(int largeurPanel, int hauteurPanel, Point clickedPoint)
    {
        if (mSalle.isGrilleActive())
        {
            return mSalle.pointPlusProcheGrille(largeurPanel, hauteurPanel, clickedPoint);
        }
        else
        {
            return clickedPoint;
        }
    }
    
    public void enregistrerProjet()
    {
        String path = Paths.get("").toAbsolutePath().toString();
        JFileChooser chooser = new JFileChooser(path);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier Muracle", "mur");
        chooser.addChoosableFileFilter(filter);
        int validation = chooser.showSaveDialog(null);
        if (validation == JFileChooser.APPROVE_OPTION)
        {
            File pathFile = chooser.getSelectedFile();
            
            try
            {
                FileOutputStream fileOut = new FileOutputStream(pathFile + ".mur");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this.mSalle);
                out.close();
                fileOut.close();
                mFichier = path;
                JOptionPane.showMessageDialog(null, "La salle est enregistrée.", "Enregistrer une salle", JOptionPane.INFORMATION_MESSAGE);
                mSalle.setPath(pathFile);
            }
            catch(IOException i)
            {
                i.printStackTrace();
            }
        }
    }
    
    public void chargerProjet()
    {
        String path = Paths.get("").toAbsolutePath().toString();
        JFileChooser chooser = new JFileChooser(path);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier Muracle", "mur");
        chooser.addChoosableFileFilter(filter);
        chooser.showOpenDialog(null);
        File curFile = chooser.getSelectedFile();
        
        try
        {
            FileInputStream fileIn = new FileInputStream(new File(String.valueOf(curFile)));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.mSalle = (Salle) in.readObject();
            in.close();
            fileIn.close();
            mFichier = path;
            System.out.println(curFile);
            curFile = new File(String.valueOf(curFile).substring(0, String.valueOf(curFile).lastIndexOf('.')));
            mSalle.setPath(curFile);
        }
        catch(IOException i)
        {
            i.printStackTrace();
            return;
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("Salle non trouvée");
            c.printStackTrace();
            return;
        }
    }
    
    public String reqFichier()
    {
        return mFichier;
    }
    

    public Accessoire itemPlusProche(Modes mode, int px, int py)
    {
        resetSelectionStatus();
        Point userPoint = new Point(px,py);
        switch (mode) {
            case FENETRE:
                TreeMap<Integer, Accessoire> fenetres = new TreeMap<>();
                for(Cote coteClick : getListeCotes())
                {
                    for(Accessoire acc : coteClick.getListeAccessoires())
                    {
                        if(acc.getName().equals("Fenêtre"))
                        {
                            int distance = (int) acc.getPoint().distanceSq(userPoint);
                            fenetres.put(distance, acc);
                        }
                    }
                }
                return fenetres.firstEntry().getValue();
            case RETOURDAIR:
                TreeMap<Integer, Accessoire> retourdair = new TreeMap<>();
                for(Cote coteClick : getListeCotes())
                {
                    for(Accessoire acc : coteClick.getListeAccessoires())
                    {
                        if(acc.getName().equals("Retour d'air"))
                        {
                            int distance = (int) acc.getPoint().distanceSq(userPoint);
                            retourdair.put(distance, acc);
                        }
                    }
                }
                return retourdair.firstEntry().getValue();
            case PRISE:
                TreeMap<Integer, Accessoire> prises = new TreeMap<>();
                for(Cote coteClick : getListeCotes())
                {
                    for(Accessoire acc : coteClick.getListeAccessoires())
                    {
                        if(acc.getName().equals("Prise électrique"))
                        {
                            int distance = (int) acc.getPoint().distanceSq(userPoint);
                            prises.put(distance, acc);
                        }
                    }
                }
                return prises.firstEntry().getValue();
            case PORTE:
                TreeMap<Integer, Accessoire> portes = new TreeMap<>();
                for(Cote coteClick : getListeCotes())
                {
                    for(Accessoire acc : coteClick.getListeAccessoires())
                    {
                        if(acc.getName().equals("Porte"))
                        {
                            int distance = (int) acc.getPoint().distanceSq(userPoint);
                            portes.put(distance, acc);
                        }
                    }
                }
                return portes.firstEntry().getValue();
            default:
                return null;
        }
    }
    
    public Mur separateurPlusProche(int px, int py)
    {
        return mSalle.separateurPlusProche(px, py);
    }
    
    /*public boolean collisionPolygonAccessoires(Polygon p)
    {
        for(Accessoire acc: mCoteCourant.getListeAccessoires())
        {
            if(p.intersects(acc.getPolygone().getBounds2D()))
            {
                return true;
            }
        }
        return false;
    }
    
    /*public boolean collisionPolygonSeparateur(Polygon p)
    {
        for(Mur mur: mCoteCourant.getListeMurs())
        {
            if(true)
            {
                return true;
            }
        }
        return false;
    }*/
    
     public boolean collisionPolygon (Polygon a)
     {
         Area area = new Area(a);
        //area.intersect(new Area(B));
        boolean estVide = false;
         
        for(Accessoire acc: mCoteCourant.getListeAccessoires())
         {
             area.intersect(new Area((Shape) acc));
             estVide = area.isEmpty();
         }
         return estVide;
     }
     
    public boolean verifierSuperpositionElement(Point positionSouris, int largeur, int hauteur)
    {
        boolean estValide = true;
        Polygon souris = new Polygon();
        Point haut_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y+hauteur);
        Point haut_gaucheSouris = new Point(positionSouris.x,positionSouris.y+hauteur);
        Point bas_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y);
        Point bas_gaucheSouris = new Point(positionSouris.x, positionSouris.y);
        souris.addPoint(haut_droiteSouris.x, haut_droiteSouris.y);
        souris.addPoint(haut_gaucheSouris.x, haut_gaucheSouris.y);
        souris.addPoint(bas_droiteSouris.x, bas_gaucheSouris.y);
        souris.addPoint(bas_gaucheSouris.x, bas_droiteSouris.y);
        for(Cote coteClick : getListeCotes())
        {
            for(Accessoire acc : coteClick.getListeAccessoires())
            {
                Point bas_gaucheAcc = new Point(acc.getPoint().x, acc.getPoint().y);
                Point bas_droiteAcc = new Point(acc.getPoint().x+acc.getLargeur().getEntier(), acc.getPoint().y);
                Point haut_gaucheAcc = new Point(acc.getPoint().x, acc.getPoint().y+acc.getHauteur().getEntier());
                Point haut_droitAcc = new Point(acc.getPoint().x+acc.getLargeur().getEntier(), acc.getPoint().y+acc.getHauteur().getEntier());
                        
                if (acc.contains(haut_droiteSouris.getX(), haut_droiteSouris.getY()))
                {
                    estValide = false;
                    return estValide;
                }
                else if (acc.contains(haut_gaucheSouris.getX(), haut_gaucheSouris.getY())){
                    estValide = false;
                    return estValide;
                }
                else if (acc.contains(bas_droiteSouris.getX(), bas_droiteSouris.getY())){
                    estValide = false;
                    return estValide;
                    
                }
                else if (acc.contains(bas_gaucheSouris.getX(), bas_gaucheSouris.getY())){
                    estValide = false;
                    return estValide;
                }
                else if (souris.contains(bas_gaucheAcc)){
                    estValide = false;
                    return estValide;
                }
                else if (souris.contains(bas_droiteAcc)){
                    estValide = false;
                    return estValide;
                }
                else if (souris.contains(haut_gaucheAcc)){
                    estValide = false;
                    return estValide;
                }
                else if (souris.contains(haut_droitAcc)){
                    estValide = false;
                    return estValide;
                }
            }
        }
            
        return estValide;
    }

     
    public boolean verifierSuperpositionSeparateur(Point positionSouris, int largeur, int hauteur)
     {
        boolean estValide = true;
        Polygon souris = new Polygon();
        Point haut_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y+hauteur);
        Point haut_gaucheSouris = new Point(positionSouris.x,positionSouris.y+hauteur);
        Point bas_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y);
        Point bas_gaucheSouris = new Point(positionSouris.x, positionSouris.y);
        souris.addPoint(haut_droiteSouris.x, haut_droiteSouris.y);
        souris.addPoint(haut_gaucheSouris.x, haut_gaucheSouris.y);
        souris.addPoint(bas_droiteSouris.x, bas_gaucheSouris.y);
        souris.addPoint(bas_gaucheSouris.x, bas_droiteSouris.y);
        for(Cote coteClick : getListeCotes())
        {
            for(Mur mur : coteClick.getListeMurs())
            {
                Separateur listePoint = mur.getListeSeparateurs().get(0);
                if (bas_gaucheSouris.x < listePoint.getPoint().x && bas_droiteSouris.x > listePoint.getPoint().x) 
                {
                estValide = false;
                return estValide; 
                }
            }
        }
        return estValide;
     }

    public boolean verifierValiditeAccessoireExterieur(Point positionSouris, int largeur, int hauteur)
    {
        boolean estValide = true;
        Polygon souris = new Polygon();
        Point haut_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y-hauteur);
        Point haut_gaucheSouris = new Point(positionSouris.x,positionSouris.y-hauteur);
        Point bas_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y);
        Point bas_gaucheSouris = new Point(positionSouris.x, positionSouris.y);
        souris.addPoint(haut_droiteSouris.x, haut_droiteSouris.y);
        souris.addPoint(haut_gaucheSouris.x, haut_gaucheSouris.y);
        souris.addPoint(bas_droiteSouris.x, bas_gaucheSouris.y);
        souris.addPoint(bas_gaucheSouris.x, bas_droiteSouris.y);
        
        Polygon test = mSalle.getmPolygoneElevationExterieur();
        
        int[] xpoints = souris.xpoints;
        int[] ypoints = souris.ypoints;
        for (int i = 0, j = 0; i < souris.npoints ; i++,j++) {
             estValide = test.contains(new Point(xpoints[i], ypoints[j]));
             if(!estValide) break;   
        }
        return estValide;
    } 
    
    public boolean verifierValiditeAccessoireInterieur(Point positionSouris, int largeur, int hauteur)
    {
        boolean estValide = true;
        Polygon souris = new Polygon();
        Point haut_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y-hauteur);
        Point haut_gaucheSouris = new Point(positionSouris.x,positionSouris.y-hauteur);
        Point bas_droiteSouris = new Point(positionSouris.x+largeur, positionSouris.y);
        Point bas_gaucheSouris = new Point(positionSouris.x, positionSouris.y);
        souris.addPoint(haut_droiteSouris.x, haut_droiteSouris.y);
        souris.addPoint(haut_gaucheSouris.x, haut_gaucheSouris.y);
        souris.addPoint(bas_droiteSouris.x, bas_gaucheSouris.y);
        souris.addPoint(bas_gaucheSouris.x, bas_droiteSouris.y);
        
        Polygon test = mSalle.getmPolygoneElevationInterieur();
        
        int[] xpoints = souris.xpoints;
        int[] ypoints = souris.ypoints;
        for (int i = 0, j = 0; i < souris.npoints ; i++,j++) {
             estValide = test.contains(new Point(xpoints[i], ypoints[j]));
             if(!estValide) break;   
        }
        return estValide;
    }
    
    public boolean verifierValiditeLargeurAccessoire(Accessoire p1, int largeur1)
    {
        boolean estValide = true;
           
        Point bas_gaucheAcc = new Point(p1.getPoint().x, p1.getPoint().y);
        Point bas_droiteAcc = new Point(p1.getPoint().x+largeur1, p1.getPoint().y);
        
        for(Cote coteClick : getListeCotes())
        {
            for(Mur mur : coteClick.getListeMurs())
            {
                Separateur listePoint = mur.getListeSeparateurs().get(0);
                if (bas_gaucheAcc.x < listePoint.getPoint().x && bas_droiteAcc.x > listePoint.getPoint().x)
                {
                estValide = false;
                return estValide; 
                }
            }
        }
        return estValide;
     }
        
    public boolean verifierValiditeHauteurAccessoire(Accessoire p1, int hauteur1)
    {
        boolean estValide = true;
           
        Point bas_gaucheAcc = new Point(p1.getPoint().x, p1.getPoint().y);
        Point haut_gaucheAcc = new Point(p1.getPoint().x, p1.getPoint().y-hauteur1);
        
        Polygon test = mSalle.getmPolygoneElevationExterieur();
        
        if (!test.contains(bas_gaucheAcc.getX(), bas_gaucheAcc.getY()))
                {
                    estValide = false;
                    return estValide;
                }
        else if (!test.contains(haut_gaucheAcc.getX(), haut_gaucheAcc.getY()))
                {
                    estValide = false;
                    return estValide;
                }
        return estValide;
     }  
    
    public boolean verifierSeparateurSurAccessoire(Point positionSouris)
    {
        boolean estValide = true;
        
        for(Cote coteClick : getListeCotes())
        {
            for(Accessoire acc : coteClick.getListeAccessoires())
            {
                Point bas_gaucheAcc = new Point(acc.getPoint().x, acc.getPoint().y);
                Point bas_droiteAcc = new Point(acc.getPoint().x+acc.getLargeur().getEntier(), acc.getPoint().y);
                
                if (bas_gaucheAcc.x < positionSouris.x && bas_droiteAcc.x > positionSouris.x)
                {
                    estValide = false;
                    return estValide;
                }
            }
        }
        return estValide;
    }
    public boolean verifierAccessoireSurSeparatuer(Accessoire accessoire , Point newPoint)
    {
        boolean estValide = true;
        int x1 = newPoint.x;
        int x2 = newPoint.x + accessoire.getLargeur().getEntier();

        Cote cote = getSalle().getListeCote().get(0);;
        switch (this.getSalle().getCoteActif()){
            case Nord:
                cote = getSalle().getListeCote().get(0);
                break;
            case Sud:
                cote = getSalle().getListeCote().get(1);
                break;
            case Est:
                cote = getSalle().getListeCote().get(2);
                break;
            case Ouest:
                cote = getSalle().getListeCote().get(3);
                break;
        }

        for(Mur mur : cote.getListeMurs())
        {
            List<Integer> pointSeparateurElev = mur.getListeSeparateurs().get(0).getmPointSeparateurElev();
            if(x1 < pointSeparateurElev.get(0) && pointSeparateurElev.get(0) < x2){
                return false;
            }
        }

        return estValide;
    }
    
    public String SVGdecoupeMurInterieur()
    {
        String test2 = "<polygon points=\"";
        for(Cote coteClick : getListeCotes())
        {
            for(Mur mur : coteClick.getListeMurs())
            {
                if (mur.isSelected())
                {
                for(Polygon p : mur.getDecoupeInterieur())
                {
                    int[] xpoints = p.xpoints;
                    int[] ypoints = p.ypoints;

                    for (int i = 0 ; i < xpoints.length ; i++)
                    {
                        test2 += Integer.toString(xpoints[i]);
                        test2 += ",";
                        test2 += Integer.toString(ypoints[i]);
                        test2 += " ";
                    }
                }
                }
            }
        }
    return test2;
}
     public String SVGdecoupeMurExterieur()
    {
        String test2 = "<polygon points=\"";
        for(Cote coteClick : getListeCotes())
        {
            for(Mur mur : coteClick.getListeMurs())
            {
                if (mur.isSelected())
                {
                
                for(Polygon p : mur.getDecoupeExterieur())
                {
                    int[] xpoints = p.xpoints;
                    int[] ypoints = p.ypoints;

                    for (int i = 0 ; i < xpoints.length ; i++)
                    {
                        test2 += Integer.toString(xpoints[i]);
                        test2 += ",";
                        test2 += Integer.toString(ypoints[i]);
                        test2 += " ";
                    }
                }
                }
            }
        }
    return test2;
}


}// Class
    

        
    

