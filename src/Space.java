import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Eddy on 2015-06-10.
 */
public class Space extends JPanel {
    private Image image;
    public Space(){
        super();
        image = Toolkit.getDefaultToolkit().createImage("assets\\space.gif");
        image = image.getScaledInstance(Main.frame.getWidth(), Main.frame.getHeight(), 0);
    }
    public void paint(Graphics2D ga){
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        ga.drawImage(image, 0, 0, null);
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
    }
}
