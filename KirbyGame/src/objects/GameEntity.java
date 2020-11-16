package src.objects;

import java.util.*;
import java.awt.geom.*;
import java.awt.*;
import src.loader.*;
import src.*;
import src.objects.enums.*;

public class GameEntity {
    
    protected int x;
    protected int y;
    private int height;
    private int width;

    protected double dx;
    protected double dy;

    protected ArrayList<CharacterState> states;
    protected RunScreen screen;
    protected CharacterData sprite;
    
    protected Rectangle2D.Double hitBox;
    
    public GameEntity(int x, int y, RunScreen screen, EntityType entityType){
                
        states = new ArrayList<CharacterState>();
        dx = 0;
        dy = 0;

        this.x = x;
        this.y = y;
        this.screen = screen;

        width = 100;
        height = 100;

        sprite = new CharacterData(screen, entityType);

        hitBox = new Rectangle2D.Double(x,y,width,height);

    }

    public Shape getHitBox(){
        return hitBox;
    }
    
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public ArrayList<CharacterState> getStates(){
        return states;
    }
}
