/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This interface is responsible for objects in the game
 */

// Please don't remove the packages because I have many folders
package src.objects.interfaces;

// import classes needed
import java.awt.*;

// create the interface that will contain two method which is the draw method and the getHitBox
public interface ObjectInterface{
    public void draw (Graphics2D g2d);

    public Shape getHitBox();
}
