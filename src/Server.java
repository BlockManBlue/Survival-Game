import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
public class Server { // holds data, saves & loads
	
	PlayerPanel[] playerPanels;
	Timer timer = new Timer(1, new ServerListener());
	public static Background bg = new Background(new ImageIcon("data/images/background.jpg"));
	public static Background mines = new Background(new ImageIcon("data/images/mines.png"));
	public static Background deepMines = new Background(new ImageIcon("data/images/mines.png"));

	long previousStartTime = -1;
	long elapsedTime;

	public Save currentSave;
	int saveNum;

	Entity[] entities = new Entity[0];
	Entity shop = new Entity(new Point(randNum(0, 5000), randNum(0, 5000)), ID.SHOP, 1);

	long spawnTimer = 0;
	long spawnTime = 5000;
	int maxEntities = 3000;
	
	public long time = 0;
	public final long maxTime = 600000;
	public double darkness;
	private double maxDarkness = 0.7;

	public Recipe[] craftingRecipes = new Recipe[10];
	public Recipe[] furnaceRecipes = new Recipe[10];
	public Recipe[] workbenchRecipes;

	public int[] sellPrices = {
		0,
		1,
		1,
		2,
		5,
		10,
		30,
		5,
		5,
		2,
		10,
		50,
		1,
		90,
		900,
		10,
		11,
		15,
		110,
		200,
		105,
		20,
		20,
		420,
		15,
		20,
		670,
		75,
		923,
		15,
		25,
		60,
		610,
		610,
		1325,
		270
	};
	public int[] shopSelling = {
		ID.BEDITEM,
		ID.WOOD,
		ID.STONE,
		ID.COOKED_FOOD,
		ID.ITEMMWALL,
		ID.REVIVALTOTEM
	};
	public int[] prices = {
		100,
		1,
		1,
		5,
		60,
		1000
	};

