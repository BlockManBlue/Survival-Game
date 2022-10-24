public class GameRules { // holds game rules for a save
    public int numPlrs;
    public boolean pvp;
    public boolean dayCycle;
    public boolean showCoords;
    public boolean hunger;
    public boolean moveShop;
    public boolean hardcore;

    public GameRules(int numPlrs, boolean pvp, boolean dayCycle, boolean coords, boolean h, boolean shop, boolean hard){
        this.numPlrs = numPlrs;
        this.pvp = pvp;
        this.dayCycle = dayCycle;
        showCoords = coords;
        hunger = h;
        moveShop = shop;
        hardcore = hard;
    }
    
}
