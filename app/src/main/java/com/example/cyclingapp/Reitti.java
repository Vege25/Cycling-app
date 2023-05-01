package com.example.cyclingapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author Sampo Savolainen
 */
public class Reitti {
    String nimi;
    ArrayList<Ohje> ohjeet;

    public Reitti(String name, ArrayList<Ohje> navigate) {
        this.nimi = name;
        this.ohjeet = navigate;
    }


    /**
     * Calculates length by getEtai() function
     * Palauttaa reitin kokonaispituuden laskemalla sen ohjeiden getEtai() funktiolla
     * @return routes total lenght
     */
    public int laskePituus() {
        int length = 0;

        for (Ohje ohje:ohjeet
        ) {
            length += ohje.getEtai();
        }
        return length;
    }

    /**
     *
     * @param i index of array
     * @return phase length
     */
    public int annaPituus(int i) {
        return ohjeet.get(i).getEtai();
    }

    /**
     * Returns route's guide
     * @param i index
     * @return guide array
     */
    public Ohje annaOhje(int i) {
        return ohjeet.get(i);
    }

    @NonNull
    /**
     * Returns routes data as a string
     * @return String "Name x Meters"
     */
    public String toString() {
        return (this.nimi + " " +this.laskePituus() + " m");
    }

    /**
     * Returns guide arrays length
     * @return length as an integer
     */
    public int annaArrayLength() {
        return ohjeet.size();
    }

}
