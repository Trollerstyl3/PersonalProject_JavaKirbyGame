import java.awt.image.*;
import javax.imageio.*;
import javax.swing.text.html.parser.Entity;
import javax.xml.stream.events.EntityReference;

import java.io.*;
import java.awt.*;

public class CharacterData {
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

    BufferedImage[][] normalKirby;
    BufferedImage[][] knightKirby;

    boolean found;
    int imgCount,currentState;
    EntityType entityType;

    BufferedImage[] currentImageArray, oldImageArray;
    
    double animTimer;

    RunScreen screen;

    public CharacterData(RunScreen screen,EntityType entityType){
        this.screen = screen;

        this.entityType = entityType;

        imgCount = 0;
        currentState = 0;
        loadImages();
    }

    public void loadImages(){
        normalKirby = new BufferedImage [24][];
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

        knightKirby = new BufferedImage [24][];
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
        found = true;

        BufferedImage spriteSheetCharacter = null;

        File myFileCharacter = new File("KirbyGame/res/kirbys_character.png");

        try {
            spriteSheetCharacter = ImageIO.read(myFileCharacter);   
        } 
        catch (IOException e) {
            found = false;
            System.out.println("not found");
        }

        if(found){
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



            
            if(entityType == EntityType.PLAYER){
                currentImageArray = normalKirby[WALK_RIGHT];
            }else if(entityType == EntityType.ENEMY){
                currentImageArray = knightKirby[WALK_LEFT];
            }
            
            oldImageArray = currentImageArray;

        }
    }

    public void setCurrentState(int currentState) {


        this.currentState = currentState;
        
        currentImageArray = normalKirby[currentState];
    }

    public int getCurrentState(){
        return currentState;
    }


    public BufferedImage draw(){
        double animLength = 0.6;
        if(currentState == CHARGE_LEFT || currentState == CHARGE_RIGHT || currentState == ATTACK_RIGHT || currentState == ATTACK_LEFT) animLength = 0.4;
        animTimer += screen.getDeltaTime();  
        if(animTimer > (animLength + animLength / 10) / currentImageArray.length && !(currentState == STAND_RIGHT || currentState == STAND_LEFT)){
            imgCount++;
            animTimer = 0;
        }

        if (oldImageArray == currentImageArray){                
            imgCount = imgCount%currentImageArray.length;              
        }
        else{
            imgCount = 0;
            oldImageArray = currentImageArray;
        }

        return currentImageArray[imgCount];
    }    


    BufferedImage flip(BufferedImage sprite){
        BufferedImage img = new BufferedImage(sprite.getWidth(),sprite.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int xx = sprite.getWidth()-1;xx>0;xx--){
            for(int yy = 0;yy < sprite.getHeight();yy++){
                img.setRGB(sprite.getWidth()-xx, yy, sprite.getRGB(xx, yy));
            }
        }
    return img;
}
}
