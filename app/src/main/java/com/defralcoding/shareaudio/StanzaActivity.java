package com.defralcoding.shareaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StanzaActivity extends AppCompatActivity {

    private Stanza stanza;
    ImageView imgCopertina;
    String url = "https://thumbs.dreamstime.com/z/spring-flowers-blue-crocuses-drops-water-backgro-background-tracks-rain-113784722.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<Utente> utenti = new ArrayList<Utente>();
        utenti.add(new Utente());
        stanza = new StanzaSlave(1, "Casa", utenti, this);

        stanza.Apri();

        //stanza.RiprendiCanzone();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stanza);
        imgCopertina = (ImageView) findViewById(R.id.imgCopertina);
        ((TextView) findViewById(R.id.txtNomeStanza)).setText(stanza.nomeStanza);
        DownloadImage downloadImage = new DownloadImage();
        try {
            Bitmap bitmap = downloadImage.execute(url).get();
            imgCopertina.setImageBitmap(bitmap);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imgCopertina.setClipToOutline(true);
    }


    public void AggiornaCanzone(Canzone canzone) {

        //@android:drawable/ic_media_play
        DownloadImage downloadImage = new DownloadImage();
        try {
            Bitmap bitmap = downloadImage.execute(canzone.urlCopertina).get();
            imgCopertina.setImageBitmap(bitmap);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.txtNomeStanza)).setText(stanza.nomeStanza);
        ((TextView) findViewById(R.id.txtTitolo)).setText(canzone.nomeCanzone);
        ((TextView) findViewById(R.id.txtArtista)).setText(canzone.artista);
        

    }

    public void Riproduci(View v) {
        stanza.ImpostaCanzone(true);
        stanza.RiprendiCanzone();
    }


    public void Precedente(View v) {
        stanza.CanzonePrecedente();
    }


    public void Successiva(View v) {
        stanza.CanzoneSuccessiva();
    }

}
