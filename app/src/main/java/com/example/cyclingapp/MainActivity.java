package com.example.cyclingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Set;
/**
 * Class shows training options and loads trail data to listview
 * @author Sampo Savolainen
 * @author Veeti Sorakivi
 */
public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();


        // rent users uid to access its data

        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();



        }
    }

    /**
     * Method runs on start
     * Creates new guides with direction and distance
     * Adds them to arraylist
     * Creates a new trail
     * Shows every trail on listview
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        SharedPreferences prefPutData = getSharedPreferences("data" , Activity.MODE_PRIVATE);

        Ohje oikeesata = new Ohje("Oikea", 100);
        Ohje vasenviiskyt = new Ohje("Vasen", 50);
        Ohje vasensataviiskyt = new Ohje("Vasen", 150);
        Ohje oikeekakssataa = new Ohje("Oikea", 200);

        ArrayList<Ohje> ohjeet = new ArrayList<>();

        ohjeet.add(oikeesata);
        ohjeet.add(vasenviiskyt);
        ohjeet.add(oikeesata);
        ohjeet.add(oikeesata);

        Reitti uusreitti = new Reitti("Kulmapolku", ohjeet);

        Log.d("MYAPP", uusreitti.nimi);
        Log.d("MYAPP", Integer.toString(uusreitti.laskePituus()));

        ArrayList<Ohje> toisetOhjeet = new ArrayList<>();

        toisetOhjeet.add(oikeesata);
        toisetOhjeet.add(oikeesata);
        toisetOhjeet.add(vasenviiskyt);
        toisetOhjeet.add(vasenviiskyt);

        Reitti toinenreitti = new Reitti("Hakaniemi", toisetOhjeet);

        ArrayList<Ohje> herewegoagain = new ArrayList<>();

        herewegoagain.add(oikeesata);
        herewegoagain.add(vasensataviiskyt);
        herewegoagain.add(vasenviiskyt);
        herewegoagain.add(oikeekakssataa);

        Reitti kolmasreitti = new Reitti("Suomenlinna street", herewegoagain);

        ArrayList<Ohje> neljasReittiOhjeet = new ArrayList<>();

        neljasReittiOhjeet.add(vasenviiskyt);
        neljasReittiOhjeet.add(oikeekakssataa);
        neljasReittiOhjeet.add(vasensataviiskyt);
        neljasReittiOhjeet.add(oikeesata);
        neljasReittiOhjeet.add(vasenviiskyt);

        Reitti neljasReitti = new Reitti("Vantaa trails", neljasReittiOhjeet);

        ArrayList<Ohje> viidesReittiOhjeet = new ArrayList<>();

        viidesReittiOhjeet.add(vasensataviiskyt);
        viidesReittiOhjeet.add(oikeekakssataa);
        viidesReittiOhjeet.add(vasenviiskyt);
        viidesReittiOhjeet.add(oikeekakssataa);
        viidesReittiOhjeet.add(vasenviiskyt);

        Reitti viidesReitti = new Reitti("Helsinki trails", viidesReittiOhjeet);

        ArrayList<Ohje> kuudesReittiOhjeet = new ArrayList<>();

        kuudesReittiOhjeet.add(oikeekakssataa);
        kuudesReittiOhjeet.add(vasenviiskyt);
        kuudesReittiOhjeet.add(oikeesata);
        kuudesReittiOhjeet.add(oikeekakssataa);
        kuudesReittiOhjeet.add(vasenviiskyt);

        Reitti kuudesReitti = new Reitti("Espoo trails", kuudesReittiOhjeet);

        ArrayList<Ohje> seitsemasReittiOhjeet = new ArrayList<>();

        seitsemasReittiOhjeet.add(vasenviiskyt);
        seitsemasReittiOhjeet.add(oikeekakssataa);
        seitsemasReittiOhjeet.add(oikeesata);

        Reitti seitsemasReitti = new Reitti("Oulu XC rata", seitsemasReittiOhjeet);

        ArrayList<Ohje> kahdeksasReittiOhjeet = new ArrayList<>();

        kahdeksasReittiOhjeet.add(vasenviiskyt);
        kahdeksasReittiOhjeet.add(oikeekakssataa);
        kahdeksasReittiOhjeet.add(vasensataviiskyt);
        kahdeksasReittiOhjeet.add(oikeekakssataa);
        kahdeksasReittiOhjeet.add(oikeesata);
        kahdeksasReittiOhjeet.add(vasensataviiskyt);
        kahdeksasReittiOhjeet.add(vasenviiskyt);

        Reitti kahdeksasReitti = new Reitti("Ruka trails", kahdeksasReittiOhjeet);

        ArrayList<Ohje> yhdeksasReittiOhjeet = new ArrayList<>();

        yhdeksasReittiOhjeet.add(vasenviiskyt);
        yhdeksasReittiOhjeet.add(vasensataviiskyt);
        yhdeksasReittiOhjeet.add(oikeekakssataa);
        yhdeksasReittiOhjeet.add(vasensataviiskyt);
        yhdeksasReittiOhjeet.add(vasenviiskyt);
        yhdeksasReittiOhjeet.add(oikeekakssataa);

        Reitti yhdeksasReitti = new Reitti("Turku mtb", yhdeksasReittiOhjeet);

        ArrayList<Ohje> kymmenesReittiOhjeet = new ArrayList<>();

        kymmenesReittiOhjeet.add(vasenviiskyt);
        kymmenesReittiOhjeet.add(vasensataviiskyt);
        kymmenesReittiOhjeet.add(oikeekakssataa);
        kymmenesReittiOhjeet.add(vasensataviiskyt);
        kymmenesReittiOhjeet.add(vasenviiskyt);
        kymmenesReittiOhjeet.add(oikeekakssataa);

        Reitti kymmenesReitti = new Reitti("Tampere mtb", kymmenesReittiOhjeet);

        ArrayList<Reitti> reitit = new ArrayList<>();
        reitit.add(uusreitti);
        reitit.add(toinenreitti);
        reitit.add(kolmasreitti);
        reitit.add(neljasReitti);
        reitit.add(viidesReitti);
        reitit.add(kuudesReitti);
        reitit.add(seitsemasReitti);
        reitit.add(kahdeksasReitti);
        reitit.add(yhdeksasReitti);
        reitit.add(kymmenesReitti);

        // Rakennetaan lista View

        ListView lv = findViewById(R.id.RoutesList);


        // Annetaan listalle adapteri

        lv.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.row,
                R.id.text_view,
                reitit
        ));

        // <li> listenerit

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Method checks which list element was clicked and starts a training based of wanted trail
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                SharedPreferences.Editor prefDataEditor = prefPutData.edit();
                prefDataEditor.putInt("routeIndex", i);
                prefDataEditor.putBoolean("onRoute", true);
                prefDataEditor.commit();

                Valittu.haeSingle().maaritaReitti(reitit.get(i));
                startActivity(new Intent(MainActivity.this, TrainingInProgressActivity.class));
            }
        });



        if(user == null){
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }
        else if(prefPutData.getBoolean("trainingProgress", false)){
            int index = prefPutData.getInt("routeIndex", 0);
            Valittu.haeSingle().maaritaReitti(reitit.get(index));

            startActivity(new Intent(MainActivity.this, TrainingInProgressActivity.class));
        }
    }

    /**
     * Opens profile activity on a button press
     */
    public void openProfileActivity(View v){
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }

    /**
     * Starts a training activity on a buttonpress
     */
    public void startTrainingActivity(View v){
        SharedPreferences prefPutData = getSharedPreferences("data" , Activity.MODE_PRIVATE);

        SharedPreferences.Editor prefEdit = prefPutData.edit();
        prefEdit.putBoolean("onRoute", false);
        prefEdit.commit();

        startActivity(new Intent(MainActivity.this, TrainingInProgressActivity.class));
    }

    /**
     * Returns to a main activity
     */
    public void returnToMain(View a) {
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

}