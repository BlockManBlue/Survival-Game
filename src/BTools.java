import java.util.*;
import java.awt.*;
public class BTools { // general class for general tools that i use a lot

    public static int getDistance(Point p1, Point p2){ // gets the distance between p1 and p2
        int distX = Math.abs(p2.x - p1.x);
        int distY = Math.abs(p2.y - p1.y);

        // a^2 + b^2 = c^2
        // c = sqrt(a^2 + b^2)
        int dist = (int)(Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)));

        return dist;
    }

    public static double[] getDirection(Point p1, Point p2){
        p1 = new Point(p1.x, p1.y);
        p2 = new Point(p2.x, p2.y); // make copies to not mess up things
        double[] result = new double[2];
        p2.x -= p1.x;
        p2.y -= p1.y;
        int absX, absY;
        absX = Math.abs(p2.x);
        absY = Math.abs(p2.y);
        int total = absX + absY;
        if(total == 0){
            result[0] = 0;
            result[1] = 0;
            return result;
        }
        double x = (double)absX / total;
        double y = (double)absY / total;
        if(p2.x < 0) x *= -1;
        if(p2.y < 0) y *= -1;
        result[0] = x;
        result[1] = y;
        return result;
    }

    public static int randInt(int min, int max){
        if(max < min){ // min is bigger, swap
            int temp = max;
            max = min;
            min = temp;
        }
        if(max == min) return max; // same number, just return the number
        Random rand = new Random();
        return rand.nextInt(max - min) + min; // generate number
    }

    public static double randDouble(double min, double max){
        if(max < min){ // min is bigger, swap
            double temp = max;
            max = min;
            min = temp;
        }
        if(min == max) return min;
        int minInt = 0;
        if(min >= 1){
            minInt = (int)min;
        }
        int maxInt = 0;
        if(max >= 1){
            maxInt = (int)max;
        }
        int randInt = 0;
        if(minInt > 0){
            // get random for int part
            randInt = randInt(minInt, maxInt);

            // make min and max not have int part
            min -= minInt;
            max -= maxInt;
        }
        double randDecimal = 0;
        Random rand = new Random();
        do{
            randDecimal = rand.nextDouble();
        }while(randDecimal < min || randDecimal > max);
        return randInt + randDecimal;
    }

    public static boolean collides(Rectangle r1, Rectangle r2){
        return r1.intersects(r2);
    }

    public static boolean rectContains(Rectangle r, Point p){
        return r.contains(p);
    }

    public static int clamp(int a, int min, int max){
        a = Math.min(a, max);
        a = Math.max(a, min);
        return a;
    }
}
