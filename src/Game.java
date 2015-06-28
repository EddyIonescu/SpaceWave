import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


public class Game extends JPanel {
    private ArrayList<Wave> waves = new ArrayList<Wave>();
    private ArrayList<EmptyWave> emptyWaves = new ArrayList<EmptyWave>();
    protected ArrayList<Item> activeitems = new ArrayList<Item>();
    protected ArrayList<String> obstaclefiles = new ArrayList<String>();
    protected ArrayList<String> playerfiles = new ArrayList<String>();
    public ArrayList<BufferedImage>[] obstacles = new ArrayList[8];
    public Stopwatch stopwatch;
    private int offset;
    public Enemies enemies;
    public Energy energy;
    public TimeDisplay display;
    public Sound music = new Sound("assets\\music\\Frozen Star.wav");
    public Game(){
        super();
        recurse(0);
        setVisible(false);
        this.setBackground(Color.black);
    }
    public void recurse(int i){
        if(i==8) return;
        obstacles[i] = new ArrayList<BufferedImage>();
        recurse(i+1);
    }
    public void GameOver(){
        music.playSound();
        int score = (int)(stopwatch.elapsedTime()/1000);
        int current = 0;
        energy.incrementFuel(100.0);
        try{
            BufferedReader in = new BufferedReader(new FileReader("highscore.txt"));
            current = Integer.parseInt(in.readLine());
            in.close();
            if(score>current){
                BufferedWriter out = new BufferedWriter(new FileWriter("highscore.txt"));
                out.write(Integer.toString(score));
                out.close();
            }
        }
        catch(Exception ex){
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("highscore.txt"));
                out.write(Integer.toString(score));
                out.close();
            }
            catch(Exception ex1){
                System.out.println("Error creating file");
            }
        }
        display.readScore();
        stopwatch = new Stopwatch();

    }
    public void start(){
        waves = new ArrayList<Wave>();
        emptyWaves = new ArrayList<EmptyWave>();
        activeitems = new ArrayList<Item>();
        obstaclefiles = new ArrayList<String>();
        playerfiles = new ArrayList<String>();
        offset = 0;
        enemies = new Enemies();
        stopwatch = new Stopwatch();
        energy = new Energy();
        display = new TimeDisplay();
        loaditems(8, 1);
        Main.space = new Space();
        setVisible(true); System.out.println("width" + Main.frame.getWidth());
        Player p = new Player(Math.max(200, Main.frame.getWidth() / 2), Math.max(200, Main.frame.getHeight() / 2), Main.frame.getHeight() / 10,
                Main.frame.getHeight() / 10, 0, 0, playerfiles.get(0));
        p.reset();
        addItem(p);
        music.playSound();
    }
    public void loaditems(int nobstacles, int nplayers){
        for(int i = 0; i<nobstacles; i++){
            obstaclefiles.add("assets\\obstacles\\obstacle" + Integer.toString(i+1) + ".gif");
            try {
                obstacles[i] = getFrames(new File(obstaclefiles.get(i)));
                System.out.println("preloaded gif");
            }
            catch(IOException e){
                System.out.println("Error preloading gifs");
            }
        }
        for(int i = 0; i<nplayers; i++){
            playerfiles.add("assets\\players\\player" + Integer.toString(i+1) + ".png");
        }
    }
    public ArrayList<BufferedImage> getFrames(File gif) throws IOException {
        if(gif.getName().contains("png")){
            ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();
            list.add(ImageIO.read(gif));
            return list;
        }
        ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
        ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
        ir.setInput(ImageIO.createImageInputStream(gif));
        for(int i = 0; i < ir.getNumImages(true); i++) {
            frames.add(ir.read(i));
            //frames.set(i, frames.get(i));
            frames.set(i, resize(frames.get(i), Main.frame.getHeight() / 10,
                    Main.frame.getHeight() / 10));
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
    public int getOffset(){
        return offset;
    }
    public void step(){
        if(isVisible()){
            if(energy.getFuel()<=2){
                System.out.println("You lose");
                Main.game.GameOver();
                Player p = (Player)activeitems.get(0);
                p.reset();
            }
            energy.incrementFuel(0.5);
            if (activeitems.size() > 0)
                offset = -1 * (int) (activeitems.get(0).x - Main.frame.getWidth() / 2);
            for (Wave wave : waves) {
                wave.increment();
                for (Item item : activeitems) {
                    item.force(wave.calculateforce(item.getx() + item.getWidth() / 2, item.gety() + item.getHeight() / 2));
                }
            }
            for(EmptyWave wave:emptyWaves){
                wave.increment();
            }
            Player p = (Player)activeitems.get(0); //guaranteed to be a player
            p.collision(energy);
            //System.out.println(activeitems.size() + " activeitems; ");
            int random = (int)(Math.random()*10.0);
            if(random>8) {
                if (activeitems.size() < 8) {  //TODO: smarter code here
                    enemies.addEnemy((int) activeitems.get(0).gety());
                }
            }

            repaint();
        }
        else{
            Main.screen.repaint();
        }
    }
    public void addWave(Wave wave){
        waves.add(wave);
    }
    public void addEmptyWave(EmptyWave emptyWave){
        emptyWaves.add(emptyWave);
    }
    public void addItem(Item item){
        activeitems.add(item);
    }
    public void paint(Graphics g) {
        Graphics2D ga = (Graphics2D)g;
        ga.clearRect(0, 0, this.getWidth(), this.getHeight());
        ga.setPaint(Color.BLACK);
        ga.fillRect(0, 0, this.getWidth(), this.getHeight());
        Main.space.paint(ga);
        energy.paint(ga);
        display.paint(ga);
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
      //  RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      //  ga.setRenderingHints(rh);
        for(EmptyWave emptyWave:emptyWaves)
            emptyWave.draw(ga);
        for(Wave wave:waves)
            wave.draw(ga);
        for(int i = 0; i<activeitems.size(); i++){
            if(activeitems.get(i).candrawandstep()) activeitems.get(i).draw(ga);
            else{
                if(i>0) {
                    activeitems.remove(i);
                    i--;
                }
            }
        }
    }
}
