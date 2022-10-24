import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
public class mainPanel extends JPanel { // main panel, gets inputs, holds title screen, & holds player panels

	mainPanel parent = this;
	
	Server server;
	PlayerPanel[] playerPanels;
	
	boolean titleScreen = true;
	int selected = 0;
	int screen = 0;
	final int[] maxSel = {2, 2, 2, 3, 3, 8, 7};
	Color selColor = new Color(255, 255, 255), unSelColor = new Color(150, 150, 150);
	
	int selSave = 0;
	boolean pvp = true, dayCycle = true, showCoords = false, hunger = true, moveShop = true, hardcore = false;
	int numPlrs = 1;

	//keybinds screen vars
	int plr = 1;
	Controls plr1Controls, plr2Controls, plr3Controls, plr4Controls;
	boolean keySet = false;

	public mainPanel() {
		setPreferredSize(new Dimension(512, 288));
		addKeyListener(new KeyListener());
		server = new Server();
		setFocusable(true);
		addMouseListener(new MouseListener());
		requestFocus();
		setBackground(Color.black);
		if(server.getControls(1) == null) plr1Controls = new Controls(87, 83, 68, 65, 88, 67);
		else plr1Controls = server.getControls(1);
		if(server.getControls(2) == null) plr2Controls = new Controls(38, 40, 39, 37, 47, 46);
		else plr2Controls = server.getControls(2);
		if(server.getControls(3) == null) plr3Controls = new Controls(73, 75, 76, 74, 59, 222);
		else plr3Controls = server.getControls(3);
		if(server.getControls(4) == null) plr4Controls = new Controls(84, 71, 72, 70, 66, 78);
		else plr4Controls = server.getControls(4);
		try{
			Scanner reader = new Scanner(new File("data/settings.txt"));
			Audio.enabled = reader.nextBoolean();
		}catch(Exception e){

		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(titleScreen){
			if(screen == 0){// Title screen
				// >>> Title <<<
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.setColor(Color.white);
				g.drawString("Survival Game", 0, 60);
				// ***

				// >>> Selections <<<
				g.setFont(new Font("Arial", Font.PLAIN, 20));

				// Play
				if(selected == 0) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Play", 0, 80);
				// Settings
				if(selected == 1) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Settings", 0, 100);
				// Quit
				if(selected == 2) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Quit", 0, 120);
				// ***
			}
			if(screen == 1){// Play screen
				// >>> Title <<<
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.setColor(Color.white);
				g.drawString("Play", 0, 60);
				// ***

				// >>> Selections <<<
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				
				// Load Game
				if(selected == 0) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Load Game", 0, 80);
				// New Game
				if(selected == 1) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("New Game", 0, 100);
				// Back
				if(selected == 2) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Back", 0, 120);
				// ***
			}
			if(screen == 2){// Settings screen
				// >>> Title <<<
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.setColor(Color.white);
				g.drawString("Settings", 0, 60);
				// ***

				// >>> Selections <<<
				g.setFont(new Font("Arial", Font.PLAIN, 20));

				// Keybinds
				if(selected == 0) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Keybinds", 0, 80);
				// Sounds: ON/OFF
				String soundsEnabled = "";
				if(Audio.enabled) soundsEnabled = "ON";
				else soundsEnabled = "OFF";
				if(selected == 1) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Sounds: " + soundsEnabled, 0, 100);
				// Back
				if(selected == 2) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Back", 0, 120);
				// ***
			}
			if(screen == 3 || screen == 4){// Load Game Screen OR New Game screen, they're the same just with different code
				// >>> Title <<<
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.setColor(Color.white);
				g.drawString("Save Select", 0, 60);
				//

				// >>> Selections <<<
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				if(selected == 0) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Save 1", 0, 80);
				if(selected == 1) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Save 2", 0, 100);
				if(selected == 2) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Save 3", 0, 120);
				if(selected == 3) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Back", 0, 140);
				//
			}
			if(screen == 5){// Player amount / Game settings select
				// >>> Title <<<
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.setColor(Color.white);
				g.drawString("Game Options", 0, 60);
				// ***

				// >>> Selections <<<
				g.setFont(new Font("Arial", Font.PLAIN, 20));

				if(selected == 0) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("PVP: " + pvp, 0, 80);
				if(selected == 1) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("DAYCYCLE: " + dayCycle, 0, 100);
				if(selected == 2) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("PLAYERS: " + numPlrs, 0, 120);
				if(selected == 3) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("SHOWCOORDS: " + showCoords, 0, 140);
				if(selected == 4) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("HUNGER: " + hunger, 0, 160);
				if(selected == 5) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("MOVESHOP: " + moveShop, 0, 180);
				if(selected == 6) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("HARDCORE: " + hardcore, 0, 200);
				if(selected == 7) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Start", 0, 220);
				if(selected == 8) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Cancel", 0, 240);
				// ***
			}
			if(screen == 6){// Keybinds screen
				// >>> Title <<<
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.setColor(Color.white);
				g.drawString("Keybinds", 0, 60);
				// ***

				// >>> Selections <<<
				g.setFont(new Font("Arial", Font.PLAIN, 20));

				// Player
				if(selected == 0) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Player: " + plr, 0, 80);
				// up
				if(selected == 1) g.setColor(selColor);
				else g.setColor(unSelColor);
				if(keySet && selected == 1) g.drawString("UP: -", 0, 100);
				else if(plr == 1) g.drawString("UP: " + KeyEvent.getKeyText(plr1Controls.UP), 0, 100);
				else if(plr == 2) g.drawString("UP: " + KeyEvent.getKeyText(plr2Controls.UP), 0, 100);
				else if(plr == 3) g.drawString("UP: " + KeyEvent.getKeyText(plr3Controls.UP), 0, 100);
				else if(plr == 4) g.drawString("UP: " + KeyEvent.getKeyText(plr4Controls.UP), 0, 100);
				
				// down
				if(selected == 2) g.setColor(selColor);
				else g.setColor(unSelColor);
				if(keySet && selected == 2) g.drawString("DOWN: -", 0, 120);
				else if(plr == 1) g.drawString("DOWN: " + KeyEvent.getKeyText(plr1Controls.DOWN), 0, 120);
				else if(plr == 2) g.drawString("DOWN: " + KeyEvent.getKeyText(plr2Controls.DOWN), 0, 120);
				else if(plr == 3) g.drawString("DOWN: " + KeyEvent.getKeyText(plr3Controls.DOWN), 0, 120);
				else if(plr == 4) g.drawString("DOWN: " + KeyEvent.getKeyText(plr4Controls.DOWN), 0, 120);
				// right
				if(selected == 3) g.setColor(selColor);
				else g.setColor(unSelColor);
				if(keySet && selected == 3) g.drawString("RIGHT: -", 0, 140);
				else if(plr == 1) g.drawString("RIGHT: " + KeyEvent.getKeyText(plr1Controls.RIGHT), 0, 140);
				else if(plr == 2) g.drawString("RIGHT: " + KeyEvent.getKeyText(plr2Controls.RIGHT), 0, 140);
				else if(plr == 3) g.drawString("RIGHT: " + KeyEvent.getKeyText(plr3Controls.RIGHT), 0, 140);
				else if(plr == 4) g.drawString("RIGHT: " + KeyEvent.getKeyText(plr4Controls.RIGHT), 0, 140);
				// left
				if(selected == 4) g.setColor(selColor);
				else g.setColor(unSelColor);
				if(keySet && selected == 4) g.drawString("LEFT: -", 0, 160);
				else if(plr == 1) g.drawString("LEFT: " + KeyEvent.getKeyText(plr1Controls.LEFT), 0, 160);
				else if(plr == 2) g.drawString("LEFT: " + KeyEvent.getKeyText(plr2Controls.LEFT), 0, 160);
				else if(plr == 3) g.drawString("LEFT: " + KeyEvent.getKeyText(plr3Controls.LEFT), 0, 160);
				else if(plr == 4) g.drawString("LEFT: " + KeyEvent.getKeyText(plr4Controls.LEFT), 0, 160);
				// a
				if(selected == 5) g.setColor(selColor);
				else g.setColor(unSelColor);
				if(keySet && selected == 5) g.drawString("A: -", 0, 180);
				else if(plr == 1) g.drawString("A: " + KeyEvent.getKeyText(plr1Controls.A), 0, 180);
				else if(plr == 2) g.drawString("A: " + KeyEvent.getKeyText(plr2Controls.A), 0, 180);
				else if(plr == 3) g.drawString("A: " + KeyEvent.getKeyText(plr3Controls.A), 0, 180);
				else if(plr == 4) g.drawString("A: " + KeyEvent.getKeyText(plr4Controls.A), 0, 180);
				// b
				if(selected == 6) g.setColor(selColor);
				else g.setColor(unSelColor);
				if(keySet && selected == 6) g.drawString("B: -", 0, 200);
				else if(plr == 1) g.drawString("B: " + KeyEvent.getKeyText(plr1Controls.B), 0, 200);
				else if(plr == 2) g.drawString("B: " + KeyEvent.getKeyText(plr2Controls.B), 0, 200);
				else if(plr == 3) g.drawString("B: " + KeyEvent.getKeyText(plr3Controls.B), 0, 200);
				else if(plr == 4) g.drawString("B: " + KeyEvent.getKeyText(plr4Controls.B), 0, 200);
				// back
				if(selected == 7) g.setColor(selColor);
				else g.setColor(unSelColor);
				g.drawString("Back", 0, 220);
				// ***
			}
		}
	}
	
	
	
	
	
	// internal classes ********************************
	private class KeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			// title screen controls
			if(titleScreen) {
				if(keySet){
					if(plr == 1){
						switch(selected){
						case 1:
							plr1Controls.UP = key;
							break;
						case 2:
							plr1Controls.DOWN = key;
							break;
						case 3:
							plr1Controls.RIGHT = key;
							break;
						case 4:
							plr1Controls.LEFT = key;
							break;
						case 5:
							plr1Controls.A = key;
							break;
						case 6:
							plr1Controls.B = key;
							break;
						}
					}
					if(plr == 2){
						switch(selected){
						case 1:
							plr2Controls.UP = key;
							break;
						case 2:
							plr2Controls.DOWN = key;
							break;
						case 3:
							plr2Controls.RIGHT = key;
							break;
						case 4:
							plr2Controls.LEFT = key;
							break;
						case 5:
							plr2Controls.A = key;
							break;
						case 6:
							plr2Controls.B = key;
							break;
						}
					}
					if(plr == 3){
						switch(selected){
						case 1:
							plr3Controls.UP = key;
							break;
						case 2:
							plr3Controls.DOWN = key;
							break;
						case 3:
							plr3Controls.RIGHT = key;
							break;
						case 4:
							plr3Controls.LEFT = key;
							break;
						case 5:
							plr3Controls.A = key;
							break;
						case 6:
							plr3Controls.B = key;
							break;
						}
					}
					if(plr == 4){
						switch(selected){
						case 1:
							plr4Controls.UP = key;
							break;
						case 2:
							plr4Controls.DOWN = key;
							break;
						case 3:
							plr4Controls.RIGHT = key;
							break;
						case 4:
							plr4Controls.LEFT = key;
							break;
						case 5:
							plr4Controls.A = key;
							break;
						case 6:
							plr4Controls.B = key;
							break;
						}
					}
					keySet = false;
					repaint();
					return;
				}
				// 38 = up
				// 40 = down
				if(key == 38){
					selected--;
					try{
						Audio.main("data/sounds/moveSel.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
				}
				if(key == 40){
					selected++;
					try{
						Audio.main("data/sounds/moveSel.wav");
					}catch(Exception ex){
						ex.printStackTrace();
						System.exit(1);
					}
				}
				if(selected < 0) selected = maxSel[screen];
				if(selected > maxSel[screen]) selected = 0;
				if(key == 32){
					// select
					/*
					0 - Title screen
					1 - Play screen
					2 - Settings screen
					3 - Load Game screen
					4 - New Game screen
					5 - Player amount select
					6 - Keybinds screen
					*/
					if(screen == 0){
						// title screen
						switch(selected){
						case 0:
							// play screen
							screen = 1; // play screen
							try{
								Audio.main("data/sounds/select.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 1:
							// settings
							screen = 2; // settings screen
							selected = 0; // reset
							try{
								Audio.main("data/sounds/select.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 2:
							// quit
							System.exit(0);
							try{
								Audio.main("data/sounds/select.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						
						}
					}
					else if(screen == 1){
						// play screen
						//Load Game
						//New Game
						//Back
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						switch(selected){
						case 0:
							// load game
							screen = 3;
							break;
						case 1:
							// new game
							screen = 4;
							selected = 0;
							break;
						case 2:
							// back
							screen = 0;
							selected = 0;
							break;
						}
					}
					else if(screen == 2){
						// settings screen
						//Keybinds
						//Back
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						switch(selected){
						case 0:
							//keybinds
							screen = 6;
							break;
						case 1:
							Audio.enabled = !Audio.enabled;
							break;
						case 2:
							//back
							screen = 0;
							selected = 0;
							server.saveControls(plr1Controls, plr2Controls, plr3Controls, plr4Controls);
							break;
						}
					}
					else if(screen == 3){
						// load game screen
						//Save 1
						//Save 2
						//Save 3
						//Back
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						switch(selected){
						case 0:
							if(server.getSave(1) != null){
								server.setSave(1);
								if(server.currentSave.gameRules.numPlrs == 1) parent.setLayout(new GridLayout(1, 1));
								else if(server.currentSave.gameRules.numPlrs == 2) parent.setLayout(new GridLayout(2, 1));
								else parent.setLayout(new GridLayout(2, 2));
								playerPanels = new PlayerPanel[server.currentSave.gameRules.numPlrs];
								playerPanels[0] = new PlayerPanel(plr1Controls, server.currentSave.plr1, server);
								parent.add(playerPanels[0]);
								if(server.currentSave.gameRules.numPlrs >= 2){
									playerPanels[1] = new PlayerPanel(plr2Controls, server.currentSave.plr2, server);
									parent.add(playerPanels[1]);
								}
								if(server.currentSave.gameRules.numPlrs >= 3){
									playerPanels[2] = new PlayerPanel(plr3Controls, server.currentSave.plr3, server);
									parent.add(playerPanels[2]);
								}
								if(server.currentSave.gameRules.numPlrs == 4){
									playerPanels[3] = new PlayerPanel(plr4Controls, server.currentSave.plr4, server);
									parent.add(playerPanels[3]);
								}
								if(server.currentSave.gameRules.numPlrs <= 2) parent.setPreferredSize(new Dimension(512, 288 * server.currentSave.gameRules.numPlrs));
								else parent.setPreferredSize(new Dimension(512 * 2, 288 * 2));
								GUI.pack();
								titleScreen = false;
								server.setPlayerPanels(playerPanels);
								server.start();
							}else System.out.println("no save");
							break;
						case 1:
							if(server.getSave(2) != null){
								server.setSave(2);
								if(server.currentSave.gameRules.numPlrs == 1) parent.setLayout(new GridLayout(1, 1));
								else if(server.currentSave.gameRules.numPlrs == 2) parent.setLayout(new GridLayout(2, 1));
								else parent.setLayout(new GridLayout(2, 2));
								playerPanels = new PlayerPanel[server.currentSave.gameRules.numPlrs];
								playerPanels[0] = new PlayerPanel(plr1Controls, server.currentSave.plr1, server);
								parent.add(playerPanels[0]);
								if(server.currentSave.gameRules.numPlrs == 2){
									playerPanels[1] = new PlayerPanel(plr2Controls, server.currentSave.plr2, server);
									parent.add(playerPanels[1]);
								}
								if(server.currentSave.gameRules.numPlrs >= 3){
									playerPanels[2] = new PlayerPanel(plr3Controls, server.currentSave.plr3, server);
									parent.add(playerPanels[2]);
								}
								if(server.currentSave.gameRules.numPlrs == 4){
									playerPanels[3] = new PlayerPanel(plr4Controls, server.currentSave.plr4, server);
									parent.add(playerPanels[3]);
								}
								if(server.currentSave.gameRules.numPlrs <= 2) parent.setPreferredSize(new Dimension(512, 288 * server.currentSave.gameRules.numPlrs));
								else parent.setPreferredSize(new Dimension(512 * 2, 288 * 2));
								GUI.pack();
								titleScreen = false;
								server.setPlayerPanels(playerPanels);
								server.start();
							}
							break;
						case 2:
							if(server.getSave(3) != null){
								server.setSave(3);
								if(server.currentSave.gameRules.numPlrs == 1) parent.setLayout(new GridLayout(1, 1));
								else if(server.currentSave.gameRules.numPlrs == 2) parent.setLayout(new GridLayout(2, 1));
								else parent.setLayout(new GridLayout(2, 2));
								playerPanels = new PlayerPanel[server.currentSave.gameRules.numPlrs];
								playerPanels[0] = new PlayerPanel(plr1Controls, server.currentSave.plr1, server);
								parent.add(playerPanels[0]);
								if(server.currentSave.gameRules.numPlrs == 2){
									playerPanels[1] = new PlayerPanel(plr2Controls, server.currentSave.plr2, server);
									parent.add(playerPanels[1]);
								}
								if(server.currentSave.gameRules.numPlrs >= 3){
									playerPanels[2] = new PlayerPanel(plr3Controls, server.currentSave.plr3, server);
									parent.add(playerPanels[2]);
								}
								if(server.currentSave.gameRules.numPlrs == 4){
									playerPanels[3] = new PlayerPanel(plr4Controls, server.currentSave.plr4, server);
									parent.add(playerPanels[3]);
								}
								if(server.currentSave.gameRules.numPlrs <= 2) parent.setPreferredSize(new Dimension(512, 288 * server.currentSave.gameRules.numPlrs));
								else parent.setPreferredSize(new Dimension(512 * 2, 288 * 2));
								GUI.pack();
								titleScreen = false;
								server.setPlayerPanels(playerPanels);
								server.start();
							}
							break;
						case 3:
							screen = 0; 
							selected = 0;
							break;
						}
					}
					else if(screen == 4){
						// New game screen
						//Save 1
						//Save 2
						//Save 3
						//back
						try{
							Audio.main("data/sounds/select.wav");
						}catch(Exception ex){
							ex.printStackTrace();
							System.exit(1);
						}
						switch(selected){
						case 0:
							selSave = 1;
							screen = 5;
							selected = 0;
							break;
						case 1:
							selSave = 2;
							screen = 5;
							selected = 0;
							break;
						case 2:
							selSave = 3;
							screen = 5;
							selected = 0;
							break;
						case 3:
							screen = 0;
							selected = 0;
							break;
						}
					}
					else if(screen == 5){
						// player amount/gamerule select
						//PVP: -
						//DAYCYCLE: -
						//PLAYERS: -
						//SHOWCOORDS: -
						//HUNGER: -
						//MOVESHOP: -
						//HARDCORE: -
						//Start
						//Cancel
						switch(selected){
						case 0:
							pvp = !pvp;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 1:
							dayCycle = !dayCycle;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 2:
							if(numPlrs != 4) numPlrs++;
							else numPlrs = 1;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 3:
							showCoords = !showCoords;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 4:
							hunger = !hunger;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 5:
							moveShop = !moveShop;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 6:
							hardcore = !hardcore;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						case 7:
							Save newSave = new Save(new GameRules(numPlrs, pvp, dayCycle, showCoords, hunger, moveShop, hardcore), new Player(new Point(2500, 2500), Color.blue), new Player(new Point(2550, 2500), Color.orange), new Player(new Point(2500, 2550), Color.pink), new Player(new Point(2550, 2550), Color.cyan), new Entity[0], 0);
							server.save(newSave, selSave);
							server.setSave(selSave, newSave);
							if(numPlrs == 1) parent.setLayout(new GridLayout(1, 1));
							else if(numPlrs == 2) parent.setLayout(new GridLayout(2, 1));
							else parent.setLayout(new GridLayout(2, 2));
							playerPanels = new PlayerPanel[numPlrs];
							playerPanels[0] = new PlayerPanel(plr1Controls, server.currentSave.plr1, server);
							parent.add(playerPanels[0]);
							if(numPlrs >= 2){
								playerPanels[1] = new PlayerPanel(plr2Controls, server.currentSave.plr2, server);
								parent.add(playerPanels[1]);
							}
							if(numPlrs >= 3){
								playerPanels[2] = new PlayerPanel(plr3Controls, server.currentSave.plr3, server);
								parent.add(playerPanels[2]);
								System.out.println("added panel 3");
							}
							if(numPlrs == 4){
								playerPanels[3] = new PlayerPanel(plr4Controls, server.currentSave.plr4, server);
								parent.add(playerPanels[3]);
								System.out.println("added panel 4");
							}
							if(numPlrs <= 2) parent.setPreferredSize(new Dimension(512, 288 * server.currentSave.gameRules.numPlrs));
							else parent.setPreferredSize(new Dimension(512 * 2, 288 * 2));
							GUI.pack();
							titleScreen = false;
							server.setPlayerPanels(playerPanels);
							for(int i = 0; i < 250; i++){
								Random rand = new Random();
								int objectType = rand.nextInt(5);
								// Types:
								//0 - TREE
								//1 - ROCK
								//2 - COW
								//3 - ORE
								//4 - FOREST
								Point pos = new Point(rand.nextInt(5000), rand.nextInt(5000));
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
									obj.health = 10;
									// add 4 trees surrounding
									server.addEntity(new Entity(new Point(pos.x - 25, pos.y - 25), ID.TREE, 10));
									server.addEntity(new Entity(new Point(pos.x + 25, pos.y - 25), ID.TREE, 10));
									server.addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.TREE, 10));
									server.addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.TREE, 10));
									break;
								}
								obj.maxHP = obj.health;
								server.addEntity(obj);
							}
							// cave items
							for(int i = 0; i < 250; i++){
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
									server.addEntity(new Entity(new Point(pos.x - 25, pos.y - 25), ID.ROCK, 50));
									server.addEntity(new Entity(new Point(pos.x + 25, pos.y - 25), ID.ROCK, 50));
									server.addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.ROCK, 50));
									server.addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.ROCK, 50));
									break;
								case 3:
									obj.id = ID.CRYSORE;
									obj.health = 200;
									break;
								case 4:
									obj.id = ID.ORE;
									obj.health = 70;
									server.addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.ORE, 70));
									server.addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.ORE, 70));
									break;
								}
								obj.maxHP = obj.health;
								server.addEntity(obj);
							}
							// deep mine items
							for(int i = 0; i < 250; i++){
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
									server.addEntity(new Entity(new Point(pos.x - 25, pos.y - 25), ID.ROCK, 50));
									server.addEntity(new Entity(new Point(pos.x + 25, pos.y - 25), ID.ROCK, 50));
									server.addEntity(new Entity(new Point(pos.x - 25, pos.y + 25), ID.ROCK, 50));
									server.addEntity(new Entity(new Point(pos.x + 25, pos.y + 25), ID.ROCK, 50));
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
								server.addEntity(obj);
							}
							newSave.entities = server.getEntities();
							server.setSave(selSave, newSave);
							server.save();
							server.start();
							try{
								Audio.main("data/sounds/select.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						case 8:
							screen = 4;
							selected = 0;
							try{
								Audio.main("data/sounds/select.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
							break;
						}
					}
					else if(screen == 6){
						// Keybinds screen
						//Player: <1/2/3/4>
						//UP: -
						//DOWN: -
						//RIGHT: -
						//LEFT: -
						//A: -
						//B: -
						//Back
						if(selected == 0){
							if(plr != 4) plr++;
							else plr = 1;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}else if(selected >= 1 && selected < maxSel[6]){
							keySet = true;
							try{
								Audio.main("data/sounds/moveSel.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}else if(selected == 7){
							screen = 2;
							selected = 0;
							try{
								Audio.main("data/sounds/select.wav");
							}catch(Exception ex){
								ex.printStackTrace();
								System.exit(1);
							}
						}
					}
				}
				repaint();
				return;
			}
			
			
			// controls to send to player panels
			if(!titleScreen && playerPanels != null) for(PlayerPanel p: playerPanels) p.updateControls(key, true);
			
			
		}
		
		public void keyReleased(KeyEvent e) {
			if(!titleScreen && playerPanels != null) for(PlayerPanel p: playerPanels) p.updateControls(e.getKeyCode(), false);
		}
	}

	private class MouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			requestFocus();
		}
	}
}
