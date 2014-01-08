package graphics2;
/*package graphics2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.BackGround;
import main.MainView;
import main.Rocket;

public class CopyOfCanvas
{
    private static CopyOfCanvas canvas = new CopyOfCanvas();

    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private BufferedImage background;
    private JFrame frame;
    private CanvasComponent component;
    private MyMouseListener mouseListener;

    private static final int MIN_SIZE = 600;
    private static final int MARGIN = 10;
    private static final int LOCATION_OFFSET = 120;


    class CanvasComponent extends JComponent
    {
        
        public void paintComponent(Graphics g)
        {
           // g.setColor(java.awt.Color.BLACK);
        	g.setColor(java.awt.Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(java.awt.Color.GRAY);
            if (background != null)
            {
                g.drawImage(background, 0, 0, null);
            }               
            for (Shape s : new ArrayList<Shape>(shapes))
            {
                Graphics2D g2 = (Graphics2D) g.create();
                s.paintShape(g2);
                g2.dispose();
            }
        }

        public Dimension getPreferredSize()
        {

        	int maxx = 1280;
            int maxy = 820;
            //maxx = GameView.backGround.getWidth();
            //maxy = GameView.backGround.getHeight();
        	
            //HE COMENTADO ESTO PARA HACER EL BACKGROUND UNA IMAGEN FIJA
            
            
          //int maxx = MIN_SIZE;
          //int maxy = MIN_SIZE;
          //if (background != null)
          //  {
          //      maxx = Math.max(maxx, background.getWidth());
          //      maxy = Math.max(maxx, background.getHeight());
          //  }
          //  for (Shape s : shapes)
          //  {
          //      maxx = (int) Math.max(maxx, s.getX() + s.getWidth());
          //      maxy = (int) Math.max(maxy, s.getY() + s.getHeight());
          //  }
            
            return new Dimension(maxx + MARGIN, maxy + MARGIN);
        }
    }
    
    class MyMouseListener implements MouseListener,MouseMotionListener{
    	
    	private Point mouseLocation = new java.awt.Point();
    	private Object mouseState = new Object();;

    	
    	// MouseListener

    	  private void getMouseLocation(MouseEvent e) {
    	    mouseLocation.x = e.getX();
    	    mouseLocation.y = e.getY();
    	  }
    	  
    	  public void mouseClicked(MouseEvent e) {
    		 //System.out.println("Click");
    		 //MainView.nuevoMisil = true;
    	  }

    	  public void mousePressed(MouseEvent e) {
    		  MainView.nuevoMisil = true;
    		    synchronized(mouseLocation) {
    		      getMouseLocation(e);
    		    }
    		  }

    		  public void mouseReleased(MouseEvent e) {
    		    synchronized(mouseLocation) {
    		      getMouseLocation(e);
    		      mouseLocation.notify();
    		    }
    		    synchronized(mouseState) {
    		      mouseState.notify();
    		    }
    		  }

    		  public void mouseEntered(MouseEvent e) {
    		    synchronized(mouseLocation) {
    		      getMouseLocation(e);
    		    }
    		  }

    		  public void mouseExited(MouseEvent e) {
    		  }
    		  
    		  //
    		  // Returns the current mouse location (relative to the corresponding 
    		  // drawing panel) as a <code>java.awt.Point</code>.
    		  //
    		  // @return      the <code>java.awt.Point</code> object
    		  //
    		  public java.awt.Point getMouse() {
    			    synchronized(mouseLocation) {
    			      return mouseLocation;
    			    }
    			  }
    		 
    		  //
    		  //  Waits until the mouse button is pressed or released, then returns the 
    		  //  location of the mouse.
    		  //  
    		  //  @return      <code>java.awt.Point</code> object;
    		  // 
    		//  public java.awt.Point waitClick() {
    		//    synchronized(mouseState) {
    		//      try {
    		//        mouseState.wait();
    		//      }
    		//      catch( InterruptedException it ) {}
    		//    }
    		//    return mouseLocation;
    		//  }

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				synchronized(mouseLocation) {
	    		      getMouseLocation(e);
	    		    }
			}
    		  
    }

    private CopyOfCanvas()
    {
        component = new CanvasComponent();
        mouseListener = new MyMouseListener();

        if (System.getProperty("com.horstmann.codecheck") == null)
        {
            frame = new JFrame("Invasores de Toledo v1.0");
            if (!System.getProperty("java.class.path").contains("bluej"))
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(component);
            frame.pack();
            frame.setLocation(LOCATION_OFFSET, LOCATION_OFFSET);
            frame.setVisible(true);
            component.addMouseListener(mouseListener);
            component.addMouseMotionListener(mouseListener);
        }
        else
        {
            final String SAVEFILE ="canvas.png";
            final Thread currentThread = Thread.currentThread();
            Thread watcherThread = new Thread() 
                {
                    public void run()
                    {
                        try
                        {
                        	
                            final int DELAY = 10;
                            while (currentThread.getState() != Thread.State.TERMINATED)
                            {
                                Thread.sleep(DELAY);
                            }
                            saveToDisk(SAVEFILE);
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                };
            watcherThread.start();
        }
    }

    public static CopyOfCanvas getInstance()
    {
        return canvas;
    }

    public void show(Shape s)
    {
        if (!shapes.contains(s))
        {
            shapes.add(s);
        }
        repaint();
    }
    
    public void hide(Shape s)
    {
        shapes.remove(s);
        repaint();
    }

    public void repaint()
    {
    	//COMO NO VOY A AJUSTAR EL CANVAS YA QUE ESTA FIJO. ESTO NO LO VOY A NECESITAR
    	//
        //if (frame == null) return;
        //Dimension dim = component.getPreferredSize();
        //if (dim.getWidth() > component.getWidth()
        //        || dim.getHeight() > component.getHeight())
        //{
        //    frame.pack();
        //}
        //else
        //{
        //    frame.repaint();
        //}
        //
    	frame.repaint();
        
    }

    //
    // Pauses so that the user can see the picture before it is transformed.
    //
    public static void pause()
    {
        JFrame frame = getInstance().frame;
        if (frame == null) return;
        JOptionPane.showMessageDialog(frame, "Click Ok to continue");
    }

    //
    // Takes a snapshot of the screen, fades it, and sets it as the background.
    //
    public static void snapshot()
    {
        Dimension dim = getInstance().component.getPreferredSize();
        java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, dim.width, dim.height);
        BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, rect.width, rect.height);
        g.setColor(java.awt.Color.BLACK);
        getInstance().component.paintComponent(g);
        float factor = 0.8f;
        float base = 255f * (1f - factor);
        RescaleOp op = new RescaleOp(factor, base, null);
        BufferedImage filteredImage
           = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        op.filter(image, filteredImage);
        getInstance().background = filteredImage;
        getInstance().component.repaint();
    }

    public void saveToDisk(String fileName)
    {
        Dimension dim = component.getPreferredSize();
    	java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, dim.width, dim.height);
    	BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fill(rect);
        g.setColor(java.awt.Color.BLACK);
        component.paintComponent(g);
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        try
        {
            ImageIO.write(image, extension, new File(fileName));
        } 
        catch(IOException e)
        {
            System.err.println("Was unable to save the image to " + fileName);
        }
    	g.dispose();    	
    }
    
    public Point getMousePosition() {
    	
    	return this.mouseListener.getMouse();
    }
    //
    //public Point waitMouseClick() {
    	//return this.mouseListener.waitClick();
    //}
    

}
*/