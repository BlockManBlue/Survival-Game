import java.awt.*;
public class SmokeEntity extends Smoke {
    Entity parent;
    public SmokeEntity(Point pos, int maxPart, Color[] c, boolean debugMode, long minL, long maxL, long spawnT, double windStr, double[] windDir, int minS, int maxS, Entity p){
        super(pos, maxPart, c, debugMode, minL, maxL, spawnT, windStr, windDir, minS, maxS);
        parent = p;
    }
}
