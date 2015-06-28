import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Wave{
    protected double x;
    protected double y;
    protected double r;
    protected int length;
    public Wave(double x, double y, int length){
        this.x = x;
        this.y = y;
        this.length = length;
        r = 5;
    }
    public void increment(){
        if(r>0) {
            //r += 5;
            r += 1.7*(8.0 - (0.003*r));
        }
        if(r>4*Math.max(Main.game.getWidth(), Main.game.getHeight()))
            r = 0;
    }
    public Vector calculateforce(double x, double y){
        double f = 0.0;
        double myx = this.x;//+r/2;
        double myy = this.y;//+r/2;
        double d = Math.sqrt((x-myx)*(x-myx)+(y-myy)*(y-myy));
        //distance from centre of item to centre of wave
        if(d<=r && d>=r-length && r>0){
            f = Math.sin((d-(r-length))/(2*Math.PI));
            double lost = Math.max(0, (100-Math.pow(0.015*r, 2)/3.0)/100.0);
            f *= lost;
            System.out.println("force is strong " + f + " and difference is " + (d-r));
            double t = Math.atan2(y-myy, x-myx);
            if(t<0) t += Math.PI*2;
            return new Vector(f, t);
        }
        return new Vector(0,0);
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getR(){
        return r;
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
                100.0f, new float[]{0.1f, 0.5f, 0.9f}, new Color[]{new Color(b, b, b),
                new Color(a, a, a),
                new Color(b, b, b)},
                MultipleGradientPaint.CycleMethod.REPEAT);
        ga.setPaint(rgp);
        ga.fill(generateDonut(x - r / 2, y - r / 2, r - length, r)); //send top-left
    }
    public Shape generateDonut(double x, double y, double innerRadius, double outerRadius) {
        Area a1 = new Area(new Ellipse2D.Double(x, y, outerRadius, outerRadius));
        double innerOffset = (outerRadius - innerRadius)/2;
        Area a2 = new Area(new Ellipse2D.Double(x + innerOffset, y + innerOffset, innerRadius, innerRadius));
        a1.subtract(a2);
        return a1;
    }
}
