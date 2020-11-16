package src.objects;

import java.awt.*;
import java.awt.geom.*;
import src.objects.interfaces.*;

public class Walls implements ObjectInterface{
    
    int x;
    int y;
    int width;
    int height;

    Rectangle2D.Double hitBox;


    public Walls(int x, int y){
        this.x = x;
        this.y = y;

        width = 50;
        height = 50;

        hitBox = new Rectangle2D.Double(x,y,width,height);
    }

    public void draw(Graphics2D g2d){
        g2d.setPaint(Color.BLACK);
        g2d.draw(hitBox);
    }

    public Shape getHitBox(){
        return hitBox;
    }



}
