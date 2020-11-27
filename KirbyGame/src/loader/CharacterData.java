/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class will load up all the sprites for the enemy and the player and allow the 
 * other class to access it
 */

// Please don't remove the packages because I have many folders
package src.loader;

// import all packages needed
import java.awt.image.*;
import javax.imageio.*;
import src.*;
import src.objects.enums.*;
import java.io.*;

// Create the class CharacterData
public class CharacterData {
  
    // All the variable to keep tract of what movement is what array
    public static final int WALK_RIGHT = 0;
    public static final int WALK_LEFT = 1;
    public static final int PAUSED_RIGHT = 2;
    public static final int PAUSED_LEFT = 3;
    public static final int SLAM_RIGHT = 4;
    public static final int SLAM_LEFT = 5;
    public static final int CHARGE_RIGHT = 6;
    public static final int CHARGE_LEFT = 7;
    public static final int ATTACK_RIGHT = 8;
    public static final int ATTACK_LEFT = 9;
    public static final int HURT_RIGHT = 10;
    public static final int HURT_LEFT = 11;
    public static final int PANIC_RIGHT = 12;
    public static final int PANIC_LEFT = 13;
    public static final int VICTORY_RIGHT = 14;
    public static final int VICTORY_LEFT = 15;
    public static final int LOSE_RIGHT = 16;
    public static final int LOSE_LEFT = 17;
    public static final int STAND_RIGHT = 18;
    public static final int STAND_LEFT = 19;
    public static final int JUMP_RIGHT = 20;
    public static final int JUMP_LEFT = 21;
    public static final int FALL_RIGHT = 22;
    public static final int FALL_LEFT = 23;

    // 2D array to load the sprites onto for the row it is the movement and column is the sprite
    BufferedImage[][] normalKirby;
    BufferedImage[][] knightKirby;
    BufferedImage spriteSheetCharacter; // this to load up the sheet first
    
    // keep track of the animation 
    boolean found;
    int imgCount,currentState;
    EntityType entityType;

    //keep track of the animation
    BufferedImage[] currentImageArray, oldImageArray;
    
    // animation timer
    double animTimer;

    // the RunScreen
    RunScreen screen;

    // Constructor for this class that will pass in the RunScreen and the type of entity to know what to take from the sprite sheet
    public CharacterData(RunScreen screen,EntityType entityType){
        this.screen = screen; //Screen sent in is equal to screen

        this.entityType = entityType; // set entityType to entityType

        // Set the current state and count to 0
        imgCount = 0;
        currentState = 0;
        
        // load the image
        loadImages();
    }

    public void loadImages(){


      // Check the type of entity, if it is player then create 2d array for player if enemy then just create it for enemy
        if(entityType == EntityType.PLAYER){
            normalKirby = new BufferedImage [24][];
            initNormalKirby();
        }else if(entityType == EntityType.ENEMY){
            knightKirby = new BufferedImage [24][];
            initKnightKirby();
        }
        
        found = true; // default found to true
 
        spriteSheetCharacter = null; // sprite sheet to null

        // Find the file
        File myFileCharacter = new File("src/res/kirbys_character.png");

        try { // try catch to catch the exception thrown when file not foudn
            spriteSheetCharacter = ImageIO.read(myFileCharacter);    // try loading it in
        } 
        catch (IOException e) {
            found = false;
            System.out.println("not found");
        }

        if(found){ // if the file was found then load up the sprite for the according EntityType

            if(entityType == EntityType.PLAYER){
                setNormalKirby();
                currentImageArray = normalKirby[WALK_RIGHT];
            }else if(entityType == EntityType.ENEMY){
                setKnightKirby();
                currentImageArray = knightKirby[WALK_LEFT];
            }
            
            oldImageArray = currentImageArray;

        }
    }

