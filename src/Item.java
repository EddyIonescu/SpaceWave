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

public class Item extends JPanel{
    protected double xspeed; //pixels per 50ms
    protected double yspeed;
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected double mass = 2.5;
    protected double count = 0.0;
    protected ArrayList<BufferedImage> animatedimage = new ArrayList<BufferedImage>();
    public Item(double x, double y, int width, int height, double xspeed, double yspeed, String filepath){
        super();
        System.out.println("width1:" + width);
        this.x = x + width/2;
        this.y = y + height/2;
        this.width = width;
        this.height = height;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        try { animatedimage = getFrames(new File(filepath)); }
        catch(IOException e){ System.out.println(filepath + " is invalid. Cannot create item"); }
        repaint();
    }
    public Item(double x, double y, int width, int height, double xspeed, double yspeed, ArrayList<BufferedImage> frames){
        super();
        System.out.println("width1:" + width);
        this.x = x + width/2;
        this.y = y + height/2;
        this.width = width;
        this.height = height;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        animatedimage = frames;
        //System.out.println(animatedimage.size() + " FRAMES");
        repaint();
    }
    public double getx(){
        return x;
    }
    public double gety(){
        return y;
    }
    public double getXspeed(){
        return xspeed;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void draw(Graphics ga){
        ga.drawImage(animatedimage.get((int)count), (int) x - width / 2, (int) y - height / 2, null);
        count += 0.5;
        count %= animatedimage.size();
    }
    public boolean candrawandstep(){
     //   if(x<30 || x>800) xspeed*=-1;
       // if(y<30 || y>800) yspeed *= -1;
        if(y<Main.frame.getHeight()/7 && mass>2) yspeed *= -1;
        x += 2*xspeed;
        y += 2*yspeed;
        if((x<0 || x>2*Main.game.getWidth()) || (y<0 || y>Main.game.getHeight()))
            return false;
        else
            return true;
    }
    public void force(Vector vector){ //between 0 and 1
        double acceleration = vector.getMagnitude()/mass;
        //xspeed += (Math.abs(Main.game.getWidth()-x)/(Main.game.getWidth()*0.2))*acceleration*Math.cos(vector.getDirection());
        //yspeed += (Math.abs(Main.game.getHeight()-y)/(Main.game.getHeight()*0.2))*acceleration*Math.sin(vector.getDirection());
        xspeed += acceleration*Math.cos(vector.getDirection());
        if(acceleration>0)
            System.out.println("direction is " + (180.0/3.14*vector.getDirection()) + " and mag is " + vector.getMagnitude());
        yspeed += acceleration*Math.sin(vector.getDirection());
    }
    public ArrayList<BufferedImage> getFrames(File gif) throws IOException {
        if(gif.getName().contains("png")){
            ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();
            list.add(resize(ImageIO.read(gif), this.width, this.height));
            return list;
        }
        ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
        ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
        ir.setInput(ImageIO.createImageInputStream(gif));
        for(int i = 0; i < ir.getNumImages(true); i++) {
            frames.add(ir.read(i));
            frames.set(i, resize(frames.get(i), this.width, this.height));
        }
        ir.dispose();
        return frames;
    }
    public BufferedImage resize(BufferedImage image, int width, int height) {
        int type=0;
        type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height,type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