	public Server() {
		setRecipes();
		mines.position = new Point(6000, 0);
		deepMines.position = new Point(0, 6000);
	}
	
	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	private void setRecipes(){
		// >>> Crafting Recipes <<<
		//furnace
		if(true){
			int[] items = {ID.STONE};
			int[] amounts = {10};
			craftingRecipes[ID.CRAFTFURN] = new Recipe(items, amounts, ID.ITEMFURN);
		}
		// ***
		//wardrobe
		if(true){
			int[] items = {ID.WOOD};
			int[] amounts = {30};
			craftingRecipes[ID.CRAFTWARD] = new Recipe(items, amounts, ID.ITEMWARD);
		}
		// ***
		//floor
		if(true){
			int[] items = {ID.WOOD};
			int[] amounts = {5};
			craftingRecipes[ID.CRAFTFLOOR] = new Recipe(items, amounts, ID.ITEMFLOOR);
		}
		// ***
		//wall
		if(true){
			int[] items = {ID.STONE};
			int[] amounts = {5};
			craftingRecipes[ID.CRAFTWALL] = new Recipe(items, amounts, ID.ITEMWALL);
		}
		// ***
		//metal wall
		if(true){
			int[] items = {ID.STEEL};
			int[] amounts = {5};
			craftingRecipes[ID.CRAFTMWALL] = new Recipe(items, amounts, ID.ITEMMWALL);
		}
		// ***
		//wood pick
		if(true){
			int[] items = {ID.WOOD};
			int[] amounts = {10};
			craftingRecipes[ID.CRAFTWOODPICK] = new Recipe(items, amounts, ID.WOODPICK);
		}
		// ***
		//stone pick
		if(true){
			int[] items = {ID.WOOD, ID.STONE};
			int[] amounts = {5, 5};
			craftingRecipes[ID.CRAFTSTONEPICK] = new Recipe(items, amounts, ID.STONEPICK);
		}
		// ***
		//club
		if(true){
			int[] items = {ID.WOOD, ID.STONE};
			int[] amounts = {5, 10};
			craftingRecipes[ID.CRAFTCLUB] = new Recipe(items, amounts, ID.CLUB);
		}
		// ***
		
		// >>> Furnace Recipes <<<
		//cooked food
		if(true){
			int[] items = {ID.FOOD, ID.WOOD};
			int[] amounts = {1, 2};
			furnaceRecipes[ID.COOKFOOD] = new Recipe(items, amounts, ID.COOKED_FOOD);
		}
		// ***
		//steel
		if(true){
			int[] items = {ID.OREITEM, ID.WOOD};
			int[] amounts = {3, 2};
			furnaceRecipes[ID.COOKMETAL] = new Recipe(items, amounts, ID.STEEL);
		}
		// ***
		//steel pick
		if(true){
			int[] items = {ID.STEEL, ID.WOOD};
			int[] amounts = {10, 10};
			furnaceRecipes[ID.COOKSTEELPICK] = new Recipe(items, amounts, ID.STEELPICK);
		}
		// ***
		//steel armor
		if(true){
			int[] items = {ID.STEEL, ID.WOOD};
			int[] amounts = {20, 5};
			furnaceRecipes[ID.COOKSTEELARMOR] = new Recipe(items, amounts, ID.STEELARMOR);
		}
		// ***
		//steel sword
		if(true){
			int[] items = {ID.STEEL, ID.WOOD};
			int[] amounts = {10, 7};
			furnaceRecipes[ID.COOKSTEELSWORD] = new Recipe(items, amounts, ID.STEELSWORD);
		}
		// ***
		//workbench
		if(true){
			int[] items = {ID.WOOD, ID.STEEL, ID.CRYSTAL};
			int[] amounts = {20, 30, 5};
			furnaceRecipes[ID.COOKWORKBENCH] = new Recipe(items, amounts, ID.ITEMWB);
		}
		// ***
		//evanite
		if(true){
			int[] items = {ID.EVANITEORE, ID.WOOD, ID.SHROOMS};
			int[] amounts = {5, 5, 1};
			furnaceRecipes[ID.COOKEVANITE] = new Recipe(items, amounts, ID.EVANITE);
		}
		// ***

		// >>> Workbench Recipes <<<
		workbenchRecipes = new Recipe[9];
		//wire
		if(true){
			int[] items = {ID.STEEL, ID.FOOD, ID.WOOD};
			int[] amounts = {1, 1, 1};
			workbenchRecipes[ID.WB_WIRE] = new Recipe(items, amounts, ID.WIRE);
		}
		// ***
		//battery
		if(true){
			int[] items = {ID.WIRE, ID.COIN, ID.FOOD};
			int[] amounts = {1, 5, 2};
			workbenchRecipes[ID.WB_BATTERY] = new Recipe(items, amounts, ID.BATTERY);
		}
		// ***
		//drill
		if(true){
			int[] items = {ID.STEEL, ID.WIRE, ID.BATTERY, ID.CRYSTAL};
			int[] amounts = {20, 10, 6, 10};
			workbenchRecipes[ID.WB_DRILL] = new Recipe(items, amounts, ID.DRILL);
		}
		// ***
		//circuit board
		if(true){
			int[] items = {ID.WIRE, ID.BATTERY, ID.STEEL};
			int[] amounts = {1, 1, 1};
			workbenchRecipes[ID.WB_CBOARD] = new Recipe(items, amounts, ID.CIRCUITBOARD);
		}
		// ***
		//auto drill
		if(true){
			int[] items = {ID.CIRCUITBOARD, ID.WIRE, ID.FOOD, ID.DRILL, ID.CRYSTAL};
			int[] amounts = {1, 5, 3, 1, 5};
			workbenchRecipes[ID.WB_AUTODRILL] = new Recipe(items, amounts, ID.AUTODRILL);
		}
		// ***
		//evanite sword
		if(true){
			int[] items = {ID.EVANITE, ID.WOOD, ID.CRYSTAL};
			int[] amounts = {10, 5, 5};
			workbenchRecipes[ID.WB_EVASWORD] = new Recipe(items, amounts, ID.EVASWORD);
		}
		// ***
		//evanite pick
		if(true){
			int[] items = {ID.EVANITE, ID.WOOD, ID.CRYSTAL};
			int[] amounts = {10, 7, 2};
			workbenchRecipes[ID.WB_EVAPICK] = new Recipe(items, amounts, ID.EVAPICK);
		}
		// ***
		//evanite armor
		if(true){
			int[] items = {ID.EVANITE, ID.SHROOMS};
			int[] amounts = {20, 5};
			workbenchRecipes[ID.WB_EVAARMOR] = new Recipe(items, amounts, ID.EVAARMOR);
		}
		// ***
		//chest
		if(true){
			int[] items = {ID.WOOD, ID.STEEL};
			int[] amounts = {20, 5};
			workbenchRecipes[ID.WB_CHEST] = new Recipe(items, amounts, ID.CHESTITEM);
		}
		// ***
	}

