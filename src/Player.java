import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class Player extends Item{
    public Player(double x, double y, int width, int height, double xspeed, double yspeed, String filepath) {
        super(x, y, width, height, xspeed, yspeed, filepath);
        System.out.println("width1:" + width);
        this.x = x + width / 2;
        this.y = y + height / 2;
        this.width = width;
        this.height = height;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        mass = 0.8;
        try {
            animatedimage = getFrames(new File(filepath));
        } catch (IOException e) {
            System.out.println(filepath + " is invalid. Cannot create item");
        }
        repaint();
    }
    public void reset(){
        x = Main.frame.getWidth()/2;
        y = Main.frame.getHeight()/2;
        xspeed = 0;
        yspeed = 0;
    }
    public void collision(Energy energy){
        if((y<0 || y>Main.frame.getHeight()) || (x<0 || x>Main.frame.getWidth())){
            System.out.println("You lose");
            Main.game.GameOver();
            reset();
        }
        xspeed *= 0.98;
        yspeed *= 0.98;
        /*
        //bouncing
        if(energy.getFuel()<=0) {
            System.out.println("You lose");
            Main.game.GameOver();
        }
        if(y<0 || y>Main.game.getHeight()){
            yspeed *= -1;
        }
        if(x<0 || x>Main.game.getWidth()){
            xspeed *= -1;
        }
        */
        Rectangle r = new Rectangle((int)(x-width/4), (int)(y-height/4), width/2, height/2);
        for(int i = 1; i<Main.game.activeitems.size(); i++){
            Item item = Main.game.activeitems.get(i);
            Rectangle r1 = new Rectangle((int)item.getx(), (int)item.gety(), item.getWidth(), item.getHeight());
            if(r.intersects(r1)){
                System.out.print(" Collision ");
                energy.incrementFuel(-3);
            }
        }
    }
    public void draw(Graphics ga){
        //Rectangle r = new Rectangle((int)(x+width/4), (int)(y+height/4), width/2, height/2);
        //ga.setColor(Color.red);
        //ga.drawRect((int) (x - width / 4), (int) (y - height / 4), width / 2, height / 2);
        ga.drawImage(animatedimage.get((int) count), (int) x - width / 2, (int) y - height / 2, null);
        count+=0.01; count %= animatedimage.size();
        //ga.setColor(Color.red);
        //ga.drawOval((int)x, (int)y, 20,20);
    }
}
