import javax.swing.*;
import java.awt.*;

public class Energy extends JPanel {
    private double fuel;
    public Energy(){
        super();
        fuel = 100;
    }
    public void paint(Graphics2D ga){
        //TODO: draw line grid showing increments for making a wave
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        LinearGradientPaint rgp = new LinearGradientPaint((float)(Main.game.getWidth()/2), (float)(Main.frame.getHeight()/10),
                (float)(Main.frame.getWidth()*0.7), (float)(Main.frame.getHeight()/6), new float[]{0.1f, 0.9f},
                new Color[]{Color.DARK_GRAY,
                Color.WHITE},
                MultipleGradientPaint.CycleMethod.NO_CYCLE);
        ga.setPaint(rgp);
        ga.fillRect(0, (Main.frame.getHeight()/20),
                (int)(Main.frame.getWidth()/140.0*fuel), Main.frame.getHeight()/20);
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
    }
    public void incrementFuel(double add){
        fuel = Math.max(0, Math.min(100, fuel+add));
    }
    public double getFuel(){
        return fuel;
    }
}

