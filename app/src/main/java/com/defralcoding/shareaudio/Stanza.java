package com.defralcoding.shareaudio;


import android.view.View;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Stanza {

    protected SocketConnettore socket;
    protected MainActivity view;

    public int idStanza = 1;
    public String nomeStanza = "Casa";
    public ArrayList<Utente> utentiStanza;
    public boolean masterConnesso;

    public Canzoni canzoni = new Canzoni();
    public boolean inRiproduzione = false;
    protected boolean shouldPlay = false;
    protected UUID userPaused = null;

    public Stanza(int idStanza, String nomeStanza, ArrayList<Utente> utentiStanza, MainActivity view) {
        this.idStanza = idStanza;
        this.nomeStanza = nomeStanza;
        this.utentiStanza = utentiStanza;
        this.view = view;
        this.masterConnesso = false;

        try {
            socket = new SocketConnettore(new URI("ws://ec2-18-219-160-162.us-east-2.compute.amazonaws.com:8080/?idStanza="+idStanza), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO init da JSON

    public void Apri() {
        this.socket.connect();

        while (!this.socket.isOpen()) {
            //aspetto che il socket sia aperto
        }
    }

    public void ImpostaCanzone(boolean riproduci) {
        if (userPaused != null
                && riproduci
                && (canzoni.inRiproduzione.id == userPaused)) {
            //TODO this.RiprendiCanzone();
        } else {
            //TODO this.RiproduciCanzone();
        }
        this.view.AggiornaCanzone(new Canzone("Autostop", "Shade", "https://i.scdn.co/image/ab67616d0000b273f738d0cd5c80c41983e55a7e"));
    }


    public void RiproduciCanzone(boolean riproduci, boolean daSocket) {
        shouldPlay = riproduci;
        if (riproduci) {
            if (!daSocket) {
                socket.InviaRiproduci();
            }
        }
    }

    public void RiproduciCanzone(boolean riproduci) {
        RiproduciCanzone(riproduci, false);
    }



    public void CanzoneSuccessiva(boolean daSocket) {
        //aggiungo la canzone attuale all'elenco delle canzoni riprodotte
        if (canzoni.inRiproduzione != null) {
            canzoni.riprodotte.add(canzoni.inRiproduzione);
        }
        //estraggo la canzone dall'elenco delle canzoni in coda e la elimino dalla lista
        if (!canzoni.inCoda.isEmpty()) {
            canzoni.inRiproduzione = canzoni.inCoda.get(0);
            canzoni.inCoda.remove(0);
            ImpostaCanzone(shouldPlay);
        } else {
            canzoni.inRiproduzione = null;
        }
        //TODO songDidChange.send()
        if (!daSocket) {
            socket.InviaCanzoneSuccessiva();
        }
    }

    public void CanzoneSuccessiva() {
        CanzoneSuccessiva(false);
    }


    public void CanzonePrecedente(boolean daSocket) {
        if (!canzoni.riprodotte.isEmpty()) { //se esistono canzoni già riprodotte torno alla canzone precedente
            //aggiungo la canzone attuale alle canzoni in coda
            if (canzoni.inRiproduzione != null) {
                canzoni.inCoda.add(0, canzoni.inRiproduzione);
            }
            //estraggo la canzone dall'elenco delle canzoni riprodotte e la elimino dalla lista
            canzoni.inRiproduzione = canzoni.riprodotte.isEmpty() ? canzoni.riprodotte.get(canzoni.riprodotte.size()-1) : null;
            canzoni.riprodotte.remove(canzoni.riprodotte.size() - 1);
            ImpostaCanzone(shouldPlay);
        } else {//se non ci sono canzoni già riprodotte faccio ripartire la canzone attuale
            ImpostaCanzone(shouldPlay);
        }
        //TODO songDidChange.send()
        if (!daSocket) {
            socket.InviaCanzonePrecedente();
        }
    }

    public void CanzonePrecedente() {
        CanzonePrecedente(false);
    }

    public void PausaCanzone(boolean daSocket) {
        shouldPlay = false;
        userPaused = canzoni.inRiproduzione.id;
        if (!daSocket) {
            socket.InviaPausa();
        }
    }

    public void PausaCanzone() {
        PausaCanzone(false);
    }


    public void RiprendiCanzone(boolean daSocket) {
        shouldPlay = true;
        if (!daSocket) {
            socket.InviaRiprendi();
        }
    }

    public void RiprendiCanzone() {
        RiprendiCanzone(false);
    }

    public void AggiungiInTesta(Canzone canzone) {
        canzoni.inCoda.add(0, canzone);
        //TODO socket.InviaCanzoni()
        if (canzoni.inRiproduzione == null) {
            CanzoneSuccessiva();
        }
    }
    
    public void AggiungiInCoda(ArrayList<Canzone> canzoniDaAggiungere) {
        for (Canzone canzoneDaAggiungere : canzoniDaAggiungere) {
            canzoni.inCoda.add(canzoni.inCoda.size() - 1, canzoneDaAggiungere);
        }
        //TODO socket.InviaCanzoni()
        if (canzoni.inRiproduzione == null) {
            CanzoneSuccessiva();
        }
    }

    //TODO public void OrdineCasuale()

    public void ModificaCoda(Canzoni canzoni) {
        this.canzoni = canzoni;
        //TODO songDidChange.send
    }

    public void ListaModificata() {
        //TODO socket.InviaCanzoni
    }

    public void Update() {

    }


}