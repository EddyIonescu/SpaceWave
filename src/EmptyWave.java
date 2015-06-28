import java.awt.*;

public class EmptyWave extends Wave{
    public EmptyWave(double x, double y, int length){
        super(x, y, length);
    }
    public Vector calculateforce(double x, double y){
        System.out.println("force is weak");
        return new Vector(0,0);
    }
    public void draw(Graphics2D ga){
        if(r<=0) return;
        double d = Math.pow(0.015*r, 2);
        int a = Math.max(0, 255-(int)d);
        int b = Math.max(0, 105-(int)(0.5*d));
        if(a==0 && b==0){
            r = -1;
            return;
        }
        RadialGradientPaint rgp = new RadialGradientPaint((float)x, (float)y,
                100.0f, new float[]{0.1f, 0.5f, 0.9f}, new Color[]{new Color(255, b, b),
                new Color(255, a, a),
                new Color(255, b, b)},
                MultipleGradientPaint.CycleMethod.REPEAT);
        ga.setPaint(rgp);
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float)(1f-r/(0.04*Math.max(Main.game.getWidth(), Main.game.getHeight())))));
        ga.fill(generateDonut(x - r / 2, y - r / 2, r - length, r)); //send top-left
        ga.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.41f));
    }
    public void increment(){
        if(r>0) {
            //r += 5;
            r += 1.7*(8.0 - (0.003*r));
        }
        if(r>0.04*Math.max(Main.game.getWidth(), Main.game.getHeight()))
            r = 0;
    }
}