    // declare the number of sprites for the according actions for normal Kirby
    private void initNormalKirby (){
        normalKirby[WALK_RIGHT] = new BufferedImage[2];
        normalKirby[WALK_LEFT] = new BufferedImage[2];
        normalKirby[PAUSED_RIGHT] = new BufferedImage[3];
        normalKirby[PAUSED_LEFT] = new BufferedImage[3];
        normalKirby[SLAM_RIGHT] = new BufferedImage[1];
        normalKirby[SLAM_LEFT] = new BufferedImage[1];
        normalKirby[JUMP_RIGHT] = new BufferedImage[1];
        normalKirby[JUMP_LEFT] = new BufferedImage[1];
        normalKirby[FALL_RIGHT] = new BufferedImage[1];
        normalKirby[FALL_LEFT] = new BufferedImage[1];
        normalKirby[CHARGE_RIGHT] = new BufferedImage[5];
        normalKirby[CHARGE_LEFT] = new BufferedImage[5];      
        normalKirby[ATTACK_RIGHT] = new BufferedImage[4];
        normalKirby[ATTACK_LEFT] = new BufferedImage[4];
        normalKirby[HURT_RIGHT] = new BufferedImage[1];
        normalKirby[HURT_LEFT] = new BufferedImage[1];
        normalKirby[PANIC_RIGHT] = new BufferedImage[2];
        normalKirby[PANIC_LEFT] = new BufferedImage[2];
        normalKirby[VICTORY_RIGHT] = new BufferedImage[6];
        normalKirby[VICTORY_LEFT] = new BufferedImage[6];
        normalKirby[LOSE_RIGHT] = new BufferedImage[1];
        normalKirby[LOSE_LEFT] = new BufferedImage[1];
        normalKirby[STAND_RIGHT] = new BufferedImage[2];
        normalKirby[STAND_LEFT] = new BufferedImage[2];
    }

