import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayDeque;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import java.util.ArrayList;

public class Main{
    public static Game game = new Game();
    public static JFrame frame = new JFrame("SpaceWave");
    public static StartScreen screen;
    public static int fps = 20;
    public static Timer timer;
    public static Space space;
    public static void main(String[] args) {
        frame.getContentPane().setBackground(Color.BLACK);
        screen = new StartScreen();
        frame.add(screen);
        game.setVisible(false);
        frame.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                System.out.println("componentResized");
                if(game.isVisible()) {
                    game.music.stopSound();
                    game = new Game();
                    startGame();
                }
                game.setSize(frame.getWidth(), frame.getHeight());
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(800, 600);
        timer = new Timer(1000/fps, new Animate());
        timer.start();
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);
        final Sound wavesound = new Sound("assets\\music\\WaveSound.wav");
        final Sound emptywavesound = new Sound("assets\\music\\EmptyWaveSound.wav");

        game.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("Clicked at " + e.getX() + ", " + e.getY());
                if (game.energy.getFuel() > 20) {
                    game.energy.incrementFuel(-20);
                    //game.addWave(new Wave(e.getX() - game.getOffset(), e.getY(), frame.getHeight() / 50));
                    wavesound.playSoundOnce();
                    game.addWave(new Wave(e.getX(), e.getY(), frame.getHeight() / 50));
                } else {
                    //game.addEmptyWave(new EmptyWave(e.getX() - game.getOffset(), e.getY(), frame.getHeight() / 50));
                    emptywavesound.playSoundOnce();
                    game.addEmptyWave(new EmptyWave(e.getX(), e.getY(), frame.getHeight() / 50));
                }
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
    public static void startGame(){
        frame.add(game);
        game.start();
    }
}

