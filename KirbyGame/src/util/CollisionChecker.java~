package src.util;

/**
 * TestIntersect
 */
import java.awt.geom.*; 
import java.awt.*;

public class CollisionChecker {

    public boolean check(Shape shapeA, Shape shapeB){
        Area areaA = new Area(shapeA);
        areaA.intersect(new Area(shapeB)); // Compares if it is intersected
        return !areaA.isEmpty(); // Return true if the area is not empty
    }

}