    // Get the subImage from the spritesheet and load it into the 2D array with the according movement for normalKirby
    private void setNormalKirby(){
            normalKirby[STAND_RIGHT][0] = spriteSheetCharacter.getSubimage(4, 38, 22, 19);
            normalKirby[WALK_RIGHT][0] = spriteSheetCharacter.getSubimage(29, 37, 22, 20);
            normalKirby[WALK_RIGHT][1] = spriteSheetCharacter.getSubimage(54, 37, 22, 20);

            normalKirby[PAUSED_RIGHT][0] = spriteSheetCharacter.getSubimage(106, 42, 22, 15);
            normalKirby[PAUSED_RIGHT][1] = spriteSheetCharacter.getSubimage(131, 41, 22, 16);
            normalKirby[PAUSED_RIGHT][2] = spriteSheetCharacter.getSubimage(156, 40, 22, 17);

            //normalKirby[JUMP_RIGHT][0] = spriteSheetCharacter.getSubimage(211, 41, 26, 16);
            normalKirby[JUMP_RIGHT][0] = spriteSheetCharacter.getSubimage(240, 37, 20, 20);

            normalKirby[FALL_RIGHT][0] = spriteSheetCharacter.getSubimage(263, 38, 22, 19);

            normalKirby[SLAM_RIGHT][0] = spriteSheetCharacter.getSubimage(288, 41, 22, 16);

            normalKirby[CHARGE_RIGHT][0] = spriteSheetCharacter.getSubimage(433, 37, 22, 20);
            normalKirby[CHARGE_RIGHT][1] = spriteSheetCharacter.getSubimage(458, 36, 35, 21);
            normalKirby[CHARGE_RIGHT][2] = spriteSheetCharacter.getSubimage(496, 25, 35, 32);
            normalKirby[CHARGE_RIGHT][3] = spriteSheetCharacter.getSubimage(534, 14, 32, 43);
            normalKirby[CHARGE_RIGHT][4] = spriteSheetCharacter.getSubimage(569, 24, 22, 32);
        
            normalKirby[ATTACK_RIGHT][0] = spriteSheetCharacter.getSubimage(598, 36, 46, 21);
            normalKirby[ATTACK_RIGHT][1] = spriteSheetCharacter.getSubimage(647, 15, 32, 43);
            normalKirby[ATTACK_RIGHT][2] = spriteSheetCharacter.getSubimage(682, 17, 46, 40);
            normalKirby[ATTACK_RIGHT][3] = spriteSheetCharacter.getSubimage(731, 36, 47, 21);
        
            normalKirby[HURT_RIGHT][0] = spriteSheetCharacter.getSubimage(818, 34, 22, 23);

            normalKirby[PANIC_RIGHT][0] = spriteSheetCharacter.getSubimage(885, 36, 24, 21);
            normalKirby[PANIC_RIGHT][1] = spriteSheetCharacter.getSubimage(911, 37, 22, 20);
        
            normalKirby[VICTORY_RIGHT][0] = spriteSheetCharacter.getSubimage(956, 37, 22, 20);
            normalKirby[VICTORY_RIGHT][1] = spriteSheetCharacter.getSubimage(981, 36, 24, 21);
            normalKirby[VICTORY_RIGHT][2] = spriteSheetCharacter.getSubimage(1008, 36, 22, 21);
            normalKirby[VICTORY_RIGHT][3] = spriteSheetCharacter.getSubimage(1033, 38, 18, 19);
            normalKirby[VICTORY_RIGHT][4] = spriteSheetCharacter.getSubimage(1054, 37, 20, 20);
            normalKirby[VICTORY_RIGHT][5] = spriteSheetCharacter.getSubimage(1077, 36, 23, 21);

            normalKirby[LOSE_RIGHT][0] = spriteSheetCharacter.getSubimage(1244, 37, 21, 20);


            // For all left movemnt it is just right but flipped
            normalKirby[STAND_LEFT][0] = flip(normalKirby[STAND_RIGHT][0]);

            normalKirby[WALK_LEFT][0] = flip(normalKirby[WALK_RIGHT][0]);
            normalKirby[WALK_LEFT][1] = flip(normalKirby[WALK_RIGHT][1]);

            normalKirby[PAUSED_LEFT][0] = flip(normalKirby[PAUSED_RIGHT][0]);
            normalKirby[PAUSED_LEFT][1] = flip(normalKirby[PAUSED_RIGHT][1]);
            normalKirby[PAUSED_LEFT][2] = flip(normalKirby[PAUSED_RIGHT][2]);

            //normalKirby[JUMP_LEFT][0] = spriteSheetFlipped.getSubimage(1076, 41, 26, 16);
            normalKirby[JUMP_LEFT][0] = flip(normalKirby[JUMP_RIGHT][0]);

            normalKirby[FALL_LEFT][0] = flip(normalKirby[FALL_RIGHT][0]);

            normalKirby[SLAM_LEFT][0] = flip(normalKirby[SLAM_RIGHT][0]);

            normalKirby[CHARGE_LEFT][0] = flip(normalKirby[CHARGE_RIGHT][0]);
            normalKirby[CHARGE_LEFT][1] = flip(normalKirby[CHARGE_RIGHT][1]);
            normalKirby[CHARGE_LEFT][2] = flip(normalKirby[CHARGE_RIGHT][2]);
            normalKirby[CHARGE_LEFT][3] = flip(normalKirby[CHARGE_RIGHT][3]);
            normalKirby[CHARGE_LEFT][4] = flip(normalKirby[CHARGE_RIGHT][4]);

            normalKirby[ATTACK_LEFT][0] = flip(normalKirby[ATTACK_RIGHT][0]);
            normalKirby[ATTACK_LEFT][1] = flip(normalKirby[ATTACK_RIGHT][1]);
            normalKirby[ATTACK_LEFT][2] = flip(normalKirby[ATTACK_RIGHT][2]);
            normalKirby[ATTACK_LEFT][3] = flip(normalKirby[ATTACK_RIGHT][3]);

            normalKirby[HURT_LEFT][0] = flip(normalKirby[HURT_RIGHT][0]);

            normalKirby[PANIC_LEFT][0] = flip(normalKirby[PANIC_RIGHT][0]);
            normalKirby[PANIC_LEFT][1] = flip(normalKirby[PANIC_RIGHT][1]);

            normalKirby[VICTORY_LEFT][0] = flip(normalKirby[VICTORY_RIGHT][0]);
            normalKirby[VICTORY_LEFT][1] = flip(normalKirby[VICTORY_RIGHT][1]);
            normalKirby[VICTORY_LEFT][2] = flip(normalKirby[VICTORY_RIGHT][2]);
            normalKirby[VICTORY_LEFT][3] = flip(normalKirby[VICTORY_RIGHT][3]);
            normalKirby[VICTORY_LEFT][4] = flip(normalKirby[VICTORY_RIGHT][4]);
            normalKirby[VICTORY_LEFT][5] = flip(normalKirby[VICTORY_RIGHT][5]);
            
            normalKirby[LOSE_LEFT][0] = flip(normalKirby[LOSE_RIGHT][0]);
    }

