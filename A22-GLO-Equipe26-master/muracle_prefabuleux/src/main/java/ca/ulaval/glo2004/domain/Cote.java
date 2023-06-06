package ca.ulaval.glo2004.domain;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author equipe26
 */
public class Cote extends Element {
    public enum listeCote {Nord,Sud,Est,Ouest};
    private listeCote nom;
    private Salle mSalle;
    private MesureImperial mZ;
    private Polygon[] mPolygonePlan;
    private Polygon[] mPolygoneElevation;
    private Accessoire[] mAccessoires;
    private List<Mur> listeMurs;
    private List<Accessoire> listeAccessoires;
    private List<Separateur> listeSeparateurs;
    private List<PolygonImperial> listePolygonesPlan;
    private List<PolygonImperial> listePolygonesElevation;
    private MesureImperial mLongueurCote;
    private Point mPointElevation;

    
    
    public Cote(Point point, Salle salle, listeCote direction)
    {
        super(point);
        this.nom = direction;
        this.listeMurs = new LinkedList<Mur>();
        this.ajouterMur(new Mur(point, this));
        this.ajouterMur(new Mur(point, this));
        this.ajouterMur(new Mur(point, this));
        this.ajouterMur(new Mur(point, this));
        this.ajouterMur(new Mur(point, this));
        this.recalculePoints(listeMurs);
        this.ajouterSeparateur();
        listeAccessoires = new LinkedList<>();
        listeSeparateurs = new LinkedList<>();
        listePolygonesPlan = new LinkedList<PolygonImperial>();
        listePolygonesElevation = new LinkedList<PolygonImperial>();
        this.mLongueurCote = calculeLongueurCote(this);
        this.mSalle = salle;
    }
    
    public void ajouterMur(Mur aMur)
    {
        listeMurs.add(aMur);
    }

    public void retirerMur(Mur mur) {listeMurs.remove(mur);}
    
    public Mur mur(Point point)
    {
        for(Mur mur: getListeMurs())
        {
            if(mur.getPoint() == point)
            {
                return mur;
            }
        }
        return null;
    }
    
    //changer parametre pour position:Imperial ?
    public void ajouterSeparateur()
    {
        for(Mur mur : listeMurs)
        {
            mur.ajouterSeparateur(new Separateur(mur.getPoint(),mur));
        }
    }
    
    public void ajouterAccessoire(Accessoire aAccessoire)
    {
        listeAccessoires.add(aAccessoire);
    }
    
    public Accessoire accessoire(Point point)
    {
        for(Accessoire acc: getListeAccessoires())
        {
            if(acc.getPoint() == point)
            {
                return acc;
            }
        }
        return null;
    }
    
    public void ajouterPolygonePlan(PolygonImperial aPolygone)
    {
        listePolygonesPlan.add(aPolygone);
    }
    
    public void ajouterPolygoneElevation(PolygonImperial aPolygone)
    {
        listePolygonesElevation.add(aPolygone);
    }
    
    public List<Mur> getListeMurs()
    {
        return listeMurs;
    }
    
    public void setListeMurs(List<Mur> liste)
    {
        this.listeMurs = liste;
    }

    public void setmPointElevation(Point mPointElevation) {
        this.mPointElevation = mPointElevation;
    }

    public Point getmPointElevation() {
        return mPointElevation;
    }

    public int getNumberMurs()
    {
        return listeMurs.size();
    }

    public Salle getmSalle() {
        return mSalle;
    }

    public MesureImperial getLongueurCote() { return this.mLongueurCote;}
    
    public List<Accessoire> getListeAccessoires()
    {
        return listeAccessoires;
    }
    
    public void setListeAccessoires(List<Accessoire> liste)
    {
        this.listeAccessoires = liste;
    }
    
    public List<Separateur> getListeSeparateurs()
    {
        return listeSeparateurs;
    }
    
