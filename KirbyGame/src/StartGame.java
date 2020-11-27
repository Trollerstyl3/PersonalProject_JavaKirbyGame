/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This is the class that contain the main method that starts the whole program off
 * it creates the new window object and starts the timer
 */

// Please don't remove the packages because I have many folders
package src;


public class StartGame {
  
  // Main method where I create the new main window
    public static void main(String[] args) {
      
      // Create new Main Window and starts the timer
        MainWindow prog = new MainWindow();
        prog.start();
    }
}