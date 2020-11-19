package src.objects;

import java.awt.Graphics2D;
import java.awt.Shape;

import src.objects.interfaces.ObjectInterface;
import src.util.CollisionChecker;
import src.RunScreen;
import src.loader.BackgroundData;
import java.awt.image.BufferedImage;

public class Background implements ObjectInterface {

    RunScreen screen;
    CollisionChecker collision;
    BackgroundData sprite;

    int x;
    int y;
    int width;
    int height;

    double scale;


    public Background(int x, int y,RunScreen screen){
        this.screen = screen;
        sprite = new BackgroundData(screen);
        this.x = x;
        this.y = y;
        scale = 1;

        width = sprite.draw().getWidth();
        height = sprite.draw().getHeight();

    }


    @Override
    public void draw(Graphics2D g2d) {
        
        BufferedImage current = sprite.draw();

        g2d.drawImage(current, x, y, (int) (current.getWidth()*scale),(int) (current.getHeight()*scale),null);
    }

    @Override
    public Shape getHitBox() {
        return null;
    }

    
}
    