/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;


/**
 *
 * @author sebastien
 */
public class PointImperial {
    private MesureImperial mX;
    private MesureImperial mY;
    private double dX;
    private double dY;

    public PointImperial(MesureImperial x, MesureImperial y)
    {
        this.mX = x;
        this.mY = y;
        this.dX = (double)x.getEntier()+(double)(x.getNumerateur()/x.getDenominateur());
        this.dY = (double)y.getEntier()+(double)(y.getNumerateur()/y.getDenominateur());
    }
    
    public void setmX(MesureImperial pX){
    this.mX = pX;
    this.dX = (double)pX.getEntier()+(double)(pX.getNumerateur()/pX.getDenominateur());
    }
    
    public void setmY(MesureImperial pY){
    this.mY =pY;
    this.dY = (double) pY.getEntier()+(double)(pY.getNumerateur()/pY.getDenominateur());
    }
    
    public MesureImperial X(){
        return this.mX;
    }
    
    public MesureImperial Y(){
        return this.mY;
    }
    
    public Point pixel(){
       Echelle echelle = Echelle.getInstance();
       return echelle.imperialVersPixel(mX, mY);
    }
    
    public double getdx(){
    return dX;
    }
    public double  getdy(){
    return dY;
    }
    
    public static PointImperial addPointImp(PointImperial p1, PointImperial p2)
    {
        return new PointImperial(MesureImperial.add(p1.X(),p2.X()),MesureImperial.add(p1.Y(), p2.Y()));
    }
    


    
}
