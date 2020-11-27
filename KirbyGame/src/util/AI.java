/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class is in charge of moving the enemy towards the player and hit when it gets close enough
 * it can also slam if the character is below it
 */

// Please don't remove the packages because I have many folders
package src.util;

// import all packages needed
import src.objects.*;
import src.objects.enums.*;
import src.*;

// create the AI class
public class AI {

  // Declare all variables needed
    private RunScreen screen;
    private double attackTimer;

    // The constructor gets the screen and pass its into the screen variable
    public AI(RunScreen screen){
        this.screen = screen;
        attackTimer = 0; // set the timer to 0 
    }
     
    // Movement method the is incharge of moving the enemy near the player and hitting the player
    public void movement(Enemy enemy, Player player){
        double attackLength = 2; // each 2 second it will hit the player

        // Add the screen time that have passed to the timer
        attackTimer += screen.getDeltaTime();
        
        // If the time is over the time length then allow to swing the sword
        if(attackTimer < attackLength){
            enemy.attack = false;
        }else{
          // if close enough it is going to swing the sword
            if((Math.abs(enemy.getX()-(player.getX()+player.getWidth())) < 100 || Math.abs(player.getX()-enemy.getX()) < 100) && enemy.getStates().contains(CharacterState.GROUNDED) && enemy.getY() == player.getY() && !enemy.getStates().contains(CharacterState.HURT)){
        
                enemy.attack = true;
                attackTimer = 0; // reset the timer
    
            }
            
            // if player is under the enemy then slam
            if(Math.abs(enemy.getX()-player.getX()) < 30 && enemy.getY() < player.getY() && !enemy.getStates().contains(CharacterState.GROUNDED)){
        
                enemy.moveDown = true;
                enemy.moveUp = false;

                attackTimer = 0; // retset the timer
            }
        }


        // In charge of moving the enemy right and left
        if(enemy.getX() < player.getX() && player.getX() < 700 && player.getX()> 0-player.getWidth()){
            enemy.moveRight = true;
            enemy.moveLeft = false;
        }else if(enemy.getX() > player.getX() && player.getX() < 700 && player.getX()> 0 -  player.getWidth()){
            enemy.moveLeft = true;
            enemy.moveRight = false;
        }

        // In charge of moving the enemy up and down
        if(enemy.getY() > player.getY()+200 && enemy.getStates().contains(CharacterState.GROUNDED)){
            enemy.moveUp = true;
            enemy.moveDown = false;
        }else if(enemy.getStates().contains(CharacterState.GROUNDED)){
            enemy.moveUp = false;
            enemy.moveDown = false;
        }
    }
}