package src.objects;

import java.awt.*;
import java.awt.image.*;
import src.loader.*;
import src.util.*;
import src.*;
import src.objects.enums.*;
import src.objects.interfaces.*;

public class Enemy extends GameEntity implements ObjectInterface{

    CollisionChecker collision;

    public boolean moveLeft;
    public boolean moveRight;
    public boolean moveUp;
    public boolean moveDown;

    public boolean charge;
    public boolean attack;
    public boolean taunt;

    private int scale; 

    double attackTime;
    int attackCounter;
    int attackLimit;

    BufferedImage current;
    CharacterState attackSide;

    int xOffSet,yOffSet;

    public Enemy(int x, int y, RunScreen screen){

        super(x,y,screen,EntityType.ENEMY);

        collision = new CollisionChecker();
    
        states.add(CharacterState.GROUNDED);
        states.add(CharacterState.RIGHT);

        dx = 0;
        dy = 0;

        yOffSet = 0;
        xOffSet = 0;

        scale = 2;

        attackTime = 0;
        attackCounter = 0;
        attackLimit = 0;
    }


    public void move(){
        
        if(moveLeft && moveRight || !moveLeft && !moveRight){
            dx *= 0.8;
            if(states.contains(CharacterState.SLAM)) dx = 0;
            if((states.contains(CharacterState.CHARGING) || (states.contains(CharacterState.ATTACKING) && states.contains(CharacterState.GROUNDED)))&& states.contains(CharacterState.RIGHT)) dx = 5;
            if((states.contains(CharacterState.CHARGING) || (states.contains(CharacterState.ATTACKING) && states.contains(CharacterState.GROUNDED))) && states.contains(CharacterState.LEFT)) dx = -5;
        }else if(moveLeft && !moveRight){
            
                dx = Math.max( -7 ,--dx); 
                if(states.contains(CharacterState.CHARGING)  && attackSide == CharacterState.LEFT) dx += -5; 
                if(states.contains(CharacterState.ATTACKING)  && attackSide == CharacterState.LEFT  && states.contains(CharacterState.GROUNDED)) dx = -7;
                if(states.contains(CharacterState.SLAM))  dx = 0;
                if(states.contains(CharacterState.RIGHT) && (attackSide == CharacterState.LEFT || attackSide == null))states.remove(CharacterState.RIGHT);
                if(!states.contains(CharacterState.LEFT) && (attackSide == CharacterState.LEFT || attackSide == null))states.add(CharacterState.LEFT);
            
        }else if(!moveLeft && moveRight){
                dx =Math.min( 7 ,++dx);  
                if(states.contains(CharacterState.ATTACKING)  && attackSide == CharacterState.RIGHT  && states.contains(CharacterState.GROUNDED)) dx = 7;
                if(states.contains(CharacterState.CHARGING) && attackSide == CharacterState.RIGHT) dx += 5;
                if(states.contains(CharacterState.SLAM)) dx = 0;
                if(!states.contains(CharacterState.RIGHT) && (attackSide == CharacterState.RIGHT || attackSide == null))states.add(CharacterState.RIGHT);
                if(states.contains(CharacterState.LEFT) && (attackSide == CharacterState.RIGHT || attackSide == null))states.remove(CharacterState.LEFT);
        
        }
        
        dy += 0.3;

        if(moveUp){
            for(Walls wall: screen.walls){
                hitBox.y +=2;
                if(collision.checkWall(wall.getHitBox(),getHitBox())){
                    dy = -12;
                    if(states.contains(CharacterState.GROUNDED))states.remove(CharacterState.GROUNDED);
                    if(!states.contains(CharacterState.JUMPING))states.add(CharacterState.JUMPING);
                }
                hitBox.y -= 2;
            }
        }

        if(taunt){
            if(!states.contains(CharacterState.TAUNTING))states.add(CharacterState.TAUNTING);
        }else{
            if(states.contains(CharacterState.TAUNTING))states.remove(CharacterState.TAUNTING);
        }

        if(charge){
            if(!states.contains(CharacterState.CHARGING))states.add(CharacterState.CHARGING);
        }

        if(attack){
            if(!states.contains(CharacterState.ATTACKING))states.add(CharacterState.ATTACKING);
        }

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

        if(states.contains(CharacterState.CHARGING) && !states.contains(CharacterState.SLAM)){
            double attackLength = 0.4;
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

        if(states.contains(CharacterState.ATTACKING) && !states.contains(CharacterState.SLAM)){
            double attackLength = 0.4;
            if(attackCounter == 0 && states.contains(CharacterState.LEFT)) attackSide = CharacterState.LEFT;
            else if(attackCounter == 0 && states.contains(CharacterState.RIGHT)) attackSide = CharacterState.RIGHT;
            if(attackCounter < 4){
                if(attackSide == CharacterState.LEFT) dx = -Math.abs(dx);
                else if(attackSide == CharacterState.RIGHT) dx = Math.abs(dx);
                
                if(attackCounter < 2 && attackSide == CharacterState.RIGHT && states.contains(CharacterState.GROUNDED)) xOffSet = 25;
                if(attackCounter >= 2 && attackSide == CharacterState.LEFT && states.contains(CharacterState.GROUNDED)) xOffSet = 25;

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

        if(dx > -0.75 && dx < 0.75){
            dx = 0;
            if(!states.contains(CharacterState.STOPPED))states.add(CharacterState.STOPPED);
        }else{
            if(states.contains(CharacterState.STOPPED))states.remove(CharacterState.STOPPED);
        }

        if(dy > 0){
            if(states.contains(CharacterState.JUMPING))states.remove(CharacterState.JUMPING);
            if(!states.contains(CharacterState.FALLING))states.add(CharacterState.FALLING);
        }
    
        if(states.contains(CharacterState.GROUNDED)){


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

        




        if(states.contains(CharacterState.GROUNDED) || states.contains(CharacterState.FALLING) || states.contains(CharacterState.SLAM)){
            hitBox.y += dy;
            for(Walls wall: screen.walls){

                if(collision.checkWall(wall.getHitBox(),getHitBox())){
                    hitBox.y -= dy;

                    while(!collision.checkWall(wall.getHitBox(),getHitBox())) hitBox.y ++;
                    hitBox.y -=2;
                    y = (int) hitBox.y+yOffSet;
                    dy = 0;
                    if(states.contains(CharacterState.FALLING))states.remove(CharacterState.FALLING);
                    if(!states.contains(CharacterState.GROUNDED))states.add(CharacterState.GROUNDED);
                }

            }
        }


        x += dx;
        y += dy;

        hitBox.x = x;
        hitBox.y = y;
    }

    public void draw(Graphics2D g2d){


        current = sprite.draw();

            
        yOffSet = current.getHeight()*scale;

        
        hitBox.setFrame(x-xOffSet, y-yOffSet,current.getWidth()* scale, current.getHeight()* scale);
        g2d.setPaint(Color.RED);
        g2d.draw(hitBox);
        g2d.drawImage(current, x-xOffSet, y-yOffSet, current.getWidth()* scale, current.getHeight() * scale,null);
    }

}
