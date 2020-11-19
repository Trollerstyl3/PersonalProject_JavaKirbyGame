package src;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private RunScreen screen;
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 700;


    public void start(){
        
        screen = new RunScreen();
        screen.setLocation(0,0);
        screen.setSize(this.getSize());
        screen.setVisible(true);
        screen.addKeyListener(screen);
        this.add(screen);
        screen.timer.start();

        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)(screenSize.getWidth()/2 - WINDOW_WIDTH/2), (int)(screenSize.getHeight()/2 - WINDOW_HEIGHT/2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // Handle the CLOSE button
        setTitle("Kirbys Fight");
        pack();           // pack all the components in the JFrame
        setVisible(true); // show it
        setResizable(false);
    }

}