	public Controls getControls(int plr){
		try{
			// create reader
			Scanner reader = new Scanner(new File("data/settings.txt"));
			reader.nextLine(); // skip sounds line

			if(plr == 1){
				// load player 1 controls
				int UP, DOWN, RIGHT, LEFT, A, B;
				UP = reader.nextInt();
				reader.nextLine();
				DOWN = reader.nextInt();
				reader.nextLine();
				RIGHT = reader.nextInt();
				reader.nextLine();
				LEFT = reader.nextInt();
				reader.nextLine();
				A = reader.nextInt();
				reader.nextLine();
				B = reader.nextInt();
				reader.nextLine();
				Controls c = new Controls(UP, DOWN, RIGHT, LEFT, A, B);
				return c;
			}else if(plr == 2){
				for(int i = 0; i < 6; i++){
					reader.nextLine(); // skip to player 2 controls
				}
				// load player 2 controls
				int UP, DOWN, RIGHT, LEFT, A, B;
				UP = reader.nextInt();
				reader.nextLine();
				DOWN = reader.nextInt();
				reader.nextLine();
				RIGHT = reader.nextInt();
				reader.nextLine();
				LEFT = reader.nextInt();
				reader.nextLine();
				A = reader.nextInt();
				reader.nextLine();
				B = reader.nextInt();
				reader.nextLine();
				Controls c = new Controls(UP, DOWN, RIGHT, LEFT, A, B);
				return c;
			}else if(plr == 3){
				for(int i = 0; i < 12; i++){
					reader.nextLine(); // skip to player 3 controls
				}
				// load player 3 controls
				int UP, DOWN, RIGHT, LEFT, A, B;
				UP = reader.nextInt();
				reader.nextLine();
				DOWN = reader.nextInt();
				reader.nextLine();
				RIGHT = reader.nextInt();
				reader.nextLine();
				LEFT = reader.nextInt();
				reader.nextLine();
				A = reader.nextInt();
				reader.nextLine();
				B = reader.nextInt();
				reader.nextLine();
				Controls c = new Controls(UP, DOWN, RIGHT, LEFT, A, B);
				return c;
			}else if(plr == 4){
				for(int i = 0; i < 18; i++){
					reader.nextLine(); // skip to player 3 controls
				}
				// load player 3 controls
				int UP, DOWN, RIGHT, LEFT, A, B;
				UP = reader.nextInt();
				reader.nextLine();
				DOWN = reader.nextInt();
				reader.nextLine();
				RIGHT = reader.nextInt();
				reader.nextLine();
				LEFT = reader.nextInt();
				reader.nextLine();
				A = reader.nextInt();
				reader.nextLine();
				B = reader.nextInt();
				reader.nextLine();
				Controls c = new Controls(UP, DOWN, RIGHT, LEFT, A, B);
				return c;
			}
			return null;
		}catch(Exception e){
			return null;
		}
	}