    // declare the number of sprites for the according actions for enemy Kirby
    private void initKnightKirby(){
        knightKirby[WALK_RIGHT] = new BufferedImage[2];
        knightKirby[PAUSED_RIGHT] = new BufferedImage[3];
        knightKirby[SLAM_RIGHT] = new BufferedImage[1];
        knightKirby[JUMP_RIGHT] = new BufferedImage[1];
        knightKirby[FALL_RIGHT] = new BufferedImage[1];
        knightKirby[CHARGE_RIGHT] = new BufferedImage[2];
        knightKirby[ATTACK_RIGHT] = new BufferedImage[3];
        knightKirby[HURT_RIGHT] = new BufferedImage[1];
        knightKirby[PANIC_RIGHT] = new BufferedImage[2];
        knightKirby[VICTORY_RIGHT] = new BufferedImage[7];
        knightKirby[LOSE_RIGHT] = new BufferedImage[1];
        knightKirby[STAND_RIGHT] = new BufferedImage[1]; 

        knightKirby[WALK_LEFT] = new BufferedImage[2];
        knightKirby[PAUSED_LEFT] = new BufferedImage[3];
        knightKirby[SLAM_LEFT] = new BufferedImage[1];
        knightKirby[JUMP_LEFT] = new BufferedImage[1];
        knightKirby[FALL_LEFT] = new BufferedImage[1];
        knightKirby[CHARGE_LEFT] = new BufferedImage[2];
        knightKirby[ATTACK_LEFT] = new BufferedImage[3];
        knightKirby[HURT_LEFT] = new BufferedImage[1];
        knightKirby[PANIC_LEFT] = new BufferedImage[2];
        knightKirby[VICTORY_LEFT] = new BufferedImage[7];
        knightKirby[LOSE_LEFT] = new BufferedImage[1];
        knightKirby[STAND_LEFT] = new BufferedImage[1];
    }

