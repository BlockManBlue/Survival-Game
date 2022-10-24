import java.awt.*;
import java.util.Random;
public class Entity {
	
	public Point position;
	public int id;
	Random rand = new Random();
	public int health;
	public int itemID;
	public int maxHP;

	public Entity(Point pos, int id, int hp, int itemID) {
		position = pos;
		this.id = id;
		health = hp;
		this.itemID = itemID;
		maxHP = hp;
	}

	public Entity(Point pos, int id, int hp) {
		position = pos;
		this.id = id;
		health = hp;
		maxHP = hp;
	}
		
	public void move(Point p) {
		position.x += p.x;
		position.y += p.y;
	}

	public double getDistance(Point p){
        int distX, distY;
        distX = p.x - position.x;
        distY = p.y - position.y;
        double totalDist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        return totalDist;
    }


}