    public void setListeSeparateurs(List<Separateur> liste)
    {
        this.listeSeparateurs = liste;
    }
    
    public List<PolygonImperial> getListePolygonesPlan()
    {
        return listePolygonesPlan;
    }
    
    //ajouter en parametre boolean exterieur
    public List<PolygonImperial> getListePolygonesElevation()
    {
        return listePolygonesElevation;
    }
    
    public void switchSelectionStatus(double x, double y)
    {
        for(Accessoire accessoire: this.listeAccessoires)
        {
            if(accessoire.contains(x, y))
            {
                accessoire.switchSelectionStatus();
            }
        }
    }
    
    void updateSelectedItemsPosition(Point delta) {
        for (Accessoire accessoire : this.listeAccessoires) {
                if (accessoire.isSelected()) {
                        accessoire.translate(delta);
                }
        }    
    }
    public listeCote getNom() { return this.nom;}

    public void setNom (listeCote nom) {this.nom = nom;}

    public MesureImperial calculeLongueurCote(Cote cote)
    {
        this.mLongueurCote = new MesureImperial(0,0,1);
        List<Mur> murs = this.getListeMurs();
        for (Mur mur : murs)
        {
            MesureImperial longueur = mur.getmLongueurExterieur();
            this.mLongueurCote = MesureImperial.add(mLongueurCote, longueur);
        }
        return this.mLongueurCote;
    }

    public void recalculePoints(List<Mur> listeMurs)
    {
        for(int i = 0; i < listeMurs.size(); i++)
        {
            Mur mur = listeMurs.get(i);
            Point newPoint;
            if(i == 0)
            {
                newPoint = new Point(mur.getmCote().getPoint());
            }
            else
            {
                int prevIndex = i - 1;
                Mur prevMur = listeMurs.get(prevIndex);
                Point prevPoint = prevMur.getPoint();
                if (this.getNom() == listeCote.Est) {
                    newPoint = new Point(prevPoint.x, prevPoint.y + prevMur.getmLongueurExterieur().getEntier());
                } else if (this.getNom() == listeCote.Ouest) {
                    newPoint = new Point(prevPoint.x, prevPoint.y - prevMur.getmLongueurExterieur().getEntier());
                } else if (this.getNom() == listeCote.Sud) {
                    newPoint = new Point(prevPoint.x - prevMur.getmLongueurExterieur().getEntier(), prevPoint.y);
                } else {
                    newPoint = new Point(prevPoint.x + prevMur.getmLongueurExterieur().getEntier(), prevPoint.y);
                }
            }
            mur.setPoint(newPoint);
        }
    }

    public void recalculePointsElevation(List<Mur> listeMurs)
    {
        for(int i = 0; i < listeMurs.size(); i++)
        {
            Mur mur = listeMurs.get(i);
            Point newPoint;
            if(i == 0)
            {
                if (this.getNom() == listeCote.Est || this.getNom() == listeCote.Nord) {
                    newPoint = new Point(mur.getmCote().getmSalle().getPoint());
                }
                else if (this.getNom() == listeCote.Ouest){
                    newPoint = new Point(mur.getmCote().getmSalle().getPoint().x + mur.getmCote().getmSalle().getLongueur().getEntier(),mur.getmCote().getmSalle().getPoint().y);
                }
                else {
                    newPoint = new Point(mur.getmCote().getmSalle().getPoint().x + mur.getmCote().getmSalle().getLargeur().getEntier(),mur.getmCote().getmSalle().getPoint().y);
                }
            }
            else
            {
                int prevIndex = i - 1;
                Mur prevMur = listeMurs.get(prevIndex);
                Point prevPoint = prevMur.getmPointElevation();
                if (this.getNom() == listeCote.Nord) {
                    if(i == mur.getmCote().getListeMurs().size() - 1)
                        newPoint = new Point(mur.getmCote().getmSalle().getListeCote().get(2).getPoint().x - mur.getmLongueurExterieur().getEntier(), prevPoint.y);
                    else
                        newPoint = new Point(prevPoint.x + prevMur.getmLongueurExterieur().getEntier(), prevPoint.y);
                }
                else if(this.getNom() == listeCote.Est){
                    if(i == mur.getmCote().getListeMurs().size() - 1)
                        newPoint = new Point(mur.getmCote().getmSalle().getListeCote().get(1).getPoint().y - mur.getmLongueurExterieur().getEntier(), prevPoint.y);
                    else
                        newPoint = new Point(prevPoint.x + prevMur.getmLongueurExterieur().getEntier(), prevPoint.y);
                }
                else {
                    newPoint = new Point(prevPoint.x - prevMur.getmLongueurExterieur().getEntier(), prevPoint.y);
                }
            }
            mur.setmPointElevation(newPoint);
        }
    }

