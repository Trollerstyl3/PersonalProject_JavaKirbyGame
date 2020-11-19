package src.loader;

import java.awt.image.*;
import javax.imageio.*;
import src.*;
import java.io.*;

public class StageData {

    BufferedImage stageOneSheet;

    BufferedImage[] stage;

    RunScreen screen;

    int imgCount;
    boolean found;

    BufferedImage[] currentImageArray;


    public StageData(RunScreen screen){

        this.screen = screen;

        imgCount = 0;
        loadImage();
    }

    private void loadImage(){
        initMap();

        
        found = true;

        stageOneSheet = null;

        File stageOneFile = new File("src/res/stage_1.png");

        try {
            stageOneSheet = ImageIO.read(stageOneFile);   
        } 
        catch (IOException e) {
            found = false;
            System.out.println("not found");
        }

        if(found){
            setStageOne();
        }

    }

    private void initMap(){
        stage = new BufferedImage[2];
    }

    private void setStageOne(){
        stage[0] = stageOneSheet.getSubimage(13, 69, 208, 81);
        stage[1] = stageOneSheet.getSubimage(50, 247, 150, 43);
    }

    public BufferedImage draw(){
        return stage[0];
    }

    
}
