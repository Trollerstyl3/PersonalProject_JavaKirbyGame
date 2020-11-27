/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This ScreenState class is the parent object of others classes that draw messages on screen
 * It imports a new font that I like, also creates a draw method that takes in the current screenstate and g2d
 */

// Please don't remove the packages because I have many folders
package src.gamestates;

// import all needed package
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import src.gamestates.enums.GameStates;

// Create class ScreenState
public class ScreenState {
    
  // Create a new font called sfAtarian
    Font sfAtarian;
    
    // Constructor for the class ScreenState
    public ScreenState(){
      
      // Put in try and catch block because it could through an error when loading up the font
        try {
            // create the new font and point it to the file
            sfAtarian = Font.createFont(Font.TRUETYPE_FONT, new File("src/res/sf_atarian.ttf"));
            
            // Register the font to the graphics environment for the program to be able to load the font
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/res/sf_atarian.ttf")));
        } catch (Exception e ) {
          //if throws an exception then print the stack trace and print not found
            e.printStackTrace();    
            System.out.println("not found");
        }
    }

    // Blank draw method so the childs can override it
    public void draw(Graphics2D g2d, GameStates screenState){

    }

}
