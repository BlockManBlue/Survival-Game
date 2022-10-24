import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class PlayerPanel extends JPanel{
	
	Controls controls;
	Player player;
	boolean[] keyPressed = new boolean[6];
	final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, A = 4, B = 5;
	double speed = 0.5;
	int drawDist = 600;
	Server server;
	boolean invOpen = false;
	int invSel = 0;
	int menu = 0;
	int reach = 55;
	boolean itemSelected = false;
	boolean craftingMenu = false;
	boolean workbenchMenu = false;
	boolean cookMenu = false;
	boolean shopOpen = false;
	boolean shopSell = false;
	boolean wardOpen = false;
	int dimension = ID.OVERWORLD;

	// chest
	boolean chestOpen = false;
	Entity openedChest = null;
	ImageIcon chestMenu = new ImageIcon("data/images/chestMenu.png");

	// inventory images
	ImageIcon inventory = new ImageIcon("data/images/inventory.png");
	ImageIcon equipment = new ImageIcon("data/images/equipment.png");
	ImageIcon menuImage = new ImageIcon("data/images/menu.png");
	ImageIcon pointer = new ImageIcon("data/images/pointer.png");
	ImageIcon pointerSel = new ImageIcon("data/images/pointerSelected.png");
	ImageIcon itemToast = new ImageIcon("data/images/itemToast.png");
	ArrayList<Toast> itemToasts = new ArrayList<Toast>();
	final int maxToasts = 3;
	ImageIcon[] itemImages = {
		new ImageIcon(),
		new ImageIcon("data/images/items/wood.png"),
		new ImageIcon("data/images/items/stone.png"),
		new ImageIcon("data/images/items/food.png"),
		new ImageIcon("data/images/items/foodCooked.png"),
		new ImageIcon("data/images/items/furnace.png"),
		new ImageIcon("data/images/items/wardrobe.png"),
		new ImageIcon("data/images/items/floor.png"),
		new ImageIcon("data/images/items/wall.png"),
		new ImageIcon("data/images/items/ore.png"),
		new ImageIcon("data/images/items/steel.png"),
		new ImageIcon("data/images/items/mWall.png"),
		new ImageIcon("data/images/items/coin.png"),
		new ImageIcon("data/images/items/bed.png"),
		new ImageIcon("data/images/items/totem.png"),
		new ImageIcon("data/images/items/woodpick.png"),
		new ImageIcon("data/images/items/stonePick.png"),
		new ImageIcon("data/images/items/club.png"),
		new ImageIcon("data/images/items/steelPick.png"),
		new ImageIcon("data/images/items/steelArmor.png"),
		new ImageIcon("data/images/items/steelSword.png"),
		new ImageIcon("data/images/items/crystal.png"),
		new ImageIcon("data/images/items/mineLadder.png"),
		new ImageIcon("data/images/items/workbench.png"),
		new ImageIcon("data/images/items/wire.png"),
		new ImageIcon("data/images/items/battery.png"),
		new ImageIcon("data/images/items/drill.png"),
		new ImageIcon("data/images/items/circuitBoard.png"),
		new ImageIcon("data/images/items/autoDrill.png"),
		new ImageIcon("data/images/items/evaniteOre.png"),
		new ImageIcon("data/images/items/shrooms.png"),
		new ImageIcon("data/images/items/evanite.png"),
		new ImageIcon("data/images/items/evaniteSword.png"),
		new ImageIcon("data/images/items/evanitePick.png"),
		new ImageIcon("data/images/items/evaniteArmor.png"),
		new ImageIcon("data/images/items/chest.png"),
	};
	ImageIcon craft = new ImageIcon("data/images/crafting.png");
	ImageIcon cook = new ImageIcon("data/images/cooking.png");
	ImageIcon buy = new ImageIcon("data/images/buy.png");
	ImageIcon sell = new ImageIcon("data/images/sell.png");
	ImageIcon[] entityImages = {
		new ImageIcon(),
		new ImageIcon(),
		new ImageIcon("data/images/tree.png"),
		new ImageIcon("data/images/rock.png"),
		new ImageIcon("data/images/cow.png"),
		new ImageIcon(),
		new ImageIcon("data/images/craftingTable.png"),
		new ImageIcon("data/images/furnace.png"),
		new ImageIcon("data/images/wardrobe.png"),
		new ImageIcon("data/images/floor.png"),
		new ImageIcon("data/images/wall.png"),
		new ImageIcon("data/images/ore.png"),
		new ImageIcon("data/images/metalWall.png"),
		new ImageIcon("data/images/shop.png"),
		new ImageIcon("data/images/bed.png"),
		new ImageIcon("data/images/crystal.png"),
		new ImageIcon("data/images/workbench.png"),
		new ImageIcon("data/images/evaniteOre.png"),
		new ImageIcon("data/images/poisonShroom.png"),
		new ImageIcon("data/images/chest.png"),
	};
	ImageIcon wardrobe = new ImageIcon("data/images/wardrobeMenu.png");

	long hungerTimer = 0;
	long hungerTime = 3000;
	int hungerAmount = 1;

	long hurtFlash = 0;

	Color[] wardrobeSelections = {
		Color.blue,
		Color.orange,
		Color.pink,
		Color.cyan,
		Color.black,
		Color.white, 
		Color.red,
		Color.gray,
		Color.lightGray,
		Color.darkGray,
		Color.MAGENTA,
		Color.YELLOW,
		new Color(100, 40, 0)
	};
	int wardrobeSel;

	boolean deathSoundPlayed = false;

	ArrayList<SmokeEntity> smoke = new ArrayList<SmokeEntity>();
	Color[] smokeColors = {
		new Color(.3f, .3f, .3f, .8f), 
		new Color(.3f, .3f, .3f, .6f),
		new Color(.3f, .3f, .3f, .7f),
		new Color(.3f, .3f, .3f, .5f)
	};


	public PlayerPanel(Controls c, Player p, Server s) {
		controls = c;
		player = p;
		dimension = ID.OVERWORLD;
		if(player.position.x >= 5900 && player.position.y < 5100){
			// in mines
			dimension = ID.MINES;
		}
		if(player.position.x < 5100 && player.position.y < 5100){
			// in overworld
			dimension = ID.OVERWORLD;
		}
		if(player.position.x < 5100 && player.position.y >= 5900){
			// in deep mines
			dimension = ID.DEEPMINES;
		}
		setPreferredSize(new Dimension(512, 288));
		server = s;
		for(int i = 0; i < wardrobeSelections.length; i++){
			if(player.color.getRGB() == wardrobeSelections[i].getRGB()){
				wardrobeSel = i;
				break;
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(dimension == ID.OVERWORLD) {
			Server.bg.image.paintIcon(this, g, (((Server.bg.position.x) - player.position.x) + (getWidth() / 2)), (((Server.bg.position.y) - player.position.y) + (getHeight() / 2)));
			setBackground(new Color(0, 200, 255));
		}
		if(dimension == ID.MINES){
			Server.mines.image.paintIcon(this, g, (((Server.mines.position.x) - player.position.x) + (getWidth() / 2)), (((Server.mines.position.y) - player.position.y) + (getHeight() / 2)));
			setBackground(Color.darkGray);
		}
		if(dimension == ID.DEEPMINES){
			Server.deepMines.image.paintIcon(this, g, (((Server.deepMines.position.x) - player.position.x) + (getWidth() / 2)), (((Server.deepMines.position.y) - player.position.y) + (getHeight() / 2)));
			setBackground(Color.darkGray);
		}
		Entity[] entities = server.getEntities();
		for(Entity e: entities){
			if(player.getDistance(e.position) <= drawDist){
				if(e.id != ID.ITEM){
					if(e.id == ID.POISONSHROOM){
						g.setColor(new Color((float)67 / 255, 0f, (float)99 / 255, 0.7f));
						g.fillOval((((e.position.x) - player.position.x) + (getWidth() / 2)) - 100, (((e.position.y) - player.position.y) + (getHeight() / 2)) - 100, 200, 200);
					}
					entityImages[e.id].paintIcon(this, g, (((e.position.x) - player.position.x) + (getWidth() / 2)) - 25, (((e.position.y) - player.position.y) + (getHeight() / 2)) - 25);

					// check if smoke attached
					SmokeEntity smk = null;
					for(SmokeEntity s: smoke){
						if(s.parent.equals(e)){
							smk = s;
							break;
						}
					}
					if(smk != null){
						// smoke found, render
						if(e.id == ID.FURNACE) smk.setPos(new Point((((e.position.x) - player.position.x) + (getWidth() / 2)), (((e.position.y) - player.position.y) + (getHeight() / 2)) - 25));
						if(e.id == ID.SHOP) smk.setPos(new Point((((e.position.x) - player.position.x) + (getWidth() / 2)) + 18, (((e.position.y) - player.position.y) + (getHeight() / 2)) - 21));
						smk.render(g);
					}else{
						// no smoke found, check if needed
						if(e.id == ID.FURNACE){
							// smoke needed, add and render
							double[] windDir = {
								0.5,
								-1
							};
							SmokeEntity s = new SmokeEntity(new Point(), 30, smokeColors, false, 1000, 2500, 150, 0.1, windDir, 5, 12, e);
							smoke.add(s);
							s.setPos(new Point((((e.position.x) - player.position.x) + (getWidth() / 2)), (((e.position.y) - player.position.y) + (getHeight() / 2)) - 25));
							s.render(g);
						}
						if(e.id == ID.SHOP){
							// smoke needed, add and render
							double[] windDir = {
								0.5,
								-1
							};
							SmokeEntity s = new SmokeEntity(new Point(), 30, smokeColors, false, 1000, 2500, 150, 0.1, windDir, 4, 10, e);
							smoke.add(s);
							s.setPos(new Point((((e.position.x) - player.position.x) + (getWidth() / 2)) + 18, (((e.position.y) - player.position.y) + (getHeight() / 2)) - 21));
							s.render(g);
						}
					}
				}

				if(e.health < e.maxHP){ // mini health bar
					g.setColor(Color.black);
					g.fillRect((((e.position.x) - player.position.x) + (getWidth() / 2)) - 15, ((((e.position.y) - player.position.y) + (getHeight() / 2)) - 2) + 20, 30, 5);
					g.setColor(Color.red);
					g.fillRect((((e.position.x) - player.position.x) + (getWidth() / 2)) - 15, ((((e.position.y) - player.position.y) + (getHeight() / 2)) - 2) + 20, (int)(30 * ((double)e.health / e.maxHP)), 5);
				}

				if(e.id == ID.ITEM){
					itemImages[e.itemID].paintIcon(this, g, (((e.position.x) - player.position.x) + (getWidth() / 2)) - 10, (((e.position.y) - player.position.y) + (getHeight() / 2)) - 10);
				}
			}
		}

		// draw other players
		if(server.getPlayer(2) != null && server.getPlayer(3) == null){ // 2 players
			if(server.getPlayer(1).equals(this.player)){
				//player 2 is other player
				Player otherPlr = server.getPlayer(2);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}else{
				//player 1 is other player
				Player otherPlr = server.getPlayer(1);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
		}
		if(server.getPlayer(3) != null && server.getPlayer(4) == null){ // 3 players
			// render plr 1
			if(!server.getPlayer(1).equals(this.player)){
				Player otherPlr = server.getPlayer(1);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
			// render plr 2
			if(!server.getPlayer(2).equals(this.player)){
				Player otherPlr = server.getPlayer(2);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
			// render plr 3
			if(!server.getPlayer(3).equals(this.player)){
				Player otherPlr = server.getPlayer(3);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
		}
		if(server.getPlayer(4) != null){ // 4 players
			// render plr 1
			if(!server.getPlayer(1).equals(this.player)){
				Player otherPlr = server.getPlayer(1);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
			// render plr 2
			if(!server.getPlayer(2).equals(this.player)){
				Player otherPlr = server.getPlayer(2);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
			// render plr 3
			if(!server.getPlayer(3).equals(this.player)){
				Player otherPlr = server.getPlayer(3);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
			// render plr 4
			if(!server.getPlayer(4).equals(this.player)){
				Player otherPlr = server.getPlayer(4);
				g.setColor(otherPlr.color);
				if(otherPlr.health <= 0) g.setColor(new Color(otherPlr.color.getRed() / 255.0f, otherPlr.color.getGreen() / 255.0f, otherPlr.color.getBlue() / 255.0f, 0.6f));
				g.fillRect((((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 12, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)) - 12, 25, 25);
				if(otherPlr.health > 0) itemImages[otherPlr.tool].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)), (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
				if(otherPlr.health > 0) itemImages[otherPlr.weapon].paintIcon(this, g, (((otherPlr.position.x) - player.position.x) + (getWidth() / 2)) - 15, (((otherPlr.position.y) - player.position.y) + (getHeight() / 2)));
			}
		}
		// reach circle
		g.setColor(new Color(0.75f, 0.75f, 0.75f, 0.15f));
		g.fillOval((getWidth() / 2) - reach, (getHeight() / 2) - reach, reach * 2, reach * 2);
		// draw this player
		// dimensions: 25x25
		g.setColor(player.color);
		if(player.health <= 0) g.setColor(new Color(player.color.getRed() / 255.0f, player.color.getGreen() / 255.0f, player.color.getBlue() / 255.0f, 0.6f));
		g.fillRect((getWidth() / 2) - 12, (getHeight() / 2) - 12, 25, 25);
		if(player.health > 0) itemImages[player.tool].paintIcon(this, g, getWidth() / 2, getHeight() / 2);
		if(player.health > 0) itemImages[player.weapon].paintIcon(this, g, getWidth() / 2 - 15, getHeight() / 2);

		// darkness
		if(dimension != ID.DEEPMINES){
			g.setColor(new Color(0, 0, 0, (float)server.getDarkness()));
			g.fillRect(-1, -1, 520, 300);
		}else{
			g.setColor(new Color(0, 0, 0, 0.8f));
			g.fillRect(-1, -1, 520, 300);
		}
		

		// HUD
		//clock
		g.setColor(new Color(130, 55, 0));
		g.fillRect(getWidth() - 50, 5, 45, 15);
		g.setColor(new Color(0, 150, 255));
		g.fillRect(getWidth() - 48, 7, 41, 11);
		g.setColor(Color.black);
		g.fillRect((int)(getWidth() - 50 + (40 * ((double)server.time / server.maxTime))), 5, 3, 15);
		// coords
		if(server.currentSave.gameRules.showCoords){
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.setColor(Color.black);
			Point shownPos = new Point(player.position.x, player.position.y);
			if(dimension == ID.MINES){
				shownPos.x -= 6000;
			}
			if(dimension == ID.DEEPMINES){
				shownPos.y -= 6000;
			}
			g.drawString("Position: (" + shownPos.x + ", " + shownPos.y + ")", 0, 20);
		}

		// inventory
		if(invOpen){
			if(menu == 0){
				inventory.paintIcon(this, g, 0, 0);
			}
			if(menu == 1){
				equipment.paintIcon(this, g, 0, 0);
			}
			if(menu == 2){
				menuImage.paintIcon(this, g, 0, 0);
			}
			if(menu == 0){
				ImageIcon p;
				if(itemSelected) p = pointerSel;
				else p = pointer;
				switch(invSel){
				case 0:
					p.paintIcon(this, g, 33, 63);
					break;
				case 1:
					p.paintIcon(this, g, 33, 122);
					break;
				case 2:
					p.paintIcon(this, g, 33, 181);
					break;
				case 3:
					p.paintIcon(this, g, 118, 63);
					break;
				case 4:
					p.paintIcon(this, g, 118, 122);
					break;
				case 5:
					p.paintIcon(this, g, 118, 181);
					break;
					
				}
				// item 0
				if(player.inventory[0] != 0){
					ImageIcon i = new ImageIcon(itemImages[player.inventory[0]].getDescription());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 70, 63);
					g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
					g.setColor(Color.black);
					g.drawString("" + player.amounts[0], 105, 100);
				}
				// item 1
				if(player.inventory[1] != 0){
					ImageIcon i = new ImageIcon(itemImages[player.inventory[1]].getDescription());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 70, 122);
					g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
					g.setColor(Color.black);
					g.drawString("" + player.amounts[1], 105, 122 + 37);
				}
				// item 2
				if(player.inventory[2] != 0){
					ImageIcon i = new ImageIcon(itemImages[player.inventory[2]].getDescription());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 70, 181);
					g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
					g.setColor(Color.black);
					g.drawString("" + player.amounts[2], 105, 181 + 37);
				}
				// item 3
				if(player.inventory[3] != 0){
					ImageIcon i = new ImageIcon(itemImages[player.inventory[3]].getDescription());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 155, 63);
					g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
					g.setColor(Color.black);
					g.drawString("" + player.amounts[3], 190, 100);
				}
				// item 4
				if(player.inventory[4] != 0){
					ImageIcon i = new ImageIcon(itemImages[player.inventory[4]].getDescription());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 155, 122);
					g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
					g.setColor(Color.black);
					g.drawString("" + player.amounts[4], 190, 122 + 37);
				}
				// item 5
				if(player.inventory[5] != 0){
					ImageIcon i = new ImageIcon(itemImages[player.inventory[5]].getDescription());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 155, 181);
					g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
					g.setColor(Color.black);
					g.drawString("" + player.amounts[5], 190, 181 + 37);
				}

				if(itemSelected){
					g.setColor(new Color(0f, 0f, 0f, 0.5f));
					g.fillRect(23, 30, 262, 234);
					ImageIcon i = new ImageIcon(itemImages[player.inventory[invSel]].getDescription());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 307, 79);
					g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
					g.setColor(Color.black);
					g.drawString("" + player.amounts[invSel], 303, 131);
					// draw name
					g.setFont(new Font("Arial", Font.ITALIC, 20));
					g.setColor(Color.black);
					g.drawString(ID.itemNames[player.inventory[invSel]], 344, 115);

				}else{
					g.setColor(new Color(0f, 0f, 0f, 0.5f));
					g.fillRect(291, 30, 197, 234);
				}
			}
			if(menu == 1){
				// player preview
				g.setColor(player.color);
				g.fillRect((getWidth() / 2) - 12, (getHeight() / 2) - 12, 25, 25);
				itemImages[player.tool].paintIcon(this, g, getWidth() / 2, getHeight() / 2);
				itemImages[player.weapon].paintIcon(this, g, getWidth() / 2 - 15, getHeight() / 2);

				// armor slot
				if(player.armor != ID.NOTHING){
					ImageIcon i = new ImageIcon(itemImages[player.armor].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 172, 76);
				}
				
				// weapon slot
				if(player.weapon != ID.NOTHING){
					ImageIcon i2 = new ImageIcon(itemImages[player.weapon].getImage());
					i2.setImage(i2.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i2.paintIcon(this, g, 264, 201);
				}
				// tool slot
				if(player.tool != ID.NOTHING){
					ImageIcon i3 = new ImageIcon(itemImages[player.tool].getImage());
					i3.setImage(i3.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i3.paintIcon(this, g, 314, 148);
				}

				// stats
				g.setFont(new Font("Arial", Font.ITALIC, 12));
				g.setColor(Color.black);

				g.drawString("" + player.defense, 403, 125);
				g.drawString("" + player.attack, 403, 145);
				g.drawString("" + player.minePower, 411, 165);
			}
			if(menu == 2){
				switch(invSel){
				case 0:
					pointer.paintIcon(this, g, 43, 49);
					break;
				case 1:
					pointer.paintIcon(this, g, 43, 105);
					break;
				case 2:
					pointer.paintIcon(this, g, 42, 160);
					break;
				}

				
			}
		}
	
		// crafting menu
		if(craftingMenu){
			// >>> ITEMS <<<
			craft.paintIcon(this, g, 0, 0);
			pointer.paintIcon(this, g, 20, 20 * invSel + 46);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			//furnace
			g.drawString("Furnace", 60, 71);
			//wardrobe
			g.drawString("Wardrobe", 60, 91);
			//floor
			g.drawString("Floor", 60, 111);
			//wall
			g.drawString("Wall", 60, 131);
			//metal wall
			g.drawString("Metal Wall", 60, 151);
			//wood pick
			g.drawString("Wood Pick", 60, 171);
			//stone pick
			g.drawString("Stone Pick", 60, 191);
			//club
			g.drawString("Club", 60, 211);

			//pick up
			g.drawString("Pick Up", 60, 231);

			if(invSel != 8){
				// >>> RECIPE INFO <<<
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 25));
				ImageIcon i;
				switch(invSel){
				case ID.CRAFTFURN:
					g.drawString("FURNACE", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("STONE x" + server.craftingRecipes[ID.CRAFTFURN].amounts[0], 306, 160);
					i = new ImageIcon(itemImages[ID.ITEMFURN].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.CRAFTWARD:
					g.drawString("WARDROBE", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("WOOD x" + server.craftingRecipes[ID.CRAFTWARD].amounts[0], 306, 160);
					i = new ImageIcon(itemImages[ID.ITEMWARD].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.CRAFTFLOOR:
					g.drawString("FLOOR", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("WOOD x" + server.craftingRecipes[ID.CRAFTFLOOR].amounts[0], 306, 160);
					i = new ImageIcon(itemImages[ID.ITEMFLOOR].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.CRAFTWALL:
					g.drawString("WALL", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("STONE x" + server.craftingRecipes[ID.CRAFTWALL].amounts[0], 306, 160);
					i = new ImageIcon(itemImages[ID.ITEMWALL].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.CRAFTMWALL:
					g.drawString("METAL WALL", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("STEEL x" + server.craftingRecipes[ID.CRAFTMWALL].amounts[0], 306, 160);
					i = new ImageIcon(itemImages[ID.ITEMMWALL].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.CRAFTWOODPICK:
					g.drawString("WOOD PICK", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("WOOD x" + server.craftingRecipes[ID.CRAFTWOODPICK].amounts[0], 306, 160);
					i = new ImageIcon(itemImages[ID.WOODPICK].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.CRAFTSTONEPICK:
					g.drawString("STONE PICK", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("WOOD x" + server.craftingRecipes[ID.CRAFTSTONEPICK].amounts[0], 306, 160);
					g.drawString("STONE x" + server.craftingRecipes[ID.CRAFTSTONEPICK].amounts[1], 306, 175);
					i = new ImageIcon(itemImages[ID.STONEPICK].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.CRAFTCLUB:
					g.drawString("CLUB", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("WOOD x" + server.craftingRecipes[ID.CRAFTCLUB].amounts[0], 306, 160);
					g.drawString("STONE x" + server.craftingRecipes[ID.CRAFTCLUB].amounts[1], 306, 175);
					i = new ImageIcon(itemImages[ID.CLUB].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				}
				boolean canCraft = true;
				for(int i3 = 0; i3 < server.craftingRecipes[invSel].items.length; i3++){
					boolean enough = false;
					for(int i2 = 0; i2<player.inventory.length; i2++){
						if(player.inventory[i2] == server.craftingRecipes[invSel].items[i3] && player.amounts[i2] >= server.craftingRecipes[invSel].amounts[i3]){
							enough = true;
							break;
						}
					}
					if(!enough){
						canCraft = false;
						break;
					}
				}
				if(!canCraft){
					g.setColor(new Color(1f, 0f, 0f, 0.75f));
					g.fillRect(380, 66, 33, 33);
				}
			}
		}

		// cook menu
		if(cookMenu){
			// >>> ITEMS <<<
			cook.paintIcon(this, g, 0, 0);
			pointer.paintIcon(this, g, 20, 20 * invSel + 46);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			//cooked food
			g.drawString("Cooked Food", 60, 71);
			//steel
			g.drawString("Steel", 60, 91);
			//steel pick
			g.drawString("Steel Pick", 60, 111);
			//steel armor
			g.drawString("Steel Armor", 60, 131);
			//steel sword
			g.drawString("Steel Sword", 60, 151);
			//workbench
			g.drawString("Engineer's Workbench", 60, 171);
			//evanite
			g.drawString("Evanite", 60, 191);

			//pick up
			g.drawString("Pick Up", 60, 211);
			

			if(invSel != 7){
				// >>> RECIPE INFO <<<
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 25));
				ImageIcon i;
				switch(invSel){
				case ID.COOKFOOD:
					g.drawString("COOKED FOOD", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("FOOD x" + server.furnaceRecipes[ID.COOKFOOD].amounts[0], 306, 160);
					g.drawString("WOOD x" + server.furnaceRecipes[ID.COOKFOOD].amounts[1], 306, 175);
					i = new ImageIcon(itemImages[ID.COOKED_FOOD].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.COOKMETAL:
					g.drawString("STEEL", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("ORE x" + server.furnaceRecipes[ID.COOKMETAL].amounts[0], 306, 160);
					g.drawString("WOOD x" + server.furnaceRecipes[ID.COOKMETAL].amounts[1], 306, 175);
					i = new ImageIcon(itemImages[ID.STEEL].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.COOKSTEELPICK:
					g.drawString("STEEL PICK", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("STEEL x" + server.furnaceRecipes[ID.COOKSTEELPICK].amounts[0], 306, 160);
					g.drawString("WOOD x" + server.furnaceRecipes[ID.COOKSTEELPICK].amounts[1], 306, 175);
					i = new ImageIcon(itemImages[ID.STEELPICK].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.COOKSTEELARMOR:
					g.drawString("STEEL ARMOR", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("STEEL x" + server.furnaceRecipes[ID.COOKSTEELARMOR].amounts[0], 306, 160);
					g.drawString("WOOD x" + server.furnaceRecipes[ID.COOKSTEELARMOR].amounts[1], 306, 175);
					i = new ImageIcon(itemImages[ID.STEELARMOR].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.COOKSTEELSWORD:
					g.drawString("STEEL SWORD", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("STEEL x" + server.furnaceRecipes[ID.COOKSTEELSWORD].amounts[0], 306, 160);
					g.drawString("WOOD x" + server.furnaceRecipes[ID.COOKSTEELSWORD].amounts[1], 306, 175);
					i = new ImageIcon(itemImages[ID.STEELSWORD].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.COOKWORKBENCH:
					g.drawString("WORKBENCH", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("WOOD x" + server.furnaceRecipes[ID.COOKWORKBENCH].amounts[0], 306, 160);
					g.drawString("STEEL x" + server.furnaceRecipes[ID.COOKWORKBENCH].amounts[1], 306, 175);
					g.drawString("CRYSTAL x" + server.furnaceRecipes[ID.COOKWORKBENCH].amounts[2], 306, 190);
					i = new ImageIcon(itemImages[ID.ITEMWB].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				case ID.COOKEVANITE:
					g.drawString("EVANITE", 304, 59);
					g.setFont(new Font("Arial", Font.ITALIC, 15));
					g.drawString("EVANITE ORE x" + server.furnaceRecipes[ID.COOKEVANITE].amounts[0], 306, 160);
					g.drawString("WOOD x" + server.furnaceRecipes[ID.COOKEVANITE].amounts[1], 306, 175);
					g.drawString("SHROOMS x" + server.furnaceRecipes[ID.COOKEVANITE].amounts[2], 306, 190);
					i = new ImageIcon(itemImages[ID.EVANITE].getImage());
					i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
					i.paintIcon(this, g, 380, 66);
					break;
				}
				boolean canCraft = true;
				for(int i3 = 0; i3 < server.furnaceRecipes[invSel].items.length; i3++){
					boolean enough = false;
					for(int i2 = 0; i2<player.inventory.length; i2++){
						if(player.inventory[i2] == server.furnaceRecipes[invSel].items[i3] && player.amounts[i2] >= server.furnaceRecipes[invSel].amounts[i3]){
							enough = true;
							break;
						}
					}
					if(!enough){
						canCraft = false;
						break;
					}
				}
				if(!canCraft){
					g.setColor(new Color(1f, 0f, 0f, 0.75f));
					g.fillRect(380, 66, 33, 33);
				}
			}
		}
	
		// shop menu
		if(shopOpen){
			
			if(!shopSell){
				// items to buy
				buy.paintIcon(this, g, 0, 0);
				pointer.paintIcon(this, g, 20, 20 * invSel + 46);
				g.setColor(Color.black);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				for(int i = 0; i < server.shopSelling.length; i++){ // show items from selling
					g.drawString(ID.itemNames[server.shopSelling[i]], 60, 20 * i + 71);

					if(invSel == i){
						ImageIcon i2 = new ImageIcon(itemImages[server.shopSelling[i]].getImage());
						i2.setImage(i2.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
						i2.paintIcon(this, g, 380, 66);
					}
				}
				// show item price
				g.setFont(new Font("Arial", Font.ITALIC, 15));
				g.drawString("COIN x" + server.prices[invSel], 306, 160);
				boolean canBuy = false;
				for(int i3 = 0; i3 < player.inventory.length; i3++){
					if(player.inventory[i3] == ID.COIN && player.amounts[i3] >= server.prices[invSel]){
						// can buy
						canBuy = true;
						break;
					}
				}
				if(!canBuy){
					g.setColor(new Color(1f, 0f, 0f, 0.75f));
					g.fillRect(380, 66, 33, 33);
				}
			}else{
				// items to sell
				sell.paintIcon(this, g, 0, 0);
				pointer.paintIcon(this, g, 20, 20 * invSel + 46);
				g.setColor(Color.black);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				for(int i = 0; i < player.inventory.length; i++){ // show items from inventory
					g.drawString(ID.itemNames[player.inventory[i]] + " x" + player.amounts[i], 60, 20 * i + 71);

					if(invSel == i && player.inventory[i] != ID.NOTHING){
						ImageIcon i2 = new ImageIcon(itemImages[player.inventory[i]].getImage());
						i2.setImage(i2.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
						i2.paintIcon(this, g, 380, 66);
					}
				}
				// show item price
				g.setFont(new Font("Arial", Font.ITALIC, 15));
				g.drawString("COIN x" + server.sellPrices[player.inventory[invSel]], 306, 160);
			}
		}
	
		// wardrobe menu
		if(wardOpen){
			wardrobe.paintIcon(this, g, 0, 0);
			g.setColor(player.color);
			g.fill3DRect(221, 119, 64, 64, true);
		}
		
		// workbench menu
		if(workbenchMenu){
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			// >>> ITEMS <<<
			craft.paintIcon(this, g, 0, 0);
			pointer.paintIcon(this, g, 20, 10 * invSel + 43);
			for(int i = 0; i < server.workbenchRecipes.length; i++){
				g.drawString(ID.itemNames[server.workbenchRecipes[i].output], 60, 65 + i * 10);
			}

			//pick up
			g.drawString("Pick Up", 60, 65 + server.workbenchRecipes.length * 10);

			if(invSel != server.workbenchRecipes.length){
				// >>> RECIPE INFO <<<
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				ImageIcon i;
				g.drawString(ID.itemNames[server.workbenchRecipes[invSel].output], 304, 59);
				g.setFont(new Font("Arial", Font.ITALIC, 15));
				for(int i2 = 0; i2 < server.workbenchRecipes[invSel].items.length; i2++){
					g.drawString(ID.itemNames[server.workbenchRecipes[invSel].items[i2]] + " x" + server.workbenchRecipes[invSel].amounts[i2], 306, 160 + i2 * 15);
				}
				i = new ImageIcon(itemImages[server.workbenchRecipes[invSel].output].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 380, 66);
				boolean canCraft = true;
				for(int i3 = 0; i3 < server.workbenchRecipes[invSel].items.length; i3++){
					boolean enough = false;
					for(int i2 = 0; i2<player.inventory.length; i2++){
						if(player.inventory[i2] == server.workbenchRecipes[invSel].items[i3] && player.amounts[i2] >= server.workbenchRecipes[invSel].amounts[i3]){
							enough = true;
							break;
						}
					}
					if(!enough){
						canCraft = false;
						break;
					}
				}
				if(!canCraft){
					g.setColor(new Color(1f, 0f, 0f, 0.75f));
					g.fillRect(380, 66, 33, 33);
				}
			}
		}
		
		// chest menu
		if(chestOpen){
			// inventory
			chestMenu.paintIcon(this, g, 0, 0);
			ImageIcon p;
			if(itemSelected) p = pointerSel;
			else p = pointer;
			switch(invSel){
			case 0:
				p.paintIcon(this, g, 33, 63);
				break;
			case 1:
				p.paintIcon(this, g, 33, 122);
				break;
			case 2:
				p.paintIcon(this, g, 33, 181);
				break;
			case 3:
				p.paintIcon(this, g, 118, 63);
				break;
			case 4:
				p.paintIcon(this, g, 118, 122);
				break;
			case 5:
				p.paintIcon(this, g, 118, 181);
				break;
				
			}
			// item 0
			if(player.inventory[0] != 0){
				ImageIcon i = new ImageIcon(itemImages[player.inventory[0]].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 70, 63);
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("" + player.amounts[0], 105, 100);
			}
			// item 1
			if(player.inventory[1] != 0){
				ImageIcon i = new ImageIcon(itemImages[player.inventory[1]].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 70, 122);
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("" + player.amounts[1], 105, 122 + 37);
			}
			// item 2
			if(player.inventory[2] != 0){
				ImageIcon i = new ImageIcon(itemImages[player.inventory[2]].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 70, 181);
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("" + player.amounts[2], 105, 181 + 37);
			}
			// item 3
			if(player.inventory[3] != 0){
				ImageIcon i = new ImageIcon(itemImages[player.inventory[3]].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 155, 63);
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("" + player.amounts[3], 190, 100);
			}
			// item 4
			if(player.inventory[4] != 0){
				ImageIcon i = new ImageIcon(itemImages[player.inventory[4]].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 155, 122);
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("" + player.amounts[4], 190, 122 + 37);
			}
			// item 5
			if(player.inventory[5] != 0){
				ImageIcon i = new ImageIcon(itemImages[player.inventory[5]].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 155, 181);
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("" + player.amounts[5], 190, 181 + 37);
			}

			// chest item
			if(openedChest.itemID == ID.NOTHING){
				// no item
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("x0/200", 303, 131);
				// draw name
				g.setFont(new Font("Arial", Font.ITALIC, 20));
				g.setColor(Color.black);
				g.drawString("EMPTY", 344, 115);
			}else{
				// item
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 15));
				g.setColor(Color.black);
				g.drawString("x" + openedChest.health + "/200", 303, 131);
				// draw name
				g.setFont(new Font("Arial", Font.ITALIC, 20));
				g.setColor(Color.black);
				g.drawString(ID.itemNames[openedChest.itemID], 344, 115);
				ImageIcon i = new ImageIcon(itemImages[openedChest.itemID].getImage());
				i.setImage(i.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				i.paintIcon(this, g, 307, 79);
			}
		}
		
		// item toasts
		if(itemToasts.size() > 0){
			for(int i = 0; i < itemToasts.size(); i++){
				itemToast.paintIcon(this, g, 362, 239 - (i * 41));
				ImageIcon img = new ImageIcon(itemImages[itemToasts.get(i).id].getDescription());
				img.setImage(img.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT));
				img.paintIcon(this, g, 366, 243 - (i * 41));
				g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 13));
				g.setColor(Color.black);
				g.drawString("x" + itemToasts.get(i).amount, 405, 259 - (i * 41));
				g.drawString(ID.itemNames[itemToasts.get(i).id], 405, 276 - (i * 41));
			}
		}
		
		//health and hunger bar
		g.setColor(new Color(127, 51, 0));
		g.fillRect(5, getHeight() - 50, 90, 20);
		g.fillRect(5, getHeight() - 25, 90, 20);// health and hunger bar backgrounds
		g.setColor(Color.black);
		g.fillRect(10, (getHeight() - 50) + 5, 80, 10);
		g.fillRect(10, (getHeight() - 25) + 5, 80, 10);// black things to go behind bar to represent maximum
		g.setColor(Color.red);
		g.fillRect(10, (getHeight() - 50) + 5, (int)(80 * (player.health / 100.0)), 10);// health bar
		g.setColor(new Color(100, 25, 0));
		g.fillRect(10, (getHeight() - 25) + 5, (int)(80 * (player.hunger / 100.0)), 10);// hunger bar
		
		// hurt flash
		g.setColor(new Color(1f, 0f, 0f, (float)(hurtFlash / 1750.0)));
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void update(long elapsedTime){
		if(itemToasts.size() > 0){
			for(int i = 0; i < itemToasts.size(); i++){
				Toast t = itemToasts.get(i);
				if(t != null){
					t.update(elapsedTime);
					if(t.life <= 0){
						itemToasts.remove(t);
					}
				}
			}
		}
		int xDir = 0, yDir = 0;
		if(!invOpen && !craftingMenu && !cookMenu && !shopOpen && !wardOpen && !workbenchMenu && !chestOpen){
			
			if(keyPressed[UP]) yDir--;
			if(keyPressed[DOWN]) yDir++;
			if(keyPressed[LEFT]) xDir--;
			if(keyPressed[RIGHT]) xDir++;
			player.move(new Point((int)(xDir * speed * elapsedTime), (int)(yDir * speed * elapsedTime)));
		}
		// location clamping
		if(dimension == ID.OVERWORLD){
			player.position.x = Server.clamp(player.position.x, 0, 5000);
			player.position.y = Server.clamp(player.position.y, 0, 5000);
		}
		if(dimension == ID.MINES){
			player.position.x = Server.clamp(player.position.x, 6000, 11000);
			player.position.y = Server.clamp(player.position.y, 0, 5000);
		}
		if(dimension == ID.DEEPMINES){
			player.position.x = Server.clamp(player.position.x, 0, 5000);
			player.position.y = Server.clamp(player.position.y, 6000, 11000);
		}
		for(Entity e: server.getEntities()){
			if(e.id == ID.WALL || e.id == ID.METALWALL){
				// check x
				if(colliding(e)){
					player.move(new Point(-(int)(xDir * speed * elapsedTime), 0));
				}
		
				// check y
				if(colliding(e)){
					player.move(new Point(0, -(int)(yDir * speed * elapsedTime)));
				}
			}
		}
		if(server.currentSave.gameRules.hunger) hungerTimer += elapsedTime;
		if(hungerTimer >= hungerTime){
			hungerTimer = 0;
			player.hunger -= hungerAmount;
			if(player.hunger <= 0){
				player.hunger = 0;
				damage(3);
			}
		}
		if(hurtFlash <= 0 && dimension == ID.DEEPMINES){
			// check for poison shrooms
			for(Entity e: server.entities){
				if(e.id == ID.POISONSHROOM && e.getDistance(player.position) < 100){
					damage(5);
					break;
				}
			}
		}
		if(player.health <= 0){
			player.health = 0;
			if(!deathSoundPlayed){
				try{
					Audio.main("data/sounds/die.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}

			// check for totem
			for(int i = 0; i < player.inventory.length; i++){
				if(player.inventory[i] == ID.REVIVALTOTEM && player.amounts[i] > 0){
					player.amounts[i]--;
					if(player.amounts[i] <= 0) player.inventory[i] = ID.NOTHING;
					player.health = 100;
					player.hunger = 100;
					invOpen = false;
					craftingMenu = false;
					cookMenu = false;
					shopOpen = false;
					return;
				}
			}
			
			for(int i = 0; i < player.inventory.length; i++){
				if(player.inventory[i] != ID.NOTHING && player.amounts[i] != 0){
					for(int i2 = 0; i2 < player.amounts[i]; i2++){
						server.addEntity(new Item(new Point(player.position.x, player.position.y), player.inventory[i]));
					}
				}
			}
			if(player.armor != ID.NOTHING) server.addEntity(new Item(new Point(player.position.x, player.position.y), player.armor));
			if(player.weapon != ID.NOTHING) server.addEntity(new Item(new Point(player.position.x, player.position.y), player.weapon));
			if(player.tool != ID.NOTHING) server.addEntity(new Item(new Point(player.position.x, player.position.y), player.tool));
			player.armor = ID.NOTHING;
			player.weapon = ID.NOTHING;
			player.tool = ID.NOTHING;
			player.defense = 0;
			player.attack = 1;
			player.minePower = 1;
			player.inventory = new int[6];
			player.amounts = new int[6];
			invOpen = false;
			craftingMenu = false;
			cookMenu = false;
			shopOpen = false;
			if(!server.currentSave.gameRules.hardcore){
				dimension = ID.OVERWORLD;
				player.position = new Point(Server.randNum(0, 5000), Server.randNum(0, 5000));
				player.health = 100;
				player.hunger = 100;
			}
			if(server.currentSave.gameRules.hardcore){
				deathSoundPlayed = true;
			}
			
		}
		for(int i = 0; i < player.amounts.length; i++){
			if(player.amounts[i] <= 0){
				player.amounts[i] = 0;
				player.inventory[i] = ID.NOTHING;
			}
		}
		player.color = wardrobeSelections[wardrobeSel];
		hurtFlash = Math.max(hurtFlash - elapsedTime, 0);
		repaint();
	}

	public boolean colliding(Entity obstacle){
        int xDist, yDist;
        xDist = (int)(player.position.getX() - obstacle.position.getX());
        yDist = (int)(player.position.getY() - obstacle.position.getY());
        xDist = Math.abs(xDist);
        yDist = Math.abs(yDist);
        return xDist <= 12 + 25 && yDist <= 12 + 25;
    }
	
	public void updateControls(int key, boolean pressed) {
		if(key == controls.UP) keyPressed[UP] = pressed;
		if(key == controls.DOWN) keyPressed[DOWN] = pressed;
		if(key == controls.RIGHT) keyPressed[RIGHT] = pressed;
		if(key == controls.LEFT) keyPressed[LEFT] = pressed;
		if(player.health <= 0) return;
		if(key == controls.A){
			// A key code here
			if(!keyPressed[A] && pressed){
				if(!invOpen && !craftingMenu && !cookMenu && !shopOpen && !wardOpen && !workbenchMenu && !chestOpen){
					// inventory is closed, use tool

					// check for general entities
					for(Entity e: server.getEntities()){
						if(e == null) continue;
						if(e.id == ID.ITEM && player.getDistance(e.position) <= reach){
							addItem(e.itemID);
							e.health = 0;
							try{
								Audio.main("data/sounds/pickup.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							
							continue;
						}
						if(e.id == ID.CRAFTING_TABLE && player.getDistance(e.position) <= reach){
							craftingMenu = true;
							invSel = 0;
							try{
								Audio.main("data/sounds/openMenu.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							return;
						}
						if(e.id == ID.CHEST && player.getDistance(e.position) <= reach){
							chestOpen = true;
							openedChest = e;
							invSel = 0;
							try{
								Audio.main("data/sounds/openMenu.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							return;
						}
						if(e.id == ID.WORKBENCH && player.getDistance(e.position) <= reach){
							workbenchMenu = true;
							invSel = 0;
							try{
								Audio.main("data/sounds/openMenu.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							return;
						}
						if(e.id == ID.FURNACE && player.getDistance(e.position) <= reach){
							cookMenu = true;
							invSel = 0;
							try{
								Audio.main("data/sounds/openMenu.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							return;
						}
						if(e.id == ID.WARDROBE && player.getDistance(e.position) <= reach){
							wardOpen = true;
							try{
								Audio.main("data/sounds/openMenu.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							return;
						}
						if(e.id == ID.SHOP && player.getDistance(e.position) <= reach){
							shopOpen = true;
							invSel = 0;
							try{
								Audio.main("data/sounds/openMenu.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							return;
						}
						if(e.id == ID.BED && player.getDistance(e.position) <= reach){
							if(server.time >= server.maxTime / 3){
								server.time = 0;
								if(server.currentSave.gameRules.moveShop) server.shop.position = new Point(Server.randNum(0, 5000), Server.randNum(0, 5000));
								if(server.currentSave.gameRules.hunger) player.hunger -= 10;
							}
							try{
								Audio.main("data/sounds/sleep.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							return;
						}
						if(e.id != ID.ITEM && player.getDistance(e.position) <= reach){
							if(e.id == ID.COW || e.id == ID.POISONSHROOM) e.health -= player.attack;
							else e.health -= player.minePower;
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						
					}
					// check for players, if pvp
					if(server.getPlayer(2) != null && server.currentSave.gameRules.pvp && server.getPlayer(3) == null){ // 2 players
						if(server.getPlayer(1).equals(player)){
							// other player is player 2
							if(server.getPlayer(2).getDistance(player.position) <= reach) {
								server.playerPanels[1].damage(Math.max(player.attack - server.getPlayer(2).defense, 0));
								try{
									Audio.main("data/sounds/attack.wav");
								}catch(Exception ex){
									ex.printStackTrace();
									System.exit(1);
								}
							}
						}else{
							if(server.getPlayer(1).getDistance(player.position) <= reach) {
								server.playerPanels[0].damage(Math.max(player.attack - server.getPlayer(1).defense, 0));
								try{
									Audio.main("data/sounds/attack.wav");
								}catch(Exception ex){
									ex.printStackTrace();
									System.exit(1);
								}
							}
						}
					}
					if(server.getPlayer(3) != null && server.getPlayer(4) == null && server.currentSave.gameRules.pvp){ // 3 players
						// check 1
						if(!server.getPlayer(1).equals(player) && server.getPlayer(1).getDistance(player.position) <= reach){
							server.playerPanels[0].damage(Math.max(player.attack - server.getPlayer(1).defense, 0));
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						// 2
						if(!server.getPlayer(2).equals(player) && server.getPlayer(2).getDistance(player.position) <= reach){
							server.playerPanels[1].damage(Math.max(player.attack - server.getPlayer(2).defense, 0));
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						// 3
						if(!server.getPlayer(3).equals(player) && server.getPlayer(3).getDistance(player.position) <= reach){
							server.playerPanels[2].damage(Math.max(player.attack - server.getPlayer(3).defense, 0));
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
					}
					if(server.getPlayer(4) != null && server.currentSave.gameRules.pvp){ // 4 players
						// check 1
						if(!server.getPlayer(1).equals(player) && server.getPlayer(1).getDistance(player.position) <= reach){
							server.playerPanels[0].damage(Math.max(player.attack - server.getPlayer(1).defense, 0));
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						// 2
						if(!server.getPlayer(2).equals(player) && server.getPlayer(2).getDistance(player.position) <= reach){
							server.playerPanels[1].damage(Math.max(player.attack - server.getPlayer(2).defense, 0));
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						// 3
						if(!server.getPlayer(3).equals(player) && server.getPlayer(3).getDistance(player.position) <= reach){
							server.playerPanels[2].damage(Math.max(player.attack - server.getPlayer(3).defense, 0));
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						// 4
						if(!server.getPlayer(4).equals(player) && server.getPlayer(4).getDistance(player.position) <= reach){
							server.playerPanels[3].damage(Math.max(player.attack - server.getPlayer(4).defense, 0));
							try{
								Audio.main("data/sounds/attack.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
					}
				}
			}
			
			keyPressed[A] = pressed;
		}
		if(key == controls.B){
			// B key code here
			if(!keyPressed[B] && pressed){
				if(chestOpen){
					chestOpen = false;
					openedChest = null;
					try{
						Audio.main("data/sounds/closeMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				if(wardOpen){
					wardOpen = false;
					addItem(ID.ITEMWARD);
					// delete wardrobe
					for(Entity e: server.getEntities()){
						if(e == null) continue;
						if(e.id == ID.WARDROBE && e.getDistance(player.position) <= reach){
							e.health = 0;
							break;
						}
					}
					try{
						Audio.main("data/sounds/drop.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				if(shopOpen){
					shopOpen = false;
					try{
						Audio.main("data/sounds/closeMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				if(craftingMenu){
					craftingMenu = false;
					try{
						Audio.main("data/sounds/closeMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				if(workbenchMenu){
					workbenchMenu = false;
					try{
						Audio.main("data/sounds/closeMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				if(cookMenu){
					cookMenu = false;
					try{
						Audio.main("data/sounds/closeMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				if(itemSelected){
					itemSelected = false;
					try{
						Audio.main("data/sounds/closeMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				if(!invOpen){
					// inventory is closed, open
					invOpen = true;
					invSel = 0;
					menu = 0;
					try{
						Audio.main("data/sounds/openMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
				}else{
					// inventory is open, close
					invOpen = false;
					try{
						Audio.main("data/sounds/closeMenu.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
				}
			}
			
			keyPressed[B] = pressed;

		}
		if(invOpen && !itemSelected && pressed){
			if(key == controls.UP) {
				invSel--;
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.DOWN) {
				invSel++;
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.LEFT){
				if(invSel > 2 && menu == 0) invSel -= 3;
				else {
					menu--;
					if(menu < 0) menu = 2;
					invSel = 0;
				}
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.RIGHT){
				if(invSel < 3 && menu == 0) invSel += 3;
				else {
					menu++;
					if(menu > 2) menu = 0;
					invSel = 0;
				}
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(menu == 0){
				if(invSel > 5) invSel = 0;
				if(invSel < 0) invSel = 5;

				if(key == controls.A && player.inventory[invSel] != ID.NOTHING){
					itemSelected = true;
					try{
						Audio.main("data/sounds/invSelect.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}

			}
			if(menu == 1){
				invSel = 0;

				if(key == controls.UP && player.armor != ID.NOTHING){
					// Take off armor
					addItem(player.armor);
					player.armor = ID.NOTHING;

					// reset defence
					player.defense = 0;
				}
				if(key == controls.DOWN && player.weapon != ID.NOTHING){
					// Put away sword
					addItem(player.weapon);
					player.weapon = ID.NOTHING;

					// reset attack
					player.attack = 1;
				}
				if(key == controls.A && player.tool != ID.NOTHING){
					// Put away tool
					addItem(player.tool);
					player.tool = ID.NOTHING;

					// reset mine power
					player.minePower = 1;
				}
			}
			if(menu == 2){
				if(invSel > 2) invSel = 0;
				if(invSel < 0) invSel = 2;

				if(key == controls.A){
					switch(invSel){
					case 0:
						//save
						server.save();
						invOpen = false;
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						break;
					case 1:
						//save and quit
						server.save();
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						System.exit(0);
						break;
					case 2:
						//quit without save
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						System.exit(0);
						break;
					}
				}
			}
		}
		if(itemSelected && pressed){
			if(key == controls.A){
				// use item
				try{
					Audio.main("data/sounds/invSelect.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
				if(player.inventory[invSel] == ID.FOOD){
					player.health = Server.clamp(player.health + 1, 0, 100);
					player.hunger = Server.clamp(player.hunger + 1, 0, 100);
					player.amounts[invSel]--;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
						itemSelected = false;
					}
				} 
				if(player.inventory[invSel] == ID.WOOD && player.amounts[invSel] >= 10){
					try{
						Audio.main("data/sounds/craft.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					server.addEntity(new Entity(new Point(player.position.x, player.position.y), ID.CRAFTING_TABLE, 1));
					player.amounts[invSel] -= 10;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
				}
				if(player.inventory[invSel] == ID.STONE && player.amounts[invSel] >= 20){
					try{
						Audio.main("data/sounds/craft.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					addItem(ID.MINELADDER);
					player.amounts[invSel] -= 20;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
				}
				if(player.inventory[invSel] == ID.ITEMFURN){
					server.addEntity(new Entity(new Point(player.position.x, player.position.y), ID.FURNACE, 1));
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.CHESTITEM){
					Entity e = new Entity(new Point(player.position.x, player.position.y), ID.CHEST, 1);
					e.maxHP = 200;
					server.addEntity(e);
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.ITEMWB){
					server.addEntity(new Entity(new Point(player.position.x, player.position.y), ID.WORKBENCH, 1));
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.COOKED_FOOD){
					player.health = Server.clamp(player.health + 5, 0, 100);
					player.hunger = Server.clamp(player.hunger + 10, 0, 100);
					player.amounts[invSel]--;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
						itemSelected = false;
					}
				} 
				if(player.inventory[invSel] == ID.ITEMWARD){
					server.addEntity(new Entity(new Point(player.position.x, player.position.y), ID.WARDROBE, 1));
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.ITEMFLOOR){
					server.addEntity(new Entity(new Point(player.position.x, player.position.y), ID.FLOOR, 200));
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.ITEMWALL){
					int dimensionChange = 0;
					if(dimension == ID.MINES) dimensionChange = 6000;
					if(player.position.x < (5000 - 50 + dimensionChange)) server.addEntity(new Entity(new Point(player.position.x + 50, player.position.y), ID.WALL, 200));
					else server.addEntity(new Entity(new Point(player.position.x - 50, player.position.y), ID.WALL, 200));
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.ITEMMWALL){
					int dimensionChange = 0;
					if(dimension == ID.MINES) dimensionChange = 6000;
					if(player.position.x < (5000 - 50 + dimensionChange)) server.addEntity(new Entity(new Point(player.position.x + 50, player.position.y), ID.METALWALL, 200));
					else server.addEntity(new Entity(new Point(player.position.x - 50, player.position.y), ID.METALWALL, 200));
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.BEDITEM){
					server.addEntity(new Entity(new Point(player.position.x, player.position.y), ID.BED, 1));
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
				if(player.inventory[invSel] == ID.WOODPICK){
					if(player.tool != ID.NOTHING) addItem(player.tool);
					player.tool = ID.WOODPICK;
					player.minePower = 2;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.STONEPICK){
					if(player.tool != ID.NOTHING) addItem(player.tool);
					player.tool = ID.STONEPICK;
					player.minePower = 4;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.CLUB){
					if(player.weapon != ID.NOTHING) addItem(player.weapon);
					player.weapon = ID.CLUB;
					player.attack = 5;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.STEELPICK){
					if(player.tool != ID.NOTHING) addItem(player.tool);
					player.tool = ID.STEELPICK;
					player.minePower = 10;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.STEELARMOR){
					if(player.armor != ID.NOTHING) addItem(player.armor);
					player.armor = ID.STEELARMOR;
					player.defense = 5;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.STEELSWORD){
					if(player.weapon != ID.NOTHING) addItem(player.weapon);
					player.weapon = ID.STEELSWORD;
					player.attack = 10;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.DRILL){
					if(player.tool != ID.NOTHING) addItem(player.tool);
					player.tool = ID.DRILL;
					player.minePower = 30;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.MINELADDER){
					if(dimension == ID.OVERWORLD){
						// in overworld
						dimension = ID.MINES;
						player.position.x += 6000;
					}else if(dimension == ID.MINES){
						// in mines
						dimension = ID.OVERWORLD;
						player.position.x -= 6000;
					}else if(dimension == ID.DEEPMINES){
						// in deep mines
						dimension = ID.MINES;
						player.position.x += 6000;
						player.position.y -= 6000;
					}else{
						// not in mines or overworld, can't use
						return;
					}
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.AUTODRILL){
					if(dimension == ID.OVERWORLD){
						// in overworld
						dimension = ID.DEEPMINES;
						player.position.y += 6000;
					}else if(dimension == ID.MINES){
						// in mines
						dimension = ID.DEEPMINES;
						player.position.x -= 6000;
						player.position.y += 6000;
					}else{
						// not in mines or overworld, can't use
						return;
					}
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.EVASWORD){
					if(player.weapon != ID.NOTHING) addItem(player.weapon);
					player.weapon = ID.EVASWORD;
					player.attack = 20;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.EVAPICK){
					if(player.tool != ID.NOTHING) addItem(player.tool);
					player.tool = ID.EVAPICK;
					player.minePower = 50;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}
				if(player.inventory[invSel] == ID.EVAARMOR){
					if(player.armor != ID.NOTHING) addItem(player.armor);
					player.armor = ID.EVAARMOR;
					player.defense = 15;
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					return;
				}

				if(player.inventory[invSel] == ID.REVIVALTOTEM){
					if(server.getPlayer(2) == null || !server.currentSave.gameRules.hardcore) return; // 1 player
					Player otherPlr = null;
					// get closest player
					Player[] players;
					if(server.getPlayer(4) == null){
						// <=3 players
						players = new Player[3];
						if(server.getPlayer(3) == null){
							// 2 players
							players = new Player[2];
						}
						players[0] = server.getPlayer(1);
						players[1] = server.getPlayer(2);
						if(server.getPlayer(3) != null) players[2] = server.getPlayer(3);

					}else{
						// 4 players
						players = new Player[4];
						players[0] = server.getPlayer(1);
						players[1] = server.getPlayer(2);
						players[2] = server.getPlayer(3);
						players[3] = server.getPlayer(4);
					}

					// get closest player
					otherPlr = server.getPlayer(1);
					int plrNum = 0;
					if(otherPlr.equals(player)) otherPlr = server.getPlayer(2);
					for(int i = 0; i < players.length; i++){
						if(!players[i].equals(player) && player.getDistance(server.getPlayer(i + 1).position) < player.getDistance(otherPlr.position) && server.getPlayer(i + 1).health <= 0){
							otherPlr = server.getPlayer(i + 1);
							plrNum = i;
						}
					}
					if(otherPlr.health > 0) return;
					server.playerPanels[plrNum].deathSoundPlayed = false;
					otherPlr.health = 100;
					otherPlr.hunger = 100;
					otherPlr.position = new Point(player.position.x, player.position.y);
					player.amounts[invSel] -= 1;
					if(player.amounts[invSel] <= 0){
						player.inventory[invSel] = ID.NOTHING;
						player.amounts[invSel] = 0;
					}
					itemSelected = false;
					invOpen = false;
					return;
				}
			}

			if(key == controls.DOWN){
				try{
					Audio.main("data/sounds/drop.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
				// drop item
				int dimensionChange = 0;
				if(dimension == ID.MINES) dimensionChange = 6000;
				if(player.position.x < (5000 - 50 + dimensionChange)) server.addEntity(new Item(new Point(player.position.x + 50, player.position.y), player.inventory[invSel]));
				else server.addEntity(new Item(new Point(player.position.x - 50, player.position.y), player.inventory[invSel]));
				player.amounts[invSel]--;
				if(player.amounts[invSel] <= 0){
					player.inventory[invSel] = ID.NOTHING;
					player.amounts[invSel] = 0;
					itemSelected = false;
				}
			}
		}
		if(craftingMenu && pressed){
			if(key == controls.UP) invSel--;
			if(key == controls.DOWN) invSel++;
			if(invSel < 0) invSel = 8;
			if(invSel > 8) invSel = 0;
			if(key == controls.A){
				if(invSel == 8){
					// pick up table
					craftingMenu = false;
					addItem(ID.WOOD, 10);
					// delete table
					for(Entity e: server.getEntities()){
						if(e == null) continue;
						if(e.id == ID.CRAFTING_TABLE && e.getDistance(player.position) <= reach){
							e.health = 0;
							break;
						}
					}
					try{
						Audio.main("data/sounds/drop.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				boolean canCraft = true;
				for(int i = 0; i < server.craftingRecipes[invSel].items.length; i++){
					boolean enough = false;
					for(int i2 = 0; i2<player.inventory.length; i2++){
						if(player.inventory[i2] == server.craftingRecipes[invSel].items[i] && player.amounts[i2] >= server.craftingRecipes[invSel].amounts[i]){
							enough = true;
							break;
						}
					}
					if(!enough){
						canCraft = false;
						break;
					}
				}
				if(canCraft){
					try{
						Audio.main("data/sounds/craft.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					for(int i = 0; i < server.craftingRecipes[invSel].items.length; i++){
						for(int i2 = 0; i2<player.inventory.length; i2++){
							if(player.inventory[i2] == server.craftingRecipes[invSel].items[i] && player.amounts[i2] >= server.craftingRecipes[invSel].amounts[i]){
								player.amounts[i2] -= server.craftingRecipes[invSel].amounts[i];
								break;
							}
						}
					} // take away items
					addItem(server.craftingRecipes[invSel].output);
				}
			}

		}
		if(cookMenu && pressed){
			if(key == controls.UP) invSel--;
			if(key == controls.DOWN) invSel++;
			if(invSel < 0) invSel = 7;
			if(invSel > 7) invSel = 0;
			if(key == controls.A){
				if(invSel == 7){
					// pick up furnace
					cookMenu = false;
					addItem(ID.ITEMFURN);
					// delete furnace
					for(Entity e: server.getEntities()){
						if(e == null) continue;
						if(e.id == ID.FURNACE && e.getDistance(player.position) <= reach){
							e.health = 0;
							break;
						}
					}
					try{
						Audio.main("data/sounds/drop.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				boolean canCraft = true;
				for(int i = 0; i < server.furnaceRecipes[invSel].items.length; i++){
					boolean enough = false;
					for(int i2 = 0; i2<player.inventory.length; i2++){
						if(player.inventory[i2] == server.furnaceRecipes[invSel].items[i] && player.amounts[i2] >= server.furnaceRecipes[invSel].amounts[i]){
							enough = true;
							break;
						}
					}
					if(!enough){
						canCraft = false;
						break;
					}
				}
				if(canCraft){
					try{
						Audio.main("data/sounds/craft.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					for(int i = 0; i < server.furnaceRecipes[invSel].items.length; i++){
						for(int i2 = 0; i2<player.inventory.length; i2++){
							if(player.inventory[i2] == server.furnaceRecipes[invSel].items[i] && player.amounts[i2] >= server.furnaceRecipes[invSel].amounts[i]){
								player.amounts[i2] -= server.furnaceRecipes[invSel].amounts[i];
								break;
							}
						}
					}
					addItem(server.furnaceRecipes[invSel].output);
				}
			}
		}
		if(shopOpen && pressed){
			if(key == controls.UP) invSel--;
			if(key == controls.DOWN) invSel++;
			if(key == controls.RIGHT || key == controls.LEFT){
				shopSell = !shopSell;
				invSel = 0;
			}
			if(!shopSell){
				// buying
				if(invSel < 0) invSel = 5;
				if(invSel > 5) invSel = 0;
				
				if(key == controls.A){
					boolean canBuy = false;
					for(int i = 0; i < player.inventory.length; i++){
						if(player.inventory[i] == ID.COIN && player.amounts[i] >= server.prices[invSel]){
							// can buy
							canBuy = true;
							player.amounts[i] -= server.prices[invSel];
							break;
						}
					}
					if(canBuy){
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						addItem(server.shopSelling[invSel]);
					}
				}
			}else{
				if(invSel < 0) invSel = 5;
				if(invSel > 5) invSel = 0;

				if(key == controls.A){
					try{
						Audio.main("data/sounds/select.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					addItem(ID.COIN, server.sellPrices[player.inventory[invSel]]);
					player.amounts[invSel]--;
					if(player.amounts[invSel] <= 0){
						player.amounts[invSel] = 0;
						player.inventory[invSel] = ID.NOTHING;

					}
				}
			}
		}
		if(wardOpen && pressed){
			if(key == controls.RIGHT){
				wardrobeSel++;
				if(wardrobeSel >= wardrobeSelections.length){
					wardrobeSel = 0;
				}
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.LEFT){
				wardrobeSel--;
				if(wardrobeSel < 0){
					wardrobeSel = wardrobeSelections.length - 1;
				}
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.A){
				wardOpen = false;
				try{
					Audio.main("data/sounds/select.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
		if(workbenchMenu && pressed){
			if(key == controls.UP) invSel--;
			if(key == controls.DOWN) invSel++;
			if(invSel < 0) invSel = server.workbenchRecipes.length;
			if(invSel > server.workbenchRecipes.length) invSel = 0;
			if(key == controls.A){
				if(invSel == server.workbenchRecipes.length){
					// pick up workbench
					workbenchMenu = false;
					addItem(ID.ITEMWB);
					// delete table
					for(Entity e: server.getEntities()){
						if(e == null) continue;
						if(e.id == ID.WORKBENCH && e.getDistance(player.position) <= reach){
							e.health = 0;
							break;
						}
					}
					try{
						Audio.main("data/sounds/drop.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				boolean canCraft = true;
				for(int i = 0; i < server.workbenchRecipes[invSel].items.length; i++){
					boolean enough = false;
					for(int i2 = 0; i2<player.inventory.length; i2++){
						if(player.inventory[i2] == server.workbenchRecipes[invSel].items[i] && player.amounts[i2] >= server.workbenchRecipes[invSel].amounts[i]){
							enough = true;
							break;
						}
					}
					if(!enough){
						canCraft = false;
						break;
					}
				}
				if(canCraft){
					try{
						Audio.main("data/sounds/craft.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					for(int i = 0; i < server.workbenchRecipes[invSel].items.length; i++){
						for(int i2 = 0; i2<player.inventory.length; i2++){
							if(player.inventory[i2] == server.workbenchRecipes[invSel].items[i] && player.amounts[i2] >= server.workbenchRecipes[invSel].amounts[i]){
								player.amounts[i2] -= server.workbenchRecipes[invSel].amounts[i];
								break;
							}
						}
					} // take away items
					addItem(server.workbenchRecipes[invSel].output);
				}
			}

		}
		if(chestOpen && pressed){
			if(key == controls.UP){
				invSel--;
				if(invSel < 0) invSel = 5;
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.DOWN){
				invSel++;
				if(invSel > 5) invSel = 0;
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.LEFT){
				if(openedChest.itemID == ID.NOTHING){
					// pick up chest
					chestOpen = false;
					openedChest.health = 0;
					addItem(ID.CHESTITEM);
					try{
						Audio.main("data/sounds/drop.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
					return;
				}
				// take 1 item from chest
				addItem(openedChest.itemID);
				if(openedChest.health == 1) openedChest.itemID = ID.NOTHING;
				else openedChest.health--;
				try{
					Audio.main("data/sounds/moveSel.wav");
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			if(key == controls.RIGHT){
				// put 1 item in chest if same item
				if(player.inventory[invSel] != ID.NOTHING){
					if(openedChest.itemID == ID.NOTHING){
						// chest is empty
						openedChest.itemID = player.inventory[invSel];
						player.amounts[invSel]--;
						try{
							Audio.main("data/sounds/moveSel.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
					}else{
						// chest is not empty
						if(openedChest.itemID == player.inventory[invSel] && openedChest.health < openedChest.maxHP){
							// can store item
							openedChest.health++;
							player.amounts[invSel]--;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
					}
					
				}
			}
			if(key == controls.A){
				// put as many items as possible in chest or take all if inv slot is empty
				if(player.inventory[invSel] == ID.NOTHING){
					// take items from chest
					addItem(openedChest.itemID, openedChest.health);
					openedChest.itemID = ID.NOTHING;
					openedChest.health = 1;
					try{
						Audio.main("data/sounds/moveSel.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
				}else{
					// put items in chest
					if(openedChest.itemID == ID.NOTHING){
						// chest is empty
						if(player.amounts[invSel] <= openedChest.maxHP){
							// put all in
							openedChest.itemID = player.inventory[invSel];
							openedChest.health = player.amounts[invSel];
							player.inventory[invSel] = ID.NOTHING;
							player.amounts[invSel] = 0;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}else{
							// put 200 in
							openedChest.itemID = player.inventory[invSel];
							openedChest.health = openedChest.maxHP;
							player.amounts[invSel] -= openedChest.maxHP;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						return;
					}
					if(openedChest.health < openedChest.maxHP && player.inventory[invSel] == openedChest.itemID){
						if(player.amounts[invSel] <= openedChest.maxHP - openedChest.health){
							openedChest.health += player.amounts[invSel];
							player.amounts[invSel] = 0;
							player.inventory[invSel] = ID.NOTHING;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}else{
							// slot has more items than chest can fit
							player.amounts[invSel] -= openedChest.maxHP - openedChest.health;
							openedChest.health = openedChest.maxHP;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
						return;
					}
					if(openedChest.health == openedChest.maxHP){
						// take all items
						addItem(openedChest.itemID, openedChest.health);
						openedChest.itemID = ID.NOTHING;
						openedChest.health = 1;
						try{
							Audio.main("data/sounds/moveSel.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
					}
				}
			}
		}
	}

	public Player getPlayer(){
		return player;
	}

	public void addItem(int id, int amount){
		// check for pre-existing stacks of same id
		boolean pickedUp = false;
		for(int i = 0; i < player.inventory.length; i++){
			if(player.inventory[i] == id){
				// it's a match!
				pickedUp = true;
				player.amounts[i] += amount;
				// item toast
				boolean toastUpdated = false;
				for(Toast t: itemToasts){ // check for working toast
					if(t.id == id){
						t.amount += amount;
						t.life = 2000;
						toastUpdated = true;
						break;
					}
				}
				if(toastUpdated) return;
				if(itemToasts.size() >= maxToasts){
					itemToasts.remove(2);
				}
				Toast newToast = new Toast(id, 2000, amount);
				itemToasts.add(0, newToast);
				break;
			}
		}
		// if no pre-existing, find empty
		if(!pickedUp){
			for(int i = 0; i < player.inventory.length; i++){
				if(player.inventory[i] == ID.NOTHING){
					// it's a match!
					pickedUp = true;
					player.amounts[i] += amount;
					player.inventory[i] = id;
					// item toast
					boolean toastUpdated = false;
					for(Toast t: itemToasts){ // check for working toast
						if(t.id == id){
							t.amount += amount;
							t.life = 2000;
							toastUpdated = true;
							break;
						}
					}
					if(toastUpdated) return;
					if(itemToasts.size() >= maxToasts){
						itemToasts.remove(2);
					}
					Toast newToast = new Toast(id, 2000, amount);
					itemToasts.add(0, newToast);
					break;
				}
			}
		}
		// if no empty and no pre-existing, drop
		if(!pickedUp){
			// it wasn't meant to be :(
			int dimensionChange = 0;
			if(dimension == ID.MINES) dimensionChange = 6000;
			if(player.position.x < (5000 - 50 + dimensionChange)) for(int i = 0; i < amount; i++) server.addEntity(new Item(new Point(player.position.x + 50, player.position.y), id));
			else for(int i = 0; i < amount; i++) server.addEntity(new Item(new Point(player.position.x - 50, player.position.y), id));
		}
	}

	public void addItem(int id){
		addItem(id, 1);
	}

	public void damage(int amount){
		player.health -= amount;
		hurtFlash = 1000;
		try{
			Audio.main("data/sounds/hurt.wav");
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
