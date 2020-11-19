package src;


/**
 * GamePanel
 */
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.*;
import src.objects.*;
import src.objects.enums.CharacterState;
import src.util.*;

public class RunScreen extends JPanel implements ActionListener, KeyListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    Timer timer;
    CollisionChecker collision;
    private double deltaTime, firstTime, lastTime;
    private Player player;
    AI ai;
    Enemy enemy;
    Background background;

    public Stage stage;
    

    public RunScreen(){

        makePlatform();
        collision = new CollisionChecker();
        player = new Player(300,400,this);
        enemy = new Enemy(600, 400, this);
        background = new Background(0,0,this);
        ai = new AI();
        

        timer = new Timer(17,this);
        setFocusable(true);
    }

    public void makePlatform(){
        stage = new Stage(0,400,this);
    }


    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        try{
        background.draw(g2d);
        stage.draw(g2d);
        enemy.draw(g2d);
        player.draw(g2d);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            firstTime = System.currentTimeMillis();
            deltaTime = (firstTime - lastTime)/1000d;
            player.move();
            ai.movement(enemy, player);
            enemy.move();
            checkHit(player, enemy);
            repaint();
            lastTime = firstTime;
        }
        requestFocus();
    }

    public double getDeltaTime() {
        return deltaTime;
    }


    @Override
    public void keyTyped(KeyEvent evt) {

    }

    @Override
    public void keyPressed(KeyEvent evt) {

        
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_A: player.moveLeft = true;
                    break;
            case KeyEvent.VK_D:  player.moveRight = true;
                    break;
            case KeyEvent.VK_W:  player.moveUp = true;
                    break;
            case KeyEvent.VK_S:  player.moveDown = true;
                    break;
            case KeyEvent.VK_T:  player.attack = true;
                    break;
            case KeyEvent.VK_Y:  player.charge = true;
                    break;
            case KeyEvent.VK_U:  player.taunt = true;
                    break;
                }

        
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_A: player.moveLeft = false;
                    break;
            case KeyEvent.VK_D:  player.moveRight = false;
                    break;
            case KeyEvent.VK_W:  player.moveUp = false;
                    break;
            case KeyEvent.VK_S:  player.moveDown = false;
                    break;
            case KeyEvent.VK_T:  player.attack = false;
                    break;
             case KeyEvent.VK_Y:  player.charge = false;
                    break;
             case KeyEvent.VK_U:  player.taunt = false;
                    break;
        }
        
    }

    public void checkHit(Player player, Enemy enemy){
        player.getHit = false; 
        enemy.getHit = false;
        if(player.getStates().contains(CharacterState.ATTACKING) || (player.getStates().contains(CharacterState.SLAM) && !player.getStates().contains(CharacterState.GROUNDED))){
            if(collision.check(player.getHitBox(), enemy.getHitBox())){
                enemy.getHit = true;
                enemy.damageReceived = player.getDamage();
            }
        }
        if(enemy.getStates().contains(CharacterState.ATTACKING) || (enemy.getStates().contains(CharacterState.SLAM) && !enemy.getStates().contains(CharacterState.GROUNDED))){
            if(collision.check(player.getHitBox(), enemy.getHitBox())){
                player.getHit = true;
                player.damageReceived = enemy.getDamage();
            }
        }
    }
} 