    public void recalculeLongueurMur(Cote cote){
        for(int i = 0; i < listeMurs.size(); i++){
            if(cote.getNom() == listeCote.Nord){
                Mur mur = listeMurs.get(i);
                if(i == listeMurs.size() - 1){
                    mur.setmLongueurExterieur(new MesureImperial(cote.getmSalle().getListeCote().get(2).getPoint().x - mur.getPoint().x,0,1));
                }
                else {
                    Mur nextMur = listeMurs.get(i + 1);
                    mur.setmLongueurExterieur(new MesureImperial(nextMur.getPoint().x - mur.getPoint().x,0,1));
                }
            }
            else if (cote.getNom() == listeCote.Sud)
            {
                Mur mur = listeMurs.get(i);
                if(i == listeMurs.size() - 1){
                    mur.setmLongueurExterieur(new MesureImperial(mur.getPoint().x - cote.getmSalle().getListeCote().get(3).getPoint().x,0,1));
                }
                else {
                    Mur nextMur = listeMurs.get(i + 1);
                    mur.setmLongueurExterieur(new MesureImperial(mur.getPoint().x - nextMur.getPoint().x,0,1));
                }
            }
            else if (cote.getNom() == listeCote.Ouest)
            {
                Mur mur = listeMurs.get(i);
                if(i == listeMurs.size() - 1){
                    mur.setmLongueurExterieur(new MesureImperial(mur.getPoint().y - cote.getmSalle().getListeCote().get(0).getPoint().y,0,1));
                }
                else {
                    Mur nextMur = listeMurs.get(i + 1);
                    mur.setmLongueurExterieur(new MesureImperial(mur.getPoint().y - nextMur.getPoint().y,0,1));
                }
            }
            else
            {
                Mur mur = listeMurs.get(i);
                if(i == listeMurs.size() - 1){
                    mur.setmLongueurExterieur(new MesureImperial(cote.getmSalle().getListeCote().get(1).getPoint().y - mur.getPoint().y,0,1));
                }
                else {
                    Mur nextMur = listeMurs.get(i + 1);
                    mur.setmLongueurExterieur(new MesureImperial(nextMur.getPoint().y - mur.getPoint().y,0,1));
                }
            }
        }
    }

    public int getIndexMur(Cote cote, Mur mur){
        TreeMap<Integer,Mur> difference = new TreeMap<>();
        for(Mur murs : cote.getListeMurs())
        {
          if(cote.getNom() == listeCote.Nord || cote.getNom() == listeCote.Sud)
          {
              difference.put(Math.abs(mur.getPoint().x - murs.getPoint().x), murs);
          }
          else
          {
              difference.put((mur.getPoint().y - murs.getPoint().y), murs);
          }
        }

        int index = cote.getListeMurs().indexOf(difference.firstEntry().getValue());
        if (index == 0)
            return 1;
        else if (difference.firstEntry().getKey() < 0)
            return index + 1;
        else return index + 1;

    }
} 


    
    

    
     
    

