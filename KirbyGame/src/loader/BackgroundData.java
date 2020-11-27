/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class loads up the background image
 */

// Please don't remove the packages because I have many folders
package src.loader;

// import all needed packages 
import java.awt.image.*;
import javax.imageio.*;
import src.*;
import java.io.*;

// Create the class that will load the background up
public class BackgroundData {

  // Declare all variables needed
    private BufferedImage backgroundSheet;

    private RunScreen screen;

    private boolean found;


    // Constructor for this class 
    public BackgroundData(RunScreen screen){

        this.screen = screen;
        
        // Load the image up
        loadImage();
    }
    
    //method to load the image into the class
    private void loadImage(){

        // default is found
        found = true;
        
        // set the sheet to null first
        backgroundSheet = null;
        
        // Create the pointer to the file
        File stageOneFile = new File("src/res/background.png");

        // put int try an catch block because it could throw an exception
        try {
            backgroundSheet = ImageIO.read(stageOneFile);   // read the image and load it up into the sheet
        } 
        catch (IOException e) { // if throw exception then found is false and print out not found
            found = false;
            System.out.println("not found");
        }

    }

    //method for getting the background image
    public BufferedImage draw(){
        return backgroundSheet;
    }

    
}