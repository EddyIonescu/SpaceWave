import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemies extends Game{
    public Enemies(){
        super();
    }
    public void addEnemy(int playery){
        playery -= 0.25*Main.game.activeitems.get(0).getHeight();
        int index = (int)(Math.random()*Main.game.obstaclefiles.size());
        System.out.println("size is " + Main.game.obstacles[index].size());
        Main.game.activeitems.add(new Item(Main.game.getWidth()*1.3, (Math.random()*0.8+0.6)*(double)playery,
                Main.game.getWidth() / 20, Main.game.getHeight() / 20, Main.game.getWidth()/-1200.0*(index+1),
                (int) (Math.random() * index / 2), Main.game.obstacles[index]));
            System.out.println("added enemy; playery is " + playery + " and xspeed is " + (-2.0/3*(index+1)));
    }

}
