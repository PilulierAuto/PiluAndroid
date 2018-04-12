package fr.djambae.pilulierautomatique;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Posologie extends AppCompatActivity {
    String Nom;
    public static final String TAG = "App";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posologie);
        Intent Intent = getIntent();
        TextView TxtV = findViewById(R.id.textView2);
        String Txt = Intent.getStringExtra("Nom");
        Nom=Txt;
        Txt = "Bienvenue, "+Txt+".\n Actuellement votre posologie est :";
        TxtV.setText(Txt);
    }

    public void Lancement (View View){
        Intent Int = new Intent(this, Bluetooth.class);
        Int.putExtra("Nom",Nom);
        Log.d(TAG,"Changment activité");
        startActivity(Int);
    }
}
