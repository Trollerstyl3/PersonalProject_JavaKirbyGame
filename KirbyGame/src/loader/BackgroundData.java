package src.loader;

import java.awt.image.*;
import javax.imageio.*;
import src.*;
import java.io.*;

public class BackgroundData {

    BufferedImage backgroundSheet;

    RunScreen screen;

    int imgCount;
    boolean found;



    public BackgroundData(RunScreen screen){

        this.screen = screen;

        loadImage();
    }

    private void loadImage(){

        
        found = true;

        backgroundSheet = null;

        File stageOneFile = new File("src/res/background.png");

        try {
            backgroundSheet = ImageIO.read(stageOneFile);   
        } 
        catch (IOException e) {
            found = false;
            System.out.println("not found");
        }

    }

    public BufferedImage draw(){
        return backgroundSheet;
    }

    
}