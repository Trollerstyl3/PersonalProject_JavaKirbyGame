

/**
 * GamePanel
 */
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.ArrayList;

public class RunScreen extends JPanel implements ActionListener, KeyListener{

    /**
     *
     */
    Timer timer;
    CollisionChecker collision;
    private double deltaTime, firstTime, lastTime;
    private Player player;
    AI ai;
    Enemy enemy;

    ArrayList<Walls> walls;
    

    public RunScreen(){

        collision = new CollisionChecker();
        player = new Player(300,500,this);
        enemy = new Enemy(600, 500, this);
        ai = new AI();
        
        makePlatform();

        timer = new Timer(17,this);
        setFocusable(true);
    }

    public void makePlatform(){
        walls = new ArrayList<>();
        for(int i = 50; i < 650 ; i+=50){
            walls.add(new Walls(i,500));
        }
    }


    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        try{
        player.draw(g2d);
        enemy.draw(g2d);
        for(Walls i : walls){
            i.draw(g2d);
            
        }
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
} 