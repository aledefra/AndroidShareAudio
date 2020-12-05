package com.defralcoding.shareaudio;

import java.util.ArrayList;

public class Canzoni {

    public ArrayList<Canzone> riprodotte;
    public Canzone inRiproduzione;
    public ArrayList<Canzone> inCoda;

    public Canzoni() {
        riprodotte = new ArrayList<Canzone>();
        inRiproduzione = null;
        inCoda = new ArrayList<Canzone>();
    }

}