	public void saveControls(Controls plr1, Controls plr2, Controls plr3, Controls plr4){
		// make writer to save settings
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(new File("data/settings.txt"));
		}catch(FileNotFoundException e){
			// file not found, make new file
			try{
				File file = new File("data/settings.txt");
				file.createNewFile();
				writer = new PrintWriter(file);
			}catch(IOException ex){
				// problem creating file, which means the folder doesn't exist
				try{
					File file = new File("data/settings.txt");
					File file2 = new File("data");
					file2.mkdir();
					file.createNewFile();
					writer = new PrintWriter(file);

				}catch(IOException exc){
					// ok this shouldnt happen because we already made the folder AND file
					System.out.println("IOException");
					exc.printStackTrace();
					System.exit(1);
				}
			}
		}
		// save sounds enabled
		writer.println("" + Audio.enabled);
		// save player 1 controls
		writer.println("" + plr1.UP);
		writer.println("" + plr1.DOWN);
		writer.println("" + plr1.RIGHT);
		writer.println("" + plr1.LEFT);
		writer.println("" + plr1.A);
		writer.println("" + plr1.B);
		// save player 2 controls
		writer.println("" + plr2.UP);
		writer.println("" + plr2.DOWN);
		writer.println("" + plr2.RIGHT);
		writer.println("" + plr2.LEFT);
		writer.println("" + plr2.A);
		writer.println("" + plr2.B);
		// save player 3 controls
		writer.println("" + plr3.UP);
		writer.println("" + plr3.DOWN);
		writer.println("" + plr3.RIGHT);
		writer.println("" + plr3.LEFT);
		writer.println("" + plr3.A);
		writer.println("" + plr3.B);
		// save player 4 controls
		writer.println("" + plr4.UP);
		writer.println("" + plr4.DOWN);
		writer.println("" + plr4.RIGHT);
		writer.println("" + plr4.LEFT);
		writer.println("" + plr4.A);
		writer.println("" + plr4.B);
		writer.close();
	}

	public Save getSave(int save){
		Entity[] e = new Entity[0];
		try{
			File gameRulesFile = new File("data/saves/" + save + "/gameRules.txt");
			File plr1File = new File("data/saves/" + save + "/plr1.txt");
			File plr2File = new File("data/saves/" + save + "/plr2.txt");
			File plr3File = null;
			File plr4File = null;
			try{
				plr3File = new File("data/saves/" + save + "/plr3.txt");
				plr4File = new File("data/saves/" + save + "/plr4.txt");
			}catch(Exception ex){
				System.out.println("Old save, no plr3 or plr4");
			}
			
			File entitiesFile = new File("data/saves/" + save + "/entities.txt");
			File timeFile = new File("data/saves/" + save + "/time.txt");
			Scanner reader;

			// load info
			// gamerules
			int numPlrs;
			boolean pvp, dayCycle, showCoords, h, moveShop, hardcore;
			reader = new Scanner(gameRulesFile);
			pvp = reader.nextBoolean();
			reader.nextLine();
			numPlrs = reader.nextInt();
			reader.nextLine();
			dayCycle = reader.nextBoolean();
			reader.nextLine();
			showCoords = reader.nextBoolean();
			reader.nextLine();
			h = reader.nextBoolean();
			reader.nextLine();
			moveShop = reader.nextBoolean();
			reader.nextLine();
			hardcore = reader.nextBoolean();
			reader.nextLine();
			reader.close();
			GameRules gr = new GameRules(numPlrs, pvp, dayCycle, showCoords, h, moveShop, hardcore);

			// plr1
			Player p1 = null;
			if(true){
				Color color;
				Point pos = new Point();
				int health, hunger;
				int[] inventory = new int[6], amounts = new int[6];
				reader = new Scanner(plr1File);
				int r = reader.nextInt();
				reader.nextLine();
				int g = reader.nextInt();
				reader.nextLine();
				int b = reader.nextInt();
				reader.nextLine();
				color = new Color(r, g, b);
				pos.x = reader.nextInt();
				reader.nextLine();
				pos.y = reader.nextInt();
				reader.nextLine();
				health = reader.nextInt();
				reader.nextLine();
				hunger = reader.nextInt();
				reader.nextLine();
				for(int i = 0; i < 6; i++){
					inventory[i] = reader.nextInt();
					reader.nextLine();
					amounts[i] = reader.nextInt();
					reader.nextLine();
				}
				p1 = new Player(pos, color, inventory, amounts, health, hunger);
				p1.armor = reader.nextInt();
				reader.nextLine();
				p1.defense = reader.nextInt();
				reader.nextLine();
				p1.weapon = reader.nextInt();
				reader.nextLine();
				p1.attack = reader.nextInt();
				reader.nextLine();
				p1.tool = reader.nextInt();
				reader.nextLine();
				p1.minePower = reader.nextInt();
				reader.nextLine();
				reader.close();

			}

			// plr2
			Player p2 = null;
			if(numPlrs >= 2){
				Color color;
				Point pos = new Point();
				int health, hunger;
				int[] inventory = new int[6], amounts = new int[6];
				reader = new Scanner(plr2File);
				int r = reader.nextInt();
				reader.nextLine();
				int g = reader.nextInt();
				reader.nextLine();
				int b = reader.nextInt();
				reader.nextLine();
				color = new Color(r, g, b);
				pos.x = reader.nextInt();
				reader.nextLine();
				pos.y = reader.nextInt();
				reader.nextLine();
				health = reader.nextInt();
				reader.nextLine();
				hunger = reader.nextInt();
				reader.nextLine();
				for(int i = 0; i < 6; i++){
					inventory[i] = reader.nextInt();
					reader.nextLine();
					amounts[i] = reader.nextInt();
					reader.nextLine();
				}
				p2 = new Player(pos, color, inventory, amounts, health, hunger);
				p2.armor = reader.nextInt();
				reader.nextLine();
				p2.defense = reader.nextInt();
				reader.nextLine();
				p2.weapon = reader.nextInt();
				reader.nextLine();
				p2.attack = reader.nextInt();
				reader.nextLine();
				p2.tool = reader.nextInt();
				reader.nextLine();
				p2.minePower = reader.nextInt();
				reader.nextLine();
				reader.close();

				
			}
			// plr3
			Player p3 = null;
			if(numPlrs >= 3){
				Color color;
				Point pos = new Point();
				int health, hunger;
				int[] inventory = new int[6], amounts = new int[6];
				reader = new Scanner(plr3File);
				int r = reader.nextInt();
				reader.nextLine();
				int g = reader.nextInt();
				reader.nextLine();
				int b = reader.nextInt();
				reader.nextLine();
				color = new Color(r, g, b);
				pos.x = reader.nextInt();
				reader.nextLine();
				pos.y = reader.nextInt();
				reader.nextLine();
				health = reader.nextInt();
				reader.nextLine();
				hunger = reader.nextInt();
				reader.nextLine();
				for(int i = 0; i < 6; i++){
					inventory[i] = reader.nextInt();
					reader.nextLine();
					amounts[i] = reader.nextInt();
					reader.nextLine();
				}
				p3 = new Player(pos, color, inventory, amounts, health, hunger);
				p3.armor = reader.nextInt();
				reader.nextLine();
				p3.defense = reader.nextInt();
				reader.nextLine();
				p3.weapon = reader.nextInt();
				reader.nextLine();
				p3.attack = reader.nextInt();
				reader.nextLine();
				p3.tool = reader.nextInt();
				reader.nextLine();
				p3.minePower = reader.nextInt();
				reader.nextLine();
				reader.close();

				
			}
			// plr4
			Player p4 = null;
			if(numPlrs == 4){
				Color color;
				Point pos = new Point();
				int health, hunger;
				int[] inventory = new int[6], amounts = new int[6];
				reader = new Scanner(plr4File);
				int r = reader.nextInt();
				reader.nextLine();
				int g = reader.nextInt();
				reader.nextLine();
				int b = reader.nextInt();
				reader.nextLine();
				color = new Color(r, g, b);
				pos.x = reader.nextInt();
				reader.nextLine();
				pos.y = reader.nextInt();
				reader.nextLine();
				health = reader.nextInt();
				reader.nextLine();
				hunger = reader.nextInt();
				reader.nextLine();
				for(int i = 0; i < 6; i++){
					inventory[i] = reader.nextInt();
					reader.nextLine();
					amounts[i] = reader.nextInt();
					reader.nextLine();
				}
				p4 = new Player(pos, color, inventory, amounts, health, hunger);
				p4.armor = reader.nextInt();
				reader.nextLine();
				p4.defense = reader.nextInt();
				reader.nextLine();
				p4.weapon = reader.nextInt();
				reader.nextLine();
				p4.attack = reader.nextInt();
				reader.nextLine();
				p4.tool = reader.nextInt();
				reader.nextLine();
				p4.minePower = reader.nextInt();
				reader.nextLine();
				reader.close();

				
			}
			// load entities
			reader = new Scanner(entitiesFile);
			e = new Entity[0];
			while(reader.hasNext()){
				int x, y, id, hp, itemID;
				x = reader.nextInt();
				reader.nextLine();
				y = reader.nextInt();
				reader.nextLine();
				id = reader.nextInt();
				reader.nextLine();
				hp = reader.nextInt();
				reader.nextLine();
				itemID = reader.nextInt();
				Entity entity = new Entity(new Point(x, y), id, hp, itemID);
				if(entity.id == ID.CHEST){
					entity.maxHP = 200;
				}
				Entity[] newEntities = new Entity[e.length + 1];
				for(int i = 0; i < e.length; i++) {
					newEntities[i] = e[i];
				}
				newEntities[newEntities.length - 1] = entity;
				e = newEntities;
			}
			reader.close();

			reader = new Scanner(timeFile);
			long t = reader.nextLong();
			reader.close();

			Save s = new Save(gr, p1, p2, p3, p4, e, t);
			return s;

		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public void save(Save save, int num){
		// save is the actual file to save
		// num is which save it is under, being 1, 2, or 3
		try{
			// create files
			File dataDir = new File("data");
			dataDir.mkdir();
			File savesDir = new File("data/saves");
			savesDir.mkdir();
			File saveDir = new File("data/saves/" + num);
			saveDir.mkdir();
			File gameRulesFile = new File("data/saves/" + num + "/gameRules.txt");
			gameRulesFile.createNewFile();
			File plr1File = new File("data/saves/" + num + "/plr1.txt");
			plr1File.createNewFile();
			File plr2File = new File("data/saves/" + num + "/plr2.txt"); // even if it's only 1 player, make this to avoid errors in loading
			plr2File.createNewFile();
			File plr3File = new File("data/saves/" + num + "/plr3.txt");
			plr3File.createNewFile();
			File plr4File = new File("data/saves/" + num + "/plr4.txt");
			plr4File.createNewFile();
			File entitiesFile = new File("data/saves/" + num + "/entities.txt");
			entitiesFile.createNewFile();
			File timeFile = new File("data/saves/" + num + "/time.txt");
			timeFile.createNewFile();

			// save info in files
			// save gamerules
			GameRules gr = save.gameRules;
			PrintWriter writer = new PrintWriter(gameRulesFile);
			writer.println(gr.pvp);
			writer.println(gr.numPlrs);
			writer.println(gr.dayCycle);
			writer.println(gr.showCoords);
			writer.println(gr.hunger);
			writer.println(gr.moveShop);
			writer.println(gr.hardcore);
			writer.close();
			
			// save player 1
			Player p1 = save.plr1;
			writer = new PrintWriter(plr1File);
			writer.println(p1.color.getRed());
			writer.println(p1.color.getGreen());
			writer.println(p1.color.getBlue());
			writer.println(p1.position.x);
			writer.println(p1.position.y);
			writer.println(p1.health);
			writer.println(p1.hunger);
			for(int i = 0; i < p1.inventory.length; i++){
				writer.println(p1.inventory[i]);
				writer.println(p1.amounts[i]);
			}
			writer.println(p1.armor);
			writer.println(p1.defense);
			writer.println(p1.weapon);
			writer.println(p1.attack);
			writer.println(p1.tool);
			writer.println(p1.minePower);
			writer.close();

			// save player 2, IF there are 2+ players
			if(gr.numPlrs >= 2){
				Player p2 = save.plr2;
				writer = new PrintWriter(plr2File);
				writer.println(p2.color.getRed());
				writer.println(p2.color.getGreen());
				writer.println(p2.color.getBlue());
				writer.println(p2.position.x);
				writer.println(p2.position.y);
				writer.println(p2.health);
				writer.println(p2.hunger);
				for(int i = 0; i < p2.inventory.length; i++){
					writer.println(p2.inventory[i]);
					writer.println(p2.amounts[i]);
				}
				writer.println(p2.armor);
				writer.println(p2.defense);
				writer.println(p2.weapon);
				writer.println(p2.attack);
				writer.println(p2.tool);
				writer.println(p2.minePower);
			}
			writer.close();

			// save player 3, IF there are 3+ players
			if(gr.numPlrs >= 3){
				Player p3 = save.plr3;
				writer = new PrintWriter(plr3File);
				writer.println(p3.color.getRed());
				writer.println(p3.color.getGreen());
				writer.println(p3.color.getBlue());
				writer.println(p3.position.x);
				writer.println(p3.position.y);
				writer.println(p3.health);
				writer.println(p3.hunger);
				for(int i = 0; i < p3.inventory.length; i++){
					writer.println(p3.inventory[i]);
					writer.println(p3.amounts[i]);
				}
				writer.println(p3.armor);
				writer.println(p3.defense);
				writer.println(p3.weapon);
				writer.println(p3.attack);
				writer.println(p3.tool);
				writer.println(p3.minePower);
			}
			writer.close();

			// save player 4, IF there are 4 players
			if(gr.numPlrs == 4){
				Player p4 = save.plr4;
				writer = new PrintWriter(plr4File);
				writer.println(p4.color.getRed());
				writer.println(p4.color.getGreen());
				writer.println(p4.color.getBlue());
				writer.println(p4.position.x);
				writer.println(p4.position.y);
				writer.println(p4.health);
				writer.println(p4.hunger);
				for(int i = 0; i < p4.inventory.length; i++){
					writer.println(p4.inventory[i]);
					writer.println(p4.amounts[i]);
				}
				writer.println(p4.armor);
				writer.println(p4.defense);
				writer.println(p4.weapon);
				writer.println(p4.attack);
				writer.println(p4.tool);
				writer.println(p4.minePower);
			}
			writer.close();

			// save all the entities
			writer = new PrintWriter(entitiesFile);
			for(Entity e: entities){
				if(e.id == ID.SHOP || e.id == ID.ITEM) continue;
				writer.println(e.position.x);
				writer.println(e.position.y);
				writer.println(e.id);
				writer.println(e.health);
				writer.println(e.itemID);
			}
			writer.close();

			// save time
			writer = new PrintWriter(timeFile);
			writer.println(time);
			writer.close();
		}catch(IOException e){
			System.out.println("How the heck did you do that?");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void save(){
		save(currentSave, saveNum);
	}

	public void setSave(int save){
		saveNum = save;
		currentSave = getSave(save);
		entities = currentSave.entities;
		time = currentSave.time;
		addEntity(shop);
	}

	public void setSave(int num, Save save){
		saveNum = num;
		currentSave = save;
		entities = currentSave.entities;
		time = currentSave.time;
		addEntity(shop);
	}

	public void setPlayerPanels(PlayerPanel[] p){
		playerPanels = p;
	}

	public void addEntity(Entity e) {
		Entity[] newEntities = new Entity[entities.length + 1];
		for(int i = 0; i < entities.length; i++) {
			newEntities[i] = entities[i];
		}
		newEntities[newEntities.length - 1] = e;
		entities = newEntities;
	}

	void cleanUp(){
		Entity[] newEntities = new Entity[0];
		for(int i = 0; i < entities.length; i++){
			if(entities[i] != null && entities[i].health >= 1){
				Entity[] arr = new Entity[newEntities.length + 1];
				for(int i2 = 0; i2 < newEntities.length; i2++){
					arr[i2] = newEntities[i2];
				}
				arr[arr.length - 1] = entities[i];
				newEntities = arr;
			}
			if(entities[i].health <= 0){
				// died, try to drop items
				if(entities[i].id == ID.TREE){
					int amount = randNum(1, 3); // amount of items to possibly drop
					for(int i2 = 0; i2 < amount; i2++){
						Entity[] arr = new Entity[newEntities.length + 1];
						for(int i3 = 0; i3 < newEntities.length; i3++){
							arr[i3] = newEntities[i3];
						}
						arr[arr.length - 1] = new Item(entities[i].position, ID.WOOD);
						newEntities = arr;
					}
				}
				if(entities[i].id == ID.ROCK){
					int amount = randNum(1, 3); // amount of items to possibly drop
					for(int i2 = 0; i2 < amount; i2++){
						Entity[] arr = new Entity[newEntities.length + 1];
						for(int i3 = 0; i3 < newEntities.length; i3++){
							arr[i3] = newEntities[i3];
						}
						arr[arr.length - 1] = new Item(entities[i].position, ID.STONE);
						newEntities = arr;
					}
				}
				if(entities[i].id == ID.COW){
					int amount = randNum(2, 5); // amount of items to possibly drop
					for(int i2 = 0; i2 < amount; i2++){
						Entity[] arr = new Entity[newEntities.length + 1];
						for(int i3 = 0; i3 < newEntities.length; i3++){
							arr[i3] = newEntities[i3];
						}
						arr[arr.length - 1] = new Item(entities[i].position, ID.FOOD);
						newEntities = arr;
					}
				}
				if(entities[i].id == ID.ORE){
					int amount = randNum(2, 6); // amount of items to possibly drop
					for(int i2 = 0; i2 < amount; i2++){
						Entity[] arr = new Entity[newEntities.length + 1];
						for(int i3 = 0; i3 < newEntities.length; i3++){
							arr[i3] = newEntities[i3];
						}
						arr[arr.length - 1] = new Item(entities[i].position, ID.OREITEM);
						newEntities = arr;
					}
				}
				if(entities[i].id == ID.EVAORE){
					int amount = randNum(1, 2); // amount of items to possibly drop
					for(int i2 = 0; i2 < amount; i2++){
						Entity[] arr = new Entity[newEntities.length + 1];
						for(int i3 = 0; i3 < newEntities.length; i3++){
							arr[i3] = newEntities[i3];
						}
						arr[arr.length - 1] = new Item(entities[i].position, ID.EVANITEORE);
						newEntities = arr;
					}
				}
				if(entities[i].id == ID.POISONSHROOM){
					int amount = randNum(1, 2); // amount of items to possibly drop
					for(int i2 = 0; i2 < amount; i2++){
						Entity[] arr = new Entity[newEntities.length + 1];
						for(int i3 = 0; i3 < newEntities.length; i3++){
							arr[i3] = newEntities[i3];
						}
						arr[arr.length - 1] = new Item(entities[i].position, ID.SHROOMS);
						newEntities = arr;
					}
				}
				if(entities[i].id == ID.WALL){
					Entity[] arr = new Entity[newEntities.length + 1];
					for(int i3 = 0; i3 < newEntities.length; i3++){
						arr[i3] = newEntities[i3];
					}
					arr[arr.length - 1] = new Item(entities[i].position, ID.ITEMWALL);
					newEntities = arr;
				}
				if(entities[i].id == ID.METALWALL){
					Entity[] arr = new Entity[newEntities.length + 1];
					for(int i3 = 0; i3 < newEntities.length; i3++){
						arr[i3] = newEntities[i3];
					}
					arr[arr.length - 1] = new Item(entities[i].position, ID.ITEMMWALL);
					newEntities = arr;
				}
				if(entities[i].id == ID.FLOOR){
					Entity[] arr = new Entity[newEntities.length + 1];
					for(int i3 = 0; i3 < newEntities.length; i3++){
						arr[i3] = newEntities[i3];
					}
					arr[arr.length - 1] = new Item(entities[i].position, ID.ITEMFLOOR);
					newEntities = arr;
				}
				if(entities[i].id == ID.CRYSORE){
					Entity[] arr = new Entity[newEntities.length + 1];
					for(int i3 = 0; i3 < newEntities.length; i3++){
						arr[i3] = newEntities[i3];
					}
					arr[arr.length - 1] = new Item(entities[i].position, ID.CRYSTAL);
					newEntities = arr;
				}
			}
		}
		entities = newEntities;

	}
	
	public Entity[] getEntities(){
		return entities;
	}

	public Player getPlayer(int player){
		if(player == 1){
			return playerPanels[0].getPlayer();
		}else if(currentSave.gameRules.numPlrs >= 2 && player == 2){
			return playerPanels[1].getPlayer();
		}else if(currentSave.gameRules.numPlrs >= 3 && player == 3){
			return playerPanels[2].getPlayer();
		}else if(currentSave.gameRules.numPlrs == 4 && player == 4){
			return playerPanels[3].getPlayer();
		}
		return null;
	}

	public static int clamp(int num, int min, int max){
		if(num < min) return min;
		if(num > max) return max;
		return num;
	}

	public static int randNum(int min, int max){
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}

	public double getDarkness(){
		double darkness = 0;
		double timePercent = (double)time / maxTime;
		if(timePercent >= 0 && timePercent < 0.25){
			darkness = 0;
		}
		if(timePercent >= 0.25 && timePercent < 0.5){
			darkness = ((1/.25) * timePercent - 1) * maxDarkness;
		}
		if(timePercent >= 0.5 && timePercent < 0.75){
			darkness = maxDarkness;
		}
		if(timePercent >= 0.75 && timePercent < 1){
			darkness = (-(1/0.25) * timePercent + 4) * maxDarkness;
		}
		return darkness;
	}

	private class ServerListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			long now = System.currentTimeMillis();
			elapsedTime = previousStartTime != -1 ? now - previousStartTime : 0;
			previousStartTime = now;
			for(PlayerPanel p: playerPanels) p.update(elapsedTime);
			spawnTimer += elapsedTime;
			if(spawnTimer >= spawnTime){
				spawnTimer = 0;
				if(entities.length < maxEntities){
					int objectType = randNum(0, 4);
					// Types:
					//0 - TREE
					//1 - ROCK
					//2 - COW
					//3 - ORE
					Point pos = new Point(randNum(0, 5000), randNum(0, 5000));
					Entity obj = new Entity(pos, 0, 1);
					switch(objectType){
					case 0:
						obj.id = ID.TREE;
						obj.health = 10;
						break;
					case 1:
						obj.id = ID.ROCK;
						obj.health = 50;
						break;
					case 2:
						obj.id = ID.COW;
						obj.health = 30;
						break;
					case 3:
						obj.id = ID.ORE;
						obj.health = 70;
						break;
					case 4:
						obj.id = ID.TREE;
						obj.health = 20;
						// add 4 trees surrounding
						addEntity(new Entity(new Point(pos.x - 25, pos.y - 25), ID.TREE, 10));
						addEntity(new Entity(new Point(pos.x + 25, pos.y - 25), ID.TREE, 10));
						addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.TREE, 10));
						addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.TREE, 10));
						break;
					}
					obj.maxHP = obj.health;
					addEntity(obj);
				}
				// cave items
				if(entities.length < maxEntities){
					Random rand = new Random();
					int objectType = rand.nextInt(5);
					// Types:
					//0 - ROCK
					//1 - ORE
					//2 - ROCK CLUSTER
					//3 - CRYSTAL
					//4 - ORE CLUSTER
					Point pos = new Point(rand.nextInt(5000) + 6000, rand.nextInt(5000));
					Entity obj = new Entity(pos, 0, 1);
					switch(objectType){
					case 0:
						obj.id = ID.ROCK;
						obj.health = 50;
						break;
					case 1:
						obj.id = ID.ORE;
						obj.health = 70;
						break;
					case 2:
						obj.id = ID.ROCK;
						obj.health = 50;
						addEntity(new Entity(new Point(pos.x - 25, pos.y - 25), ID.ROCK, 50));
						addEntity(new Entity(new Point(pos.x + 25, pos.y - 25), ID.ROCK, 50));
						addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.ROCK, 50));
						addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.ROCK, 50));
						break;
					case 3:
						obj.id = ID.CRYSORE;
						obj.health = 200;
						break;
					case 4:
						obj.id = ID.ORE;
						obj.health = 70;
						addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.ORE, 70));
						addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.ORE, 70));
						break;
					}
					obj.maxHP = obj.health;
					addEntity(obj);
				}
				// deep mine items
				if(entities.length < maxEntities){
					Random rand = new Random();
					int objectType = rand.nextInt(4);
					// Types:
					//0 - ROCK CLUSTER
					//1 - Crystal Ore
					//2 - Evanite Ore
					//3 - poison shrooms (not made yet)
					Point pos = new Point(rand.nextInt(5000), rand.nextInt(5000) + 6000);
					Entity obj = new Entity(pos, 0, 1);
					switch(objectType){
					case 0:
						obj.id = ID.ROCK;
						obj.health = 50;
						addEntity(new Entity(new Point(pos.x - 25, pos.y - 25), ID.ROCK, 50));
						addEntity(new Entity(new Point(pos.x + 25, pos.y - 25), ID.ROCK, 50));
						addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.ROCK, 50));
						addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.ROCK, 50));
						break;
					case 1:
						obj.id = ID.CRYSORE;
						obj.health = 200;
						break;
					case 2:
						obj.id = ID.EVAORE;
						obj.health = 500;
						break;
					case 3:
						obj.id = ID.POISONSHROOM;
						obj.health = 50;
						break;
					
					}
					obj.maxHP = obj.health;
					addEntity(obj);
				}
			}
			if(currentSave.gameRules.dayCycle) time += elapsedTime;
			if(time > maxTime) {
				time = 0;
				if(currentSave.gameRules.moveShop) shop.position = new Point(randNum(0, 5000), randNum(0, 5000));
			}
			//System.out.println("shopPos: " + shop.position.x + ", " + shop.position.y);
			cleanUp();
		}
	}
}// main class end
