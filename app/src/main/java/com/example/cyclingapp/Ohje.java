package com.example.cyclingapp;

/**
 *  @author Sampo Savolainen
 */
public class Ohje {
    String mita;
    int etaisyys;

    public Ohje(String what, int where) {
        this.mita = what;
        this.etaisyys = where;
    }

    /**
     * Returns guides direction variable
     * in example left right
     * @return guides direction
     */
    public String getMita() {
        return mita;
    }

    /**
     * @return chosen guides distance variable
     */
    public int getEtai() {
        return etaisyys;
    }
}
