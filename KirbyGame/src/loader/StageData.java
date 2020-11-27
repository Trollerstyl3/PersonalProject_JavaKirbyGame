/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class loads up the BufferedImage of the stages and its components.
 * This makes the code for the other objects not to be too messy, as it will be a seperate class 
 * that will give the bufferedimmage when needed
 */

// Please don't remove the packages because I have many folders
package src.loader;

// Import all packages needed
import java.awt.image.*;
import javax.imageio.*;
import src.*;
import java.io.*;

// Create class StageData
public class StageData {

    // Declare all needed variables which include a sprite sheet then two buffered image array to keep track
    // of animation and to get the sprite, imgCount and found is used to load up the image and animate it
    private BufferedImage stageOneSheet;
    private BufferedImage[] stage;
    private RunScreen screen; // Private to protect variables 
    private int imgCount;
    private boolean found;
    private BufferedImage[] currentImageArray;

    // Constuctor of StageData will pass in the RunScreen just in case it needs to use it
    public StageData(RunScreen screen){
      
        // set screen to the screen that was passed in
        this.screen = screen;
        
        // Set the count of the loop to animate the stage to 0
        imgCount = 0;
        
        // Load up the images
        loadImage();
    }

    private void loadImage(){
      
        // Initialize the array for the stage image
        initMap();

        // set found to true first then test it out
        found = true;

        // Set the BufferedImage for th sheet to null so it can be added later
        stageOneSheet = null;

        // Create new file for the address of the sprite sheet
        File stageOneFile = new File("src/res/stage_1.png");

        // Putting it in a try and catch because could throw exception if not found
        try {
            //Load the sprite sheet into the buffered image 
            stageOneSheet = ImageIO.read(stageOneFile);   
        } 
        catch (IOException e) {
          
            // If throw an IOException then set found to false and then printt not found 
            found = false;
            System.out.println("not found");
        }

        if(found){
            // if the file was found the set it up
            setStageOne();
        }

    }

    private void initMap(){
        // Only need 2 sprite from the sheet so set it to 2 buffered image
        stage = new BufferedImage[2];
    }

    private void setStageOne(){
        
        // get the smaller sprite in the sprite sheet 
        stage[0] = stageOneSheet.getSubimage(13, 69, 208, 81);
        stage[1] = stageOneSheet.getSubimage(50, 247, 150, 43);
    }

    public BufferedImage draw(){
      
        // if draw is called will return the stage buffered image
        return stage[0];
    }

    
}
