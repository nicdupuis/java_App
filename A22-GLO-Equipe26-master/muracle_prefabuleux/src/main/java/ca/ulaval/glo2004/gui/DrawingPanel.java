package ca.ulaval.glo2004.gui;
 

 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.io.Serializable;
 import javax.swing.JPanel;
 import javax.swing.border.BevelBorder;
 import ca.ulaval.glo2004.domain.Affichage.SalleDrawer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
 
 public class DrawingPanel extends JPanel implements Serializable 
 {
    public Dimension initialDimension;
    private MainWindow mainWindow;
     
    private double mouseX;
    private double mouseY;
     
    public Graphics graphics;
    private boolean grilleActive = false;
    private double gapGrille = 150d;
     
          
     
     // Variables utilisées pour le zoom
    public double zoom = 1;
    public int zoomX;
    public int zoomY;
    public Point zoomPoint;
    public Point position;
    public boolean zoomed;
    private double zoomFactor = 0.95;
 
     public DrawingPanel()
     {
     }
 
     public DrawingPanel(MainWindow mainWindow) 
     {
         this.mainWindow = mainWindow;
         setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
         int width = (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
         setPreferredSize(new Dimension(width,1));
         setVisible(true);
         int height = (int)(width*0.5);
         initialDimension = new Dimension(width,height);
         
         this.addMouseWheelListener(new CustomMouseWheelListener());
     }
 
     @Override
     protected void paintComponent(Graphics g)
     {
        if (mainWindow != null)
        {
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g2d); 
            AffineTransform zoomed = g2d.getTransform();
            zoomed.translate(zoomX, zoomY);
            zoomed.scale(zoom, zoom);
            zoomed.translate(-zoomX, -zoomY);
            g2d.setTransform(zoomed);
            
            SalleDrawer mainDrawer = new SalleDrawer(mainWindow.controller,initialDimension);
            super.paintComponent(g);
            mainDrawer.draw(g);
            
            if(grilleActive)
            {
                g2d.scale(zoom, zoom);
                g2d.setPaint(Color.LIGHT_GRAY);
                Dimension adjustingDimension = new Dimension(this.getWidth(), this.getHeight());
                if(zoom >= 1)
                {
                    for(int row = 1; row <= adjustingDimension.getHeight() / this.gapGrille; row++)
                    {
                        g2d.drawLine(0, (int)(row * this.gapGrille), (int)adjustingDimension.getWidth(), (int)(row * this.gapGrille));
                    }
                    
                    for(int col = 1; col <= (int)adjustingDimension.getWidth() / this.gapGrille; col++)
                    {
                        g2d.drawLine((int)(col * this.gapGrille), 0, (int)(col * this.gapGrille), (int) adjustingDimension.getHeight());
                    }
                }
                else if(zoom < 1)
                {
                    for(int row = 1; row <= adjustingDimension.getHeight() / this.gapGrille/ (zoom); row++)
                    {
                        g2d.drawLine(0, (int)(row * this.gapGrille), (int)(adjustingDimension.getWidth() / zoom), (int)(row * this.gapGrille));
                    }
                    
                    for(int col = 1; col <= (int)adjustingDimension.getWidth() / this.gapGrille / (zoom); col++)
                    {
                        g2d.drawLine((int)(col * this.gapGrille), 0, (int)(col * this.gapGrille), (int) (adjustingDimension.getHeight() / zoom));
                    }
                }
                g2d.scale(1/zoom, 1/zoom);
            }
        }
     }
 
     public MainWindow getMainWindow()
     {
         return mainWindow;
     }
 
     public void setMainWindow(MainWindow mainWindow)
     {
         this.mainWindow = mainWindow;
     }
 
     public Dimension getInitialDimension()
     {
         return initialDimension;
     }
 
     public void setInitialDimension()
     {
     }
     
     public void setDrawingPanelDimensions()
     {
         Dimension dimension = new Dimension((int) initialDimension.getWidth(), (int) initialDimension.getHeight());
         this.setPreferredSize(new Dimension((int) (initialDimension.getWidth() * zoom), (int) (initialDimension.getHeight() * zoom)));
         revalidate();
     }
     
     public double getGapGrille()
     {
         return this.gapGrille;
     }
     
     public void setGapGrille(double gap)
     {
         this.gapGrille = gap;
     }
     
     public void setGridLines()
     {
         initialDimension = mainWindow.getMainScrollPaneDimension();
         grilleActive = !grilleActive;
         repaint();
     }
     
     public boolean getGrid()
     {
         return this.grilleActive;
     }
     
    public double getZoom()
    {
        return this.zoom;
    }
     
    public void setZoom(double zoom)
    {
        this.zoom = zoom;
    }

    public void setMouseX(double x) {
        this.mouseX = x;
    }

    public void setMouseY(double y) {
        this.mouseY = y;
    }
    
    public double getMouseX()
    {
        return this.mouseX;
    }
    
    public double getMouseY()
    {
        return this.mouseY;
    }
    
    public int getZoomX(){
        return zoomX;
    }
    
    public int getZoomY(){
        return zoomY;
    }
    
    public void setZoomX(int pZoomX){
    this.zoomX = pZoomX;
    }
    
    public void setZoomY(int pZoomY){
    this.zoomY = pZoomY;
    }
     
     // Implémentation du mouse wheel listener pour le zoom
    class CustomMouseWheelListener implements MouseWheelListener
    {
        @Override
        public void mouseWheelMoved(MouseWheelEvent event)
        {
            zoomed = true;
            zoomX = event.getX();
            zoomY = event.getY();
            zoomPoint = new Point(zoomX, zoomY);
            
            if (event.getPreciseWheelRotation() > 0)
            {
                zoom = zoom * zoomFactor;
                if (zoom ==1){
                zoomX=0;
                zoomY=0;
                }
            }
            else
            {
                zoom = zoom / zoomFactor;
                if(zoom ==1){
                zoomX=0;
                zoomY=0;
                }
            }
            repaint();      
        }
    }
 }
