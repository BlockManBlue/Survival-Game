import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
public class Smoke { // class that automatically handles basic smoke particles
    /*
    REQUIREMENTS:
    -BTools
    */
    Timer timer = new Timer(25, new TimerListener());
    ArrayList<SmokeParticle> particles = new ArrayList<SmokeParticle>();
    Point mainPos;

    // Settings
    int maxParticles;
    Color[] colors;
    boolean debugMode;
    long minLife, maxLife;
    long spawnTime;
    private long spawnTimer = 0;
    double windStrength;
    double[] windDirection;
    int minSize, maxSize;

    public Smoke(Point pos, int maxPart, Color[] c, boolean debugMode, long minL, long maxL, long spawnT, double windStr, double[] windDir, int minS, int maxS){
        mainPos = pos;
        maxParticles = maxPart;
        colors = c;
        this.debugMode = debugMode;
        minLife = minL;
        maxLife = maxL;
        spawnTime = spawnT;
        windStrength = windStr;
        windDirection = windDir;
        minSize = minS;
        maxSize = maxS;
        timer.start();
    }
    
    public void setPos(Point p){
        mainPos = p;
    }

    long previousStartTime = -1;
	long elapsedTime;
    private class TimerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            long now = System.currentTimeMillis();
			elapsedTime = previousStartTime != -1 ? now - previousStartTime : 0;
			previousStartTime = now;

            spawnTimer -= elapsedTime;
            if(spawnTimer <= 0){
                spawnTimer = spawnTime;
                Point startPos = new Point(BTools.randInt(-10, 10), BTools.randInt(-10, 10));
                Color color = colors[BTools.randInt(0, colors.length - 1)];
                particles.add(new SmokeParticle(startPos, BTools.randInt(minSize, maxSize), color, BTools.randInt((int)minLife, (int)maxLife)));
            }

            for(int i = 0; i < particles.size(); i++){

                if(particles.get(i).life <= 0){
                    particles.remove(i);
                    i--;
                }
            }
            for(SmokeParticle s: particles){
                s.position.x += windDirection[0] * windStrength * elapsedTime;
                s.position.y += windDirection[1] * windStrength * elapsedTime;
                s.life -= elapsedTime;
            }
        }
    }

    public void render(Graphics g){
        for(SmokeParticle s: particles){
            s.render(g);
        }
        if(debugMode){
            Color oldColor = g.getColor();
            g.setColor(Color.red);
            g.fillOval(mainPos.x - 10, mainPos.y - 10, 20, 20);
            g.setColor(oldColor);
        }
    }

    private class SmokeParticle{ // individual particle
        Point position; // relative to parent position
        int radius;
        Color color;
        long life;
        public SmokeParticle(Point pos, int r, Color c, long l){
            position = pos;
            radius = r;
            color = c;
            life = l;
        }

        public void render(Graphics g){
            Color oldColor = g.getColor();
            g.setColor(color);
            g.fillOval(mainPos.x + position.x - radius, mainPos.y + position.y - radius, radius * 2, radius * 2);
            g.setColor(oldColor);
        }
    }
}
