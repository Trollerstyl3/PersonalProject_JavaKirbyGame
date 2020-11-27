/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class helps check for collision between two objects
 */

// Please don't remove the packages because I have many folders
package src.util;

// import all needed packages
import java.awt.geom.*; 
import java.awt.*;

// Create the collision checker object
public class CollisionChecker {

  // the check method passes in two shape take the area of the two and compares if it intersects if it is return true
    public boolean check(Shape shapeA, Shape shapeB){
        Area areaA = new Area(shapeA);
        areaA.intersect(new Area(shapeB)); // Compares if it is intersected
        return !areaA.isEmpty(); // Return true if the area is not empty
    }

}