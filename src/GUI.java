import javax.swing.*;
public class GUI {
	private static JFrame window;

	public static void main(String[] args) {
		window = new JFrame("Survival Game");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		// panel
		window.add(new mainPanel());
		//
		window.pack();
		window.setLocation((1920 / 2) - (512 / 2), 150);
		window.setVisible(true);
	}
	
	public static void pack() {
		window.pack();
	}

}
   