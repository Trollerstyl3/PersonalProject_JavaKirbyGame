import java.awt.*;
import java.awt.geom.*; 
import java.awt.image.*;
import java.util.ArrayList;

public class Player {

    CharacterData sprite;
    CollisionChecker collision;
    ArrayList<CharacterState> states;

    boolean moveLeft;
    boolean moveRight;
    boolean moveUp;
    boolean moveDown;

    boolean attack;
    boolean charge;
    boolean taunt;

    private double dx;
    private double dy;
    
    private int x;
    private int y;
    private int height;
    private int width;

    private int scale; 

    double chargeTime;
    int chargeCounter;
    CharacterState chargeSide;

    int xOffSet,yOffSet;

    private Rectangle2D.Double hitBox;
    
    private RunScreen screen;

    public Player(int x, int y, RunScreen screen){

        collision = new CollisionChecker();
        sprite = new CharacterData(screen);
    
        states = new ArrayList<>();
        states.add(CharacterState.GROUNDED);
        states.add(CharacterState.RIGHT);

        dx = 0;
        dx = 0;

        yOffSet = 0;
        xOffSet = 0;

        this.x = x;
        this.y = y;
        this.screen = screen;

        width = 100;
        height = 100;

        scale = 2;

        chargeTime = 0;
        chargeCounter = 0;

        hitBox = new Rectangle2D.Double(x,y,width,height);
    }


    public void move(){
        
        if(moveLeft && moveRight || !moveLeft && !moveRight){
            dx *= 0.8;
            if(states.contains(CharacterState.SLAM)) dx = 0;
            if(states.contains(CharacterState.CHARGING) && states.contains(CharacterState.RIGHT)) dx = 5;
            if(states.contains(CharacterState.CHARGING) && states.contains(CharacterState.LEFT)) dx = -5;
        }else if(moveLeft && !moveRight){
            
                dx = Math.max( -7 ,--dx);  
                if(states.contains(CharacterState.SLAM))  dx = 0;
                if(states.contains(CharacterState.CHARGING) && states.contains(chargeSide)) dx += -5;
                if(states.contains(CharacterState.RIGHT))states.remove(CharacterState.RIGHT);
                if(!states.contains(CharacterState.LEFT))states.add(CharacterState.LEFT);
            
        }else if(!moveLeft && moveRight){
                if(states.contains(CharacterState.CHARGING) && )dx =Math.min( 7 ,++dx);  
                if(states.contains(CharacterState.SLAM)) dx = 0;
                if(states.contains(CharacterState.CHARGING) && states.contains(chargeSide)) dx += 5;
                if(!states.contains(CharacterState.RIGHT))states.add(CharacterState.RIGHT);
                if(states.contains(CharacterState.LEFT))states.remove(CharacterState.LEFT);
        
        }
        
        dy += 0.3;

        if(moveUp){
            for(Walls wall: screen.walls){
                hitBox.y +=2;
                if(collision.check(wall.getHitBox(),getHitBox())){
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

        if(moveDown){
            if(dy == -12) dy = 0;
            dy += 1.5;
            chargeSide = null;
            if(states.contains(CharacterState.JUMPING))states.remove(CharacterState.JUMPING);
            if(states.contains(CharacterState.FALLING))states.remove(CharacterState.FALLING);
            if(states.contains(CharacterState.CHARGING))states.remove(CharacterState.CHARGING);
            if(!states.contains(CharacterState.SLAM))states.add(CharacterState.SLAM);
        }else{
            if(states.contains(CharacterState.SLAM))states.remove(CharacterState.SLAM);
        }

        if(states.contains(CharacterState.CHARGING) && !states.contains(CharacterState.SLAM)){
            double chargeLength = 0.9;
            if(chargeCounter == 0 && states.contains(CharacterState.LEFT)) chargeSide = CharacterState.LEFT;
            else if(chargeCounter == 0 && states.contains(CharacterState.RIGHT)) chargeSide = CharacterState.RIGHT;
            if(chargeCounter < 4){
                chargeTime += screen.getDeltaTime();
                dy = 0;
                if(chargeTime > (chargeLength + chargeLength / 10) /  5){
                    chargeCounter++;
                    chargeTime = 0;
                }
            }else{
                chargeCounter = 0;
                chargeTime = 0;
                chargeSide = null;
                if(states.contains(CharacterState.CHARGING))states.remove(CharacterState.CHARGING);
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
                }else if(states.contains(CharacterState.CHARGING)){
                    if(states.contains(chargeSide) || chargeSide == null){
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
                }else if(states.contains(CharacterState.CHARGING)){
                    if(states.contains(chargeSide) || chargeSide == null){
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

            if(states.contains(CharacterState.RIGHT) && states.contains(chargeSide)) sprite.setCurrentState(CharacterData.JUMP_RIGHT);
            if(states.contains(CharacterState.LEFT) && states.contains(chargeSide)) sprite.setCurrentState(CharacterData.JUMP_LEFT);

        }else if(states.contains(CharacterState.FALLING)){

            if(states.contains(CharacterState.RIGHT)) sprite.setCurrentState(CharacterData.FALL_RIGHT);
            if(states.contains(CharacterState.LEFT)) sprite.setCurrentState(CharacterData.FALL_LEFT);

        }

        




        if(states.contains(CharacterState.GROUNDED) || states.contains(CharacterState.FALLING) || states.contains(CharacterState.SLAM)){
            hitBox.y += dy;
            for(Walls wall: screen.walls){

                if(collision.check(wall.getHitBox(),getHitBox())){
                    hitBox.y -= dy;

                    while(!collision.check(wall.getHitBox(),getHitBox())) hitBox.y ++;
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

        BufferedImage current = sprite.draw();
        
        yOffSet = current.getHeight()*scale;
        
        hitBox.setFrame(x, y-yOffSet,current.getWidth()* scale, current.getHeight()* scale);
        g2d.setPaint(Color.RED);
        g2d.draw(hitBox);
        g2d.drawString((states.contains(chargeSide) || chargeSide == null) + "", 10, 10);
        g2d.drawImage(current, x, y-yOffSet, current.getWidth()* scale, current.getHeight() * scale,null);
    }

    public Shape getHitBox(){
        return hitBox;
    }

}
