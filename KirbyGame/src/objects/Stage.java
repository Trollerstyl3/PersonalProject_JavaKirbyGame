/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This Stage class gives the stage its x and y, width and height and create a hitbox for it.
 * This class turns Stage into an objeect that the other objects can interact with.
 */

// Please don't remove the packages because I have many folders
package src.objects;

// Import all packages needed
import java.awt.Graphics2D;
import java.awt.Shape;
import src.objects.interfaces.ObjectInterface;
import src.util.CollisionChecker;
import src.RunScreen;
import src.loader.StageData;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

// Create the class Stage that implements Object as it is also an object
public class Stage implements ObjectInterface {
  
  // Declare all variables needed, make them private to protect the variables
    private RunScreen screen;
    private CollisionChecker collision;
    private StageData sprite;
    private int x;
    private int y;
    private int width;
    private int height;
    private double scale;
    private Rectangle2D.Double hitBox;

    // Constructer for the Stage class that will pass in where it is drawn and the screen
    public Stage(int x, int y,RunScreen screen){ // pass in the screen just in case needed
        
        // set screen to screen that is passed in
        this.screen = screen;
        
        // create the new stage data object that will load the images
        sprite = new StageData(screen);
        
        // set x and y to the x and y that was sent in
        this.x = x;
        this.y = y;
        
        // Scale to draw the image
        scale = 3.35;

        // Set the width and height to the width and height of the buffered image
        width = sprite.draw().getWidth();
        height = sprite.draw().getHeight();

     // Set the hitbox for the stage, set the y down 80 to allow the character to walk on the best place on the stage
        hitBox = new Rectangle2D.Double(x,y+80,(int) (width*scale),(int)(height*scale-80));
    }

    // overide the draw method of the interface
    @Override
    public void draw(Graphics2D g2d) { // create the draw method that will take in the graphics2d and draw the image on screen
        
      // Load up the current image
        BufferedImage current = sprite.draw();
       
        // Draw the image on screen with the correct x and y with scaling up the image
        g2d.drawImage(current, x, y, (int) (current.getWidth()*scale),(int) (current.getHeight()*scale),null);
    }

    // override the getHitBox of interface
    @Override
    public Shape getHitBox() {
      
      // Return the hitbox for other object to check collision
        return hitBox;
    }

    
}
    

