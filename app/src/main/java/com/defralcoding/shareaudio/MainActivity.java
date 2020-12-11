package com.defralcoding.shareaudio;

import android.graphics.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Stanza stanza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<Utente> utenti = new ArrayList<Utente>();
        utenti.add(new Utente());
        stanza = new StanzaSlave(1, "Casa", utenti, this);

        stanza.Apri();

        //stanza.RiprendiCanzone();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AggiornaCanzone(Canzone canzone) {

        /*
        try {
            URL url = new URL("http://10.119.120.10:80/img.jpg");
            InputStream is = new BufferedInputStream(url.openStream());
            Bitmap b = BitmapFactory.decodeStream(is);
            ((ImageView) findViewById(R.id.imgCopertina)).setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    */
        //@android:drawable/ic_media_play
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
