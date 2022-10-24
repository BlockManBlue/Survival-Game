public class Save {
    public GameRules gameRules;
    public Player plr1, plr2, plr3, plr4;
    public Entity[] entities;
    public long time;

    public Save(GameRules gr, Player p1, Player p2, Player p3, Player p4, Entity[] e, long t){
        gameRules = gr;
        plr1 = p1;
        plr2 = p2;
        plr3 = p3;
        plr4 = p4;
        entities = e;
        time = t;
    }
}
