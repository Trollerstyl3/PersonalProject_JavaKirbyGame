/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This GamePanel class creates the JPanel that will be added to the MainWindow and is also 
 * the ActionListener and Keylistener. It will also be in charge of the collision of character and the soundManager object
 */

// Please don't remove the packages because I have many folders
package src;

// Import all neededs package
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.*;
import src.objects.*;
import src.objects.Stage;
import src.objects.enums.CharacterState;
import src.util.*;
import src.gamestates.enums.GameStates;
import src.gamestates.Pause;
import src.gamestates.Menu;
import src.gamestates.EndPage;
import src.loader.SoundManager;

// create the class that will extend JPanel and implement both the actionlistener and keylistener
public class RunScreen extends JPanel implements ActionListener, KeyListener{

  
   
    // Create all Objects needed to added to the screen
    public Timer timer;
    private CollisionChecker collision;
    private double deltaTime, firstTime, lastTime;
    private Player player;
    private GameStates currentGameState;
    private AI ai;
    private Enemy enemy;
    private Background background;
    private Pause pause;
    private Menu menu;
    private EndPage endPage;
    public Stage stage; // These two object is public because other classes will use it
    public SoundManager soundManager ;
    
    // Constructor for the RunScreen method
    public RunScreen(){
      
      // Create the new SoundManager and start to play the background music and loop it until the program is closed
        soundManager = new SoundManager();
        soundManager.backgroundMusic.loop();
        
        // Create the stage, background, player, and enemy object and set them to their correct x and y
        stage = new Stage(0,400,this);
        player = new Player(100,400,this);
        enemy = new Enemy(600, 400, this);
        background = new Background(0,0,this);
        
        // Create utility objects to be used throughout the program
        collision = new CollisionChecker();
        ai = new AI(this);
        timer = new Timer(17,this);
        
        // Create the screens for pause, menu, and the endpage, also set up the first page they see to the game menu
        pause = new Pause();
        menu = new Menu();
        endPage = new EndPage();
        currentGameState = GameStates.GAMEMENU;
        
        // Indicate that the JPanel can be focused
        setFocusable(true);
    }

    // paint method that will run everytime repaint is called
    public void paint(Graphics g){
        super.paint(g); // since we extended a JPanel, we call the super method of paint to set up the Graphics

        // Cast Graphics to Graphics2D to be used
        Graphics2D g2d = (Graphics2D) g;
        
        // Use try and catch block because it could throw errors
        try{
            
          // Always draw the background first
            background.draw(g2d);
            
            // Depends on what game state we are in then change the screen to the correct one
            if(currentGameState == GameStates.GAMEMENU || currentGameState == GameStates.GAMEINSTRUCTION || currentGameState == GameStates.GAMEINSTRUCTION_2){
                menu.draw(g2d,currentGameState); // If it is in gamemenu, instruction then stay with menu.draw
            }else if(currentGameState == GameStates.GAMEWIN || currentGameState == GameStates.GAMELOSE){
                endPage.draw(g2d,currentGameState); // If win or lose then draw the endPage
            }else{
              // else draw the stage and player and enemy for the game to start
                stage.draw(g2d);
                enemy.draw(g2d);
                player.draw(g2d);
                if(currentGameState == GameStates.GAMEPAUSED) pause.draw(g2d,currentGameState); // If paused then paint this
            }
        }catch(Exception e){
            e.printStackTrace(); // print stack trace if throw error
        }
    }

    // Override the actionPerformed of the ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
      
