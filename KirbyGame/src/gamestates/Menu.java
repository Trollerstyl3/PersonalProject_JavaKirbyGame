/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class will draw the menu and the instructions screen when the game state is in GAMEMENU or
 * GAMEINSTRUCTION
 */

// Please don't remove the packages because I have many folders
package src.gamestates;

// import all packages needed
import java.awt.Graphics2D;
import src.gamestates.enums.*;
import java.awt.Color;

// Create the menu class that will extend the screenState
public class Menu extends ScreenState{

  // Create the variable colorCount and color to make the color of the text change
    private int colorCount;
    private boolean color;

    // Constructor of Menu 
    public Menu(){
        super(); // Call the constructor of ScreenState that will set up the font
        
        // Set up the color to the first one
        colorCount = 0;
        color = true;
        
    }


    // Override the draw method of ScreenState
    @Override
    public void draw(Graphics2D g2d, GameStates gameState){
    
        
       // If the game state is GAMEMENU then draw the title of the game and instructions to go to the next screen
        if(gameState == GameStates.GAMEMENU){
            g2d.setPaint(Color.BLACK);
            g2d.setFont(sfAtarian.deriveFont(68f));
            g2d.drawString("KIRBY'S FIGHT", 109,200); 

            g2d.setFont(sfAtarian.deriveFont(65f));
            g2d.setPaint(Color.PINK);
            g2d.drawString("KIRBY'S FIGHT", 120,200); 

            g2d.setFont(sfAtarian.deriveFont(25f));
            
            // Responsible of the change of colour
            if(colorCount%15 == 0) color = !color;
            if(color){
                g2d.setPaint(Color.BLACK);
            }else{
                g2d.setPaint(Color.RED);
            }
            
            // draw in instructions
            g2d.drawString("press I for instructions",202,410);
            g2d.drawString("press ENTER to start the game",155,450);
            g2d.drawString("press Q to quit",260,490);
            
            // add to the count
            colorCount++;
            
        // If the game state is GAMEINSTRUCTION then draw the first page of instructions
        }else if(gameState == GameStates.GAMEINSTRUCTION){
            g2d.setPaint(Color.BLACK);
            g2d.setFont(sfAtarian.deriveFont(68f));
            g2d.drawString("INSTRUCTIONS", 109,100); 

            g2d.setFont(sfAtarian.deriveFont(65f));
            g2d.setPaint(Color.PINK);
            g2d.drawString("INSTRUCTIONS", 120,100); 

            g2d.setFont(sfAtarian.deriveFont(30f));
            g2d.setPaint(Color.BLACK);
            g2d.drawString("welcome to KIRBY'S FIGHT, you will",80,200); 
            g2d.drawString("become KIRBY and fight off KNIGHT",82,250);
            g2d.drawString("KIRBY.",300,300);

            g2d.setFont(sfAtarian.deriveFont(45f));
            g2d.drawString("OBJECTIVE",232,400);

            g2d.setFont(sfAtarian.deriveFont(30f));
            g2d.drawString("kill KNIGHT KIRBY #",200,450); 

            g2d.setFont(sfAtarian.deriveFont(25f));
            g2d.drawString("press I for next page",220,550);

        // If the game state is GAMEINSTRUCTION_2 then draw the second page of instruction 
        }else if(gameState == GameStates.GAMEINSTRUCTION_2){
            g2d.setPaint(Color.BLACK);
            g2d.setFont(sfAtarian.deriveFont(45f));
            g2d.drawString("HOW TO PLAY",200,100);

            g2d.setFont(sfAtarian.deriveFont(30f));
            g2d.drawString("use A,D to move left and right",125,150); 
            g2d.drawString("use W,S to move jump and slam down",60,175); 
            g2d.drawString("use T to attack",240,200); 
            g2d.drawString("use Y to dash",247,225); 
            g2d.drawString("use U to taunt",241,250); 
            g2d.drawString("press ESC to pause",200,275); 

            g2d.setFont(sfAtarian.deriveFont(45f));
            g2d.drawString("RULES",275,350);

            g2d.setFont(sfAtarian.deriveFont(30f));
            g2d.drawString("you take damage and fall to your death",60,400); 
            g2d.drawString("if you go off the screen",180,425); 
            g2d.drawString("you can deal damage using slam or attack",40,450);
            g2d.drawString("you can dash once while airborne",105,475); 
            g2d.drawString("you can slam to block attacks on the ground",15,500); 
            g2d.drawString("first one to kill the other wins",120,525); 
            

            g2d.setFont(sfAtarian.deriveFont(25f));
            g2d.drawString("press I to go back to main menu",150,600);
        }



    }

}
