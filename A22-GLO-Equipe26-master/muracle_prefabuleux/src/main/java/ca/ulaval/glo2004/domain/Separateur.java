package ca.ulaval.glo2004.domain;
 import java.awt.Color;
 import java.awt.Point;
 import java.util.ArrayList;
 import java.util.List;

/**
 *
 * @author equipe26
 */
public class Separateur extends Element{
    private Color color;
    private Mur mMur;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Point mPoint;
    private Point mPointElevation;
    private List<Integer> mPointSeparateurElev;
    
    public Separateur(Point point, Mur mur)
    {
        super(point);
        this.mMur = mur;
        this.color = Color.BLACK;
        this.mPoint = point;
        this.mPointElevation = point;
        this.mPointSeparateurElev = new ArrayList<Integer>();
    }

    public List<Integer> getmPointSeparateurElev() {
        return mPointSeparateurElev;
    }

    public Color getColor()
    {
        return this.color;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void calculeDisposition(boolean edge) {
        this.mPointSeparateurElev = new ArrayList<>();
        this.mPoint = mMur.getPoint();
        this.mPointElevation = mMur.getmPointElevation();
        int epaisseur = this.mMur.getmCote().getmSalle().getEpaisseurMur().getEntier();
        int hauteur = this.mMur.getmCote().getmSalle().getmHauteur().getEntier();
        this.x1 = mPoint.x;
        this.y1 = mPoint.y;
        if (this.mMur.getmCote().getNom() == Cote.listeCote.Nord) {
            if (edge) {
                this.x2 = mPoint.x + epaisseur;
                this.y2 = mPoint.y + epaisseur;
            } else {
                this.x2 = mPoint.x;
                this.y2 = mPoint.y + epaisseur;
            }
        } else if (this.mMur.getmCote().getNom() == Cote.listeCote.Ouest) {
            if (edge) {
                this.x2 = mPoint.x + epaisseur;
                this.y2 = mPoint.y - epaisseur;
            } else {
                this.x2 = mPoint.x + epaisseur;
                this.y2 = mPoint.y;
            }
        } else if (this.mMur.getmCote().getNom() == Cote.listeCote.Est) {
            if (edge) {
                this.x2 = mPoint.x - epaisseur;
                this.y2 = mPoint.y + epaisseur;
            } else {
                this.x2 = mPoint.x - epaisseur;
                this.y2 = mPoint.y;
            }
        } else {
            if (edge) {
                this.x2 = mPoint.x - epaisseur;
                this.y2 = mPoint.y - epaisseur;
            } else {
                this.x2 = mPoint.x;
                this.y2 = mPoint.y - epaisseur;
            }
        }

        this.mPointSeparateurElev.add(mPointElevation.x);
        this.mPointSeparateurElev.add(mPointElevation.y);
        this.mPointSeparateurElev.add(mPointElevation.x);
        this.mPointSeparateurElev.add(mPointElevation.y + hauteur);
    }
}

