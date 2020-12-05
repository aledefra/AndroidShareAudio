package com.defralcoding.shareaudio;

import android.view.View;

import java.util.ArrayList;

public class StanzaSlave extends Stanza {

    /*TODO

    override var shouldPlay: Bool {
        didSet {
            self.inRiproduzione = shouldPlay
        }
    }
     */

    public StanzaSlave(int idStanza, String nomeStanza, ArrayList<Utente> utentiStanza, MainActivity view) {
        super(idStanza, nomeStanza, utentiStanza, view);
    }

    @Override
    public void Apri() {
        super.Apri();
        //TODO socket?.eventoMasterCollegato = self.MasterCollegato
        socket.InviaRichiestaUpdate();
    }

    public void MasterCollegato() {
        masterConnesso = true;
    }
}
