import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Eddy on 2015-06-10.
 */
public class TimeDisplay extends JPanel {
    public int highscore = 0;
    public TimeDisplay(){
        super();
        readScore();
    }
    public void paint(Graphics2D ga){
        int seconds = (int)(Main.game.stopwatch.elapsedTime()/1000);
        Font f = new Font("Arial", Font.BOLD, Main.frame.getWidth()/50);
        ga.setFont(f);
        ga.drawString(seconds + "   High Score: " + highscore,
                (int)(Main.game.getWidth()*0.77), Main.frame.getHeight()/11);
    }
    public void readScore(){
        try{
            BufferedReader in = new BufferedReader(new FileReader("highscore.txt"));
            highscore = Integer.parseInt(in.readLine());
            in.close();
        }
        catch(Exception ex){
            System.out.println("Error reading highscores file");
        }
    }
}
