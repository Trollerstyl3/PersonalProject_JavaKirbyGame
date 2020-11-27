/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: THis class is responsible of drawing the endPage of the game, which include YOU WIn or YOU LOSE
 */

// Please don't remove the packages because I have many folders
package src.gamestates;

// import all packages needed
import java.awt.Color;
import java.awt.Graphics2D;
import src.gamestates.enums.GameStates;

// Create the EndPage class that will extend the ScreenState
public class EndPage extends ScreenState{

  // Set up the constructor to call the parent constructor of ScreenState
    public EndPage(){
        super();
    }

    // Override the draw method of Screen State
    @Override
    public void draw(Graphics2D g2d, GameStates gameStates){

      // If the game state is GAMEWIN then display the according message
        if(gameStates == GameStates.GAMEWIN){
            g2d.setPaint(Color.BLACK);
            g2d.setFont(sfAtarian.deriveFont(80f));
            g2d.drawString("YOU WON", 173,200); 

            g2d.setFont(sfAtarian.deriveFont(77f));
            g2d.setPaint(Color.GREEN);
            g2d.drawString("YOU WON", 180,200); 
            
            g2d.setPaint(Color.BLACK);
            g2d.setFont(sfAtarian.deriveFont(25f));
            g2d.drawString("press ENTER to return to main menu",135,310);
            g2d.drawString("press R to play again",230,350);
            g2d.drawString("press Q to quit",260,390);
            
        // IF the game state is GAMELOSE then display the according message
        }else if(gameStates == GameStates.GAMELOSE){
            g2d.setPaint(Color.BLACK);
            g2d.setFont(sfAtarian.deriveFont(80f));
            g2d.drawString("YOU LOSE", 172,200); 

            g2d.setFont(sfAtarian.deriveFont(77f));
            g2d.setPaint(Color.RED);
            g2d.drawString("YOU LOSE", 180,200); 
            
            g2d.setPaint(Color.BLACK);
            g2d.setFont(sfAtarian.deriveFont(25f));
            g2d.drawString("press ENTER to return to main menu",135,310);
            g2d.drawString("press R to play again",230,350);
            g2d.drawString("press Q to quit",260,390);
        }
        
    }
}
