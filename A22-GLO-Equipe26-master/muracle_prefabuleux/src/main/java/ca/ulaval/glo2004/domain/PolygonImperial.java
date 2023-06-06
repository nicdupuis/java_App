/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;
import java.awt.*;
import java.util.List;


/**
 *
 * @author sebastien
 */
public class PolygonImperial{
    private Element mElement; 
    private Color mCouleur;
    private List<PointImperial> listePoints;
    private Polygon polygon;

    public PolygonImperial(Element mElement, Color mCouleur, List<PointImperial> listePoints) {
        this.mElement = mElement;
        this.mCouleur = mCouleur;
        this.listePoints = listePoints;
    }
    
    public PolygonImperial(Element pElement){
        this.mElement = pElement; 
        mCouleur = Color.BLACK;
        Echelle instance = Echelle.getInstance();
        //Point originePixel = pElement.getPoint()
        PointImperial origine  = instance.pixelsVersImperial(pElement.getPoint().x,pElement.getPoint().y);
        MesureImperial Zero = new MesureImperial(0,0,1);
        this.listePoints.add(origine);
        this.listePoints.add(PointImperial.addPointImp( origine, new PointImperial(pElement.getX(), Zero)));
        this.listePoints.add(PointImperial.addPointImp( origine, new PointImperial(pElement.getX(), pElement.getY())));
        this.listePoints.add(PointImperial.addPointImp( origine, new PointImperial(Zero,pElement.getY())));
        }
    private double calculAire() {
        int n = listePoints.size();
        double a = 0.0;
        for (int i = 0; i < n - 1; i++) {
            a += listePoints.get(i).getdx() * listePoints.get(i + 1).getdy() - listePoints.get(i + 1).getdx() * listePoints.get(i).getdy();
        }
        return a + listePoints.get(n - 1).getdx() * listePoints.get(0).getdy() - listePoints.get(0).getdx() * listePoints.get(n - 1).getdy() / 2.0;
    }
    
    }
