import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel{
    private float opacity;
    public StartScreen(){
        super();
        setVisible(true);
        setLayout(new GridLayout(1,1));
        JButton begin = new JButton("Time to Wave Goodbye");
        begin.setOpaque(false);
        begin.setEnabled(true);
        add(begin);
        begin.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.startGame();
                setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
    public void paint(Graphics g) {
        Graphics2D ga = (Graphics2D) g;
        ga.clearRect(0, 0, this.getWidth(), this.getHeight());
        ga.setPaint(Color.BLACK);
        ga.fillRect(0, 0, this.getWidth(), this.getHeight());
        if(isVisible()){
            opacity = Math.min(1f, opacity+0.05f);
        }
        else{
            opacity = 0f;
        }
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        try {
            BufferedImage image = ImageIO.read(new File("assets\\TitleCover.png"));
            //AffineTransform at = AffineTransform.getScaleInstance(Main.frame.getWidth(), Main.frame.getHeight());
            //ga.drawRenderedImage(image, at);
            ga.drawImage(resize(image, Main.frame.getWidth(), Main.frame.getHeight()), 0, 0, null);
            //System.out.println("resized to " + Main.frame.getWidth() + ", " + Main.frame.getHeight());
        }
        catch(IOException e){
            System.out.println("Missing TitleCover.png file in assets folder");
        }
        //System.out.println("drew stuff");
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.41f));
    }
    public  BufferedImage resize(BufferedImage image, int width, int height) {
        int type=0;
        type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height,type);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

}
