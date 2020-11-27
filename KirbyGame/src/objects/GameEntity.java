/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This is the class that moving entities will extend from and it is responsible for the x, y, 
 * movement of the object, the hitbox, the state of the character, and calling from the characterdata to get the spirte
 */

// Please don't remove the packages because I have many folders
package src.objects;

// import all packages needed
import java.util.*;
import java.awt.geom.*;
import java.awt.*;
import src.loader.*;
import src.*;
import src.objects.enums.*;

// Create new class called GameEntity that will be extended from
public class GameEntity {
    
  // Declare all variables needed
    protected int x; // Use protected so that the child class can access it
    protected int y;
    private int height;
    private int width;

    protected double dx;
    protected double dy;
 
    protected ArrayList<CharacterState> states; // This arrayList will keep track of the states that the character have
    protected RunScreen screen;
    protected CharacterData sprite;
    
    protected Rectangle2D.Double hitBox; // the hitbox
    
    // The constructor method of this class will get the type of entity, starting x and y, and the screen
    public GameEntity(int x, int y, RunScreen screen, EntityType entityType){
        
      // create the state arraylist
        states = new ArrayList<CharacterState>();
        dx = 0; // set movement of x to 0
        dy = 0; // set movement of y to 0

        // the x and y passed in is x and y
        this.x = x;
        this.y = y;
        this.screen = screen;

        // set width and height to the one random value just to start off
        width = 100;
        height = 100;

        // create the sprite sheet object where you take the sprite from 
        sprite = new CharacterData(screen, entityType);

        hitBox = new Rectangle2D.Double(x,y,width,height); // hitBox is equal to the x and y with width and height

    }

    // get the hit box
    public Shape getHitBox(){
        return hitBox;
    }
    
    // get the X
    public int getX(){
        return x;
    }

    // get the Y
    public int getY(){
        return y;
    }
    
    // get the Width
    public int getWidth(){
        return width;
    }

    // get the Height
    public int getHeight(){
        return height;
    }

    // get the states
    public ArrayList<CharacterState> getStates(){
        return states;
    }

    public void reset(){
        states.clear();
        states.add(CharacterState.GROUNDED);
        states.add(CharacterState.RIGHT);
        sprite.reset();
    }
}