    // Get the subImage from the spritesheet and load it into the 2D array with the according movement for enemyKirby
    private void setKnightKirby(){

        knightKirby[STAND_RIGHT][0] = spriteSheetCharacter.getSubimage(4, 275, 26, 30);

        knightKirby[WALK_RIGHT][0] = spriteSheetCharacter.getSubimage(33, 274, 25, 31);
        knightKirby[WALK_RIGHT][1] = spriteSheetCharacter.getSubimage(61, 273, 25, 32);

        knightKirby[PAUSED_RIGHT][0] = spriteSheetCharacter.getSubimage(106, 271, 22, 34);
        knightKirby[PAUSED_RIGHT][1] = spriteSheetCharacter.getSubimage(131, 270, 22, 35);
        knightKirby[PAUSED_RIGHT][2] = spriteSheetCharacter.getSubimage(156, 269, 22, 36);

        knightKirby[JUMP_RIGHT][0] = spriteSheetCharacter.getSubimage(266, 263, 23, 42);

        knightKirby[FALL_RIGHT][0] = spriteSheetCharacter.getSubimage(266, 263, 23, 42);

        knightKirby[SLAM_RIGHT][0] = spriteSheetCharacter.getSubimage(211, 276, 24, 29);

        knightKirby[CHARGE_RIGHT][0] = spriteSheetCharacter.getSubimage(433, 274, 23, 31);
        knightKirby[CHARGE_RIGHT][1] = spriteSheetCharacter.getSubimage(459, 272, 23, 33);
    
        knightKirby[ATTACK_RIGHT][0] = spriteSheetCharacter.getSubimage(598, 270, 33, 35);
        knightKirby[ATTACK_RIGHT][1] = spriteSheetCharacter.getSubimage(634, 273, 46, 32);
        knightKirby[ATTACK_RIGHT][2] = spriteSheetCharacter.getSubimage(683, 283, 44, 22);

        knightKirby[HURT_RIGHT][0] = spriteSheetCharacter.getSubimage(818, 268, 22, 37);

        knightKirby[PANIC_RIGHT][0] = spriteSheetCharacter.getSubimage(885, 275, 25, 30);
        knightKirby[PANIC_RIGHT][1] = spriteSheetCharacter.getSubimage(913, 270, 24, 35);
    
        knightKirby[VICTORY_RIGHT][0] = spriteSheetCharacter.getSubimage(956, 263, 32, 42);
        knightKirby[VICTORY_RIGHT][1] = spriteSheetCharacter.getSubimage(991, 275, 30, 30);
        knightKirby[VICTORY_RIGHT][2] = spriteSheetCharacter.getSubimage(1023, 277, 28, 28);
        knightKirby[VICTORY_RIGHT][3] = spriteSheetCharacter.getSubimage(1054, 273, 44, 32);
        knightKirby[VICTORY_RIGHT][4] = spriteSheetCharacter.getSubimage(1101, 281, 44, 24);
        knightKirby[VICTORY_RIGHT][5] = spriteSheetCharacter.getSubimage(1148, 280, 44, 25);
        knightKirby[VICTORY_RIGHT][6] = spriteSheetCharacter.getSubimage(1195, 281, 44, 24);

        knightKirby[LOSE_RIGHT][0] = spriteSheetCharacter.getSubimage(1246, 267, 23, 38);

        // For all left movemnt it is just right but flipped
        knightKirby[STAND_LEFT][0] = flip(knightKirby[STAND_RIGHT][0]);

        knightKirby[WALK_LEFT][0] = flip(knightKirby[WALK_RIGHT][0]);
        knightKirby[WALK_LEFT][1] = flip(knightKirby[WALK_RIGHT][1]);

        knightKirby[PAUSED_LEFT][0] = flip(knightKirby[PAUSED_RIGHT][0]);
        knightKirby[PAUSED_LEFT][1] = flip(knightKirby[PAUSED_RIGHT][1]);
        knightKirby[PAUSED_LEFT][2] = flip(knightKirby[PAUSED_RIGHT][2]);

        //knightKirby[JUMP_LEFT][0] = spriteSheetFlipped.getSubimage(1076, 41, 26, 16);
        knightKirby[JUMP_LEFT][0] = flip(knightKirby[JUMP_RIGHT][0]);

        knightKirby[FALL_LEFT][0] = flip(knightKirby[FALL_RIGHT][0]);

        knightKirby[SLAM_LEFT][0] = flip(knightKirby[SLAM_RIGHT][0]);

        knightKirby[CHARGE_LEFT][0] = flip(knightKirby[CHARGE_RIGHT][0]);
        knightKirby[CHARGE_LEFT][1] = flip(knightKirby[CHARGE_RIGHT][1]);

        knightKirby[ATTACK_LEFT][0] = flip(knightKirby[ATTACK_RIGHT][0]);
        knightKirby[ATTACK_LEFT][1] = flip(knightKirby[ATTACK_RIGHT][1]);
        knightKirby[ATTACK_LEFT][2] = flip(knightKirby[ATTACK_RIGHT][2]);

        knightKirby[HURT_LEFT][0] = flip(knightKirby[HURT_RIGHT][0]);

        knightKirby[PANIC_LEFT][0] = flip(knightKirby[PANIC_RIGHT][0]);
        knightKirby[PANIC_LEFT][1] = flip(knightKirby[PANIC_RIGHT][1]);

        knightKirby[VICTORY_LEFT][0] = flip(knightKirby[VICTORY_RIGHT][0]);
        knightKirby[VICTORY_LEFT][1] = flip(knightKirby[VICTORY_RIGHT][1]);
        knightKirby[VICTORY_LEFT][2] = flip(knightKirby[VICTORY_RIGHT][2]);
        knightKirby[VICTORY_LEFT][3] = flip(knightKirby[VICTORY_RIGHT][3]);
        knightKirby[VICTORY_LEFT][4] = flip(knightKirby[VICTORY_RIGHT][4]);
        knightKirby[VICTORY_LEFT][5] = flip(knightKirby[VICTORY_RIGHT][5]);
        knightKirby[VICTORY_LEFT][5] = flip(knightKirby[VICTORY_RIGHT][6]);

        knightKirby[LOSE_LEFT][0] = flip(knightKirby[LOSE_RIGHT][0]);
    }

