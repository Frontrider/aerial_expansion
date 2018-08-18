package hu.frontrider.aerialexpansion.logic;

public class TierRegistry {

    public static final Tier BASIC = new Tier(2000,600,10,"basic",false,.1f);
    public static final Tier HARDENED = new Tier(5000,400,20,"hardened",false,.2f);
    public static final Tier REINFORCED = new Tier(8000,200,40,"reinforced",true,.3f);
    public static final Tier SIGNALUM = new Tier(12000,100,70,"signalum",true,.5f);
    public static final Tier RESONANT = new Tier(20000,10,100,"resonant",true,.6f);


    public static class Tier
    {
        public final int capacity;
        public final int cost;
        public final int drain;
        public final String name;
        public final boolean special;
        public final float factor;

        public Tier(int capacity, int cost, int drain, String name, boolean special, float factor) {
            this.capacity = capacity;
            this.cost = cost;
            this.drain = drain;
            this.name = name;
            this.special = special;
            this.factor = factor;
        }
    }
}
