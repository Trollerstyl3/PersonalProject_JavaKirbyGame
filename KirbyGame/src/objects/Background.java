/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This method loads up the background and give it an x and y coordinate so it can know where to show
 */

// Please don't remove the packages because I have many folders
package src.objects;

// import all packages needed
import java.awt.Graphics2D;
import java.awt.Shape;
import src.objects.interfaces.ObjectInterface;
import src.util.CollisionChecker;
import src.RunScreen;
import src.loader.BackgroundData;
import java.awt.image.BufferedImage;

// Create background class that will implement the object interface
public class Background implements ObjectInterface {

  // Declare all variables needed
    private RunScreen screen;
    private CollisionChecker collision;
    private BackgroundData sprite;
    private int x;
    private int y;
    private int width;
    private int height;
    private double scale;

    // Constructor for the background that will take in the x and y and the screen
    public Background(int x, int y,RunScreen screen){
      
      // set this screen to the screen that was passed in
        this.screen = screen;
        sprite = new BackgroundData(screen); // get the image from the backgrounddatat loader
        this.x = x; // set x that was pass in to x
        this.y = y;// set y that was pass in to y
        scale = 1;

        // the width and hieght is equal to the width and height of the background image
        width = sprite.draw().getWidth();
        height = sprite.draw().getHeight();
    }

    // Override the draw method to draw the background image on g2d
    @Override
    public void draw(Graphics2D g2d) {
        
      // get the sprite and draw it on the screen
        BufferedImage current = sprite.draw();
        g2d.drawImage(current, x, y, (int) (current.getWidth()*scale),(int) (current.getHeight()*scale),null);
    }

    // Override the getHitBox bur since it is not an object that can get hit then just return null
    @Override
    public Shape getHitBox() {
        return null;
    }

    
}
    