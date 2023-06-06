package ca.ulaval.glo2004.domain.Affichage;
import ca.ulaval.glo2004.domain.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.Polygon;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author equipe26
 */
public class SalleDrawer {
    
    private final Controleur controller;
    private Dimension initialDimension;
    
    public SalleDrawer(Controleur controller, Dimension initialDimension)
    {
        this.controller = controller;
        this.initialDimension = initialDimension;
    }
    
    public void draw(Graphics g)
    {
        drawSalle(g);
        drawMurs(g);
        drawAccessoires(g);
        DrawDecoupe(g);
    }

    private void drawSalle(Graphics g)
    {
        if(controller.getSalle() != null) {
            Salle salle = controller.getSalle();
            salle.calculeDisposition();
            if(salle.getVueCourante() == Salle.vueType.Plan){
                g.drawPolygon(salle.getmPolygonePlanInterieur());
                g.drawPolygon(salle.getmPolygonePlanExterieur());
            } else if (salle.getVueCourante() == Salle.vueType.Elevation)
            {
                if(salle.getTypeCoteActif() == Salle.listeTypeCote.Exterieur)
                    g.drawPolygon(salle.getmPolygoneElevationExterieur());
                else if (salle.getTypeCoteActif() == Salle.listeTypeCote.Interieur)
                    g.drawPolygon(salle.getmPolygoneElevationInterieur());
            }
        }
    }
    private void drawMurs(Graphics g) {
        Salle salle = controller.getSalle();
        if (salle != null) {
            List<Cote> cotes = controller.getListeCotes();
            if (salle.getVueCourante() == Salle.vueType.Plan) {
                for (Cote cote : cotes) {
                    List<Mur> murs = cote.getListeMurs();
                    for (int i = 0; i < murs.size(); i++) {
                        boolean edge = i == 0;
                        Mur mur = murs.get(i);
                        Color color = mur.getColor();
                        g.setColor(color);

                        Separateur separateur = mur.getListeSeparateurs().get(0);
                        separateur.calculeDisposition(edge);
                        g.drawLine(separateur.getX1(), separateur.getY1(), separateur.getX2(), separateur.getY2());
                    }
                }
            }
            else if (salle.getVueCourante() == Salle.vueType.Elevation)
            {
                int index;
                if(salle.getCoteActif() == Salle.listeCoteActif.Nord)
                    index = 0;
                else if (salle.getCoteActif() == Salle.listeCoteActif.Sud)
                    index = 1;
                else if (salle.getCoteActif() == Salle.listeCoteActif.Est)
                    index = 2;
                else
                    index = 3;
                    List<Mur> murs = salle.getListeCote().get(index).getListeMurs();
                    for (int i = 0; i < murs.size(); i++) {
                        boolean edge = i == 0;
                        Mur mur = murs.get(i);
                        Color color = mur.getColor();
                        g.setColor(color);

                        Separateur separateur = mur.getListeSeparateurs().get(0);
                        separateur.calculeDisposition(edge);
                        if(salle.getTypeCoteActif() == Salle.listeTypeCote.Exterieur) {
                            g.drawLine(separateur.getmPointSeparateurElev().get(0), separateur.getmPointSeparateurElev().get(1),
                                    separateur.getmPointSeparateurElev().get(2), separateur.getmPointSeparateurElev().get(3));
                        }
                        else if (salle.getTypeCoteActif() == Salle.listeTypeCote.Interieur){
                            if(i == 0)
                                continue;
                            else{
                                g.drawLine(separateur.getmPointSeparateurElev().get(0), separateur.getmPointSeparateurElev().get(1),
                                        separateur.getmPointSeparateurElev().get(2), separateur.getmPointSeparateurElev().get(3));
                            }
                        }
                    }

            }
        }
    }
    