    //method to set the current state to change the movement animation
    public void setCurrentState(int currentState) {


        this.currentState = currentState;
        
        if(entityType == EntityType.PLAYER){ // check the entity type before changing
            currentImageArray = normalKirby[currentState];
        }else if(entityType == EntityType.ENEMY){
            currentImageArray = knightKirby[currentState];
        }
    }

    // get the current state
    public int getCurrentState(){
        return currentState;
    }

    // draw will return the sprite according to the animation length
    public BufferedImage draw(){
        double animLength = 0.6;
        if(currentState == CHARGE_LEFT || currentState == CHARGE_RIGHT || currentState == ATTACK_RIGHT || currentState == ATTACK_LEFT) animLength = 1; // attack and charge will be slower than normal animation as there will be a timer in the other class too
        animTimer += screen.getDeltaTime();  
        if(animTimer > (animLength * 2 / 10) / currentImageArray.length && !(currentState == STAND_RIGHT || currentState == STAND_LEFT)){
            imgCount++; // increase the image count if it is biggers than 2 times the animLength div 10 and divided by the array length and is not standing right or left
            animTimer = 0;
        }

        // if the old image array is equal to the current image array then the img count goes reverse to 0
        if (oldImageArray == currentImageArray){                
            imgCount = imgCount%currentImageArray.length;              
        }
        else{
            imgCount = 0;
            oldImageArray = currentImageArray;
        }

        return currentImageArray[imgCount];
    }    

    // This flips the rgb value of the buffered image to return a buffered image that is flipped on the y axis
    private BufferedImage flip(BufferedImage sprite){
        BufferedImage img = new BufferedImage(sprite.getWidth(),sprite.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int x = sprite.getWidth()-1;x>0;x--){
            for(int y = 0;y < sprite.getHeight();y++){
                img.setRGB(sprite.getWidth()-x, y, sprite.getRGB(x, y));
            }
        }
        return img; 
    }

    // reset everything for the game to restart again
    public void reset(){
        animTimer = 999;
        imgCount = 0;
        if(entityType == EntityType.PLAYER){
            setNormalKirby();
            currentState = WALK_RIGHT;
            currentImageArray = normalKirby[WALK_RIGHT];
        }else if(entityType == EntityType.ENEMY){
            setKnightKirby();
            currentState = WALK_LEFT;
            currentImageArray = knightKirby[WALK_LEFT];
        }
        oldImageArray = currentImageArray;
    }
}
