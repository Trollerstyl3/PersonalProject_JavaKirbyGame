/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class create a JFrame where we will add the JPanel to and run the whole program
 */

// Please don't remove the packages because I have many folders
package src;

// Import all needed packages
import javax.swing.*;
import java.awt.*;

// Create MainWindow Class that will extend the JFram
public class MainWindow extends JFrame {
    
  // Set up all variable needed
    private RunScreen screen;
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 700;

    // when call start method then start the screen to its correct settings
    public void start(){
      
      // Create the JPanel via RunScreen where the game will run on
        screen = new RunScreen();
        screen.setLocation(0,0); // Set it to the top right 
        screen.setSize(this.getSize()); // set size of JPanel equal to the size of the JFrame
        screen.setVisible(true); // Set it to be seeable
        screen.addKeyListener(screen); // the listener for the screen is itself
        this.add(screen); // add the screen to the panel 
        screen.timer.start(); // start the timer and run the program

        // set the size of the jframe
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When close it will exit everything 
        setTitle("Kirbys Fight"); // Set the title to Kirbys Fight
        pack();           // pack all the components in the JFrame
        setVisible(true); // show it
        setResizable(false); // Make it so the size cannot be changed
        setLocationRelativeTo(null); // Set it in the middle of the screen when opening
    }

}
