/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class draws the game paused method on the screen if the game is paused
 */

// Please don't remove the packages because I have many folders
package src.gamestates;

// import needed package
import java.awt.Graphics2D;
import src.gamestates.enums.GameStates;
import java.awt.Color;

// Create the pause class that will extend the screenstate object
public class Pause extends ScreenState{
    
  // Constructor for the Pause object
    public Pause(){
        super(); // call the constructor of ScreenState
    } 
    
    // Draw the game paused message on screne and also other messages too if the game is paused
    @Override
    public void draw(Graphics2D g2d, GameStates screenState){
        g2d.setPaint(Color.BLACK);
        g2d.setFont(sfAtarian.deriveFont(70f));
        g2d.drawString("GAME PAUSED", 85, 250);
        
        g2d.setFont(sfAtarian.deriveFont(25f));
        g2d.drawString("press ENTER to return to main menu",135,310);
        g2d.drawString("press ESC to return to the game",150,350);
        g2d.drawString("press Q to quit",260,390);
    }
}
