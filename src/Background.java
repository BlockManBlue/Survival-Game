import java.awt.*;
import javax.swing.*;
public class Background extends Entity {
    ImageIcon image;
    public Background(ImageIcon i){
        super(new Point(0, 0), ID.BACKGROUND, 1);
        image = i;
        image.setImage(image.getImage().getScaledInstance(5000, 5000, Image.SCALE_DEFAULT));
    }
}
