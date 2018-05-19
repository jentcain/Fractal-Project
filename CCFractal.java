/* The CCFractal class contains the main method in which a regular polygon is initialized, modified, and reflected to create a fractal image, which is then displayed.
Author: Jennifer Cain  */

//Imports
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;

public class CCFractal extends JPanel{
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 768;
    public static final double FPS = 60;

//* The following two fields are user inputs
    private static int _numSides; //Number of sides of the regular polygon that forms the base of the image
    private static int _numUpdates; //Number of times the polygon will be fractalized before the image is displayed

    private static Polygon initial; //Base polygon
    private static Polygon updated; //Fractalized polygon
    private static ArrayList<Polygon> kaleidoscope;
        

    private static int tick = -1;

    class Runner implements Runnable{
        public void run(){
            while (true){
		try{
		    //Implement movement
		    for (Edge e : kaleidoscope.get(0).edges){
			e.v1.x-=1;
			e.v2.x-=1;
	      		e.v1.y+=1;
			e.v2.y+=1;
		    }
		    kaleidoscope=kaleidoscope.get(0).kaleidoscope();
		}
		catch(Exception e){
		    System.out.print(".");
		}
                repaint();
                try{
                    Thread.sleep((long)(1000.0/FPS));
                }
                catch(InterruptedException e){}
            }
        }
     }
    
    
    private CCFractal(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }
    
    public static void main(String[] args){

	// Check that sufficient arguments have been provided, and then extract and parse them.
	_numSides=0;
	_numUpdates=-1;
	if (args.length != 2) {
	    showUsageAndExit();
	}
	try{
	    _numSides=Integer.valueOf(args[0]);
	    if (_numSides<3 || _numSides>9){
		 System.out.println("The first argument must be an integer between 3 and 9 (inclusive).");
		 showUsageAndExit();
	    }
	}
	catch (NumberFormatException e){
	    System.out.println("The first argument must be an integer between 3 and 10 (inclusive).");
	    showUsageAndExit();
	}
	try{
	    _numUpdates=Integer.valueOf(args[1]);
	    if (_numUpdates<0){
		 System.out.println("The second argument must be a nonnegative integer.");
		 showUsageAndExit();
	    }
	}
	catch (NumberFormatException f){
	    System.out.println("The second argument must be a nonnegative integer.");
	    showUsageAndExit();
	}

	// Make sure the cardinality of the set of edges in the kaleidoscope isn't too large. Beyond the 6th update the changes are indiscernible.
	if (_numUpdates>6){
	    _numUpdates=6;
	}
	
	//*Initialize the base polygon
	initial=new Polygon();
	initial.initialize(_numSides);

	
    	CCFractal fractal = new CCFractal();
	//*Update the polygon the appropriate number of times and reflect it to create a 'kaleidoscope' of 4 polygons
	if (_numUpdates==0){
	    kaleidoscope=initial.kaleidoscope();
	}
        else{
	    updated = initial;
	    for (int j =0; j < _numUpdates; j++)
		updated = updated.update();
	    kaleidoscope=updated.kaleidoscope();
	}


	JFrame frame = new JFrame("Fractal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(fractal);
        frame.pack();
	frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	
    }

//* Continuously fade to a new randomly generated color
    Color c;
    Color nextColor;
    public void paintComponent(Graphics g) {
	Random rand= new Random();
	super.paintComponent(g);
	g.setColor(Color.BLACK);
	g.fillRect(0,0,WIDTH,HEIGHT);
	g.translate((int) WIDTH/2, (int) HEIGHT/2-50);


	double switchtime =  1* (int)FPS;
	if (tick == -1){
	    c = new Color(0, 0, 0);
	    nextColor = new Color(255, 255, 255);
	    tick = 0;
	}
	else if (tick == switchtime){
	    tick = 0;
	    c = nextColor;
	    nextColor = new Color(rand.nextInt(255)+1,rand.nextInt(255)+1,rand.nextInt(255)+1);
	    g.setColor(c);
	}
	else{
	    tick +=1;
	    //step toward new color
	    double deltaRed =   (nextColor.getRed() -   c.getRed()) /   switchtime * tick;
	    double deltaGreen = (nextColor.getGreen() - c.getGreen()) / switchtime * tick;
	    double deltaBlue =  (nextColor.getBlue() -  c.getBlue()) /  switchtime * tick;
	    //System.out.println(deltaRed + " " + deltaGreen + " " + deltaBlue);
	    g.setColor(new Color((int)(c.getRed() + deltaRed), (int)(c.getGreen() + deltaGreen), (int)(c.getBlue() + deltaBlue)));
	}




	for(Polygon p: kaleidoscope){
	    //g.setColor(new Color(rand.nextInt(255)+1,rand.nextInt(255)+1,rand.nextInt(255)+1));
	    for(Edge e:p.edges){
		g.drawLine(e.v1.x, e.v1.y, e.v2.x, e.v2.y);
	    }
	}	    
    } 

    //*Print the usage information for correct invocation, and then exit with an error code
    private static void showUsageAndExit () {
	System.err.printf("USAGE: java DrawFractal < integer n | 2<n<10 >\n" +
			  "                 < non-negative integer >  \n"       );
	System.exit(1);

    }
    
 
    
}