    private void drawAccessoires(Graphics g)
    {
        Salle salle = controller.getSalle();
        //Doit on vraiment dessine des accessoire 
        if (salle != null) {
            List<Cote> cotes = controller.getListeCotes();
            Cote coteCourant = null;
            Salle.listeCoteActif lCoteActif = salle.getCoteActif();
            if (null != lCoteActif) //trouve le cote actif.
            switch (lCoteActif) {
                case Nord:
                    coteCourant = cotes.get(0);
                    break;
                case Sud:
                    coteCourant = cotes.get(1);
                    break; 
                case Est:
                    coteCourant = cotes.get(2);
                    break;
                case Ouest:
                    coteCourant = cotes.get(3);
                    break;
                default:
                    break;
            }
            //si vue elevation
        if (salle.getVueCourante() == Salle.vueType.Elevation) 
        {
            for(Accessoire accessoire: coteCourant.getListeAccessoires())
            {
                accessoire.calculeDisposition(coteCourant);
                    //si vue exterieur
                    if(salle.getTypeCoteActif() == Salle.listeTypeCote.Exterieur){
                        if(accessoire.getPerceExterieur()){
                            if(accessoire.isSelected())
                            {
                                g.setColor(Color.YELLOW);
                                g.drawPolygon(accessoire.getPolygonSelected());
                                g.fillPolygon(accessoire.getPolygonSelected());
                            }
                        g.setColor(accessoire.getColor());
                        g.drawPolygon(accessoire.getPolygone());
                        g.fillPolygon(accessoire.getPolygone());
                        }
                    }
                    //si vue interieur
                    if(salle.getTypeCoteActif()==Salle.listeTypeCote.Interieur){
                        if(accessoire.isSelected())
                        {
                            g.setColor(Color.YELLOW);
                            g.drawPolygon(accessoire.getPolygonSelected());
                            g.fillPolygon(accessoire.getPolygonSelected());
                        }
                        if(accessoire.getPerceInterieur()){
                        g.setColor(accessoire.getColor());
                        g.drawPolygon(accessoire.getPolygone());
                        g.fillPolygon(accessoire.getPolygone());
                        }
                    }
            }
        }
        if (salle.getVueCourante() == Salle.vueType.Plan){
            for (Cote cote : controller.getListeCotes() ){
            
                for(Accessoire accessoire : cote.getListeAccessoires()){
                    
                if(accessoire.getTrouDessus()){
                    accessoire.calculeDisposition(cote);
                    g.setColor(accessoire.getColor());
                    g.drawPolygon(accessoire.getPolygoneDessus());
                    g.fillPolygon(accessoire.getPolygoneDessus());
                }
                }
            }
            /*for(Accessoire accessoire: coteCourant.getListeAccessoires())
            {
                accessoire.calculeDisposition();
                if(accessoire.isSelected())
                {
                    g.setColor(Color.YELLOW);
                    g.drawPolygon(accessoire.getPolygonSelected());
                    g.fillPolygon(accessoire.getPolygonSelected());
                }
                g.setColor(accessoire.getColor());
                g.drawPolygon(accessoire.getPolygoneDessus());
                g.fillPolygon(accessoire.getPolygone());
            }*/
        }
        }// 
    }//Fin DrawAccessoire
      private void DrawDecoupe(Graphics g){
        if((controller.getSalle()!=null) && (controller.getSalle().getVueCourante() == Salle.vueType.Decoupe)){
            int i = 0; 
            boolean last=false;
            boolean flag=false;
            Mur muractif = null;
            aa:
            for (Cote c : controller.getListeCotes() ){
                bb:
                for(Mur m : c.getListeMurs()){
                    if(i+1 == c.getListeMurs().size()){
                        last = true;
                    }
                    if (m.isSelected()){
                        muractif = m;
                        if(i==0){
                            m.calculDecoupe(true,last);
                            break aa;
                        }else{
                            m.calculDecoupe(false, last);
                            break aa;
                        }
                    }
                }
            }
            if (muractif!=null){
            g.setColor(Color.BLUE);
            if(controller.getSalle().getTypeCoteActif()==Salle.listeTypeCote.Exterieur){
                for (Polygon p : muractif.getDecoupeExterieur()){
                g.drawPolygon(p);
                }
            }else{
                for (Polygon p : muractif.getDecoupeInterieur()){
                g.drawPolygon(p);
                }
            }
            }
        }
    }
    
    
}//Fin Class
    
    
