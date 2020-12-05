package com.defralcoding.shareaudio;

import java.util.UUID;

public class Canzone {

    public UUID id;
    public String nomeCanzone;
    public String artista;
    public String album;
    public String urlCopertina;
    public String isrc;
    public String idAppleMusic;

    public Canzone() {

    }

    public Canzone(String nomeCanzone, String artista, String urlCopertina) {
        this.nomeCanzone = nomeCanzone;
        this.artista = artista;
        this.urlCopertina = urlCopertina;
    }


}
