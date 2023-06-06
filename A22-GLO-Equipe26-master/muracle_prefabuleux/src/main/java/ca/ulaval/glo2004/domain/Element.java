/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;
 import java.awt.Color;
 import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.Stack;
/**
 *
 * @author equipe26
 */
public abstract class Element implements Serializable{
    private MesureImperial mX;
    private MesureImperial mY;
    private Type mType;
    protected enum Type {COTE, SEPARATEUR, RETOURDAIR, PRISE, FENETRE, PORTE, MUR};
    private Point mpoint;
    private boolean selectionStatus;
    protected Polygon polygon;
    private MesureImperial mLargeur;
    private MesureImperial mHauteur;
    
    public Element(){}
    
    public Element(Point point)
    {
        this.mpoint = point;
        this.selectionStatus = false;
    }
    
    public Point getPoint()
    {
        return mpoint;
    }
    
    public void setPoint(Point newPoint)
    {
        this.mpoint = newPoint;
    }
    
    public MesureImperial getX()
    {
        return mX;
    }
    
    public MesureImperial getY()
    {
        return mY;
    }
    
    public void setX(MesureImperial newX)
    {
        mX = newX;
    }
    
    public void setY(MesureImperial newY)
    {
        mY = newY;
    }
    
    public void switchSelectionStatus()
    {
        this.selectionStatus = !selectionStatus;
    }
    
    public boolean isSelected()
    {
        return selectionStatus;
    }
    
    public void setSelectionStatus(boolean status)
    {
        this.selectionStatus = status;
    }
    
    protected void setPolygon(Polygon polygon)
    {
        this.polygon = polygon;
    }
    
    public boolean intersect(Element e)
    {
        return polygon.getBounds2D().intersects(e.polygon.getBounds2D());
    }
    
    public boolean contains(double x, double y)
    {
        return polygon.contains(x, y);
    }
    
    public void translate(double deltaX, double deltaY)
    {
        this.mpoint.x = (int) (this.mpoint.getX() + deltaX);
        this.mpoint.y = (int) (this.mpoint.getY() + deltaY);
    }
    
    public void setLargeur(MesureImperial largeur)
    {
        this.mLargeur = largeur;
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
    
}
