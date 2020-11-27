/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class is incharge of the Player and all its movement and its animation
 */

// Please don't remove the packages because I have many folders
package src.objects;

import java.awt.*;
import java.awt.image.*;
import src.loader.*;
import src.util.*;
import src.*;
import src.objects.enums.*;
import src.objects.interfaces.*;

// Create the player class that will extend the game entity and implement the object interface
public class Player extends GameEntity implements ObjectInterface{

  // Declare variables needed
    CollisionChecker collision;

    // movement variables    
    public boolean moveLeft;
    public boolean moveRight;
    public boolean moveUp;
    public boolean moveDown;

    //moves variable
    public boolean charge;
    public boolean attack;
    public boolean taunt;
    public boolean getHit;

    private double scale; 
    private int health, attackDamage, slamDamage;
    public int damageReceived;

    double hurtTime;

    double attackTime;
    int attackCounter;
    int attackLimit;
    int chargeAllowed;

    double selfDamageTime;

    BufferedImage current;
    CharacterState attackSide;

    int xOffSet,yOffSet;

    int initX,initY;

    Stage stage;

    // constructor of the class that will take in the starting x and y and the screen
    public Player(int x, int y, RunScreen screen){

        // call the super constructor and pass in that this is the player
        super(x,y,screen,EntityType.PLAYER);

        // save initX and initY for reset later
        initX = x;
        initY = y;

        // set everything else to default values
        collision = new CollisionChecker();
    
        states.add(CharacterState.GROUNDED);
        states.add(CharacterState.RIGHT);

        dx = 0;
        dy = 0;

        yOffSet = 0;
        xOffSet = 0;

        scale = 2.5;

        attackTime = 0;
        attackCounter = 0;
        attackLimit = 0;

        health = 100;
        attackDamage = 15;
        slamDamage = 10;
        getHit = false;

        selfDamageTime = 0;

        stage = screen.stage;
    }

// method that is in charge of the movement of the character
    public void move(){
        // if it get hit will blocking then get hit does not work
        if(getHit && moveDown && states.contains(CharacterState.GROUNDED)){
            getHit = false;
        }

        // check if moving left or right and set dx accordingly
        if(moveLeft && moveRight || !moveLeft && !moveRight){
            dx *= 0.8; // gives slowing down effect
            if(states.contains(CharacterState.SLAM)) dx = 0;

            // if the it is attacking or charging without moving then set it to 5 either way
            if((states.contains(CharacterState.CHARGING) || (states.contains(CharacterState.ATTACKING) && states.contains(CharacterState.GROUNDED)))&& states.contains(CharacterState.RIGHT)) dx = 5;
            if((states.contains(CharacterState.CHARGING) || (states.contains(CharacterState.ATTACKING) && states.contains(CharacterState.GROUNDED))) && states.contains(CharacterState.LEFT)) dx = -5;
        }else if(moveLeft && !moveRight){ // if moving left then dx will be decreasing to only the maximum of 7
            
            dx = Math.max( -7 ,--dx); 

            // checks the state and set it accordingly
            if(states.contains(CharacterState.CHARGING)  && attackSide == CharacterState.LEFT) dx += -5; 
            if(states.contains(CharacterState.ATTACKING)  && attackSide == CharacterState.LEFT  && states.contains(CharacterState.GROUNDED)) dx = -7;
            if(states.contains(CharacterState.SLAM))  dx = 0;
            if(states.contains(CharacterState.RIGHT) && (attackSide == CharacterState.LEFT || attackSide == null))states.remove(CharacterState.RIGHT);
            if(!states.contains(CharacterState.LEFT) && (attackSide == CharacterState.LEFT || attackSide == null))states.add(CharacterState.LEFT);
        
        }else if(!moveLeft && moveRight){ // if moving right then dx will be increasing to only the maximum of 7
            dx =Math.min( 7 ,++dx);  

            // checks the state and set it accordingly
            if(states.contains(CharacterState.ATTACKING)  && attackSide == CharacterState.RIGHT  && states.contains(CharacterState.GROUNDED)) dx = 7;
            if(states.contains(CharacterState.CHARGING) && attackSide == CharacterState.RIGHT) dx += 5;
            if(states.contains(CharacterState.SLAM)) dx = 0;
            if(!states.contains(CharacterState.RIGHT) && (attackSide == CharacterState.RIGHT || attackSide == null))states.add(CharacterState.RIGHT);
            if(states.contains(CharacterState.LEFT) && (attackSide == CharacterState.RIGHT || attackSide == null))states.remove(CharacterState.LEFT);
    
        }
        
        dy += 0.3; // character is always falling

        //if W is pressed then it will check if character is on the ground if it is then jump
        if(moveUp){
            hitBox.y +=2;
            if(collision.check(stage.getHitBox(),getHitBox())){
                dy = -12;
                if(states.contains(CharacterState.GROUNDED))states.remove(CharacterState.GROUNDED);
                if(!states.contains(CharacterState.JUMPING))states.add(CharacterState.JUMPING);
            }
            hitBox.y -= 2;
        }

        // check if taunt is pressed and if is add taunt
        if(taunt){
            if(!states.contains(CharacterState.TAUNTING))states.add(CharacterState.TAUNTING);
        }else{
            if(states.contains(CharacterState.TAUNTING))states.remove(CharacterState.TAUNTING);
        }

        // if on the ground then reset the chargeAllowed to 1
        if(states.contains(CharacterState.GROUNDED)) chargeAllowed = 1;

        // check if charge is pressed only add charge if character is on the ground or there is still one charge left
        if(charge){
            if(states.contains(CharacterState.GROUNDED) || chargeAllowed > 0){
                if(!states.contains(CharacterState.CHARGING))states.add(CharacterState.CHARGING);
                if(chargeAllowed > 0) chargeAllowed--;
            }
        }

        // check if attack is pressed and if is add attacking
        if(attack){
            if(!states.contains(CharacterState.ATTACKING))states.add(CharacterState.ATTACKING);
        }

        // check if get hit is true and if is the remove attacking
        if(getHit){
        
            if(!states.contains(CharacterState.HURT))states.add(CharacterState.HURT);
            if(states.contains(CharacterState.ATTACKING))states.remove(CharacterState.ATTACKING);
            if(states.contains(CharacterState.CHARGING))states.remove(CharacterState.CHARGING);
            if(states.contains(CharacterState.TAUNTING))states.remove(CharacterState.TAUNTING);
        }

        // if moving down then make dx move faster and then remove every other state as slam is supreme
        if(moveDown){
            if(dy == -12) dy = 0;
            dy += 1.5;
            attackSide = null;

            if(states.contains(CharacterState.JUMPING))states.remove(CharacterState.JUMPING);
            if(states.contains(CharacterState.FALLING))states.remove(CharacterState.FALLING);
            if(states.contains(CharacterState.CHARGING))states.remove(CharacterState.CHARGING);
            if(states.contains(CharacterState.ATTACKING))states.remove(CharacterState.ATTACKING);
            if(!states.contains(CharacterState.SLAM))states.add(CharacterState.SLAM);
        }else{
            if(states.contains(CharacterState.SLAM))states.remove(CharacterState.SLAM);
        }

        //check for is hurt or attacking so it can run the animation
        checkState();

        // if the dx is lower than 0.75 than just make it 0 and stop
        if(dx > -0.75 && dx < 0.75){
            dx = 0;
            if(!states.contains(CharacterState.STOPPED))states.add(CharacterState.STOPPED);
        }else{
            if(states.contains(CharacterState.STOPPED))states.remove(CharacterState.STOPPED);
        }

        // if dy changes from postivie to negative than we know it is falling so it can change the animation
        if(dy > 0){
            if(states.contains(CharacterState.JUMPING))states.remove(CharacterState.JUMPING);
            if(!states.contains(CharacterState.FALLING))states.add(CharacterState.FALLING);
        }  

        // if falling or slam or is grounded always check for y collision
        if(states.contains(CharacterState.GROUNDED) || states.contains(CharacterState.FALLING) || states.contains(CharacterState.SLAM)){
            checkYCollision();
        }

        // check the animation to play the correct animation
        checkAnimation();

        // dx and dy is added to x and y
        x += dx;
        y += dy;

        // hit box change accordingly
        hitBox.x = x;
        hitBox.y = y;
        
        // check if outside of the screen then would do self damage
        checkSelfDamage();
        
    }

