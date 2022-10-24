import java.awt.*;
public class Player extends Entity {

	public int[] inventory = new int[6];
	public int[] amounts = new int[6];

	public int armor = ID.NOTHING;
	public int tool = ID.NOTHING;
	public int weapon = ID.NOTHING;

	public int defense = 0;
	public int minePower = 1;
	public int attack = 1;

	public Color color;

	public int hunger = 100;

	public Player(Point pos, Color c) {
		super(pos, ID.PLAYER, 100);
		color = c;
	}

	public Player(Point pos, Color c, int[] inventory, int[] amount, int hp, int h){
		super(pos, ID.PLAYER, hp);
		color = c;
		this.inventory = inventory;
		amounts = amount;
		hunger = h;
	}

}
