import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Animate implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Main.game.step();
        Main.timer = new Timer(1000/Main.fps, new Animate());
    }
}