    // draw the character on the screen
    public void draw(Graphics2D g2d){

        current = sprite.draw();// get the sprite
            
        yOffSet = (int) (current.getHeight()*scale); // calculating the offset

        hitBox.setFrame(x-xOffSet, y-yOffSet,(int) (current.getWidth()* scale),(int) (current.getHeight()* scale)); // set the hitbox to be the same as the sprite

        // draw the health bar
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(10, 10, 100*3, 50);
        g2d.setPaint(Color.green);
        g2d.fillRect(10, 10, health*3, 50);
        
        // draw character
        g2d.drawImage(current, x-xOffSet, y-yOffSet,(int) (current.getWidth()* scale),(int) (current.getHeight()* scale),null);
    }

    // get the damage that the character is dealing out
    public int getDamage(){
        if(states.contains(CharacterState.ATTACKING)){
            return attackDamage;
        }else if(states.contains(CharacterState.SLAM)){
            return slamDamage;
        }else{
            return 0;
        }
    }

    // check the current state, check if hurt or is attacking or is charging
    private void checkState(){
        if(states.contains(CharacterState.HURT)){
            doHurtAnimation();
        }else if(states.contains(CharacterState.ATTACKING)){
            doAttackAnimation();
        }else if(states.contains(CharacterState.CHARGING)){
            doChargingAnimation();
        }
    }

