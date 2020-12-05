package com.defralcoding.shareaudio;

import org.java_websocket.handshake.ServerHandshake;

import javax.websocket.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;


public class SocketConnettore extends WebSocketClient {


    private Stanza stanza;


    public SocketConnettore(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public SocketConnettore(URI serverURI) {
        super(serverURI);
    }

    public SocketConnettore(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }


    public SocketConnettore(URI serverURI, Stanza stanza) {
        super(serverURI);
        this.stanza = stanza;
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);

        switch (message) {
            case "riproduci":
                stanza.RiproduciCanzone(true, true);
                break;
            case "pausa":
                stanza.PausaCanzone(true);
                break;
            case "riprendi":
                stanza.RiprendiCanzone(true);
                break;
            case "canzoneSuccessiva":
                stanza.CanzoneSuccessiva(true);
                break;
            case "canzonePrecedente":
                stanza.CanzonePrecedente(true);
                break;
            case "update":
                stanza.Update();
                break;
            case "masterOn":
                ((StanzaSlave) stanza).MasterCollegato();
                break;
            default:
                System.out.println("Default");

        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }


    //Funzioni di comunicazione con il server
    /// Funzione pre inviare il comando di Riproduci
    public void InviaRiproduci() {
        super.send("riproduci");
    }

    /// Funzione per inviare il comando di Pausa
    public void InviaPausa() {
        super.send("pausa");
    }

    /// Funzione per inviare il comando Riprendi (dopo pausa)
    public void InviaRiprendi() {
        super.send("riprendi");
    }

    /// Funzione per inviare il comando Canzone Successiva
    public void InviaCanzoneSuccessiva() {
        super.send("canzoneSuccessiva");
    }

    /// Funzione per inviare il comando Canzone Precedente
    public void InviaCanzonePrecedente() {
        super.send("canzonePrecedente");
    }

    /// Funzione per inviare la lista contenente le canzoni in coda
    /*
    public void InviaCanzoni(canzoni: Canzoni) {
        DispatchQueue.main.async {
            if let data = try? JSONEncoder().encode(canzoni) {
                super.send(String(data: data, encoding: .utf8) ?? "error")
            }
        }
    }
*/
    /// Funzione per inviare la lista contenente le canzoni in coda
    public void InviaRichiestaUpdate() {
        super.send("update");
    }

    /// Funzione per inviare la lista contenente le canzoni in coda
    public void InviaCollegamentoMaster() {
        super.send("masterOn");
    }


}