      // Get the listened event, if it is the timer then run this
        if(e.getSource() == timer){
          // Keep track of the time taken to do all the task
            firstTime = System.currentTimeMillis();
            deltaTime = (firstTime - lastTime)/1000d;
            
            // If the game has started then allow for the movement of the players, and enemy and check hit and win
            if(currentGameState == GameStates.GAMESTARTED){
                player.move();
                ai.movement(enemy, player);
                enemy.move();
                checkHit(player, enemy);
                checkWin(player,enemy);
            }else if(currentGameState == GameStates.GAMEWIN || currentGameState == GameStates.GAMELOSE || currentGameState == GameStates.GAMEMENU){ // If it in any other state then reset the game except for pausd
                reset();
            }
            repaint(); // repaint the JPanel
            lastTime = firstTime; // keep track of time 
        }
        requestFocus(); // request the focus back to the JPanel
    }
   
    // This method gives anyone calling it the delta time that has passed
    public double getDeltaTime() {
        return deltaTime;
    }

    // Override the KeyTyped method of KeyListener but does not use it
    @Override
    public void keyTyped(KeyEvent evt) {    }

    

    // Override the KeyPressed method of KeyListener
    @Override
    public void keyPressed(KeyEvent evt) {

        // get the KeyEvent code and check it with the wanted case
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: // If press escape during game start then pause it, if it is pause then start the game again
                    if(currentGameState == GameStates.GAMESTARTED) currentGameState = GameStates.GAMEPAUSED;
                    else if(currentGameState == GameStates.GAMEPAUSED) currentGameState = GameStates.GAMESTARTED;
                    break; // need to break out if not it will run the next case's code 
            case KeyEvent.VK_I: // If press I during the game menu or instruction it will go to the next page 
                    if(currentGameState == GameStates.GAMEMENU) currentGameState = GameStates.GAMEINSTRUCTION;
                    else if(currentGameState == GameStates.GAMEINSTRUCTION) currentGameState = GameStates.GAMEINSTRUCTION_2;
                    else if(currentGameState == GameStates.GAMEINSTRUCTION_2) currentGameState = GameStates.GAMEMENU;
                    break;
            case KeyEvent.VK_ENTER: // If press enter during the game menu then start game, if during the game pause or game won or lose then go to main menu
                    if(currentGameState == GameStates.GAMEMENU) currentGameState = GameStates.GAMESTARTED;
                    else if(currentGameState == GameStates.GAMEPAUSED || currentGameState == GameStates.GAMEWIN ||  currentGameState == GameStates.GAMELOSE){
                        currentGameState = GameStates.GAMEMENU;
                    } 
                    break;
            case KeyEvent.VK_R: // If press R during the game won or lose screen then reset the game and start it again
                    if(currentGameState == GameStates.GAMEWIN ||  currentGameState == GameStates.GAMELOSE) currentGameState = GameStates.GAMESTARTED;
                    break;
            case KeyEvent.VK_Q: // If press Q during the game menu, paused, won or lose screen then quit the program
                    if(currentGameState == GameStates.GAMEMENU || currentGameState == GameStates.GAMEPAUSED || currentGameState == GameStates.GAMEWIN ||  currentGameState == GameStates.GAMELOSE){
                        System.exit(0);
                    } 
            case KeyEvent.VK_A:  player.moveLeft = true; // If press A then moveleft the player
                    break;
            case KeyEvent.VK_D:  player.moveRight = true; // If press D then moveRight
                    break;
            case KeyEvent.VK_W:  player.moveUp = true; // If press W then jump
                    break;
            case KeyEvent.VK_S:  player.moveDown = true; // If press S then slam
                    break;
            case KeyEvent.VK_T:  player.attack = true; // If press T then attack
                    break;
            case KeyEvent.VK_Y:  player.charge = true; // If press Y then dash
                    break;
            case KeyEvent.VK_U:  player.taunt = true; // If press U then taunt
                    break;
                }

        
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_A: player.moveLeft = false; // If release the key A then stop moving left
                    break;
            case KeyEvent.VK_D:  player.moveRight = false; // If release the key D then stop moving right
                    break;
            case KeyEvent.VK_W:  player.moveUp = false; // If release the key W then stop jumping
                    break;
            case KeyEvent.VK_S:  player.moveDown = false; // If release the key S then stop slamming
                    break;
            case KeyEvent.VK_T:  player.attack = false; // If release the key T then stop attacking
                    break;
             case KeyEvent.VK_Y:  player.charge = false; // If release the key Y then stop dashing
                    break;
             case KeyEvent.VK_U:  player.taunt = false; // If release the key U then stop taunting
                    break;
        }
        
    }

    // This method checks if the player or enemy have hit each other or not if they have then set the health of the the one getting hit to go down
    public void checkHit(Player player, Enemy enemy){
        
        // Default set it to false
        player.getHit = false; 
        enemy.getHit = false;
        
        // the enemy gets hit then the enemy play the hit animation and recieve the damage
        if(player.getStates().contains(CharacterState.ATTACKING) || (player.getStates().contains(CharacterState.SLAM) && !player.getStates().contains(CharacterState.GROUNDED))){
            if(collision.check(player.getHitBox(), enemy.getHitBox())){
                enemy.getHit = true;
                enemy.damageReceived = player.getDamage();
            }
        }
        
        // If the player gets hit then the player will play the hit animation and recieve the damage
        if(enemy.getStates().contains(CharacterState.ATTACKING) || (enemy.getStates().contains(CharacterState.SLAM) && !enemy.getStates().contains(CharacterState.GROUNDED))){
            if(collision.check(player.getHitBox(), enemy.getHitBox())){
                player.getHit = true;
                player.damageReceived = enemy.getDamage();
            }
        }
    }

    // This method checks if the player or the enemy have won and set the screen to the correct state  
    public void checkWin(Player player, Enemy enemy){
        if(player.getHealth() <= 0 ){
            currentGameState = GameStates.GAMELOSE;
        }else if (enemy.getHealth() <= 0){
            currentGameState = GameStates.GAMEWIN;
        }
    }

    // This method reset the game
    private void reset(){
        player.reset();
        enemy.reset();
    }
} 