    // this times the attack animation so that it looks good, and makes sure that the attack animation is going in the correct direction
    private void doAttackAnimation(){
        double attackLength = 0.2;
            if(attackCounter == 0 && states.contains(CharacterState.LEFT)) attackSide = CharacterState.LEFT;
            else if(attackCounter == 0 && states.contains(CharacterState.RIGHT)) attackSide = CharacterState.RIGHT;
            if(attackCounter < 4){
                if(attackSide == CharacterState.LEFT) dx = -Math.abs(dx);
                else if(attackSide == CharacterState.RIGHT) dx = Math.abs(dx);
                
                if(attackCounter < 2 && attackSide == CharacterState.RIGHT && states.contains(CharacterState.GROUNDED)) xOffSet = 25;
                if(attackCounter >= 2 && attackSide == CharacterState.LEFT && states.contains(CharacterState.GROUNDED)) xOffSet = 25;
                
                if(attackCounter == 2) screen.soundManager.swingPlayer.play();

                attackTime += screen.getDeltaTime();
                if(attackTime > (attackLength + attackLength / 10) /  5){
                    attackCounter++;
                    attackTime = 0;
                }
            }else{
                attackCounter = 0;
                attackTime = 0;
                attackSide = null;
                xOffSet = 0;
                if(states.contains(CharacterState.ATTACKING))states.remove(CharacterState.ATTACKING);
            }
    }

    // if the character is hurt then run this, it makes sure that it have to run the hurt animation for at least 0.5 seconds
    private void doHurtAnimation(){
        double hurtLength = 0.5;
        hurtTime += screen.getDeltaTime();
        if(hurtTime < hurtLength){
            if(hurtTime == screen.getDeltaTime()) screen.soundManager.damagedPlayer.play();
            dx = 0;
            charge = false;
            attack = false;
            taunt = false;
            if(!states.contains(CharacterState.HURT)) states.add(CharacterState.HURT);
        }else{
            health -= damageReceived;
            states.remove(CharacterState.HURT);
            hurtTime = 0;
            getHit = false;
            damageReceived = 0;
        }
    }

    // this times the charge animation so that it looks good, and makes sure that the charge animation is going in the correct direction
    private void doChargingAnimation(){
        double attackLength = 0.2;
        if(attackCounter == 0 && states.contains(CharacterState.LEFT)) attackSide = CharacterState.LEFT;
        else if(attackCounter == 0 && states.contains(CharacterState.RIGHT)) attackSide = CharacterState.RIGHT;
        if(attackCounter < 5){
            if(attackSide == CharacterState.LEFT) dx = -Math.abs(dx);
            else if(attackSide == CharacterState.RIGHT) dx = Math.abs(dx);
            attackTime += screen.getDeltaTime();
            dy = 0;
            if(attackTime > (attackLength + attackLength / 10) /  5){
                attackCounter++;
                attackTime = 0;
            }
        }else{
            attackCounter = 0;
            attackTime = 0;
            attackSide = null;
            if(states.contains(CharacterState.CHARGING))states.remove(CharacterState.CHARGING);
        }
    }

