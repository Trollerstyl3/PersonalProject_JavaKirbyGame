package src.objects;

import java.awt.Graphics2D;
import java.awt.Shape;

import src.objects.interfaces.ObjectInterface;
import src.util.CollisionChecker;
import src.RunScreen;
import src.loader.StageData;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Stage implements ObjectInterface {

    RunScreen screen;
    CollisionChecker collision;
    StageData sprite;

    int x;
    int y;
    int width;
    int height;

    double scale;

    Rectangle2D.Double hitBox;

    public Stage(int x, int y,RunScreen screen){
        this.screen = screen;
        sprite = new StageData(screen);
        this.x = x;
        this.y = y;
        scale = 3.3;

        width = sprite.draw().getWidth();
        height = sprite.draw().getHeight();

        hitBox = new Rectangle2D.Double(x,y+80,(int) (width*scale),(int)(height*scale-80));
        
    }


    @Override
    public void draw(Graphics2D g2d) {
        
        BufferedImage current = sprite.draw();

        g2d.drawImage(current, x, y, (int) (current.getWidth()*scale),(int) (current.getHeight()*scale),null);
    }

    @Override
    public Shape getHitBox() {
        return hitBox;
    }

    
}
    

