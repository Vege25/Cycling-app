package com.example.cyclingapp;
/**
 * Class includes a singleton
 * @author Sampo Savolainen
 */
public class Valittu {
    private static final Valittu ourInstance = new Valittu();
    private Reitti munReitti;
    private boolean set = false;


    /**
     * @return singleton instance
     */
    public static Valittu haeSingle() {
        return ourInstance;
    }


    /**
     * specify reitit-list and change set boolean to true
     * @param annettu valittu reitti
     */
    public void maaritaReitti(Reitti annettu) {
        this.munReitti = annettu;
        this.set = true;
    }

    /**
     * Returns reitit list
     * @return singleton reitit list
     */
    public Reitti annaReitti() { return this.munReitti; }

    /**
     * Checks if singleton is specified
     *@return boolean alustettu status
     */
    public boolean alustettu() {
        return this.set;
    }

}