    // This checks what state the character is currently in and pass that on to the sprite so it can be drawn later
    private void checkAnimation(){
        if(states.contains(CharacterState.HURT)){
            if(states.contains(CharacterState.LEFT)) sprite.setCurrentState(CharacterData.HURT_LEFT);
            else if(states.contains(CharacterState.RIGHT)) sprite.setCurrentState(CharacterData.HURT_RIGHT);


        }else if(states.contains(CharacterState.GROUNDED)){

            if(states.contains(CharacterState.RIGHT)){
                sprite.setCurrentState(CharacterData.WALK_RIGHT);
                if(states.contains(CharacterState.SLAM)){
                    sprite.setCurrentState(CharacterData.SLAM_RIGHT);
                }else if(states.contains(CharacterState.ATTACKING)){
                    if(states.contains(attackSide) || attackSide == null){
                    sprite.setCurrentState(CharacterData.ATTACK_RIGHT);
                    }else{
                        sprite.setCurrentState(CharacterData.ATTACK_LEFT);
                    }
                }else if(states.contains(CharacterState.CHARGING)){
                    if(states.contains(attackSide) || attackSide == null){
                    sprite.setCurrentState(CharacterData.CHARGE_RIGHT);
                    }else{
                        sprite.setCurrentState(CharacterData.CHARGE_LEFT);
                    }
                }else if(states.contains(CharacterState.STOPPED)){
                    sprite.setCurrentState(CharacterData.STAND_RIGHT);
                    if(states.contains(CharacterState.TAUNTING)) sprite.setCurrentState(CharacterData.PANIC_RIGHT);
                }
            }

            if(states.contains(CharacterState.LEFT)){
                
                sprite.setCurrentState(CharacterData.WALK_LEFT);
                if(states.contains(CharacterState.SLAM)){
                    sprite.setCurrentState(CharacterData.SLAM_LEFT);
                } else if(states.contains(CharacterState.ATTACKING)){
                    if(states.contains(attackSide) || attackSide == null){
                    sprite.setCurrentState(CharacterData.ATTACK_LEFT);
                    }else{
                        sprite.setCurrentState(CharacterData.ATTACK_RIGHT);
                    }
                }else if(states.contains(CharacterState.CHARGING)){
                    if(states.contains(attackSide) || attackSide == null){
                        sprite.setCurrentState(CharacterData.CHARGE_LEFT);
                    }else{
                    sprite.setCurrentState(CharacterData.CHARGE_RIGHT);
                    }
                }else if(states.contains(CharacterState.STOPPED)){
                    sprite.setCurrentState(CharacterData.STAND_LEFT);
                if(states.contains(CharacterState.TAUNTING)) sprite.setCurrentState(CharacterData.PANIC_LEFT);
                }
            }

        }else if(states.contains(CharacterState.SLAM)){

            if(states.contains(CharacterState.RIGHT)) sprite.setCurrentState(CharacterData.SLAM_RIGHT);
            if(states.contains(CharacterState.LEFT)) sprite.setCurrentState(CharacterData.SLAM_LEFT);

        }else if(states.contains(CharacterState.CHARGING)){

            if(states.contains(CharacterState.RIGHT)) sprite.setCurrentState(CharacterData.CHARGE_RIGHT);
            if(states.contains(CharacterState.LEFT)) sprite.setCurrentState(CharacterData.CHARGE_LEFT);
        
        }else if(states.contains(CharacterState.JUMPING)){

            if(states.contains(CharacterState.RIGHT)) sprite.setCurrentState(CharacterData.JUMP_RIGHT);
            if(states.contains(CharacterState.LEFT)) sprite.setCurrentState(CharacterData.JUMP_LEFT);

        }else if(states.contains(CharacterState.FALLING)){

            if(states.contains(CharacterState.RIGHT)) sprite.setCurrentState(CharacterData.FALL_RIGHT);
            if(states.contains(CharacterState.LEFT)) sprite.setCurrentState(CharacterData.FALL_LEFT);

        }
    }

    private void checkYCollision(){
        hitBox.y += dy;
        
        if(collision.check(stage.getHitBox(),getHitBox())){
            hitBox.y -= dy;

            while(!collision.check(stage.getHitBox(),getHitBox())) hitBox.y ++;
            hitBox.y -=2;
            y = (int) hitBox.y+yOffSet;
            dy = 0;
            if(states.contains(CharacterState.FALLING))states.remove(CharacterState.FALLING);
            if(!states.contains(CharacterState.GROUNDED))states.add(CharacterState.GROUNDED);
        }
    }

    // checks if outside of screen if outside then take damage 
    private void checkSelfDamage(){
        double selfDamageLength = 1;
        selfDamageTime += screen.getDeltaTime();
        if(selfDamageTime > selfDamageLength && ((x < 0-getWidth() || x > 700) || y > 480)){
            screen.soundManager.damagedPlayer.play();
            health -= 10;
            selfDamageTime = 0;
        }
    }

    // return the health currently
    public int getHealth(){
        return health;
    }
    
    // reset everything for the game to restart
    public void reset(){
        x = initX;
        y = initY;

        dx = 0;
        dy = 0;

        yOffSet = 0;
        xOffSet = 0;

        scale = 2.5;

        attackTime = 999;
        attackCounter = 0;
        attackLimit = 0;

        health = 100;
        attackDamage = 15;
        slamDamage = 10;
        getHit = false;
        
        selfDamageTime = 999;
        hurtTime = 999;
    }

}
