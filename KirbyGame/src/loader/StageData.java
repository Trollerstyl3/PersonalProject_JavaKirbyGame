package src.loader;

import java.awt.image.*;
import javax.imageio.*;
import src.*;
import java.io.*;

public class StageData {

    BufferedImage stageOneSheet, stageTwoSheet, stageThreeSheet;

    BufferedImage[][] stage;

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
        stage  = new BufferedImage [3][];
        initMap();

        
        found = true;

        stageOneSheet = null;

        File stageOneFile = new File("src/res/stage_1.png");
        File stageTwoFile = new File("src/res/stage_2.png");
        File stageThreeFile = new File("src/res/stage_3.png");

        try {
            stageOneSheet = ImageIO.read(stageOneFile);   
            stageTwoSheet = ImageIO.read(stageTwoFile);   
            stageThreeSheet = ImageIO.read(stageThreeFile);   
        } 
        catch (IOException e) {
            found = false;
            System.out.println("not found");
        }

        if(found){
            setStageOne();
            setStageTwo();
            setStageThree();
        }

    }

    private void initMap(){
        stage[0] = new BufferedImage[2];
        stage[1] = new BufferedImage[7];
        stage[2] = new BufferedImage[7];
    }

    private void setStageOne(){
        stage[0][0] = stageOneSheet.getSubimage(13, 69, 208, 81);
        stage[0][1] = stageOneSheet.getSubimage(50, 247, 150, 43);
    }

    private void setStageTwo(){

        stage[1][0] = stageOneSheet.getSubimage(1, 1, 460, 171);
        stage[1][1] = stageOneSheet.getSubimage(463, 1, 460, 171);
        stage[1][2] = stageOneSheet.getSubimage(925, 1, 460, 171);
        stage[1][3] = stageOneSheet.getSubimage(1387, 1, 460, 171);
        stage[1][4] = stageOneSheet.getSubimage(1849, 1, 460, 171);
        stage[1][5] = stageOneSheet.getSubimage(2311, 1, 460, 171);
        stage[1][6] = stageOneSheet.getSubimage(2773, 1, 460, 171);
    }

    private void setStageThree(){

        stage[2][0] = stageOneSheet.getSubimage(4, 2, 450, 234);
        stage[2][1] = stageOneSheet.getSubimage(460, 2, 450, 234);
        stage[2][2] = stageOneSheet.getSubimage(916, 2, 450, 234);
        stage[2][3] = stageOneSheet.getSubimage(1372, 2, 450, 234);
        stage[2][4] = stageOneSheet.getSubimage(1828, 2, 450, 234);
        stage[2][5] = stageOneSheet.getSubimage(2284, 2, 450, 234);
        stage[2][6] = stageOneSheet.getSubimage(2740, 2, 450, 234);
    }
}
